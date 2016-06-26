<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%><%@ taglib
	prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>Signin</title>

<!-- Bootstrap core CSS -->
<link href='<c:url value="/resources/css/bootstrap.min.css"></c:url>'
	rel="stylesheet">

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<link href='<c:url value="/resources/css/ie-workaround.css"></c:url>'
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href='<c:url value="/resources/css/register.css"></c:url>'
	rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div class="container">

		<form:form class="form-signin" method="post">
			<h2 class="form-signin-heading">Please sign in</h2>
			<label for="username" class="sr-only">Username</label>
			<input type="text" id="username" class="form-control"
				placeholder="Username" name="username" required autofocus>
			<label for="password" class="sr-only">Password</label>
			<input type="password" id="password" class="form-control"
				placeholder="Password" name="password" required>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
				in</button>
			<div>
				<a href='<c:url value="/register"></c:url>'>Register</a>
			</div>
		</form:form>
	</div>
	<!-- /container -->


	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="<c:url value='/resources/js/ie-workaround.js'></c:url>"></script>
</body>
</html>