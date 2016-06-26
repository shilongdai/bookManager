<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib
	prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib
	prefix="v" tagdir="/WEB-INF/tags"%><%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<v:main title="Advanced Search">
	<h1>Advanced Search</h1>
	<form action="advancedSearch" class="input-group col-lg-6">
		<input class="form-control" aria-label="Keyword Search Books" type="text"
			name="q" value='<c:out value="${q }"></c:out>'>
		<input type="hidden" name="pageCount" value="0">
		<input type="hidden" name="pageSize" value="10">
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
						class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add Filter</a></li>
				<li><a href='<c:url value="/book"></c:url>'><span
						class="glyphicon glyphicon-zoom-in"></span> Dashboard</a></li>
				<li><a href='<c:url value="/report"></c:url>'><span class="glyphicon glyphicon-list-alt"></span> Report</a></li>
				<li><a href='<c:url value="/logout"></c:url>'><span
						class="glyphicon glyphicon-arrow-left"></span> Logout</a></li>
			</ul>
		</div>
	</form>
	<v:bookTable pageCount="${pageNumber }" currentPage="${currentPage }" path="/advancedSearch" books="${books }"></v:bookTable>
</v:main>