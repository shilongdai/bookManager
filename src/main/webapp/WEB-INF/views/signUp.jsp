<%@ page isErrorPage="true"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt"
	uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="v"
	tagdir="/WEB-INF/tags"%><%@ taglib prefix="form"
	uri="http://www.springframework.org/tags/form"%><%@ taglib
	prefix="spring" uri="http://www.springframework.org/tags"%>

<v:main title="Register">
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
	<form:form method="post" class="form-horizontal" modelAttribute="form">
		<div class="form-group">
			<form:label path="username" class="col-sm-2 control-label">Username:</form:label>
			<div class="col-sm-8">
				<form:input path="username" class="form-control"
					placeholder="Username for the user" />
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
			<div class="col-sm-offset-2 col-sm-2">
				<button type="submit" class="btn btn-primary">
					<spring:message code="label.done"></spring:message>
				</button>
			</div>
		</div>

	</form:form>
</v:main>