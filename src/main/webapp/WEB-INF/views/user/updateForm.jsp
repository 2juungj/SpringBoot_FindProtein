<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<br>

<div class="container">
	<form action="/action_page.php">
		<input type="hidden" id="id" value="${principal.user.id}" />
		<div class="form-group">
			<label for="username">Username:</label> <input type="text" value="${principal.user.username}" class="form-control" placeholder="Enter username" id="username" readonly>
		</div>

		<c:if test="${empty principal.user.oauth }">
			<div class="form-group">
				<label for="password">Password:</label> <input type="password" class="form-control" placeholder="Enter password" id="password">
			</div>
		</c:if>

		<div class="form-group">
			<label for="email">Email:</label> <input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email" readonly>
		</div>
	</form>

	<div>
		<label for="Notification">알림 설정 (동의 상태가 되면 위시리스트 상품의 최저가 알림을 받습니다.)</label>
	</div>
	<div>
		<c:choose>
			<c:when test="${notification == 'NO'}">
				<button id="btn-notification" class="btn btn-danger">비동의</button>
			</c:when>
			<c:otherwise>
				<button id="btn-notification" class="btn btn-primary">동의</button>
			</c:otherwise>
		</c:choose>
	</div>
	<br>
	<div>
		<label for="EmailNotification">이메일 알림 설정 (동의 상태가 되면 위시리스트 상품의 최저가 알림을 이메일로 받습니다.)</label>
	</div>
	<div>
		<c:choose>
			<c:when test="${emailNotification == 'NO'}">
				<button id="btn-emailNotification" class="btn btn-danger">비동의</button>
			</c:when>
			<c:otherwise>
				<button id="btn-emailNotification" class="btn btn-primary">동의</button>
			</c:otherwise>
		</c:choose>
	</div>

	<br>
	<button id="btn-update" class="btn btn-primary">회원정보수정</button>

</div>

<script src="/js/user.js"></script>
<script src="/js/notification.js"></script>
<%@ include file="../layout/footer.jsp"%>



