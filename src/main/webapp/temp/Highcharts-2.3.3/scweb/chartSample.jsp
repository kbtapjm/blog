<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<%@ page pageEncoding="UTF-8"%>
<head>
<title>Chart Sample </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="js/utobj.js"></script>
<script type="text/javascript" src="js/charts.js"></script>
<script type="text/javascript" src="js/common.js"></script>

<!-- highcharts  -->
<script src="../js/highcharts.src.js"></script>
<script src="../js/modules/exporting.src.js"></script>

<script type='text/javascript'>
var perFormancechart1; // 차트1
var perFormancechart1Id = "container1";
var perFormancechart2; // 차트2

var SCWebConsole = {
    pageStart : function() {
        this.chart.init();
        this.event.init();
    },
    chart : {
        // 차트 초기화
        init : function(properteis) {
            //chartApp.chartDraw.init('container1');
            //chartApp.chartDraw.init('container2');
        },
        // 차트 추가
        drawing : function() {
            
                  
            chartApp.chartDraw.drawing(setVal);
        },
        series : {
            add : function(series) {
                chart.addSeries(series);
            },
            remove : function() {   
                
            }
        }
    },
    event : {
        init : function() {
            $('#left_buttonProperties').unbind().bind('click', function() {
                SCWebConsole.popup.properties();
            });
            
            this.chart1Event();
            this.chart2Event();
        },
        // 첫번째 차트
        chart1Event : function() {
            // 차트 초기화 -> 차트만 그리기
            $('#left_buttonChartInit').unbind().bind('click', function() {
                perFormancechart1 = chartApp.chartDraw.init('container1');
            });
            
            // 차트 생성
            $('#left_buttonChartDrawing').unbind().bind('click', function() {
                var options = {
                    chart: {
                        renderTo: 'container1'
                    },
                    title : {
                        text : 'Parameter1'
                    },
                    yAxis: {
                        title: {
                            text: '%'
                        }
                    },
                    series : [
                        {
                             id: 'container1_s0', // renderId_s0
                             name: '데이터베이스 서버/System',
                             color: ChartSeries.ColorList[0],
                             dashStyle : 'solid',
                             lineWidth : 1,
                             data: [[1358421496000,1.6],[1358421557000,1.36],[1358421618000,1.56],[1358421679000,1.4],[1358421740000,1.27],[1358421800000,1.41],[1358421861000,1.42],[1358421922000,1.31],[1358421983000,1.32],[1358422044000,1.24],[1358422105000,1.29],[1358422166000,1.24],[1358422226000,1.51],[1358422287000,1.67],[1358422348000,1.46],[1358422409000,1.49],[1358422470000,1.31],[1358422531000,1.41],[1358422957000,1.4],[1358423018000,1.61],[1358424600000,1.86],[1358424661000,1.23],[1358424722000,1.47],[1358424783000,1.5],[1358424844000,1.44],[1358424905000,1.28],[1358424965000,1.55],[1358425026000,1.17]]
                        }
                    ]
                };
                
                var callback = chartApp.chartDraw.drawing(options);
           
                // 콜백후 차트 정보 확인
                perFormancechart1 = callback.chart;
                chartDatas.perFormancechart1Id = callback.setup;
                
                log("chartDatas : " + JSON.stringify(chartDatas));
            });
            
            // 시리즈추가1
            $('#left_buttonSeriesAdd1').unbind().bind('click', function() {
                var seriesNo = perFormancechart1.series.length;
                
                var seriesOptions = {};
                seriesOptions.id = 'container1_s' + seriesNo;
                seriesOptions.name = '데이터베이스 서버/postgresql';
                seriesOptions.color = ChartSeries.ColorList[seriesNo];
                seriesOptions.data = [[1358421922000,1.31],[1358422044000,1.24],[1358422105000,1.29],[1358422226000,1.51],[1358422287000,1.67],[1358422348000,1.46],[1358422409000,1.49],[1358422470000,1.31]];
                
                var opts = $.extend(true, {}, defVal.seriesDefaultOptions, seriesOptions);
                
                perFormancechart1.addSeries(opts);
            });
            
            // 시리즈추가2
            $('#left_buttonSeriesAdd2').unbind().bind('click', function() {
                var seriesNo = perFormancechart1.series.length;
                
                var seriesOptions = {};
                seriesOptions.id = 'container1_s' + seriesNo;
                seriesOptions.name = '데이터베이스 서버/System/Disk IO Busy';
                seriesOptions.color = ChartSeries.ColorList[seriesNo];
                seriesOptions.data = [[1358421922000,1.31],[1358422105000,1.29],[1358422226000,1.51],[1358422348000,1.46],[1358422409000,1.49],[1358422470000,1.31]];
                
                var opts = $.extend(true, {}, defVal.seriesDefaultOptions, seriesOptions);
                
                perFormancechart1.addSeries(opts);
            });
            
            // 시리즈 삭제1
            $('#left_buttonSeriesRemove1').unbind().bind('click', function() {
                var seriese = perFormancechart1.get('container1_s0');
                seriese.remove();
            });
            
            // 시리즈 삭제2
            $('#left_buttonSeriesRemove2').unbind().bind('click', function() {
                var seriese = perFormancechart1.get('container1_s1');
                seriese.remove();
            });
            
            // 시리즈 전체삭제
            $('#left_buttonSeriesDrop').unbind().bind('click', function() {
                while(perFormancechart1.series.length > 0) {
                    perFormancechart1.series[0].remove(true);
                }
            });
        },
        // 2번째 차트
        chart2Event : function() {
            
            // 차트 초기화
            $('#right_buttonChartInit').unbind().bind('click', function() {
                perFormancechart2 = chartApp.chartDraw.init('container2');
            });
            
            // 차트 생성
            $('#right_buttonChartDrawing').unbind().bind('click', function() {
                var setVal = {
                    chart: {
                        renderTo: 'container2'
                    },
                    title : {
                        text : 'Parameter2'
                    },
                    yAxis: {
                        title: {
                            text: 'Count'
                        }
                    },
                    series : [
                        {
                             id: 'container2_s0', // renderId_s0
                             name: '데이터베이스 서버/System',
                             color: ChartSeries.ColorList[0],
                             dashStyle : 'solid',
                             lineWidth : 1,
                             data: [[1358421496000,1.6],[1358421557000,1.36],[1358421679000,1.4],[1358421740000,1.27],[1358421800000,1.41],[1358421861000,1.42],[1358421922000,1.31],[1358421983000,1.32],[1358422044000,1.24],[1358422105000,1.29],[1358422166000,1.24],[1358422226000,1.51],[1358422287000,1.67],[1358422348000,1.46],[1358422409000,1.49],[1358422470000,1.31],[1358422531000,1.41],[1358422957000,1.4],[1358423018000,1.61],[1358424600000,1.86],[1358424661000,1.23],[1358424722000,1.47],[1358424783000,1.5],[1358424844000,1.44],[1358424905000,1.28],[1358424965000,1.55],[1358425026000,1.17]]
                        }
                    ]
                };  
                
                perFormancechart2 = chartApp.chartDraw.drawing(setVal);
            });
            
            // 시리즈추가1
            $('#right_buttonSeriesAdd1').unbind().bind('click', function() {
                var seriesNo = perFormancechart2.series.length;
                
                var seriesOptions = {};
                seriesOptions.id = 'container2_s' + seriesNo;
                seriesOptions.name = '데이터베이스 서버/postgresql';
                seriesOptions.color = ChartSeries.ColorList[seriesNo];
                seriesOptions.data = [[1358421922000,1.31],[1358422044000,1.24],[1358422105000,1.29],[1358422226000,1.51],[1358422287000,1.67],[1358422348000,1.46],[1358422409000,1.49],[1358422470000,1.31]];
                
                var opts = $.extend(true, {}, defVal.seriesDefaultOptions, seriesOptions);
                
                perFormancechart2.addSeries(opts);
            });
            
            // 시리즈추가2
            $('#right_buttonSeriesAdd2').unbind().bind('click', function() {
                var seriesNo = perFormancechart2.series.length;
                
                var seriesOptions = {};
                seriesOptions.id = 'container2_s' + seriesNo;
                seriesOptions.name = '데이터베이스 서버/System/Disk IO Busy';
                seriesOptions.color = ChartSeries.ColorList[seriesNo];
                seriesOptions.data = [[1358421922000,1.31],[1358422105000,1.29],[1358422226000,1.51],[1358422348000,1.46],[1358422409000,1.49],[1358422470000,1.31]];
                
                seriesOptions.marker = {};
                
                var opts = $.extend(true, {}, defVal.seriesDefaultOptions, seriesOptions);
                
                perFormancechart2.addSeries(opts);
            });
            
            // 시리즈 삭제1
            $('#right_buttonSeriesRemove1').unbind().bind('click', function() {
                var seriese = perFormancechart2.get('container2_s0');
                seriese.remove();
            });
            
            // 시리즈 삭제2
            $('#right_buttonSeriesRemove2').unbind().bind('click', function() {
                var seriese = perFormancechart2.get('container2_s1');
                seriese.remove();
            });
            
            // 시리즈 전체삭제
            $('#right_buttonSeriesDrop').unbind().bind('click', function() {
                while(perFormancechart2.series.length > 0) {
                    perFormancechart2.series[0].remove(true);
                }
            });
        }
    },
    popup : {
        properties : function() {
            
            // 그리드에서 선택유무
            var gridId = "container2_s0";
            
            setPopup(500, 300);
            
            url="chartProperties.jsp?gridId=" + gridId;
            wr = window.open(url, '','left='+px+',top='+py+',width='+cw+',height='+ch+',location=no, scrollbars=yes, status=1, resizable=yes');
        }
    }
};

$(document).ready(function(){
    try {
        SCWebConsole.pageStart();
    } catch (e) {
        log(e.toString());
    }   
});
</script>

</head>
<body>
<table width="100%">
    <tr>
        <td bgcolor="yellow">
            <input type="button" id="left_buttonProperties" value='속성' class="sc-button">
            <input type="button" id="left_buttonChartInit" value='초기화' class="sc-button">
            <input type="button" id="left_buttonChartDrawing" value='차트생성' class="sc-button">
            <input type="button" id="left_buttonSeriesAdd1" value='시리즈추가1' class="sc-button">
            <input type="button" id="left_buttonSeriesAdd2" value='시리즈추가2' class="sc-button">
            <input type="button" id="left_buttonSeriesRemove1" value='시리즈삭제1' class="sc-button">
            <input type="button" id="left_buttonSeriesRemove2" value='시리즈삭제2' class="sc-button">
            <input type="button" id="left_buttonSeriesDrop" value='시리즈전체삭제' class="sc-button">
        </td>
        <td bgcolor="green">
            <input type="button" id="right_buttonProperties" value='속성' class="sc-button">
            <input type="button" id="right_buttonChartInit" value='초기화' class="sc-button">
            <input type="button" id="right_buttonChartDrawing" value='차트생성' class="sc-button">
            <input type="button" id="right_buttonSeriesAdd1" value='시리즈추가1' class="sc-button">
            <input type="button" id="right_buttonSeriesAdd2" value='시리즈추가2' class="sc-button">
            <input type="button" id="right_buttonSeriesRemove1" value='시리즈삭제1' class="sc-button">
            <input type="button" id="right_buttonSeriesRemove2" value='시리즈삭제2' class="sc-button">
            <input type="button" id="right_buttonSeriesDrop" value='시리즈전체삭제' class="sc-button">
        </td>
    </tr>
    <tr>
        <td><div id="container1" style="min-width: 500px; height: 500px; margin: 20"></div></td>
        <td><div id="container2" style="min-width: 500px; height: 500px; margin: 20"></div></td>
    </tr>

</table>

</body>
</html>
