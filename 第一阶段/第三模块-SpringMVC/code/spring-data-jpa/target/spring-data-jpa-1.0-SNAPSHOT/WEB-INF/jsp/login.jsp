<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>登录界面</title>
</head>
<body>
<center>
    <form action="/login" method="post">
        <table>
            <tr>
                <td colspan="2">用户表单</td>
            </tr>
            <tr>
                <td>用户名：</td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td>密&nbsp;&nbsp;码:</td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" name="提交">
                    <input type="reset" name="重置">
            </tr>
        </table>
    </form>
</center>


</body>
</html>