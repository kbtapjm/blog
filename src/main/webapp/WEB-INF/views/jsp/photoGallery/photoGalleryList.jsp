<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.photos"/></title>
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
    var App = {
            
    };

    $(document).ready(function() {
        $('.active').removeClass();
        $('#photoGalleryMenu').addClass('active');
          
        $('.carousel').carousel();
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
        <fieldset>
            <legend><strong><spring:message code="blog.label.photos"/></strong></legend>
            
            <!-- table 영역 start-->
            <table class="table table-striped ">
                <thead>
                    <tr>
                        <th style="width: 5%">
                            <label class="checkbox">
                               <input type="checkbox" name="checkAll" id="checkAll"  value="">
                            </label>
                        </th>
                        <th style="width: 10%"><spring:message code="blog.label.no"/></th>
                        <th style="width: 35%"><spring:message code="blog.label.subject"/></th>
                        <th style="width: 20%"><spring:message code="blog.label.create.user"/></th>
                        <th style="width: 20%"><spring:message code="blog.label.create.date"/></th>
                        <th style="width: 10%"><spring:message code="blog.label.views"/></th>
                    </tr>
                </thead>
                <tbody>
                    <!-- 
                    <tr>
                        <td colspan="6" align="center"><spring:message code="blog.label.list.null"/></td>
                    <tr>
                     -->
                    
                    <tr th:each="result, rowStat : ${resultList}">
                        <td>
                            <label class="checkbox">
                              <input type="checkbox" name="checkBoardId" id="checkBoardId" value="">
                            </label>
                        </td>
                        <td align="center" th:text="${rowStat.count}">sdfd</td>
                        <td th:text="${result.subject}">dsfs</td>
                        <td align="center"></td>
                        <td align="center"></td>
                        <td align="center"></td>
                    </tr>
                </tbody>
            </table>
            <!-- table 영역 end-->
           
        </fieldset>
     
        <!-- 하단 footer 영역 -->   
       
    </div>
    <!-- /container -->

    <!-- common html include -->
    <%@ include file="/WEB-INF/views/jsp/common/commonHtml.jsp" %>

    <!-- common file include -->
    <%@ include file="/WEB-INF/views/jsp/common/include.jsp" %>
</body>
</html>