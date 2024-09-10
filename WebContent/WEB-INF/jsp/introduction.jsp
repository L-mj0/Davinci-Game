<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>达·芬奇密码桌游介绍</title>
    <link rel="stylesheet" href="css/introduce.css">
</head>
<body>
	<div class="navbar">
        <a href="logout">Logout</a>
        <div class="menu">
            <a href="game">Game</a>
            <a href="introduction">Introduction</a>
        </div>
       	<% String username = (session != null) ? (String) session.getAttribute("username") : null;%>
       	<span class="username">用户名: <%= (username != null) ? username : "Guest" %></span>
   	</div>

    <div class="container">
        <h1>达·芬奇密码桌游介绍</h1>
        
        <h2>游戏概述</h2>
        <p><strong>设计师：</strong> Eiji Wakasugi<br>
           <strong>玩家人数：</strong> 1人<br>
           <strong>游戏时间：</strong> 20-30分钟<br>
           <strong>适合年龄：</strong> 10岁及以上
        </p>
        
        <h2>游戏组件</h2>
        <ul>
            <li>数字牌：包含数字0-11，每个数字有两张，一共24张牌。</li>
            <li>颜色：牌有两种颜色，一半是白色，一半是黑色。</li>
            <li>游戏规则手册。</li>
        </ul>
        
        <h2>游戏目标</h2>
        <p>玩家的目标是通过推理和猜测，成功解开对手的数字排列，并保持自己的hp不为0。</p>
        
        <h2>游戏玩法</h2>
        <ol>
            <li><strong>分发牌：</strong> 开局将所有数字牌洗混，随机发给玩家与电脑相同数量的牌（每人4张，两张黑两张白）。玩家只能看到自己的牌面，形成一排。牌的颜色和顺序应按照数字大小从左到右从小到大摆放（注意：若数字相同白色 小于 黑色，则白色应该在靠左的地方）。每位玩家初始hp为7</li>
            <li><strong>猜测对手的数字：</strong> 游戏按照玩家先手进行。玩家在自己的回合中可以选择直接抽牌或猜测一位电脑的一张牌。若选择猜测，若猜测错误，则hp-1；当hp</li>
            <li><strong>特殊规则：</strong> 
                <ul>
                    <li>若玩家hp为0或电脑方卡牌全部被猜出，游戏结束。玩家hp为0则玩家失败。若电脑卡牌全部猜出，则玩家获胜。</li>
                    <li>若当前卡牌全部被抽完，若玩家未全部猜出或猜错电脑的牌，则游戏失败</li>
                </ul>
            </li>
            <li><strong>游戏胜利：</strong> 将卡牌全部猜出者获胜。这里模拟了一个玩家hp规则，每猜错一次，hp-1，当hp小于0的时候游戏结束，玩家失败。</li>
        </ol>
        
        <h2>策略提示</h2>
        <div class="strategy">
            <p><strong>记忆和推理：</strong> 记住对手翻开的牌，并利用已知信息推理出未翻开的牌。</p>
            <p><strong>掩饰和迷惑：</strong> 通过假意猜测对手的牌，迷惑对手，增加对手的推理难度。</p>
            <p><strong>风险管理：</strong> 在猜测时平衡风险和回报，避免因为猜错而增加自己的牌数。</p>
        </div>
        
        <h2>适合人群</h2>
        <p>《达·芬奇密码》适合喜欢推理和逻辑挑战的玩家。它的规则简单，但需要较强的记忆力和推理能力，适合家庭聚会或朋友间的轻松娱乐。</p>
    	 
    	<div class="cards">
           <h2>游戏卡牌展示</h2>
           <div class="card-row">
           	   <img src="images/num/w0.jpg" alt="白色0">
               <img src="images/num/w1.jpg" alt="白色1">
               <img src="images/num/w2.jpg" alt="白色2">
               <img src="images/num/w3.jpg" alt="白色3">
               <img src="images/num/w4.jpg" alt="白色4">
               <img src="images/num/w5.jpg" alt="白色5">
               <img src="images/num/w6.jpg" alt="白色6">
               <img src="images/num/w7.jpg" alt="白色7">
               <img src="images/num/w8.jpg" alt="白色8">
               <img src="images/num/w9.jpg" alt="白色9">
               <img src="images/num/w10.jpg" alt="白色10">
               <img src="images/num/w11.jpg" alt="白色11">
               <img src="images/num/w-.jpg" alt="白色12">
           </div>
           <div class="card-row">
               <img src="images/num/b0.jpg" alt="黑色0">
               <img src="images/num/b1.jpg" alt="黑色1">
               <img src="images/num/b2.jpg" alt="黑色2">
               <img src="images/num/b3.jpg" alt="黑色3">
               <img src="images/num/b4.jpg" alt="黑色4">
               <img src="images/num/b5.jpg" alt="黑色5">
               <img src="images/num/b6.jpg" alt="黑色6">
               <img src="images/num/b7.jpg" alt="黑色7">
               <img src="images/num/b8.jpg" alt="黑色8">
               <img src="images/num/b9.jpg" alt="黑色9">
               <img src="images/num/b10.jpg" alt="黑色10">
               <img src="images/num/b11.jpg" alt="黑色11">
               <img src="images/num/b-.jpg" alt="黑色12">
           </div>
       </div>
    </div>
</body>
</html>
