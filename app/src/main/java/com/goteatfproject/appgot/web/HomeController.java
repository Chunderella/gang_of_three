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

  @GetMapping("/")
  public String List(Model model) throws Exception {
    model.addAttribute("parites",partyService.list());
    model.addAttribute("feeds",feedService.list());
    model.addAttribute("events",eventService.list());
    return "index";
  }


}
