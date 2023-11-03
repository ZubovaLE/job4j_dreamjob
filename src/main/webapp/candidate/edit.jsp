<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.job4j.models.Candidate" %>
<%@ page import="ru.job4j.store.CsqlStore" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script>
        function validate() {
            if ($('#lastName').val() === '' || $('#firstName').val() === '') {
                alert("Все поля должны быть заполнены");
                return false;
            } else if ($('input[name=gender]:checked').length === 0) {
                alert("Выберите пол");
                return false;
            }
            return true;
        }

        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/dreamjob/cities',
                dataType: 'json',
                success: function (data) {
                    let cities = "";
                    for (let i = 0; i < data.length; i++) {
                        cities += "<option value=" + data[i]['id'] + ">" + data[i]['name'] + "</option>";
                    }
                    $('#city').html(cities);
                }
            })
        });
    </script>
    <title>Работа мечты</title>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate.CandidateBuilder(0, "", "").build();
    if (id != null) {
        candidate = CsqlStore.instOf().findById(Integer.parseInt(id));
    }
%>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новый кандидат.
                <% } else { %>
                Редактирование кандидата.
                <% } %>
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/candidates.do?id=<%=candidate.getId()%>" method="post">
                    <div class="form-group">
                        <label for="lastName">Фамилия</label>
                        <input type="text" class="form-control" name="lastName" id="lastName"
                               value="<%=candidate.getLastName()%>">
                        <label for="firstName">Имя</label><br>
                        <input type="text" class="form-control" name="firstName" id="firstName"
                               value="<%=candidate.getFirstName()%>"><br>
                        <% if (candidate.getGender() == null) { %>
                        <label>Пол</label><br>
                        <input type="radio" name="gender" value="MALE">Мужской<br>
                        <input type="radio" name="gender" value="FEMALE">Женский<br>
                        <% } else {%>
                        <label>Пол</label><br>
                        <input type="radio" name="gender" value="<%=candidate.getGender().toString()%>" checked>
                        <%=candidate.getGender().toString()%><br>
                        <% } %>
                        <label for="city">Место жительства</label>
                        <select name="city" id="city">
                        </select>
                        <% if (candidate.getPhoto() != null) { %>
                        <input type="hidden" name="photo" value="<%=candidate.getPhoto()%>">
                        <% }%>
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="return validate();">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<footer>
    <ul class="nav justify-content-center">
        <li class="nav-item">
            <a href="<%=request.getContextPath()%>">На главную страницу</a>
        </li>
    </ul>
</footer>
</html>
