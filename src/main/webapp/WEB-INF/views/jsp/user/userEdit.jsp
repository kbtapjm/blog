<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.update"/></title>
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
        // 취소
        $('#cancel').bind('click', function() {
            location.href = "../user/userInfo.do";
        });
        
        // 성별
        $('#gender').val("${user.gender}");
        
        // Validation(http://docs.jquery.com/Plugins/Validation/validate#options)   
        $("#registerHere").validate({
            submitHandler: function(form) {
                form.submit();
            },
            rules:{
                userName:"required",
                password:{
                    required:true,
                    minlength: 6
                },
                cpassword:{
                    required:true,
                    equalTo: "#password"
                },
                email:{
                    required:true,
                    email: true
                },
                birthday:{
                    required:true,
                    minlength:8, 
                    number:true
                },
                gender:"required"
            },
            messages:{
                userName:"<spring:message code='blog.label.input.name'/>",
                password:{
                    required:"<spring:message code='blog.label.input.password'/>",
                    minlength:jQuery.format("<spring:message code='blog.label.input.minimum.characters'/>")
                },
                cpassword:{
                    required:"<spring:message code='blog.label.input.confirm.password'/>",
                    equalTo:"<spring:message code='blog.label.input.password.match'/>"
                },
                email:{
                    required:"<spring:message code='blog.label.input.email.address'/>",
                    email:"<spring:message code='blog.label.input.vaild.email.address'/>"
                },
                birthday:{
                    required:"<spring:message code='blog.label.input.birthdy'/>",
                    minlength:jQuery.format("<spring:message code='blog.label.input.minimum.characters'/>"),
                    number: "<spring:message code='blog.label.input.only.numbers'/>"
                },
                gender:"<spring:message code='blog.label.select.gender'/>"
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
        <form class="form-horizontal" id="registerHere" method='post' action='../user/updateUser.do'>
            <input type="hidden" name="userId" value="${user.userId}">
            <fieldset>
                <legend><strong><spring:message code="blog.label.user.edit"/></strong></legend>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.memberid"/></label>
                    <div class="controls">
                        <input type="text" class="input-small" id="memberId"  name="memberId" value="${user.memberId}" disabled>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for=""><spring:message code="blog.label.password"/></label>
                    <div class="controls">
                        <input type="password" class="input-medium" id="password" name="password"
                            rel="popover" data-content="<spring:message code='blog.label.input.password'/>"
                            data-original-title="<spring:message code='blog.label.password'/>" value="${user.password}"  maxlength="12">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.confirm.password"/></label>
                    <div class="controls">
                        <input type="password" class="input-medium" id="cpassword" name="cpassword"
                            rel="popover" data-content="<spring:message code='blog.label.input.confirm.password'/>"
                            data-original-title="<spring:message code='blog.label.confirm.password'/>" value="" maxlength="12">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.name"/></label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="userName"
                            name="userName" rel="popover" data-content="<spring:message code='blog.label.input.name'/>"
                            data-original-title="<spring:message code='blog.label.name'/>" value="${user.userName}" maxlength="50">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.email"/></label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="email" name="email" 
                            rel="popover" data-content="<spring:message code='blog.label.input.email.address'/>"
                            data-original-title=<spring:message code='blog.label.email'/> maxlength="30" value="${user.email}">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.birthday"/></label>
                    <div class="controls">
                        <input type="text" class="input-small" id="birthday" name="birthday" 
                            rel="popover" data-content="<spring:message code='blog.label.input.birthdy'/>(19820000)"
                            data-original-title="<spring:message code='blog.label.birthday'/>" value="${user.birthday}" maxlength="8">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.gender"/></label>
                    <div class="controls">
                        <select class="span2" name="gender" id="gender">
                            <option value=""><spring:message code="blog.label.select"/></option>
                            <option value="M"><spring:message code="blog.label.gender.male"/></option>
                            <option value="F"><spring:message code="blog.label.gender.female"/></option>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"></label>
                    <div class="controls">
                        <button type="submit" class="btn btn-success"><spring:message code="blog.label.update"/></button>
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