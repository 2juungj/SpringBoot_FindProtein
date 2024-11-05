<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<br>
<div class="container">
	<div class="col-md-12" style="text-align: center;">
		<img alt="thumbnail" src="${nssItemList[0].image}" width="200" height="200">
		<h1 class="page-header">${item.name}</h1>
		<div style="color: #6e6e6e;">
			<!-- 최신화 날짜와 시간 분까지만 출력 -->
			<fmt:formatDate value="${item.updateTime}" pattern="yyyy년 MM월 dd일 HH:mm 최신화되었습니다." />
		</div>
		<input type="hidden" value="${item.id}" id="id">
		<sec:authorize access="hasRole('ADMIN')">
			<a href="/admin/updateItemForm/${item.id}" class="btn btn-default">수정</a>
			<button id="btn-delete" class="btn btn-default">삭제</button>
		</sec:authorize>
		<c:if test="${not empty principal}">
			<div>
				<button id="btn-addToWish" class="btn btn-default">위시리스트 추가★</button>
				<button id="btn-renew" class="btn btn-default">최저가 갱신</button>
			</div>
		</c:if>
		<br>
	</div>
	<div class="row qnas" style="text-align: center;">
		<table class="table table-hover" style="width: 70%; margin: auto; border-bottom: 1px solid #D5D5D5;">
			<thead>
				<tr>
					<th>이미지</th>
					<th colspan="2" style="text-align: center;">상품명</th>
					<th>가격</th>
					<th>판매처</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${nssItemList}" var="nssItem" varStatus="status">
					<tr>
						<td><img alt="thumbnail" src="${nssItem.image}" width="80" height="80"></td>
						<td></td>
						<td>${nssItem.name}</td>
						<td><fmt:formatNumber type="number" value="${nssItem.price}" />&nbsp;원</td>
						<td><a href="${nssItem.link}" target="_blank">${nssItem.mallName}</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<script src="/js/item.js"></script>
<script src="/js/wish.js"></script>
<%@ include file="../layout/footer.jsp"%>