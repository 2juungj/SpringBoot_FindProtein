<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<br>
<div class="container">
	<h1 style="text-align: center;">상품 등록</h1>
	<div style="display: flex; flex-direction: row; justify-content: space-between; align-items: center;">
		<div style="flex: 1;">
			<div>
				<!-- 카테고리 선택 추가 -->
				<label for="category">카테고리 선택</label> <select class="form-control" id="category" required>
					<!-- 이전 카테고리 선택값과 일치하면 자동선택 -->
					<option value="PROSUPPS" ${item.category == 'PROSUPPS' ? 'selected' : ''}>PROSUPPS</option>
				</select>
			</div>
			<br> <input type="hidden" value="${item.id}" id="id">
			<div>
				<label>상품명</label> <input value="${item.name}" type="text" class="form-control" id="name" required>
			</div>
			<div>
				<label>상품 설명</label> <input value="${item.info}" type="text" class="form-control" id="info">
			</div>
			<br>
			<div>
				<input type="button" value="상품 수정" id="btn-update">
			</div>
		</div>
	</div>
</div>

<script src="/js/item.js"></script>
<%@ include file="../layout/footer.jsp"%>