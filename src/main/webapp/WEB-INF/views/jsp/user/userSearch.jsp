<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.search"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="${root}/common/css/bootstrap.css" rel="stylesheet">
<link href="${root}/common/css/bootstrap-responsive.css" rel="stylesheet">
<link href="${root}/common/css/common.css" rel="stylesheet">

<script type="text/javascript" src="${root}/common/js/jQuery/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${root}/common/js/jQuery/jquery.validate.js"></script>
<script type="text/javascript" src="${root}/common/js/common.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $('#login').bind('click', function() {
            location.href = "../user/login.do";
        });

       // 아이디 찾기
        $("#idSearchFrm").validate({
            rules:{
                email:{required:true, email: true}
            },
            messages:{
                email:{
                    required:"<spring:message code='blog.label.input.email.address'/>",
                    email:"<spring:message code='blog.label.input.vaild.email.address'/>"
                }
            },
            errorClass: "help-inline",
            errorElement: "span",
            highlight:function(element, errorClass, validClass) {
                $('#emailResult').html("");
                $(element).parents('.control-group').addClass('error');
            },
            unhighlight: function(element, errorClass, validClass) {
                $('#emailResult').html("");
                $(element).parents('.control-group').removeClass('error');
                $(element).parents('.control-group').addClass('success');
            },
            submitHandler: function(form) {
                var params = "";
                params += "email=" + $('#email').val();
                
                $.ajax({
                    url: "../user/userIdSearch.do",
                    type: "GET",
                    cache: false,
                    dataType: "json",
                    data: params,
                    success: function(data) {
                        var result = data.result;
                        var user = data.user;
                        
                        var msg;
                        if(result == true) {
                            msg = "<spring:message code='blog.label.member.your.id' arguments='"+user.memberId+"'/>";
                            //msg = msg.replace("{memberId}", user.memberId);
                            $('#emailResult').html(msg);
                        } else {
                            $('#emailResult').html("<spring:message code='blog.label.member.not.machting'/>");
                        }
                        $('#emailResult').show();
                    },
                    error: function(data) {
                        $('#emailResult').show();
                        $('#emailResult').html("<spring:message code='blog.error.common.fail'/>");
                    }
                });
            }
        });
        
       // 패스워드 찾기
        $("#pwdSearchFrm").validate({
            rules:{
                memberId:"required",
                userName:"required"
            },
            messages:{
                memberId:"<spring:message code='blog.label.input.id'/>",
                userName:"<spring:message code='blog.label.input.name'/>"
            },
            errorClass: "help-inline",
            errorElement: "span",
            highlight:function(element, errorClass, validClass) {
                $(element).parents('.control-group').addClass('error');
            },
            unhighlight: function(element, errorClass, validClass) {
                $(element).parents('.control-group').removeClass('error');
                $(element).parents('.control-group').addClass('success');
            },
            submitHandler: function(form) {
                var params = "";
                params += "memberId=" + $('#memberId').val();
                params += "&userName=" + $('#userName').val();
                
                $.ajax({
                    url: "../user/userPasswordSearch.do",
                    type: "GET",
                    cache: false,
                    dataType: "json",
                    data: params,
                    success: function(data) {
                        var result = data.result;
                        var user = data.user;
                        
                        var msg;
                        if(result == true) {
                            msg = "<spring:message code='blog.label.member.your.password' arguments='"+user.password+"'/>";
                            //msg = msg.replace("{password}", user.password);
                            $('#passwrodlResult').html(msg);
                        } else {
                            $('#passwrodlResult').html("<spring:message code='blog.label.member.not.machting'/>");
                        }
                        $('#passwrodlResult').show();
                    },
                    error: function(data) {
                        $('#passwrodlResult').show();
                        $('#passwrodlResult').html("<spring:message code='blog.label.member.not.machting'/>");
                    }
                });
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
        <div class="content">
            <form class="form-horizontal" id="idSearchFrm" method="post" action="" novalidate="novalidate">
                <fieldset>
                    <legend><spring:message code="blog.label.find.id"/></legend>
                    <div class="control-group">
                        <label class="control-label"><spring:message code="blog.label.email"/></label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="email" name="email"  placeholder="Enter your email">
                            <span class="help-inline" id="emailResult"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                            <button type="submit" class="btn btn-primary"><spring:message code="blog.label.search"/></button>
                        </div>
                    </div>
                </fieldset>
            </form>
            <form class="form-horizontal" id="pwdSearchFrm" method="post" action="" novalidate="novalidate">
                <fieldset>
                    <legend><spring:message code="blog.label.find.password"/></legend>
                    <div class="control-group">
                        <label class="control-label"><spring:message code="blog.label.memberid"/></label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="memberId" name="memberId"  placeholder="Enter your id">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"><spring:message code="blog.label.name"/></label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="userName" name="userName"  placeholder="Enter your name">
                            <span class="help-inline" id="passwrodlResult"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                            <button type="submit" class="btn btn-primary"><spring:message code="blog.label.search"/></button>
                            <button type="button" class="btn" id="login"><spring:message code="blog.label.login"/></button>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
    <!-- /container -->

    <!-- common html include -->
    <%@ include file="/WEB-INF/views/jsp/common/commonHtml.jsp" %>

    <!-- common file include -->
    <%@ include file="/WEB-INF/views/jsp/common/include.jsp" %>
</body>
</html>