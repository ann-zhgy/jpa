package cn.ann.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Create By 88475 With IntelliJ IDEA On 2019-12-6 12:39
 */
@Entity
@Table(name = "t_order")
public class Order implements Serializable {
    private static final long serialVersionUID = -3377980542523646913L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_desc")
    private String orderDesc;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /*@ManyToOne(
            *//*targetEntity = User.class,
            fetch = FetchType.LAZY,
            optional = true,
            cascade = {CascadeType.PERSIST}*//*
    )
    @JoinColumn(name = "user_id")*/
    @Transient
    private User user;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDesc='" + orderDesc + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
