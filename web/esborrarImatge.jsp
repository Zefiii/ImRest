<%-- 
    Document   : esborrarImatge
    Created on : 06/11/2018, 17:48:09
    Author     : jrubiralta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div>
            <h1 id="headerEsborrarImatge">Esborrar Imatge</h1>
            <p id="dades"><strong>Introdueix l'ID de l'imatge que desitja esborrar</strong></p>
            <form method="post" action="webresources/generic/delete" id="modificarImatge">
                <label for="id"><br>ID:</br></label>
                <input type="text" name="id" id="id">
                <br>
                <input type="submit" name="esborrar" id="esborrar" value="Esborrar">
            </form>
        </div>    
    </body>
</html>
