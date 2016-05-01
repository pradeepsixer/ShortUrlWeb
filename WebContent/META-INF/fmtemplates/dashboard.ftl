<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
      <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dashboard | Company Name</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>      
    <div id="wrapper">
         <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="adjust-nav">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">
                        <img src="assets/img/logo.png" />

                    </a>
                    
                </div>
            </div>
        </div>
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                    <li class="active-link">
                        <a href="/dashboard" ><i class="fa fa-desktop "></i>Dashboard </a>
                    </li>
                    <!-- <li>
                        <a href="ui.html"><i class="fa fa-table "></i>List of Links</a>
                    </li>
                    <li>
                        <a href="blank.html"><i class="fa fa-edit "></i>Blank Page<span class="badge">Included</span></a>
                    </li> -->
                    <li>
                        <a href="#"><i class="fa fa-edit "></i>Banner Generator </a>
                    </li>
                    <li>
                        <a href="/preferences"><i class="fa fa-cog "></i>Preferences </a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-question "></i>Help </a>
                    </li>
					<li>
                        <a href="#"><i class="fa fa-envelope-o "></i>Contact </a>
                    </li>
                    <li>
                        <a href="/logout"><i class="fa fa-sign-out "></i>Logout </a>
                    </li>
                    
                </ul>
            </div>

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-lg-12">
						<h2>DASHBOARD</h2>   
                    </div>
                </div>              
                <!-- /. ROW  -->
                <hr />
				<div class="row">
                    <div class="col-lg-12">
						<h3>List of URLS</h3>   
                    </div>
                </div>              
                <!-- /. ROW  -->
                <hr />
				<div class="row">
                    <div class="col-lg-3">
						<div class="url" role="navigation">
							<ul class="nav">
								<#list object as shortUrlDetails>
									<li>
										<a data-href="/s/${shortUrlDetails.shortUrl}">
											<div>${shortUrlDetails.shortUrl}</div>
										</a>
									</li>
								</#list>
							</ul>
						</div>
                    </div>
					<div class="col-lg-9">
						<div class="chart">
							<div class="chart-data">
							</div>
						</div>
                    </div>
                </div>
			</div>
            <!-- /. PAGE INNER  -->
		</div>
         <!-- /. PAGE WRAPPER  -->
		<div class="footer">
            <div class="row">
                <div class="col-lg-12" >
                    &copy;  2016 yourdomain.com.
                </div>
            </div>
        </div>
    </div>
	<!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="assets/js/jquery-1.10.2.js"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="assets/js/bootstrap.min.js"></script>
      <!-- CUSTOM SCRIPTS -->
    <script src="assets/js/custom.js"></script>
</body>
</html>