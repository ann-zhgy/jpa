package cn.ann.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-5 13:36
 */
@Entity
@Table(name = "t_user")
public class User implements Serializable {
    private static final long serialVersionUID = -5201721404180914044L;

    @Id
    /*@TableGenerator(name = "tableId",
            table = "t_id",
            allocationSize = 1,
            initialValue = 1,
            pkColumnName = "pk_name",
            pkColumnValue = "pk_value",
            valueColumnName = "user_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableId")*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "last_name")
    private String lastName;
    @Basic
    private Integer age;
    private String email;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Column(columnDefinition = "double(8,2)")
    private Double doubleField;
    @Column(precision = 8, scale = 2)
    private BigDecimal decimalField;

    public User() {
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(Double doubleField) {
        this.doubleField = doubleField;
    }

    public BigDecimal getDecimalField() {
        return decimalField;
    }

    public void setDecimalField(BigDecimal decimalField) {
        this.decimalField = decimalField;
    }

    @Transient
    public String getInfo() {
        return this.getId() + " : " + this.getLastName();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
