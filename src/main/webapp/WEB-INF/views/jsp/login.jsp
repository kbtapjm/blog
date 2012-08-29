<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Login</title>
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
        
        // Popover 
        $('#login input').hover(function() {
            $(this).popover('show');
        });

        // Validation
        $("#login").validate({
            rules:{
                user_name:"required",
                pwd:{required:true,minlength: 6}
            },

            messages:{
                user_name:"Enter your name",
                pwd:{
                    required:"Enter your password",
                    minlength:"Password must be minimum 6 characters"
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
    <!-- 상단 hearder 영역 -->
    <%@ include file="/WEB-INF/views/jsp/layout/head.jsp" %>

    <!-- contents 영역 -->
    <div class="container">
        <div class="content">
            <form class="form-horizontal" id="login" method="post" action=""
                novalidate="novalidate">
                <fieldset>
                    <legend>Login</legend>
                    <div class="control-group">
                        <label class="control-label">Username</label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="user_name"
                                name="user_name" rel="popover"
                                data-content="Enter your username."
                                data-original-title="Full Name" placeholder="Enter your name">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">Password</label>
                        <div class="controls">
                            <input type="password" class="input-xlarge" id="pwd" name="pwd"
                                rel="popover" data-content="6 characters or more! Be tricky"
                                data-original-title="Password"
                                placeholder="6 characters or more! Be tricky">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                            <label class="checkbox"> <input type="checkbox">
                                Remember me
                            </label>
                            <button id="signin" class="btn btn-success" rel="tooltip"
                                title="first tooltip">Login</button>
                            <a href="user/userCreate.html" id="signup"
                                class="btn btn-primary" data-bitly-type="bitly_hover_card">Sign-up</a>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
    <!-- /container -->

    <!-- common file include -->
    <%@ include file="/WEB-INF/views/jsp/common/include.jsp" %>
</body>
</html>