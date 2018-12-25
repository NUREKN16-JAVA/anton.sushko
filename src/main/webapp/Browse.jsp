<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>User management/Browse</title></head>
<body>
<form method="post" action="/browse">
    <table id=”userTable” border=”1”>
        <tr>
            <th></th>
            <th>First name</th>
            <th>Last name</th>
            <th>Date of birth</th>
        </tr>
        <c:forEach var="user" items="${sessionScope.users}">
            <tr>
                <td><input type="radio" name="id" id="id" value="${user.id}"></td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.dateOfBirth}</td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" name="addButton" value="Add">
    <input type="submit" name="deleteButton" value="Delete" id="deleteButton">
    <input type="submit" name="editButton" value="Edit">
    <input type="submit" name="detailsButton" value="Details">
</form>
<c:if test="${requestScope.error != null}">
    <script>
        alert('${requestScope.error}')
    </script>
</c:if>
<script>
    var deleteButton = document.getElementById("deleteButton");
    deleteButton.addEventListener("click", function (e) {
        if (!isCheck("id")) {
            e.preventDefault();
            alert("You should choose user");
            return;
        }
        if (!confirm("Do you really want to delete this user?")) {
            e.preventDefault();
        }
    });
    function isCheck(name) {
        return document.querySelector('input[name="' + name + '"]:checked');
    }
</script>
</body>
</html>