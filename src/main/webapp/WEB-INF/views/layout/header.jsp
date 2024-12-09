<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">



<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
	<!-- property가 principal이면 현재 유저의 오브젝트에 접근 허용 -->
</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
<title>득근득근</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<!--<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>-->
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-md" style="background-color: #964444;">
		<a class="navbar-brand" href="/" style="color: white !important;">홈</a>
		<c:choose>
			<c:when test="${not empty principal}">
				<div style="position: relative; margin-right: 5px; cursor: pointer;">
					<!-- 종모양 아이콘 -->
					<i class="bi bi-bell" style="font-size: 24px; color: white;" id="notificationBell" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></i>
					<!-- 알림 개수 표시 -->
					<span
						style="position: absolute; top: -3px; right: -3px; background-color: red; color: white; font-size: 11px; padding: 1px 3px; border-radius: 50%; 
                    ${sessionScope.notification.count > 0 ? 'display: inline-block;' : 'display: none;'}">
						${sessionScope.notification.count} </span>
					<!-- 플로팅 알림 리스트 -->
					<div class="dropdown-menu" aria-labelledby="notificationBell" style="width: 780px; max-height: 400px; overflow-y: auto; right: 0;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="모두 삭제" id="btn-deleteAllNotifications"">
						<c:choose>
							<c:when test="${not empty sessionScope.notificationsList}">
								<c:forEach var="notifications" items="${sessionScope.notificationsList}">
									<a class="dropdown-item" href="${notifications.link}" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis; padding-top: 10px; padding-bottom: 10px;">
										${notifications.content} <input type="button" value="X" id="btn-deleteNotifications" data-id="${notifications.id}">
									</a>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<span class="dropdown-item text-muted" >알림이 없습니다.</span>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:when>
		</c:choose>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar" style="background-color: black; color: white !important;">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
			<c:choose>
				<c:when test="${empty principal }">
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link" href="/auth/loginForm" style="color: white !important;">로그인</a></li>
						<li class="nav-item"><a class="nav-link" href="/auth/joinForm" style="color: white !important;">회원가입</a></li>
						<li class="nav-item"><a class="nav-link" href="/item/itemListForm" style="color: white !important;">보충제 최저가</a></li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul class="navbar-nav">
						<sec:authorize access="hasRole('ADMIN')">
							<li class="nav-item"><a class="nav-link" href="/admin/addItemForm" style="color: white !important;">상품등록</a></li>
						</sec:authorize>
						<li class="nav-item"><a class="nav-link" href="/user/updateForm" style="color: white !important;">회원정보</a></li>
						<li class="nav-item"><a class="nav-link" href="/logout" style="color: white !important;">로그아웃</a></li>
						<li class="nav-item"><a class="nav-link" href="/item/itemListForm" style="color: white !important;">보충제 최저가</a></li>
						<li class="nav-item"><a class="nav-link" href="/wish/wishListForm" style="color: white !important;">위시리스트★</a></li>
						<li class="nav-item"><a class="nav-link" href="/board/boardListForm" style="color: white !important;">상품신청</a></li>
					</ul>
				</c:otherwise>
			</c:choose>
		</div>
	</nav>

	<script src="/js/notification.js"></script>