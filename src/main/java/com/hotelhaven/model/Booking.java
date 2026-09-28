package com.hotelhaven.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="bookings")
public class Booking {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne(optional=false,fetch=FetchType.LAZY) private User user;
  @ManyToOne(optional=false,fetch=FetchType.EAGER) private Room room;
  @Column(nullable=false) private LocalDate checkIn;
  @Column(nullable=false) private LocalDate checkOut;
  @Column(nullable=false) private int guests;
  @Column(nullable=false) private double totalPrice;
  @Column(nullable=false) private String status;
  protected Booking() {}
  public Booking(User user,Room room,LocalDate checkIn,LocalDate checkOut,int guests,double totalPrice){this.user=user;this.room=room;this.checkIn=checkIn;this.checkOut=checkOut;this.guests=guests;this.totalPrice=totalPrice;this.status="CONFIRMED";}
  public Long getId(){return id;} public User getUser(){return user;} public Room getRoom(){return room;} public LocalDate getCheckIn(){return checkIn;} public LocalDate getCheckOut(){return checkOut;} public int getGuests(){return guests;} public double getTotalPrice(){return totalPrice;} public String getStatus(){return status;}
}
