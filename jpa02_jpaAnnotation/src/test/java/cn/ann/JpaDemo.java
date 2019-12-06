package cn.ann;

import cn.ann.entity.User;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
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
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa02");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        try {
            Random random = new Random();
            User user = new User();
            int year = 1990 + random.nextInt(10);
            LocalDate localDate = LocalDate.of(year, random.nextInt(11) + 1, random.nextInt(28) + 1);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            user.setLastName("tom");
            user.setAge(LocalDate.now().getYear() - year);
            user.setEmail(user.getLastName() + "@ann.cn");
            user.setBirthday(date);
            user.setDoubleField(1000d);
            user.setDecimalField(BigDecimal.valueOf(1000));

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

}
