<%-- 
    Document   : buscaClau
    Created on : 07-nov-2018, 17:47:18
    Author     : Oriol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Buscar imatges per paraules clau</h1>
        <form method="post" action="searchKeywords" id="searchKeywords">
                <label for="keywords"><br>Data:</br></label>
                <input type="text" name="keywords" id="keywords">
                <input type="submit" name="Submit">
                <br>
        </form>
    </body>
</html>

