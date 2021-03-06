<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<html>
<head>
	<title>게시판 목록</title>
	  <meta charset="utf-8">
	  <meta name="viewport" content="width=device-width, initial-scale=1">
	  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
	  <a class="navbar-brand" href="<%=request.getContextPath()%>/">Practice</a>
	  <ul class="navbar-nav">
	    <li class="nav-item">
	      <a class="nav-link" href="<%=request.getContextPath()%>/board/list">게시판</a>
	    </li>
	    <li class="nav-item">
	      <a class="nav-link" href="<%=request.getContextPath()%>/member/signin">로그인</a>
	    </li>
	    <li class="nav-item">
	      <a class="nav-link" href="<%=request.getContextPath()%>/member/signup">회원가입</a>
	    </li>
  	    <li class="nav-item">
	      <a class="nav-link" href="<%=request.getContextPath()%>/member/mypage">마이페이지</a>
	    </li>
	  </ul>
	</nav>
	
	<div class="container">
	  <h2>게시판 목록</h2>
	  <p>자유롭게 글을 써재끼세요 Yayyy</p>
	  <a href="<%=request.getContextPath()%>/board/register"><button class="btn btn-outline-primary">글쓰기</button></a>            
	  <c:if test="${list.size() != 0}">
		  <table class="table table-hover">
		    <thead>
		      <tr>
		        <th>글번호</th>
		        <th>제목</th>
		        <th>작성자</th>
		        <th>조회수</th>
		        <th>등록일</th>
		      </tr>
		    </thead>
		    <tbody>
	      		<c:forEach items="${list}" var ="list">
		    	  <tr>
			        <td>${list.num}</td>
			        <td><a href="<%=request.getContextPath()%>/board/detail?num=${list.num}">${list.title}</a></td>
			        <td>${list.writer}</td>
			        <td>${list.views}</td>
			        <td>${list.getDateTime()}</td>
		      	  </tr>
	        	</c:forEach>
		    </tbody>
		  </table>
	  </c:if>
	  <c:if test="${list.size()==0}">
	  	<h2>등록된 게시글이 없습니다.</h2>
	  </c:if>
	    <ul class="pagination justify-content-center">
	      <c:if test="${pm.prev}">
	    	<li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/board/list?page=${pm.startPage-1}">이전</a></li>
	      </c:if>
		  <c:forEach begin="${pm.startPage}" end="${pm.endPage}" var="index">
			<li class="page-item <c:if test="${pm.criteria.page == index }">active</c:if>"><a class="page-link" href="<%=request.getContextPath()%>/board/list?page=${index}">${index}</a></li>
		  </c:forEach>
		  <c:if test="${pm.next}">
		  	<li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/board/list?page=${pm.endPage+1}">다음</a></li>
		  </c:if>
	  	</ul>
	</div>
</body>
</html>
