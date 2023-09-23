<%@ page import="ru.job4j.models.Candidate" %>
<%@ page import="ru.job4j.store.CsqlStore" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <title>PhotoUploading</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate.CandidateBuilder(0, "", "").build();
    if (id != null) {
        candidate = CsqlStore.instOf().findById(Integer.parseInt(id));
    }
%>
<div class="container">
    <div class="card">
        <div class="card-header" style="background-color: cadetblue; text-align: center;">
            <label style="font-size: large; font-family: Arial,serif;">Добавить фото</label>
        </div>
        <div class="card-body" style="background-color: lightskyblue">
            <form action="<%=request.getContextPath()%>/upload?id=<%=candidate.getId()%>" method="post"
                  enctype="multipart/form-data">
                <input type="file" class="form-group" name="file">
                <button type="submit" class="btn btn-primary">Добавить</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>