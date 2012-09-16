<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.login"/></title>
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
        getLogin();
        
        $('#signup').bind('click', function() {
            location.href = "../user/signUp.do";
        });
        
        $('#idSave').bind('click', function() {
            saveMemberId();
        });
        
        // Popover 
        $('#login input').hover(function() {
            $(this).popover('show');
        });

        // Validation
        $("#login").validate({
            rules:{
                memberId:"required",
                password:{required:true,minlength: 6}
            },
            messages:{
                memberId:"<spring:message code='blog.label.input.id'/>",
                password:{
                    required:"<spring:message code='blog.label.input.password'/>",
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
            },
            submitHandler: function(form) {
                if($("#idSave").attr("checked")) {
                    saveMemberId();
                }
                
                form.submit();
            }
        });
        
        if("${result}" == "N") {
            getErrMsg("<spring:message code='blog.error.login.fail'/>");
        }
    });

    // 쿠키값 가져오기
    function getCookie(key){
        var cook = document.cookie + ";";
        var idx =  cook.indexOf(key, 0);
        var val = "";
        if(idx != -1)  {
            cook = cook.substring(idx, cook.length);
            begin = cook.indexOf("=", 0) + 1;
            end = cook.indexOf(";", begin);
            val = unescape( cook.substring(begin, end) );
        }
        return val;
    }
    
    // 쿠키값 설정
    function setCookie(name, value, expiredays){
        var today = new Date();
        today.setDate( today.getDate() + expiredays );
        document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + today.toGMTString() + ";";
    }
    
    // 쿠키에서 로그인 정보 가져오기
    function getLogin() {
        // userid 쿠키에서 id 값을 가져온다.
        var memberId = getCookie("memberId");
        // 가져온 쿠키값이 있으면
        if(memberId != "") {
            $("#memberId").val(memberId);
            $("#idSave").attr("checked","checked");
        }
    }
    
    // 쿠키에 로그인 정보 저장
    function saveMemberId(){
        var memberId = $("#memberId").val();
        
        if(memberId != "")  {
            // userid 쿠키에 id 값을 7일간 저장
            setCookie("memberId", memberId, 7);
        } else {
            // userid 쿠키 삭제
            setCookie("memberId", memberId, -1);
        }
    }
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
            <form class="form-horizontal" id="login" method="post" action="../user/loginProc.do" novalidate="novalidate">
                <fieldset>
                    <legend><spring:message code="blog.label.login"/></legend>
                    <div class="control-group">
                        <label class="control-label"><spring:message code="blog.label.memberid"/></label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="memberId"
                                name="memberId" rel="popover"
                                data-content="Enter your ID"
                                data-original-title="ID" placeholder="Enter your ID">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"><spring:message code="blog.label.password"/></label>
                        <div class="controls">
                            <input type="password" class="input-xlarge" id="password" name="password"
                                rel="popover" data-content="6 characters or more! Be tricky"
                                data-original-title="Password"
                                placeholder="6 characters or more! Be tricky">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                            <label class="checkbox"> <input type="checkbox" id="idSave" name="idSave"><spring:message code="blog.label.remember.id"/>&nbsp;&nbsp; <a href="../user/userSearch.do" >회원정보 찾기</a></label>
                            <button id="signin" class="btn btn-success" rel="tooltip" title="first tooltip"><spring:message code="blog.label.login"/></button>
                            <a href="#" id="signup" class="btn btn-primary" data-bitly-type="bitly_hover_card"><spring:message code="blog.label.signup"/></a>
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