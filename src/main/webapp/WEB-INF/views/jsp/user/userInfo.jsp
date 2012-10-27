<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.userInfo"/></title>
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
        // 회원정보 수정
        $('#update').bind('click', function() {
            location.href = "../user/userEdit.do";
        });
        
        // 회원탈퇴
        $('#delete').bind('click', function() {
            var buttons = {
                "Ok": function () {
                    location.href = "../user/deleteUser.do";
                },
                "Cancel": function () {
                    
                }    
            };
            
            alertModalMsg("<spring:message code='blog.label.members.reave'/>", buttons);
        });
        
        $('.active').removeClass();
        $('#userInfo').addClass('active');
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
        <form class="form-horizontal" method='get' action='../user/userEdit.do'>
            <fieldset>
                <legend><strong><spring:message code="blog.label.userInfo"/></strong></legend>
                <div class="control-group">
                    <label class="control-label" for="title"><spring:message code="blog.label.memberid"/></label>
                    <label class="control-label" for="title">${user.memberId}</label>
                </div>
                <div class="control-group">
                    <label class="control-label" for="username"><spring:message code="blog.label.name"/></label>
                    <label class="control-label" for="username">${user.userName}</label>
                </div>
                <div class="control-group">
                    <label class="control-label" for="email"><spring:message code="blog.label.email"/></label>
                    <label class="control-label" for="email">${user.email}</label>
                </div>
                <div class="control-group">
                    <label class="control-label" for="birthday"><spring:message code="blog.label.birthday"/></label>
                    <label class="control-label" for="birthday">${fn:substring(user.birthday , 0, 4)}/${fn:substring(user.birthday , 4, 6)}/${fn:substring(user.birthday , 6, 8)}</label>
                </div>
                <div class="control-group">
                    <label class="control-label" for="gender"><spring:message code="blog.label.gender"/></label>
                    <label class="control-label" for="gender">
                        <c:choose>
                            <c:when test="${user.gender eq 'M' }">
                                <spring:message code="blog.label.gender.male"/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="blog.label.gender.female"/>
                            </c:otherwise>
                        </c:choose>
                    </label>
                </div>
                <div class="control-group">
                    <label class="control-label"></label>
                    <button type="button" class="btn btn-success" id="update" ><spring:message code="blog.label.update"/></button>
                    <button type="button" class="btn btn-danger" id="delete" ><spring:message code="blog.label.delete.user"/></button>
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