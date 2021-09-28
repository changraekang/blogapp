<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ include file="../layout/header.jsp" %>

<div class="container">
	<form onsubmit="update(event,${sessionScope.principal.id})">
	  <div class="form-group">
	    <input type="text" value="${sessionScope.principal.username }" class="form-control" placeholder="Enter username" readonly="readonly" maxlength="20">
	  </div>
		<div class="form-group">
			<input type="email" id= "email"  value="${sessionScope.principal.email }"  class="form-control" placeholder="Enter email" >
		</div>
		<button type="submit" class="btn btn-primary">회원정보 수정</button>
	</form>
	<!-- name지운 이유 Action 보낼 때 key value를 사용하는데
		 Action put 이 작동하지 않기 때문에 필요가 없다 
		 put 은 JavaScript를 통해 처리-->
	
</div>
 <script>
 	
 		async function update(event,id){
 			//console.log(event);
	 		event.preventDefault();
	 	   // 주소 : PUT board/3
	       // UPDATE board SET title = ?, content = ? WHERE id = ?

 			let userUpdateDto = {
 				email: document.querySelector("#email").value
 			};
 			console.log(userUpdateDto);
 			console.log(JSON.stringify(userUpdateDto));
 			
 			// JSON.stringify( javascript Object) -> return: json 문자열
 			// JSON.parse (jason 문자열) -> return: javascript 함수
 			
 			
 			let response = await fetch("http://localhost:8080/user/"+id, {
 				method: "put",
 				body: JSON.stringify(userUpdateDto),
 				headers:{
 					"Content-Type" : "application/json; charset=utf-8" 					
 				}
 			} );
 			
 		
 			let parseResponse = await response.json(); // 나중에 Spring Method에서 return 될 때 return 값이 무엇인지 확인!
 			// response.text()로 변경하여 확인
 			console.log(parseResponse);
 			  if(parseResponse.code == 1){
 	                alert("업데이트 성공");
 	                location.href = "/" ;
 	             }else{
 	  	    	  alert(parseResponse.msg);
 	             }

			 			
 		}
 
 
  
  </script>




<%@ include file="../layout/footer.jsp" %>
