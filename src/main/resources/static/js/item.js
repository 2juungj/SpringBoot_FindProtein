let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
		$("#btn-update").on("click", () => {
			this.update();
		});
		$("#btn-delete").on("click", () => {
			this.deleteById();
		});
	},

	save: function() {
		let data = {
			name: $("#name").val(),
			info: $("#info").val(),
			category: $("#category").val()
		};

		$.ajax({
			type: "POST",
			url: "/admin/addItem",
			data: JSON.stringify(data),
			contentType: "application/json; charset = utf-8",
			dataType: "json"
		}).done(function(resp) {
			if (resp.status === 500) {
				alert("상품등록 실패");
			}
			else {
				alert("상품등록 완료");
				location.href = "/";
			}

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	update: function() {
		let id = $("#id").val();
		
		let data = {
			name: $("#name").val(),
			info: $("#info").val(),
			category: $("#category").val()
		};

		$.ajax({
			type: "PUT",
			url: "/admin/updateItem/" + id,
			data: JSON.stringify(data),
			contentType: "application/json; charset = utf-8",
			dataType: "json"
		}).done(function(resp) {
			if (resp.status === 500) {
				alert("상품수정 실패");
			}
			else {
				alert("상품수정 완료");
				location.href = "/item/" + id;
			}

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	deleteById: function() {
		let id = $("#id").val();
		$.ajax({
			type: "DELETE",
			url: "/admin/deleteItem/" + id,
			dataType: "json"
		}).done(function(resp) {
			if (resp.status === 500) {
				alert("상품삭제 실패");
			}
			else {
				alert("상품삭제 완료");
				location.href = "/item/itemListForm";
			}

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

}

index.init();