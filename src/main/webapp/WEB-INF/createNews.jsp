<%-- 
    Document   : createNews
    Created on : 25 sept. 2023, 14:02:50
    Author     : Rene
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Création de news</title>
        <link rel="stylesheet" href='<c:url value="/assets/css/style.css"/>'>
        <link rel="stylesheet" href='<c:url value="/assets/css/form.css"/>'>
    </head>
    <body>
        <header>
            <%@ include file="/WEB-INF/jspf/menu.jspf"%>
        </header>
        <main>
            <h1 id="maintitle">Création de news</h1>
            <div class="containerlogin">
            <form action="<c:url value="/createNews" />" method="post">
                <div class="login">
                    <label for="author">Auteur</label>
                    <input type="text" id="author" name="author" readonly
                           value="<c:out value="${sessionScope.bean.login}" />">
                </div>
                <div class="login">
                    <label for="title">Titre</label>
                    <input type="text" id="title" name="title" placeholder="100 caractères max.">
                    <span class="error">${requestScope.errors.title.message}</span>
                </div>
                <div class="login">
                    <label for="content">Contenu</label>
                    <textarea id="content" name="content" cols="100" rows="10"></textarea>
                    <span class="error">${requestScope.errors.content.message}</span>
                </div>
                <div class="submitcontainer">
                    <input class="submit" type="submit" value="Envoyer">
                    <input class="submit" type="reset" value="Annuler">
                </div>
            </form>
            </div>
        </main>
        <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
