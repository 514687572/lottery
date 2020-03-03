<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ctx" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="baseUrl" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>
        <ctx:message code="dice73" />
    </title>
    <link rel="stylesheet" href="${baseUrl}/EOS_DICE/css/reset.css">
    <link rel="stylesheet" href="${baseUrl}/EOS_DICE/css/theDiceHome.css">
    <link rel="stylesheet" href="${baseUrl}/EOS_DICE/css//theDiceHomeJackpot.css">
    <link rel="stylesheet" href="${baseUrl}/EOS_DICE/css//theDiceHomeBetting.css">
    <link rel="stylesheet" href="${baseUrl}/EOS_DICE/css/theDiceGameRules.css">
    <link rel="stylesheet" href="${baseUrl}/EOS_DICE/css/theDicePopup.css">
    <link rel="stylesheet" href="${baseUrl}/EOS_DICE/css/message.css">
    <link rel="stylesheet" href="${baseUrl}/EOS_DICE/css/theDiceInvite.css">
    <link rel="stylesheet" href="${baseUrl}/EOS_DICE/css/dialog.css">
</head>

<body>
    <header>
        <nav>
            <div class="nav1-1">
                <div class="nav-pull-down">
                    <p> <img id="logo" src="${baseUrl}/EOS_DICE/img/CASINO .png" alt=""></p>
                   
                </div>
                <!-- <div class="SelectGame">
                    <p id="tiger"><img src="${baseUrl}/EOS_DICE/img/tiger.png" alt=""></p>
                    <p id="lottery"><img src="${baseUrl}/EOS_DICE/img/lotteryLogo.png" alt=""></p>
                </div> -->
                <p id="gamesHelpBtn">
                    <ctx:message code="dice1" />
                </p>
                <p id="InviteFriends">
                    <ctx:message code="dice2" />
                </p>
            </div>
            <div class="nav1-2">
                <div>
                    <div class="headerLoginBtn">
                        <p id="navLoginBtn">
                            <ctx:message code="dice19" />
                        </p>
                    </div>
                    <div class="userinfo">
                            <div>
                                <p>
                                    <img id="cpuImg" src="${baseUrl}/EOS_DICE/img/3.png" alt="">
                                    <p id="cpu">2.02%</p>
                                </p>
                                <p id="userName"><ctx:message code="dice66" /></p>
                                <img id="navQuit" src="${baseUrl}/EOS_DICE/img/4.png" alt="">
                            </div>
                            <div>
                                <p>
                                    <img src="${baseUrl}/EOS_DICE/img/eos.svg" alt="">
                                    <p id="userBalance"> <ctx:message code="dice76" /></p>
                                </p>
                            </div>
                        
                    </div>
                    <!-- 抵押cpu弹窗 -->
                    <div id="cpuCPM">
                        <img id="cpuCPMCloce" src="${baseUrl}/EOS_DICE/img/close.png" alt="">
                        <p><ctx:message code="cpuMortgage" /></p>
                        <div id="cpuCPM1">
                            <img src="${baseUrl}/EOS_DICE/img/eos.svg" alt="">
                            <input placeholder="<ctx:message code='inputCupMoney' />" id="cpuInput" type="text">
                            <p id="cpuComfire"><ctx:message code="sure" /></p>
                        </div>
                    </div>
                </div>
                <!-- <img src="${baseUrl}/EOS_DICE/img/voice.png" alt=""> -->
                <div id="language">
                    <div id="language1">
                        <img src="${baseUrl}/EOS_DICE/img/Singapore.png" alt="">
                        <p>
                            <ctx:message code="dice41" />
                        </p>
                        <img src="${baseUrl}/EOS_DICE/img/pull-down.png" alt="">

                    </div>
                    <div id="language2">
                        <div id="Chinese">
                            <img src="${baseUrl}/EOS_DICE/img/Singapore.png" alt="">
                            <span>
                                <ctx:message code="dice41" />
                            </span>
                        </div>
                        <div id="English">
                            <img src="${baseUrl}/EOS_DICE/img/guoqi02.png" alt="">
                            <span>English</span>
                        </div>
                    </div>

                </div>

            </div>
        </nav>
    </header>
    <!-- å¼å¥è®°å½åºå -->
    <section id="gameHome">
        <section class="The-lottery-record">
            <div class="lotteryHeader">

                <h6>
                    <ctx:message code="dice42" />
                </h6>

            </div>
            <!-- å¼å¥è®°å½ç°å¨å¼å¥æ¾ç¤ºåºå -->
            <div class="CurrentlyTheLottery">
                <div class="newIssue">
                    <div>
                        <p><ctx:message code="dice13" />:</p>
                        <p id="newPeriods">25684285</p>
                    </div>

                    <div>
                        <p><ctx:message code="dice12" />:</p>
                        <p id="newTime">08:40:00:00</p>
                        <p>now</p>
                    </div>
                </div>
                <div class="newBlockChainNo">
                    <p>...</p>
                    <p id="newBlockChainNo">123abc456789132asda1a</p>
                </div>
                <div class="newResult">
                    <p id="newType">
                        <span id="newBig">
                            <ctx:message code="dice3" />
                        </span>
                        <span id="newSmall">
                            <ctx:message code="dice4" />
                        </span>
                        <span id="newPair">
                            <ctx:message code="dice5" />
                        </span>
                    </p>
                    <p id="newWinningNumbers">87</p>
                </div>
            </div>
            <!-- å¼å¥è®°å½è¡¨æ ¼åºå -->
            
            <div class="lotteryTable">
                <div class="lotteryTable1-1">
                    <ul class="lotteryTableTh">
                        <li>
                            <ctx:message code="dice13" />
                        </li>
                        <li>
                            <ctx:message code="dice16" />
                        </li>
                        <li>
                            <ctx:message code="dice11" />
                        </li>
                        <li>
                            <ctx:message code="dice12" />
                        </li>
                        <li>
                            <ctx:message code="dice14" />
                        </li>
                    </ul>
                </div>
                <div id="carousel">

                </div>

            </div>
        </section>

        <!-- å¥æ± åºå -->
        <section class="jackpot">
            <div class="jackpot1-1">
                <p>
                    <ctx:message code="dice15" />
                </p>
                <div>
                    <!-- <img src="${baseUrl}/EOS_DICE/img/BonusPools.png" alt=""> -->
                    <canvas id="c"></canvas>
                    <!-- <p id="jackpotSum"></p> -->
                </div>
                <!-- <p>åçº¢æ± </p> -->
            </div>
            <!-- ææ³¨åº -->
            <div class="bettingArea">
                <div class="diceBet">
                    <div class="selectiveType">
                        <div class="intervalOrSize">
                            <div>
                                <p id="selectInterva">
                                    <ctx:message code="dice36" />
                                </p>
                                <p id="selectSize">
                                    <ctx:message code="dice37" />
                                </p>
                            </div>
                        </div>
                        <div class="SelectTypeArea">
                            <div class="guessInterval1-1">
                                <div>
                                    <p class="selectBet" id="greaterThan" data-id="1">
                                        <ctx:message code="dice6" />
                                    </p>
                                    <p class="selectBet" id="lessThan" data-id="2">
                                        <ctx:message code="dice7" />
                                    </p>
                                </div>
                                <div class="slider">
                                    <input type="range" id="range1" min="0" max="99" step="1" defaultValue="50" onchange="b.value=this.value" />
                                    <p>
                                        <span>
                                            <ctx:message code="dice118" />:</span>
                                            <span id="sizeIn"></span>
                                        <output id="b" for="range1"></output>
                                    </p>

                                </div>
                            </div>
                            <div class="digitalRange">
                                    <p id="minimum">0</p>
                                    <p id="max">99</p>
                            </div>
                        </div>
                        <div class="CrapArea">
                            <div class="crap1-1">
                                <div>
                                    <p class="selectBet" data-id="3">
                                        <ctx:message code="dice3" />
                                    </p>
                                    <p>
                                        <ctx:message code="dice78" />
                                    </p>
                                </div>
                                <div>
                                    <p class="selectBet" data-id="4">
                                        <ctx:message code="dice4" />
                                    </p>
                                    <p>
                                        <ctx:message code="dice77" />
                                    </p>
                                </div>
                                <div>
                                    <p class="selectBet" data-id="5">
                                        <ctx:message code="dice5" />
                                    </p>
                                    <p>
                                        <ctx:message code="dice79" />
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="oddsOrBonus" style="width:100%">
                            <div>
                                <p>
                                    <ctx:message code="dice9" />:</p>
                                <h4>
                                    <span id="odds">0</span>X</h4>
                            </div>
                            <div>
                                <p>
                                    <ctx:message code="dice10" />:</p>
                                <h4 id="bonus">0</h4>EOS
                            </div>
                        </div>
                    </div>
                    <div class="WinningNumbers">
                        <p>
                            <ctx:message code="dice11" />
                        </p>
                        <!-- <img src="${baseUrl}/EOS_DICE/img/qiu.png" alt=""> -->
                        <div>
                            <p id="winningNum"></p>
                            <p id="betNum"></p>
                        </div>

                    </div>
                </div>

                <div class="bettingAmount">
                    <p>
                        <ctx:message code="dice17" />
                    </p>
                    <div class="bettingAmount1-1">
                        <img src="${baseUrl}/EOS_DICE/img/eos.png" alt="">
                        <input type="number" id="inputBox" value="0.1" step="0.1" min="0.1" max="100" onkeyup="clearNoNum(this)">
                        <p id="divide">1/2</p>
                        <p id="ride">X2</p>
                        <p id="maximum" style="margin-top: 0px">MAX</p>
                    </div>
                    <p id="mask"></p>
                    <p id="loginBtn" data-state="1">
                        <ctx:message code="dice19" />
                    </p>
                </div>

            </div>
        </section>
        <section class="record">
            <div class="recordNav">
                <div>
                    <p id="theWinningBetsFont">
                        <ctx:message code="dice21" />
                    </p>
                    <p id="iBetTheFont">
                        <ctx:message code="dice22" />
                    </p>
                </div>
            </div>
            <div class="recordTable">
                <div id="iBetTheTable">
                    <!-- æçææ³¨åºå -->
                    <ul class="recordHeader">
                        <li>
                            <ctx:message code="dice43" />
                        </li>
                        <li>
                            <ctx:message code="dice47" />
                        </li>
                        <li>
                            <ctx:message code="dice23" />
                        </li>
                        <li>
                            <ctx:message code="dice18" />
                        </li>
                        <li>
                            <ctx:message code="dice48" />
                        </li>
                        <li>
                            <ctx:message code="dice24" />
                        </li>
                    </ul>
                    <div id="iBetThe">

                    </div>

                </div>
                <!-- ä¸­å¥ææ³¨åºå -->
                <div id="theWinningTable">
                    <ul class="theWinningBets">
                        <li>
                            <ctx:message code="dice43" />
                        </li>
                        <li>
                            <ctx:message code="dice44" />
                        </li>
                        <li>
                            <ctx:message code="dice20" />
                        </li>
                        <li>
                            <ctx:message code="dice23" />
                        </li>
                        <li>
                            <ctx:message code="dice18" />
                        </li>
                        <li>
                            <ctx:message code="dice9" />
                        </li>
                        <li>
                            <ctx:message code="dice24" />
                        </li>
                    </ul>
                    <div id="allPrizeTable">

                    </div>

                </div>
            </div>

        </section>
    </section>
    <!-- ç©æ³ä»ç»/æ¸¸æå¸®å© -->
    <div id="gamesHelp">
        <div class="gamesHelp-header">
            <p>
                <ctx:message code="dice80" />
            </p>
            <p id="goBack">
                <ctx:message code="dice81" />
            </p>
        </div>
        <section class="generationNumber">
            <div>
                <div class="generationNumber1-1 ">
                    <p class="shadow"></p>
                    <img class="gamesHelp-img" src="${baseUrl}/EOS_DICE/img/Lottery.png" alt="">
                </div>
                <div class="generationNumber1-2">
                    <p class="gamesHelp-h3">
                        <ctx:message code="dice25" />
                    </p>
                    <div>
                        <p class="gamesHelp-h5">
                            <ctx:message code="dice11" />
                        </p>
                        <p class="gamesHelp-font">
                            <ctx:message code="dice26" />
                        </p>
                    </div>
                    <div>
                        <p class="gamesHelp-h5">
                            <ctx:message code="dice38" />
                        </p>
                        <p class="gamesHelp-font">
                            <ctx:message code="dice27" />
                        </p>
                    </div>
                </div>
            </div>
        </section>
        <section class="generationNumber">
            <div>
                <div class="generationNumber1-1 ">
                    <p class="shadow"></p>
                    <img class="gamesHelp-img" src="${baseUrl}/EOS_DICE/img/gold.png" alt="">
                </div>
                <div class="generationNumber1-2">
                    <p class="gamesHelp-h3">
                        <ctx:message code="dice28" />
                    </p>
                    <div>
                            <p class="gamesHelp-h5"><ctx:message code="dice36" /></p>
                            <p class="gamesHelp-font">
                                <span><ctx:message code="dice127" /></span>
                                <span class="gameplayFont-color"><ctx:message code="dice128" /></span>
                                <span><ctx:message code="dice129" /></span>
                                <span class="gameplayFont-color"><ctx:message code="dice130" /></span>
                                <span><ctx:message code="dice131" /></span>
                            </p>
                            <p class="gamesHelp-font"><ctx:message code="dice132" /></p>
                            <p class="gamesHelp-font"><ctx:message code="dice133" /></p>
                            <p class="gamesHelp-font"><ctx:message code="dice134" /></p>
                        </div>
                        <div>
                            <p class="gamesHelp-h5"><ctx:message code="dice37" /></p>
                            <p class="gamesHelp-font">
                                <span><ctx:message code="dice135" /></span>
                                <span class="gameplayFont-color"><ctx:message code="dice136" /></span>
                                <span><ctx:message code="dice137" /></span>
                                <span class="gameplayFont-color"><ctx:message code="dice138" /></span>
                                <span><ctx:message code="dice139" /></span>
                                <span class="gameplayFont-color"><ctx:message code="dice140" /></span>
                                <span><ctx:message code="dice141" /></span>
                            </p>
                            <p class="gamesHelp-font">
                                <span><ctx:message code="dice142" /></span>
                                <span class="gameplayFont-color"><ctx:message code="dice143" /></span>
                                <span><ctx:message code="dice144" /></span>
                            </p>
                            <p class="gamesHelp-font">
                                <span><ctx:message code="dice145" /></span>
                                <span class="gameplayFont-color"><ctx:message code="dice146" /></span>
                                <span><ctx:message code="dice147" /></span>
                            </p>
                            <p class="gamesHelp-font">
                                <span><ctx:message code="dice148" /></span>
                                <span class="gameplayFont-color"><ctx:message code="dice149" /></span>
                                <span><ctx:message code="dice150" /></span>
                            </p>
                        </div>
                        <div>
                            <p class="gamesHelp-font li"><ctx:message code="dice151" /></p>
                            <p class="gamesHelp-font"><ctx:message code="dice152" /></p>
                            <p class="gamesHelp-font"><ctx:message code="dice153" /></p>
                            <p class="gamesHelp-font"><ctx:message code="dice154" /></p>
                        </div>
                </div>
            </div>
        </section>
        <section class="generationNumber">
            <div>
                <div class="generationNumber1-1 ">
                    <p class="shadow"></p>
                    <img class="gamesHelp-img" src="${baseUrl}/EOS_DICE/img/time.png" alt="">
                </div>
                <div class="generationNumber1-2">
                    <p class="gamesHelp-h3">
                        <ctx:message code="dice35" />
                    </p>
                    <p class="gamesHelp-Time-font">
                        <ctx:message code="dice31" />
                    </p>
                </div>
            </div>
        </section>
        <section class="generationNumber">
            <div>
                <div class="generationNumber1-1 ">
                    <p class="shadow"></p>
                    <img class="gamesHelp-img" src="${baseUrl}/EOS_DICE/img/quota.png" alt="">
                </div>
                <div class="generationNumber1-2">
                    <p class="gamesHelp-h3">
                        <ctx:message code="dice32" />
                    </p>
                    <p class="gamesHelp-Time-font">
                        <ctx:message code="dice33" />
                    </p>
                </div>
            </div>
        </section>
        <div class="gamesHelp-footer">
            <p>
                <ctx:message code="dice34" /> </p>

        </div>

    </div>
    <!-- éè¯·å¥½å -->
    <div id="Invite">
        <div class="InviteHeader">
            <p>
                <ctx:message code="p12" /> INVITE FRIENDS
            </p>
            <p id="InviteGoBack">
                <ctx:message code="dice81" />
            </p>
        </div>
        <div class="briefIntroduction">
            <div class="briefIntroduction1-1">
                <div>
                    <div>
                        <p class="briefIntroduction2-1">0.1
                            <span>EOS</span>
                        </p>

                    </div>
                    <div>
                        <p>
                            <ctx:message code="p11" />
                        </p>
                        <p>
                            <ctx:message code="p111" />
                        </p>
                    </div>
                </div>
                <div>
                    <div>
                        <p class="briefIntroduction2-1">0.1
                            <span>EOS</span>
                        </p>

                    </div>
                    <div>
                        <p>
                            <ctx:message code="p12" />
                        </p>
                        <p>
                            <ctx:message code="p121" />
                        </p>
                        <p>
                            <ctx:message code="p122" />
                        </p>
                        <p>
                            <ctx:message code="p123" />
                        </p>
                    </div>
                </div>
                <div>
                    <div>
                        <p class="briefIntroduction2-1">1.0
                            <span>%</span>
                        </p>

                    </div>
                    <div>
                        <p>
                            <ctx:message code="p13" />
                        </p>
                        <p>
                            <ctx:message code="p131" />
                        </p>
                        <p>
                            <ctx:message code="p132" />
                        </p>
                        <p>
                                <ctx:message code="p133" />
                            </p>
                        <p>
                            <ctx:message code="dice86" />
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="theInvitationWay">
            <div class="theInvitationWay1-1">
                <div>
                    <p>
                        <ctx:message code="p14" />
                    </p>
                    <div class="theInvitationWay2-1">
                        <p>
                            <img src="${baseUrl}/EOS_DICE/img/fasong.png" alt="">
                        </p>
                        <p></p>
                        <p>
                            <img src="${baseUrl}/EOS_DICE/img/denglu.png" alt="">
                        </p>
                        <p></p>
                        <p>
                            <img src="${baseUrl}/EOS_DICE/img/xiaji.png" alt="">
                        </p>
                    </div>
                    <div class="theInvitationWay3-1">
                        <p>
                            <ctx:message code="copy" />
                        </p>
                        <p>
                            <ctx:message code="loginBet" />
                        </p>
                        <p>
                            <ctx:message code="refe" />
                        </p>
                    </div>
                    <div class="theInvitationWay4-1">
                        <div>
                            <p>
                                <ctx:message code="P142" />
                            </p>
                        </div>
                        <div>
                            <p>
                                <ctx:message code="P143" />
                            </p>
                            <!-- <p><ctx:message code="dice87" /></p> -->
                        </div>
                        <div>
                            <p>
                                <ctx:message code="P144" />
                            </p>
                            <!-- <p><ctx:message code="dice88" /></p> -->
                        </div>
                    </div>
                    <div class="kyrieandrewirving">
                        <input id="inviteInput" readonly="true" type="text">
                        <p id="InviteCopy">
                            <ctx:message code="dice63" />
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="subordinate">
            <div>
                <div class="subordinate1-1">
                    <p>
                        <ctx:message code="dice64" />
                    </p>
                    <p>
                        <ctx:message code="dice89" />
                        <span id="headcount">0</span>
                        <ctx:message code="dice90" />
                    </p>
                </div>
                <ul class="subordinateTh">
                    <li>
                        <ctx:message code="dice65" />
                    </li>
                    <li>
                        <ctx:message code="dice66" />
                    </li>
                    <li>
                        <ctx:message code="dice67" />
                    </li>
                    <li>
                        <ctx:message code="dice68" />
                    </li>
                    <li>
                        <ctx:message code="dice69" />
                    </li>
                </ul>
                <div class="subuserForm">

                </div>
                <div class="page">
                    <p id="upPage">&lt;</p>
                    <p id="currentPage">1</p>
                    <p>/</p>
                    <p id="TotalPages">1</p>
                    <p id="nextPage">&gt;</p>
                </div>
            </div>
        </div>
    </div>
    <!-- ç»å½å¼¹çª -->
    <div id="loginContainer">
        <div id="loginBox">
            <div id="loginHead">
                <img src="${baseUrl}/EOS_DICE/img/Lottery_logo.png" alt="lotteryLogo" />
                <i id="loginClose"></i>
            </div>
            <div id="loginStyleContainer">
                <div id="loginStyle">
                    <div>
                        <div id="myChainId">
                            <ctx:message code="dice91" />
                        </div>
                        <div id="scatterLogin">
                            <i id="scatterLogo"></i>
                            <ctx:message code="dice19" />
                        </div>
                    </div>
                    <div id="privateLogin">
                        <input placeholder="<ctx:message code='dice92' />" id="userChainId" />
                        <button id="loginComfire">
                            <ctx:message code="dice19" />
                        </button>
                    </div>
                    <div id="loadScatter">
                        <div id="loadScatterMiidle">
                            <img src="${baseUrl}/EOS_DICE/img/scatterLoad.png" alt="scatterLoad" />
                            <p>
                                <ctx:message code="dice109" />
                            </p>
                            <button class="scatter_login">
                                <ctx:message code="dice19" />
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
     <!-- 点击投注弹出详情弹窗 -->
     <div id="particularsCPM">
            <img id="CPMclose" src="${baseUrl}/EOS_DICE/img/close.png" alt="">
            <div class="particularsCPM1-1">
                <p><ctx:message code="resultValidation" /></p>
                <div class="particularsCPM1-2">
                    <p id="CPMIssue"></p>
                    <p>
                        <span>...</span>
                        <span id="CPMhash"></span>
                    </p>
                    <p id="CPMtime"></p>
                </div>
                <p>
                    <span><ctx:message code="Number" /></span>
                    <span id="CPMnum"></span>
                </p>
            </div>
    
        </div>
        <div id="backgroundMask"></div>
    <footer></footer>
 	<script>
 	let dice3 = "<ctx:message code="dice3" />";
    let dice4 = "<ctx:message code="dice4" />";
    let dice5 = "<ctx:message code="dice5" />";
    let dice6 = "<ctx:message code="dice6" />";
    let dice7 = "<ctx:message code="dice7" />";
    let dice93 = "<ctx:message code="dice93" />";
    let dice94 = "<ctx:message code="dice94" />";
    let dice95 = "<ctx:message code="dice95" />";
    let dice96 = "<ctx:message code="dice96" />";
    let dice97 = "<ctx:message code="dice97" />";
    let dice98 = "<ctx:message code="dice98" />";
    let dice99 = "<ctx:message code="dice99" />";
    let dice100 = "<ctx:message code="dice100" />";
    let dice101 = "<ctx:message code="dice101" />";
    let dice102 = "<ctx:message code="dice102" />";
    let dice103 = "<ctx:message code="dice103" />";
    let dice104 = "<ctx:message code="dice104" />";
    let dice105 = "<ctx:message code="dice105" />";
    let dice106 = "<ctx:message code="dice106" />";
    let dice107 = "<ctx:message code="dice107" />";
    let dice108 = "<ctx:message code="dice108" />";
    let dice109 = "<ctx:message code="dice109" />";
    let dice111 = "<ctx:message code="dice111" />";
    let dice112 = "<ctx:message code="dice112" />";
    let dice113 = "<ctx:message code="dice113" />";
    let dice114 = "<ctx:message code="dice114" />";
    let dice115 = "<ctx:message code="dice115" />";
    let dice116 = "<ctx:message code="dice116" />";
    let dice117 = "<ctx:message code="dice117" />";
    let dice119 = "<ctx:message code="dice119" />";
    let cpuerror = "<ctx:message code="cpuerror" />";
    let dice122 = "<ctx:message code="dice122" />";
    let dice126 = "<ctx:message code="dice126" />";
    let inputAmount = "<ctx:message code="inputAmount" />";
 	var lang = '${pageContext.response.locale}';

 	</script>
 	<script src="${baseUrl}/EOS_DICE/js/zepto.min.js"></script>
     <script src="${baseUrl}/EOS_DICE/js/dialog.min.js"></script>
    <script src="https://cdn.scattercdn.com/file/scatter-cdn/js/latest/scatterjs-core.min.js"></script>
    <script src="${baseUrl}/EOS_DICE/js/eos.min.js"></script>
    <script src="https://cdn.scattercdn.com/file/scatter-cdn/js/latest/scatterjs-plugin-eosjs.min.js"></script>
    <script src="https://cdn.scattercdn.com/file/scatter-cdn/js/latest/scatterjs-plugin-web3.min.js"></script>
    <script src="https://cdn.scattercdn.com/file/scatter-cdn/js/latest/scatterjs-plugin-tron.min.js"></script>
    <script src="${baseUrl}/EOS_DICE/js/jquery-1.12.4.min.js"></script>
    <script src="${baseUrl}/EOS_DICE/js/message.min.js"></script>
    <script src="${baseUrl}/EOS_DICE/js/theDiceHomeJackpot.js"></script>
    <script src="${baseUrl}/EOS_DICE/js/diceWebSocket.js"></script>
    <script src="${baseUrl}/EOS_DICE/js/bettingRecord.js"></script>
    <script src="${baseUrl}/EOS_DICE/js/ball.js"></script>

</body>

</html>