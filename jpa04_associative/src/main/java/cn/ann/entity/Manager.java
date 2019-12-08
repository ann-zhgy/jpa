package cn.ann.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-6 19:42
 */
@Entity
@Table(name = "t_manager")
public class Manager implements Serializable {
    private static final long serialVersionUID = 3961648472714219946L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", unique = true)
    private Department department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                '}';
    }
}
