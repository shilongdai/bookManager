<%@ tag language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" body-content="scriptless"
	display-name="template"%><%@ attribute name="title"
	type="java.lang.String" rtexprvalue="true" required="true"%><%@ taglib
	prefix="form" uri="http://www.springframework.org/tags/form"%><%@ taglib
	prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib
	prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib
	prefix="spring" uri="http://www.springframework.org/tags"%><%@ taglib
	prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title><c:out value="${title }"></c:out></title>

<!-- Bootstrap Core CSS -->
<link href='<c:url value="/resources/css/bootstrap.min.css"></c:url>'
	rel="stylesheet">


<!-- Custom CSS -->
<link href='<c:url value="/resources/css/simple-sidebar.css"></c:url>'
	rel="stylesheet">

<link
	href='<c:url value="/resources/css/bootstrap-datepicker3.min.css"></c:url>'
	rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<!-- jQuery -->
<script src='<c:url value="/resources/js/jquery-2.2.4.min.js"></c:url>'></script>

<!-- Bootstrap Core JavaScript -->
<script src='<c:url value="/resources/js/bootstrap.min.js"></c:url>'></script>

<script type="text/javascript"
	src='<c:url value="/resources/js/bootstrap-datepicker.min.js"></c:url>'></script>

<script type="text/javascript" src="//cdn.tinymce.com/4/tinymce.min.js"></script>

<script type="text/javascript"
	src='<c:url value="/resources/js/html-css-sanitizer-minified.js"></c:url>'></script>

<script type="text/javascript">
	tinymce
			.init({
				selector : '#editor',
				content_css : '<c:url value="/resources/css/bootstrap.min.css"></c:url>'
			});
</script>

<script type="text/javascript"
	src='<c:url value="/resources/js/Chart.bundle.min.js"></c:url>'></script>
<script type="text/javascript">
	function logout() {
		$("#logoutForm").submit();
	}
</script>
</head>
<body>

	<div id="wrapper">

		<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li class="sidebar-brand"><a
					href='<c:url value="/books"></c:url>'>Obj. 13516</a></li>
				<li><a href='<c:url value="/book"></c:url>'><span
						class="glyphicon glyphicon-home"></span> Dashboard</a></li>
				<li><a href='<c:url value="/advancedSearch"></c:url>'><span
						class="glyphicon glyphicon-zoom-in"></span> Advanced Search</a></li>
				<li><a href='<c:url value="/report"></c:url>'><span
						class="glyphicon glyphicon-list-alt"></span> Report</a></li>
				<sec:authorize access="hasAuthority('admin')">
					<li><a
						href='<c:url value="/user"><c:param name="page">0</c:param><c:param name="size">10</c:param></c:url>'><span
							class="glyphicon glyphicon-user"></span> Users</a></li>
				</sec:authorize>
				<li><a href='#' onclick="logout()"><span
						class="glyphicon glyphicon-arrow-left"></span> <sec:authorize access="isAnonymous()">Login</sec:authorize><sec:authorize access="isFullyAuthenticated()">Logout</sec:authorize></a></li>

			</ul>
		</div>
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="page-content-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<jsp:doBody></jsp:doBody>
					</div>
				</div>
			</div>
		</div>
		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->

	<c:url value="/logout" var="logoutLink"></c:url>
	<form:form action="${logoutLink }" method="post" id="logoutForm"
		cssStyle="display:none"></form:form>

</body>

</html>

