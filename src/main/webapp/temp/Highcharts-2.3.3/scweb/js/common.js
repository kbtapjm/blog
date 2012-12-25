
/**
 *로그 출력 
 * str : 출력할 문자열
 */
function log(str) {
    if(typeof console == "undefined") {
        return false;
    };
    
    console.log(str);
}

/**
 * 셀렉트박스 옵션 생성
 * id : selector
 * data : 생성 데이터
 * selected : 선택 값
 */
function createOptions(id, data, selected) {
    
    try {
    	if(data.length > 0) {
    		$('#'+id).removeAttr("disabled");
            $('#'+id).find('option').each(function() {
                $(this).remove();
            });	
    	}
    	
        var option = "";
        for(var i = 0; i < data.length; i++) {
            option = document.createElement("option");
            option.value = data[i].value;
            option.text = data[i].message;
            
            if(data[i].value == selected) {
                option.selected = true;    
            }
            
            $('#'+id).append(option);
        }    
    } catch(e) {
        log(e.toString());
    }
}

/**
 * 체크박스 생성
 * id : selector
 * name : name
 * data : 생성 데이터
 * selected : 체크값
 */
function createCheckbox(id, name, data, chkVal) {
    
    try {
        $('#'+id).html("");
        
        for(var i = 0; i < data.length; i++) {
            var checkbox = document.createElement('input'); 
            checkbox.type = "checkbox"; 
            checkbox.name = name; 
            checkbox.value = data[i].value; 
            checkbox.id = data[i].value;
            
            if(data[i].value == chkVal) {
            	checkbox.checked = true;
            }
            
            var label = document.createElement('label') ;
            label.htmlFor = data[i].value; 
            label.appendChild(document.createTextNode(data[i].message)); 
            
            $('#'+id).append(checkbox);
            $('#'+id).append(label);
        }    
    } catch(e) {
        log(e.toString());
    }
}

/**
 * 라디오박스 생성
 * id : selector
 * name : name
 * data : 생성 데이터
 * selected : 체크값
 */
function createRadiobox(id, name, data, chkVal) {
    
    try {
        $('#'+id).html("");
        
        for(var i = 0; i < data.length; i++) {
            var radio = document.createElement('input'); 
            radio.type = "radio"; 
            radio.name = name; 
            radio.value = data[i]; 
            radio.id = i;
            
            if(data[i] == chkVal) {
            	radio.selected = true;
            }
            
            var label = document.createElement('label') ;
            label.htmlFor = i; 
            label.appendChild(document.createTextNode(data[i])); 
            
            $('#'+id).append(radio);
            $('#'+id).append(label);
        }    
    } catch(e) {
        log(e.toString());
    }
}

/**
 * Client 현재날짜시간
 */
function getCurrentDate() {
	var oDate = new Date;
	var year = oDate.getFullYear();
	var month = oDate.getMonth() + 1;
	var date = oDate.getDate();
	
	var hours = oDate.getHours();
	var minutes = oDate.getMinutes();
	var seconds = oDate.getSeconds() ;
	
	var today = year + "-" +  addZero(month) + "-" + "" + addZero(date) + "- " + addZero(hours) + ":" + addZero(minutes) + ":" + addZero(seconds);
	
	return today;
}

/**
 *  날짜, 시간앞에 0 붙이기
 */
function addZero(n) {
	if(n == null) return "00"; 
	
	return n < 10 ? "0" + n : n;
}

/**
 *  숫자만 입력
 */
function onlyNum() {
 
    if (event.keyCode >= 48 && event.keyCode <= 57) {
        return true;
    } else {
        event.returnValue = false;
    }
}

function numberCheck(val) {
	if(!onlyNum())	return false;
	
	var hourCheck = function(val) {
		log("val : " + val);
	};
	
	return {
		'hourCheck' : hourCheck
	};
}

var numberOptions = {
		onlyNumber : function() {
				$('.num_only').css('imeMode','disabled').keypress(function(event) {
						if(event.which && (event.which < 48 || event.which > 57) ) {
							event.preventDefault();
						}
				});
		},
		timeSetup : function() {
				$('.num_hour').keyup(function(){
						if($(this).val() != null && $(this).val() != '' ) {
								$(this).val($(this).val().replace(/[^0-9]/g, '') );
								var val = $(this).val();
								if(!(Number(val) >= 0 && Number(val) <= 23)) {
									$(this).val('');
								}
						}
				});
				$('.num_minutes').keyup(function(){
						if($(this).val() != null && $(this).val() != '' ) {
								$(this).val($(this).val().replace(/[^0-9]/g, '') );
								var val = $(this).val();
								if(!(Number(val) >= 0 && Number(val) <= 59)) {
									$(this).val('');
								}
						}
				});
				
				$('.num_seconds').keyup(function(){
						if($(this).val() != null && $(this).val() != '' ) {
								$(this).val($(this).val().replace(/[^0-9]/g, '') );
								var val = $(this).val();
								if(!(Number(val) >= 0 && Number(val) <= 59)) {
									$(this).val('');
								}
						}
				});
		}
};

function setPopup(width, height) {
    cw = width;
    ch = height;

    sw = screen.availWidth;
    sh = screen.availHeight;

    px = Math.ceil((sw - cw) / 2);
    py = Math.ceil((sh - ch) / 2);
}



