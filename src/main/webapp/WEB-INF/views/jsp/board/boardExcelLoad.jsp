<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>boardExcelLoad</title>
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
        
        // 닫기
        $('#close').bind('click', function() {
            window.close();
        });
        
        // validation check
        $('#listFrm').validate({
            submitHandler: function(form) {
            	form.submit();
            },
            rules:{
            	excelFile:{
            		required:true,
            		vaildExcelFile:true
            	}
            },
            messages:{
            	excelFile:{
                    required:"<spring:message code='blog.label.input.excefile'/>",
                    vaildExcelFile : "<spring:message code='blog.label.input.excefile.only'/>"
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
        
        // 유효한 엑셀파일인지 체크 추가
        jQuery.validator.addMethod('vaildExcelFile', function(id) {
        	var excelFile = $.trim($('#excelFile').val());
           
            if(excelFile.lastIndexOf(".xls") == -1 && excelFile.lastIndexOf(".xlsx") == -1) {
                return false;
            }
            return true;
        }, '');
        
        // 서버처리 결과
        if("${message}".length != 0) {
        	alert("${message}");
        }
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
        <fieldset>
            <legend><strong><spring:message code="blog.label.loadexcel"/></strong></legend>

            <form class="form-horizontal" id="listFrm" method="POST" action="../board/boardExcelLoadProc.do" enctype="multipart/form-data">
                <fieldset>
	                <div class="control-group">
	                    <label class="control-label" for="excelFile"><spring:message code="blog.label.attachments"/></label>
	                    <div class="controls">
	                        <input class="input-file" id="excelFile" name="excelFile" type="file">
	                    </div>
	                </div>
	                <div class="control-group">
	                    <label class="control-label"></label>
	                    <div class="controls">
	                        <button type="submit" class="btn btn-primary">검색</button>
	                        <button type="button" class="btn" id=close><spring:message code="blog.label.cancel"/></button>
	                    </div>
	                </div>
                </fieldset>
            </form>

            <!-- table 영역 start-->
            <table class="table table-striped ">
                <thead>
                    <tr>
                        
                        <th style="width: 10%"><spring:message code="blog.label.no"/></th>
                        <th style="width: 35%"><spring:message code="blog.label.subject"/></th>
                        <th style="width: 20%"><spring:message code="blog.label.create.user"/></th>
                        <th style="width: 20%"><spring:message code="blog.label.create.date"/></th>
                        <th style="width: 10%"><spring:message code="blog.label.views"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${fn:length(resultList) == 0}"> 
                    <tr>
                        <td colspan="6" align="center"><spring:message code="blog.label.list.null"/></td>
                    <tr>
                    </c:if>
                    
                    <c:forEach var="resultList" items="${resultList}" varStatus="stat">
                    <tr>
                        <td align="center">${resultList.boardId}</td>
                        <td>${resultList.subject}</td>
                        <td align="center">${resultList.createUser}</td>
                        <td align="center">${fn:substring(resultList.createDt, 0, 16)}</td>
                        <td align="center">${resultList.count}</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <!-- table 영역 end-->

        </fieldset>
    </div>
    <!-- /container -->
    
    <!-- common html include -->
    <%@ include file="/WEB-INF/views/jsp/common/commonHtml.jsp" %>

    <!-- common file include -->
    <%@ include file="/WEB-INF/views/jsp/common/include.jsp" %>
</body>
</html>