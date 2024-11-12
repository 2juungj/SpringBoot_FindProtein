let indexNotification = { // user/updateForm.jsp에서 user.js의 index 함수와 이름이 겹쳐서 수정
	init: function() {
        $(document).on("click", "#btn-notification", () => {
            this.notification(); 
        });
        $(document).on("click", "#btn-emailNotification", () => {
            this.emailNotification(); 
        });
	},

	notification: function() {
		let data = {
		};

		$.ajax({
			type: "PUT",
			url: "/user/notification",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(resp) {
			if(resp.status === 500){
				alert("알림설정 변경 실패");
			}
			else{
				alert("알림설정 변경 완료");
				location.reload();
			}
			
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
	emailNotification: function() {
		let data = {
		};

		$.ajax({
			type: "PUT",
			url: "/user/emailNotification",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(resp) {
			if(resp.status === 500){
				alert("이메일 알림설정 변경 실패");
			}
			else{
				alert("이메일 알림설정 변경 완료");
				location.reload();
			}
			
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
	
}

indexNotification.init();