<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>    
<html>
<head>
	<title>게시글</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
	  <h2>게시판 목록</h2>
	  <c:if test="${list.size() != 0}">
		  <table class="table table-striped">
		    <thead>
		      <tr>
		        <th>번호</th>
		        <th>제목</th>
		        <th>작성자</th>
	   	        <th>조회수</th>
	   	        <th>등록일</th>	        
		      </tr>
		    </thead>
		    <tbody>
		    	<!-- items에는 서버에서 보낸 리스트를 연결하고, var에는 리스트에서 하나 꺼낸 객체의 이름을 지정 -->	    	
			    <c:forEach items="${list}" var="board">
			      <tr><!-- vo를 이용하여 멤버변수명을 쓰면 해당 멤버 변수를 부르는 것이 아니라 해당 멤버 변수의 getter를 부르는 것 -->
			        <td>${board.num}</td>
			        <td><a href="<%=request.getContextPath()%>/board/detail?num=${board.num}">${board.title}</a></td>
			        <td>${board.writer}</td>
		   	        <td>${board.views}</td>
		   	        <td>${board.registeredDate}</td>
			      </tr>
		     	</c:forEach>
		    </tbody>
		  </table>
	  </c:if>
	  <c:if test="${list.size()==0 }">
	  	<h1>게시글이 없습니다.</h1>
	  </c:if>
		  <a href="<%=request.getContextPath()%>/board/register"><button class="btn btn-outline-success">글쓰기</button></a>
	  	  <a href="<%=request.getContextPath()%>/"><button class="btn btn-outline-success">메인으로</button></a>  
	</div>
</body>
</html>
