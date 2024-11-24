let indexNotification = { // user/updateForm.jsp에서 user.js의 index 함수와 이름이 겹쳐서 수정
	init: function() {
		$(document).on("click", "#btn-notification", () => {
			this.notification();
		});
		$(document).on("click", "#btn-emailNotification", () => {
			this.emailNotification();
		});
		$(document).on("click", "#btn-deleteNotifications", (event) => {
			this.deleteById(event.currentTarget); // 클릭한 버튼을 인자로 전달
		});
		$("#btn-deleteAllNotifications").on("click", () => {
			this.deleteAll();
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
			if (resp.status === 500) {
				alert("알림설정 변경 실패");
			}
			else {
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
			if (resp.status === 500) {
				alert("이메일 알림설정 변경 실패");
			}
			else {
				alert("이메일 알림설정 변경 완료");
				location.reload();
			}

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	deleteById: function(button) {
		let id = $(button).data("id"); // 클릭한 버튼의 data-id 속성 가져오기 // notificationId
		$.ajax({
			type: "DELETE",
			url: "/notification/deleteNotifications/" + id,
			dataType: "json"
		}).done(function(resp) {
			if (resp.status === 500) {
				alert("알림 삭제 실패");
			} else {
				alert("알림 삭제 완료");
				location.reload(); // 페이지 새로고침
			}
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	deleteAll: function() {
		let data = {
		};

		$.ajax({
			type: "DELETE",
			url: "/notification/deleteAllNotifications",
			dataType: "json"
		}).done(function(resp) {
			if (resp.status === 500) {
				alert("모든 알림 삭제 실패");
			} else {
				alert("모든 알림 삭제 완료");
				location.reload(); // 페이지 새로고침
			}
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},


}

indexNotification.init();