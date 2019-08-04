<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<%        
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setDateHeader("Expires", -1);
    	if(request.getSession().getAttribute("user")==null){
		%>
			
<%		
	}
%>


 <link href="css/main.css" rel="stylesheet">

<div class="errorPage">
	<c:out value="${errorMessage}"/>
</div>