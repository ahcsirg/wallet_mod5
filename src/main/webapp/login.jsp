<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="static/bootstrap/js/bootstrap.bundle.min.js"></script>
<meta charset="ISO-8859-1">
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<title>Inicio de Sesi�n</title>
</head> 
<body>

<style>
        body {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            text-align: center;
        }

        a {
            display: block;
            margin: 10px 0;
        }
    </style>
    
	<h1>Inicio de Sesi�n</h1>
    
            <div class="card" style="width: 40%;">
                <div class="card-body">
                    <h5 class="card-title">Login</h5>
                    <form action="cliente" method="post">
                    <input type="hidden" name="action" value="login" required>
                    <label for="" class="form-label">Correo</label>
                    <input type="text" id="correo" name="correo" required>
                    <div class="mb-3">
					</div>   
					<div class="mb-3">
					  <label for="password" >Contrase�a</label>
					  <input type="password" id="password" name="password">
					  <button type="submit">Iniciar Sesion</button>
					  <br>
					</div>
					</form>                 
                </div>
            </div> 
	

    <c:if test="${not empty errorMessage}">
        <p style="color:red;">${errorMessage}</p>
    </c:if>
    
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
	
</body>
</html>