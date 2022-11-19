//package com.goteatfproject.appgot.service;
//
//import com.goteatfproject.appgot.dao.EventDao;
//import com.goteatfproject.appgot.dao.TicketDao;
//import com.goteatfproject.appgot.vo.Ticket;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//public interface DefaultTicketService implements TicketService {
//
//
//    @Autowired
//    TicketDao ticketDao;
//
//    @Autowired
//    EventDao eventdao;
//
//
//    @Transactional
//    public void add(Ticket ticket) throws Exception {
//
//        if (ticketDao.insert(ticket) == 0) {
//            throw new Exception("게시글 등록 실패!");
//        }
//
//    }
//    @Override
//    public List<Ticket> list() throws Exception {
//        return ticketDao.findAll();
//    }
//
//    @Override
//    public Ticket get(int ticketNo) throws Exception {
//        return ticketDao.findByNo(ticketNo);
//    }
//
//
//    @Transactional
//    @Override
//    public boolean update(Ticket ticket) throws Exception {
//
//        if (ticketDao.update(ticket) == 0) {
//            return false;
//        }
//    }
//
//    @Transactional
//    @Override
//    public boolean delete(int ticketNo) throws Exception {
//        return ticketDao.delete(ticketNo) > 0;
//    }
//}
