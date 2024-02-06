<%-- 
    Document   : myNews
    Created on : 25 sept. 2023, 14:05:42
    Author     : Rene
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mon profil</title>
        <link rel="stylesheet" href="<c:url value="/assets/css/style.css"/>">
    </head>
    <body>
        <header>
            <%@ include file="/WEB-INF/jspf/menu.jspf"%>
        </header>
        <main>
            <section>
            <h1 id="maintitle">Oh My Blog de ${sessionScope.bean.login.split("@")[0]}</h1>
        </section>
            <article>
                <h2 id="Dix">Mes news :</h2>
                <c:forEach items="${requestScope.news}" var="news">
                    <article id="newscontent">                       
                        <div class="myNews">
                            <h2 class="atitle"><c:out value="${news.title_news}"/></h2>   
                            <div id="score">Score :
                                ${((news.positive - news.negative)/(news.positive+news.negative))*100}%
                            </div>   
                        </div>                         
                        <div class="abody"><c:out value="${news.content_news}"/></div>
                    </article>
                </c:forEach>
            </article>       
        </main>
        <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
