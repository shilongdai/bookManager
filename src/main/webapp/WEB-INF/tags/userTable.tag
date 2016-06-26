<%@ tag language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" body-content="scriptless"
	display-name="template"%><%@ attribute name="users"
	type="java.util.List" rtexprvalue="true" required="true"%><%@ attribute
	name="pageCount" type="java.lang.Integer" rtexprvalue="true"
	required="true"%><%@ attribute name="currentPage"
	type="java.lang.Integer" rtexprvalue="true" required="true"%><%@ attribute
	name="path" type="java.lang.String" rtexprvalue="true"
	required="true"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>

<div class="table-responsive">
	<table class="table table-hover">
		<thead>
			<tr>
				<th>Username</th>
				<th>User ID</th>
				<th>Usable</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users }" var="user">
				<tr
					onclick="document.location = '<c:url value="/user/${user.id }">
				</c:url>';"
					style="cursor: pointer;">
					<td><c:out value="${user.username }"></c:out></td>
					<td><c:out value="${user.id}"></c:out></td>
					<td><c:out value="${user.accountNonExpired && user.accountNonLocked && user.credentialsNonExpired && user.enabled }"></c:out></td>
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
						href='<c:url value="${path }"><c:param name="page">${currentPage - 1 }</c:param><c:param name="size">10</c:param></c:url>'><span
							aria-hidden="true">&laquo;</span></a></li>
				</c:otherwise>

			</c:choose>
			<c:forEach begin="0" end="${pageCount - 1}" var="i">
				<li <c:if test="${i == currentPage }">class="active"</c:if>><a
					href='<c:url value="${path }"><c:param name="page">${i }</c:param><c:param name="size">10</c:param></c:url>'>${i + 1 }</a></li>
			</c:forEach>

			<c:choose>
				<c:when test="${currentPage == pageCount - 1 }">
					<li class="disabled"><a href="#" aria-label="Next"> <span
							aria-hidden="true">&raquo;</span>
					</a></li>
				</c:when>

				<c:otherwise>
					<li <c:if test="${i == currentPage }">class="active"</c:if>><a
						href='<c:url value="${path }"><c:param name="page">${currentPage + 1 }</c:param><c:param name="size">10</c:param></c:url>'><span
							aria-hidden="true">&raquo;</span></a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</nav>
</div>