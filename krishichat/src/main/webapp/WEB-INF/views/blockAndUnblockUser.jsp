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
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${users}" var="user">
						<tr>
							<td>${count }</td>
							<td>${user.name}</td>
							<td>${user.email}</td>
							<td class="text-center"><c:if test="${user.isBlock==true }">
									<a href="unblockUser?id=${user.id}" class="btn btn-outline-success">Unblock</a>
								</c:if> <c:if test="${user.isBlock==false }">
									<a href="blockUser?id=${user.id}" class="btn btn-outline-danger">Block</a>
								</c:if></td>
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