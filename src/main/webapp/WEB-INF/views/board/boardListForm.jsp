<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<br>
<div class="container">
	<input type="button" value="상품 신청" onclick="location.href='/board/saveForm';">


	<c:forEach var="board" items="${boardList.content}">
		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title">${board.title}</h4>
				<input type="button" value="상세보기" onclick="location.href='/board/${board.id}';">
			</div>
		</div>
	</c:forEach>

	<ul class="pagination justify-content-center">
		<c:if test="${boardList.totalPages > 1}">
			<!-- Previous page button -->
			<c:choose>
				<c:when test="${boardList.first}">
					<li class="page-item disabled"><span class="page-link">Previous</span></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="?page=${boardList.number - 1}">Previous</a></li>
				</c:otherwise>
			</c:choose>

			<!-- Page numbers -->
			<c:forEach var="pageNumber" begin="0" end="${boardList.totalPages - 1}">
				<c:choose>
					<c:when test="${pageNumber eq boardList.number}">
						<li class="page-item active" aria-current="page"><span class="page-link">${pageNumber + 1}</span></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link" href="?page=${pageNumber}">${pageNumber + 1}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<!-- Next page button -->
			<c:choose>
				<c:when test="${boardList.last}">
					<li class="page-item disabled"><span class="page-link">Next</span></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="?page=${boardList.number + 1}">Next</a></li>
				</c:otherwise>
			</c:choose>
		</c:if>
	</ul>
</div>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>



