<%@ page import="ru.job4j.models.Candidate" %>
<%@ page import="ru.job4j.store.CsqlStore" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script>
        function validate() {
            if ($('#file').val() === '') {
                alert("Выберите файл");
                return false;
            }
            return true;
        }
    </script>
    <title>Photo uploading</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate.CandidateBuilder(0, "", "", null).build();
    if (id != null) {
        candidate = CsqlStore.instOf().findById(Integer.parseInt(id));
    }
%>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header" style="background-color: cadetblue; text-align: center">
                <label style="font-size: large; font-family: Arial,serif;">Добавить фото</label>
            </div>
            <div class="card-body" style="background-color: lightblue">
                <form action="<%=request.getContextPath()%>/upload?id=<%=candidate.getId()%>" method="post"
                      enctype="multipart/form-data">
                    <input type="file" class="form-group" name="file" id="file">
                    <button type="submit" class="btn btn-primary" onclick="return validate();">Добавить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<footer>
    <ul class="nav justify-content-center">
        <li class="nav-item">
            <a href="<%=request.getContextPath()%>/index.do">На главную страницу</a>
        </li>
    </ul>
</footer>
</html>