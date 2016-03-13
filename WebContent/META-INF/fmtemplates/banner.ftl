<!-- This  -->
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Action Banner Generator</title>

        <!-- CSS -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400,300,600">
        <link rel="stylesheet" href="/assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="/assets/css/style.css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>

    <body>
		<div id="content">
			<!-- Bottom Content - Fixed -->
			<div class="bottom-wrapper">
				<div class="bottom-content">
					<div class="bottom-logo">
						<a href="#"><img src="/assets/img/logo.png" width="90" height="90"/></a>
					</div>
					<div class="bottom-data">
						<div class="cta-message-button">
							<p class="action-message"><span class="message-text">${bannerDescription}</span> <a id="bt" href="${landingPageUrl}" target="_blank" class="btn">${bannerTitle}</a></p>
						</div>
					</div>
					<div class="bottom-share"> share and close
					</div>
				</div>
			</div>
			<iframe src="${shareUrl}" width="100%" height="100%" frameborder="0">  </iframe>
		</div>

        <!-- Javascript -->
        <script src="/assets/js/jquery-1.11.1.min.js"></script>
        <script src="/assets/bootstrap/js/bootstrap.min.js"></script>
        <!-- <script src="/assets/js/jquery.backstretch.min.js"></script>
        <script src="/assets/js/scripts.js"></script> -->
        
        <!--[if lt IE 10]>
            <script src="/assets/js/placeholder.js"></script>
        <![endif]-->
        <script type="text/javascript">
        	$(document).ready(function() {
				$('#bt').click(function() {
					console.log('button clicked');
	        		$.ajax({
	        			type: 'POST',
	        			url: '/updvwct',
	        			data: 'u=${shortUrl}'
	        		});
        		});        	
        	});
        </script>
    </body>
</html>