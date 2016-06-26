<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib
	prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib
	prefix="v" tagdir="/WEB-INF/tags"%><%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags"%><%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<v:main title="DashBoard">
	<h1>
		<spring:message code="root.header"></spring:message>
	</h1>
	<form action="book" class="input-group col-lg-6">
		<input class="form-control" aria-label="Search Books" type="text"
			name="q" value='<c:out value="${q }"></c:out>'> <input
			type="hidden" name="page" value="0"> <input type="hidden"
			name="size" value="10">
		<div class="input-group-btn">
			<button type="submit" class="btn btn-default">
				<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
				<spring:message code="root.search"></spring:message>
			</button>
			<button type="button" class="btn btn-default dropdown-toggle"
				data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				<span class="caret"></span> <span class="sr-only">Toggle
					Dropdown</span>
			</button>
			<ul class="dropdown-menu pull-right" role="menu">
				<li><a href='<c:url value="/book/add"></c:url>'><span
						class="glyphicon glyphicon-plus" aria-hidden="true"></span> <spring:message
							code="root.add"></spring:message></a></li>
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
						class="glyphicon glyphicon-arrow-left"></span> Logout</a></li>
			</ul>
		</div>
	</form>
	<v:bookTable pageCount="${pageCount }" currentPage="${currentPage }"
		books="${books }" path="/book"></v:bookTable>
</v:main>