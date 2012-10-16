<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.print"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="${root}/common/css/bootstrap/bootstrap.css" rel="stylesheet">
<link href="${root}/common/css/bootstrap/bootstrap-responsive.css" rel="stylesheet">
<link href="${root}/common/css/jquery/jquery-ui-1.8.16.custom.css" rel="stylesheet">
<link href="${root}/common/css/common.css" rel="stylesheet">

<script type="text/javascript" src="${root}/common/js/jquery/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${root}/common/js/common.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        window.print();
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
        <form class="form-horizontal" id="printFrm">
	        <div class="control-group">
	            <label class="control-label" for="title"><spring:message code="blog.label.subject"/></label>
	            <div class="controls">
	                ${board.subject}</div></div>
	        <div class="control-group">
	            <label class="control-label" for="url"><spring:message code="blog.label.url"/></label>
	             <label class="control-label" for="title">${board.pageUrl}</label>
	        </div>
	        <div class="control-group">
	            <label class="control-label" for="content"><spring:message code="blog.label.contents"/></label>
	            <div class="controls">
	                ${board.content}
	            </div>
	        </div>
	        <div class="control-group">
	            <label class="control-label" for="attachFile"><spring:message code="blog.label.attachments"/></label>
	            <div class="controls">${board.fileName}</div>
	        </div>
        </form>
    </div>
    <!-- /container -->
    
     <!-- 하단 footer 영역 -->

    <!-- common html include -->
    <%@ include file="/WEB-INF/views/jsp/common/commonHtml.jsp" %>

    <!-- common file include -->
    <%@ include file="/WEB-INF/views/jsp/common/include.jsp" %>
</body>
</html>