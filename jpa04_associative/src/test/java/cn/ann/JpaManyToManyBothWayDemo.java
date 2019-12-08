package cn.ann;

import cn.ann.entity.Menu;
import cn.ann.entity.Role;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-7 14:17
 */
public class JpaManyToManyBothWayDemo {
    private EntityManager entityManager;
    private EntityManagerFactory factory;
    private EntityTransaction transaction;

    @Before
    public void before() {
        factory = Persistence.createEntityManagerFactory("jpa04");
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

    @Test
    public void manyToManyBothWayTest_persist() {
        Role role1 = new Role();
        role1.setName("r1");
        Role role2 = new Role();
        role2.setName("r2");
        Menu menu1 = new Menu();
        menu1.setName("m1");
        Menu menu2 = new Menu();
        menu2.setName("m2");

        role1.getMenus().add(menu1);
        role1.getMenus().add(menu2);
        role2.getMenus().add(menu1);
        role2.getMenus().add(menu2);

        menu1.getRoles().add(role1);
        menu1.getRoles().add(role2);
        menu2.getRoles().add(role1);
        menu2.getRoles().add(role2);

        entityManager.persist(role1);
        entityManager.persist(role2);
        entityManager.persist(menu1);
        entityManager.persist(menu2);
    }

    @Test
    public void manyToManyBothWayTest_find() {
        Role role = entityManager.find(Role.class, 1L);
        System.out.println(role.getMenus());
    }

    @Test
    public void manyToManyBothWayTest_merge() {
        Role role = entityManager.find(Role.class, 1L);
        role.getMenus().remove(0);
        entityManager.merge(role);
    }

    @Test
    public void manyToManyBothWayTest_firstCache() {
        // 只发送一次SQL
        Role role1 = entityManager.find(Role.class, 1L);
        Role role2 = entityManager.find(Role.class, 1L);
    }

}
