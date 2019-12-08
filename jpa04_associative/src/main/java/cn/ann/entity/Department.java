package cn.ann.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-6 19:42
 */
@Entity
@Table(name = "t_department")
public class Department implements Serializable {
    private static final long serialVersionUID = 2214499725238161575L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Name;

    @OneToOne(mappedBy = "department", fetch = FetchType.LAZY)
    private Manager manager;

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

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                '}';
    }
}
