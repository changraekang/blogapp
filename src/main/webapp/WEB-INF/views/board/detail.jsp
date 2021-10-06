<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<c:if test="${sessionScope.principal.id eq boardEntity.user.id}">
		<a href="/board/${boardEntity.id }/updateForm" class="btn btn-warning">수정</a>

		<button class="btn btn-danger"
			onclick="deleteById(${boardEntity.id })">삭제</button>
		<script>
	     	async function deleteById(id){
	    		// 1. 비동기 함수 호출 -> 비동기를 잘처리하는 방법  
	     		let response = await fetch("http://localhost:8080/board/"+id, {
	    		  method: "delete"
	    	  } ); // 약속 - 어음 (10초)
	
	    	  
	    	  // 2.코드
	    	  // json() method는 json 형태의 String을 Javascript object로 convert
	    	  let parseResponse = await response.json();
	    	  console.log(parseResponse);
	  			
	    	  if(parseResponse.code == 1) {
	    		  
	    	  alert("삭제 성공");
	    	  location.href = "/";
	    	  } else {
	    	  alert(parseResponse.msg);
	    	  location.href = "/";
	    	  }
	    	  // 3.코드
	     		
	     	}
	      
	      
	      </script>
	</c:if>




	<br /> <br />
	<div>
		글 번호 : ${boardEntity.id } </span> 작성자 : <span><i>
				${boardEntity.user.username} </i></span>
	</div>
	<br />
	<div>
		<h3>${boardEntity.title }</h3>
	</div>
	<hr />
	<div>
		<div>${boardEntity.content }</div>
	</div>
	<hr />

	<div class="card">
		<!--  댓글 쓰기 시작-->
		<form action="/board/${boardEntity.id}/comment" method="post">
			<div class="card-body">
				<textarea id="reply-content" class="form-control" name="content"
					rows="1"></textarea>
			</div>
			<div class="card-footer">
				<button type=submit id="btn-reply-save" class="btn btn-primary">등록</button>
			</div>
		</form>
		<!--  댓글 쓰기 종료-->
	</div>
	<br />
	<div class="card">
		<div class="card-header">
			<b>댓글 리스트</b>
		</div>
		<c:forEach var="comment" items="${boardEntity.comments}">
			<ul id="reply-box" class="list-group">
				<li id="reply-${comment.id }"
					class="list-group-item d-flex justify-content-between">
					<div>${comment.content}</div>
					<div class="d-flex">
						<div class="font-italic">작성자 : ${comment.user.username}
							&nbsp;</div>

						<c:if test="${sessionScope.principal.id eq boardEntity.user.id}">
							<button class="btn btn-danger"
								onclick="commentdeleteById(${comment.id })">삭제</button>
							<script>
					     	async function commentdeleteById(boardid){
					    		// 1. 비동기 함수 호출 -> 비동기를 잘처리하는 방법  
					     		let response = await fetch("http://localhost:8080/board/"+boardid + "/comment", {
					    		  method: "delete"
					    	  } ); // 약속 - 어음 (10초)
					
					    	  
					    	  // 2.코드
					    	  // json() method는 json 형태의 String을 Javascript object로 convert
					    	  let parseResponse = await response.json();
					    	  console.log(parseResponse);
					  			
					    	  if(parseResponse.code == 1) {
					    		  
					    	  alert("삭제 성공");
					    	  location.reload();
					    	  } else {
					    	  alert(parseResponse.msg);
					    	  location.reload();
					    	  }
					    	  // 3.코드
					     		
					     	}
					      
					      
					      </script>
										</c:if>
					</div>
				</li>
			</ul>
		</c:forEach>
	</div>
	<br /> <br />
</div>
<%@ include file="../layout/footer.jsp"%>