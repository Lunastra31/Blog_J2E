<%-- Document : voteError Created on : 28 sept. 2023, 10:22:36 Author : Rene --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Vote Error:Can't vote twice</title>
            <link rel="stylesheet" href='<c:url value="/assets/css/style.css"/>'>
            <link rel="stylesheet" href='<c:url value="/assets/css/form.css"/>'>

        </head>
        <body>
            <header>
                <ul class="ul">
                    <li><a href="<c:url value='./'/>" class="menu">Accueil</a></li>
                    <li><a href="<c:url value='/showAllNews'/>" class="menu">Voir les news</a></li>
                    <c:choose>
                        <c:when test="${empty sessionScope.loggedIn}">
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
                <h1 id="maintitle">Vous avez déja voté pour cette news ${sessionScope.bean.login}</h1>
                <p id="commentmyNews"> Vous ne pouvez pas voter deux fois pour la même news</p>
            </main>
            <footer>&copy;Groupe 4 - Propulsé par Java EE</footer>
        </body>

        </html>