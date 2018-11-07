<%-- 
    Document   : buscarAutor
    Created on : 07-nov-2018, 12:58:30
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
        <h1>Buscar imatges per Autor</h1>
        <form method="post" action="searchAuthor" id="searchAutor">
                <label for="author"><br>Autor:</br></label>
                <input type="text" name="author" id="author">
                <input type="submit" name="Submit">
                <br>
        </form>
    </body>
</html>