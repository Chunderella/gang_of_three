package com.goteatfproject.appgot.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.goteatfproject.appgot.service.MemberService;
import com.goteatfproject.appgot.vo.Member;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class LoginController {

  MemberService memberService;

  public LoginController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/login")
  public String login(@CookieValue(name="id", defaultValue = "") String id, Model model, HttpSession session) throws Exception {

    model.addAttribute("id", id);
    return "auth/login"; // TODO login 다시 복구
  }

  @PostMapping("/login")
  public String login(
      String id,
      String password,
      String saveEmail,
      String toUrl, // TODO url2
      HttpServletResponse response,
      HttpSession session) throws Exception {

    Member member = memberService.get(id, password);

    System.out.println("password = " + password);
    //    System.out.println("userpassword = " + user.getPassword());

    if (member != null) {
      session.setAttribute("loginMember", member);
    }

    Cookie cookie = new Cookie("id", id);
    if (saveEmail == null) {
      cookie.setMaxAge(0);
    } else {
      cookie.setMaxAge(60 * 60 * 24 * 7);
      cookie.setPath("/");
    }

    response.addCookie(cookie);

    if (id.equals("admin@test.com")){
      return "redirect:/admin/main";
    }

    return "redirect:/";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }


  static final String BASE_URL = "https://kauth.kakao.com";
  static final String AUTHORIZE_URL = "/oauth/authorize";
  static final String TOKEN_URL = "/oauth/token";
  static final String REST_API_KEY = "a6321ae7ab283db449919d4052cd51e3";
  static final String REDIRECT_URL = "http://localhost:8080/auth/kakaoLogin";

  @RequestMapping("/kakaoLoginPage")
  String kakaoLoginPage() {
    String parameter = String.format("?client_id=%s&redirect_uri=%s&response_type=%s",
        REST_API_KEY,
        REDIRECT_URL,
        "code");

    return String.format("redirect:%s%s%s", BASE_URL, AUTHORIZE_URL, parameter);
  }

  @RequestMapping("/kakaoLogin")
  public ModelAndView kakaoLogin(
      @RequestParam String code,
      HttpServletResponse response,
      HttpSession session) throws Exception {

    URL url = new URL(BASE_URL+TOKEN_URL);

    System.out.println(code);


    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    connection.setDoOutput(true);

    System.out.println(code);

    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))) {
      writer.write(String.format("grant_type=%s&client_id=%s&redirect_uri=%s&code=%s",
          "authorization_code",
          REST_API_KEY,
          REDIRECT_URL,
          code));
      writer.flush();
    }

    StringBuilder builder = new StringBuilder();
    String temp;
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
      while ((temp = reader.readLine()) != null) builder.append(temp);
    }
    Map<String, Object> responseData = jsonToMap(builder.toString());
    Map<String, Object> userData = userData((String)responseData.get("access_token"));

    Member member = memberService.selectKakaoId((long)userData.get("id"));



    ModelAndView mv = new ModelAndView("/loginresult");
    mv.addObject("member", member);
    return mv;
  }

  Map<String, Object> userData(@RequestParam String accessToken) throws Exception {
    URL url = new URL("https://kapi.kakao.com/v2/user/me");
    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Authorization", String.format("Bearer %s", accessToken));
    connection.setDoOutput(true);

    StringBuilder builder = new StringBuilder();
    String temp;
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
      while ((temp = reader.readLine()) != null) builder.append(temp);
    }
    Map<String, Object> responseData = jsonToMap(builder.toString());

    return responseData;
  }

  private static ObjectMapper objectMapper;
  private static ObjectMapper getObjectMapper() {
    if (objectMapper == null) {
      objectMapper = new ObjectMapper();
    }
    return objectMapper;
  }
  public static Map<String, Object> jsonToMap(String json) throws Exception {
    return getObjectMapper().readValue(json, new TypeReference< Map<String, Object>>(){});
  }

}
