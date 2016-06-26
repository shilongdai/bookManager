<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content="5; URL='<c:url value="/login"></c:url>'" />
<title>Invalid Login</title>
</head>
<body>
<p>Your credential is invalid, redirecting to login page in 5 seconds</p>
<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
</body>
</html>