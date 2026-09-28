package com.hotelhaven.controller;
import com.hotelhaven.dto.*; import com.hotelhaven.model.*; import com.hotelhaven.repository.*; import com.hotelhaven.service.*; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api") public class ApiController { private final UserService users; private final RoomRepository rooms; private final BookingService bookings; public ApiController(UserService u,RoomRepository r,BookingService b){users=u;rooms=r;bookings=b;}
 @GetMapping("/rooms") List<Room> rooms(){return rooms.findAll();}
 @PostMapping("/register") ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req){try{User u=users.register(req);return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail()));}catch(IllegalArgumentException e){return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error",e.getMessage()));}}
 @GetMapping("/me") Map<String,Object> me(Authentication a){User u=users.getByEmail(a.getName());return Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail());}
 @GetMapping("/bookings") List<Booking> history(Authentication a){return bookings.history(a.getName());}
 @PostMapping("/bookings") ResponseEntity<?> create(Authentication a,@Valid @RequestBody BookingRequest req){try{return ResponseEntity.status(HttpStatus.CREATED).body(bookings.create(a.getName(),req));}catch(IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}}
}
