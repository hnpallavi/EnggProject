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
        <div class="header">SECURED SHARING OF DATA AMONG GROUP OF USERS</div>
        <div class="contents">
            <div id="tabs">
                <ul>
                    <li><a href="#tabs-1">Login</a></li>
                    <li><a href="#tabs-2">Sign Up</a></li>
                    <li><a href="#tabs-3">Forgot Password</a></li>
                </ul>
                <div id="tabs-1">
                        <fieldset>
                            <form class="centerForm" method="post" action="doLogin.sab">                          
                                <legend> Login :</legend>
                                <input type="text" name="userId" placeholder="Enter User Id" required autocomplete="off" class="inputBox"/><br/><br/>
                                <input type="password" name="userPassword" placeholder="Enter Password" required autocomplete="off" class="inputBox"/><br/><br/>
                                <button type="submit" class="button">Log In</button>
                        </fieldset>
                      </form>        
                </div>
                 <div id="tabs-2">
                        <fieldset>
                            <form class="centerForm" method="post" action="createUser.sab">                          
                                <legend> Sign Up :</legend>
                                <input type="text" name="userName" placeholder="Enter User Name" required autocomplete="off" class="inputBox"/><br/><br/>
                                <input type="text" name="userEmail" placeholder="Enter User Email" required autocomplete="off" class="inputBox"/><br/><br/>
                                <input type="text" name="userPh" placeholder="Enter User Phone" required autocomplete="off" class="inputBox"/><br/><br/>
                                <button type="submit" class="button">Sign Up</button>
                        </fieldset>
                      </form>                       
                </div>
                 <div id="tabs-3">
                        <fieldset>
                            <form class="centerForm" method="post" action="forgotPassword.sab">                          
                                <legend> Forgot Password :</legend>
                                <input type="text" name="userId" placeholder="Enter User Id" required autocomplete="off" class="inputBox"/><br/><br/>
                                <button type="submit" class="button">Retrieve Password</button>
                        </fieldset>
                      </form>                     
                </div>
                <div class="successMessage">${successMessage}</div>
                <div class="errorMessage">${errorMessage}</div>
            </div>
        </div>
    </body>