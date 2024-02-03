package com.akshay.HappyCakeDay.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;
import jakarta.persistence.Entity;

@Entity
@Data

@AllArgsConstructor


@Table(name = "user")
public class User {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @Column(name = "firstname")
    private String  firstName;

    @Column(name = "lastname")
    private String  lastName;

    @Column(name = "email", nullable=false, unique = true)

    @Email(message = "{errors.invalid_email}")
    private String  email;

    @Column(name = "password", length = 255)
    private String  password;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
                joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
        inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")}
    )


    private List<Role> roles;

    public User(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    public User() {
    }
}
