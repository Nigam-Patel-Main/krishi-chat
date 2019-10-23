<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp"></jsp:include>
<div class="row m-3">
	<div class="col-lg-2"></div>
	<div class="col-lg-8">
		<div class="card">
			<div class="card-header">Login
				
			</div>
			<div class="card-body">
				<a href="${contextPath}/googlelogin"
					class="btn btn-danger btn-block w-100"><i class="fab fa-google"></i>
					Sign in with <b>Google</b></a>
					<div class="or-seperator"><i>or</i></div>
				<form action="${contextPath}/login" method="post">
					<!-- Email  -->

					<div class="form-group row">
						<label class="col-lg-3 col-form-lable">Email:</label>
						<div class="col-lg-9">
							<input type="text" name="email" class="form-control" required />

						</div>
					</div>

					<!-- Password  -->

					<div class="form-group row">
						<label class="col-lg-3 col-form-lable">Password:</label>
						<div class="col-lg-9">
							<input type="password" name="password" class="form-control"
								required />

						</div>
					</div>
					<div class="form-group row">
						<label class="col-lg-3 col-form-lable"></label>
						<div class="col-lg-5">
							<button type="submit" class="btn btn-primary">Login</button>


						</div>
						<label class="col-lg-4 text-right"><a
							href="${contextPath}/showAddEmail">forgot password?</a></label>
					</div>

				</form>
			</div>
		</div>
	</div>
	<div class="col-lg-2"></div>
</div>
<jsp:include page="footer.jsp"></jsp:include>