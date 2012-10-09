<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse"
                data-target=".nav-collapse"> <span class="icon-bar"></span> <span
                class="icon-bar"></span> <span class="icon-bar"></span>
            </a> <a class="brand" href="../user/home.do">Blog</a>
            <div class="nav-collapse">
                <ul class="nav">
                    <c:if test="${sessionScope.sessionuser != null}">
                    <li><a href="../board/boardList.do"><spring:message code="blog.label.board"/></a></li>
                    <li><a href="#"><spring:message code="blog.label.photos"/></a></li>
                    </c:if>
                </ul>
                <c:if test="${sessionScope.sessionuser != null}">
	                <form class="navbar-search pull-left" action="">
	                    <input type="text" class="search-query span2" placeholder="Search">
	                </form>
                </c:if>
                <ul class="nav pull-right">
                    <c:if test="${sessionScope.sessionuser != null}">
                        <li class="about-menu" id="userInfo"><a href="../user/userInfo.do">${sessionuser.userName } </a></li>
                        <li class="about-menu"  id="signout"><a href="../user/signout.do"><spring:message code="blog.label.logout"/></a></li>
                    </c:if>
                    <li class="divider-vertical"></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-bitly-type="bitly_hover_card">Language <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="#" onclick="setLocale('ko_KR')" data-bitly-type="bitly_hover_card">Korean</a></li>
                            <li><a href="#" onclick="setLocale('en_US')" data-bitly-type="bitly_hover_card">Enghish</a></li>
                        </ul>
                    </li>
                </ul>
           </div>
        </div>
    </div>
</div>