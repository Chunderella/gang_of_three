//package com.goteatfproject.appgot.service;
//
//import com.goteatfproject.appgot.dao.EventDao;
//import com.goteatfproject.appgot.dao.MemberDao;
//import com.goteatfproject.appgot.dao.TicketDao;
//import com.goteatfproject.appgot.vo.Party;
//import com.goteatfproject.appgot.vo.Ticket;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//public interface DefaultTicketService implements TicketService{
//
//
//  @Autowired
//  TicketDao ticketDao;
//
//  @Autowired
//  EventDao eventdao;
//
//  @Autowired
//  MemberDao memberDao;
//
//
//  @Transactional
//  public void add(Ticket ticket) throws Exception {
//
//    if (ticketDao.insert(ticket) == 0) {
//      throw new Exception("게시글 등록 실패!");
//    }
//  }
//
//}
