package cn.ann.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-5 13:36
 */
@NamedQuery(name = "namedQuery", query = "from User u where u.id = :id")
@Cacheable(true)
@Entity
@Table(name = "t_user")
public class User implements Serializable {
    private static final long serialVersionUID = -5201721404180914044L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "last_name")
    private String lastName;
    private Integer age;
    private String email;

    @OneToMany(targetEntity = Order.class, mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    public User() {
    }

    public User(String lastName, Integer age) {
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
