<jsp:include page="/WEB-INF/view/partials/header.jsp"></jsp:include>
<h2>Error!</h2>
<p>That record doesn't exist!</p>
<a href="${empty redirect_url ? '/' : redirect_url}">Okay</a>
<jsp:include page="/WEB-INF/view/partials/footer.jsp"></jsp:include>
