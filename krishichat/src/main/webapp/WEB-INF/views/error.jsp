<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"></jsp:include>
<div id="notfound">
	<div class="notfound">
		<div class="notfound-404">
			<h1>Oops!</h1>
		</div>
		<h2>${from}</h2>

		<a href="${contextPath}/home">Go To Homepage</a>


	</div>
</div>
<div class="row">
	<div class="col-lg-1"></div>
	<div class="col-lg-10">
		<p class="text-danger">${exception }</p>
	</div>

</div>
<jsp:include page="footer.jsp"></jsp:include>