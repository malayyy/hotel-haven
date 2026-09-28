package com.hotelhaven;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelhaven.dto.RegisterRequest;
import com.hotelhaven.model.User;
import com.hotelhaven.repository.BookingRepository;
import com.hotelhaven.repository.RoomRepository;
import com.hotelhaven.repository.UserRepository;
import com.hotelhaven.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HotelHavenApplicationTests {
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper json;
  @Autowired UserService userService;
  @Autowired UserRepository users;
  @Autowired RoomRepository rooms;
  @Autowired BookingRepository bookings;
  @Autowired PasswordEncoder encoder;

  @Test void registrationWorksAndPasswordIsHashed() throws Exception {
    mvc.perform(post("/api/register").with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(json.writeValueAsString(new RegisterRequest("Ava Stone","ava@example.com","password123"))))
      .andExpect(status().isCreated()).andExpect(jsonPath("$.email").value("ava@example.com"));
    User u=users.findByEmailIgnoreCase("ava@example.com").orElseThrow();
    Assertions.assertNotEquals("password123",u.getPassword());
    Assertions.assertTrue(encoder.matches("password123",u.getPassword()));
  }

  @Test void duplicateRegistrationIsRejected() throws Exception {
    userService.register(new RegisterRequest("Ava Stone","ava@example.com","password123"));
    mvc.perform(post("/api/register").with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .content(json.writeValueAsString(new RegisterRequest("Another Ava","AVA@EXAMPLE.COM","password123"))))
      .andExpect(status().isConflict()).andExpect(jsonPath("$.error",containsString("already exists")));
  }

  @Test void protectedDashboardRequiresLogin() throws Exception {
    mvc.perform(get("/dashboard")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrlPattern("**/login"));
  }

  @Test void loginAuthenticatesRegisteredUser() throws Exception {
    userService.register(new RegisterRequest("Ava Stone","ava@example.com","password123"));
    mvc.perform(post("/login").with(csrf()).param("username","ava@example.com").param("password","password123"))
      .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/dashboard"));
  }

  @Test void roomsAreRetrievable() throws Exception {
    mvc.perform(get("/api/rooms")).andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(3))).andExpect(jsonPath("$[*].name",containsInAnyOrder("Deluxe Room","Ocean Suite","Executive Room")));
  }

  @Test void authenticatedUserCanCreateAndRetrieveBooking() throws Exception {
    User u=userService.register(new RegisterRequest("Ava Stone","ava@example.com","password123"));
    Long roomId=rooms.findAll().get(0).getId();
    String body=json.writeValueAsString(Map.of("roomId",roomId,"checkIn",LocalDate.now().plusDays(2).toString(),"checkOut",LocalDate.now().plusDays(5).toString(),"guests",2));
    mvc.perform(post("/api/bookings").with(user(u.getEmail())).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(body))
      .andExpect(status().isCreated()).andExpect(jsonPath("$.totalPrice").value(rooms.findById(roomId).orElseThrow().getPrice()*3));
    mvc.perform(get("/api/bookings").with(user(u.getEmail())))
      .andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(1))).andExpect(jsonPath("$[0].room.id").value(roomId));
  }
}
