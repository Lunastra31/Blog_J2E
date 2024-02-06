<%-- 
    Document   : banned
    Created on : 26 sept. 2023, 14:55:36
    Author     : Rene
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sécurité</title>
        <link rel="stylesheet" href='<c:url value="/assets/css/style.css"/>'>
        <link rel="stylesheet" href='<c:url value="/assets/css/form.css"/>'>
    </head>
    <body>
        <header>
            <%@ include file="/WEB-INF/jspf/menu.jspf"%>
        </header>
        <main>
            <h1 id="maintitle">Malheureusement vous avez été banni ${sessionScope.bean.login}</h1>
            <p id="commentmyNews"">Si vous êtes sage, vous serez réinscrit sur la liste du père Nowel (ou pas). <br> Signé le père Fwettard </p>
        </main>
            <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    </body>

</html>
