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

<link href="${root}/common/css/bootstrap.css" rel="stylesheet">
<link href="${root}/common/css/bootstrap-responsive.css" rel="stylesheet">
<link href="${root}/common/css/common.css" rel="stylesheet">

<script type="text/javascript" src="${root}/common/js/jQuery/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${root}/common/js/jQuery/jquery.validate.js"></script>
<script type="text/javascript" src="${root}/common/js/common.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $('#cancel').bind('click', function() {
            location.href = "../user/login.do";
        });
        
        // 중복체크
        $('#memberCheck').bind('click', function() {
            var memberId = $('#memberId').val();
            
            if($.trim(memberId).length == 0) {
                getErrMsg('아이디를 입력하세요.');
                return false;
            }
            
            $.ajax({
                url: "../user/membercheck.do",
                type: "GET",
                cache: false,
                dataType: "json",
                data: "memberId=" + memberId,
                success: function(result) {
                    console.log(JSON.stringify(result));
                    
                    if(result == null) {
                        console.log("111");
                        
                    } else {
                        console.log("222");
                        
                    }
                },
                error: function(result) {
                    console.log(JSON.stringify(result));
                    // {"readyState":0,"responseText":"","status":0,"statusText":"error"} 
                    getErrMsg("에러가 발생하여 중복확인이 실패하였습니다.");
                }
            });
            
        });

        // Popover 
        $('#registerHere input').hover(function() {
            $(this).popover('show');
        });
        
        // Validation
        $("#registerHere").validate({
            rules:{
                memberId:"required",
                userName:"required",
                password:{required:true,minlength: 6},
                cpassword:{required:true,equalTo: "#password"},
                email:{required:true,email: true},
                birthday:{required:true,minlength: 8},
                gender:"required"
            },

            messages:{
                memberId:"Enter your ID",
                password:{
                    required:"Enter your password",
                    minlength:"Password must be minimum 6 characters"
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
                    minlength:"birthday must be minimum 8 characters"
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
        <form class="form-horizontal" id="registerHere" method='post' action='../user/createUser.do'>
            <input type="hidden" name="userId" value="">
            <fieldset>
                <legend><spring:message code="blog.label.signup"/></legend>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.memberid"/></label>
                    <div class="controls">
                        <input type="text" class="input-small" id="memberId"
                            name="memberId" rel="popover"
                            data-content="Enter ID."
                            data-original-title="ID" value="kbtapjm">
                            <button type="button" class="btn btn-info" id="memberCheck"><spring:message code="blog.label.member.check"/></button>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for=""><spring:message code="blog.label.password"/></label>
                    <div class="controls">
                        <input type="password" class="input-xlarge" id="password" name="password"
                            rel="popover" data-content="6 characters or more! Be tricky"
                            data-original-title="Password" value="123456">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.confirm.password"/></label>
                    <div class="controls">
                        <input type="password" class="input-xlarge" id="cpassword" name="cpassword"
                            rel="popover"
                            data-content="Re-enter your password for confirmation."
                            data-original-title="Re-Password" value="123456">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.name"/></label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="userName"
                            name="userName" rel="popover"
                            data-content="Enter your first and last name."
                            data-original-title="Full Name" value="검은몽스">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.email"/></label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="email"
                            name="email" rel="popover"
                            data-content="What’s your email address?"
                            data-original-title="Email" value="kbtapjm@gmai.com">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.birthday"/></label>
                    <div class="controls">
                        <input type="text" class="input-small" id="birthday"
                            name="birthday" rel="popover"
                            data-content="Enter your birthday"
                            data-original-title="Birthday" value="19820509">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"><spring:message code="blog.label.gender"/></label>
                    <div class="controls">
                        <select name="gender" id="gender">
                            <option value=""><spring:message code="blog.label.gender.select"/></option>
                            <option value="m"><spring:message code="blog.label.gender.male"/></option>
                            <option value="f"><spring:message code="blog.label.gender.female"/></option>
                        </select>
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