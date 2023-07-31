<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 29.07.2023
  Time: 23:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Title</title>
</head>
<body>
<h2>Upload Photo</h2>
<form action="<c:url value='/photoUpload'/>" method="post" enctype="multipart/form-data">
    <div class="checkbox">
        <input type="file" name="file">
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</form>
</body>
</html>
