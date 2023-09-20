<%--
  Created by IntelliJ IDEA.
  User: Professional
  Date: 20.09.2023
  Time: 23:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <title>Registration</title>
</head>
<body>
<div class="container pt-3">
    <div class="card-header">Новый пользователь</div>
</div>
<div class="card-body">
    <form action="<%=request.getContextPath()%>/reg.do" method="post">
        <div class="form-group">
            <label>Почта</label>
            <input type="text" class="form-control" name="email">
            label>Пароль</label>
            <input type="text" class="form-control" name="password">
        </div>
        <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
    </form>
</div>
</body>
</html>
