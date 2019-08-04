<html>
<head>
<title>SAB</title>
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/main.css">
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript">
	$(function() {
		$("#tabs").tabs().addClass("ui-tabs-vertical ui-helper-clearfix");
		$("#tabs li").removeClass("ui-corner-top").addClass("ui-corner-left");
		var windowHeight = window.innerHeight;
		var headerHeight = $('.header').height();
		$('.body').height(windowHeight - headerHeight);
	});
</script>
</head>

<body>
	<div class="header">Shared Authority Based Privacy-preserving</div>
	<div class="contents">
		<form class="centerForm" method="post" action="updatePassword.sab">
			<legend> Update Password :</legend>
			<input type="password" name="updatedPassword" placeholder="Enter New Password" required
				autocomplete="off" class="inputBox" /><br />
				<input type="hidden" name="userId" value="${userId}"/><br/><br/>
			<br /> 
			<button type="submit" class="button">Update Password</button>
			</fieldset>
		</form>
	</div>
</body>
</html>