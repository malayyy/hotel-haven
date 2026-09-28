package com.hotelhaven.dto;
import jakarta.validation.constraints.*; import java.time.LocalDate;
public record BookingRequest(@NotNull Long roomId,@NotNull @FutureOrPresent LocalDate checkIn,@NotNull LocalDate checkOut,@Min(1) int guests) {}
