package com.chotot.doantotnghiep.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {


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

    @Column(name = "status")
    private Boolean status;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "roleid"))
    private List<RoleEntity> roles;


    @OneToMany(mappedBy = "user")
    private List<ProductEntity> product;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> order;
}
