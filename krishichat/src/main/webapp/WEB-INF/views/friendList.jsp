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
					You not have any friend...
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
								<img src="${profileImage}/${user.profileImagePath}"
									class="img rounded img-fluid" />
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
											<td></td>
										</tr>
										<tr>
											<td><button
													class="seeProfileButton btn btn-primary btn-sm"
													data-id="${user.id}">See Profile</button>
												<button class="btn btn-primary  btn-sm unfiendRequestLink"
													data-id="${user.id}" data-email="${user.email}">
													<i class="py-1 unfriendRequestLable" style="display: none">&nbsp;Success</i><span
														class=" py-1 unfiendRequestButton">Unfriend</span>
												</button></td>
										</tr>

									</tbody>
								</table>
							</div>
						</div>

					</div>
				</div>
			</div>
			<c:set var="count" value="${count+1}"></c:set>

		</c:forEach>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>