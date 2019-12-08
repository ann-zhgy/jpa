package cn.ann;

import cn.ann.entity.Order;
import cn.ann.entity.User;
import org.hibernate.jpa.QueryHints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-5 13:45
 */
public class JpqlDemo {
    private EntityManager entityManager;
    private EntityManagerFactory factory;
    private EntityTransaction transaction;

    @Before
    public void before() {
        factory = Persistence.createEntityManagerFactory("jpa05");
        entityManager = factory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @After
    public void after() {
        transaction.commit();
        entityManager.close();
        factory.close();
    }

    /**
     * 准备垃圾数据
     */
    @Test
    public void JpaTest_persist() {
        Random random = new Random();
        for (int i = 1; i <= 50; i++) {
            User user = new User();
            int year = 1990 + random.nextInt(10);
            user.setLastName("程序员" + i + "号");
            user.setAge(LocalDate.now().getYear() - year);
            user.setEmail(user.getLastName() + "@ann.cn");
            Order order1 = new Order();
            order1.setCreateTime(new Date());
            order1.setOrderDesc("o" + i);
            order1.setUser(user);
            Order order2 = new Order();
            order2.setCreateTime(new Date());
            order2.setOrderDesc("o" + (50 + 2 * i));
            order2.setUser(user);
            entityManager.persist(user);
            entityManager.persist(order1);
            entityManager.persist(order2);
        }
    }

    /**
     * 查询所有：query.setParameter从 1 开始
     */
    @Test
    public void selectTest() {
        String jpql = "from User u where u.age > ?2";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        List<User> list = query.setParameter(2, 22).getResultList();
        list.forEach(System.out::println);
    }

    /**
     * 查询部分属性：默认返回Object[]
     */
    @Test
    public void selectPartlyPropertiesTest01() {
        String jpql = "select u.lastName, u.age from User u where u.age > ?1";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        List<User> list = query.setParameter(1, 22).getResultList();
        list.forEach(System.out::println);
    }

    /**
     * 查询部分属性
     * 实体类必须提供对应的构造方法
     */
    @Test
    public void selectPartlyPropertiesTest02() {
        String jpql = "select new User(u.lastName, u.age) from User u where u.age > ?1";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        List<User> list = query.setParameter(1, 27).getResultList();
        list.forEach(System.out::println);
    }

    /**
     * jpql语句可以使用 @NamedQuery 写在实体类上
     */
    @Test
    public void namedQueryTest() {
        TypedQuery<User> query = entityManager.createNamedQuery("namedQuery", User.class);
        User user = query.setParameter("id", 2L).getSingleResult();
        System.out.println(user);
    }

    /**
     * 原生sql
     */
    @Test
    public void nativeQueryTest01() {
        String sql = "select * from t_user where id = ?";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, 3);
        Object result = query.getSingleResult();
        System.out.println(Arrays.toString((Object[]) result));
    }

    /**
     * 原生sql
     */
    @Test
    public void nativeQueryTest02() {
        String sql = "select * from t_user where id = ?";
        Query query = entityManager.createNativeQuery(sql, User.class);
        query.setParameter(1, 3);
        Object result = query.getSingleResult();
        System.out.println(result);
    }

    /**
     * 查询缓存
     */
    @Test
    public void queryCacheTest() {
        String sql = "select * from t_user";
        Query query = entityManager.createNativeQuery(sql, User.class).setHint(QueryHints.HINT_CACHEABLE, true);
        List list = query.getResultList();
        System.out.println(list.size());
        query = entityManager.createNativeQuery(sql, User.class).setHint(QueryHints.HINT_CACHEABLE, true);
        list = query.getResultList();
        System.out.println(list.size());
    }

    /**
     * order by
     */
    @Test
    public void selectOrderTest() {
        String jpql = "from User u where u.age > :age order by u.id";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        List<User> list = query.setParameter("age", 22).getResultList();
        list.forEach(System.out::println);
    }

    /**
     * group by
     */
    @Test
    public void selectGroupTest() {
        String jpql = "select u.age, count(u.id) as count from User u group by u.age having u.age > :age ";
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> list = query.setParameter("age", 22).getResultList();
        list.forEach(o -> System.out.println(Arrays.toString(o)));
    }

    /**
     * 左外连接
     */
    @Test
    public void relationalSelectTest_LeftOuterJoin() {
        String jpql = "from User u left outer join fetch u.orders where u.age > :age";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        List<User> list = query.setParameter("age", 22).getResultList();
        list.forEach(System.out::println);
        for (User user : list) {
            System.out.println(user.getOrders());
        }
    }

    /**
     * 子查询
     */
    @Test
    public void relationalSelectTest_subSelect() {
        String jpql = "from Order o where o.user in (from User u where u.age = :age)";
        TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class);
        List<Order> list = query.setParameter("age", 22).getResultList();
        list.forEach(System.out::println);
    }

    /**
     * 内建函数
     */
    @Test
    public void relationalSelectTest_func() {
        String jpql = "select upper(u.email) from User u";
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
        query.getResultList().forEach(System.out::println);
    }

    /**
     * update
     */
    @Test
    public void updateTest() {
        String jpql = "update User u set u.lastName = :lastName where u.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("lastName", "john");
        query.setParameter("id", 1L);
        query.executeUpdate();
    }

    /**
     * delete
     */
    @Test
    public void deleteTest() {
        String jpql = "delete Order o where o.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("id", 1L);
        query.executeUpdate();
    }

}
