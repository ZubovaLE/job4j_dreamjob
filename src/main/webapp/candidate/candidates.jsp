<%--
  Created by IntelliJ IDEA.
  User: Любовь
  Date: 07.06.2023
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Работа мечты</title>
</head>
<body>
<div class="container">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить кандидата</a>
            </li>
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/logout.do"><c:out value="${user.name}"/> |
                        Выйти</a>
                </li>
            </c:if>
        </ul>
    </div>
    <div class="container pt-3">
        <div class="row">
            <div class="card" style="width: 100%">
                <div class="card-header">
                    Кандидаты
                </div>
                <div class="card-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">Фамилия</th>
                            <th scope="col">Имя</th>
                            <th scope="col">Пол</th>
                            <th scope="col">Фото</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${candidates}" var="candidate">
                            <tr>
                                <td>
                                    <a href='<c:url value="/candidate/edit.jsp?id=${candidate.id}"/>'
                                       title="Редактировать кандидата">
                                        <i class="fa fa-edit fa-2x"></i>
                                    </a>
                                    <c:out value="${candidate.lastName}"/>
                                    <form action='<c:url value="/candidates.do"/>' method="post">
                                        <input type="hidden" class="form-control" name="id"
                                               value="<c:out value="${candidate.id}"/>">
                                        <input type="hidden" class="form-control" name="isDeleted"
                                               value="true">
                                        <button type="submit" class="btn-danger"
                                                title="Удалить кандидата"><i class="bi bi-trash"></i></button>
                                    </form>
                                </td>
                                <td>
                                    <c:out value="${candidate.firstName}"/>
                                </td>
                                <td>
                                    <c:out value="${candidate.gender}"/>
                                </td>
                                <td>
                                    <form action='<c:url value="/upload"/>' method="get">
                                        <input type="hidden" class="form-control" name="id"
                                               value="<c:out value="${candidate.id}"/>">
                                        <c:choose>
                                            <c:when test="${not empty candidate.photo}">
                                                <img src="<c:url value='/download?photo=${candidate.photo}'/>"
                                                     width="100px" height="100px" alt="фото"/><br>
                                                <button type="submit" class="btn btn-primary">Изменить</button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" class="btn btn-primary">Добавить</button>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                    <c:if test="${not empty candidate.photo}">
                                    <form action="<c:url value='/upload'/>" method="post">
                                        <input type="hidden" class="form-control" name="id"
                                               value="<c:out value="${candidate.id}"/>">
                                        <input type="hidden" class="form-control" name="photo"
                                               value="<c:out value="${candidate.photo}"/>">
                                        <button type="submit" class="btn btn-primary">Удалить</button>
                                        </c:if>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
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