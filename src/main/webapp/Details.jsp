<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" class="ua.nure.kn.shalamov.usermanagement.User" scope="session"/>
<html>
<head><title>User management/Edit</title></head>
<body>
<form action="/details" method="post">
    <input type="hidden" value="${user.id}" name="id">
    <p>FullName: ${user.fullName}</p>
    <p>Age: ${user.age}</p>
    <input type="submit" name="backButton" value="Back">
</form>
<c:if test="${requestScope.error != null}">
    <script>
        alert('${requestScope.error}')
    </script>
</c:if>
</body>
</html>