<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="header.jsp"></jsp:include>

<div class="container my-4">

	<c:if test="${fn:length(users) lt 1}">
		<div class="row">
			<div class="col-lg-2"></div>
			<div class="col-lg-8">
				<div
					class="alert alert-danger alert-dismissible fade show box-shadow"
					role="alert">
					Sorry you have not any pending request..
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			</div>
		</div>


	</c:if>
	<div class="row">
		<c:set var="count" value="1"></c:set>
		<c:forEach items="${users}" var="user">
			<div class="col-lg-6">
				<div class="card my-1">
					<div class="card-body ">
						<div class="row">
							<div class="col-lg-4 text-center">
								<c:choose>
									<c:when test="${fn:startsWith(user.profileImagePath, 'http')}">
										<img src="${user.profileImagePath}" class="rounded"
											width="100%" id="navbarProfileImage">
									</c:when>
									<c:otherwise>
										<img src="${profileImage}/${user.profileImagePath}"
											class="rounded" width="100%" id="navbarProfileImage">
									</c:otherwise>
								</c:choose>
							</div>
							<div class="col-lg-8">
								<table class="table table-borderless  ">
									<thead class="">
										<tr class="Primary">
											<th>${user.name }</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><i class="fas fa-envelope"></i>&nbsp;&nbsp;&nbsp; <small>${user.email }</small></td>
										</tr>
										<tr>
											<td>
												<p class="card-text text-muted">
													<i class="fas fa-clock"></i><small class="pl-3">${user.timeAgo }</small>
												</p>
											</td>
										</tr>
										<tr>
											<td><button
													class="seeProfileButton btn btn-primary btn-sm"
													data-id="${user.id}">See Profile</button></td>
										</tr>

									</tbody>
								</table>
							</div>
						</div>

					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>



<jsp:include page="footer.jsp"></jsp:include>