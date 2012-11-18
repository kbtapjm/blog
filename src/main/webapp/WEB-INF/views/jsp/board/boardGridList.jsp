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
<!-- grid css -->
<link href="${root}/common/css/jquery/grid/ui.jqgrid.css" rel="stylesheet">

<script type="text/javascript" src="${root}/common/js/jquery/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${root}/common/js/common.js"></script>

<!-- grid js -->
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.locale-en.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/jquery.jqGrid.src.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        createGrid();
        
        // 검색
        $("#btnSearch").click(function(){
            $("#gridList").setGridParam({
                url:"../board/getBoardGridList.json",
                postData: {
                    searchType : $("#searchType").val(), 
                    searchWord : $("#searchWord").val()
                }
            });
            
            $("#gridList").trigger("reloadGrid");
        });
        
        // 상세정보
        $("#read").click(function(){
            var id = $("#gridList").getGridParam('selrow');
            
            if (id) {
                var ret = $("#gridList").getRowData(id);
                log(JSON.stringify(ret));
            } else { 
                log("Row를 선택해주세요");
            }
        });
        
        // 추가
        $("#create").click(function(){
            var datarow = {id:"99",invdate:"2007-09-01",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"};
            var su=$("#gridList").addRowData(0, datarow);
            
            //$("#gridList").jqGrid('editGridRow',"new",{height:280,reloadAfterSubmit:false});
            log("create : " + su);
        });
        
        // 삭제
        $("#delete").click(function(){
            // 하나의 로우 삭제시
            /*
            var id = $("#gridList").jqGrid('getGridParam','selrow');
            if (id) {
                var ret = $("#gridList").jqGrid('getRowData', id);
                log("id="+ret.id+" invdate="+ret.invdate+"...");
                
                var su=jQuery("#gridList").jqGrid('delRowData',id);
                $("#gridList").resetSelection();
                log("삭제 유무 : " + su);
            } else { 
                log("Please select row");
            }
            */
            
            // 여러행 선택(체크박스)
            var selarrrow = $("#gridList").jqGrid('getGridParam','selarrrow');
            
            if(selarrrow.length == 0) {
                alert("Please select row");
                return false;
            }
            
            var ret;
            for(var i = 0; i < selarrrow.length; i++) {
                ret = $("#gridList").delRowData(selarrrow[i]);
                $("#gridList").resetSelection();
                
                log("삭제 유무 : " + ret);
            }
        });
        
         // 특정 로우 수정
        $("#update").click(function(){
            var su=$("#gridList").jqGrid('setRowData',2,{amount:"333.00",tax:"33.00",total:"366.00",note:"<img src='../common/images/user1.gif'/ onclick=alert();>"});
            log("2행 수정 : " + su);
        });
        
        // 초기화
        $("#clear").click(function(){
            $("#gridList").clearGridData();
        });
        
        // 컬럼 show
        $("#columnShow").click(function(){
            $("#gridList").showCol("tax");
        });
        
        // 컬럼 hide
        $("#columnHide").click(function(){
            $("#gridList").hideCol("tax");
        });
        
        // reset
        $("#reset").click(function(){
            $("#gridList").resetSelection();
        });
        
        // reload
        $("#reload").click(function(){
            $("#gridList").trigger("reloadGrid");
        });
        
        // 로우 셀렉트
        $("#rowSelect").click(function(){
            $("#gridList").resetSelection();
            $("#gridList").setSelection("5");   // 5번째 선택
        });
        
        // 보여줄 컬럼 선택
        $("#columnSelect").click(function(){
            var options = {};
            $("#gridList").columnChooser(options);
        });
        
        // 로우 가로 넓이 변경
        $("#changeRowWidth").click(function(){
            $("#gridList").setGridWidth(500, false);
        });
        
        // 현재 테이블의 총 로우 갯수
        $("#logLength").click(function(){
            var lowLen = $("#gridList").jqGrid('getDataIDs').length;
            alertModalMsg("데이터 카운트 : " + lowLen);
        });
    });
    
    function createGrid() {
        // grid 설정
        jQuery.extend(jQuery.jgrid.defaults,{ 
            emptyrecords: "No records retrieved", 
            viewrecords: true 
            } 
        ); 
        
        $("#gridList").jqGrid({
            url:'../board/getBoardGridList.json', // request url
            datatype: 'json', // local/json/array
            mtype:'POST',
            datatype : "json",
            postData : {'searchWord' : '', 'searchType' : ''},
            colNames:['Inv No', 'Date', 'Client', 'Amount', 'Tax', 'Total', 'Notes', ''],
            colModel:[
                {key : true, name:'id', index:'id', width:80, sorttype:'int'},
                {name:'invdate', index:'invdate', align:'center', width:90, sorttype:'date', formatoptions: { newformat: 'n/j/Y' }},
                {name:'name', index:'name', width:100, align:'center'},
                {name:'amount', index:'amount', width:80, align:'right', sorttype:'float'},
                {name:'tax', index:'tax', width:80, align:'right', sorttype:'float'},     
                {name:'total', index:'total', width:80, align:'right', sorttype:'float'},      
                {name:'note', index:'note', width:150, sortable:false},   
                {name: 'act', width:60, fixed:true, sortable:false, resize:false, formatter:'actions', 
                    formatoptions:{editbutton:true, deletebutton:false, keys:true,
                        delOptions:{msg:'삭제 하시겠습니까? ',
                            onclickSubmit:function(rp_ge, rowid){
                                $("#gridList").delRowData(rowid);
                                $("#gridList").resetSelection();
                                /*
	                            $("#gridList").delGridRow(rowid, {
	                                reloadAfterSubmit:true,
	                                delData:{communityId: rowid},
	                                url:"../board/getBoardGridList.json",
	                                afterComplete : function (response, postdata, formid) {
	                                    return false;
	                                } 
	                            })
	                            */
                        }}
                    }
                }
            ],
            sortname: 'id',                 // 정렬 컬럼 이름
            sortorder: 'desc',               // 정렬순서
            sortable: true,                 // 컬럼 순서 변경
            autowidth: true,                // 자동 width설정, width와 동시에 사용 안됨
            height: 300,                    // 세로높이
            rowNum: 100  ,                    // 검색 row 개수
            rowList: [100, 200, 300],          // 한번에 가져오는 row개수
            multiselect: true,              // 다중 선택 유무
            //shrinkToFit: true,              // 우측스크롤바 위의 조그만 공간 없애고 거기까지 width 설정
            //width:'100%',                 // 가로넓이, autowidth와 동시 사용 못함.
            //multikey: "ctrlKey",          // "ctrlKey/shiftKey" 키선택후 선택처리
            emptyrecords: 'Empty records',   // 데이터 없을시 표시  
            loadtext: 'Loading....',        // loading 라벨
            scrollOffset: 0,                 // 우측 스크롤 여부
            viewrecords: true,               // records의 View여부
            loadonce: false,                 // re load 여부
            gridview: true,                 // 처리속도를 향상 ==> treeGrid, subGrid, afterInsertRow(event) 같이 사용 불가
            //rownumbers: true,              // 맨앞에 줄번호 보이기 여부
            //rownumWidth: 40,               // 줄번호의 width
            //recordpos: 'right',              // 우측좌측 기준변경 records 카운트의 위치 설정
            pager:'#pager',               // 하단 페이지처리 selector
            //hiddengrid: false,            // Grid를 최초 닫힌 상태로 로드  default:false
            //toolbar : [true,"top"]        // toolbar 사용유무
            //caption: "Manipulating Array Data", // 타이틀
            scroll:1, 
            // 하나의 그리드 완료
            gridComplete: function() {     // 실행완료시!
                var tm = $("#gridList").jqGrid('getGridParam','totaltime');  //load time가져오기!
                log("gridComplete Load time: "+ tm+" ms ");
            },
            // 로드 에러
            loadError: function(xhr,st,err) {
                log("loadError Type: " + st + "; Response: " + xhr.status + " " + xhr.statusText);
            },
            // 로드 완료
            loadComplete: function(data) {
                log("loadComplete : " + data.rows.length);
                
                var lowLen = $("#gridList").jqGrid('getDataIDs').length;
         
                 // 번호체크를 선택하였을때만 선택이 되게.
                for(var i = 1; i <= lowLen; i++) { 
                    var obj = $('#jqg_gridList_' + i).data('i', i); 
                
                    obj.unbind('click').bind('click', function() { 
                        var x = $(this).data('i'); // 안되면 obj.data('i'); 
                        log("check click : " + x); 
                        //$("#gridList").jqGrid('setSelection', 'jqg' + x);
                        $("#gridList").setSelection(x);   // 5번째 선택
                    }); 
                }
                
            },
            //그리드 선택전
            beforeSelectRow: function(rowid, e) {   
                log("beforeSelectRow : " + rowid, + " >>>>>> " + e);
                return false;
            },
            // 전체 선택시
            onSelectAll: function(aRowids,status) {     
                log("onSelectAll : " + aRowids + ">>>>>>>>" + status);
            },
            // 그리드 선택시
            onSelectRow: function(ids, status) {    // row 선택시 처리. ids는 선택한 row
                log('선택 row ids : ' + ids + " Status : " + status);
            },        
            // 그리드 선택 후 action
            afterInsertRow: function(rowid, aData) { // row에 대한 설정
                switch (aData.name) {
                    case 'test':
                        $("#gridList").setCell(rowid,'total','',{color:'green'});
                        break;
                    case 'test2':
                        $("#gridList").setCell(rowid,'total','',{color:'red'});
                        break;
                    case 'test3':
                        $("#gridList").setCell(rowid,'total','',{color:'blue'});
                        break;
                }
            },
            // rows 데이터 json 파싱
            jsonReader : {   
                page: "page", 
                total: "total", 
                root: "rows", 
                records: function(obj){return obj.length;},
                repeatitems: false, 
                id: "id"
            }, // 여기 까지 
            /*
            onPaging: function () {
                $("#gridList").jqGrid('setGridParam', { datatype: 'json' });                
            }
            */
        });
        
        /*
        for(var i=0;i<=mydata.length;i++) {
            $("#gridList").jqGrid('addRowData',i+1, mydata[i]);
        };
        */
        
        // 가로 넓이 조정
        //$("#gridList").setGridWidth(500,false);
        
        // 컬럼선택
        var columnSelect = function() {
            $("#gridList").jqGrid('navGrid','#pager',{add:false,edit:false,del:false,search:false,refresh:false});
            $("#gridList").jqGrid('navButtonAdd','#pager',{
                caption: "Columns",
                title: "Reorder Columns",
                onClickButton : function (){
                    $("#gridList").jqGrid('columnChooser');
                }
            });    
        };
        columnSelect();
        
        $("#gridList").jqGrid('navGrid','#pager',{del:false,add:false,edit:false});
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
            <legend><strong>Grid Sample</strong></legend>
            
            <!--  검색영역  start-->
                <div align="right">
                    <select class="span2" name="searchType" id="searchType">
                        <option value="subject_content"><spring:message code="blog.label.subject"/>+<spring:message code="blog.label.contents"/></option>
                        <option value="subject"><spring:message code="blog.label.subject"/></option>
                        <option value="createUser"><spring:message code="blog.label.create.user"/></option>
                        <option value="content"><spring:message code="blog.label.contents"/></option>
                    </select>
                    <input type="text" name="searchWord" id="searchWord" value="${params.searchWord }" class="input-medium search-query" style="margin: 0 auto;" data-provide="typeahead">
                    <button id="btnSearch" class="btn btn-primary"><spring:message code="blog.label.search"/></button>
                </div>
            <!--  검색영역  end-->
            
            <!-- 그리드 영역 -->
            <div>
                <table id="gridList" class="scroll"></table>
                <!--  --><div id="pager"></div>
            </div>
            
            <br><br>
            <!-- 액션 버튼 영역 -->
            <div align="right">
	            <button type="button" class="btn btn-primary" id="read">선택 로우정보</button>
	            <button type="button" class="btn btn-primary" id="create">등록</button>
	            <button type="button" class="btn btn-primary" id="delete">삭제</button>
	            <button type="button" class="btn btn-primary" id="update">수정</button>
	            <button type="button" class="btn btn-primary" id="clear">클리어</button>
	            <button type="button" class="btn btn-primary" id="columnShow">Tax컬럼 보이기</button>
	            <button type="button" class="btn btn-primary" id="columnHide">Tax컬럼 숨기기</button>
	            <button type="button" class="btn btn-primary" id="reset">리셋</button>
	            <button type="button" class="btn btn-primary" id="reload">새로고침</button>
	            <button type="button" class="btn btn-primary" id="rowSelect">특정 로우선택</button>
	            <button type="button" class="btn btn-primary" id="columnSelect">컬럼 설정</button>
	            <button type="button" class="btn btn-primary" id="changeRowWidth">그리드 넓이 변경</button>
	            <button type="button" class="btn btn-primary" id="logLength">데이터 카운트</button>    
            </div>
            
            <br><br>
            
        </fieldset>
    </div>
    <!-- /container -->

    <!-- common html include -->
    <%@ include file="/WEB-INF/views/jsp/common/commonHtml.jsp" %>

    <!-- common file include -->
    <%@ include file="/WEB-INF/views/jsp/common/include.jsp" %>

<!--
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.base.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.common.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.custom.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.filter.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.formedit.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.grouping.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.import.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.inlinedit.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.jqueryui.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.loader.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.subgrid.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.tbltogrid.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/grid.treegrid.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/jqDnR.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/jqModal.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/jqModal.js"></script>
<script type="text/javascript" src="${root}/common/js/jquery/grid/jquery.fmatter.js"></script>
 -->
    
</body>
</html>