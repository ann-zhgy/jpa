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
public class JpaManyToOneDemo {
    @Test
    public void manyToOneTest_persist() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa04");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            User user = new User();
            user.setAge(20);
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1999-03-12"));
            user.setLastName("john");
            user.setEmail("john@ann.cn");

            Order order1 = new Order();
            Order order2 = new Order();
            order1.setOrderDesc("order1");
            order2.setOrderDesc("order2");
            order1.setCreateTime(new Date());
            order2.setCreateTime(new Date());
            order1.setUser(user);
            order2.setUser(user);

            entityManager.persist(user);
            entityManager.persist(order1);
            entityManager.persist(order2);
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
     * 默认使用左外连接查询多的一方
     * 将 fetch 改为 FetchType.LAZY 后，会发送两条sql语句查询
     */
    @Test
    public void manyToOneTest_find() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa04");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            Order order = entityManager.find(Order.class, 1L);
            System.out.println("order = " + order);
            System.out.println("order.user = " + order.getUser());
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
            Order order = entityManager.find(Order.class, 2L);
            order.getUser().setLastName("Tom");
            entityManager.merge(order);
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
     * 删除多的一方时，发送一条sql语句
     */
    @Test
    public void manyToOneTest_remove01() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa04");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            Order order = entityManager.find(Order.class, 1L);
            entityManager.remove(order);

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
    public void manyToOneTest_remove02() {
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
