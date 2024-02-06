<%-- Document : index Created on : 26 sept. 2023, 08:47:33 Author : stag --%>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

            <!DOCTYPE html>
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Accueil</title>
                <link rel="stylesheet" href='<c:url value="/assets/css/style.css"/>'>
            </head>

            <body>
                <header>
                    <%@ include file="/WEB-INF/jspf/menu.jspf" %>
                </header>
                <main>
                    <section  class="DixIndex">
                        <div class="divsec">
                            <h1 id="maintitle">Oh My Blog de ${sessionScope.bean.login.split("@")[0]}</h1>
                            <h3></h3>
                        </div>
                    </section>
                    <div class="maincontent">
                        <article class="histo">
                            <h2 id="Dix">Nos 10 dernières News</h2>
                            <c:forEach items="${requestScope.news}" var="news" begin="0" end="9">
                                <article class="newscontent">
                                    <div class="titleNews">
                                        <h2 class="atitle">
                                            <c:out value="${news.title_news}" />
                                        </h2>
                                        <div class="notation">
                                            <div class="star"></div>
                                            <div id="score">Score :
                                                ${((news.positive - news.negative)/(news.positive+news.negative))*100}%
                                            </div>
                                        </div>
                                    </div>
                                    <div class="abody">
                                        <c:out value="${news.content_news}" />
                                    </div>
                                    <p class="containerbutton">
                                        <button>
                                            <a class="more" href="<c:url value="/showNews?id=${news.id_news}"/>">
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