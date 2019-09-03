
$(function(){
	
	
	var homePath= $("#contextPath").val();
	if ( homePath != undefined) {
		// is session is exist than Connect fire all ways when page is load
		connect();
	}
});

// Set global variable
var stompClient = null;
var fromId=null;
var toId=null;
var toProfileImagePath=null;
var toEmail=null;
var contextPath=null;
var profileImagePath=$("#hiddenTextBoxProfileImage").val();
var loaderUrl=profileImagePath+"/image-spin.gif";


// Establish socket connection
function connect() {
	var homePath= $("#contextPath").val();
	var socket = new SockJS(homePath+'/connect');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		stompClient.subscribe('/app/subscribeOnline', function(messageOutput) {});
		
		stompClient.subscribe('/user/broker/setUserOnline', function(messageOutput) {
			if ($("#friend-list")[0]){
				$('#'+messageOutput.body+'OnlineStatusLabel').children().html('online').parent("span").removeClass("text-danger").addClass("text-success").parent().parent().siblings().children().children(".greenDot").show().siblings(".redDot").hide();
			}
			if ($(".mesgs")[0]){
				$('#chatUserLastSeen').removeClass("text-danger").addClass("text-success").html("online");
			}
		});
		stompClient.subscribe('/user/broker/setUserOffline', function(messageOutput) {
			var user= JSON.parse(messageOutput.body);
			if ($("#friend-list")[0]){
				$('#'+user.id+'OnlineStatusLabel').children().html(user.activeTimeAgo).parent("span").removeClass("text-success").addClass("text-danger").parent().parent().siblings().children().children(".redDot").show().siblings(".greenDot").hide();
			 }
			if ($(".mesgs")[0]){
				$('#chatUserLastSeen').removeClass("text-sucess").addClass("text-danger").html(user.activeTimeAgo);
			}
		});
		
		// get home DTO with information
		stompClient.subscribe('/app/getHomeDTO', function(messageOutput) {});
		
		// typing notification
		stompClient.subscribe('/user/broker/subscriptionTypingNotification', function(messageOutput) {
			var typingFlag = messageOutput.body;
			if ($(".mesgs")[0]){
				if(typingFlag=="true")
				{
					$("#chatUserLastSeen").html("").html("typing...");
				}
				if(typingFlag=="false")
				{
					$("#chatUserLastSeen").html("").html("online");
				}
			}
		});
		
		// get friend list when group creation model open..
		stompClient.subscribe('/user/broker/subscriptionGetFriendList', function(messageOutput) {
			var friends= JSON.parse(messageOutput.body); 
			var isEmpty=true;
			var content="";
			for(var i = 0; i < friends.length; i++) {
		        isEmpty=false;
				var friend = friends[i];
				content+="	<div class='form-check'>"+
								"<label class='form-check-label'>" +
									" <input type='checkbox' name='friends' value='"+friend.id+" "+friend.email+"' class='form-check-input' value=''>" +
									"<span >"+friend.name+"<small class='ml-5'>"+friend.email+"</small></span>"+
								"</label>"+
							"</div>";
			}
			if(isEmpty)
			{
				 content+="<p class='text-center'>No friends found<p>";
			}
			$("#createGroupModelCheckBoxContent").html("").html(content);
			$('#createGroupModel').modal('show');	
		});
		// get friend list when edit member
		stompClient.subscribe('/user/broker/getFriendListExcludeGroupMember', function(messageOutput) {
			var friends= JSON.parse(messageOutput.body); 
			var isEmpty1=true;
			var isEmpty2=true;
			var groupMemberContent="";
			var friendListContent="";
			for(var i = 0; i < friends.length; i++) {
		        
				var friend = friends[i];
				if(friend.flag==false)
				{
					friendListContent+="	<div class='form-check'>"+
												"<label class='form-check-label'>" +
													" <input type='checkbox' name='friends' value='"+friend.id+" "+friend.email+"' class='form-check-input' value=''>" +
													"<span >"+friend.name+"<small class='ml-5'>"+friend.email+"</small></span>"+
												"</label>"+
											"</div>";	
					isEmpty1=false;
				}
				else
				{
					groupMemberContent +="<tr>"+
											"<td>"+(i+1)+"</td>"+
											"<td>"+friend.name+"</td>"+
											"<td>"+friend.email+"</td>"+
											"<td ><i data-id='"+friend.id+"' data-email='"+friend.email+"' class='far fa-trash-alt pointer deleteMemberTransButton'></i></td>"+
										 "</tr>";
					isEmpty2=false;
				}
			}
			if(isEmpty1)
			{
				friendListContent+="<p class='text-center'>No friends found<p>";
			}
			if(isEmpty2)
			{
				groupMemberContent+="<tr><td colspan=4>No friend Found</td></tr>";
			}
			$("#unGroupMemberEditMemberContentModel").html(friendListContent);
			$("#groupMemberEditMemberContentModel").html(groupMemberContent);
			$('#editGroupMemberModel').modal('show');	
		});
		// All Notification on click of popover
		stompClient.subscribe('/user/broker/receiveAllNotification', function(messageOutput) {
			
			var messages= JSON.parse(messageOutput.body); 
			var isEmpty=true;
			var content="";
			for(var i = 0; i < messages.length; i++) {
				
				var obj = messages[i];
		        let today= moment(obj.createdAt,"DD/MM/YYYY hh:mm:ss A").calendar();
		        var homePath= $("#contextPath").val();
		        var frinedListPage=homePath+"/friendList";
		        var acceptFrinedRequestPage=homePath+"/friendRequest";
		        var rejectFrinedRequestPage=homePath+"/discoverUser";
		        var chatPage=homePath+"/chat";
		        if(!(obj.readed))
				{
						
						if(obj.purpose=="SENDREQUEST")
						{
							content+="<a href='"+acceptFrinedRequestPage+"'> <p>"+obj.message+" <span class='badge badge-primary'>New</span></p><p class='text-right'><small>"+today+"</small></p></a><hr>";
						}
						else if(obj.purpose=="ACCEPTREQUEST")
						{
							content+="<a href='"+frinedListPage+"'> <p>"+obj.message+" <span class='badge badge-primary'>New</span></p><p class='text-right'><small>"+today+"</small></p></a><hr>";
						}
						else if((obj.purpose=="REJECTREQUEST") ||(obj.purpose=="UNFRIEND"))
						{
							content+="<a href='"+rejectFrinedRequestPage+"'> <p>"+obj.message+" <span class='badge badge-primary'>New</span></p><p class='text-right'><small>"+today+"</small></p></a><hr>";
						}
						else if(obj.purpose=="MEMBEROFGROUP")
						{
							content+="<a href='"+chatPage+"'> <p>"+obj.message+" <span class='badge badge-primary'>New</span></p><p class='text-right'><small>"+today+"</small></p></a><hr>";
						}
						
						
				}
				else 
				{
						if(obj.purpose=="SENDREQUEST")
						{
							content+="<a href='"+acceptFrinedRequestPage+"'> <p>"+obj.message+"</p><p class='text-right'><small>"+today+"</small></p></a><hr>";
						}
						else if(obj.purpose=="ACCEPTREQUEST")
						{
							content+="<a href='"+frinedListPage+"'> <p>"+obj.message+" </p><p class='text-right'><small>"+today+"</small></p></a><hr>";
						}
						else if((obj.purpose=="REJECTREQUEST") ||(obj.purpose=="UNFRIEND"))
						{
							content+="<a href='"+rejectFrinedRequestPage+"'> <p>"+obj.message+"</p><p class='text-right'><small>"+today+"</small></p></a><hr>";
						}
						else if(obj.purpose=="MEMBEROFGROUP")
						{
							content+="<a href='"+chatPage+"'> <p>"+obj.message+"</p><p class='text-right'><small>"+today+"</small></p></a><hr>";
						}
				}
				isEmpty=false;
			}
			if(isEmpty)
			{
				 content+="<p class='text-center'>Empty<p>";
			}
			$("#notifivationPopup").popover({
		        html: true,
		        title: "Notifications",
		        content: content,
		        placement: 'bottom',
		        trigger:'focus'
		     }).popover('show');
		});
		
		// Receive Notification count
		stompClient.subscribe('/user/broker/receiveNotification', function(messageOutput) {
			// get message DTO
			var notification = JSON.parse(messageOutput.body);
			
			// increase notification count
			var totalNotification= $("#totalCountNotification").html();
			$("#totalCountNotification").html(parseInt(totalNotification)+1).show();
		});
		
		
		
		
		// get home DTO with information
		stompClient.subscribe('/user/broker/getHomeDTO', function(messageOutput) {
			var homeDTO=JSON.parse(messageOutput.body);
			var totalCountMessage=parseInt(homeDTO.totalPersonalMessageCount);
			var totalCountNotification=parseInt(homeDTO.totalNotificationCount);
			if(totalCountMessage!=0)
				$("#totalCountMessages").show().html(totalCountMessage);
			
			if(totalCountNotification!=0)
				$("#totalCountNotification").show().html(totalCountNotification);
			
			
		});
		
		// Group Id when group created..
		stompClient.subscribe('/user/broker/getGroupIdAfterRegister', function(messageOutput) {
			var group= JSON.parse(messageOutput.body); 
			content="<li class='list-group-item  hover-bg-lightgray py-2 w-100'>"+
						"<div class='row'>"+
							"<div class='col-lg-3 text-center chat_img px-0'>"+
								"<img src='"+profileImagePath+"/defaultProfile.png'	style='width: 50px; height: 50px;' class='rounded-circle chat-sidebar-profile-image seeProfileButton pointer' data-id='"+group.id+"'>"+
							"</div>"+
							"<div class='col-lg-8 chat_ib p-o my-auto' data-id='"+group.id+"' data-isleader='true'>"+
								"<div class='row'>"+
									"<div class='col-lg-8'>"+
										"<h6 class='name'>"+group.name+"<i class='fa fa-users pl-2' aria-hidden='true' ></i></h6>"+
									"</div>"+
									"<div class='col-lg-2'>"+
										"<i class='badge badge-pill badge-success messageCount mx-2'	style='display: none'>0</i>"+
									"</div>"+
								"</div>"+
							"</div>"+
						"</div>"+
					"</li>";
				$('#friend-list').prepend(content);
				$.toast({text:'Group created..',hideAfter: 5000});
		});
		
		// get Group Detail
		stompClient.subscribe('/user/broker/getGroupInfo', function(messageOutput) {
			var group= JSON.parse(messageOutput.body);
			$("#profileImageEditGroupDetailModel").attr("src","");
			$("#profileImageEditGroupDetailModel").attr("src",$("#profileImageContextPath").html()+ group.profileImagePath);
			$("#groupNameEditGroupDetailModel").val(group.name);
			$("#groupStatusEditGroupDetailModel").val(group.status);
			$("#editGroupProfileModel").modal('show');
		});
		
		// get Group Detail
		stompClient.subscribe('/user/broker/getGroupMember', function(messageOutput) {
			var friends= JSON.parse(messageOutput.body); 
			var isEmpty1=true;
			var groupMemberContent="";
			var trClass="";
			var adminText="";
			for(var i = 0; i < friends.length; i++) {
				var friend=friends[i];
				trClass="";
				adminText="";
				if(friend.flag==true)
				{
					trClass="style='background-color:#ffe6ff'";
					adminText="&nbsp;&nbsp;&nbsp;(Admin)";
				}
	        	groupMemberContent +="<tr "+trClass+">"+
										"<td>"+(i+1)+"</td>"+
										"<td>"+friend.name+adminText+"</td>"+
										"<td>"+friend.email+"</td>"+
									 "</tr>";
				isEmpty1=false;
			}
			if(isEmpty1)
			{
				groupMemberContent+="<tr><td colspan=4>No friend Found</td></tr>";
			}
			$("#groupMemberShowProfileModel").html(groupMemberContent);
			$("#showGroupProfileModel").modal('show');
		});
		
		
		
		// get all Personal chat
		stompClient.subscribe('/user/broker/getAllPersonalChat', function(messageOutput) {
			if ($(".msg_history")[0]){
				var messages= JSON.parse(messageOutput.body); 
				var isEmpty=true;
				for(var i = 0; i < messages.length; i++) {
			        isEmpty=false;
					var obj = messages[i];
					
					// set name of the user in group
					
					var name=$('div[data-id="'+obj.fromId+'"] .name').html();
					var nameText="<span class='text-success'><small>"+name+"</small></span>"+"<br>";
					if(name==undefined )
						nameText="<span class=' text-danger'><small>"+obj.fromEmail+"</small></span>"+"<br>";
					 
					var replaceImageText=null;
					if(obj.message==null)
					{
						var ext = obj.file.substr(obj.file.lastIndexOf('.') + 1);
						if(ext=="mp4")
						{
							replaceImageText="<video class='w-100'  controls>"+
							"<source src='"+contextPath+"/"+obj.file+"'>"+
							"</video>";
						}
						else
						{
							replaceImageText="<img class='rounded w-100' src='"+contextPath+"/"+obj.file+"'>";
						}
						
					}
					else
					{
						replaceImageText="<span style='overflow-wrap: break-word' >"+obj.message+"</span>";
					}
					
			        if(obj.fromId==fromId)
		        	{
			        	let today= moment(obj.chatTime,"DD/MM/YYYY hh:mm:ss A").calendar();
			        	
			        	var outgoingMessage=   	"<div class='row justify-content-end '>"+
													"<div class='card message-card m-1 border-0 bg-transparent ' >"+
														"<div class='card-body border p-1 px-3  text-center box-shadow ' style='background-color:#dcf8c6;border-radius: 20px 20px 3px 20px;background-image: url('"+loaderUrl+"');'  >"+
														replaceImageText +
														"</div>"+
														"<div class='card-footer  border-0 py-1 pl-0 time-tranparent'  >"+
															"<span class='float-rigth ' ><small class='text-muted '>"+today+"</small></span>"+
														"</div>"+
													"</div>"+
												"</div>";
			        	$(".msg_history").append(outgoingMessage);
			        }
			        else if(obj.fromId==toId)
			        {
			        	let today= moment(obj.chatTime,"DD/MM/YYYY hh:mm:ss A").calendar();
			        	var incomingMessage=   	"<div class='row'>"+
													"<div class='card message-card m-1 border-0 bg-transparent' >"+
														"<div class='card-body border p-1 px-3  bg-white text-center box-shadow' style='border-radius: 20px 20px 20px 3px;'   >"+
														replaceImageText +
														"</div>"+
														"<div class='card-footer  border-0 py-1 pr-0 time-tranparent' >"+
															"<span class='float-rigth'><small class='text-muted'>"+today+"</small></span>"+
														"</div>"+
													"</div>"+
												"</div>";
			        	$(".msg_history").append(incomingMessage);
			        }
			        else
		        	{
			        	
			        	let today= moment(obj.chatTime,"DD/MM/YYYY hh:mm:ss A").calendar();
			        	var incomingMessage=   	"<div class='row'>"+
													"<div class='card message-card m-1 border-0 bg-transparent' >"+
													"<div class='card-body border p-1 px-3  bg-white text-center box-shadow' style='border-radius: 20px 20px 20px 3px;'   >"+
													nameText+replaceImageText+
														"</div>"+
														"<div class='card-footer  border-0 py-1 pr-0 time-tranparent' >"+
															"<span class='float-rigth'><small class='text-muted'>"+today+"</small></span>"+
														"</div>"+
													"</div>"+
												"</div>";
			        	$(".msg_history").append(incomingMessage);
		        	}
			    }
				if(isEmpty)
				{
					$(".msg_history").append("<p class='text-center'>Nothing</p>");
				}
			    
				// scroll bottom
				$('.msg_history').scrollTop($('.msg_history')[0].scrollHeight);
				
				// Send message that all message read update status isReaded
				// false to true
				stompClient.send("/app/changePersonalMessageStatusAllReaded/"+toId+"/"+fromId, {}, JSON.stringify({
					'message' : null					
				}));
				$('#'+toId+'OnlineStatusLabel').parent().parent().siblings().children().children(".messageCount").html("0").hide();
			}
		});
		
		// recieve personal chat
		stompClient.subscribe('/user/broker/getOnePersonalMessage', function(messageOutput) {
			var message= JSON.parse(messageOutput.body); 
			if(toId==message.fromId && $(".msg_history")[0])
			{
				
				// set name of the user in group
				
				var name=$('div[data-email="'+message.toEmail+'"] .name').html();
				var nameText="<span class='text-success'><small>"+name+"</small></span>"+"<br>";
				if(name==undefined )
					nameText="<span class=' text-danger'><small>"+message.toEmail+"</small></span>"+"<br>";
				
				var type= message.fromId.substr(0, 4);
				if(type=="USER")
				{
					nameText="";
				}	
				
				
				let today= moment(message.messageTime,"DD/MM/YYYY hh:mm:ss A").calendar();
				var incomingMessage=null;
				if(message.message==null)
					{
							var ext = message.file.substr(message.file.lastIndexOf('.') + 1);
							if(ext=="mp4")
							{
								replaceImageText="<video class='w-100' controls>"+
								"<source src='"+contextPath+"/"+message.file+"'>"+
								"</video>";
							}
							else
							{
								replaceImageText="<img class='rounded w-100' src='"+contextPath+"/"+message.file+"'>";
							} 
							var loaderUrl=profileImagePath+"/image-spin.gif";
							incomingMessage=   	"<div class='row'>"+
														"<div class='card message-card m-1 border-0 bg-transparent' >"+
															"<div class='card-body border p-1  px-3 text-center box-shadow bg-white ' style='background-image: url('"+loaderUrl+"');border-radius: 20px 20px 20px 3px;' >"+
															nameText+replaceImageText +
															"</div>"+
															"<div class='card-footer  border-0 py-1 pr-0 time-tranparent' >"+
																"<span class='float-rigth'><small class='text-muted'>"+today+"</small></span>"+
															"</div>"+
														"</div>"+
													"</div>";
					}
				else
					{
					
					 		incomingMessage=   	"<div class='row'>"+
												"<div class='card message-card m-1 border-0 bg-transparent' >"+
													"<div class='card-body border p-1  px-3 text-center box-shadow bg-white' style='border-radius: 20px 20px 20px 3px;'>"+
													nameText+"<span style='overflow-wrap: break-word'>"+message.message+"</span>" +
													"</div>"+
													"<div class='card-footer  border-0 py-1 pr-0 time-tranparent' >"+
														"<span class='float-rigth'><small class='text-muted'>"+today+"</small></span>"+
													"</div>"+
												"</div>"+
											"</div>";	
					}
				$(".msg_history").append(incomingMessage);
				// scroll bottom
				$('.msg_history').scrollTop($('.msg_history')[0].scrollHeight);
			}
			else
			{
				if ($("#sidebar")[0]){
					var countMessage = $('#'+message.fromId+'OnlineStatusLabel').parent().parent().siblings().children().children(".messageCount").html();
					$('#'+message.fromId+'OnlineStatusLabel').parent().parent().siblings().children().children(".messageCount").html(parseInt(countMessage)+1).show();
				}
				else
				{
					$.toast({text:message.fromEmail+' sent message..',hideAfter: 5000});
				}
				var countMessage = $('#totalCountMessages').html();
				$("#totalCountMessages").show().html(parseInt(countMessage)+1);
				
				stompClient.send("/app/changePersonalMessageStatusNotReaded", {}, JSON.stringify({
					'message' : "",
					'toEmail':"",
					'fromId': message.fromId,
					'messageId':message.messageId,
					'path':null
				}));
			}
		});
		stompClient.subscribe('/user/broker/errors', function(messageOutput) {
		});
	});
}

// Load all personal messages and do stuff with it
$(function(){
	$(document).on('click','.chat_ib',function(){
		var userId = $(this).attr("data-id");
		var isLeader=$(this).attr("data-isleader");
		var type= userId.substr(0, 4);
		// Hide the dropdown based on authorization
		$("#editGroupDetailDropdownButton").hide();
		$("#editMemberDropdownButton").hide();
		if(type=="GROU")
		{
			if(isLeader=="true")
			{
				$("#editMemberDropdownButton").show();
			}
			$("#editGroupDetailDropdownButton").show();
			
			// hide the time status on chat pade
			$("#chatUserLastSeen").hide();
		}
		
		if(type=="USER")
		{
			stopTyping();
			toEmail=$(this).attr("data-email");
			var lastSeen=$(this).children().children().children().children().html();
			$("#chatUserLastSeen").addClass("text-danger").html("").html(lastSeen).show();
			
		}
		
		
		$(".msg_history").html("");
		toId=userId;
		fromId=$("#hiddenTextBoxFromId").val();
		contextPath=$("#hiddenTextBoxContextPath").val();
		
		$(this).parent().parent().addClass("active-chat").siblings().removeClass("active-chat");
		
		// set chat user profile name and when it is last seen
		var name=$(this).children().children().children(".name").html();
		
		$("#chatUserName").html("").html(name);
		$("#editGroupAndUserDropDownButton").attr('data-id',toId);
		stompClient.send("/app/loadChat/"+userId+"/"+fromId, {}, JSON.stringify({
			'from' : null
		}));
		
		$(".mesgs").show("very-slow");
		
		// substract total count
		
		var subNumber = $(this).children().children().children(".messageCount").html();
		var countMessage = $('#totalCountMessages').html();
		var number= parseInt(countMessage)-parseInt(subNumber);
		if(number!=0)
			$('#totalCountMessages').html(number);
		else
			$('#totalCountMessages').html("0").hide();
	});	
	
	// Send message on enter button
	$('#messageSendTextBox').on("keyup", function(e) {
        if (e.keyCode == 13) {
        	var message=$(this).val();
        	if(message.trim()=="")
        		return true;
        	sendMessage(message);
        	$(this).val("");
        	stopTyping();
            return false; 
        }
	});
	
	// Send a message by clicking send message button
 	$("#messageSendButton").click(function(){
		
 		var message=$(this).siblings('input').val();
		if(message.trim()=="")
    		return true;
		if($("#messageSendTextBox").attr("readonly"))
		{
			sendFile();
			$("#messageSendTextBox").val('').attr({'readonly':false});
		}
		else
		{
			sendMessage(message);
		}
		$(this).siblings('input').val("");
		stopTyping();
	});
 	
 	function sendFile()
 	{
 		 	const files =$('#exampleFormControlFile1').prop('files'); 
 		 	const reader = new FileReader();
 		 	var ext = $('#exampleFormControlFile1').val().split('.').pop().toLowerCase();
 		 	if($.inArray(ext, ['gif','png','jpg','jpeg']) != -1) {
 	 		    new Compressor(files[0], {
 			 	    quality: 0.1,
 			 	    success(result) {
 			 	    	reader.readAsDataURL(result);
 			 	    },
 			 	    error(err) {
 			 	      console.log(err.message);
 			 	    },
 			 	  });
 		 	}
 		 	else
	 		{
 		 		reader.readAsDataURL(files[0]);
	 		}
 		 	reader.onload = function (e) {
	 		   stompClient.send("/app/sendPersonalMessage/"+toId, {}, JSON.stringify({
 		 			'message' : null,
 		 			'toEmail':toEmail,
 		 			'fromId':fromId,
 		 			'file':e.target.result
 		 		}));
 		 	}
 		    var tmppath = URL.createObjectURL(files[0]);

 		   	
 		    var ext = $("#exampleFormControlFile1").val().split('.').pop().toLowerCase();
 		    if(ext=="mp4")
			{
				replaceImageText="<video width='320' height='240' controls>"+
				"<source src='"+tmppath+"'>"+
				"</video>";
			}
			else
			{
				replaceImageText="<img class='rounded w-100' src='"+tmppath+"'>";
			} 
 		    
 		    let today= moment(new Date()).tz("Asia/Kolkata").calendar();
 		   var outgoingMessage=   	"<div class='row justify-content-end'>"+
										"<div class='card message-card m-1 border-0 bg-transparent' >"+
											"<div class='card-body border p-1 px-3 text-center  box-shadow' style='background-color:#dcf8c6;border-radius: 20px 20px 3px 20px;' >"+
											replaceImageText +
											"</div>"+
											"<div class='card-footer  border-0 py-1 pl-0 time-tranparent' >"+
												"<span class='float-rigth'><small class='text-muted'>"+today+"</small></span>"+
											"</div>"+
										"</div>"+
									"</div>";
 		   $(".msg_history").append(outgoingMessage);
 	 		// scroll bottom when message send
 	 		$('.msg_history').scrollTop($('.msg_history')[0].scrollHeight);
 		   	
 	}
 	
 	
 	
 	
 	
 	// send message function on chat board
 	function sendMessage(message)
 	{
 		stompClient.send("/app/sendPersonalMessage/"+toId, {}, JSON.stringify({
 			'message' : message,
 			'toEmail':toEmail,
 			'fromId':fromId,
 			'file':null
 		}));
 		let today= moment(new Date()).tz("Asia/Kolkata").calendar();
 		var outgoingMessage=   	"<div class='row justify-content-end'>"+
									"<div class='card message-card m-1 border-0 bg-transparent' >"+
										"<div class='card-body border p-1 px-3  text-center box-shadow' style='background-color:#dcf8c6;border-radius: 20px 20px 3px 20px;' >"+
										"<span style='overflow-wrap: break-word'>"+message+"</span>"+
										"</div>"+
										"<div class='card-footer border-0 py-1 pl-0 time-tranparent' >"+
											"<span class='float-rigth'><small class='text-muted'>"+today+"</small></span>"+
										"</div>"+
									"</div>"+
								"</div>";
 		$(".msg_history").append(outgoingMessage);
 		// scroll bottom when message send
 		$('.msg_history').scrollTop($('.msg_history')[0].scrollHeight);
 	}

 	// reset the file input
 	$("#exampleFormControlFile1").click(function(){
 		this.value = null;
 	});
 	// file select event in personal chat board folder button
 	$("#exampleFormControlFile1").change(function(event){
 		
 		var ext = $(this).val().split('.').pop().toLowerCase();
 		if($.inArray(ext, ['gif','png','jpg','jpeg','mp3','mp4']) == -1) {
 		    alert('Invalid extension! only gif, png, jpg, jpeg, mp3 and mp4 is valid. Thanks!!');
 		    $(this).val('');
 		    return false;
 		}
 		
 		if(this.files[0].size > 10000000) {
 	       alert("Please upload file less than 10MB. Thanks!!");
 	       $(this).val('');
 	       return false;
 		}
 		
 		var name=event.target.files[0].name;
 		$("#messageSendTextBox").val("").val(name).attr({'readonly':true});
 	});
 	
  	// click event on notification button
 	$("#notifivationPopup").click(function(e){
	 	e.preventDefault();
	 	e.stopPropagation();
	 	
 		stompClient.send("/app/changeStatusNotificationsReaded", {}, JSON.stringify({
 		}));
 		
 		
 		// set notification to 0 and hide it
 		$("#totalCountNotification").html("0").hide();
 	});
 	
 	 	
 	
 	
});

// Send friend request

$(".friendRequestSentButton").parent().click(function(){
	var id = $(this).attr("data-id");
	var email=$(this).attr("data-email");
	
	// send notification to the destination user that user sent you message
	stompClient.send("/app/userSendFriendRequest/"+id+"/"+email, {}, JSON.stringify({
		'email' :  null
	}));
	
	$(this).children(".friendRequestSentButton").hide().parent().addClass("disabled");
	$(this).children(".friendRequestSentButton").siblings(".friendRequestSentLabel").show();
	$(this).parent().parent().parent().parent().parent().parent().parent().parent().parent().fadeOut(4000);
	
});

// accept friend request

$(".acceptFriendRequestLink").click(function(){
	var id = $(this).attr("data-id");
	var email=$(this).attr("data-email");

	stompClient.send("/app/acceptFriendRequest/"+id+"/"+email, {}, JSON.stringify({
		'email' : null
	}));
	
	$(this).children(".acceptFriendRequestButton").hide().parent().addClass("disabled");
	$(this).siblings('button').children('.rejectFriendRequestLabel').parent().hide();
	$(this).children(".acceptFriendRequestButton").siblings(".acceptFriendRequestLable").show();
	$(this).parent().parent().parent().parent().parent().parent().parent().parent().parent().fadeOut(4000);
	
});

// accept friend request

$(".rejectFriendRequestLink").click(function(){
	var id = $(this).attr("data-id");
	var email=$(this).attr("data-email");
	stompClient.send("/app/rejectFriendRequest/"+id+"/"+email, {}, JSON.stringify({
		'email' : null
	}));
	
	$(this).children(".rejectFriendRequestButton").hide().parent().addClass("disabled");
	$(this).siblings('button').children('.acceptFriendRequestButton').parent().hide();
	$(this).children(".rejectFriendRequestButton").siblings(".rejectFriendRequestLabel").show();
	$(this).parent().parent().parent().parent().parent().parent().parent().parent().parent().fadeOut(4000);
	
	
});


// unfriend friend request

$(".unfiendRequestLink").click(function(){
	var id = $(this).attr("data-id");
	var email =$(this).attr("data-email");
	stompClient.send("/app/rejectFriendRequest/"+id+"/"+email, {}, JSON.stringify({
		'email' : null
	}));
	
	$(this).children(".unfiendRequestButton").hide().parent().addClass("disabled");
	$(this).siblings('button').children('.unfriendRequestLable').parent().hide();
	$(this).children(".unfiendRequestButton").siblings(".unfriendRequestLable").show();
	$(this).parent().parent().parent().parent().parent().parent().parent().parent().parent().fadeOut(4000);
	
	
});

var onlineFlag=false;
var offlineFlag=false;
// typing status send
$("#messageSendTextBox").on("keyup", function() {
	var type= toId.substr(0, 5);
	if(type=="GROUP")
		return false;
	
	var value = $(this).val().trim().length;
    if(value>0)
	{
    	if(!onlineFlag)
		{
    		stompClient.send("/app/typingNotification/"+toEmail+"/"+true, {}, JSON.stringify({
    			
    		}));
		}
    	onlineFlag=true;
		
    }
    else
	{ 
    	if(onlineFlag)
		{
    		stompClient.send("/app/typingNotification/"+toEmail+"/"+false, {}, JSON.stringify({
    			
    		}));
    		onlineFlag=false;
		}
	}	
});
function stopTyping()
{
	stompClient.send("/app/typingNotification/"+toEmail+"/"+false, {}, JSON.stringify({
		
	}));
	onlineFlag=false;
}

// create group model
$("#createGroupOpenModelButton").click(function(){
	stompClient.send("/app/getfriendList", {}, JSON.stringify({
	}));
});
$("#createGroupForm").submit(function(e) {
    // prevent Default functionality
    e.preventDefault();
    var favorite = [];

    $.each($("input[name='friends']:checked"), function(){            
        favorite.push($(this).val());
    });
   friends= favorite.join(", ");
   var name=$("#groupName").val(); 
   var status=$("#groupStatus").val();
   var leaderId=$("#hiddenTextBoxFromId").val();
   stompClient.send("/app/addGroup", {}, JSON.stringify({
	   'name':name,
	   'status':status,
	   'leaderId':leaderId,
	   'friends':friends
   }));
   $("#createGroupForm")[0].reset();
   $('#createGroupModel').modal('hide');
});


// Edit group member model
$("#editMemberDropdownButton").click(function(){
	var groupId=$(this).parent().attr("data-id");
	$("#hiddenTextGroupIdForModel").val(groupId);
	stompClient.send("/app/getFriendListExcludeGroupMember/"+groupId, {}, JSON.stringify({
	}));
});
$("#editGroupMemberForm").submit(function(e) {
    // prevent Default functionality
    e.preventDefault();
    var favorite = [];

    $.each($("input[name='friends']:checked"), function(){            
        favorite.push($(this).val());
    });
   friends= favorite.join(", ");
   var name=$("#chatUserName").text();
   var leaderId=$("#hiddenTextBoxFromId").val();
   var groupId=$("#hiddenTextGroupIdForModel").val();
   stompClient.send("/app/editGroupMember", {}, JSON.stringify({
	   'name':name,
	   'status':null,
	   'groupId':groupId,
	   'leaderId':leaderId,
	   'friends':friends
   }));
   $(this)[0].reset();
   $('#editGroupMemberModel').modal('hide');
});

$(".deleteMemberTransButton").on("click",function(){

});
$(document).on('click','.deleteMemberTransButton',function(){
	var memberId=$(this).attr("data-id");
	var memberEmail=$(this).attr("data-email")
	var name=$("#chatUserName").text();
	var leaderId=$("#hiddenTextBoxFromId").val();
	var groupId=$("#hiddenTextGroupIdForModel").val();
	stompClient.send("/app/deleteGroupMember", {}, JSON.stringify({
		   'name':name,
		   'status':memberId,
		   'groupId':groupId,
		   'leaderId':leaderId,
		   'friends':memberEmail
	   }));
	$(this).parent().parent().hide("slow");
});


//editGroupDetailDropdownButton
//editGroupProfileModel

//Edit group detail model
$("#editGroupDetailDropdownButton").click(function(){
	var groupId=$(this).parent().attr("data-id");
	$("#hiddenTextGroupIdForModel").val(groupId);
	stompClient.send("/app/getGroupInfo/"+groupId, {}, JSON.stringify({
	}));
});

$("#profileImageFileInputEditGroupDetailModel").change(function(){
	
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
	const files =$(this).prop('files'); 
    var tmppath = URL.createObjectURL(files[0]);
    $("#profileImageEditGroupDetailModel").attr("src",tmppath);
    
});


$("#editGroupDetailForm").submit(function(e) {
    e.preventDefault();
   
   var name=$("#groupNameEditGroupDetailModel").val();
   var leaderId=$("#hiddenTextBoxFromId").val();
   var status=$("#groupStatusEditGroupDetailModel").val();
   var groupId=$("#hiddenTextGroupIdForModel").val();
    const files =$('#profileImageFileInputEditGroupDetailModel').prop('files'); 

   
    
    
    const reader = new FileReader();
 	var ext = $('#profileImageFileInputEditGroupDetailModel').val().split('.').pop().toLowerCase();
 	
 	if(ext!="") {
 			new Compressor(files[0], {
	 	    quality: 0.1,
	 	    success(result) {
	 	    	reader.readAsDataURL(result);
	 	    },
	 	    error(err) {
	 	      console.log(err.message);
	 	    },
	 	  });
 	}
 	else
 	{
 		stompClient.send("/app/editGroupInfo", {}, JSON.stringify({
 		   'name':name,
 		   'status':status,
 		   'groupId':groupId,
 		   'leaderId':leaderId,
 		   'friends':null,
 		   'file':null
 	   }));
 	}
 	reader.onload = function (e) {
 		stompClient.send("/app/editGroupInfo", {}, JSON.stringify({
 		   'name':name,
 		   'status':status,
 		   'groupId':groupId,
 		   'leaderId':leaderId,
 		   'friends':null,
 		   'file':e.target.result
 	   }));
 		
 	}
 	if(files[0] != undefined)
	{
 		 var tmppath = URL.createObjectURL(files[0]);
 		 $("div[data-id="+groupId+"]").siblings().children("img").attr('src',tmppath);	
	}

   $("div[data-id="+groupId+"] .name").html(name+"<i class='fa fa-users pl-2' aria-hidden='true'></i>")
   $("#chatUserName").html(name+"<i class='fa fa-users pl-2' aria-hidden='true'></i>");
   $(this)[0].reset();
   $.toast({text:'Group Detail Updated',hideAfter: 5000});
   $('#editGroupProfileModel').modal('hide');
});

$(".seeProfileButton").click(function(){
	var id = $(this).attr("data-id");
	var type= id.substr(0, 5);
	if(type=="GROUP")
	{
		stompClient.send("/app/getGroupMember/"+id, {}, JSON.stringify({
		}));

	}
});






