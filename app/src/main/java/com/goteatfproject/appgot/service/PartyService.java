package com.goteatfproject.appgot.service;

import com.goteatfproject.appgot.vo.*;

import java.util.List;
import java.util.Map;

public interface PartyService {

  void add(Party party) throws Exception;
  List<Party> list() throws Exception;

  // 파티게시판 - 카테고리
  List<Party> list2(String meal, String food) throws Exception;

  //페이징
  List<Map<String, Object>> selectPartyList(Criteria criteria);
  Party get(int no) throws Exception;
  boolean update(Party party) throws Exception;
  boolean delete(int no) throws Exception;
  AttachedFile getAttachedFile(int fileNo) throws Exception;
  boolean deleteAttachedFile(int fileNo) throws Exception;


  
  // 관리자 페이지 이벤트+피드+파티 게시글 조회
  List<Board> listAll() throws Exception;

  // 댓글 테스트
  public void insertComment(Comment comment) throws Exception;

  public List<Comment> getCommentList(Comment comment) throws Exception;

  boolean updateComment(Comment comment) throws Exception;

  List<Party> mainList(String name) throws Exception;
}
