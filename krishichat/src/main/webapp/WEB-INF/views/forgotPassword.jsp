<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="row my-4" >
	<div class="col-lg-2"></div>
	<div class="col-lg-8">
		<div class="card">
		<div class="card-header">
			Forgot Password
		</div>
			<div class="card-body">
				<form action="${contextPath}/forgotPassword" method="post">

					<!-- Hidden Email -->
					
					<input type="hidden" name="email" value="${email}" />
					
					
					<!-- Password  -->

					<div class="form-group row">
						<label class="col-lg-3 col-form-lable">Password:</label>
						<div class="col-lg-9">
							<input type="password" name="password" id="password" class="form-control"
								required />
							
						</div>
					</div>
					
					<!-- Confirm Password  -->

					<div class="form-group row">
						<label class="col-lg-3 col-form-lable">Confirm Password:</label>
						<div class="col-lg-9">
							<input type="password" id="confirm_password" name="confirm_password" class="form-control"
								required />
							
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-lg-3 col-form-lable"></label>
						<div class="col-lg-5">
							<button type="submit" class="btn btn-primary">Forgot</button>
						</div>
					</div>
					
				</form>
			</div>
		</div>
	</div>
	<div class="col-lg-2"></div>
</div>
<jsp:include page="footer.jsp"></jsp:include>