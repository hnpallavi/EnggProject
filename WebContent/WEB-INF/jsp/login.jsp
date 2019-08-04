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
<html>
    <head>       
    	<title>SAB</title>
        <link rel="stylesheet" href="css/jquery-ui.css"/>
    	<link rel="stylesheet" href="css/main.css">
   		<script src="js/jquery-1.9.1.js"></script>
    	<script src="js/jquery-ui.js"></script>
        <script type="text/javascript">
            $(function() {
                $( "#tabs" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
                $( "#tabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
                var windowHeight = window.innerHeight;
                var headerHeight = $('.header').height();
                $('.body').height(windowHeight - headerHeight);
            });
        </script>
        <style>
          .ui-tabs-vertical { width: 55em; }
          .ui-tabs-vertical .ui-tabs-nav { padding: .2em .1em .2em .2em; float: left; width: 12em; }
          .ui-tabs-vertical .ui-tabs-nav li { clear: left; width: 100%; border-bottom-width: 1px !important; border-right-width: 0 !important; margin: 0 -1px .2em 0; }
          .ui-tabs-vertical .ui-tabs-nav li a { display:block; }
          .ui-tabs-vertical .ui-tabs-nav li.ui-tabs-active { padding-bottom: 0; padding-right: .1em; border-right-width: 1px; border-right-width: 1px; }
          .ui-tabs-vertical .ui-tabs-panel { padding: 1em; float: right; width: 40em;}
        </style>
    </head>
    
    <body>
        <div class="header">SECURED SHARING OF DATA AMONG GROUP OF USERS 
        <div style="float: right;">
				Logged In:&nbsp;
				<c:out value="${userName}" />
				&nbsp;&nbsp;&nbsp; <span style="cursor: pointer"
					onclick="location.href='logout.sab'" title="Logout">
					<b>Logout</b>&nbsp;&nbsp;
				</span>
			</div>
		</div>
        <div class="contents">
            <div id="tabs">
                <ul>
                    <li><a href="#tabs-1">Upload</a></li>
                    <li><a href="#tabs-2">Download</a></li>
                    <li><a href="#tabs-3">Request Share</a></li>
                    <li><a href="#tabs-4">Request file</a></li>
                    <li><a href="#tabs-5">Download shared file</a></li>
                </ul>
                <div id="tabs-1">
                	<fieldset>
                            <form:form method="post" action="upload.sab" id="form"
						modelAttribute="uploadForm" enctype="multipart/form-data">
							<legend> Upload File :</legend>
							File to Upload : <input type="file" name="uploadedFile"
								autocomplete="off" required="required" class="textFields"
								placeholder="Upload file" /><br />
							<br /> <input type="password" name="privateKey" class="textFields" required autocomplete="off" placeholder="Enter key"/>
							<input type="hidden" id="userId" name="userId"
								value="${userId}" /> <input type="hidden" id="userName"
								name="userName" value="${userName}" /><br/><br/>
							<button type="submit" class="button">Upload File</button>
							</fieldset>
					</form:form>
                </div>
                 <div id="tabs-2">
                 <fieldset>
                        <form method="post" action="download.sab">
						<legend> Download File :</legend>
						<br /> <select name="downloadFiles" required="required">
							<option value="">No Selection</option>
							<c:forEach items="${files}" var="file">
								<option value="<c:out value="${file.key}"/>"><c:out
										value="${file.value}" /></option>
							</c:forEach>
						</select><br /> <br />
						<input type="password" name="privateKey" class="textFields" required autocomplete="off" placeholder="Enter key"/>
						<input type="hidden" id="userId" name="userId"
								value="${userId}" /> <input type="hidden" id="userName"
								name="userName" value="${userName}" /><br /> <br />
						<button type="submit" class="button">Download File</button>
						</fieldset>
					</form>                    
                </div>
                 <div id="tabs-3">
                 <fieldset>
                        <form method="post" action="share.sab">
						<legend> Request Share with user :</legend>
						<br /> <select name="users" required="required">
							<option value="">No Selection</option>
							<c:forEach items="${users}" var="file">
								<option value="<c:out value="${file.key}"/>"><c:out
										value="${file.value}" /></option>
							</c:forEach>
						</select><input type="hidden" id="userId" name="userId"
								value="${userId}" /> <input type="hidden" id="userName"
								name="userName" value="${userName}" /><br /> <br />
						<button type="submit" class="button">Request Share</button>
						</fieldset>
					</form>                   
                </div>
                
                <div id="tabs-4">
                 <fieldset>
                        <form method="post" action="requestdoc.sab">
						<legend> Request files from shared users</legend>
						<br /> <select name="docs" required="required">
							<option value="">No Selection</option>
							<c:forEach items="${files1}" var="file">
								<option value="<c:out value="${file.key}"/>"><c:out
										value="${file.value}" /></option>
							</c:forEach>
						</select><input type="hidden" id="userId" name="userId"
								value="${userId}" /> <input type="hidden" id="userName"
								name="userName" value="${userName}" /><br /> <br />
						<button type="submit" class="button">Request Document</button>
						</fieldset>
					</form>                   
                </div>
                
                <div id = "tabs-5">
                <fieldset>
                	 <form method="post" action="download.sab">
						<legend> Download shared files :</legend>
						<br /> <select name="downloadFiles" required="required">
							<option value="">No Selection</option>
							<c:forEach items="${files2}" var="file">
								<option value="<c:out value="${file.key}"/>"><c:out
										value="${file.value}" /></option>
							</c:forEach>
						</select><br /> <br />
						<input type="password" name="privateKey" class="textFields" required autocomplete="off" placeholder="Enter key"/>
						<input type="hidden" id="userId" name="userId"
								value="${userId}" /> <input type="hidden" id="userName"
								name="userName" value="${userName}" /><br /> <br />
						<button type="submit" class="button">Request Share</button>
						</fieldset>
					</form>   
                </div>
                <div class="successMessage">${successMessage}</div>
                <div class="errorMessage">${errorMessage}</div>
            </div>
        </div>
    </body>