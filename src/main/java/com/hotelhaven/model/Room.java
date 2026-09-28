package com.hotelhaven.model;

import jakarta.persistence.*;

@Entity
@Table(name="rooms")
public class Room {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false) private String name;
  @Column(nullable=false,length=1000) private String description;
  @Column(nullable=false) private double price;
  @Column(nullable=false) private int capacity;
  @Column(nullable=false,length=500) private String image;
  @Column(nullable=false,length=1000) private String amenities;
  protected Room() {}
  public Room(String name,String description,double price,int capacity,String image,String amenities){this.name=name;this.description=description;this.price=price;this.capacity=capacity;this.image=image;this.amenities=amenities;}
  public Long getId(){return id;} public String getName(){return name;} public String getDescription(){return description;} public double getPrice(){return price;} public int getCapacity(){return capacity;} public String getImage(){return image;} public String getAmenities(){return amenities;}
}
