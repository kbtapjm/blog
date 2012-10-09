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
<script type="text/javascript" src="${root}/common/js/common.js"></script>

<!-- redactor set -->
<script type="text/javascript"src="${root}/common/plugin/redactor/redactor/redactor.min.js"></script>
<link rel="stylesheet"href="${root}/common/plugin/redactor/redactor/redactor.css" />

<script type="text/javascript">
    $(document).ready(function() {
        // 컨텐츠 HTML 에디트 설정
        $('#content').redactor();
        
        // 취소
        $('#cancel').bind('click', function() {
            location.href = "../board/boardList.do";
        });
        
        // 단축 URL 생성
        $('#setShortUrl').bind('click', function() {
            var url = $('#url').val();
            
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
                        
                        $('#url').val(url);
                        break;
                    case 403 : 
                        if(statusTxt == "RATE_LIMIT_EXCEEDED") {
                            msg = "<spring:message code='blog.label.input.rate.limit.exceeded'/>";
                        }
                        getErrMsg(msg + statusMsg);
                        break;
                    case 500 : 
                        if(statusTxt == "INVALID_URI") {
                            msg = "<spring:message code='blog.label.input.invaild.uri'/>";
                        } else if(statusTxt == "MISSING_ARG_LOGIN") {
                            msg = "<spring:message code='blog.label.input.missing.arg.login'/>";
                        } else if(statusTxt == "ALREADY_A_BITLY_LINK"){
                            msg = "<spring:message code='blog.label.input.already.bitly.link'/>";
                        }
                        alertMsg(msg + statusMsg);
                        break;
                    case 503 :
                        if(statusTxt == "UNKNOWN_ERROR") {
                            msg = "<spring:message code='blog.label.input.unknown.error'/>";
                        }
                        alertMsg(msg + statusMsg);
                        break;
                    default :
                        alertMsg("<spring:message code='blog.label.input.unknown.error'/>");
                    }
                },
                error: function(result) {
                    alertMsg("<spring:message code='blog.label.input.unknown.error'/>");
                }
            });
        });
        
        // validation check
        $('#inputFrm').validate({
            rules:{
                title:"required"
            },
            messages:{
                title:"<spring:message code='blog.label.input.subject'/>"
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
        <form class="form-horizontal" id="inputFrm" method="POST" action="../board/boardCreateProc.do" enctype="multipart/form-data">
            <fieldset>
                <legend><spring:message code="blog.label.create"/></legend>
                <div class="control-group">
                    <label class="control-label" for="title"><spring:message code="blog.label.subject"/></label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="title" name="title">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="url"><spring:message code="blog.label.url"/></label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="url" name="url">
                        <button type="button" class="btn btn-info" id="setShortUrl"><spring:message code="blog.label.url.shortening"/></button>
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
    </div>
    <!-- /container -->
    
    <!-- common html include -->
    <%@ include file="/WEB-INF/views/jsp/common/commonHtml.jsp" %>

    <!-- common file include -->
    <%@ include file="/WEB-INF/views/jsp/common/include.jsp" %>
</body>
</html>