<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"></jsp:include>

<div class="row m-4">
	<div class="col-lg-2"></div>
	<div class="col-lg-8">
		<div class="card">
			<div class="card-header ">Registration</div>
			<div class="card-body">
				<form:form action="${contextPath}/registration"
					 modelAttribute="user"
					class="form-horizontal" method="post">

					<!-- Name  -->

					<div class="form-group row">
						<label class="col-lg-3 col-form-lable">Name:</label>
						<div class="col-lg-9">
							<form:input path="name"  required="true" class="form-control" />
							<form:errors path="name" cssClass="text-danger"></form:errors>
						</div>
					</div>
					
					<!-- Email  -->

					<div class="form-group row">
						<label class="col-lg-3 col-form-lable">Email:</label>
						<div class="col-lg-9">
							<form:input path="email" class="form-control" required="true" />
							<form:errors path="email" cssClass="text-danger"></form:errors>
						</div>
					</div>

					<!-- Password  -->

					<div class="form-group row">
						<label class="col-lg-3 col-form-lable">Password:</label>
						<div class="col-lg-9">
							<form:password path="password" class="form-control"
								required="true" />
							<form:errors path="password" cssClass="text-danger"></form:errors>
						</div>
					</div>
					
					<!-- Confirm Password  -->

					<div class="form-group row">
						<label class="col-lg-3 col-form-lable">Confirm Password:</label>
						<div class="col-lg-9">
							<input type="password" name="confirm_password" id="confirm_password" class="form-control"
								required />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-lg-3 col-form-label"></label>
						<div class="col-lg-9">
							<button type="submit" class="btn btn-primary">Submit</button>
						</div>
					</div>

				</form:form>
			</div>
		</div>
	</div>
	<div class="col-lg-2"></div>
</div>
<jsp:include page="footer.jsp"></jsp:include>