let indexWish = { // itemCompareForm.jsp에서 item.js의 index 함수와 이름이 겹쳐서 수정
	init: function() {
		$("#btn-addToWish").on("click", () => {
			this.addToWish();
		});
		// 모든 삭제 버튼에 클릭 이벤트 추가
        $(document).on("click", "#btn-delete", (event) => {
            this.deleteById(event.currentTarget); // 클릭한 버튼을 인자로 전달
        });
	},

	addToWish: function() {
		let data = {
			id: $("#id").val() // itemId
		};

		$.ajax({
			type: "POST",
			url: "/wish/addToWish",
			data: JSON.stringify(data),
			contentType: "application/json; charset = utf-8",
			dataType: "json"
		}).done(function(resp) {
			if (resp.status === 500) {
				alert("위시리스트 담기 실패");
			}
			else {
				alert("위시리스트 담기 완료");
			}

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	deleteById: function(button) {
        let id = $(button).data("id"); // 클릭한 버튼의 data-id 속성 가져오기 // wishItemId
        if (confirm("정말로 이 상품을 삭제하시겠습니까?")) { // 삭제 확인
            $.ajax({
                type: "DELETE",
                url: "/wish/deleteWish/" + id, 
                dataType: "json"
            }).done(function(resp) {
                if (resp.status === 500) {
                    alert("상품 삭제 실패");
                } else {
                    alert("상품 삭제 완료");
                    location.reload(); // 페이지 새로고침
                }
            }).fail(function(error) {
                alert(JSON.stringify(error));
            });
        }
    },
}

indexWish.init();