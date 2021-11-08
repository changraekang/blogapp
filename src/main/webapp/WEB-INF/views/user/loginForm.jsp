<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ include file="../layout/header.jsp" %>

<div class="container">


	<form action="/login" method="POST">
		
		<div class="form-group">
			<input type="text" name="username"  id="result" class="form-control" placeholder="Enter username" required="required" value= >
		</div>
		<div class="form-group">
			<input type="password" name="password" class="form-control" placeholder="Enter password" required="required" >			
		</div>
		<button type="submit" class="btn btn-primary">login</button>
	</form>
	
</div>
<script>
function printName()  {
	  const name = document.getElementById('name').value;
	  document.getElementById("result").value = name;
	}
</script>

<%@ include file="../layout/footer.jsp" %>
