<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ include file="../layout/header.jsp" %>

<div class="container">
	<form onsubmit="update(event,${boardEntity.id})">
	  <div class="form-group">
	    <input type="text" id= "title" value="${boardEntity.title }" class="form-control" placeholder="Enter title" required="required" >
	  </div>
	  <div class="form-group">
	    <textarea id= "content" class="form-control" rows="5" >
	    ${boardEntity.content }
	    </textarea>
	  </div>
		<button type="submit" class="btn btn-primary">수정하기</button>
	</form>
</div>
 <script>
 	
 		async function update(event,id){
 			//console.log(event);
	 		event.preventDefault();
	 	   // 주소 : PUT board/3
	       // UPDATE board SET title = ?, content = ? WHERE id = ?

 			let boardUpdateDto = {
 				title: document.querySelector("#title").value,
 				content: document.querySelector("#content").value,
 			};
 			console.log(boardUpdateDto);
 			console.log(JSON.stringify(boardUpdateDto));
 			
 			// JSON.stringify( javascript Object) -> return: json 문자열
 			// JSON.parse (jason 문자열) -> return: javascript 함수
 			
 			
 			let response = await fetch("http://localhost:8080/board/"+id, {
 				method: "put",
 				body: JSON.stringify(boardUpdateDto),
 				headers:{
 					"Content-Type" : "application/json; charset=utf-8" 					
 				}
 			} );
 			
 		
 			let parseResponse = await response.json(); 
 			// 나중에 Spring Method에서 return 될 때 return 값이 무엇인지 확인!
 			// response.text()로 변경하여 확인
 			console.log(parseResponse);
 			  if(parseResponse.code == 1){
 	                alert("업데이트 성공");
 	                location.href = "/board/" + id ;
 	             }else{
 	  	    	  alert(parseResponse.msg);
 	             }

			 			
 		}
 
 
        $('#content').summernote({
            placeholder: '게시글을 입력해주세요',
            tabsize: 2,
            height: 500
          });
  </script>




<%@ include file="../layout/footer.jsp" %>
