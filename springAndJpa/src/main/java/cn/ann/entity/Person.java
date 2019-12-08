package cn.ann.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-8 15:20
 */
@Entity
@Table(name = "t_person")
public class Person implements Serializable {
    private static final long serialVersionUID = -5964437048712784999L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
