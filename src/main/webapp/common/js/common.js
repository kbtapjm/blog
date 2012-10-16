/*
 * 에러 메세지 출력(사용안함)
 * msg : 메세지 내용
 */
function getErrMsg(msg, options) {
    $('#errMsg').html(msg);
    $("#errModal").modal({
        "backdrop" : "static",
        "keyboard" : true,
        "show" : true
    });
    
    // 버튼 세팅
    if(options) {
        
    }
    
    if(afterAction) {
        if(typeof afterAction == "function") {
            $('#close').bind('click', afterAction);
        }
    }
}

/*
 * 메세지 출력 -> 분석하자~!! 특히 옵션
 * options : 설정
 */
function alertMsg(msg, options) {
    var default_value =  {
        autoOpen : false,
        width : 350,
        tile:"<spring:message code='blog.label.notification'/>",
        height: 100,
        modal: true,
        buttons: {
            "Cancel": function () {
                $(this).dialog("close");
            }
        }
    };
    
    var opts = $.extend({}, default_value, options); 
    
    $("#dialog-message").dialog({
        autoOpen: opts.autoOpen,
        width: opts.width,
        modal: opts.modal,
        buttons: opts.buttons
    });
    
    $('#messageText').html(msg);
    $("#dialog-message").show();
    $('#dialog-message').dialog('open');
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

/**
 * 팝업창 크기 세팅
 * width : 가로넓이 
 * height : 세로넓이
 */
function setPopup(width, height) {
    cw = width;
    ch = height;

    sw = screen.availWidth;
    sh = screen.availHeight;

    px = Math.ceil((sw - cw) / 2);
    py = Math.ceil((sh - ch) / 2);
}