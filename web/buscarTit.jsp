<%-- 
    Document   : buscarTit
    Created on : 07-nov-2018, 11:44:23
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
        <h1>Buscar imatges per Id</h1>
        <form method="post" action="searchTitle" id="modificarImatge">
                <label for="Titol"><br>Titol:</br></label>
                <input type="text" name="title" id="id">
                <input type="submit" name="Submit">
                <br>
        </form>
    </body>
</html>
