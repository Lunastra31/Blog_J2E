<%-- 
    Document   : voteTooLate
    Created on : 28 sept. 2023, 12:46:47
    Author     : Rene
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vote Error:too late</title>
        <link rel="stylesheet" href='<c:url value="/assets/css/style.css"/>'>
        <link rel="stylesheet" href='<c:url value="/assets/css/form.css"/>'>
    </head>
    <body>
        <header>
            <ul class="ul">
                <li><a href="<c:url value='./'/>" class="menu">Accueil</a></li>
                <li><a href="<c:url value='/showAllNews'/>" class="menu">Voir les news</a></li>
                    <c:choose>     
                        <c:when test = "${empty sessionScope.loggedIn}">
                        <li><a href="<c:url value='/login'/>" class="menu">Se connecter</a></li>
                        <li><a href="<c:url value='/logon'/>" class="menu">S'inscrire</a></li>
                    </c:when>
                    <c:otherwise> 
                        <c:if test="${sessionScope.loggedIn}">
                            <li><a href="<c:url value='/myNews'/>" class="menu">Mon profil</a></li>
                            <li><a href="<c:url value='/createNews'/>" class="menu">Créer une News</a></li>
                            <li><a href="<c:url value='/manageProfile'/>" class="menu">Gérer le profil</a></li>
                            <li><a href="<c:url value='/logout'/>" class="menu">Me déconnecter</a></li>                               
                            </c:if>         
                            <c:if test="${sessionScope.admin}">
                            <li><a href="<c:url value='/admin'/>" class="menu">Administrer le site</a></li>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
            </ul>
        </header>
        <main>
            <h1 id="maintitle">${sessionScope.bean.login}Le délai pour voter pour cette news est dépassé</h1>
            <p id="commentmyNews"> Malheuresement , la news a été crée il y'a plus de 15 jours et vous ne pouvez plus voter pour celle ci.</p>
        </main>
        <footer>&copy;Groupe 4 - Propulsé par Java EE</footer>
    </body>
</html>
