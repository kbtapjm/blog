<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.user.search"/></title>
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
            <form class="form-horizontal" id="login" method="post" action="../user/loginProc.do" novalidate="novalidate">
                <fieldset>
                    <legend><spring:message code="blog.label.user.search"/></legend>
                    <div class="control-group">
                        <label class="control-label"><spring:message code="blog.label.name"/></label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="userName" name="userName"  placeholder="Enter your Name">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"><spring:message code="blog.label.email"/></label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="email" name="email"  placeholder="Enter your E-mail">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                            <button type="button" class="btn btn-primary"  id="search" ><spring:message code="blog.label.user.search"/></button>
                            <button type="button" class="btn" id="cancel"><spring:message code="blog.label.cancel"/></button>
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