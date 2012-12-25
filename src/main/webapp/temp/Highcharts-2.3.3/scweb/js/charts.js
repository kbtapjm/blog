
// 차트 옵션, 설정 데이터를 저장하는 객체
var chartDatas = {};

var ChartSeries = {};

// 지정할 색상
ChartSeries.ColorList = [
 	'red',
	'green',
	'gray',
	'#80699B',
	'#3D96AE',
	'#DB843D',
	'#92A8CD',
	'#A47D7C',
	'#B5CA92'
];

/* charts common*/
// 차트 기본 속성
var	defVal = {
	chart: {
		renderTo: '', // render id
		backgoundGradient : false, // 배경색 그러데이션 유무
        backgroundColor1: 'blue', // 배경색
        backgroundColor2: 'yellow', // 배경색
        plotBackgoundGradient : false, // 데이터 영역
        plotBackgroundColor1: 'blue', // 데이터영역 배경색깔
        plotBackgroundColor2: 'yellow', // 데이터영역 배경색깔
        plotBorderWidth: 0,	// 데이터영역 테두리 넓이
        borderRadius: 0,	// 테두리 곡선여부
        //marginBottom: 90,
        marginRight: 50,
        marginLeft: 50,
        //marginTop: 50,
        type: 'line' // line, spline, area, areaspline, column, bar, pie and scatter.
	},
    // 차트 정보
    credits: {
    	enabled: false	// highchart logo
    },
    // 차트 저장
    exporting: {
        enabled: true
    },
    // 제목
    title: {
    	text: '',	// 제목 라벨
    	style: {
    		color: '#FFFFFF',	// 글꼴색
            fontWeight: '', 	// 굵기('', bold)
            fontSize: '16',
            fontFamily: 'arial'
    	},
        align: 'center'	// left, center, align
    },
    // 범례
	legend: {
		enabled: true,	// 사용여부
		layout: 'vertical',	// layout
		backgroundColor: '', // 배경색
		align: 'center',
		//margin: 100,
		itemMarginTop: 3,	// 시리즈와 시리즈 마진
		itemMarginBottom: 3,
	    verticalAlign: 'bottom',
	    itemStyle: {
	    	color: 'yellow',	// 글꼴색
            fontSize: '12px',	// 크기
            fontWeight: '',	// 굵기
            fontFamily: 'arial'	// 글꼴
	    },
        itemHoverStyle: {	// hover style
        	color: '#FFFFFF'
        },
        isBorderRadius : false,	// 테두리 사용여부
        borderColor: '#FFFFFF', // 테두리 색깔
        borderRadius: 0, // 테두리 곡선여부
        borderWidth: 0 // 테두리 두께
	},
	// X축 설정
	xAxis: {
		labels: {
			// 글자속성
            style: {
            	color: '#FFFFFF',
                fontWeight: '', 
                fontSize: '10',
                fontFamily: 'arial'
            }
		},
		type: 'datetime',
		lineColor: '#FFFFFF',	// X축 라인 색상
        lineWidth: 2,		// X축 라인 넓이
        gridLineWidth: 1,	// 그리드 라인 넓이
        gridLineDashStyle : 'Dot', // 그리드 스타일(longdash, ,solid)
        gridLineColor: '#FFFFFF', // 그리드 라인 색상
        endOnTick: true,	// 그리드 끝에 점선여부
        startOnTick: true,	// 축 시작점 부터 시작할건지 여부
        showEmpty: true, // 시리즈가 없을때 X축 표시 여부
        isGridX : true  // 그리드 라인 적용여부
    },
    // Y축 설정
    yAxis: {
    	title: {
    		align: 'high',
            y: 10,
            text: '',
            style: {
            	color: '#FFFFFF'
            }
    	},
    	labels: {	// 글자속성
    		style: {
    			color: '#FFFFFF',
                fontWeight: '', 
                fontSize: '10',
                fontFamily: 'arial'
    		}
		},
		gridLineColor: '#FFFFFF', // 그리드 라인 색상
        gridLineDashStyle : 'Dot', // 그리드 스타일(longdash, ,solid)
        gridLineWidth: 1,	// 그리드 라인 넓이
        lineColor: '#FFFFFF',	// Y축 라인 색상
        lineWidth: 2,		// Y축 라인 넓이
        autoranging : true,	// y축범위 자동조절
        min: 0,	// 최소값
        max: 0,	// 최대값
        showEmpty: true, // 시리즈가 없을때 Y축 표시 여부
        isGridY: true   // 그리드 라인 적용 여부
	},
	// series
	series : [],
	
	// seriesDefault
	seriesDefaultOptions : {
		id : '',
		name : '',
		color : '',   
		dashStyle : 'solid', // 라인 스타일
		lineWidth : 1,    // 라인두께
		marker : {
		    fillColor: '#FFFFFF',
            lineWidth: 2,   // 포인트 외곽선
            lineColor: null,
			symbol : 'circle' // 심볼
		},
		data : [],
	}
};

var chartApp = {
	chartDraw : {
		// 차트 초기화
		init : function(renderId) {
			chart = new Highcharts.Chart({
				chart: {
					renderTo: renderId, // render id
	            	backgroundColor: 'blue', // 배경색
	            	plotBackgroundColor: 'blue', // 데이터영역 배경색깔
	            	plotBorderWidth: 0,	// 데이터영역 테두리 넓이
	                borderRadius: 0,
	            	showAxes: true,
				},
				title: {
					text: ''
				},
				credits: {
            		enabled: false
            	},
            	exporting: {
                    enabled: false
                },
            	xAxis: {
            		showEmpty: false
            	},
            	yAxis: {
		    		showEmpty: false,
		    		title: {
		    			text: '',
                	}
				}
			});
			
			return chart;
		},
		// 차트 그리기
		drawing : function(setVal) {
			
			// 값 변경된 부분이 있으면 변경 -> 다양한 설정 가능하게.
			var prop = $.extend(true, {}, defVal, setVal);
			
			// 차트 기본 settup
			var setup = {};
			
			// 차트 설정
			setup.chart = {};
			setup.chart.renderTo = prop.chart.renderTo;
			
			// 윈도우영역 그러데이션 사용 여부
			if(prop.chart.backgoundGradient) {
				setup.chart.backgroundColor = {
						linearGradient: [0, 0, 500, 500],
		                stops: [
		                        [0, prop.chart.backgroundColor1],
		                        [1, prop.chart.backgroundColor2]
		                ]
				};
			} else {
				setup.chart.backgroundColor = prop.chart.backgroundColor1;
			}
			
			// 데이터 영역 그러데이션 사용 여부
			if(prop.chart.plotBackgoundGradient) {
				setup.chart.plotBackgroundColor = {
						linearGradient: [0, 0, 500, 500],
						stops: [
						        	[0, prop.chart.plotBackgroundColor1],
						        	[1, prop.chart.plotBackgroundColor2]
						]
				};
			} else {
				setup.chart.plotBackgroundColor = prop.chart.plotBackgroundColor1;
			}
		
			setup.chart.plotBorderWidth = prop.chart.plotBorderWidth;
			setup.chart.borderRadius = prop.chart.borderRadius;
			setup.chart.marginBottom = prop.chart.marginBottom;
			setup.chart.marginRight = prop.chart.marginRight;
			setup.chart.marginLeft = prop.chart.marginLeft;
			setup.chart.marginTop = prop.chart.marginTop,
			setup.chart.type = prop.chart.type,
			
			// 제목 설정
			setup.title = {};
			setup.title.text = prop.title.text;
			setup.title.align = prop.title.align;
			
			setup.title.style = {};
			setup.title.style.color = prop.title.style.color;
			setup.title.style.fontWeight = prop.title.style.fontWeight; 
			setup.title.style.fontSize = prop.title.style.fontSize;
			setup.title.style.fontFamily = prop.title.style.fontFamily;
			
			// 차트 로고 설정
			setup.credits = {};
			setup.credits.enabled = prop.credits.enabled;
			
			// 차트 범례 설정
			setup.legend = {};
			setup.legend.enabled = prop.legend.enabled;
			setup.legend.layout = prop.legend.layout;
			setup.legend.backgroundColor = prop.legend.backgroundColor;
			setup.legend.align = prop.legend.align;
			setup.legend.margin =  prop.legend.margin;
			setup.legend.itemMarginTop = prop.legend.itemMarginTop;
			setup.legend.itemMarginBottom = prop.legend.itemMarginBottom;
			setup.legend.verticalAlign = prop.legend.verticalAlign;
			
			setup.legend.itemStyle = {};
			setup.legend.itemStyle.color = prop.legend.itemStyle.color;
			setup.legend.itemStyle.fontSize = prop.legend.itemStyle.fontSize;
			setup.legend.itemStyle.fontWeight = prop.legend.itemStyle.fontWeight;
			setup.legend.itemStyle.fontFamily = prop.legend.itemStyle.fontFamily;
			
			setup.legend.itemHoverStyle = {};
			setup.legend.itemHoverStyle.color = prop.legend.itemHoverStyle.color;
			
			setup.legend.borderColor = prop.legend.borderColor;
			setup.legend.borderRadius = prop.legend.borderRadius;
			setup.legend.borderWidth = prop.legend.borderWidth;
			
			// X축 설정
			setup.xAxis = {};
			
			setup.xAxis.labels = {};
			setup.xAxis.labels.style = {};
			setup.xAxis.labels.style.color = prop.xAxis.labels.style.color;
			setup.xAxis.labels.style.fontWeight = prop.xAxis.labels.style.fontWeight; 
			setup.xAxis.labels.style.fontSize = prop.xAxis.labels.style.fontSize;
			setup.xAxis.labels.style.fontFamily = prop.xAxis.labels.style.fontFamily;
			
			setup.xAxis.type = prop.xAxis.type;
			setup.xAxis.lineColor = prop.xAxis.lineColor;
			setup.xAxis.lineWidth = prop.xAxis.lineWidth;
			setup.xAxis.gridLineWidth = prop.xAxis.gridLineWidth;
			setup.xAxis.gridLineDashStyle = prop.xAxis.gridLineDashStyle;
			setup.xAxis.gridLineColor = prop.xAxis.gridLineColor;
			setup.xAxis.endOnTick = prop.xAxis.endOnTick;
			setup.xAxis.startOnTick = prop.xAxis.startOnTick;
			setup.xAxis.showEmpty = prop.xAxis.showEmpty;
			
			// Y축 설정
			setup.yAxis = {};
			
			setup.yAxis.title = {};
			setup.yAxis.title.align = prop.yAxis.title.align;
			setup.yAxis.title.y = prop.yAxis.title.y;
			setup.yAxis.title.text = prop.yAxis.title.text;
			
			setup.yAxis.title.style = {};
			setup.yAxis.title.style.color = prop.yAxis.title.style.color;
			
			setup.yAxis.labels = {};
			setup.yAxis.labels.style = {};
			setup.yAxis.labels.style.color = prop.yAxis.labels.style.color;
			setup.yAxis.labels.style.fontWeight = prop.yAxis.labels.style.fontWeight; 
			setup.yAxis.labels.style.fontSize = prop.yAxis.labels.style.fontSize;
			setup.yAxis.labels.style.fontFamily = prop.yAxis.labels.style.fontFamily;
			
			setup.yAxis.gridLineColor = prop.yAxis.gridLineColor;
			setup.yAxis.gridLineDashStyle = prop.yAxis.gridLineDashStyle;
			setup.yAxis.gridLineWidth = prop.yAxis.gridLineWidth;
			setup.yAxis.lineColor = prop.yAxis.lineColor;
			setup.yAxis.lineWidth = prop.yAxis.lineWidth;
			
			// 범위 자동 조절이 아닐 경우
			if(!prop.yAxis.autoranging) {
				setup.yAxis.min = prop.yAxis.min;
				setup.yAxis.max = prop.yAxis.max;
			}
			setup.yAxis.showEmpty = prop.yAxis.showEmpty;
			
			// 시리즈
			setup.series = prop.series;
			
			// 차트 생성
			chart = new Highcharts.Chart(setup);
			
			// 차트객체와 차트 설정을 같이 리턴
			return {
			    chart : chart,
			    setup : setup
			};
			
		},
		// 차트 다시 그리기(속성 값, 차트 정보 다시 세팅)
		redraw : function() {
		    
		    
		    // 값 변경된 부분이 있으면 변경 -> 다양한 설정 가능하게.
            var prop = $.extend(true, {}, defVal, setVal);
            
            // 차트 기본 settup
            var setup = {};
            
            // 차트 설정
            setup.chart = {};
            setup.chart.renderTo = prop.chart.renderTo;
            
            // 윈도우영역 그러데이션 사용 여부
            if(prop.chart.backgoundGradient) {
                setup.chart.backgroundColor = {
                        linearGradient: [0, 0, 500, 500],
                        stops: [
                                [0, prop.chart.backgroundColor1],
                                [1, prop.chart.backgroundColor2]
                        ]
                };
            } else {
                setup.chart.backgroundColor = prop.chart.backgroundColor1;
            }
            
            // 데이터 영역 그러데이션 사용 여부
            if(prop.chart.plotBackgoundGradient) {
                setup.chart.plotBackgroundColor = {
                        linearGradient: [0, 0, 500, 500],
                        stops: [
                                    [0, prop.chart.plotBackgroundColor1],
                                    [1, prop.chart.plotBackgroundColor2]
                        ]
                };
            } else {
                setup.chart.plotBackgroundColor = prop.chart.plotBackgroundColor1;
            }
        
            setup.chart.plotBorderWidth = prop.chart.plotBorderWidth;
            setup.chart.borderRadius = prop.chart.borderRadius;
            setup.chart.marginBottom = prop.chart.marginBottom;
            setup.chart.marginRight = prop.chart.marginRight;
            setup.chart.marginLeft = prop.chart.marginLeft;
            setup.chart.marginTop = prop.chart.marginTop,
            setup.chart.type = prop.chart.type,
            
            // 제목 설정
            setup.title = {};
            setup.title.text = prop.title.text;
            setup.title.align = prop.title.align;
            
            setup.title.style = {};
            setup.title.style.color = prop.title.style.color;
            setup.title.style.fontWeight = prop.title.style.fontWeight; 
            setup.title.style.fontSize = prop.title.style.fontSize;
            setup.title.style.fontFamily = prop.title.style.fontFamily;
            
            // 차트 로고 설정
            setup.credits = {};
            setup.credits.enabled = prop.credits.enabled;
            
            // 차트 범례 설정
            setup.legend = {};
            setup.legend.enabled = prop.legend.enabled;
            setup.legend.layout = prop.legend.layout;
            setup.legend.backgroundColor = prop.legend.backgroundColor;
            setup.legend.align = prop.legend.align;
            setup.legend.margin =  prop.legend.margin;
            setup.legend.itemMarginTop = prop.legend.itemMarginTop;
            setup.legend.itemMarginBottom = prop.legend.itemMarginBottom;
            setup.legend.verticalAlign = prop.legend.verticalAlign;
            
            setup.legend.itemStyle = {};
            setup.legend.itemStyle.color = prop.legend.itemStyle.color;
            setup.legend.itemStyle.fontSize = prop.legend.itemStyle.fontSize;
            setup.legend.itemStyle.fontWeight = prop.legend.itemStyle.fontWeight;
            setup.legend.itemStyle.fontFamily = prop.legend.itemStyle.fontFamily;
            
            setup.legend.itemHoverStyle = {};
            setup.legend.itemHoverStyle.color = prop.legend.itemHoverStyle.color;
            
            setup.legend.borderColor = prop.legend.borderColor;
            setup.legend.borderRadius = prop.legend.borderRadius;
            setup.legend.borderWidth = prop.legend.borderWidth;
            
            // X축 설정
            setup.xAxis = {};
            
            setup.xAxis.labels = {};
            setup.xAxis.labels.style = {};
            setup.xAxis.labels.style.color = prop.xAxis.labels.style.color;
            setup.xAxis.labels.style.fontWeight = prop.xAxis.labels.style.fontWeight; 
            setup.xAxis.labels.style.fontSize = prop.xAxis.labels.style.fontSize;
            setup.xAxis.labels.style.fontFamily = prop.xAxis.labels.style.fontFamily;
            
            setup.xAxis.type = prop.xAxis.type;
            setup.xAxis.lineColor = prop.xAxis.lineColor;
            setup.xAxis.lineWidth = prop.xAxis.lineWidth;
            setup.xAxis.gridLineWidth = prop.xAxis.gridLineWidth;
            setup.xAxis.gridLineDashStyle = prop.xAxis.gridLineDashStyle;
            setup.xAxis.gridLineColor = prop.xAxis.gridLineColor;
            setup.xAxis.endOnTick = prop.xAxis.endOnTick;
            setup.xAxis.startOnTick = prop.xAxis.startOnTick;
            setup.xAxis.showEmpty = prop.xAxis.showEmpty;
            
            // Y축 설정
            setup.yAxis = {};
            
            setup.yAxis.title = {};
            setup.yAxis.title.align = prop.yAxis.title.align;
            setup.yAxis.title.y = prop.yAxis.title.y;
            setup.yAxis.title.text = prop.yAxis.title.text;
            
            setup.yAxis.title.style = {};
            setup.yAxis.title.style.color = prop.yAxis.title.style.color;
            
            setup.yAxis.labels = {};
            setup.yAxis.labels.style = {};
            setup.yAxis.labels.style.color = prop.yAxis.labels.style.color;
            setup.yAxis.labels.style.fontWeight = prop.yAxis.labels.style.fontWeight; 
            setup.yAxis.labels.style.fontSize = prop.yAxis.labels.style.fontSize;
            setup.yAxis.labels.style.fontFamily = prop.yAxis.labels.style.fontFamily;
            
            setup.yAxis.gridLineColor = prop.yAxis.gridLineColor;
            setup.yAxis.gridLineDashStyle = prop.yAxis.gridLineDashStyle;
            setup.yAxis.gridLineWidth = prop.yAxis.gridLineWidth;
            setup.yAxis.lineColor = prop.yAxis.lineColor;
            setup.yAxis.lineWidth = prop.yAxis.lineWidth;
            
            // 범위 자동 조절이 아닐 경우
            if(!prop.yAxis.autoranging) {
                setup.yAxis.min = prop.yAxis.min;
                setup.yAxis.max = prop.yAxis.max;
            }
            setup.yAxis.showEmpty = prop.yAxis.showEmpty;
            
            // 시리즈
            setup.series = prop.series;
            
            // 차트 생성
            chart = new Highcharts.Chart(setup);
            
            return chart;
		}
	},
	series : {
		// 추가
		add : function(seriesOptions) {
			var defaultOptions = {
					id : '',
					name : '',
					color : '',
					dashStyle : 'solid',
					lineWidth : 1,
					marker : {
						symbol : 'circle'
					},
					data : [],
			};
			
			var opts = $.extend(true, {}, defaultOptions, seriesOptions);
			
			log("render : " + opts.renderChart);
			
			opts.renderChart.addSeries(opts);
		},
		// 삭제
		remove : function() {
			
		}
	}
};