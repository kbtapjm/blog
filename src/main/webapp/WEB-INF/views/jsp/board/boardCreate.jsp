<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.board.create"/></title>
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
        // 목록
        var cancel = function() {
            location.href = "./boardList.html";    
        }
        
        // 저장
        var save = function() {
            console.log("저장~!!!");
        }
        
        // 단축 URL 생성
        var setShortUrl = function() {
            var url = $('#url').val();
            
            if($.trim(url).length == 0) {
                getErrMsg('url을 입력하세요.');
                return false;
            }
            
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
                            msg = "허용한도 초과로 인해 URL생성을 실패하였습니다.";
                        }
                        getErrMsg(msg + statusMsg);
                        break;
                    case 500 : 
                        if(statusTxt == "INVALID_URI") {
                            msg = "알수없는 URL로 인해 URL생성을 실패하였습니다.";
                        } else if(statusTxt == "MISSING_ARG_LOGIN") {
                            msg = "사용자 인증이 만료되어 URL생성을 실패하였습니다.";
                        } else if(statusTxt == "ALREADY_A_BITLY_LINK"){
                            msg = "이미 단축된 URL입니다.";
                        }
                        getErrMsg(msg + statusMsg);
                        break;
                    case 503 :
                        if(statusTxt == "UNKNOWN_ERROR") {
                            msg = "알수없는 에러로 인해 URL생성을 실패하였습니다.";
                        }
                        getErrMsg(msg + statusMsg);
                        break;
                    default :
                        getErrMsg("에러로 인해 URL생성을 실패하였습니다.");
                    }
                },
                error: function(result) {
                    console.log(JSON.stringify(result));
                    // {"readyState":0,"responseText":"","status":0,"statusText":"error"} 
                    getErrMsg("에러로 인해 URL생성을 실패하였습니다.");
                }
            });
        }
        
        // validation check
        $('#inputFrm').validate({
            rules:{
                title:"required",
                url:"required"
            },

            messages:{
                title:"Enter Title",
                url:"Enter URL"
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
        
        $('#content').redactor();
        $('#cancel').bind('click', cancel);
        $('#save').bind('click', save);
        $('#setShortUrl').bind('click', setShortUrl);
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
        <form class="form-horizontal" id="inputFrm">
            <fieldset>
                <legend><spring:message code="blog.label.board.create"/></legend>
                <div class="control-group">
                    <label class="control-label" for="title">Title</label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="title" name="title"
                            rel="popover" data-content="Enter your Title."
                            data-original-title="Title" placeholder="Title">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="url">URL</label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="url" name="url"
                            rel="popover" data-content="Enter your URL"
                            data-original-title="URL" placeholder="URL">
                        <button type="button" class="btn btn-info" id="setShortUrl">Short
                            URL</button>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="content">Content</label>
                    <div class="controls">
                        <textarea id="content" name="content"></textarea>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="attachFile">File</label>
                    <div class="controls">
                        <input class="input-file" id="attachFile" name="attachFile"
                            type="file">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"></label>
                    <div class="controls">
                        <button type="submit" class="btn btn-primary" id="save">Save</button>
                        <button type="button" class="btn" id="cancel">Cancel</button>
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