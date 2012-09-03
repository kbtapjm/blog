/*
 * 에러 메세지 출력
 * msg : 메세지 내용
 */
function getErrMsg(msg) {
    $('#errMsg').html(msg);
    $("#errModal").modal({
        "backdrop" : "static",
        "keyboard" : true,
        "show" : true
    });
}