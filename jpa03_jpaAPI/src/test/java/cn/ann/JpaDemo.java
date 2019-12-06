package cn.ann;

import cn.ann.entity.User;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-5 13:45
 */
public class JpaDemo {
    @Test
    public void JpaTest_persist() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa03");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            Random random = new Random();
            for (int i = 1; i <= 100; i++) {
                User user = new User();
                int year = 1990 + random.nextInt(10);
                LocalDate localDate = LocalDate.of(year, random.nextInt(11) + 1, random.nextInt(28) + 1);
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                user.setLastName("程序员" + i + "号");
                user.setAge(LocalDate.now().getYear() - year);
                user.setEmail(user.getLastName() + "@ann.cn");
                user.setBirthday(date);
                entityManager.persist(user);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
            factory.close();
        }
    }

    @Test
    public void JpaTest_find() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa03");
        EntityManager entityManager = factory.createEntityManager();
        try {
            User user = entityManager.find(User.class, 1L);
            System.out.println("===========================");
            System.out.println(user);
        } finally {
            entityManager.close();
            factory.close();
        }
    }

    @Test
    public void JpaTest_getReference() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa03");
        EntityManager entityManager = factory.createEntityManager();
        try {
            User user = entityManager.getReference(User.class, 1L);
            System.out.println(user.getClass().getName());
            System.out.println("===========================");
            System.out.println(user);
        } finally {
            entityManager.close();
            factory.close();
        }
    }

    @Test
    public void JpaTest_remove() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa03");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            User user = entityManager.find(User.class, 1L);
            entityManager.remove(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
            factory.close();
        }
    }

    /**
     * 传入新建对象
     */
    @Test
    public void JpaTest_merge01() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa03");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            User user = new User();
            Random random = new Random();
            int year = 1990 + random.nextInt(10);
            LocalDate localDate = LocalDate.of(year, random.nextInt(11) + 1, random.nextInt(28) + 1);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            user.setLastName("john");
            user.setBirthday(date);
            user.setAge(LocalDate.now().getYear() - year);
            user.setEmail("john@qq.com");

            User user1 = entityManager.merge(user);
            tx.commit();

            System.out.println(user1.getId());      // 101
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
            factory.close();
        }
    }

    /**
     * 传入游离对象，缓存和数据库中都没有
     */
    @Test
    public void JpaTest_merge02() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa03");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            User user = new User();
            Random random = new Random();
            int year = 1990 + random.nextInt(10);
            LocalDate localDate = LocalDate.of(year, random.nextInt(11) + 1, random.nextInt(28) + 1);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            user.setLastName("tom");
            user.setBirthday(date);
            user.setAge(LocalDate.now().getYear() - year);
            user.setEmail("tom@qq.com");

            user.setId(1000L);

            User user1 = entityManager.merge(user);
            tx.commit();

            System.out.println("user.id = " + user.getId());    // user.id = 1000
            System.out.println("user1.id = " + user1.getId());  // user1.id = 102
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
            factory.close();
        }
    }

    /**
     * 传入游离对象，缓存没有，数据库有
     */
    @Test
    public void JpaTest_merge03() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa03");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            User user = new User();
            Random random = new Random();
            int year = 1990 + random.nextInt(10);
            LocalDate localDate = LocalDate.of(year, random.nextInt(11) + 1, random.nextInt(28) + 1);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            user.setLastName("tom");
            user.setBirthday(date);
            user.setAge(LocalDate.now().getYear() - year);
            user.setEmail("tom@ann.cn");

            user.setId(102L);

            User user1 = entityManager.merge(user);
            tx.commit();

            System.out.println("user.id = " + user.getId());    // user.id = 102
            System.out.println("user1.id = " + user1.getId());  // user1.id = 102
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
            factory.close();
        }
    }

    /**
     * 传入游离对象，缓存中有
     */
    @Test
    public void JpaTest_merge04() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa03");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            User user = new User();
            Random random = new Random();
            int year = 1990 + random.nextInt(10);
            LocalDate localDate = LocalDate.of(year, random.nextInt(11) + 1, random.nextInt(28) + 1);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            user.setLastName("john");
            user.setBirthday(date);
            user.setAge(LocalDate.now().getYear() - year);
            user.setEmail("john@ann.cn");

            user.setId(101L);

            entityManager.find(User.class, 101L);

            User user1 = entityManager.merge(user);
            tx.commit();

            System.out.println("user.id = " + user.getId());    // user.id = 101
            System.out.println("user1.id = " + user1.getId());  // user1.id = 101
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
            factory.close();
        }
    }

    @Test
    public void JpaTest_flush() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa03");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            User user = new User();
            Random random = new Random();
            int year = 1990 + random.nextInt(10);
            LocalDate localDate = LocalDate.of(year, random.nextInt(11) + 1, random.nextInt(28) + 1);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            user.setLastName("john");
            user.setBirthday(date);
            user.setAge(LocalDate.now().getYear() - year);
            user.setEmail("john@ann.cn");

            user.setId(101L);

            entityManager.find(User.class, 101L);

            entityManager.flush();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
            factory.close();
        }
    }
}
