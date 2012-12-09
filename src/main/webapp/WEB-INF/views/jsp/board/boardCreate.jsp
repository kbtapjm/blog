<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.create"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="${root}/common/css/bootstrap/bootstrap.css" rel="stylesheet">
<link href="${root}/common/css/bootstrap/bootstrap-responsive.css" rel="stylesheet">
<link href="${root}/common/css/jquery/jquery-ui-1.8.16.custom.css" rel="stylesheet">
<link href="${root}/common/css/common.css" rel="stylesheet">

<script type="text/javascript" src="${root}/common/js/jquery/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="${root}/common/js/lib/spin.js"></script>
<script type="text/javascript" src="${root}/common/js/common.js"></script>

<!-- redactor set -->
<script type="text/javascript"src="${root}/common/plugin/redactor/redactor/redactor.min.js"></script>
<link rel="stylesheet"href="${root}/common/plugin/redactor/redactor/redactor.css" />

<style type="text/css">
    /* 로딩이미지 박스 꾸미기 */
    div#viewLoading {
        text-align: center;
        padding-top: 70px;
        background: #FFFFF0;
        filter: alpha(opacity=60);
        opacity: alpha*0.6;
    }

</style>

<script type="text/javascript">
    $(document).ready(function() {
        // 컨텐츠 HTML 에디트 설정
        $('#content').redactor();
        
        /*
        spin 설정
        var opts = {
                lines: 10, // The number of lines to draw
                length: 8, // The length of each line
                width: 5, // The line thickness
                radius: 10, // The radius of the inner circle
                color: '#000', // #rgb or #rrggbb
                speed: 1.4, // Rounds per second
                trail: 50, // Afterglow percentage
                shadow: false // Whether to render a shadow
               };
        
        var target =document.body;
        var spinner = new Spinner(opts).spin(target);
        
        // show
        target.appendChild(spinner.el);
        
        // hide
        //spinner.stop();
        */
     // 페이지가 로딩될 때 'Loading 이미지'를 숨긴다.
        $('#viewLoading').hide();

        // ajax 실행 및 완료시 'Loading 이미지'의 동작을 컨트롤하자.
        $('#viewLoading')
        .ajaxStart(function()
        {
            // 로딩이미지의 위치 및 크기조절 
            $('#viewLoading').css('position', 'absolute');
            //$('#viewLoading').css('left', $('#loadData').offset().left);
            //$('#viewLoading').css('top', $('#loadData').offset().top);
            //$('#viewLoading').css('width', $('#loadData').css('width'));
            //$('#viewLoading').css('height', $('#loadData').css('height'));

            //$(this).show();
            $(this).fadeIn(500);
        })
        .ajaxStop(function()
        {
            //$(this).hide();
            $(this).fadeOut(500);
        });
        
        
        // 취소
        $('#cancel').bind('click', function() {
            location.href = "../board/boardList.do";
        });
        
        // 단축 URL 생성
        $('#urlShortening').bind('click', function() {
            var url = $('#pageUrl').val();
            
            if($.trim(url).length == 0) return false; 
                
            // bitly API 이용한 URL 단축
            var bitlyUrl = "http://api.bitly.com/v3/shorten";
            var reqParams = "";
            reqParams += "?login=kbtapjm";
            reqParams += "&apiKey=R_cbd539f406a8f18a5aab8763a708cc70";
            reqParams += "&longUrl=" + encodeURIComponent(url);
            reqParams += "&format=json";
            
            $.ajax({
                url: bitlyUrl + reqParams,
                type: "GET",
                dataType: "json",
                data: "",
                success: function(result) {
                    var long_url, url, hash, global_hash, new_hash, msg;
                    var statusCode = result.status_code;
                    var statusTxt = result.status_txt;
                    var statusMsg = "(" + statusCode +")";
                    
                    switch(statusCode) {
                    case 200 :
                        long_url = result.data.long_url;
                        url = result.data.url;
                        hash = result.data.hash;
                        global_hash = result.data.global_hash;
                        new_hash = result.data.new_hash;
                        
                        $('#pageUrl').val(url);
                        break;
                    case 403 : 
                        if(statusTxt == "RATE_LIMIT_EXCEEDED") {
                            msg = "<spring:message code='blog.label.input.rate.limit.exceeded'/>";
                        }
                        alertModalMsg(msg + statusMsg);
                        break;
                    case 500 : 
                        if(statusTxt == "INVALID_URI" || statusTxt == "MISSING_ARG_URI") {
                            msg = "<spring:message code='blog.label.input.invaild.uri'/>";
                        } else if(statusTxt == "MISSING_ARG_LOGIN") {
                            msg = "<spring:message code='blog.label.input.missing.arg.login'/>";
                        } else if(statusTxt == "ALREADY_A_BITLY_LINK"){
                            msg = "<spring:message code='blog.label.input.already.bitly.link'/>";
                        }
                        alertModalMsg(msg + statusMsg);
                        break;
                    case 503 :
                        if(statusTxt == "UNKNOWN_ERROR") {
                            msg = "<spring:message code='blog.label.input.unknown.error'/>";
                        }
                        alertModalMsg(msg + statusMsg);
                        break;
                    default :
                        alertModalMsg("<spring:message code='blog.label.input.unknown.error'/>");
                    }
                },
                error: function(result) {
                    alertModalMsg("<spring:message code='blog.label.input.unknown.error'/>");
                }
            });
        });
        
        // validation check
        $('#inputFrm').validate({
            rules:{
                subject:"required"
            },
            messages:{
                subject:"<spring:message code='blog.label.input.subject'/>"
            },
            errorClass: "help-inline",
            errorElement: "span",
            highlight:function(element, errorClass, validClass) {
                $(element).parents('.control-group').addClass('error');
            },
            unhighlight: function(element, errorClass, validClass) {
                $(element).parents('.control-group').removeClass('error');
                $(element).parents('.control-group').addClass('success');
            }
        });
        
        // 등록 실패시
        if("${result}" == "N") {
            alertModalMsg("<spring:message code='blog.error.create.error'/>");
        }
    });
</script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>
<body>
    <!-- 상단 hearder 영역 -->
    <%@ include file="/WEB-INF/views/jsp/layout/header.jsp" %>

    <!-- contents 영역 -->
    <div class="container">
        <div id="viewLoading">
            <img src="${root}/common/images/viewLoading.gif" />
        </div>
        <form class="form-horizontal" id="inputFrm" method="POST" action="../board/boardCreateProc.do" enctype="multipart/form-data">
            <fieldset>
                <legend><strong><spring:message code="blog.label.create"/></strong></legend>
                <div class="control-group">
                    <label class="control-label" for="title"><spring:message code="blog.label.subject"/></label>
                    <div class="controls">
                        <input type="text" class="input-xxxlarge" id="subject" name="subject">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="url"><spring:message code="blog.label.url"/></label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="pageUrl" name="pageUrl">
                        <button type="button" class="btn btn-info" id="urlShortening"><spring:message code="blog.label.url.shortening"/></button>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="content"><spring:message code="blog.label.contents"/></label>
                    <div class="controls">
                        <textarea id="content" name="content"></textarea>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="attachFile"><spring:message code="blog.label.attachments"/></label>
                    <div class="controls">
                        <input class="input-file" id="attachFile" name="attachFile" type="file">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"></label>
                    <div class="controls">
                        <button type="submit" class="btn btn-primary"><spring:message code="blog.label.save"/></button>
                        <button type="button" class="btn" id="cancel"><spring:message code="blog.label.cancel"/></button>
                    </div>
                </div>
            </fieldset>
        </form>
        
        <!-- 하단 footer 영역 -->   
        <%@ include file="/WEB-INF/views/jsp/layout/footer.jsp" %>
    </div>
    <!-- /container -->
    
    <!-- common html include -->
    <%@ include file="/WEB-INF/views/jsp/common/commonHtml.jsp" %>

    <!-- common file include -->
    <%@ include file="/WEB-INF/views/jsp/common/include.jsp" %>
</body>
</html>