package com.goteatfproject.appgot.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goteatfproject.appgot.service.MemberService;
import com.goteatfproject.appgot.vo.Member;
import org.springframework.boot.web.server.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {

  MemberService memberService;

  public MemberController(MemberService memberService) {
    System.out.println("MemberController() 호출됨!");
    this.memberService = memberService;
  }

  @GetMapping ("/add")
  public String add() throws Exception {
    return "member/memberForm";
  }

  @PostMapping("/add")
  public String add(Member member) throws Exception {
    memberService.add(member);
    return "redirect:list";
  }

  @GetMapping("/list")
  public String list(Model model) throws Exception {
    model.addAttribute("members", memberService.list());
    return "member/memberList";
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



    ModelAndView mv = new ModelAndView("/auth/loginResult");
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

