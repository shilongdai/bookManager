<%@ tag language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" body-content="scriptless"
	display-name="template"%><%@ attribute name="books"
	type="java.util.List" rtexprvalue="true" required="true"%><%@ attribute
	name="pageCount" type="java.lang.Integer" rtexprvalue="true"
	required="true"%><%@ attribute name="currentPage"
	type="java.lang.Integer" rtexprvalue="true" required="true"%><%@ attribute
	name="path" type="java.lang.String" rtexprvalue="true"
	required="true"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags"%><%@ taglib
	prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="table-responsive">
	<table class="table table-hover">
		<thead>
			<tr>
				<th><spring:message code="label.title"></spring:message></th>
				<th><spring:message code="label.genre"></spring:message></th>
				<th><spring:message code="label.author"></spring:message></th>
				<th><spring:message code="label.status"></spring:message></th>
				<sec:authorize access="hasAuthority('admin')">
					<th><spring:message code="label.owner"></spring:message></th>
				</sec:authorize>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${books }" var="book">
				<tr
					onclick="document.location = '<c:url value="/book/${book.id }">
				</c:url>';"
					style="cursor: pointer;">
					<td><c:out value="${book.title }" default='----'></c:out></td>
					<td><c:out value="${book.genre }" default='----'></c:out></td>
					<td><c:out value="${book.author }" default="None"></c:out></td>
					<td><c:choose>
							<c:when test="${book.available }">
								<spring:message code="table.statusAvailable"></spring:message>
							</c:when>
							<c:otherwise>
								<spring:message code="table.statusUnavailable"></spring:message>
							</c:otherwise>
						</c:choose></td>
					<sec:authorize access="hasAuthority('admin')"><td><c:out value="${book.owner.username }"></c:out></td></sec:authorize>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="text-center">
	<nav>
		<ul class="pagination">
			<c:choose>
				<c:when test="${currentPage == 0 }">
					<li class="disabled"><a href="#"><span aria-hidden="true">&laquo;</span></a></li>
				</c:when>
				<c:otherwise>
					<li><a
						href='<c:url value="${path }"><c:param name="page">${currentPage - 1 }</c:param><c:param name="size">10</c:param><c:param name="usePrev" value=""></c:param></c:url>'><span
							aria-hidden="true">&laquo;</span></a></li>
				</c:otherwise>

			</c:choose>
			<c:forEach begin="0" end="${pageCount - 1}" var="i">
				<li <c:if test="${i == currentPage }">class="active"</c:if>><a
					href='<c:url value="${path }"><c:param name="page">${i }</c:param><c:param name="size">10</c:param><c:param name="usePrev" value=""></c:param></c:url>'>${i + 1 }</a></li>
			</c:forEach>

			<c:choose>
				<c:when test="${currentPage == pageCount - 1 }">
					<li class="disabled"><a href="#" aria-label="Next"> <span
							aria-hidden="true">&raquo;</span>
					</a></li>
				</c:when>

				<c:otherwise>
					<li <c:if test="${i == currentPage }">class="active"</c:if>><a
						href='<c:url value="${path }"><c:param name="page">${currentPage + 1 }</c:param><c:param name="size">10</c:param><c:param name="usePrev" value=""></c:param></c:url>'><span
							aria-hidden="true">&raquo;</span></a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</nav>
</div>


