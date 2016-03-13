<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Action Banner Generator</title>

        <!-- CSS -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400,300,600">
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="assets/css/form-elements.css">
        <link rel="stylesheet" href="assets/css/style.css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

        <!-- Favicon and touch icons -->
        <!-- <link rel="shortcut icon" href="assets/ico/favicon.png">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png"> -->
    </head>

    <body>
		
		<!-- Top content -->
        <div class="top-content">
        	
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-8 col-sm-offset-2 text" style="text-align: center;">
                            <h1>Action Banner Generator</h1>
                            <div class="description">
                            	<p>
	                            	Drive traffic to your website with Our's unique, customizable Call-to-Action banner. Increase your website visits by adding a message visit-my-website to your social media post.
                            	</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
						<div class="col-sm-6 form-box" style="text-align:center">
							<img src = "assets/img/thumbnail.jpg" alt = "" class="img-thumbnail img-responsive">
						</div>
                        <div class="col-sm-6 form-box">
                        	<div class="form-top">
                    			<h3>Create your Action Banner</h3>
                        		<p>Fill in the form below to create your action banner:</p>
                            </div>
                            <div class="form-bottom contact-form">
			                    <form role="form" method="post" action="/shortenurl">
			                    	<div class="form-group">
			                    		<label class="sr-only" for="share-url">URL to be shared</label>
			                        	<input type="text" name="share_url" placeholder="URL to be shared" class="share-url form-control" id="share-url" data-toggle="popover" data-trigger="focus" data-placement="left" data-content="Paste the URL you want to share.">
			                        </div>
			                        <div class="form-group">
			                        	<label class="sr-only" for="cta-message">Call-to-Action Message</label>
			                        	<input type="text" name="cta_msg" placeholder="Call-to-Action Message" class="cta-message form-control" id="cta-message" data-toggle="popover" data-trigger="focus" data-placement="left" data-content="Type your eye-catching message">
			                        </div>
									<div class="form-group">
			                    		<label class="sr-only" for="cta-button">Call-to-Action Button Text</label>
			                        	<input type="text" name="cta_title" placeholder="Call-to-Action Button Text" class="cta-button form-control" id="cta-button" data-toggle="popover" data-trigger="focus" data-placement="left" data-content="Action Text ">
			                        </div>
			                        <div class="form-group">
			                        	<label class="sr-only" for="landing-url">Landing page URL</label>
			                        	<input type="text" name="lp_url" placeholder="Landing page URL" class="landing-url form-control" id="landing-url" data-toggle="popover" data-trigger="focus" data-placement="left" data-content="Paste the Landing page URL">
			                        </div>
			                        <button type="submit" class="btn  btn-lg">Create</button>
			                    </form>
		                    </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- Javascript -->
        <script src="assets/js/jquery-1.11.1.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/jquery.backstretch.min.js"></script>
        <script src="assets/js/scripts.js"></script>
        
        <!--[if lt IE 10]>
            <script src="assets/js/placeholder.js"></script>
        <![endif]-->

    </body>

</html>
