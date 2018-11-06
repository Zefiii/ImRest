<%-- 
    Document   : registrarImatge
    Created on : 26-oct-2018, 10:55:19
    Author     : oriol.nin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar Imatge</title>
    </head>
    <body>
        <div>
            <h1 id="headerRegistreImatge">Registrar imatge</h1>
            <p id="dades"><strong>Introdueix les dades que es demanen a continuació</strong></p>
            <form method="post" action="webresources/generic/register" id="registrarImatge">
                <label for="title"><br>Títol:<br></label>
                <input type="text" name="title" id="title">
                <br>
                <textarea cols='30' rows='10' name="description" id="description" placeholder="Escriu una breu descripció..."></textarea>
                <br>
                <label for="keywords"><br>Paraules clau:<br></label>
                <input type="text" name="keywords" id="keywords">
                <br>
                <label for="author"><br>Autor:<br></label>
                <input type="text" name="author" id="author">
                <br>
                <label for="creation"><br>Data de creació:<br></label>
                <input type="date" name="creation" id="creation">
                <br>
                <input type="submit" name="registrar" id="registrar" value="Registrar">
                <input type="reset" name="netejar" id="netejarQuestionari" value="Esborrar dades">
            </form>
        </div>
    </body>
</html>