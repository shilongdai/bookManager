<%@ page isErrorPage="true"%><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt"
	uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="v"
	tagdir="/WEB-INF/tags"%><%@ taglib prefix="form"
	uri="http://www.springframework.org/tags/form"%><%@ taglib
	prefix="spring" uri="http://www.springframework.org/tags"%>
<v:main title="Add Book">
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
	<form:form action="${submitTarget }" method="${type }"
		class="form-horizontal" modelAttribute="book">
		<div class="form-group">
			<form:label path="title" class="col-sm-2 control-label">
				<spring:message code="label.title"></spring:message>
			</form:label>
			<div class="col-sm-8">
				<form:input class="form-control" path="title"
					placeholder="Title of the book" />
			</div>
		</div>

		<div class="form-group">
			<form:label path="author" class="col-sm-2 control-label">
				<spring:message code="label.author"></spring:message>
			</form:label>
			<div class="col-sm-8">
				<form:input class="form-control" path="author"
					placeholder="Author of the book" />
			</div>
		</div>

		<div class="form-group">
			<form:label path="isbn" class="col-sm-2 control-label">ISBN</form:label>
			<div class="col-sm-8">
				<form:input path="isbn" class="form-control"
					placeholder="ISBN of the book" />
			</div>
		</div>

		<div class="form-group">
			<form:label path="genre" class="col-sm-2 control-label">
				<spring:message code="label.genre"></spring:message>
			</form:label>
			<div class="col-sm-8">
				<form:select path="genre" class="form-control">
					<c:forEach items="${genre }" var="g">
						<form:option value="${g }">
							${g }
						</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>

		<div class="form-group">
			<form:label path="lang" class="col-sm-2 control-label">
				<spring:message code="label.language"></spring:message>
			</form:label>
			<div class="col-sm-8">
				<form:input path="lang" class="form-control"
					placeholder="Language of the book" />
			</div>
		</div>

		<div class="form-group">
			<form:label path="published" class="col-sm-2 control-label">
				<spring:message code="label.published"></spring:message>
			</form:label>
			<div class="col-sm-8">
				<c:choose>
					<c:when test="${not empty book.published }">
						<fmt:formatDate value="${book.published }" pattern="MM/dd/yyyy"
							var="formatDate" />
					</c:when>
					<c:otherwise>
						<c:set var="formatDate" value=""></c:set>
					</c:otherwise>
				</c:choose>
				<div class="input-group date" data-provide="datepicker">
					<form:input class="form-control" path="published"
						data-date-format="mm/dd/yyyy" placeholder="mm/dd/yyyy"
						readonly="readonly" value="${formatDate }" />
					<div class="input-group-addon">
						<span class="glyphicon glyphicon-th"></span>
					</div>
				</div>
			</div>
		</div>

		<div class="form-group">
			<form:label path="location" class="control-label col-sm-2">
				<spring:message code="label.location"></spring:message>
			</form:label>
			<div class="col-sm-8">
				<form:input path="location" class="form-control" placeholder="Location of the book"/>
			</div>
		</div>

		<div class="form-group">
			<form:label for="editor" path="description"
				class="col-sm-2 control-label">
				<spring:message code="label.description"></spring:message>
			</form:label>
			<div class="col-sm-8">
				<form:textarea rows="4" class="form-control" id="editor"
					path="description"></form:textarea>
			</div>
		</div>


		<div class="form-group">
			<div class="checkbox col-sm-offset-2 col-sm-8">
				<label> <form:checkbox path="available" /> <spring:message
						code="label.available"></spring:message>
				</label>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-2">
				<div class="btn-group">
					<a
						href='<c:url value="/book"><c:param name="usePrev" value="true"></c:param><c:param name="prevPage" value="true"></c:param></c:url>'
						class="btn btn-default"><spring:message code="label.back"></spring:message></a>
					<button type="submit" class="btn btn-primary">
						<spring:message code="label.done"></spring:message>
					</button>
				</div>
			</div>
		</div>

	</form:form>
</v:main>