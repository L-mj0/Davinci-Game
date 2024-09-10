<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>抽卡结果</title>
    <link rel="stylesheet" href="../css/game.css">
</head>
<body>
<div class="container">
            <span class="close">&times;</span>
            <% String message = (String) request.getAttribute("message");
            String newCard = (String) request.getAttribute("newCard");
            String computerNewCard = (String) request.getAttribute("computerNewCard");
            %>

            <% if (message != null) { %>
                <p><%= message %></p>
            <% } else { %>
                <h2>你抽到啦: <%= newCard %></h2>
          		<div class="draw-cards" id="draw-cards">
                 <img src="../images/num/<%= newCard %>.jpg" alt="<%= newCard %>">
                 </div>
            <% } %>
        <form action="../game" method="post">
            <button type="submit">确认</button>
        </form>
    <% if (message != null && message.equals("游戏结束")) { %>
        <form action="../game/reset" method="post">
            <button type="submit">重新开始</button>
        </form>
    <% } %>

    <script>
        var modal = document.getElementById("resultModal");
        var span = document.getElementsByClassName("close")[0];

        window.onload = function() {
            modal.style.display = "block";
        }

        span.onclick = function() {
            modal.style.display = "none";
            window.location.href = "game";
        }

        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
                window.location.href = "game";
            }
        }
    </script>

</div>
</body>
</html>
