<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ page import="eclipses.EclipsesBean"  %>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList"></jsp:useBean>

<html>
<head>
<link rel="stylesheet" href="themes/blue/style.css" type="text/css" media="print, projection, screen" />
<script src="jquery-latest.js" type="text/javascript"></script>
<script src="jquery.tablesorter.min.js" type="text/javascript"></script>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="hako.css">
<title>JetBrainsを信じよ</title>
</head>

<body>
<h1>統合開発環境の宗教戦争</h1>
<p>Eclipseではなく、JetBrains社のIntelliJ IDEAを使いましょう!!</p>
<table border="1" id="sampleTable" class="tablesorter">
<thead>
<tr>
<th>一覧番号</th>
<th>バージョン</th>
<th>おすすめ度</th>
<th>アイコン</th>
</tr>
</thead>
<tbody>
<%
for(int i = 0; i < list.size(); i++)
{
  EclipsesBean bean = (EclipsesBean)list.get(i);
%>

<tr>
<td><%= bean.getEclipseNo()%></td>
<td><%= bean.getVarsion() %></td>
<td><%= bean.getOsusume()%></td>
<td><img src="images/<%= bean.getGazo()%>" width="247" hight="147"></td><% } %>
</tr>
</tbody>
</table>
<script type="text/javascript">
   $(document).ready(function()
       {
           $("#sampleTable").tablesorter();
       }
   );
</script>
<br>
<p>Photonが一番使いやすかったな！それ以降のEclipseは重すぎ!!コンパイルも遅すぎ!!</p>
</body>
</html>
