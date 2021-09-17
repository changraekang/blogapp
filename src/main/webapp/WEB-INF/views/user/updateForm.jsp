<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ include file="../layout/header.jsp" %>

<div class="container">
	<form >
	  <div class="form-group">
	    <input type="text" value="${sessionScope.principal.username }" class="form-control" placeholder="Enter username" readonly="readonly" maxlength="20">
	  </div>
	  <div class="form-group">
	    <input type="password" value="${sessionScope.principal.password }"  class="form-control" placeholder="Enter password" required="required" maxlength="20">
	  </div>
		<div class="form-group">
			<input type="email" value="${sessionScope.principal.email }"  class="form-control" placeholder="Enter email" >
		</div>
		<button type="submit" class="btn btn-primary">회원정보 수정</button>
	</form>
	<!-- name지운 이유 Action 보낼 때 key value를 사용하는데
		 Action put 이 작동하지 않기 때문에 필요가 없다 
		 put 은 JavaScript를 통해 처리-->
	
</div>





<%@ include file="../layout/footer.jsp" %>
