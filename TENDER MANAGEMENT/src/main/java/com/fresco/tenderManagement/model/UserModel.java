package com.fresco.tenderManagement.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "Users")
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "Username")
    private String username;
    @Column(name = "Companyname")
    private String companyName;
    @Column(name = "password")
    private String password;
    @Column(name = "email", unique = true)
    private String email;
    @OneToOne
    @JoinColumn(name = "role", referencedColumnName = "id")
    private RoleModel role;



    //constructors

    public UserModel() {
    }

    public UserModel(int id, String username, String companyName, String password, String email, RoleModel role) {
        this.id = id;
        this.username = username;
        this.companyName = companyName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public UserModel(int id, String username, String password, String email, RoleModel role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public UserModel(String username, String password, String email, RoleModel role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }


    public UserModel(String password, String email) {
        this.password = password;
        this.email = email;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    //to-string


    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", companyName='" + companyName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        List<SimpleGrantedAuthority> a= new ArrayList<>();
        a.add(new SimpleGrantedAuthority(role.getRolename()));
        return a;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}