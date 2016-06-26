<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib
	prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib
	prefix="v" tagdir="/WEB-INF/tags"%>

<v:main title="Report">
	<div class="text-center col-sm-8 col-sm-offset-2">
		<div class="table-responsive">
			<table class="table">
				<tbody>
					<tr>
						<th>Total:</th>
						<td><c:out value="${report.total }"></c:out></td>
					</tr>
					<tr>
						<th>Genres Present:</th>
						<td><c:out value="${report.categories }"></c:out></td>
					</tr>
					<tr>
						<th>Authors Present:</th>
						<td><c:out value="${report.authors }"></c:out></td>
					</tr>
					<tr>
						<th>Languages Present:</th>
						<td><c:out value="${report.languages }"></c:out></td>
					</tr>
					<tr>
						<th>Percent Available:</th>
						<td><c:out value="${report.available} %"></c:out></td>
					</tr>
				</tbody>
			</table>
		</div>
		<hr>
		<div class="table-responsive">
			<table class="table">
				<caption>Category Overview</caption>
				<tbody>
					<c:forEach items="${genres }" var="i">
						<tr>
							<th><c:out value="${i.key }:"></c:out></th>
							<td><c:out value="${i.value }"></c:out></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<canvas id="genreDesc"></canvas>
		<script type="text/javascript">
			$(document).ready(function() {
				var polarChart = $("#genreDesc");
				var data = {
					    datasets: [{
					        data: 
					            ${genreData}
					        ,
					        backgroundColor: ${colours},
					        label: 'Genres' // for legend
					    }],
					    labels: ${genreString}
					};
				new Chart(polarChart, {
				    data: data,
				    type: "polarArea",
				    options: {
				        
				    }
				});
			})
		</script>
	</div>
	
</v:main>