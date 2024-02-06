<%-- Document : newPassword Created on : 25 sept. 2023, 14:04:05 Author : Rene --%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Gestion de profil</title>
                <link rel="stylesheet" href='<c:url value="/assets/css/style.css"/>'>
                <link rel="stylesheet" href='<c:url value="/assets/css/form.css"/>'>

            </head>
            <body>
                <header>
                    <%@ include file="/WEB-INF/jspf/menu.jspf" %>
                </header>
                <main>
                    <h1 id="maintitle">Gestion du Profil</h1>
                    <div class="containerlogin">
                        <h2>Changer mon mot de passe :</h2>
                        <form class="<c:if test=" ${empty requestScope.errors}">hidden</c:if>"
                            action="
                            <c:url value="/user/profile" />" method="post">
                            <div class="login">
                                <label for="ancient">Ancien mot de passe</label>
                                <input type="password" id="ancient" name="ancient">
                                <div class="error">${requestScope.errors.ancient.message}</div>
                            </div>
                            <div class="login">
                                <label for="newPwd">Nouveau mot de passe</label>
                                <input type="password" id="newP" name="newP">
                                <div class="error">${requestScope.errors.newP.message}</div>
                            </div>
                            <div class="login">
                                <label for="confirm">Confirmation</label>
                                <input type="password" id="confirm" name="confirm">
                                <div class="error">${requestScope.errors.confirm.message}</div>
                            </div>
                            <div class="submitcontainer">
                                <input class="submit" type="submit" value="Changer">
                                <input class="submit" type="reset" value="Annuler">
                            </div>
                        </form>
                            
                        <h2>Supprimer mon compte :</h2>
                        <p id="warning">Attention : en remplissant les champs ci-dessous et en cliquant sur supprimer,
                            votre compte ainsi que vos articles et vos commentaire seront supprimés !</p>
                        <form action="<c:url value="/myNews" />"method="post">
                        <div class="login">
                            <label for="login">Login</label>
                            <input type="text" id="login" name="login" value="<c:out value="${requestScope.bean.login}" />">
                            <span class="errors">${requestScope.errors.login.message}</span>
                        </div>
                        <div class="login">
                            <label for="password">Mot de passe</label>
                            <input type="password" id="password" name="password">
                            <span class="errors">${requestScope.errors.password.message}</span>
                        </div>
                        <div class="submitcontainer">
                            <input class="submit" type="submit" value="Envoyer">
                            <input class="submit" type="reset" value="Annuler">
                        </div>
                        </form>
                    </div>
                    <div id="showNews">
                    <%@ include file="/WEB-INF/jspf/deleteNews.jspf" %>
                </div>
                </main>
                <%@ include file="/WEB-INF/jspf/footer.jspf" %>
            </body>

            </html>