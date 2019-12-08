package cn.ann.service.impl;

import cn.ann.dao.PersonDao;
import cn.ann.entity.Person;
import cn.ann.service.PersonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-8 15:33
 */
@Service("personService")
public class PersonServiceImpl implements PersonService {
    @Resource(name = "personDao")
    private PersonDao personDao;

    @Override
    public void save(Person person) {
        personDao.save(person);
    }
}
