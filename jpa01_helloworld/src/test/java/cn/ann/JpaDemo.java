package cn.ann;

import cn.ann.entity.User;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Random;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-5 13:45
 */
public class JpaDemo {
    @Test
    public void JpaTest_persist() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa01");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            Random random = new Random();
            for (int i = 1; i <= 100; i++) {
                User user = new User();
                int year = 1990 + random.nextInt(10);
                user.setLastName("程序员" + i + "号");
                user.setAge(LocalDate.now().getYear() - year);
                user.setEmail(user.getLastName() + "@ann.cn");
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

}
