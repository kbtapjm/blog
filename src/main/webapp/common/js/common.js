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

/**
 *로그 출력 
 * str : 출력할 문자열
 */
function log(str) {
    if(typeof console == "undefined") {
        return false;
    };
    
    console.log(str);
}

/**
 * locale 변경 
 * locale : 변경할 locale
 */
function setLocale(locale) {
    
    // 1) localeChangeInterceptor를 사용해서 파라미터 세팅
    //location.href = location.pathname + "?locale=" + locale;
    location.href =  "../user/home.do?locale=" + locale;
    return false;
    
    // 2) localeResolver를 이용해서 서버에서 변경
    $.ajax({
        url: "../user/setLocale.do",
        type: "GET",
        cache: false,
        dataType: "json",
        data: "locale=" + locale,
        success: function(data) {
            location.reload();
        },
        error: function(data) {
            log(data);
        }
    });
}