<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<% Enumeration<String> names = request.getAttributeNames();
    while (names.hasMoreElements()){
        String attName = (String)names.nextElement();
        System.out.println("<br/>" + attName);
    }
%>

<table border = "1" width = "100%">
    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th>isExceed</th>
    </tr>

    <c:forEach var = "meal" items = "${requestScope.mealWithExceedList}">
        <tr>
            <td><c:out value = "${meal.getDateTime}"/></td>
            <td><c:out value = "${meal.getDescription}"/></td>
            <td><c:out value = "${meal.getCalories}"/></td>
            <td><c:out value = "${meal.isExceed}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>