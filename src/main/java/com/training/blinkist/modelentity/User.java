package com.training.blinkist.modelentity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

//Entity class that defines a particular User

@Entity
@Getter
@Setter
@Table(name = "blinkist_user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;


    @NotBlank(message = "Please provide the name.")
    private String name;

    @NotBlank(message = "Please provide the email.")
    @Column(name = "email", unique = true)
    private String email;


    @NotBlank(message = "Password can not be empty.")
    private String password;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "User can not be empty.")
    private String username;

    @Column(name = "phone", unique = true)
    private long phone;

    @JsonProperty
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "creation_date", updatable = false)
    @CreationTimestamp
    private Date creationDate;

    @JsonProperty
    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    @GeneratedValue(strategy = GenerationType.AUTO)
    // ID to identify all user resources
    @Column(name = "subscription_id")
    private UUID subscriptionId;





}
