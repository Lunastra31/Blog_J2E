<%-- Document : showAllNews Created on : 25 sept. 2023, 14:06:29 Author : Rene --%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>News</title>
                <link rel="stylesheet" href='<c:url value="/assets/css/style.css"/>'>

            </head>

            <body>
                <header>
                    <%@ include file="/WEB-INF/jspf/menu.jspf" %>
                </header>
                <main>
                    <section>
                        <h1 id="maintitle">Les news</h1>
                    </section>
                    <h2 id="Dix">Voici toutes les news disponibles pour le moment :</h2>
                    <div class="maincontent">
                    <article class="histo">
                        <c:forEach items="${requestScope.news}" var="news" begin="0" end="9">
                        <article class="newscontent">
                            <div class="titleNews">
                                <h2 class="atitle">
                                    <c:out value="${news.title_news}" />
                                </h2>
                                <div class="notation">
                                    <div class="star"></div>
                                    <div id="score">
                                        Score : ${((news.positive - news.negative)/(news.positive+news.negative))*100}%
                                    </div>
                                </div>
                            </div class="buttoncontainer">
                            <div class="abody">
                                <c:out value="${news.content_news}" />
                            </div>
                            <p class="containerbutton">
                                <button>
                                    <a class="more" href="<c:url value="/showNews?id=${news.id_news}" />">
                                    En savoir plus
                                    </a>
                                </button>
                            </p>
                        </article>
                    </c:forEach>
                    </article>
                    </div>
                </main>
                <%@ include file="/WEB-INF/jspf/footer.jspf" %>
            </body>



            </html>