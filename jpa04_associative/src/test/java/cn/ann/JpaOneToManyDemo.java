package cn.ann;

import cn.ann.entity.Order;
import cn.ann.entity.User;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-5 13:45
 */
public class JpaOneToManyDemo {
    @Test
    public void oneToManyTest_persist() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa04");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            Order order1 = new Order();
            Order order2 = new Order();
            order1.setOrderDesc("order1");
            order2.setOrderDesc("order2");
            order1.setCreateTime(new Date());
            order2.setCreateTime(new Date());

            User user = new User();
            user.setAge(20);
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1999-03-12"));
            user.setLastName("john");
            user.setEmail("john@ann.cn");
            user.getOrders().add(order1);
            user.getOrders().add(order2);

            entityManager.persist(order1);
            entityManager.persist(order2);
            entityManager.persist(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
            factory.close();
        }
    }

    /**
     * 默认使用懒加载
     */
    @Test
    public void oneToManyTest_find() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa04");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            User user = entityManager.find(User.class, 1L);
            System.out.println(user);
            System.out.println(user.getOrders());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
            factory.close();
        }
    }

    @Test
    public void manyToOneTest_merge() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa04");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            User user = entityManager.find(User.class, 1L);
            user.getOrders().iterator().next().setOrderDesc("order===");
            entityManager.merge(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
            factory.close();
        }
    }

    /**
     * 删除一的一方时，不能直接删除，因为有外键约束
     */
    @Test
    public void manyToOneTest_remove() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa04");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            User user = entityManager.find(User.class, 1L);
            entityManager.remove(user);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
            factory.close();
        }
    }

}
