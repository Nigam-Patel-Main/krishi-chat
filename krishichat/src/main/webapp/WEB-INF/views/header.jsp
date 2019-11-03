<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<spring:url value="resources/css" var="css" scope="session"></spring:url>
<spring:url value="resources/images" var="image" scope="session"></spring:url>
<spring:url value="resources/profileImage" var="profileImage"
	scope="session"></spring:url>
<spring:url value="resources/shareImage" var="shareImage"
	scope="session"></spring:url>
<spring:url value="resources/js" var="js" scope="session"></spring:url>
<c:set var="contextPath" value="${pageContext.request.contextPath }"
	scope="session"></c:set>

<html>
<head>
<meta charset="utf-8">
<meta content="text/html; charset=UTF-8; X-Content-Type-Options=nosniff"
	http-equiv="Content-Type" />

<title>KRISHI CHAT</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">



<!-- KRISHI CHAT title icon -->
<link rel="icon" href="${image}/chat.png" type="image/x-icon">


<!-- Bootstrap CSS File -->
<link href="${css}/bootstrap.min.css" rel="stylesheet">

<!-- Libraries CSS Files -->

<link href="${css}/jquery.toast.min.css" rel="stylesheet">
<link href="${css}/fontawesome-all.min.css" rel="stylesheet">

<link href="${css}/devfolio.css" rel="stylesheet">

<!-- Main Stylesheet File -->
<link href="${css}/mycss.css" rel="stylesheet">


</head>
<body id="body">
	<div id="header">
		<nav
			class="navbar navbar-expand-lg navbar-light bg-light navigation-bar "
			style="background-color: white !important; box-shadow: 0 1px 5px 0 rgba(0, 0, 0, 0.2), 0 2px 10px 0 rgba(0, 0, 0, 0.19);">
			<a class="navbar-brand"><img src="${image}/chat.png"
				width="40px;"></a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">

				<ul class="navbar-nav " style="margin-top:1px;">
					<li class="nav-item  "><a class="nav-link"
						href="${contextPath}/home"><i class="fa fa-home fa-lg py-1"></i>
					</a></li>

				</ul>


				<c:if test="${userSession!=null}">
					<!-- Hidden field -->
					<input type="hidden" id="contextPath" name=""
						value="${contextPath}">
					<input type="hidden" id="hiddenTextBoxUserId" name=""
						value="${user.id}">

					<ul class="navbar-nav">


						<li class="nav-item"><a class="nav-link"
							href="${contextPath}/chat"> <i
								class="far fa-comment-dots  py-1"></i><sup><span
									class="nav-link badge badge-warning px-2" style="display: none"
									id="totalCountMessages">0</span></sup>
						</a></li>

						<li class="nav-item dropdown "><a
							class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
								<i class="fas fa-user-friends  fa -lg pt-0"></i>
						</a>
							<div class="dropdown-menu">
								<a class="dropdown-item" href="${contextPath}/discoverUser">Search
									People<i class="fa fa-search px-2" aria-hidden="true"></i>
								</a> <a class="dropdown-item"
									href="${contextPath}/friendPendingRequest">Pending Request<i
									class="fas fa-clock px-2"></i>
								</a> <a class="dropdown-item" href="${contextPath}/friendRequest">Friend
									Request<i class="fab fa-get-pocket px-2"></i>
								</a> <a class="dropdown-item" href="${contextPath}/friendList">Friends<i
									class="fas fa-list px-2"></i>
								</a>
							</div></li>
						<sec:authorize access="isAuthenticated()">
							<sec:authorize access="hasAuthority('ADMIN') ">
								<li class="nav-item"><a class="nav-link"
									href="${contextPath}/log"><i class="fas fa-history   py-1"></i></a></li>
								<li class="nav-item"><a class="nav-link"
									href="${contextPath}/showUsers"><i
										class="fas fa-user-lock py-1"></i></a></li>
							</sec:authorize>
						</sec:authorize>
					</ul>
					<ul class="navbar-nav ml-auto .dropdown-menu-right">

						<li class="nav-item"><a class="nav-link"
							id="notifivationPopup" href="#!"><i class="fas fa-bell pt-1"
								id="notificationButton"></i><sup><span
									class="nav-link badge badge-warning px-2" style="display: none"
									id="totalCountNotification">0</span></sup> </a></li>
						<li class="nav-item"></li>
						<li class="nav-item dropdown   form-inline  mr-5 "
							data-display="dynamic"><a
							class="nav-link dropdown-toggle navbar-brand" href="#"
							id="navbarDropdown" role="button" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">
							<c:choose>
									<c:when test="${fn:startsWith(userSession.profileImagePath, 'http')}">
								       <img src="${userSession.profileImagePath}"
								class="rounded" width="30" height="30" id="navbarProfileImage">	
								    </c:when>
									<c:otherwise>
										<img src="${profileImage}/${userSession.profileImagePath}"
								class="rounded" width="30" height="30" id="navbarProfileImage">
									</c:otherwise>
							</c:choose>
							 
						</a>
							<div class="dropdown-menu" aria-labelledby="navbarDropdown">
								<a class="dropdown-item " href="${contextPath}/showProfile"
									title="click to show profile">Profile</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="${contextPath}/logout">Logout</a>
							</div></li>
					</ul>
				</c:if>
				<c:if test="${userSession==null}">
					<ul class="navbar-nav ml-auto mr-2">
						<li class="nav-item"><a class="nav-link"
							href="${contextPath}/login">Login</a></li>
						<li class="nav-item"><a class="nav-link"
							href="${contextPath}/registration"> <i class="fa fa-user"
								aria-hidden="true"></i>&nbsp;Registration
						</a></li>
					</ul>
				</c:if>
			</div>
		</nav>
	</div>
	<div id="main" style="background-color: #FFFFFF !important">
		<div>
			<c:if test="${message!=null}">
				<div class="row">
					<div class=" col-lg-8 mx-auto mt-3">
						<div
							class="alert alert-success alert-dismissible fade show box-shadow"
							role="alert">
							<strong>Message : </strong> ${message}
							<button type="button" class="close" data-dismiss="alert"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
					</div>
				</div>

			</c:if>

			<c:if test="${error!=null}">
				<div class="row ">
					<div class=" col-lg-8 mx-auto mt-3">
						<div
							class="alert alert-danger alert-dismissible fade show box-shadow"
							role="alert">
							<strong>Error :</strong> ${error}
							<button type="button" class="close" data-dismiss="alert"
								aria-label="Close">
								<span aria-hidden="true"> &times; </span>
							</button>
						</div>
					</div>
				</div>

			</c:if>