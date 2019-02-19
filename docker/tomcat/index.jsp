<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: lemen
  Date: 2018/12/25
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>我的页面</title>
  </head>
  <body>
  成功了！

  <%

    try {
      // 加载驱动
      Class.forName("com.mysql.jdbc.Driver");
      // 建立连接
      Connection con = DriverManager.getConnection("jdbc:mysql://172.18.0.2:3306/company","root","mysql");
      // 创建状态
      Statement state = con.createStatement();
      // 查询
      String sql = "select * from employees";
      ResultSet rs = state.executeQuery(sql);
      while (rs.next()) {
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String department = rs.getString("department");
        String email = rs.getString("email");
        out.println("first_name:" + firstName + "last_name:" + lastName + "department:" + department +
        "email:" +  email + "<br>");
      }
      state.close();
      con.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  %>

  </body>
</html>
