let index= {
    init: function () {
        $("#refresh").on("click", () => {// function(){}, ()=> {} this를 바인딩하기 위해서
            this.userDelete();
        });


    },
    userDelete: function () {
        $.ajax({
            type: "DELETE",
            url: "user/all",
            dataType: "json" // 요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 문자열로오는데
            // 생긴게 json이라면 => javascript 오브젝트로 변경 해줌
        }).done(function (resp) {
            alert("데이터 리셋 완료");
            // console.log(resp)
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}
index.init()