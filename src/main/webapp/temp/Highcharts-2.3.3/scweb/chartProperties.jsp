<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<%@ page pageEncoding="UTF-8"%>
<head>
<title>Chart Sample </title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/chartProperties.css"  type="text/css">

<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/utobj.js"></script>
<script type="text/javascript" src="js/charts.js"></script>
<script type="text/javascript" src="js/common.js"></script>

<script type="text/javascript">

var SCWebConsole = {
        pageStart : function () {
            SCWebConsole.tab.init();
            SCWebConsole.settings.init();
            SCWebConsole.event.init();
            SCWebConsole.data.init();
        },
        data : {
            init : function() {
                //this.setProperties();
                
               log("chartDatas : " + JSON.stringify(chartDatas));
            },
            // 속성정보 저장
            saveProperties : function(type) {
        
                
                
                // 속성정보 저장
                
                // 차트 다시 그리기
            },
            // 차트 속성 정보 세팅
            setProperties : function() {
                
                // chart
                if(defVal.chart.backgoundGradient) $('input[name=chart_backgoundGradient]').attr("checked", true); 
                $('#chart_backgroundColor1').val(defVal.chart.backgroundColor1);
                $('#chart_backgroundColor2').val(defVal.chart.backgroundColor2);
                
                if(defVal.chart.plotBackgoundGradient) $('input[name=chart_backgoundGradient]').attr("checked", true);
                $('#chart_plotBackgroundColor1').val(defVal.chart.plotBackgroundColor1);
                $('#chart_plotBackgroundColor2').val(defVal.chart.plotBackgroundColor2);
                
                // title
                $('#title_fontColor').val(defVal.title.style.color);
                $('#title_fontFamily').val(defVal.title.style.fontFamily);
                $('#title_fontSize').val(defVal.title.style.fontSize);
                if(defVal.title.style.fontWeight == "bold") $('input[name=title_fontWeight]').attr("checked", true);
                $("#title_align").val(defVal.title.align);
                
                // legend
                $('#legend_backgroundColor').val(defVal.legend.backgroundColor);
                $('#legend_align').val(defVal.legend.align);
                $('#legend_verticalAlign').val(defVal.legend.verticalAlign);
                if(defVal.legend.isBorderRadius) $('input[name=legend_isBorderRadius]').attr("checked", true);
                
                $('#legend_borderColor').val(defVal.legend.borderColor);
                $('#legend_borderRadius').val(defVal.legend.borderRadius);
                $('#legend_borderWidth').val(defVal.legend.borderWidth);
                
                // axies
                $('#axies_foregroundColor').val(defVal.xAxis.lineColor);
                if(defVal.yAxis.autoranging) $('input[name=axies_autoranging]').attr("checked", true);
                $('#axies_min').val(defVal.yAxis.min);
                $('#axies_max').val(defVal.yAxis.max);
                
                if(defVal.xAxis.isGridX) $('input[name=axies_isGridX]').attr("checked", true);
                if(defVal.yAxis.isGridY) $('input[name=axies_isGridY]').attr("checked", true);
                
                $('#axies_gridLineColor').val(defVal.xAxis.lineColor);
                $('#axies_gridLineDashStyle').val(defVal.xAxis.gridLineDashStyle);
                $('#axies_gridLineWidth').val(defVal.xAxis.gridLineWidth);
                
                $('#axies_fontFamily').val(defVal.xAxis.labels.style.fontFamily);
                $('#axies_fontSize').val(defVal.xAxis.labels.style.fontSize);
                if(defVal.xAxis.labels.style.fontWeight == "bold") $('input[name=axies_fontWeight]').attr("checked", true);
                
                // series
                 $('#series_lineWidth').val(def.seriesDefaultOptions.lineWidth);
                 $('#series_dashStyle').val(def.seriesDefaultOptions.dashStyle);
                 $('#series_color').val(def.seriesDefaultOptions.color);
                 
                 $('#series_markerRadius').val(def.seriesDefaultOptions.dashStyle);
                 $('#series_markerSymbol').val(def.seriesDefaultOptions.marker.sysmbol);
                 
                 $('#series_markerFillColor').val();
                 $('#series_markerSymbol').val();
                 $('#series_markerLineWidth').val();
                 $('#series_markerLineColor').val();
            }
        },
        tab : {
            init : function() {
                $('div.sc-tab-content-wrap').find('div.sc-group-container:first').show().siblings('div.sc-group-container').hide();
                $('div.sc-tab-title-group').find('div.sc-tab-title:first').addClass('sc-tab-title-selected').siblings('div.sc-tab-title').removeClass('sc-tab-title-selected');
            }
        },
        settings : {
            init : function() {
                numberOptions.onlyNumber();
            }
        },
        event : {
            init : function(){
                // tab                  
                $('div.sc-tab-title').click(function() {
                    if (!$(this).hasClass('sc-tab-title-selected')) {
                        $(this).addClass('sc-tab-title-selected').siblings('div.sc-tab-title').removeClass('sc-tab-title-selected');
                        
                        $($(this).attr('tabname')).show().siblings('div.sc-group-container').hide();
                    }
                    
                    $(this).blur();
                    return false;
                });
                
                var gridId = null;
                // 선택된 시리즈가 없을 경우 시리즈 탭 hide
                if(gridId == null) {
                    $('#seriesTab').hide();
                }
                
                // 등록
                $('#btnOK').unbind().bind('click', function() {
                    SCWebConsole.data.saveProperties('0');
                });
                
                // 적용
                $('#btnApply').unbind().bind('click', function() {
                    SCWebConsole.data.saveProperties('1');
                });
                
                // 취소
                $('#btnCancel').unbind().bind('click', function() {
                    window.close();
                });
                
            }
        },
};

$(document).ready(function(){
    try{
        SCWebConsole.pageStart();
    }catch(e){}
});
	
</script>
</head>
<body>
<div class="wrap">
	<form>
        <div class="sc-sub-container">
            <div class="sc-tab-title-group">
                <div class="sc-tab-title" tabname="#chart">
                        <label class="sc-group-title">차트</label>
                    </div>
                    <div class="sc-tab-title" tabname="#title">
                        <label class="sc-group-title">제목</label>
                    </div>
                    <div class="sc-tab-title" tabname="#legend">
                        <label class="sc-group-title">범례</label>
                    </div>
                    <div class="sc-tab-title" tabname="#axies">
                        <label class="sc-group-title">축</label>
                    </div>
                    <div class="sc-tab-title" tabname="#series" id="seriesTab">
                        <label class="sc-group-title">계열</label>
                    </div>
            </div>
            <div class="sc-tab-content-wrap">
            <!-- 차트  -->
            <div id="chart" class="sc-group-container">
                <table class="sc-field-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="50%">
                            <fieldset >
                                <legend>윈도우 영역</legend>
                                <input type="checkbox" name="chart_backgoundGradient" id="chart_backgoundGradient"><label for="chart_backgoundGradient">그라데이션 사용</label><br><br>
                                <label class="sc-field-label">배경색</label>
                                <input type="text" class="sc-field-input" name="chart_backgroundColor1" id="chart_backgroundColor1">
                                <label class="sc-field-label">배경색 2</label>
                                <input type="text" class="sc-field-input" name="chart_backgroundColor2" id="chart_backgroundColor2">
                                <br><br><br><br>
                            </fieldset>
                        </td>
                        <td>
                            <fieldset>
                                <legend>데이터 영역</legend>
                                <input type="checkbox" name="chart_plotBackgoundGradient" id="chart_plotBackgoundGradient"><label for="chart_plotBackgoundGradient">그라데이션 사용</label><br><br>
                                <label class="sc-field-label">배경색</label>
                                <input type="text"  class="sc-field-input" name="chart_plotBackgroundColor1" id="chart_plotBackgroundColor1">
                                <label class="sc-field-label">배경색 2</label>
                                <input type="text"  class="sc-field-input" name="chart_plotBackgroundColor2" id="chart_plotBackgroundColor2">
                                <br><br><br><br>
                            </fieldset>
                        </td>
                    </tr>
                </table>
            </div>
            <!-- 제목  -->
            <div id="title" class="sc-group-container">
                <table class="sc-field-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="50%">
                            <fieldset>
                                <legend>색</legend>
                                <br>
                                <label class="sc-field-label">글꼴색</label>
                                <input type="text" class="sc-field-input" name="title_fontColor" id="title_fontColor"><br><br>
                            </fieldset>
                        </td>
                        <td>
                            <fieldset>
                                <legend>배치</legend>
                                <br>
                                <label class="sc-field-label">정렬</label>
                                <select class="sc-field-input-select" name="title_align" id="title_align">
                                    <option value="left">왼쪽</option>
                                    <option value="center">가운데</option>
                                    <option value="right">오른쪽</option>
                                </select><br><br>
                            </fieldset>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <fieldset>
                                <legend>글꼴</legend>
                                <br>
                                <label class="sc-field-label width-50">글꼴</label>
                                <select class="sc-field-input-select sc-field-input-h" name="title_fontFamily" id="title_fontFamily">
                                    <option>Arial</option>
                                </select>
                                <label class="sc-field-label width-50">크기</label>
                                <input type="text" class="sc-field-input-h num_only" name="title_fontSize" id="title_fontSize" >
                                <input type="checkbox" class="sc-field-input-checkbox" name="title_fontWeight" id="title_fontWeight"><label for="title_fontWeight">굵게</label><br><br>
                            </fieldset>
                        </td>
                    </tr>   
                </table>
            </div>
            <!-- 범례  -->
            <div id="legend" class="sc-group-container">
                <table class="sc-field-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="50%">
                            <fieldset>
                                <legend>색</legend>
                                <br>
                                <label class="sc-field-label">배경색</label>
                                <input type="text" class="sc-field-input" name="legend_backgroundColor" id="legend_backgroundColor">
                                <br><br>
                            </fieldset>
                        </td>
                        <td>
                            <fieldset>
                                <legend>배치</legend>
                                <br>
                                <label class="sc-field-label">좌우</label>
                                <select class="sc-field-input-select" name="legend_align" id="legend_align">
                                    <option value="left">왼쪽</option>
                                    <option value="center">가운데</option>
                                    <option value="right">오른쪽</option>
                                </select>
                                <br>
                                <label class="sc-field-label">상하</label>
                                <select class="sc-field-input-select" name="legend_verticalAlign" id="legend_verticalAlign">
                                    <option value="top">상</option>
                                    <option value="middle">가운데</option>
                                    <option value="bottom">하</option>
                                </select><br><br>
                            </fieldset>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <fieldset>
                                <legend>테두리</legend>
                                <input type="checkbox" name="legend_isBorderRadius" id="legend_isBorderRadius"><label for="legend_isBorderRadius">테두리 사용</label><br><br>
                                <label class="sc-field-label width-50">색</label>
                                <input type="text" class="sc-field-input-h width-80" name="legend_borderColor" id="legend_borderColor">
                                <label class="sc-field-label width-50">라운딩</label>
                                <input type="text"  class="sc-field-input-h width-80 num_only" name="legend_borderRadius" id="legend_borderRadius">
                                <label  class="sc-field-label width-50">두께</label>
                                <input type="text" class="sc-field-input-h width-80 num_only" name="legend_borderWidth" id="legend_borderWidth"><br>
                            </fieldset>
                        </td>
                    </tr>
                </table>
            </div>
            <!-- 축  -->
            <div id="axies" class="sc-group-container">
                <table class="sc-field-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="50%">
                            <fieldset>
                                <legend>축</legend>
                                <label class="sc-field-label width-80">전경색</label>
                                <input type="text" class="sc-field-input" name="axies_foregroundColor" id="axies_foregroundColor">
                                <!-- 
                                <label class="sc-field-label width-80">X축 시간 형식</label>
                                <select class="sc-field-input-select" name="axies_dateTimeLabelFormats" id="axies_dateTimeLabelFormats">
                                 -->
                                </select><br>
                                Y축 <input type="checkbox" name="axies_autoranging" id="axies_autoranging"><label for="axies_autoranging">범위 자동 조정</label><br> 
                                <label class="sc-field-label width-40">최소</label>
                                <input type="text"   class="sc-field-input-h width-40 num_only" name="axies_min" id="axies_min">
                                <label class="sc-field-label width-40">최대</label>
                                <input type="text"   class="sc-field-input-h width-40 num_only" name="axies_max" id="axies_max">
                            </fieldset>
                        </td>
                        <td>
                            <fieldset>
                                <legend>그리드 라인</legend>
                                <input type="checkbox" id="axies_isGridX" name="axies_isGridX"><label for="axies_isGridX">X축</label> 
                                <input type="checkbox" id="axies_isGridY" name="axies_isGridY"><label for="axies_isGridY">Y축</label><br>
                                <label class="sc-field-label">색</label>
                                <input type="text" class="sc-field-input" name="axies_gridLineColor" id="axies_gridLineColor">
                                <label class="sc-field-label">패턴</label>
                                <select class="sc-field-input-select" name="axies_gridLineDashStyle" id="axies_gridLineDashStyle">
                                        <option value="Solid">Solid</option>
                                        <option value="ShortDash">ShortDash</option>
                                        <option value="ShortDot">ShortDot</option>
                                        <option value="ShortDashDot">ShortDashDot</option>
                                        <option value="ShortDashDotDot">ShortDashDotDot</option>
                                        <option value="Dot">Dot</option>
                                        <option value="Dash">Dash</option>
                                        <option value="LongDash">LongDash</option>
                                        <option value="DashDot">DashDot</option>
                                        <option value="LongDashDot">LongDashDot</option>
                                        <option value="LongDashDotDot">LongDashDotDot</option>
                                </select><br>
                                <label class="sc-field-label">두께</label>
                                <input type="text" class="sc-field-input num_only" name="axies_gridLineWidth" id="axies_gridLineWidth">
                            </fieldset>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <fieldset>
                                <legend>글꼴</legend>
                                <label class="sc-field-label width-50">글꼴</label>
                                <select class="sc-field-input-select sc-field-input-h width-80" name="axies_fontFamily" id="axies_fontFamily">
                                    <option>Arial</option>
                                </select>
                                <label class="sc-field-label  width-50">크기</label>
                                <input type="text" class="sc-field-input-h  width-80 num_only" name="axies_fontSize" id="axies_fontSize">
                                <input type="checkbox" class="sc-field-input-checkbox" name="axies_fontWeight" id="axies_fontWeight"><label for="axies_fontWeight">굵게</label>
                            </fieldset>
                        </td>
                    </tr>
                </table>
            </div>
            <!-- 계열  -->
            <div id="series" class="sc-group-container">
                <table class="sc-field-table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>
                            <fieldset>
                                <legend>색</legend>
                                <label class="sc-field-label">두께</label>
                                <input type="text" class="sc-field-input num_only" name="series_lineWidth" id="series_lineWidth">
                                <label class="sc-field-label">패턴</label>
                                <select class="sc-field-input-select" name="series_dashStyle" id="series_dashStyle">
                                    <option value="Solid">Solid</option>
                                      <option value="ShortDash">ShortDash</option>
                                      <option value="ShortDot">ShortDot</option>
                                      <option value="ShortDashDot">ShortDashDot</option>
                                      <option value="ShortDashDotDot">ShortDashDotDot</option>
                                      <option value="Dot">Dot</option>
                                      <option value="Dash">Dash</option>
                                      <option value="LongDash">LongDash</option>
                                      <option value="DashDot">DashDot</option>
                                      <option value="LongDashDot">LongDashDot</option>
                                      <option value="LongDashDotDot">LongDashDotDot</option>
                                </select><br>
                                <label class="sc-field-label">색</label>
                                <input type="text" class="sc-field-input" name="series_color" id="series_color">
                            </fieldset>
                        </td>
                        <td>
                            <fieldset>
                                <legend>포인트</legend>
                                <label class="sc-field-label">크기</label>
                                <input type="text" class="sc-field-input num_only" name="series_markerRadius" id="series_markerRadius">
                                <label class="sc-field-label">모양</label>
                                <select class="sc-field-input-select" name="series_markerSymbol" id="series_markerSymbol">
                                    <option value="circle">circle</option>
                                    <option value="square">square</option>
                                    <option value="diamond">diamond</option>
                                    <option value="triangle">triangle</option>
                                    <option value="triangle-down">triangle-down</option>
                                </select><br>
                                <label class="sc-field-label">색</label>
                                <input type="text" class="sc-field-input" name="series_markerFillColor" id="series_markerFillColor">
                            </fieldset>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <fieldset>
                                <legend>포인트 외각선</legend>
                                <br>
                                <label class="sc-field-label">두께</label>
                                <input type="text" class="sc-field-input-h num_only" name="series_markerLineWidth" id="series_markerLineWidth">
                                <label class="sc-field-label ">색</label>
                                <input type="text" class="sc-field-input-h" name="series_markerLineColor" id="series_markerLineColor">
                                <br><br>
                            </fieldset>
                        </td>
                    </tr>
                </table>
            </div>
            </div>
        </div>
        <div class="buttons">
            <input type="button" id="btnOK" class="sc-button" value="OK">
            <input type="button" id="btnCancel" class="sc-button" value="Cancel">
            <input type="button" id="btnApply" class="sc-button" value="Apply">
        </div>
    </form>
</div>
</body>
</html>