<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.list"/></title>
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
	$(function() { 
	    $('#searchType').val('${params.searchType}');
	    
	    // typehead
	    var typehead = function() {
	        $.ajax({
                url: "../board/getBoardTypeheadSubject.do",
                type: "GET",
                cache: false,
                async: false,
                dataType: "json",
                data: "",
                success: function(data) {
                    $('#searchWord').typeahead({
                        source: data.list,
                        property: 'name',
                        items:10,
                        onselect: function (obj) {
                            log("obj : " + obj);
                        }
                    });
                    
                },
                error: function(data) {
                    log(JSON.stringify(data));
                }
            });
	    };
	    
	    typehead();
	});
	
    $(document).ready(function() {
        // 게시글 등록폼
        $('#boardCreate').bind('click', function() {
            location.href = "../board/boardCreate.do";
        });
        
        // 전체선택, 전체해제
        $('#checkAll').bind('click', function() {
            if ( $("[name=checkAll]").is(":checked") ) {
                 $("[name=checkBoardId]").attr("checked", true);   
            } else {
                 $("[name=checkBoardId]").attr("checked",false);     
            }
        });
        
        // 선택삭제
        $('#boardMultiDelete').bind('click', function() {
            // 체크 유무 확인
            if($("[name=checkBoardId]:checked").length == 0) {
                alertModalMsg("<spring:message code='blog.label.select.board'/>");
                return false;
            }
            
            var buttons = {
                "Ok": function () {
                    $('#searchFrm').attr("action", "../board/boardMultiDelete.do");
                    $('#searchFrm').attr("target", "_self");
                    $('#searchFrm').attr("method", "POST");
                    $('#searchFrm').submit();    
                },
                "Cancel": function () {
                     
                }    
            };
             
            alertModalMsg("<spring:message code='blog.label.delete.confirm'/>", buttons);
        });
        
        // 엑셀저장
        $('#boardExcelSave').bind('click', function() {
            $('#searchFrm').attr("action", "../board/boardExcelSave.do");
            $('#searchFrm').attr("target", "_self");
            $('#searchFrm').attr("method", "POST");
            $('#searchFrm').submit();    
        });
        
        // pdf저장
        $('#boardPdfSave').bind('click', function() {
            
        });
        
        // 툴팁
        var tooltipSet = function() {
            var trObj = $("table > tbody > tr");
            
            for(var i = 0; i < trObj.length; i++) {
                $(trObj[i]).find('#titleTooltip').tooltip();
            }    
        };
        
        tooltipSet(); 
    });
    
    // 게시글 상세조회
    function boardRead(boardId) {
        $('#boardId').val(boardId);
        $('#searchFrm').attr("action", "../board/boardRead.do");
        $('#searchFrm').attr("target", "_self");
        $('#searchFrm').submit();
    }
    
    // 페이지 이동
    function goPage(page) {
        $('#pageNo').val(page);
        $('#searchFrm').submit();
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
        <fieldset>
            <legend><spring:message code="blog.label.list"/></legend>

            <!--  검색영역  start-->
            <form class="well well-small  form-search" id="searchFrm" method="POST" action="../board/boardSearchList.do">
                <input type="hidden" name="pageNo" id="pageNo" value="${params.pageNo }">
                <input type="hidden" name="pageSize" id="pageSize" value="${params.pageSize }">
                <input type="hidden" name="boardId" id="boardId" value="">
                
                <div align="right">
                    <select class="span2" name="searchType" id="searchType">
                        <option value="subject_content"><spring:message code="blog.label.subject"/>+<spring:message code="blog.label.contents"/></option>
                        <option value="subject"><spring:message code="blog.label.subject"/></option>
                        <option value="createUser"><spring:message code="blog.label.create.user"/></option>
                        <option value="content"><spring:message code="blog.label.contents"/></option>
                    </select>
                    <input type="text" name="searchWord" id="searchWord" value="${params.searchWord }" class="input-medium search-query" style="margin: 0 auto;" data-provide="typeahead">
                    <button type="submit" class="btn btn-primary"><spring:message code="blog.label.search"/></button>
                </div>
             </form>
            <!--  검색영역  end-->

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
                    <c:if test="${fn:length(resultList) == 0}"> 
		            <tr>
		                <td colspan="6" align="center"><spring:message code="blog.label.list.null"/></td>
		            <tr>
		            </c:if>
		            
		            <c:forEach var="resultList" items="${resultList}" varStatus="stat">
                    <tr>
                        <td>
                            <label class="checkbox">
							  <input type="checkbox" name="checkBoardId" id="checkBoardId" value="${resultList.boardId}">
							</label>
                        </td>
                        <td align="center">${rowNo - (stat.count - 1)}</td>
                        <td><a href="#" onClick="javascript:boardRead('${resultList.boardId}');" id="titleTooltip" rel="tooltip" data-placement="bottom" data-bitly-type="bitly_hover_card"
                            data-original-title="${resultList.subject}">
                            
                            <!-- 새로운 글 일 경우 new 표시 -->
                            <c:choose>
		                        <c:when test="${fn:substring(resultList.createDt, 0, 10) eq today}">
		                             
		                        </c:when>
		                        <c:otherwise>
		                                   
		                        </c:otherwise>
		                    </c:choose>
                            
                            ${resultList.subject}
                            </a>
                        </td>
                        <td align="center">${resultList.user.userName}</td>
                        <td align="center">${fn:substring(resultList.createDt, 0, 16)}</td>
                        <td align="center">${resultList.count}</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <!-- table 영역 end-->

            <div align="right">
                <button type="button" class="btn btn-primary" id="boardCreate"><spring:message code="blog.label.create"/></button>
                <!--
                <button type="button" class="btn btn-primary" id="boardMultiDelete"><spring:message code="blog.label.delete"/></button>
                <button type="button" class="btn btn-info" id="boardExcelSave"><spring:message code="blog.label.saveexcel"/></button>
                <button type="button" class="btn btn-info" id="boardPdfSave"><spring:message code="blog.label.savepdf"/></button>
                 -->
            </div>

            <!--  페이징 start-->
            ${pageControl}
            <!--  페이징 end-->
        </fieldset>
    </div>
    <!-- /container -->
    
     <!-- 하단 footer 영역 -->

    <!-- common html include -->
    <%@ include file="/WEB-INF/views/jsp/common/commonHtml.jsp" %>

    <!-- common file include -->
    <%@ include file="/WEB-INF/views/jsp/common/include.jsp" %>
</body>
</html>