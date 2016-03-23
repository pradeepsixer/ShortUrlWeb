
jQuery(document).ready(function() {
	
    /*
        Fullscreen background
    */
    $.backstretch("assets/img/backgrounds/1.jpg");
	$(function () {
		$('[data-toggle="popover"]').popover()
	});
    
    /*
	    Contact form
	*/
	$('.contact-form form input[type="text"], .contact-form form textarea').on('focus', function() {
		$('.contact-form form input[type="text"], .contact-form form textarea').removeClass('input-error');
	});
	var today = Date.parse(new Date().toDateString());
	var setupTime = localStorage.getItem('setupTime');
	if (setupTime == undefined) {
		setupTime = today;
		localStorage.setItem('setupTime', setupTime);
	}	
	if(today-setupTime > 0) {
		localStorage.clear();
		localStorage.setItem('setupTime', today);
	}
	var limit = localStorage.getItem('limit');
	if (limit == undefined) {
		limit = 0;
		localStorage.setItem('limit', limit);
	}
		$(".contact-form form").validate({
			rules: {
				share_url: {
					required: true,
					url: true
				},
				cta_msg: {
					required: true,
					minlength: 8
				},
				cta_title: {
					required: true,
					minlength: 4
				},
				lp_url: {
					required: true,
					url: true
				}
			},
			messages: {
				share_url: {
					required: 'Enter a valid URL'
				},
				cta_msg: {
					required: 'Enter min 8 characters.'
				},
				cta_title: {
					required: 'Enter min 4 characters.'
				},
				lp_url: {
					required: 'Enter a valid URL'
				}
			}, 
			submitHandler: function(form) {
				//this runs when the form validated successfully 
				if (limit < 3) {
				var postdata = $('#banner-form').serialize();
				$.ajax({
					type: 'POST',
					url: '/shortenurl',
					data: postdata,
					dataType: 'json',
					success: function(json){
						$('.contact-form .alert-message .alert').fadeOut('fast');
						limit = limit + 1;
						$('.contact-form .alert-message').append("<div class='alert alert-success alert-dismissible' role='alert'><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button><strong>Success!</strong> Your URL is <a href='" + json['short-url'] + "' target='_blank'> " + json['short-url'] +" </a> .</div>");
						// reload background
						$('.contact-form form')[0].reset();
						$.backstretch("resize");
					},
					error: function(xhr, textStatus, errorThrown){
						$('.contact-form .alert-message').append("<div class='alert alert-danger alert-dismissible' role='alert'><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button><strong>Error!</strong> Unable to process. Try again later.</div>");   
					}
				});
				} else { $('.contact-form .alert-message').append("<div class='alert alert-danger alert-dismissible' role='alert'><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button><strong>Stop!</strong> You have already used up your free daily quota.!!</div>"); }
				return false;
			} //submit it the form
		}); 
	});
