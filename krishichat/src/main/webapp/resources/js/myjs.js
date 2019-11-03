$(function() {
	// navbar activr tag
	var parts = document.location.href.split('/');
	var lastSegment1 = parts.pop() || parts.pop();
	
	
//	console.log("document.location.hostname : "+lastSegment1);
	
	var links = [];
	$('a').each(function() {
		var parts = this.href.split('/');
		var lastSegment2 = parts.pop() || parts.pop();	
		
	   links.push( lastSegment2); 
	   // According to comment
	   // if you need each link separately then
	   // may try like

	   var href = this.href;
	   if(lastSegment1==lastSegment2)
	   {
		   $(this).addClass("activemenu");
		   $(this).parent().siblings("a").addClass("activemenu");
	   }
	   // now process with the href as you wish
	});
	
	
	
	// new chat board js script
	$(".sizeBar").click(function(){
		$("#sidebar-content")
        .removeClass("w-100")
        .width($("#sidebar").width());
		$("#sidebar").css({"flex" : "none"});
		$("#sidebar").animate({
		    width: "toggle"
		    }, 600, function() {
		            $("#sidebar").css({"flex" : '', "width" : ''});
		            $("#sidebar-content")
		                        .css("width", "")
		                        .addClass("w-100");
		});		
	});
	$("#search").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#friend-list li .name").filter(function() {
             $(this).parent().parent().parent().parent().parent().toggle($(this).text().toLowerCase().indexOf(value) > -1);
        });
    });
	
	// custom file input
	$(".custom-file-input").on(
			"change",
			function() {
				var fileName = $(this).val().split("\\").pop();
				$(this).siblings(".custom-file-label").addClass("selected")
						.html(fileName);
			});

	// Set footer bottom
	function setFooter() {
		var windowh = $(window).outerHeight(true);

		var contenth = $("#main").outerHeight(true);

		var headerh = $("#header").outerHeight(true);
		var footerh = $("#footer").outerHeight(true);
		var main_contenth = windowh - headerh - footerh;
		$("#main").css({
			"min-height" : main_contenth
		});
	}	
	setFooter();
	$(window).resize(function() {
		setFooter();
	});
	$("#footer").show();
	// Confirm password validation
	$("#password").focusout(function() {
		validatePassword();
	});

	$("#confirm_password").keyup(function() {
		validatePassword();
	});

	function validatePassword() {
		var password = $("#password").val();
		var confirm_password = $("#confirm_password").val();
		var confirm_password_object = $("#confirm_password")[0];

		if (password != confirm_password) {

			confirm_password_object.setCustomValidity("Passwords Don't Match");
		} else {
			confirm_password_object.setCustomValidity('');
		}
	}

	

});



// open profile model
$(".seeProfileButton").click(function(){
	var id = $(this).attr("data-id");
	var type= id.substr(0, 4);
	if(type=="USER")
	{
		var homePath= $("#contextPath").val();
		$.ajax({
			url : homePath+'/getUserProfile',
			type : 'GET',
			contentType : 'application/json',
			dataType : 'json',
			data : {
				id : id
			},
			success : function(result) {
				console.log(result);
				$("#userProfileName").html(result.name);
				$("#userProfileEmail").html(result.email);
				
				if(result.status==null)
					$("#userProfileStatus").html("---");
				else
					$("#userProfileStatus").html(result.status);
				
				if(result.address==null)
					$("#userProfileAddress").html("---");
				else
					$("#userProfileAddress").html(result.address);
				
				if(result.gender==null)
					$("#userProfileGender").html("---");
				else
					$("#userProfileGender").html(result.gender);
				
				if(result.birthdate==null)
					$("#userProfileBirthdate").html("---");
				else
					$("#userProfileBirthdate").html(result.birthdate);
				
				$("#userProfileProfileImagePath").attr("src","");
				var profilePath = result.profileImagePath;
				if(profilePath.startsWith("http"))
				{
					$("#userProfileProfileImagePath").attr("src",result.profileImagePath);
				}
				else
				{
					$("#userProfileProfileImagePath").attr("src",$("#profileImageContextPath").html()+ result.profileImagePath);
				}
				
				
				$('#userProfileModel').modal();
			},
			error : function(response) {
				console.log(response);
			}
		});
	}	
});


// update profile image on browser not server
$("#profileImageFileInput").change(function(){
		var ext = $(this).val().split('.').pop().toLowerCase();
		if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
		    alert('invalid extension! only gif, png, jpg and jpeg is valid. Thanks!!');
		    $(this).val('');
		    return false;
		}
		
		if(this.files[0].size > 10000000) {
	       $(this).val('');
	       return false;
		}
		const files =$('#profileImageFileInput').prop('files'); 
	    var tmppath = URL.createObjectURL(files[0]);
	    
	    $("#profileImage").attr("src",tmppath);
	    $("#updateProfileImage").show("slow");
	    
	    
	  
});

// update profile image on server
$("#updateProfileImage").click(function(){
	const files =$('#profileImageFileInput').prop('files'); 
	const reader = new FileReader();
    new Compressor(files[0], {
 	    quality: 0.4,
 	    success(result) {
 	    	reader.readAsDataURL(result);
 	    },
 	    error(err) {
 	      console.log(err.message);
 	    },
	    });
    var contextPath=$("#contextPath").val();
    var userId=$("#hiddenTextBoxUserId").val();
    reader.onload = function (e) {
    	$.ajax({
    	  type: "POST",
    	  url:contextPath+"/updateProfileImage",
    	  data:{"userId":userId,"file":e.target.result},
    	  cache: false,
    	  success: function(data){
    	     console.log("Success"+data);
    	  },
    	  fail:function(data){
    		  console.log(data);
    	  }
	});
   }
    var tmppath = URL.createObjectURL(files[0]);
    $("#navbarProfileImage").attr("src",tmppath);
    $(this).hide("slow");
});



