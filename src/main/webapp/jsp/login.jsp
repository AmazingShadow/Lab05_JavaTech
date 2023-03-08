<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String message = request.getParameter("login");
    String notice = "";
    if (message != null && message.equals("fail")) {
        notice = "Sai tài khoản hoặc mật khẩu";
    }

    String email = "";
    String password = "";

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("account")) {
                String cookieValue = cookie.getValue();
                String[] part = cookieValue.split(":");
                email = part[0];
                password = part[1];
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login Page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
            <h3 class="text-center text-secondary mt-5 mb-3">User Login</h3>
            <form class="border rounded w-100 mb-5 mx-auto px-3 pt-3 bg-light" action="/login" method="POST">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input id="username" value="<%= email %>" name="username" type="text" class="form-control" placeholder="Username">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input id="password" value="<%= password %>" name="password" type="password" class="form-control" placeholder="Password">
                </div>
                <div class="form-group form-check">
                    <input type="checkbox" class="form-check-input" id="exampleCheck1" name="saveAccount">
                    <label class="form-check-label" for="exampleCheck1">Check me out</label>
                </div>
                <div class="form-group">
                    <button class="btn btn-success px-5">Login</button>
                </div>
                <div class="form-group">
                    <a href="/register" class="btn btn-primary px-5">Register</a>
                </div>
                <div class="form-group">
                    <p>Forgot password? <a href="#">Click here</a></p>
                </div>
            </form>
            <% if (!notice.equals("")) { %>
            <div class="alert alert-success" role="alert">
                <%= notice %>
            </div>
            <% } %>
        </div>
    </div>
</div>

</body>
</html>
