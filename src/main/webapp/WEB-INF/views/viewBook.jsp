<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib
	prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib
	prefix="v" tagdir="/WEB-INF/tags"%><%@ taglib prefix="form"
	uri="http://www.springframework.org/tags/form"%><%@ taglib
	prefix="spring" uri="http://www.springframework.org/tags"%><%@ taglib
	prefix="sec" uri="http://www.springframework.org/security/tags"%>
<v:main title="View Book">
	<c:url value="/book/${book.id }" var="deleteUrl"></c:url>
	<form:form action="${deleteUrl }" method="delete">
		<div class="btn-group">
			<a class="btn btn-default"
				href='<c:url value="/book"><c:param name="usePrev" value="true"></c:param><c:param name="prevPage" value="true"></c:param></c:url>'><spring:message
					code="label.back"></spring:message></a> <a
				href='<c:url value="/book/edit">
					<c:param name="id">${book.id }</c:param>
	</c:url>'
				class="btn btn-primary"><spring:message code="label.edit"></spring:message></a>
			<button type="submit" class="btn btn-danger">
				<spring:message code="label.delete"></spring:message>
			</button>
		</div>
	</form:form>
	<br>
	<script>
		$(document)
				.ready(
						function() {
							var clientCode = '<c:out value="${book.description}" escapeXml="false"></c:out>';
							var sanitized = html_sanitize(clientCode,
							/* optional */function(url) {
								return url /* rewrite urls if needed */
							},
							/* optional */function(id) {
								return id; /* rewrite ids, names and classes if needed */
							});
							$("#desc").html(sanitized);
						})
	</script>
	<div class="panel panel-info">
		<div class="panel-heading">
			<h2 class="panel-title">
				<spring:message code="label.description"></spring:message>
			</h2>
		</div>
		<div class="panel-body">
			<h2>
				<i><c:out value="${book.title }" default="Unknown Book"></c:out></i>
				<c:out value=" by ${book.author }" default="Unknown"></c:out>
			</h2>
			<div id="desc"></div>
			<div class="table-responsive">
				<table class="table">
					<tbody>
						<sec:authorize access="hasAuthority('admin')">
							<tr>
								<th><spring:message code="label.owner"></spring:message>:</th>
								<td><c:out value="${book.owner.username }"></c:out></td>
							</tr>
						</sec:authorize>
						<tr>
							<th><spring:message code="label.title"></spring:message>:</th>
							<td><c:out value="${book.title }" default="-"></c:out></td>
						</tr>
						<tr>
							<th><spring:message code="label.author"></spring:message>:</th>
							<td><c:out value="${book.author }" default="-"></c:out></td>
						</tr>
						<tr>
							<th>ISBN:</th>
							<td><c:out value="${book.isbn }"></c:out></td>
						</tr>
						<tr>
							<th><spring:message code="label.location"></spring:message>:</th>
							<td><c:out value="${book.location }"></c:out></td>
						</tr>
						<tr>
							<th><spring:message code="label.status"></spring:message>:</th>
							<td><c:choose>
									<c:when test="${book.available }">
										<c:out value="Available"></c:out>
									</c:when>
									<c:otherwise>
										<c:out value="Unavailable"></c:out>
									</c:otherwise>
								</c:choose></td>
						</tr>
						<tr>
							<th><spring:message code="label.published"></spring:message>:</th>
							<td><c:if test="${not empty book.published }">
									<fmt:formatDate value="${book.published }" dateStyle="long"
										var="formatDate" type="date" />
								</c:if> <c:out value="${formatDate }" default="-"></c:out></td>
						</tr>
						<tr>
							<th><spring:message code="label.genre"></spring:message>:</th>
							<td><c:out value="${book.genre }" default="-"></c:out></td>
						</tr>
						<tr>
							<th><spring:message code="label.language"></spring:message>:</th>
							<td><c:out value="${book.lang }" default="-"></c:out></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<a class="btn btn-default"
		href='<c:url value="/book"><c:param name="usePrev" value="true"></c:param><c:param name="prevPage" value="true"></c:param></c:url>'><spring:message code="label.back"></spring:message></a>
</v:main>