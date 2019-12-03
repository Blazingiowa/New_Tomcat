<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ page import="eclipses.EclipsesBean"  %>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList"></jsp:useBean>

<html>
<head>
<meta charset="utf-8">
<title>検索結果</title>
</head>

<body>
<h1>検索結果</h1>
<table border="1">
<tr>
<th>一覧番号</th><th>バージョン</th><th>おすすめ度</th><th>アイコン</th>
</tr>
<%
for(int i = 0; i < list.size(); i++)
{
  EclipsesBean bean = (EclipsesBean)list.get(i);
%>
<tr>
<td><%= bean.getEclipseNo()%></td>
<td><%= bean.getVarsion() %></td>
<td><%= bean.getOsusume()%></td>
<td><%= bean.getGazo()%></td>
</tr>
<% } %>
</table>
<br>
</body>
</html>
