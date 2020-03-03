<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ctx" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../css/reset.css">
    <link rel="stylesheet" href="../css/pay.css">
    <title>pay</title>
</head>
<body>
    <header>
        <nav class="navContainer">
            <img src="../images/casino_logo_1.svg" alt="logo" id="backToHome"/>
            <div id="headRight">
                <div id="LanguageBox" class="langCut">
                <div id="inputBox">
                    <img src="../images/Singapore.png" alt="" id="guoqiBox" />中文
                </div>
                <ul id="language">
                    <li id="Ch">
                    <i></i><ctx:message code="chinese"/></li>
                    <li id="En">
                    <i></i>English</li>
                </ul>
                </div>
                <img src="../images/down.png" alt="down" id="changeLanguage" class="langCut" style="width:20px;height:20px;"/>
            </div>
        </nav>
    </header>
    <section>
        <div id="title">
            充值
            <span id="back">返回</span>
        </div>
        <div class="payContain">
            <span style="font-size:16px;">充值账号：</span><span id="userPayPhone"></span>
        </div>
        <div class="payContain">
            <span style="font-size:16px;">账户余额：</span><span id="userBalance"></span>
        </div>
        <div class="payContain">
            <span style="font-size:16px;">充值金额：</span><input id="sumInput" placeholder="请输入充值金额" type=text;>
        </div>
        <p >(1GOS=1元)</p> 
        <div class="payContain">
            <span>应付金额：</span><span id="payMoney"></span>
        </div>
        <div id="payComfire">
            <button id="payFor" >点击充值</button>
        </div>
    </section>
    <script src="../js/jquery.js"></script>
    <script src="../js/zepto.min.js"></script>
    <script src="../js/dialog.min.js"></script>
    <script src="../js/pay.js"></script>
</body>
</html>