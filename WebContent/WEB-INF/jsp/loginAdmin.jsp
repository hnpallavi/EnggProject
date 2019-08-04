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
    	<script src="js/Chart.js"></script>
        <script type="text/javascript">
            $(function() {
                $( "#tabs" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
                $( "#tabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
                var windowHeight = window.innerHeight;
                var headerHeight = $('.header').height();
                $('.body').height(windowHeight - headerHeight);
                var options;
                var data = ${data};
                var ctx1 = document.getElementById("myChart");
                var ctx = ctx1.getContext("2d");
                var myNewChart = new Chart(ctx).Bar(data,options);
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
                    <li><a href="#tabs-1">Approve Share</a></li>
                    <li><a href="#tabs-2">Analysis</a></li>
                </ul>
                 <div id="tabs-1">
                 <fieldset>
                        <form method="post" action="approveShare.sab">
						<legend> Approve users shares :</legend>
						<br /> <select name="downloadFiles" required="required">
							<option value="">No Selection</option>
							<c:forEach items="${files}" var="file">
								<option value="<c:out value="${file.key}"/>"><c:out
										value="${file.value}" /></option>
							</c:forEach>
						</select>
						<input type="hidden" id="userId" name="userId"
								value="${userId}" /> <input type="hidden" id="userName"
								name="userName" value="${userName}" /><br /> <br />
						<button type="submit" class="button">Approve Share</button>
						</fieldset>
					</form>                    
                </div>
                <div id="tabs-2">
            	<canvas id="myChart" width="400" height="400"></canvas>
					<p>X-Axis--->Users</p>
					<p>Y-Axis---> No of files by user</p>
            </div>
                <div class="successMessage">${successMessage}</div>
                <div class="errorMessage">${errorMessage}</div>
            </div>
        </div>
    </body>