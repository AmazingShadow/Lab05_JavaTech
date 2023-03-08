<%@ page import="pojo.Product" %>
<%@ page import="dao.ProductDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    List<Product> productList = ProductDAO.getInstance().getAll();
    String message = "";
    String name = "";
    String price = "";
    String check = (String) session.getAttribute("check");

    if (request.getParameter("method") != null) {
        if (check == null || check.trim().isEmpty()) {
            name = request.getParameter("name");
        } else {
            name = check;
        }
        price = request.getParameter("price");
        if (name ==  null || name.trim().isEmpty()) {
            message = "Vui lòng nhập tên sản phẩm";
            name = check;
        } else if (price == null || price.trim().isEmpty()) {
            message = "Vui lòng nhập giá trị sản phẩm";
        }
    } else {
        name = "";
        price = "";
    }

    String account = (String) session.getAttribute("account");
    String[] temp = account.split(":");
    String username = temp[0];

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Danh sách sản phẩm</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body style="background-color: #f8f8f8">

<div class="container-fluid p-5">
    <div class="row mb-5">
        <div class="col-md-6">
            <h3>Product Management</h3>
        </div>
        <div class="col-md-6 text-right">
            Xin chào <span class="text-danger"><%= username %></span> | <a href="/login?message=logout">Logout</a>
        </div>
    </div>
    <div class="row rounded border p-3">
        <div class="col-md-4">
            <h4 class="text-success">Thêm sản phẩm mới</h4>
            <form class="mt-3" method="post">
                <div class="form-group">
                    <label for="product-name">Tên sản phẩm</label>
                    <input value="<%= name %>" class="form-control" type="text" placeholder="Nhập tên sản phẩm" id="product-name" name="name">
                </div>
                <div class="form-group">
                    <label for="price">Giá sản phẩm</label>
                    <input class="form-control" type="number" placeholder="Nhập giá bán" id="price" name="price">
                </div>
                <div class="form-group">
                    <button class="btn btn-success mr-2">Thêm sản phẩm</button>
                </div>
                <% if (message.length() > 0) { %>
                <div class="alert alert-danger">
                    <%= message %>
                </div>
                <% } %>
            </form>
        </div>
        <div class="col-md-8">
            <h4 class="text-success">Danh sách sản phẩm</h4>
            <p>Chọn một sản phẩm cụ thể để xem chi tiết</p>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>STT</th>
                    <th>Tên sản phẩm</th>
                    <th>Giá</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <% for (Product product : productList) { %>
                <tr>
                    <td><%= product.getId() %></td>
                    <td><a href="#"><%= product.getProductName() %></a></td>
                    <td><%= "$" + product.getPrice() %></td>
                    <td>
                        <a href="<%= product.getId() %>" class="remove" data-bs-toggle="modal" data-bs-target="#exampleModal">Xóa</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="exampleModalLabel">Xoá sản phẩm</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Bạn có chắc chắn muốn xoá?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger delete" data-bs-dismiss="modal">Xoá</button>
            </div>
        </div>
    </div>
</div>
<form action="/?delete=success" method="POST" class="formDelete" style="display: none">
    <input name="id" value="" class="inputValue">
    <button type="submit">Bấm vào đây</button>
</form>

<script>
    // Add the following code if you want the name of the file appear on select
    $(".custom-file-input").on("change", function() {
        var fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    })


    let remove = document.querySelectorAll(".remove")
    let formDelete = document.querySelector(".formDelete");
    let deleteStudent = document.querySelector(".delete");
    let inputValue = document.querySelector(".inputValue");

    remove.forEach((tab) => {
        tab.onclick = function () {
            let data = tab.href
            data = data.split("/")
            inputValue.value = data[data.length-1]
        }
    })

    deleteStudent.onclick = function() {
        formDelete.submit()
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
