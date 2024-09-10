<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.roe21005.entity.Card" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>达·芬奇密码</title>
    <link rel="stylesheet" href="css/game.css">
    <style>
        .highlight {
            border: 2px solid red;
        }
    </style>
</head>
<body>
	<div class="navbar">
        <a href="logout">Logout</a>
        <div class="menu">
            <a href="game">Game</a>
            <a href="introduction">Introduction</a>
        </div>
        <% 
            String username = (session != null) ? (String) session.getAttribute("username") : null;
            Integer hp = (session != null) ?(Integer) session.getAttribute("hp"):null;
        %>
        
        <span class="username">用户名: <%= (username != null) ? username : "Guest" %></span>
    </div>
	<div class="container">
        <h1>达·芬奇密码</h1>
        <h2 class="hp">HP: <%= (hp != null) ? hp : "N/A" %></h2>
        <div class="top-cards" id="top-cards">
        	<form id= "computer-cards" action="game/guess" method="post">
        	<input type="hidden" id="card" name="card" value="">
        	<!-- 顶部牌显示 -->
				<%	List<String> computerStones = (List<String>) session.getAttribute("computerStones");
					int[] s = (int[])session.getAttribute("cards");
		        	if (computerStones != null ) {
		            	for (String stone : computerStones) {
		            		String c = stone.substring(0, 1);
		        	        int number = Integer.parseInt(stone.substring(1));
		        	        
		        	        int state = (c.equals("w"))?s[number]:s[12+number];
		            		String imagePath = (state == 1) ? stone + ".jpg" : c + "q.jpg";%>
		                                    
                        <!--<img src="images/num/<%= c %>q.jpg" alt="<%= stone %>" class="computer-card" data-id="<%= stone %>"  onclick="guessCard('<%= stone %>')" >  -->
		                 	<img src="images/num/<%= imagePath %>" alt="<%= stone %>" class="computer-card" data-id="<%= stone %>"  onclick="guessCard('<%= stone %>')">
		    				<%}
		       		}%>
        	</form>
        </div>
        <div class="draw-button">
            <form id="draw-button" action="game/draw" method="post">
                <input type="hidden" id="card-color" name="color" value="">
                <img src="images/num/wq.jpg" alt="wq"  onclick="drawCard('w')">
                <img src="images/num/bq.jpg" alt="bq"  onclick="drawCard('b')">
            </form>
        </div>
        
        <div class="bottom-cards" id="bottom-cards">
            <!-- 底部牌显示 -->
            <% List<String> playerStones = (List<String>) session.getAttribute("playerStones");
                if (playerStones != null) {
                    for (String stone : playerStones) {  %>
                        <img src="images/num/<%= stone %>.jpg" alt="<%= stone %>" >
            		<%}
                }%>
        </div>
         <% String newCard = (String) request.getAttribute("newCard");
            if (newCard != null) {%>
            <div class="new-card">
                <p>你抽到了: <%= newCard %></p>
            </div>
        	<%}
            String message = (String)request.getAttribute("message");
            if(message != null){%>
            	<div class="message">
            		<p><%=message %></p>
            	</div>
            <%}%>
        	
        	
    </div>
    
        <script>
        function drawCard(color) {
            document.getElementById('card-color').value = color;
            document.getElementById('draw-button').submit();
        }
        
        function guessCard(card) {
            const guessedId = prompt("你猜测该数字为(格式为颜色（b/w)+数字）：_____");
            let allGuessed = true;
            if (guessedId) {
                if (guessedId === card) {
                    alert("猜测成功");
                    const imgElement = document.querySelector("img[data-id='" + card + "']");
                    imgElement.src = "images/num/" + card + ".jpg";
                    imgElement.classList.add("highlight");
                    
                    // 发送 AJAX 请求更新服务器端的卡片状态
                    const xhr = new XMLHttpRequest();
                    xhr.open("POST", "game/updateState", true);
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    xhr.onreadystatechange = function() {
                        if (xhr.readyState === 4 && xhr.status === 200) {
                            console.log("Card state updated successfully");
                        }
                    };
                    xhr.send("card=" + card + "&state=1");
                    checkAllCardsGuessed();
                    } 
                else {
                    alert("猜测失败");
                    
                 	// 更新 HP
                    const hpElement = document.querySelector(".hp");
                    const hpValue = parseInt(hpElement.innerText.split(":")[1].trim());
                    const newHP = hpValue - 1;
                    hpElement.innerText = "HP: " + newHP;

                    // 检查游戏是否结束
                    if (newHP < 0) {
                        alert("游戏结束！");
                        window.location.href = "game/reset";
                    }
               		}
            	}
        	}
        
        function checkAllCardsGuessed() {
            const computerCards = document.querySelectorAll('.computer-card');
            let allGuessed = true;
            
            computerCards.forEach(function(card) {
                if (!card.classList.contains('highlight')) {
                    allGuessed = false;
                }
            });

            if (allGuessed) {
                alert('玩家获胜');
                window.location.href = 'game/reset';
            }
        }
        
       
      
    </script>
   
</body>
</html>