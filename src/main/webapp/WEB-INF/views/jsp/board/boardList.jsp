<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.board.list"/></title>
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
        // 게시판 등록폼
        $('#boardCreate').bind('click', function() {
            location.href = "../board/boardCreate.do";
        });
        
        // 툴팁
        var tooltipSet = function() {
            var trObj = $("table > tbody > tr");
            
            for(var i = 0; i < trObj.length; i++) {
                $(trObj[i]).find('#titleTooltip').tooltip();
            }    
        };
        
        // typehead
        var alCities = ['Baltimore', 'Boston', 'New York', 'Tampa Bay', 'Toronto', 'Chicago', 'Cleveland', 'Detroit', 'Kansas City', 'Minnesota', 'Los Angeles', 'Oakland', 'Seattle', 'Texas'].sort();
        $('#searchword').typeahead({
            /*
            source: function(typeahead, query){
                $.ajax({
                    url: window.location.origin+"/bows/get_manufacturers.json",
                    type: "POST",
                    data: "",
                    dataType: "JSON",
                    async: false,
                    success: function(results){
                        var manufacturers = {};
                        $.map(results.data.manufacturers, function(data, item){
                            var group;
                            group = {
                                manufacturer_id: data.Manufacturer.id,
                                manufacturer: data.Manufacturer.manufacturer
                            };
                            manufacturers.push(group);
                        });
                        typeahead.process(manufacturers);
                    },
                    error: function(results){
                        console.log("results : " + JSON.stringify(results));
                    }
                });
                typeahead.process();
            },
            */
            source: alCities,
            property: 'name',
            items:5,
            onselect: function (obj) {
                console.log("obj : " + obj);
            }
        });
        
        tooltipSet(); 
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
            <legend><spring:message code="blog.label.board.list"/></legend>

            <!--  search area  start-->
            <form class="well well-small  form-search">
                <div align="right">
                    <select class="span2">
                        <option>Title+Content</option>
                        <option>Title</option>
                        <option>CreateUser</option>
                        <option>Content</option>
                    </select> <input type="text" name="searchword" id="searchword"
                        class="input-medium search-query" style="margin: 0 auto;"
                        data-provide="typeahead">
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>
            </form>

            <!-- table 영역 -->
            <table class="table table-striped ">
                <thead>
                    <tr>
                        <th style="width: 10%">No</th>
                        <th style="width: 40%">Title</th>
                        <th style="width: 20%">CreateUser</th>
                        <th style="width: 20%">CreateDate</th>
                        <th style="width: 10%">ViewCount</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td><a href="#" onClick="javascript:boardRead('1');"
                            id="titleTooltip" rel="tooltip" data-placement="bottom"
                            data-bitly-type="bitly_hover_card"
                            data-original-title="블로그 개발~!!">블로그 개발~!!</a></td>
                        <td>검은몽스</td>
                        <td>2012-08-15 17:03</td>
                        <td>25</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td><a href="#" onClick="javascript:boardRead('2');"
                            id="titleTooltip" rel="tooltip" data-placement="bottom"
                            data-bitly-type="bitly_hover_card"
                            data-original-title="Twitter boorstrap~!!">Twitter
                                boorstrap~!!</a></td>
                        <td>검은몽스</td>
                        <td>2012-08-17 17:03</td>
                        <td>25</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td><a href="#" onClick="javascript:boardRead('3');"
                            id="titleTooltip" rel="tooltip" data-placement="bottom"
                            data-bitly-type="bitly_hover_card" data-original-title="jQuery">jQuery</a></td>
                        <td>검은몽스</td>
                        <td>2012-08-17 17:03</td>
                        <td>10</td>
                    </tr>
                </tbody>
            </table>

            <div align="right">
                <button type="button" class="btn btn-primary" id="boardCreate">Create</button>
                <button type="button" class="btn btn-info disabled">ExcelSava</button>
                <button type="button" class="btn btn-info disabled">PdfSave</button>
            </div>

            <div class="pagination pagination-centered">
                <ul>
                    <li class="disabled"><a href="#" data-bitly-type="bitly_hover_card">«</a></li>
                    <li class="active"><a href="#" data-bitly-type="bitly_hover_card">1</a></li>
                    <li><a href="#" data-bitly-type="bitly_hover_card">2</a></li>
                    <li><a href="#" data-bitly-type="bitly_hover_card">3</a></li>
                    <li><a href="#" data-bitly-type="bitly_hover_card">4</a></li>
                    <li><a href="#" data-bitly-type="bitly_hover_card">»</a></li>
                </ul>
            </div>
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