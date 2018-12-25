<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>User management/Add</title></head>
<body>
<form action="/add" method="post">
    First name <input name="firstName" value=""><br>
    Last name <input name="lastName" value=""><br>
    Date of birth <input name="date" value=""><br>
    <input type="submit" name="okButton" value="Ok">
    <input type="submit" name="cancelButton" value="Cancel">
</form>
<c:if test="${requestScope.error != null}">
    <script>
        alert('${requestScope.error}')
    </script>
</c:if>
</body>
</html>