<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>singUp</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="${root}/common/css/bootstrap.css" rel="stylesheet">
<link href="${root}/common/css/bootstrap-responsive.css" rel="stylesheet">
<link href="${root}/common/css/common.css" rel="stylesheet">

<script type="text/javascript" src="${root}/common/js/jQuery/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${root}/common/js/jQuery/jquery.validate.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $('#cancel').bind('click', function() {
            location.href = "../user/login.do";
        });

        // Popover 
        $('#registerHere input').hover(function() {
            $(this).popover('show')
        });
        
        // Validation
        $("#registerHere").validate({
            rules:{
                user_name:"required",
                user_email:{required:true,email: true},
                pwd:{required:true,minlength: 6},
                cpwd:{required:true,equalTo: "#pwd"},
                gender:"required"
            },

            messages:{
                user_name:"Enter your first and last name",
                user_email:{
                    required:"Enter your email address",
                    email:"Enter valid email address"
                },
                pwd:{
                    required:"Enter your password",
                    minlength:"Password must be minimum 6 characters"
                },
                cpwd:{
                    required:"Enter confirm password",
                    equalTo:"Password and Confirm Password must match"
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
        <form class="form-horizontal" id="registerHere" method='post' action='../user/userCreate.do'>
            <fieldset>
                <legend>Registration</legend>
                <div class="control-group">
                    <label class="control-label">Name</label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="user_name"
                            name="user_name" rel="popover"
                            data-content="Enter your first and last name."
                            data-original-title="Full Name">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">Email</label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="user_email"
                            name="user_email" rel="popover"
                            data-content="What’s your email address?"
                            data-original-title="Email">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="">Password</label>
                    <div class="controls">
                        <input type="password" class="input-xlarge" id="pwd" name="pwd"
                            rel="popover" data-content="6 characters or more! Be tricky"
                            data-original-title="Password">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">Confirm Password</label>
                    <div class="controls">
                        <input type="password" class="input-xlarge" id="cpwd" name="cpwd"
                            rel="popover"
                            data-content="Re-enter your password for confirmation."
                            data-original-title="Re-Password">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">Gender</label>
                    <div class="controls">
                        <select name="gender" id="gender">
                            <option value="">Gender</option>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"></label>
                    <div class="controls">
                        <button type="submit" class="btn btn-success">Create My
                            Account</button>
                        <button type="button" class="btn" id="cancel">Cancel</button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <!-- /container -->

    <!-- common file include -->
    <%@ include file="/WEB-INF/views/jsp/common/include.jsp" %>
</body>
</html>