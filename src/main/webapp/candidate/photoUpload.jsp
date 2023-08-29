<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Работа мечты</title>
</head>
<body>
<h2>Upload Photo</h2>
<form action="<c:url value='/photoUpload'/>" method="post">
    <div class="checkbox">
        <input type="file" name="file">
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</form>
</body>
</html>