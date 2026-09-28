package com.hotelhaven.service;
import com.hotelhaven.dto.RegisterRequest; import com.hotelhaven.model.User; import com.hotelhaven.repository.UserRepository; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service public class UserService { private final UserRepository users; private final PasswordEncoder encoder; public UserService(UserRepository users,PasswordEncoder encoder){this.users=users;this.encoder=encoder;}
@Transactional public User register(RegisterRequest r){String email=r.email().trim().toLowerCase(); if(users.existsByEmailIgnoreCase(email)) throw new IllegalArgumentException("An account with that email already exists."); return users.save(new User(r.name().trim(),email,encoder.encode(r.password())));}
public User getByEmail(String email){return users.findByEmailIgnoreCase(email).orElseThrow();}}
