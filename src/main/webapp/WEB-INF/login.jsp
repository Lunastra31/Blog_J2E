<%-- Document : login Created on : 25 sept. 2023, 14:03:43 Author : Rene --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Connexion</title>
            <link rel="stylesheet" href='<c:url value="/assets/css/style.css"/>'>
            <link rel="stylesheet" href='<c:url value="/assets/css/form.css"/>'>
        </head>

        <body>
            <header>
                <%@ include file="/WEB-INF/jspf/menu.jspf" %>
            </header>
            <main>
                <h1 id="maintitle">Connexion</h1>
                <form action="<c:url value="/login"/>"method="post">
                <div class="containerlogin">
                    <div class="login">
                        <label for="login" class="login">Login</label>
                        <input type="text" id="login" name="login" value="<c:out value="${requestScope.bean.login}"/>">
                        <span class="errors">${requestScope.errors.login.message}</span>
                    </div>
                    <div class="login">
                        <label for="password">Mot de passe</label>
                        <input type="password" id="password" name="password">
                        <span class="errors">${requestScope.errors.password.message}</span>
                    </div>
                    <div>
                        <input class="submit" type="submit" value="Envoyer">
                        <input class="submit" type="reset" value="Annuler">
                    </div>
                </div>
                </form>
            </main>
            <%@ include file="/WEB-INF/jspf/footer.jspf" %>
        </body>

        </html>