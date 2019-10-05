</div>
</div>
<div id="footer" style="display:none">
	<footer class="mb-0 py-4 "
		style="box-shadow: 0 1px 5px 0 rgba(0, 0, 0, 0.2), 0 2px 10px 0 rgba(0, 0, 0, 0.19);background-color:#FFFFFF !important">
		<div class="container text-center">
			<small style="color:black">Copyright &copy; Nigam Patel, Agricultural Information Technology,
				AAU Anand</small>
		</div>
	</footer>
</div>
  <a href="#" class="back-to-top"><i class="fa fa-chevron-up"></i></a>
<div id="preloader"></div>

<!-- Hidden field -->
<input type="hidden" id="hiddenTextBoxContextPath" name=""
	value="${shareImage}">
<input type="hidden" id="hiddenTextBoxProfileImage" name=""
	value="${profileImage}">	
	

<!-- JavaScript Libraries -->
<script  src="${js}/jquery.min.js" type="text/javascript"></script>
<script  src="${js}/popper.min.js" type="text/javascript"></script>
<script  src="${js}/bootstrap.min.js" type="text/javascript"></script>
<script  src="${js}/jquery.toast.min.js" type="text/javascript"></script>
<script  src="${js}/moment.min.js" type="text/javascript"></script>
<script  src="${js}/moment-timezone-with-data.js" type="text/javascript"></script>
<script  src="${js}/stomp.js" type="text/javascript"> </script>
<script  src="${js}/sockjs.js" type="text/javascript"></script>

<!-- Template Main Javascript File -->
<script  src="${js}/myjs.js" type="text/javascript"></script>
<script  src="${js}/mychat.js" type="text/javascript"></script>
<script  src="${js}/devfolio.js" type="text/javascript"></script>


<!-- Model parts -->

<!-- open personal profile model -->

<div class="modal fade" id="userProfileModel">
	<div class="modal-dialog">
		<div class="modal-content">

			<!-- Modal Header -->
			<div class="modal-header">
				<h4 class="modal-title">Profile</h4>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>

			<!-- Modal body -->
			<div class="modal-body">
				<div class="row">
					<div class="col-md-4 text-center">
						<span hidden id="profileImageContextPath">${profileImage}/</span>
						<img src="data:,"
							id="userProfileProfileImagePath"
							class="mx-auto img-fluid img-thumbnail d-block" alt="avatar">
					</div>
					<div class="col-md-8">
						<h5 id="userProfileName"></h5>
						<p style="color: #999;">
							<small id="userProfileAddress"></small><i
								class="fas fa-map-marker-alt px-3"></i>
						</p>
						<h6>
							<i class="fas fa-envelope"></i><span id="userProfileEmail"
								class="px-3"></span>
						</h6>
						<h6>
							<i class="fas fa-birthday-cake" ></i><span class="px-3" id="userProfileBirthdate"></span>

						</h6>
						<h6>
							<i class="fas fa-user"></i><span class="px-3"
								id="userProfileGender"></span>
						</h6>
						<h6>
							<i class="far fa-comment-alt"></i><span class="px-3"
								id="userProfileStatus"></span>
						</h6>

					</div>
				</div>
			</div>

			<!-- Modal footer -->
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
			</div>

		</div>
	</div>
</div>
</body>
</html>