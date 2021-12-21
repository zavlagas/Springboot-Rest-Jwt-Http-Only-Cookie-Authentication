package com.zavlagas.spring.security.defence.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zavlagas.spring.security.defence.constants.Permission;
import com.zavlagas.spring.security.defence.constants.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Column(nullable = false, updatable = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private String firstName;

    @Column(nullable = false, updatable = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private String lastName;

    @Column(nullable = false, updatable = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private String username;

    @Column(nullable = false, updatable = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private String password;

    @Column(nullable = false, updatable = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    private String email;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    private boolean isEnabled;

    private boolean isNotLocked;

    public User() {
    }

    public User(String firstName, String lastName, String username, String password, String email, Role role, boolean isEnabled, boolean isNotLocked) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.isEnabled = isEnabled;
        this.isNotLocked = isNotLocked;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getEmail() {
        return email;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }


    public boolean isNotLocked() {
        return isNotLocked;
    }

    public void setNotLocked(boolean notLocked) {
        isNotLocked = notLocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getUsername().equals(user.getUsername()) && getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword());
    }
}
