<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>    
<html>
<head>
	<title>회원 정보</title>
</head>
<body>
	<form class="container" method="post" action="<%=request.getContextPath()%>/member/mypage">
	  <h2>마이페이지</h2>
	  <div class="form-group">
		 <label>아이디</label> <!-- value값에 있는 user는 세션정보에있는 user.id라서 로그인후 마이페이지 바로넘어가면 해당 회원정보를 볼 수 있음 -->
		 <input type="text" class="form-control" name="id" value="${user.id}" readonly> 
	  </div>
  	  <div class="form-group">
		 <label>새 비밀번호</label>
		 <input type="text" class="form-control" name="pw"> <!-- 비밀번호와 비번확인에는 value넣을 필요 없음 -->
	  </div>
   	  <div class="form-group">
		 <label>새 비밀번호 확인</label>
		 <input type="text" class="form-control" name="pw2">
	  </div>
  	  <div class="form-group">
	  <label>성별</label>
	  <select class="form-control" name="gender" value="${user.gender}">
		  <option value="M" <c:if test ="${user.gender=='M'}">selected</c:if>>남성</option>
		  <option value="F" <c:if test ="${user.gender=='F'}">selected</c:if>>여성</option>
	  </select>
	</div>
   	  <div class="form-group">
		 <label>이메일</label>
		 <input type="text" class="form-control" name="email" value="${user.email}">
	  </div>
   	  <div class="form-group">
		 <label>이름</label>
		 <input type="text" class="form-control" name="name" value="${user.name}" readonly>
	  </div>
   	  <a href="<%=request.getContextPath()%>/"><button type="button" class="btn btn-outline-secondary col-5">홈으로</button></a>
  	  <button type="submit" class="btn btn-outline-success col-5">수정</button>
	</form>
<script type="text/javascript">
	$(function(){
		$('form').submit(function(){
			var pw = $('[name=pw]').val();
			var pw2 = $('[name=pw2]').val();
			if(pw == pw2){
				return true;
			}
			else{
				alert('비밀번호와 비밀번호 확인이 다릅니다.')
				return false;
			}
		})
	})
</script>
</body>
</html>
