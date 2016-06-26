<%@ page isErrorPage="true"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt"
	uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="v"
	tagdir="/WEB-INF/tags"%><%@ taglib prefix="form"
	uri="http://www.springframework.org/tags/form"%><%@ taglib
	prefix="spring" uri="http://www.springframework.org/tags"%>
<v:main title="User Form">
	<c:forEach items="${errors }" var="e">
		<div class="alert alert-danger alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>Error</strong>
			<c:out value="${e }"></c:out>
		</div>
	</c:forEach>
	
	<c:url value="${target }" var="submitTarget"></c:url>
	<c:if test="${method.equals('put') }">
		<form:form action="${submitTarget }" method="delete">
			<div class="form-group">
				<div class="col-sm-offset-2">
					<button type="submit" class="btn btn-danger">Delete</button>
				</div>
			</div>
		</form:form>
	</c:if>
	<form:form action="${submitTarget }" method="${method }"
		class="form-horizontal" modelAttribute="form">
		<div class="form-group">
			<form:label path="username" class="col-sm-2 control-label">Username:</form:label>
			<div class="col-sm-8">
				<c:choose>
					<c:when test="${not canEditUsername }">
						<form:input path="username" class="form-control"
							placeholder="Username for the user" readonly="true" />
					</c:when>
					<c:otherwise>
						<form:input path="username" class="form-control"
							placeholder="Username for the user" />
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<div class="form-group">
			<form:label path="password" class="col-sm-2 control-label">Password:</form:label>
			<div class="col-sm-8">
				<form:password path="password" class="form-control"
					placeholder="Password of user" />
			</div>
		</div>
		
		<div class="form-group">
			<div class="checkbox col-sm-offset-2 col-sm-8">
				<label> <form:checkbox path="enabled"/> Enabled
				</label>
			</div>
		</div>

		<div class="form-group">
			<div class="checkbox col-sm-offset-2 col-sm-8">
				<label> <form:checkbox path="accountNonExpired"/> Not Expired
				</label>
			</div>
		</div>
		
		<div class="form-group">
			<div class="checkbox col-sm-offset-2 col-sm-8">
				<label> <form:checkbox path="accountNonLocked"/> Not Locked
				</label>
			</div>
		</div>

		<div class="form-group">
			<div class="checkbox col-sm-offset-2 col-sm-8">
				<label> <form:checkbox path="credentialsNonExpired"/> Password Not Expired
				</label>
			</div>
		</div>


		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-2">
				<div class="btn-group">
					<a
						href='<c:url value="/user"><c:param name="page" value="0"></c:param><c:param name="size" value="10"></c:param></c:url>'
						class="btn btn-default"><spring:message code="label.back"></spring:message></a>
					<button type="submit" class="btn btn-primary">
						<spring:message code="label.done"></spring:message>
					</button>
				</div>
			</div>
		</div>

	</form:form>
</v:main>