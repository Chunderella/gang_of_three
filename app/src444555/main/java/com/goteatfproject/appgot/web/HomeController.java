package com.goteatfproject.appgot.web;

import com.goteatfproject.appgot.service.EventService;
import com.goteatfproject.appgot.service.FeedService;
import com.goteatfproject.appgot.service.MemberService;
import com.goteatfproject.appgot.service.PartyService;
import com.goteatfproject.appgot.vo.Event;
import com.goteatfproject.appgot.vo.Feed;
import com.goteatfproject.appgot.vo.Party;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;

@Controller
public class HomeController {

  PartyService partyService;
  FeedService feedService;
  EventService eventService;
  ServletContext sc;

  public HomeController(PartyService partyService, FeedService feedService,
                        EventService eventService, ServletContext sc) {
    this.partyService = partyService;
    this.feedService = feedService;
    this.eventService = eventService;
    this.sc = sc;
  }

  
  //메인페이지에서 버튼을 눌렀을때 게시물을 노출시키기 위해서 
  //mainList(meal,food) 추가함
  @GetMapping("/")
  public String List(Model model,String meal, String food) throws Exception {
    model.addAttribute("feeds",feedService.mainList());
    model.addAttribute("events",eventService.mainList());
    model.addAttribute("parties",partyService.mainList(meal, food));
    model.addAttribute("meal", meal);
    model.addAttribute("food", food);

    return "index";
  }

}
