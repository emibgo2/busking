let index= {
    init: function () {
        $("#refresh").on("click", () => {// function(){}, ()=> {} this를 바인딩하기 위해서
            this.Delete();
        });


    },
    Delete: function (target) {
        var t = target;
        var result =t.toLowerCase();
        $.ajax({
            type: "DELETE",
            url: `${result}/all`,
            dataType: "json" // 요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 문자열로오는데
            // 생긴게 json이라면 => javascript 오브젝트로 변경 해줌
        }).done(function (resp) {
            if (resp == 200) {
                alert("데이터 리셋 완료");
            }else if (resp==50){
                alert("삭제되는 User중 Team이 있는 User가 있습니다. 속한 Team을 먼저 삭제해 주세요")
            }
            else alert(resp);

            // console.log(resp)
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}
index.init()