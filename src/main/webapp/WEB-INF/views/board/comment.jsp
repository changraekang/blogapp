<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../layout/header.jsp"%>
    
<h2> 게시글 id : ${boardEntity.id } </h2>
		
		<c:forEach var="comment" items="${commentsEntity.content}">
	<!-- var은 pageScope -->
		<!-- 카드 글 시작 -->
		<div class="card">
			<div class="card-body">
				<!-- el표현식은 변수명을 적으면 자동으로 get함수를 호출해준다 -->
				<h4 class="card-title">${comment.content} 11</h4>
			</div>
		</div>
		<br />
		<!-- 카드 글 끝 -->
	</c:forEach>

	<!--  disabled -->

	<%@ include file="../layout/footer.jsp"%>