package com.user.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(unique = true,nullable = false)
    @NotBlank
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 255)
    @Email
    private String email;

    @Size(min=10,max=10)
    private  String mobileNo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "userid"))
    @Column(name = "role")
    private List<String> roles=new ArrayList<>();



}
