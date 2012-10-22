<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="This is description" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code="blog.label.read"/></title>
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
<script type="text/javascript" src="${root}/common/js/jquery/jquery.youtubepopup.min.js"></script>
<script type="text/javascript" src="${root}/common/js/common.js"></script>

<!-- redactor set -->
<script type="text/javascript"src="${root}/common/plugin/redactor/redactor/redactor.min.js"></script>
<link rel="stylesheet"href="${root}/common/plugin/redactor/redactor/redactor.css" />

<!-- zeroclipboard set -->
<script type="text/javascript"src="${root}/common/plugin/clipboard/ZeroClipboard.js"></script>

<script type="text/javascript">
    $(function() { 
        // 댓글 가져오기
        $("a.youtube").YouTubePopup({ youtubeId: 'o_5nD9IPPl4', title: 'My New Title' });
    });
    
    // 스크랩 객체 변수
    var clip = null;
    $(document).ready(function() {
        $('#content').redactor();
        
        // zeroClipboard 초기화
        clip = new ZeroClipboard.Client();
        clip.setHandCursor( true );
        clip.addEventListener('mouseOver', scrapMouseOver);
        clip.glue('boardScrap');
        
        // 수정
        $('#boardUpdate').bind('click', function() {
            $('#readFrm').attr("action", "../board/boardUpdate.do");
            $('#readFrm').attr("target", "_self");
            $('#readFrm').attr("method", "POST");
            $('#readFrm').submit();
        });
        
        // 삭제
        $('#boardDelete').bind('click', function() {
            /*
            var options = {
                buttons: {
                    "Ok": function () {
                        $(this).dialog("close");
                        
                        $('#readFrm').attr("action", "../board/boardDelete.do");
                        $('#readFrm').attr("target", "_self");
                        $('#readFrm').attr("method", "POST");
                        $('#readFrm').submit();
                    },
                    "Cancel": function () {
                        $(this).dialog("close");
                    }
                }   
            };
            
            alertMsg("<spring:message code='blog.label.delete.confirm'/>", options) ;
            */
            var buttons = {
                "Ok": function () {
                    $('#readFrm').attr("action", "../board/boardDelete.do");
                    $('#readFrm').attr("target", "_self");
                    $('#readFrm').attr("method", "POST");
                    $('#readFrm').submit();
                },
                "Cancel": function () {
                    
                }    
            };
            
            alertModalMsg("<spring:message code='blog.label.delete.confirm'/>", buttons);
        });
        
        // 목록
        $('#boardList').bind('click', function() {
            $('#readFrm').attr("action", "../board/boardSearchList.do");
            $('#readFrm').attr("target", "_self");
            $('#readFrm').attr("method", "POST");
            $('#readFrm').submit();    
        });
 
        // 인쇄
        $('#boardPrint').bind('click', function() {
            setPopup(1200, 800);

            var boardId = $('#boardId').val();
            
            url="../board/boardPrint.do?boardId="+boardId;
            wr = window.open(url, '','left='+px+',top='+py+',width='+cw+',height='+ch+',location=no, scrollbars=yes, status=1, resizable=yes');
        });
        
        // E-mail 전송
        $('#boardEmailSend').bind('click', function() {
            setPopup(800, 700);

            var boardId = $('#boardId').val();
            
            url="../board/boardEmailSend.do?boardId="+boardId;
            wr = window.open(url, '','left='+px+',top='+py+',width='+cw+',height='+ch+',location=no, scrollbars=yes, status=1, resizable=yes');
        });
        
        // 즐겨찾기 추가
        $('#boardFavorites').bind('click', function() {
            setPopup(1000, 800);
            
            var subject = $('#subject').val();
            var pageUrl = $('#pageUrl').val();
            winurl="https://www.google.com/bookmarks/mark?op=edit&bkmk="+pageUrl +"&title="+encodeURIComponent(subject); 
            
            wr = window.open(winurl,"viewer",'left='+px+',top='+py+',width='+cw+',height='+ch+' scrollbars=yes, status=1, resizable=yes');
        });
        
        // 스크랩
        $('#boardScrap').bind('click', function() {
            
            
            /*
            var pageUrl = $('#pageUrl').val();
            
            try {
                window.clipboardData.setData('Text',pageUrl);
                alertModalMsg("<spring:message code='blog.label.scrap.paste'/>");
            } catch(e) {
                alertModalMsg(pageUrl + "<br><br><spring:message code='blog.label.scrap.copy'/>");
            }
            */
        });
        
        // SNS 포스팅
        $('#facebook').find('img').css('cursor', 'pointer');
        $('#facebook').bind('click', function() {
            setPopup(1000, 800);
            
            var subject = $('#subject').val();
            var pageUrl = $('#pageUrl').val();
            var winurl="http://www.facebook.com/sharer.php?u="+pageUrl+"&t="+encodeURIComponent(subject); 
            
            wr = window.open(winurl,"viewer",'left='+px+',top='+py+',width='+cw+',height='+ch+' scrollbars=yes, status=1, resizable=yes');
        });
        $('#twitter').find('img').css('cursor', 'pointer');
        $('#twitter').bind('click', function() {
            setPopup(1000, 800);
            
            var subject = $('#subject').val();
            var pageUrl = $('#pageUrl').val();
            winurl="http://twitter.com/home/?status="+pageUrl+", "+encodeURIComponent(subject); 
            
            wr = window.open(winurl,"viewer",'left='+px+',top='+py+',width='+cw+',height='+ch+' scrollbars=yes, status=1, resizable=yes');
        });
        $('#gplus').find('img').css('cursor', 'pointer');
        $('#gplus').bind('click', function() {
            setPopup(1000, 800);
      
            var pageUrl = $('#pageUrl').val();
            winurl="https://plus.google.com/share?url="+pageUrl; 
            
            wr = window.open(winurl,"viewer",'left='+px+',top='+py+',width='+cw+',height='+ch+' scrollbars=yes, status=1, resizable=yes');
        });
        
    });

    // 클립보드 복사
    function scrapMouseOver(client) {
        clip.setText($('#pageUrl').val());
    }
    
    // 폰트확대 
    function fontPlus() { 
        var obj = document.getElementById('content'); 
        var nSize = obj.style.fontSize  ? obj.style.fontSize  : '9pt'; 
        var iSize = parseInt(nSize.replace('pt','')); 
        
        if (iSize < 12) { 
            obj.style.fontSize  = (iSize + 1) + 'pt'; 
            obj.style.lineHeight = '140%'; 
        } 
    } 

    // 폰트축소 
    function fontMinus() { 
        var obj = document.getElementById('content'); 
        var nSize = obj.style.fontSize ? obj.style.fontSize : '9pt'; 
        var iSize = parseInt(nSize.replace('pt','')); 
        
        if (iSize > 9)  { 
            obj.style.fontSize = (iSize - 1) + 'pt'; 
            obj.style.lineHeight = '140%'; 
        } 
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
        <form class="form-horizontal" id="readFrm">
            <input type="hidden" name="searchType" id="searchType" value="${params.searchType }">
            <input type="hidden" name="searchWord" id="searchWord" value="${params.searchWord }">
            <input type="hidden" name="pageNo" id="pageNo" value="${params.pageNo }">
            <input type="hidden" name="pageSize" id="pageSize" value="${params.pageSize }">
            <input type="hidden" name="boardId" id="boardId" value="${board.boardId}">
            <input type="hidden" name="fileName" id="fileName" value="${board.fileName}">
            <input type="hidden" name="subject" id="subject" value="${board.subject}">
            <input type="hidden" name="pageUrl" id="pageUrl" value="${board.pageUrl}">
        
            <fieldset>
                <legend><spring:message code="blog.label.read"/></legend>
                <div id="print_div">
                <div class="control-group">
                    <label class="control-label" for="title"><spring:message code="blog.label.subject"/></label>
                    <div class="controls">
                        <span class="input-xxlarge uneditable-input">${board.subject}</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="url"><spring:message code="blog.label.url"/></label>
                     <div class="controls">
                        <span class="input-xlarge uneditable-input"><a href="${board.pageUrl}" target="_blank">${board.pageUrl}</a></span>
                        <button type="button" class="btn btn-info" id="boardScrap"><spring:message code="blog.label.scrap"/></button>
                        <a href="#" title="..." class="youtube" youtube="4eYSpIz2FjU" >Click me</a>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="content"><spring:message code="blog.label.contents"/></label>
                    <div class="controls">
	                    <textarea id="content" name="content">${board.content}</textarea>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="attachFile"><spring:message code="blog.label.attachments"/></label>
                    <div class="controls">
	                    <c:url var="downURL" value="./fileDownload.do">
	                       <c:param name="fileName" value="${board.fileName}"></c:param>
	                    </c:url>  
	                    <a href="${downURL}">${board.fileName}</a>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="attachFile"></label>
                    <div class="controls">
                        ${board.user.userName}(${board.user.memberId})&nbsp;&nbsp;${fn:substring(board.createDt, 0, 16)}
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <spring:message code="blog.label.views"/> : ${board.count}
                    </div>
                </div>
                </div>
                <div class="control-group">
                    <label class="control-label"></label>
                    <div class="controls">
                        <c:if test="${sessionScope.sessionuser.memberId eq board.user.memberId}">
                        <button type="button" class="btn btn-primary" id="boardUpdate"><spring:message code="blog.label.update"/></button>
                        <button type="button" class="btn btn-primary" id="boardDelete"><spring:message code="blog.label.delete"/></button>
                        </c:if>
                        
                        <button type="button" class="btn" id="boardList"><spring:message code="blog.label.list"/></button>
                        <button type="button" class="btn btn-info" id="boardPrint"><spring:message code="blog.label.print"/></button>
                        <button type="button" class="btn btn-info" id="boardEmailSend"><spring:message code="blog.label.emailsend"/></button>
                        <button type="button" class="btn btn-info" id="boardFavorites"><spring:message code="blog.label.favorites"/></button>
                        
                        <div align="right">
                            <a id="facebook"><img src="${root}/common/images/facebook.png"alt="facebook"></a>
                            <a id="twitter"><img src="${root}/common/images/twitter.png" alt="twitter"></a> 
                            <a id="gplus"><img src="${root}/common/images/gplus.png" alt="gplus"></a>
                        </div>
                        &nbsp;&nbsp;&nbsp;
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"></label>
                    <div class="controls">
                        <blockquote>
                            <p>Reply</p>
                        </blockquote>
                        <table class="table">
                            <tr>
                                <td>reply1</td>
                            </tr>
                            <tr>
                                <td>reply2</td>
                            </tr>
                            <tr>
                                <td>reply3</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label"></label>
                    <div class="controls">
                        <textarea class="input-xxlarge" id="replycontent" rows="3"></textarea>
                        <button type="button" class="btn btn-primary" id="boardReplay">Reply Create</button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <!-- /container -->
    
     <!-- 하단 footer 영역 -->

    <!-- common html include -->
    <%@ include file="/WEB-INF/views/jsp/common/commonHtml.jsp" %>

    <!-- common file include -->
    <%@ include file="/WEB-INF/views/jsp/common/include.jsp" %>
</body>
</html>