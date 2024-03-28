package com.chotot.doantotnghiep.entity;

import com.chotot.doantotnghiep.utils.Action;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class UserEntity{

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createddate", nullable = false, updatable = false)
    @CreatedDate
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createdDate;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "atm")
    private String atm;

    @Column(name = "status")
    private Boolean status = true;

    @Column(name = "modifieddate")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    @LastModifiedDate
    private Date modifiedDate;

    @Column(name = "action")
    private Action action;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "roleid"))
    private List<RoleEntity> roles;


    @OneToMany(mappedBy = "seller")
    private List<ProductEntity> product;

    @OneToMany(mappedBy = "buyer")
    private List<OrderEntity> order;

}
