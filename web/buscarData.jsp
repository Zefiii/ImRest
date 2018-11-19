<%-- 
    Document   : buscarData
    Created on : 07-nov-2018, 12:17:13
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
        <h1>Buscar imatges per Data</h1>
        <form method="post" action="searchDate" id="modificarImatge">
                <label for="date"><br>Data:</br></label>
                <input type="text" name="date" id="date">
                <input type="submit" name="Submit">
                <br>
        </form>
    </body>
</html>
