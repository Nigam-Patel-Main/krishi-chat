<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="header.jsp"></jsp:include>
<link href="${css}/dataTables.bootstrap4.min.css" rel="stylesheet">
<div class="container my-4">
	<div class="row">
		<c:set var="count" value="1"></c:set>
		<div class="table-responsive">

			<table class="table table-bordered table-hover nowrap" id="logTable"
				style="width: 100%">
				<thead>
					<tr>
						<th>No</th>
						<th>Name</th>
						<th>Email</th>
						<th>Login Time</th>
						<th>Logout Time</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${logs}" var="log">
						<tr>
							<td>${count }</td>
							<td>${log.name}</td>
							<td>${log.email}</td>
							<td><fmt:formatDate value="${log.loginTime}" pattern="hh:mm a dd-MM-yyyy " /></td>
							<td><fmt:formatDate value="${log.logoutTime}" pattern="hh:mm a dd-MM-yyyy " /></td>
						</tr>
						<c:set var="count" value="${count+1}"></c:set>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
<script src="${js}/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${js}/dataTables.bootstrap4.min.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		$('#logTable').DataTable();
	});
</script>