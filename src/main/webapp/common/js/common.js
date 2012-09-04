/*
 * 에러 메세지 출력
 * msg : 메세지 내용
 */
function getErrMsg(msg, afterAction) {
    $('#errMsg').html(msg);
    $("#errModal").modal({
        "backdrop" : "static",
        "keyboard" : true,
        "show" : true
    });
    
    if(afterAction) {
        if(typeof afterAction == "function") {
            $('#close').bind('click', afterAction);
        }
    }
}