<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealWithExceed" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<%
    List<MealWithExceed> eList = (List<MealWithExceed>)request.getAttribute("mealWithExceedList");
    request.setAttribute("eList", eList);
%>

<table border = "1" width = "100%">
    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th>isExceed</th>
    </tr>

    <c:forEach var = "meal" items="${eList}">
        <c:choose>
        <c:when test="${meal.exceed}">
            <tr class="text" style="color:red; font-size:20px;">
        </c:when>
        <c:otherwise>
            <tr class="text" style="color:green; font-size:20px;">
        </c:otherwise>
        </c:choose>
            <td><c:out value = "${meal.dateTimeAsString}"/></td>
            <td><c:out value = "${meal.description}"/></td>
            <td><c:out value = "${meal.calories}"/></td>
            <td><c:out value = "${meal.exceed}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>