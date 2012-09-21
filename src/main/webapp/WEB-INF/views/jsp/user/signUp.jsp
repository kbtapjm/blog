<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.signup"/></title>
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
        $('#cancel').bind('click', function() {
            location.href = "../user/login.do";
        });

        // Popover 
        $('#registerHere input').hover(function() {
            $(this).popover('show');
        });
        
        // Validation(http://docs.jquery.com/Plugins/Validation/validate#options)   
        $("#registerHere").validate({
            submitHandler: function(form) {
                form.submit();
            },
            rules:{
                memberId:{
                    required:true,
                    minlength: 4,
                    maxlength: 12,
                    chartersCheck:true,
                    chartersEngCheck:true,
                    memberCheck:true
                },
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
                    //date:true
                },
                gender:"required",
                agree:"required"
            },

            messages:{
                memberId:{
                    required:"<spring:message code='blog.label.input.id'/>",
                    minlength:jQuery.format("<spring:message code='blog.label.input.minimum.characters'/>"),
                    maxlength:jQuery.format("<spring:message code='blog.label.input.maxinum.characters'/>"),
                    chartersCheck: "<spring:message code='blog.label.input.id.first.character.enghish'/>",
                    chartersEngCheck: "<spring:message code='blog.label.input.id.number.english'/>",
                    memberCheck: "<spring:message code='blog.label.input.id.exists'/>"
                },
                password:{
                    required:"<spring:message code='blog.label.input.password'/>",
                    minlength:jQuery.format("<spring:message code='blog.label.input.minimum.characters'/>")
                },
                cpassword:{
                    required:"<spring:message code='blog.label.input.confirm.password'/>",
                    equalTo:"<spring:message code='blog.label.input.password.match'/>"
                },
                userName:"<spring:message code='blog.label.input.name'/>",
                email:{
                    required:"<spring:message code='blog.label.input.email.address'/>",
                    email:"<spring:message code='blog.label.input.vaild.email.address'/>"
                },
                birthday:{
                    required:"<spring:message code='blog.label.input.birthdy'/>",
                    minlength:jQuery.format("<spring:message code='blog.label.input.minimum.characters'/>"),
                    number: "<spring:message code='blog.label.input.only.numbers'/>"
                    //date:"<spring:message code='blog.label.input.vaild.birthday'/>"
                },
                gender:"<spring:message code='blog.label.select.gender'/>",
                agree:"<spring:message code='blog.label.check.privacy.aggree'/>"
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
        
        // id 첫글자 체크 g
        jQuery.validator.addMethod('chartersCheck', function(id) {
            var pattern1 = /(^[a-zA-Z])/;
            var memberId = $.trim(id);

            if(!pattern1.test(memberId)){
                return false;
            }
            return true;
        }, '');
        
        // id 유효성 체크
        jQuery.validator.addMethod('chartersEngCheck', function(id) {
            var pattern2 = /([^a-zA-Z0-9\-_])/;
            var memberId = $.trim(id);
            
            if(pattern2.test(memberId)){
                return false;
            }
            
            return true;
        }, '');
        
        // id 중복체크
        jQuery.validator.addMethod('memberCheck', function(id) {
            
            var ret = true;
            $.ajax({
                url: "../user/membercheck.do",
                type: "GET",
                cache: false,
                async: false,
                dataType: "json",
                data: "memberId=" + id,
                success: function(data) {
                    var result = data.result;
                    
                    if(result == true) {
                        ret =  false;       
                    } else {
                        ret =  true;
                    }
                },
                error: function(data) {
                    return false;
                }
            });
            return ret;
        }, '');
        
        // check for unwanted characters
        $.validator.addMethod('validChars', function (value) {
	        var result = true;
	        // unwanted characters
	        var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?";
	
	        for (var i = 0; i < value.length; i++) {
	            if (iChars.indexOf(value.charAt(i)) != -1) {
	                return false;
	            }
	        }
	        return result;
        }, '');
        
        // 회원가입 실패 처리
        if("${result}" == "N") {
            $('#signUpResult').show();
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
        <form class="form-horizontal" id="registerHere" method='post' action='../user/createUser.do'>
            <input type="hidden" name="userId" value="">
            <fieldset>
                <legend><spring:message code="blog.label.signup"/></legend>
                <div class="alert alert-error" id="signUpResult" style="display:none;">
                   <button type="button" class="close" data-dismiss="alert">×</button>
                   <strong>Error!</strong> <spring:message code="blog.error.register.failed"/>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.memberid"/></label>
                    <div class="controls">
                        <input type="text" class="input-small" id="memberId"
                            name="memberId" rel="popover"
                            data-content="Enter ID."
                            data-original-title="ID" value="" maxlength="12">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for=""><spring:message code="blog.label.password"/></label>
                    <div class="controls">
                        <input type="password" class="input-medium" id="password" name="password"
                            rel="popover" data-content="6 characters or more! Be tricky"
                            data-original-title="Password" value="" maxlength="12">
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
                            data-original-title="Full Name" value="" maxlength="50">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.email"/></label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="email"
                            name="email" rel="popover"
                            data-content="What’s your email address?"
                            data-original-title="Email" value="" maxlength="30">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.birthday"/></label>
                    <div class="controls">
                        <input type="text" class="input-small" id="birthday"
                            name="birthday" rel="popover"
                            data-content="Enter your birthday(19820101)"
                            data-original-title="Birthday" value="" maxlength="8">
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
                    <label class="control-label"><spring:message code="blog.label.personal.received"/></label>
                    <div class="controls">
                        <label class="checkbox">
						      <input type="checkbox" id="agree" name="agree">
						</label>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"></label>
                    <div class="controls">
                        <button type="submit" class="btn btn-success"><spring:message code="blog.label.signup"/></button>
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