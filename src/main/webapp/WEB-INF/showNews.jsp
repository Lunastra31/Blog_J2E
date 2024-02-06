<%-- Document : showNews Created on : 25 sept. 2023, 14:04:14 Author : Rene --%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Ma News</title>
                <link rel="stylesheet" href='<c:url value="/assets/css/style.css"/>'>
                <link rel="stylesheet" href='<c:url value="/assets/css/form.css"/>'>
                <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
            </head>

            <body>
                <header>
                    <%@ include file="/WEB-INF/jspf/menu.jspf" %>
                </header>
                <main>
                    <section>
                        <h1 id="maintitle">Ma News</h1>
                    </section>
                    <div id="showNews">
                        <div class="histo">
                            <div class="divsec">
                                <h2 id="Dix">Titre :
                                    <c:out value="${selectedNews.title_news}" />
                                </h2>
                                <div class="gridnews">
                                    <p class="asubtitle">Créé le ${selectedNews.created_news} par
                                        <c:out value="${selectedNews.user.login}" />
                                    </p>
                                    <div class="voting">
                                        <div id="score">
                                            Score : ${((selectedNews.positive -
                                            selectedNews.negative)/(selectedNews.positive+selectedNews.negative))*100}%
                                        </div>
                                        <c:if test="${not empty sessionScope.bean.login}">
                                            <a href="<c:url value="/Positive?id_news=${selectedNews.id_news}"/>"><button id="thumbup"></button></a>
                                            <a href="<c:url value="/Negative?id_news=${selectedNews.id_news}"/>"><button id="thumbdown"></button></a>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="abody">
                                    <c:out value="${selectedNews.content_news}" />
                                </div>
                                <c:if test="${not empty sessionScope.bean.login}">
                            </div>
                        </div>
                    </div>
                    <div class="containerlogin">
                        <h3>Votre commentaire :</h3>
                        <form action='<c:url value="/showNews"/>' method="post">
                            <input type="hidden" name="id" value="${selectedNews.id_news}" />
                            <div class="login">
                                <label for="author">Auteur</label>
                                <input type="text" id="author" name="author" readonly="readonly" value="<c:out value="${sessionScope.bean.login}" />">
                            </div>
                            <div class="login">
                                <label for="content">Contenu</label>
                                <textarea id="content" name="content" rows="10" column="120"></textarea>
                            </div>
                            <div class="submitcontainer">
                                <input class="submit" type="submit" value="Envoyer">
                                <input class="submit" type="reset" value="Annuler">
                            </div>
                            </c:if>
                    </div>
                    <div id="commentmyNews">
                        <h3>Commentaires : </h3>
                        <c:forEach items="${requestScope.comments}" var="comment">
                            <article class="newscontent">
                                <p class="asubtitle">Écrit le ${comment.created_comment} par
                                    <c:out value="${comment.user.login}" />
                                </p>
                                <div class="abody">
                                    <c:out value="${comment.content_comment}" />
                                </div>
                            </article>
                        </c:forEach>
                        </form>
                    </div>
                </main>
                <%@ include file="/WEB-INF/jspf/footer.jspf" %>
            </body>

            </html>