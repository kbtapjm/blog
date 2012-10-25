/*
 * 모달 메세지 출력
 * msg : 메세지
 * options : 옵션(버튼)
 */
function alertModalMsg(msg, buttons) {
    // 메세지 세팅
    $('#errMsg').html(msg);
    $("#errModal").modal({
        "backdrop" : "static",
        "keyboard" : true,
        "show" : true
    });
    
    if(buttons) {
        $('#selectYes').bind('click', buttons.Ok);
        $('#selectNo').bind('click', buttons.Cancel);
        $('#changeButton').show();
    }  else {
        $('#defaultButton').show();
    }
}

/*
 * 모달팝업 html 생성
 * msg : 메세지
 * options : 옵션(버튼)
 */
function getModalHtml(msg, options) {
    var modalHtml = '';
    modalHtml += '<div style="display:none;" class="modal hide fade in" id="modalPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">';
    modalHtml += '<div class="modal-header">';
    modalHtml += '    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>';
    modalHtml += '    <h3 id="myModalLabel"><spring:message code="blog.label.notification"/></h3>';
    modalHtml += '</div>';
    modalHtml += '<div class="modal-body">';
    modalHtml += '    <p>';
    modalHtml += msg;
    modalHtml += '    </p>';
    modalHtml += '</div>';
    modalHtml += '<div class="modal-footer">';
    
    if(options && options.buttons.ok) {
        modalHtml += '<button id="no" class="btn" data-dismiss="modal" data-bitly-type="bitly_hover_card" aria-hidden="true">';
        modalHtml += '    <spring:message code="blog.label.no"/>';
        modalHtml += '</button>';
    }
    if(options && options.buttons.Ok) {
        modalHtml += '<button id="yes" class="btn btn-primary" data-dismiss="modal" data-bitly-type="bitly_hover_card" aria-hidden="true">';
        modalHtml += '    <spring:message code="blog.label.yes"/>';
        modalHtml += '</button>';
    }
    modalHtml += '</div>';
    modalHtml += '</div>';
    
    return modalHtml;
}

/*
 * 메세지 출력 -> 분석하자~!! 특히 옵션
 * options : 설정
 */
function alertMsg(msg, options) {
    
    /* 호출시 버턴 오프젝트
    var options = {
        buttons: {
            "Ok": function () {
                $(this).dialog("close");
                
                $('#readFrm').attr("action", "../board/boardDelete.do");
                $('#readFrm').attr("target", "_self");
                $('#readFrm').attr("method", "POST");
                $('#readFrm').submit();
            },
            "Cancel": function () {
                $(this).dialog("close");
            }
        }   
    };
    */
    
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