<%-- 
    Document   : Admin
    Created on : 25 sept. 2023, 14:02:37
    Author     : Rene
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
        <link rel="stylesheet" href='<c:url value="/assets/css/style.css"/>'>

    </head>
    <body>
        <header>
            <%@ include file="/WEB-INF/jspf/menu.jspf"%>
        </header>
        <main>
            <section>
            <h1 id="maintitle">Administration du site</h1>
        </section>
            <h2 id="Dix">Les utilisateurs</h2>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Login</th>
                    <th id="gerer">Gérer</th>
                </tr>
                <c:forEach items="${requestScope.users}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.login}</td>
                        <td id="buttonadmin">
                            <button>
                                <a class="danger"
                                   href="<c:url value="/admin/deleteUser?id=${user.id}"/>">
                                    Supprimer
                                </a>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.activate == false}">
                                        <button>
                                            <a class="success"
                                               href="<c:url value="/admin/unbanUser?id=${user.id}"/>">
                                                Unban
                                            </a>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button>
                                            <a class="danger"
                                               href="<c:url value="/admin/banUser?id=${user.id}"/>">
                                                Bannir
                                            </a>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                    </tr>
                </c:forEach>
            </table>
            <%@ include file="/WEB-INF/jspf/deleteNews.jspf"%>
        </main>
            <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
