<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>    
<html>
<head>
	<title>회원가입</title>
</head>
<body>
<form class="container" method="post" action="<%=request.getContextPath() %>/signup">
	<h1>회원가입</h1>
	<div class="form-group">
	  <label>아이디:</label>
	  <input type="text" class="form-control" name="id">
	</div>
	<div class="form-group">
	  <label>비밀번호:</label>
	  <input type="password" class="form-control" name="pw">
	</div>
	<div class="form-group">
	  <label>비밀번호 확인:</label>
	  <input type="password" class="form-control" name="pw2">
	</div>
	<div class="form-group">
	  <label>성별:</label> <!--글자누를게 아닌데 label없어도 되지않나-->
	  <select class="form-control" name="gender">
		  <option value="M">남성</option>
		  <option value="F">여성</option>
	  </select>
	</div>
	<div class="form-group">
	  <label>이메일:</label>
	  <input type="email" class="form-control" name="email">
	</div>
	<div class="form-group">
	  <label>이름:</label>
	  <input type="text" class="form-control" name="name">
	</div>
	<button class="btn btn-outline-success col-12">회원 가입</button>
</form>
<script type="text/javascript">
	$(function(){
		$('form').submit(function(){
			var id = $('[name=id]').val();
			var pw = $('[name=pw]').val();
			var pw2 = $('[name=pw2]').val();
			var name = $('[name=name]').val();
			var email = $('[name=email]').val();
			if(id.trim() == ''){ //trim() 공백빼서 붙여주기
				alert('아이디를 입력하세요');
				return false;
			}
			if(pw.trim() == ''){ 
				alert('비밀번호를 입력하세요.');
				return false;
			}
			if(pw != pw2){
				alert('비밀번호가 일치하지 않습니다.');
				return false;
			}
			if(name.trim() == '' ){
				alert('이름을 입력하세요.')
				return false;
			}
			if(email.trim() == '' ){
				alert('이메일을 입력하세요.')
				return false;
			}
		})
	})
</script>

</body>
</html>
