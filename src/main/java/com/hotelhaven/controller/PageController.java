package com.hotelhaven.controller;
import org.springframework.stereotype.Controller; import org.springframework.web.bind.annotation.GetMapping;
@Controller public class PageController { @GetMapping({"/","/index.html"}) String home(){return "index";} @GetMapping("/login") String login(){return "login";} @GetMapping("/register") String register(){return "register";} @GetMapping("/dashboard") String dashboard(){return "dashboard";} }
