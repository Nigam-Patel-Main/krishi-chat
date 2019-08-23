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
				<form action="${contextPath}/sendforgotPasswordEmailToken" method="post">
					<!-- Email  -->

					<div class="form-group row">
						<label class="col-lg-3 col-form-lable">Email:</label>
						<div class="col-lg-9">
							<input type="email" name="email" class="form-control" required/>
							
						</div>
					</div>

					<div class="form-group row">
						<label class="col-lg-3 col-form-lable"></label>
						<div class="col-lg-5">
							<button type="submit" class="btn btn btn-outline-primary">Send</button>
						</div>
					</div>
					
				</form>
			</div>
		</div>
	</div>
	<div class="col-lg-2"></div>
</div>
<jsp:include page="footer.jsp"></jsp:include>