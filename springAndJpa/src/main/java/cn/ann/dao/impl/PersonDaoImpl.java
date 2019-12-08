package cn.ann.dao.impl;

import cn.ann.dao.PersonDao;
import cn.ann.entity.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-8 15:28
 */
@Repository("personDao")
public class PersonDaoImpl implements PersonDao {
    // 通过 @PersistenceContext 可以获取当前事务绑定的EntityManager对象
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Person person) {
        entityManager.persist(person);
    }
}
