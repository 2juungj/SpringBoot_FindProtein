<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<br>
<div class="container">
	<div class="col-md-12" style="text-align: center;">
		<h1 class="page-header">위시리스트</h1>
		<br>
	</div>
	<div class="row qnas" style="text-align: center;">
		<table class="table table-hover" style="width: 100%; margin: auto; border-bottom: 1px solid #D5D5D5;">
			<thead>
				<tr>
					<th>이미지</th>
					<th colspan="2" style="text-align: center;">상품명</th>
					<th>최저가</th>
					<th>상품정보</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${wishItems != null && fn:length(wishItems) > 0}">
						<c:forEach items="${wishItems}" var="wishItem" varStatus="status">
							<tr>
								<td><img alt="thumbnail" src="${wishItem.item.image}" width="80" height="80"></td>
								<td></td>
								<td><a href="http://localhost:8000/item/${wishItem.item.id}" target="_blank">${wishItem.item.name}</a></td>
								<c:choose>
									<c:when test="${not empty wishItem.item.naverShopSearchItems}">
										<td><fmt:formatNumber type="number" value="${wishItem.item.naverShopSearchItems[0].price}" />&nbsp;원</td>
									</c:when>
									<c:otherwise>
										<td>최저가 정보 없음</td>
									</c:otherwise>
								</c:choose>
								<td>${wishItem.item.info}</td>
								<td><button id="btn-delete" class="btn btn-default" data-id="${wishItem.id}">삭제</button></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="7" style="text-align: center;"><h3>위시리스트에 내역이 없습니다.</h3></td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</div>


<script src="/js/wish.js"></script>
<%@ include file="../layout/footer.jsp"%>