<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<br>
<div class="container">
	<h1 style="text-align: center;">상품 등록</h1>
	<div style="display: flex; flex-direction: row; justify-content: space-between; align-items: center;">
		<div style="flex: 1;">
			<div>
				<!-- 카테고리 선택 추가 -->
				<label for="category">카테고리 선택</label>
				<select class="form-control" id="category" required>
					<option value="PROSUPPS">PROSUPPS</option>
				</select>
			</div>
			<br>
			<div>
				<label>상품명</label> <input type="text" class="form-control" id="name" required>
				<!-- required를 추가하면 필수 입력 값이 된다. -->
			</div>
			<div>
				<label>상품 설명</label> <input type="text" class="form-control" id="info">
			</div>
			<br>
			<div>
				<input type="button" value="상품 등록" id="btn-save">
			</div>
		</div>
	</div>
</div>

<script src="/js/item.js"></script>
<%@ include file="../layout/footer.jsp"%>