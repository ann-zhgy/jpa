package cn.ann;

import cn.ann.entity.Person;
import cn.ann.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-8 15:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class Demo {
    @Resource(name = "personService")
    private PersonService personService;

    @Test
    public void test01() {
        Person person = new Person();
        person.setName("p1");
        personService.save(person);
    }
}
