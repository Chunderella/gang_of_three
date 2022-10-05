<%@page import="com.bitcamp.board.domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--네임스페이스 : 패키지 개념(java)-->
<!--태그세계에서는 네임스페이스 이름은 url임-->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bitcamp</title>
</head>
<body>
  <h1>환영합니다!-JSP</h1>
  <p>비트캠프 게시판 관리 시스템 프로젝트입니다.</p>
  <ul>
    <li><a href='board/list'>게시글</a></li>
    <li><a href='member/list'>회원</a></li>
    
    <c:choose>
      <c:when test="${not empty sessionScope.loginMember}"> <!--null이 아니면 getName -->
        <li><a href="auth/logout">${sessionScope.loginMember.name}(로그아웃)</a></li>
      </c:when>
      <c:otherwise>
        <li><a href='auth/form.jsp'>로그인</a></li>
      </c:otherwise>
    </c:choose>

  </ul>
</body>
</html>