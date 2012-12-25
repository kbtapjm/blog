/*
 * SCWebConsole Utility
 * by : action1020
 */

var utobj =  {
		conf : {
			 sitename : "SCWebConsole"
			,contextPath : "/SCWebConsole"
			,version : "1.0"
			,grid : {
				emptyrecords : "Empty records",
				loadui : "block",
				loadtext : "Data Loading From Server",
				height : 450,
				//rowNum : 10,
				//rowList : [10,20,30,40,50]
				rowNum : 100,
				rowList: [100, 200, 300],
				pager : "gridPager",
				recordpos : "right",
				scroll: 0,
				gridview : true,
				viewrecords : true,
				autowidth : true,
				scrollOffset : 0,
				shrinkToFit : true,
				multiselect : true,
				sortable : true
			}
		},
		dummy: (function(){
			   var d = new Date();
			   return('SCdummy' + d.getUTCHours() + d.getUTCMinutes() + d.getUTCSeconds() + d.getUTCMilliseconds());
		})(),
		data : {
			// mode 를 설정합니다.
			setMode : function(i){
				$('#m').val(i);
			},
			getMode : function(){
				return Number($('#m').val());
			},
			// null String empty 처리
			isNull: function(s){
				if ( s == "null" && s == undefined ) return "";
				else return s;
			},
			// 백분율을 구함(소수점 1자리)
	        percent: function (num, total) {
	            var p = (num / total) * 100;
	            return isNaN(parseFloat(p.toFixed(1))) ? 0 : parseFloat(p.toFixed(1));
	        },
	        mask : {
	        	// fancybox 의 overlay 를 사용
	        	queue : 0,
	        	open : function () {
	        		utobj.data.mask.queue++;
	        		if ( utobj.data.mask.queue == 1){
		        		$.fancybox.helpers.overlay.open(); 
		        		$.fancybox.showLoading(); 
	        		}
	        		//alert ( 'open queue : ' + utobj.data.mask.queue );
	        	},
	        	close : function () {
	        		utobj.data.mask.queue--;
	        		if ( utobj.data.mask.queue == 0){
	        			$.fancybox.helpers.overlay.close(); 
		        		$.fancybox.hideLoading();	
	        		}
	        		//alert ( 'close queue : ' + utobj.data.mask.queue );
	        	}
	        },
	        bind : {
	        	btn : function(s,c,style){
	        		
	        		var m = 1, btn = "";
	        		
	        		if(c) m += 2;
	        		if(style) m+= 4;
	        		
	        		switch(m){
	        			case 1: btn = "<span>" + s + "</span>"; 
	        				break;
	        			case 3: btn = "<span class='" + c + "'>" + s + "</span>";
	        				break;
	        			case 7: btn = "<span class='" + c + "' style='" + style + "'>" + s + "</span>";
	        				break;
	        		}
	        		return btn;
	        	},
	        	combo : function(_config){
	        		// var s = [{ value :'', text : '전체' },{ value :'1', text : '1' },{ value :'2', text : '2' },{value :'3', text:'3'}];
	        		// utobj.data.bind.combo({renderID : 'searchType', object : s });
	        		// utobj.data.bind.combo({renderID : 'searchType3', object : [{ value :'', text : '전체' },{ value :'1', text : '1' },{ value :'2', text : '2' },{value :'3', text:'3'}] });
	        		var po = [];
	        		$('#' + _config.renderID).empty();
					$.each(_config.object, function(i){
						po.push("<option value=\"" + _config.object[i]['value'] + "\">" + _config.object[i]['text'] + "</option>");
					});	

					$('#' + _config.renderID).append(po.join(''));
	        	},
	        	enumToCombo : function(_config){

	        		var url = utobj.conf.contextPath + "/common/message/multilingualMessage";
	        		var pars = "";
	        		var renderId = [];
	        		var renderClass = [];
	        		var renderSelected = [];
	        		var renderBlank = [];
	        		
	        		for ( var i = 0 ; i < _config.length; i++ ){
	        				if (i > 0){
	        					pars+="&";
	        				}
	        				
	        				if(!_config[i].selected) _config[i].selected = "";
	        					
	        				renderId.push( _config[i].renderId);
	        				renderClass.push(_config[i].enumClass);
	        				renderSelected.push(_config[i].selected);
	        				renderBlank.push(_config[i].renderBlank);
	        				
	        				pars += "name=" + _config[i].enumClass;
	        				
	        			 	//alert(_config[i].renderId + " : " + _config[i].enumClass)
	        		} 
	        		//alert("pars : " + pars );
	        		utobj.data.mask.open();
	        		utobj.data.ajax(url, {pars : pars, async : true, onsucc : function(res){
	        			for ( var k = 0; k < renderId.length; k++ ){
	        				
	        				var renderID = renderId[k];
	        				var selected = renderSelected[k];
	        				var blank = renderBlank[k];
	        				
	        				if(blank) blank = true;
	        				else blank = false;
	        				
	        				if ( res.multilingualMessageGroupVoLists.multilingualMessageGroupVoLists[k].count < 1) {
	        					alert ('empty to records of bind element : ' + renderID + ' , Class Name : ' + renderClass[k]);
	        					$('#' + renderID).empty();
	        				}else{
		        				
		        				var object = res.multilingualMessageGroupVoLists.multilingualMessageGroupVoLists[k].multilingualMessageVoList;
		    	        		var po = [];
		    	        		
		    	        		if(blank) po.push("<option value=''> </option>");
		    	        		
		    	        		$('#' + renderID).empty();
		    					$.each( object, function(i){
		    						po.push("<option value=\"" + object[i]['value'] + "\"");
		    						if ( selected == object[i]['value']){
		    							po.push(" selected ");
		    						}
		    						po.push(">" + object[i]['message'] + "</option>");
		    					});	
		    					$('#' + renderID).append(po.join(''));
	        				}
	        			}
	        			utobj.data.mask.close();
	        		}});
	        	}
	        },
	        ajax: function (url, _config) {
	            // jQuery Ajax 를 사용할때 사용합니다.
	            try {
	                if (!_config || !url) throw "utobj.data.ajax configuration error!\ncheck your script code!";

	                if (!_config.method) _config.method = "post";
	                if (!_config.async) _config.async = true;
	                if (!_config.data) _config.data = "json";

	                //alert("_config.type:" + _config.type + "\n_config.async:" + _config.async + "\n_config.data:" + _config.data);

	                jQuery.ajax({
	                    url: url + "?dummy=" + utobj.dummy,
	                    data: _config.pars,
	                    type: _config.method,
	                    async: _config.async,
	                    contentType: "application/x-www-form-urlencoded",
	                    dataType: _config.data,
	                    success: function (res) {
	                    	var result = {};

	                    	try {
	                    		if ( res.message != undefined){
	                    			result.message = res.message.message;
	                    			result.code = res.message.code;
	                    		}else{
	                    			result = res;
	                    		}
	                    		
	                    	}catch(e){
	                    		result.message = null;
	                    		result.code = null;
	                    	}
	                    	_config.onsucc( result );
	                    },
	                    error: function (res) {
	                    	
	                    	if ( _config.onerr != undefined ){
	                    		_config.onerr ( res.responseText );	
	                    	}else{
	                    		alert("onError : " + res.responseText);
	                    	}
	                    },
	                    timeout: function (res) {
	                        alert("onTimeout : " + res.responseText);
	                    }
	                });
	            } catch (e) { alert(e); }
	        },
	        left : function(i) { 
	        	return this.toString().substr(0, i);
	        },
	        right : function(i) {
	        	 return this.substring(this.length - i, this.length);
	        },
	        dec : function() {
	        	return decodeURIComponent(this.replace(/\+/g, " "));
	        },
	        enc : function() {
	        	return encodeURIComponent(this);
	        },
	        number : function() {
	        	var pair = this.replace(/,/g, "").split(".");
	            var isMinus = false;
	            if (parseFloat(pair[0]) < 0) isMinus = true;
	            var returnValue = 0.0;
	            pair[0] = pair[0].replace(/[-|+]?[\D]/gi, "");
	            if (pair[1]) {
	                pair[1] = pair[1].replace(/\D/gi, "");
	                returnValue = parseFloat(pair[0] + "." + pair[1]) || 0;
	            } else {
	                returnValue = parseFloat(pair[0]) || 0;
	            }
	            return (isMinus) ? -returnValue : returnValue;
	        },
	        money : function(){
	            var txtNumber = '' + this;
	            if (isNaN(txtNumber) || txtNumber == "") {
	                return "";
	            } else {
	                var rxSplit = new RegExp('([0-9])([0-9][0-9][0-9][,.])');
	                var arrNumber = txtNumber.split('.');
	                arrNumber[0] += '.';
	                do {
	                    arrNumber[0] = arrNumber[0].replace(rxSplit, '$1,$2');
	                } while (rxSplit.test(arrNumber[0]));
	                if (arrNumber.length > 1) {
	                    return arrNumber.join('');
	                } else {
	                    return arrNumber[0].split('.')[0];
	                }
	            }
	        },
	        toString : function(){
	        	return this + '';
	        }
		},
		date: {
	        date: new Date(),
	        firstDayOfMonth: function () {
	            // 현재 월의 첫째날(01)을 리턴한다. 2012-07-01
	            var date = utobj.date.date;
	            date.setDate(1);
	            return utobj.date.getDate(date);
	        },
	        lastDayOfMonth: function () {
	            // 현재 월의 마지막날(31)을 리턴한다. 2012-07-31
	            var date = utobj.date.date;
	            var month = parseInt(date.getMonth()) + 1;
	            var last = new Date(date.getFullYear(), (month < 10 ? "0" + month : month), 0).getDate();
	            date.setDate(last);
	            return utobj.date.getDate(date);
	        },
	        firstDayOfWeek: function () {
	            return utobj.date.getWeekStartEndDate()[0];
	        },
	        lastDayOfWeek: function () {
	            return utobj.date.getWeekStartEndDate()[1];
	        },
	        getWeek: function () {
	            // 현재일자의 주차를 리턴한다.
	            var date = new Date(new Date().getFullYear(), 0, 1);
	            return Math.ceil((((new Date() - date) / 86400000) + date.getDay() + 1) / 7);
	        },
	        getYear: function () {
	            // 현재 년도를 리턴 2012
	            return this.date.getFullYear();
	        },
	        getWeekStartEndDate: function () {
	            // 현재 주차의 시작일자(월) 부터 종료일자(금) 까지의 날자를 리턴한다.
	            var start = 0;
	            //var today = new Date(this.setHours(0, 0, 0, 0));
	            var today = new Date();

	            var day = today.getDay() - start;
	            var date = today.getDate() - day;

	            var StartDate = new Date(today.setDate(date + 1));
	            var EndDate = new Date(today.setDate(date + 5));

	            return [utobj.date.getDate(StartDate), utobj.date.getDate(EndDate)];
	        },
	        getDate: function (date) {
	            date = date || utobj.date.date;
	            var month = parseInt(date.getMonth()) + 1;
	            var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	            return date.getFullYear() + "-" + (month < 10 ? "0" + month : month) + "-" + day;
	        },
	        getDayFromBit : function(ad,n ,l){
	        	// bit 로 날짜들을 구함. 15 -> 월,화,수,목
				var korea_day = new Array('월','화','수','목','금','토','일','휴무');
				var english_day = new Array('Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun', 'Holi');
				var bit = new Array(1, 2, 4, 8, 16, 32, 64, 128);
				var days = "";
				var flag = false;
				
				if(!l) l = 'ko';
				if(l) l = l.toLowerCase();

				for (var i=0; i< bit.length ; i++){
					if((bit[i] & n)){
						if(flag) days += ",";
						
						days += ad[i];
						flag = true;
					}
				}
				return days == "" ? " " : days;
			},
			getDt : function(ds,hs){
				if(!ds) ds = "-";
				if(!hs) hs = ":";
				
				var date = new Date();
				var ymd = utobj.date.getDate(date);
				var hms = (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) 
					hms+= hs + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes());
					hms+= hs + (date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds());

				return ds == "-" ? ymd + " " + hms : ymd.split('-').join(ds) + " " + hms;
			},
	        timestamp : {
	        	// unix timestamp 계산
	        	to_stamp : function(s){
	        		var t = new String();
	        		t = Date.parse(s).toString();
	        		return t.substr(0, t.length - 3);
	        	},
	        	to_string : function(s, f){
	        		var t = new Date();
	        		t.setTime(s + "000");
	        		
	        		if(!f) f = "-";
	        		
	        		var y = t.getFullYear();
	        		var m = (t.getMonth() + 1);
	        		var d = t.getDate();
	        		
	        		if ( m < 10 ) m = "0" + m;
	        		if ( d < 10 ) d = "0" + d;
	        		
	        		var ymd = y + f + m + f + d;
	        		// use time
	        		// t.getHours() + ":" + t.getMinutes() + ":" + t.getSeconds();
	        		
	        		return ymd == "1970-01-01" ? " ": ymd; 
	        	}
	        },
	        time : {
	        	getTimeSixToString : function(s, f){
	        		// 235959 문자열을 잘라 23:59:59 형태로 변환
	        		s = s + "";
	        		s = s.split(' ').join('');
	        		
	        		if(isNaN(s)) return " ";
	        		if(s.length < 6) return " ";
	        		
	        		var hour = s.substring(0,2);
	        		var minute = s.substring(2,4);
	        		var second = s.substring(4,6); 
	        		
	        		if(!f) f = ":";
	        		
	        		var hms = hour + f + minute + f + second;

	        		return hms;
	        	},
	        	getTimeFromTo: function(s, f){
	        		// 000000~235959 문자열을 00:00:00~23:59:59 형태로 변환
					s = s + "";
					s = s.split(' ').join('');

					if(s.length < 13) return " ";

					if(!f) f = "~";
					var arr = s.split(f);

					if ( arr.length < 1) return " ";

					var stime = utobj.date.time.getTimeSixToString(arr[0]);
					var etime = utobj.date.time.getTimeSixToString(arr[1]);

					var time = stime + f + etime;

					return time;
	        	}
	        }
	    },
		popup : {
			// 일반 팝업창을 생성합니다.
			open : function(_config){
				// url, title, width, height
				
				if (!_config.url) throw new ("utobj.poup.open url not defined !");
				if (!_config.title) _config.title = utobj.conf.sitename + "_popup";
				if (!_config.method) _config.method = "get";
				if (!_config.width) _config.width = "100%";
				if (!_config.height) _config.height = "100%";
				if (!_config.resizeable) _config.resizeable = "no";
				if (!_config.scrollbars) _config.scrollbars = "yes";
				if (!_config.toolbars) _config.toolbars = "no";
				if (!_config.pars) _config.pars = null;
				
				var options = { top: 0, left: 0, width: 800, height: screen.availHeight - 60, title: "", resizeable: "no", scrollbars: "yes", toolbars: "no", status: "no", menu: "no", mode: "center" };
	            options.left = (screen.availWidth - options.width) / 2;
	            options.top = 0;

	            var opt = 'width=' + _config.width + ',height=' + _config.height + ',top=' + options.top + ',left=' + options.left + ',resizable=' + _config.resizeable + ',scrollbars=' + _config.scrollbars;
	            opt += ',toolbars=' + _config.toolbars + ',status=' + options.status + ',menu=' + options.menu;

	            win = window.open("about:blank", _config.title, opt);
	            
				var frm = document.createElement("form");
				frm.target = _config.title;
				frm.setAttribute("method", _config.method);
				frm.setAttribute("action", _config.url);
				
				
				if (_config.pars != null){
					
					for ( var k in _config.pars ){
						if ( _config.pars[k] != '' && _config.pars[k] != undefined){
							
							var pa = document.createElement("input");
					        pa.setAttribute("type", "hidden");    
					        pa.setAttribute("name", k); 
					        pa.setAttribute("value", _config.pars[k]); 
					        frm.appendChild(pa); 
						}
					}
	            }
				
				document.body.appendChild(frm);
				frm.submit();

	            if (parseInt(navigator.appVersion) >= 4) {
	                win.focus();
	            }
			},
			// fancybox 를 이용하여 레이어 팝업을 생성합니다.
			fopen : function(_config) {
					
					//if (!_config.width) _config.width = "90%";
					//if (!_config.height) _config.height = "100%";
					if (!_config.scrolling) _config.scrolling = "no";
					if (!_config.transitionIn) _config.transitionIn = "elastic";
					if (!_config.transitionOut) _config.transitionOut = "elastic";
					if (!_config.type) _config.type = "iframe";
					if (!_config.url) _config.url = "#";
					if (!_config.pars) _config.pars = null;
					if (!_config.method) _config.method = "get";
					if (!_config.modal) _config.modal = true;
					if (!_config.closeClick) _config.closeClick = false;
					
					var cont = [], form = [], pars = [];
					
					cont.push("<html>");
					cont.push("<head>");
					cont.push("<link rel='stylesheet' href='" + utobj.conf.contextPath + "/css/fancybox/jquery.fancybox.css' type='text/css' media='screen' />");
					cont.push("<script src='" + utobj.conf.contextPath + "/js/jquery-1.8.2.min.js'></script>")
					cont.push("<script src='" + utobj.conf.contextPath + "/js/fancybox/jquery.fancybox.js'></script>");
					cont.push("<script src='" + utobj.conf.contextPath + "/js/utobj.js'></script>");
					cont.push("</head>");
					cont.push("<body>");
					
					form.push("<form method='" + _config.method + "' action='" + _config.url +"' name='SCWebConsoleIframeForm'>");
					if ( _config.pars != null ){
						for ( var k in _config.pars ){
							if ( _config.pars[k] != '' && _config.pars[k] != undefined){
								pars.push("<input type='hidden' name='" + k + "' value='" + _config.pars[k]  + "'/>");
							}
						}
						form.push(pars.join(''));
					}
					form.push("</form>");
					
					cont.push(form.join(''));
					cont.push("</body>");
					cont.push("<script type='text/javascript'>");
					cont.push("window.onload = function(){");
					cont.push("utobj.data.mask.open();")
					cont.push("document.SCWebConsoleIframeForm.submit();");
					cont.push("}");
					cont.push("</script>");
					cont.push("</body>");
					cont.push("</html>");
					
					jQuery.fancybox({
							  content : '<iframe id="SCWebConsolePopIframe" class="fancybox-iframe" frameborder="0" vspace="0" hspace="0" src="about:blank"></iframe>'
							, 'width' : _config.width
							, 'height': _config.height
							, 'scrolling': _config.scrolling
							, 'transitionIn': _config.transitionIn
							, 'transitionOut': _config.transitionOut
							, 'type': _config.type
							, 'href': _config.url
							, 'modal' : _config.modal
							, 'closeClick' : _config.closeClick
							, 'autoDimensions' : false
							, 'hideOnOverlayClick' : true
							, 'fitToView' : false
							, 'autoSize' : false
							, 'afterShow' : function() {
					            var oi = document.getElementById('SCWebConsolePopIframe');
					            var oid = (oi.contentWindow.document || oi.contentDocument );  
					            oid.open(); 
					            oid.write(cont.join(''));
					            oid.close();
					        }
					});
			},
			fresize : function(w,h){
				// fancy iframe popup 을 리사이징 합니다.
				if(!h) h = '90%';
				
				parent.$.fancybox.current.width = w;
				parent.$.fancybox.current.height = h;
				parent.$.fancybox.update();
			}
		},
		event : {
	        // 검색 이벤트 바인딩
	        searchEvent: function () {
	            // 검색 이벤트 바인딩
	            try {
	                $('.searchType').bind('change', SCWebConsole.jqgrid.search);
	            } catch (e) {}
	            
	            try {
	            	$('.searchWord').bind('keypress', function(e) {
	                	
	                    if ("13" == e.keyCode) {
	                    	if(e) e.preventDefault();
	                    	SCWebConsole.jqgrid.search();
	                    }
	                });
	            } catch (e) {}
	            
	            try {
	            	$('.searchGroup').bind('change', function(e) {
	            		if( $($('.searchWord')[0]).val() != ""){
	            			if(e) e.preventDefault();
	                    	SCWebConsole.jqgrid.search();
	            		}
	            	});
	            }catch(e){}
	        },
	        // 숫자만 입력가능하도록 바인딩
	        intEvent : function() {
	        	$(".int").bind("focus", function(event){
	                if (event.target.tagName == "INPUT") {
	                    var _self = jQuery(event.target);
	                    if (_self.val() == "") {
	                        _self.val(0);
	                        _self.select();
	                    }
	                    if (_self.val() != "") {
	                        _self.css({ "text-align": "left" });
	                        _self.val(_self.val().number());
	                        _self.select();
	                    }
	                }
	            });

				$(".int").bind("blur", function(event){
	                if (event.target.tagName == "INPUT") {
	                	var maxsize = 15;
	                    var _self = jQuery(event.target);
	                    if (_self.val().length > maxsize) {
	                        alert(maxsize + "이상의 숫자를 입력할 수 없습니다.");
	                        _self.val(_self.val().left(maxsize - 1).number());
	                    } else {
	                        _self.val(_self.val().number());
	                    }
	                    _self.css({ "text-align": "right" });
	                }
	            });
	        },
	        // 입력된 값을 금액단위( 콤마) 로 변환
	        moneyEvent : function() {
	        	$(".money").bind("focus", function(event){
	                if (event.target.tagName == "INPUT") {
	                    var _self = jQuery(event.target);
	                    if (_self.val() == "") {
	                        _self.val(0);
	                        _self.select();
	                    }
	                    if (_self.val() != "") {
	                        _self.css({ "text-align": "left" });
	                        _self.val(_self.val().number());
	                        _self.select();
	                    }
	                }
	            });

				$(".money").bind("blur", function(event){
	                if (event.target.tagName == "INPUT") {
	                	var maxsize = 10;
	                    var _self = jQuery(event.target);
	                    if (_self.val().length > maxsize) {
	                        alert(maxsize + "이상의 숫자를 입력할 수 없습니다.");
	                        _self.val(_self.val().left(maxsize - 1).number().money());
	                    } else {
	                        _self.val(_self.val().number().money());
	                    }
	                    _self.css({ "text-align": "right" });
	                }
	            });
	        }
		},
		jquery : {
			// 객체의 값을 alert 창으로 확인
			alert : function(){
				try{
					alert($(this).val());
				}catch(e){}		
			},
			// 객체 상태를 disabled
			disabled : function(){
				try{
					$(this).attr('disabled','disabled');
				}catch(e){}
			},
			// 객체 상태를 enabled
			enabled : function(){
				try{
					$(this).attr('disabled',false);
				}catch(e){}
			},
			// 객체의 readonly 속성을 boolean 값에 따라 설정
			readonly : function(b){
				try{
					$(this).attr('readonly', b);
				}catch(e){}
			},
			setDt : function(){
				try{
					$(this).val(utobj.date.getDt());
				}catch(e){}		
			}
		}
};

// jQuery extension function
$.fn.alert					= utobj.jquery.alert;
$.fn.disabled				= utobj.jquery.disabled;
$.fn.enabled				= utobj.jquery.enabled;
$.fn.readonly				= utobj.jquery.readonly;
$.fn.setDt					= utobj.jquery.setDt;

// String Object
String.prototype.left		= utobj.data.left;
String.prototype.right		= utobj.data.right;
String.prototype.enc		= utobj.data.enc;
String.prototype.dec		= utobj.data.dec;
String.prototype.number		= utobj.data.number;

// Number Object
Number.prototype.money		= utobj.data.money;
Number.prototype.toString	= utobj.data.toString;