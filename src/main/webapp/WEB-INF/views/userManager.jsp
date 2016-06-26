<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib
	prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib
	prefix="v" tagdir="/WEB-INF/tags"%><%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags"%>

<v:main title="User Manager">
	<div class="btn-group">
		<button type="button" class="btn btn-default dropdown-toggle"
			data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			Add <span class="caret"></span>
		</button>
		<ul class="dropdown-menu">
			<li><a href='<c:url value="/user/newUser"></c:url>'>Add User</a></li>
			<li><a href='<c:url value="/user/newAdmin"></c:url>'>Add Admin</a></li>
		</ul>
	</div>
	<br>
	<c:url value="/user" var="path"></c:url>
	<v:userTable pageCount="${pageCount }" currentPage="${currentPage }"
		path="${path }" users="${users }"></v:userTable>
</v:main>