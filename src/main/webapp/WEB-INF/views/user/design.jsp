<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        div {
            padding: 10px;
            border: 1px solid black;
        }

        .container {
            height: 500px;
            display: flex;
            flex-direction: row;
            justify-content: flex-end;
            align-items: flex-end;
        }

        .box1 {
            width:200px;
            height: 200px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="box1">박스1</div>
        <div class="box1">박스2</div>
        <div class="box1">박스3</div>
    </div>  
</body>
</html>