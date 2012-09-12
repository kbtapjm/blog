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

<link href="${root}/common/css/bootstrap.css" rel="stylesheet">
<link href="${root}/common/css/bootstrap-responsive.css" rel="stylesheet">
<link href="${root}/common/css/common.css" rel="stylesheet">

<script type="text/javascript" src="${root}/common/js/jQuery/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${root}/common/js/jQuery/jquery.validate.js"></script>
<script type="text/javascript" src="${root}/common/js/common.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $('#cancel').bind('click', function() {
            location.href = "../user/userInfo.do";
        });
        
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
                password:{
                    required:"Enter your password",
                    minlength:jQuery.format("Password must be minimum {0} or more characters")
                },
                cpassword:{
                    required:"Enter confirm password",
                    equalTo:"Password and Confirm Password must match"
                },
                userName:"Enter your first and last name",
                email:{
                    required:"Enter your email address",
                    email:"Enter valid email address"
                },
                birthday:{
                    required:"Enter your birthday",
                    minlength:jQuery.format("birthday must be minimum {0} characters"),
                    number: "Numeric only."
                },
                gender:"Select Gender"
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
    <%@ include file="/WEB-INF/views/jsp/layout/head.jsp" %>

    <!-- contents 영역 -->
    <div class="container">
        <form class="form-horizontal" id="registerHere" method='post' action='../user/updateUser.do'>
            <input type="hidden" name="userId" value="${user.userId }">
            <fieldset>
                <legend><spring:message code="blog.label.user.edit"/></legend>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.memberid"/></label>
                    <div class="controls">
                        <input type="text" class="input-small" id="memberId"  name="memberId" value="${user.memberId }" disabled>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for=""><spring:message code="blog.label.password"/></label>
                    <div class="controls">
                        <input type="password" class="input-medium" id="password" name="password"
                            rel="popover" data-content="6 characters or more! Be tricky"
                            data-original-title="Password" value="${user.memberId }" maxlength="12">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.confirm.password"/></label>
                    <div class="controls">
                        <input type="password" class="input-medium" id="cpassword" name="cpassword"
                            rel="popover"
                            data-content="Re-enter your password for confirmation."
                            data-original-title="Re-Password" value="" maxlength="12">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.name"/></label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="userName"
                            name="userName" rel="popover"
                            data-content="Enter your first and last name."
                            data-original-title="Full Name" value="${user.userName }" maxlength="50">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.email"/></label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="email"
                            name="email" rel="popover"
                            data-content="What’s your email address?"
                            data-original-title="Email" value="${user.email }" maxlength="30">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.birthday"/></label>
                    <div class="controls">
                        <input type="text" class="input-small" id="birthday"
                            name="birthday" rel="popover"
                            data-content="Enter your birthday(19820101)"
                            data-original-title="Birthday" value="${user.birthday }" maxlength="8">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.gender"/></label>
                    <div class="controls">
                        <select class="span2" name="gender" id="gender">
                            <option value=""><spring:message code="blog.label.gender.select"/></option>
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