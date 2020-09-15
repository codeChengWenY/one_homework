<%@ page import="java.util.List" %>
<%@ page import="com.lagou.edu.pojo.Resume" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Insert title here</title>
</head>
<body>
<form action="/resume/save" method="post">
    <table>
        <tr>
            <td>姓  名：</td>
            <td><input type="text" name="name"></td>
        </tr>
        <tr>
            <td>地 址&nbsp;&nbsp;:</td>
            <td><input type="text" name="address"></td>
        </tr>

        <tr>
            <td>手 机 号:</td>
            <td><input type="text" name="phone"></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" name="提交">
                <input type="reset" name="重置">
        </tr>
    </table>
</form>
</body>
</html>