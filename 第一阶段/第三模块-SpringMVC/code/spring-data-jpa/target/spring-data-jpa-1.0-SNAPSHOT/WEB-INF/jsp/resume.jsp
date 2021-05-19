<%@ page import="java.util.List" %>
<%@ page import="com.lagou.edu.pojo.Resume" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Insert title here</title>
</head>
<body>
<div align="right"><a href="/resume/add" >新增</a></div>
<table border="1" cellspacing="0" cellpadding="0" width="100%" style="align:center;">
    <tr bgcolor="ff9900" style="font-weight:bold;">
        <th>Id</th>
        <th>姓名</th>
        <th>地址</th>
        <th>手机号</th>
        <th width="100" colspan="2">操作</th>
    </tr>
    <%
        //循环显示数据
        List<Resume> resumeList = (List) request.getAttribute("resumeList"); // 取request里面的对象队列
        if (resumeList.size() != 0) {
            for (int i = 0; i < resumeList.size(); i++) {
                pageContext.setAttribute("resumes", resumeList.get(i));
                //保存到页面pageContext里面方便下面进行EL表达式调用
    %>
    <tr>
        <td align="c">${resumes.id }</td>
        <td>${resumes.name }</td>
        <td>${resumes.address }</td>
        <td>${resumes.phone }</td>
        <td align="center"><a href="/resume/update?id=${resumes.id }">修改</a> | <a
                href="/resume/del?id=${resumes.id }" onclick='return confirm("确定要删除吗?")'
        >删除</a></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="6">数据库中没有数据！</td>
    </tr>
    <%
        }
    %>
</table
</body>
</html>