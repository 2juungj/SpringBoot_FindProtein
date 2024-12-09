<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>
<%@ include file="layout/header2.jsp"%>

<div class="container">
	<br>
	<h3 class="text-center">New Items</h3>

	<!-- Section-->
	<section class="py-5">
		<div class="container px-4 px-lg-5 mt-5">
			<div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
				<c:forEach var="item" items="${itemList.content}">
					<div class="col mb-5">
						<div class="card h-100">
							<!-- Product image-->
							<div class="image-container" style="width: 100%; padding-top: 100%; position: relative;">
								<img class="card-img-top" src="${item.image}" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;" />
							</div>
							<!-- Product details-->
							<div class="card-body p-4">
								<div class="text-center">
									<!-- Product name-->
									<h5 class="fw-bolder">${item.name}</h5>
									<!-- Product price-->
									${item.naverShopSearchItems[0].price}원
								</div>
							</div>
							<!-- Product actions-->
							<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
								<div class="text-center">
									<a class="btn btn-outline-dark mt-auto" href="/item/${item.id}">상세보기</a>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>
</div>



<%@ include file="layout/footer.jsp"%>