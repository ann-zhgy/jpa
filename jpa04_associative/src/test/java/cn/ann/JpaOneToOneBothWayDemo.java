package cn.ann;

import cn.ann.entity.Department;
import cn.ann.entity.Manager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-6 19:56
 */
public class JpaOneToOneBothWayDemo {
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
    public void jpaOneToOneBothWayTest_persist() {
        Department department = new Department();
        department.setName("department02");
        Manager manager = new Manager();
        manager.setName("manager02");
        manager.setDepartment(department);
        entityManager.persist(department);
        entityManager.persist(manager);
    }

    @Test
    public void jpaOneToOneBothWayTest_find01() {
        Manager manager = entityManager.find(Manager.class, 1L);
        System.out.println("manager = " + manager);
        System.out.println("manager.dept = " + manager.getDepartment());
        System.out.println("manager.dept.class = " + manager.getDepartment().getClass().getName());
    }

    @Test
    public void jpaOneToOneBothWayTest_find02() {
        Department department = entityManager.find(Department.class, 1L);
        System.out.println("department = " + department);
        System.out.println("department.getManager() = " + department.getManager());
        System.out.println("department.getManager().getClass() = " + department.getManager().getClass());
    }

}
