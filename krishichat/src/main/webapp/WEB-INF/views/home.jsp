<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:include page="header.jsp"></jsp:include>

<!--/ Intro Skew Star /-->
  <div id="home" class="intro route bg-image" style="background-image: url(${image}/intro-bg.jpg)">
    <div class="overlay-itro"></div>
    <div class="intro-content display-table">
      <div class="table-cell">
        <div class="container">
          <!--<p class="display-6 color-d">Hello, world!</p>-->
          <h1 class="intro-title mb-4">Nothin' Says Lovin' Like Chat.</h1>
          <p class="intro-subtitle"><span class="text-slider-items">Nigam Patel,Web Developer</span><strong class="text-slider"></strong></p>
           <c:if test="${userSession!=null}">
           	<p class="pt-5"><a class="btn btn-primary btn js-scroll px-4" href="${contextPath}/discoverUser" role="button">Discover Friends..</a></p>
           </c:if> 
        </div>
      </div>
    </div>
  </div>
 



<jsp:include page="footer.jsp"></jsp:include>