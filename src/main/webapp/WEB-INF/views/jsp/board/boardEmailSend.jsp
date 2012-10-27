<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.emailsend"/></title>
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

<script type="text/javascript">
    $(document).ready(function() {
        
        // 닫기
        $('#close').bind('click', function() {
            window.close();
        });
        
        // validation check
        $('#inputFrm').validate({
            submitHandler: function(form) {
                // 전송처리
                var to = $('#to').val();
                var message = $('#message').val();
                var boardId = $('#boardId').val();
                
                var params = "to=" + to + "&boardId=" + boardId + "&message="+encodeURIComponent(message);

                $.ajax({
                    url: "boardEmailSendProc.do",
                    asyn: true,
                    type: "POST",
                    //timeout: 8000,
                    data: params,
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    beforeSend: function(){
                        $("#progress").modal({
                            "backdrop" : "static",
                            "keyboard" : false,
                            "show" : true
                        });
                        $('#progressbar').css('width', 10 + '%');
                    },
                    dataType: "json",
                    cache : true,
                    success: function(data, status, result) {
                        if(data.result == "success") {
                            $('#progressbar').css('width', 100 + '%');
                            $('#progressLabel').html('<spring:message code="blog.label.emailsend.success"/>');
                            setTimeout(function() {
                                window.close();    
                            }, 1000);
                        } else {
                            $('#progress').modal('hide');
                            alertModalMsg('<spring:message code="blog.label.emailsend.fail"/>');
                        }
                    },
                    error: function(data) {
                        $('#progress').modal('hide');
                        alertModalMsg('<spring:message code="blog.label.emailsend.fail"/>\n(' + data.statusText + ')');
                    }
                });
                
            },
            rules:{
                to:{required:true, email: true}
            },
            messages:{
                to:{
                    required:"<spring:message code='blog.label.input.email.address'/>",
                    email:"<spring:message code='blog.label.input.vaild.email.address'/>"
                }
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
    <!-- contents 영역 -->
    <div class="container">
        <form class="form-horizontal" id="inputFrm">
        <input type="hidden" name="boardId" id="boardId" value="${boardId}">
        
            <fieldset>
                <legend><strong><spring:message code="blog.label.emailsend"/></strong></legend>
                <div class="control-group">
                    <label class="control-label" for="to"><spring:message code="blog.label.to"/></label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="to" name="to">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="message"><spring:message code="blog.label.message"/></label>
                    <div class="controls">
                        <textarea class="input-xxlarge" id="message" name="message"  rows="3"></textarea>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"></label>
                    <div class="controls">
                        <button type="submit" class="btn btn-primary"><spring:message code="blog.label.send"/></button>
                        <button type="button" class="btn" id="close"><spring:message code="blog.label.close"/></button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <!-- /container -->
    
    <!-- 전송 프로그레스 -->
    <div style="display:none;" class="modal hide fade in" id="progress" tabindex="-1" role="dialog" aria-labelledby="progressLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="progressLabel"><spring:message code="blog.label.transferof"/></h3>
    </div>
    <div class="modal-body">
        <p>
            <div class="progress">
			     <div class="bar" id="progressbar"></div>
			</div>
        </p>
    </div>
    <div class="modal-footer">
        <button id="close" class="btn" data-dismiss="modal" data-bitly-type="bitly_hover_card" aria-hidden="true">
            <spring:message code="blog.label.close"/>
        </button>
    </div>
</div>
    
    <!-- common html include -->
    <%@ include file="/WEB-INF/views/jsp/common/commonHtml.jsp" %>

    <!-- common file include -->
    <%@ include file="/WEB-INF/views/jsp/common/include.jsp" %>
</body>
</html>