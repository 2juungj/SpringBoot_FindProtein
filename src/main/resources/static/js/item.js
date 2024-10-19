let index = {
	init: function() {
		$("#btn-save").on("click", () => { 
			this.save();
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
			if(resp.status === 500){
				alert("상품등록 실패");
			}
			else{
				alert("상품등록 완료");
				location.href = "/";
			}
			
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
}

index.init();