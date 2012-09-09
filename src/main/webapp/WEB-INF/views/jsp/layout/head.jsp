<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %> 

<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse"
                data-target=".nav-collapse"> <span class="icon-bar"></span> <span
                class="icon-bar"></span> <span class="icon-bar"></span>
            </a> <a class="brand" href="#">MyBlog</a>
            <div class="nav-collapse">
                    <ul class="nav">
                        
                        <c:if test="${sessionScope.sessionuser != null}">
	                        <li class="active"><a href="#">Board</a></li>
	                        <li><a href="#">Photo</a></li>
                        </c:if>
                    </ul>
                    <ul class="nav pull-right">
                        <c:if test="${sessionScope.sessionuser != null}">
                            <li class="about-menu"><a href="#" id="myProfile">MyProfile</a></li>
                            <li class="about-menu"><a href="../login.html">Logout</a></li>
                        </c:if>
                    </ul>
                </div>
             session : ${sessionScope.sessionuser.userName }
        </div>
    </div>
</div>