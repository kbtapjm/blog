<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- Modal popup -->
<div style="display: none;" class="modal hide fade in" id="errModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel"><spring:message code="blog.label.notification"/></h3>
    </div>
    <div class="modal-body">
        <p>
            <div id="errMsg"></div>
        </p>
    </div>
    <div class="modal-footer">
        <button id="close" class="btn" data-dismiss="modal" data-bitly-type="bitly_hover_card" aria-hidden="true"><spring:message code="blog.label.close"/></button>
    </div>
</div>