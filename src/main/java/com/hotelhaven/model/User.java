package com.hotelhaven.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="users", uniqueConstraints=@UniqueConstraint(columnNames="email"))
public class User {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false) private String name;
  @Column(nullable=false, unique=true) private String email;
  @Column(nullable=false) private String password;
  @Column(nullable=false) private LocalDateTime createdAt;
  protected User() {}
  public User(String name,String email,String password){this.name=name;this.email=email;this.password=password;this.createdAt=LocalDateTime.now();}
  public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public String getPassword(){return password;} public LocalDateTime getCreatedAt(){return createdAt;}
}
