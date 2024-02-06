<%-- 
    Document   : succes
    Created on : 25 sept. 2023, 14:04:41
    Author     : Rene
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Succès</title>
        <link rel="stylesheet" href='<c:url value="/assets/css/style.css"/>'>
        <link rel="stylesheet" href='<c:url value="/assets/css/form.css"/>'>
    </head>
    <body>
        <header>
            <%@ include file="/WEB-INF/jspf/menu.jspf"%>
        </header>
        <main>
            <h1 id="maintitle">Changement réussi</h1>
            <p id="commentmyNews">Vous avez changé votre mot de passe !</p>
        </main>
        <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
