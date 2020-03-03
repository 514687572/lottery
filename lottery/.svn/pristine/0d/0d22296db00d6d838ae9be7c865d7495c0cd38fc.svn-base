<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
    <%@taglib prefix="ctx" uri="http://www.springframework.org/tags" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <c:set var="baseUrl" value="${pageContext.request.contextPath}" />
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
                <title>DICE</title>
                <script src="${baseUrl}/EOS_DICE/js/auto-size.js"></script>
                <link rel="stylesheet" href="${baseUrl}/EOS_DICE/css/reset.css">
                <link rel="stylesheet" href="${baseUrl}/EOS_DICE/css/wapDiceHome.css">
                <link rel="stylesheet" href="${baseUrl}/EOS_DICE/css/dialog.css">
            </head>

            <body>
                <header>

                    <div>
                        <div class="header1">
                            <p id="logo">
                                <img src="${baseUrl}/EOS_DICE/img/lottery_logo.svg" alt="">
                                <span>DICE</span>
                            </p>
                            <p id="login">
                                <ctx:message code="dice19" />
                            </p>
                            <div class="header1-1">
                                <p id="banner">
                                    <img data-banner="0" src="${baseUrl}/EOS_DICE/img/Singapore.png" alt="">
                                    <span>
                                        <ctx:message code="chinese" />
                                    </span>
                                </p>

                                <img id="MoreOperations" data-more="0" src="${baseUrl}/EOS_DICE/img/more.png" alt="">
                                <div class="switchTheLanguage">
                                    <p id="chinese">
                                        <img src="${baseUrl}/EOS_DICE/img/Singapore.png" alt="">
                                        <span>
                                            <ctx:message code="chinese" />
                                        </span>
                                    </p>
                                    <p id="english">
                                        <img src="${baseUrl}/EOS_DICE/img/guoqi02.png" alt="">
                                        <span>English</span>
                                    </p>
                                </div>
                            </div>
                            <div id="operation">
                                <p id="gameHelpBtn">
                                    <img src="${baseUrl}/EOS_DICE/img/ruler.svg" alt="">
                                    <ctx:message code="dice1" />
                                </p>
                                <p id="InviteBtn">
                                    <img src="${baseUrl}/EOS_DICE/img/usersInfo.svg" alt="">
                                    <ctx:message code="dice2" />
                                </p>
                            </div>
                        </div>
                        <div id="userMessage">
                            <div class="user">
                                <div>
                                    <img src="${baseUrl}/EOS_DICE/img/user.png" alt="">
                                    <p id="userName"></p>
                                </div>
                                <div>
                                    <img src="${baseUrl}/EOS_DICE/img/eos1.png" alt="">
                                    <p id="userBalance"></p>
                                </div>
                                <div>
                                    <img src="${baseUrl}/EOS_DICE/img/6.png" alt="">
                                    <p id="userIntegral"></p>
                                </div>
                                <div class="userCpu">
                                    <div>
                                        <div id="cpuImg"></div>
                                        <div id="cpuPlan"></div>
                                    </div>

                                    <p id="cpu"></p>
                                </div>
                                <img id="logOut" src="${baseUrl}/EOS_DICE/img/loginOut.svg" alt="">
                            </div>
                        </div>
                        <div id="gosUserMessage">
                                <div class="gosUser">
                                    <div>
                                        <img src="${baseUrl}/EOS_DICE/img/user.png" alt="">
                                        <p id="telephone">13945824598</p>
                                    </div>
                                    <div>
                                        <img src="${baseUrl}/EOS_DICE/img/chip.svg" alt="">
                                        <p id="integral">10Gos</p>
                                    </div>
                                    <img id="topUp" src="${baseUrl}/EOS_DICE/img/chongzhi.svg" alt="">
                                    <img id="withdrawDeposit" src="${baseUrl}/EOS_DICE/img/tixian.svg" alt="">
                                    <img id="gosQuit" src="${baseUrl}/EOS_DICE/img/loginOutWap.svg" alt="">
                                </div>
                
                            </div>
                    </div>
                </header>
                <div id="gamePage">
                    <section class="newLottery">
                        <div class="newLottery1-1">
                            <p id="newPeriods">53365</p>
                            <p id="newTime">15:12:00</p>
                        </div>
                        <div>
                            <span>...</span>
                            <p id="newBlockChainNo">53725fdc2af716dsa398</p>
                        </div>
                        <div class="newLottery1-2">
                            <p id="result">
                                <ctx:message code="dice5" />
                            </p>
                            <p id="newWinningNumbers">87</p>
                        </div>
                    </section>
                    <div></div>
                    <section class="Carousel">
                        <div id="carousel">

                        </div>


                    </section>
                    <section class="BettingArea">
                        <div>
                            <div class="WinningNumbers">
                                <p>
                                    <ctx:message code="dice11" />
                                </p>
                                <div>
                                    <p id="winningNum"></p>
                                    <p id="betNum"></p>
                                </div>
                            </div>
                            <div class="jackpot">
                                <p>
                                    <ctx:message code="dice15" />
                                </p>
                                <canvas id="c"></canvas>
                                <p id="jackpotSum"></p>
                            </div>
                        </div>
                        <div class="SelectBet">
                            <div>
                                <div class="select">
                                    <div>
                                        <p id="GuessInterval">
                                            <ctx:message code="dice36" />
                                        </p>
                                    </div>
                                    <div>
                                        <p id="crap">
                                            <ctx:message code="dice37" />
                                        </p>
                                    </div>

                                </div>
                                <div class="SelectTypeArea">
                                    <div class="guessInterval1-1">
                                        <div>
                                            <p class="CrapBtn" id="greaterThan" data-id="1">
                                                <ctx:message code="dice6" />
                                            </p>
                                            <p class="CrapBtn" id="lessThan" data-id="2">
                                                <ctx:message code="dice7" />
                                            </p>
                                        </div>
                                        <div class="slider">
                                            <!-- <div id="range"></div> -->
                                            <input type="range" id="range1" min="0" max="99" step="1" defaultValue="50" onchange="b.value=this.value" />
                                            <p>
                                                <span id="overUnder"></span>
                                                <output id="b" for="range1"></output>
                                            </p>

                                        </div>
                                    </div>
                                    <div class="digitalRange">
                                        <p id="minimum">0</p>
                                        <p id="max">99</p>
                                    </div>
                                    <div class="addAndSubtract">
                                        <p id="subtract">-</p>
                                        <p id="add">+</p>
                                    </div>
                                    <!-- <div class="addAndSubtract1">
                                        <div>
                                            <p id="subtract1">-</p>
                                            <p id="add1">+</p>
                                        </div>
                                    </div> -->
                                </div>
                                <div class="crap1-1">
                                    <div>
                                        <p class="CrapBtn" data-id="3">
                                            <ctx:message code="dice3" />
                                        </p>
                                        <p class="CrapBtn" data-id="4">
                                            <ctx:message code="dice4" />
                                        </p>
                                        <p class="CrapBtn" data-id="5">
                                            <ctx:message code="dice5" />
                                        </p>
                                    </div>

                                </div>
                                <div class="OddsAndBonuses">
                                    <p>
                                        <ctx:message code="dice9" />:
                                        <span id="odds">0</span>X</p>
                                    <p>
                                        <ctx:message code="dice10" />:
                                        <span id="bonus">0</span>
                                        <span id="currency">EOS</span> 
                                    </p>
                                </div>
                            </div>
                            <div class="amountSelected">
                                <img src="${baseUrl}/EOS_DICE/img/eos1.png" alt="">
                                <input type="tel" id="inputBox" value="0.1" step="0.1" min="0.1" max="100" onkeyup="clearNoNum(this)">
                                <p id="divide">1/2</p>
                                <p id="ride">X2</p>
                                <p id="maximum" style="margin-top: .03rem">MAX</p>
                            </div>
                        </div>
                    </section>
                    <div class="login">
                        <p id="loginBtn" data-state="1">
                            <ctx:message code="dice19" />
                        </p>
                    </div>
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
                                <!-- 我的投注区域 -->
                                <div id="loading" style="width:100%;height:3.8rem;background-color:rgba(0,0,0,0.5);position:absolute;bottom:-1.5rem;text-align:center;line-height: 3.8rem;display:none;">
                                    <img src="${baseUrl}/EOS_DICE/img/loading.gif" alt="" style="width:0.6rem;height:0.6rem">
                                </div>
                                <ul class="recordHeader">
                                    <li>
                                        <ctx:message code="dice43" />
                                    </li>
                                    <li>
                                        <ctx:message code="dice44" />
                                    </li>
                                    <li>
                                        <ctx:message code="dice120" />
                                    </li>
                                    <li>
                                        <ctx:message code="dice68" />
                                    </li>
                                    <li>
                                        <ctx:message code="dice24" />
                                    </li>
                                </ul>
                                <div id="iBetThe">
                                    <!-- <ul class="devoteTheResults">
                        <li>11/21 17:34</li>
                        <li>29096032</li>
                        <li>对子</li>
                        <li>14.21</li>
                        <li>+52.20</li>
                    </ul> -->
                                </div>

                            </div>
                            <!-- 中奖投注区域 -->
                            <div id="theWinningTable">
                                <ul class="theWinningBets">
                                    <li>
                                        <ctx:message code="dice43" />
                                    </li>
                                    <li>
                                        <ctx:message code="dice44" />
                                    </li>
                                    <li>
                                        <ctx:message code="dice45" />
                                    </li>
                                    <li>
                                        <ctx:message code="dice23" />
                                    </li>
                                    <li>
                                        <ctx:message code="dice49" />
                                    </li>
                                </ul>
                                <div id="allPrizeTable">
                                    <!-- <ul class="lotteryTableTr">
                        <li>11/21 17:34</li>
                        <li>29096032</li>
                        <li>xcvsdx</li>
                        <li>大</li>
                        <li>+88.66</li>
                    </ul> -->
                                </div>

                            </div>
                        </div>

                    </section>
                    <div class="loginCPM">
                        <div>
                            <p id="privateKeyLogin">
                                <ctx:message code="dice91" />
                            </p>
                            <p id="shortcutLogin">
                                <ctx:message code="QuickLogin" />
                            </p>
                        </div>
                        <div class="privateKeyPage">
                            <input placeholder="<ctx:message code='dice92' />" id="userChainId" />
                            <p id="loginComfire">
                                <ctx:message code="dice19" />
                            </p>
                        </div>
                        <div class="shortcutPage">
                            <input placeholder="<ctx:message code='phoneMail' />" id="phone" />
                            <input type="password" placeholder="<ctx:message code='passwordInput' />" id="password" />
                            <p id="shortcutLoginBtn">
                                <ctx:message code="dice19" />
                            </p>
                            <p id="register">
                                <ctx:message code="Register" />
                            </p>
                        </div>
                        <img id="closeCPM" src="${baseUrl}/EOS_DICE/img/wapClose.png" alt="">
                    </div>
                    <!-- 点击我的投注内容，弹窗 -->
                    <div id="CheckThePopover">
                        <div class="CheckThePopover1-1">
                            <P>
                                <img id="CheckThePopoverClose" src="${baseUrl}/EOS_DICE/img/close.png" alt="">
                            </P>
                            <div>
                                <P>
                                    <ctx:message code="dice123" />
                                </P>
                                <P id="betIssus"></P>
                            </div>
                            <div>
                                <P>
                                    <ctx:message code="dice124" />
                                </P>
                                <P id="CheckThePopover-result"></P>
                            </div>
                        </div>
                        <div class="CheckThePopover1-2">
                            <p>
                                <ctx:message code="dice125" />
                            </p>
                            <div>
                                <P id="betIssus1"></P>
                                <P>
                                    <span>...</span>
                                    <span id="betHash"></span>
                                </P>
                                <P id="betTime"></P>
                            </div>
                        </div>
                    </div>
                    <!-- 弹窗蒙板 -->
                    <div id="CPMmask"></div>
                    <!-- 抵押cpu弹窗 -->
                    <div id="pledgeCPU">
                        <img id="cpuCPMCloce" src="${baseUrl}/EOS_DICE/img/close.png" alt="">
                        <p>
                            <ctx:message code="cpuMortgage" />
                        </p>
                        <input id="cpuInput" placeholder="<ctx:message code='inputCupMoney' />" type="text">
                        <p id="cpuComfire">
                            <ctx:message code="sure" />
                        </p>
                    </div>
                </div>
                <!-- 玩法规则页面 -->
                <div id="gameRules">
                    <div class="rulesTitle">
                        <p>
                            <ctx:message code="dice28" />
                        </p>
                        <p>GAME RULES</p>
                        <p id="getBack">
                            <ctx:message code="dice81" />
                        </p>
                    </div>
                    <section class="NumberGeneration">
                        <div class="NumberGeneration1-1">
                            <img src="${baseUrl}/EOS_DICE/img/Lottery.png" alt="">
                            <p>
                                <ctx:message code="dice25" />
                            </p>
                        </div>
                        <div class="NumberGeneration2-1">
                            <div>
                                <p>
                                    <ctx:message code="dice11" />
                                </p>
                                <p>
                                    <ctx:message code="dice26" />
                                </p>
                            </div>
                            <div>
                                <p>
                                    <ctx:message code="dice38" />
                                </p>
                                <p>
                                    <ctx:message code="dice27" />
                                </p>
                            </div>
                        </div>
                    </section>
                    <section class="gameplay">
                        <div class="gameplay1-1">
                            <img src="${baseUrl}/EOS_DICE/img/gold.png" alt="">
                            <p>
                                <ctx:message code="dice28" />
                            </p>
                        </div>
                        <div class="gameplay2-1">
                            <div>
                                <p><ctx:message code="dice36" /></p>
                                <p>
                                    <span><ctx:message code="dice127" /></span>
                                    <span class="gameplayFont-color"><ctx:message code="dice128" /></span>
                                    <span><ctx:message code="dice129" /></span>
                                    <span class="gameplayFont-color"><ctx:message code="dice130" /></span>
                                    <span><ctx:message code="dice131" /></span>
                                </p>
                                <p><ctx:message code="dice132" /></p>
                                <p><ctx:message code="dice133" /></p>
                                <p><ctx:message code="dice134" /></p>
                            </div>
                            <div>
                                <p><ctx:message code="dice37" /></p>
                                <p>
                                    <span><ctx:message code="dice135" /></span>
                                    <span class="gameplayFont-color"><ctx:message code="dice136" /></span>
                                    <span><ctx:message code="dice137" /></span>
                                    <span class="gameplayFont-color"><ctx:message code="dice138" /></span>
                                    <span><ctx:message code="dice139" /></span>
                                    <span class="gameplayFont-color"><ctx:message code="dice140" /></span>
                                    <span><ctx:message code="dice141" /></span>
                                </p>
                                <p>
                                    <span><ctx:message code="dice142" /></span>
                                    <span class="gameplayFont-color"><ctx:message code="dice143" /></span>
                                    <span><ctx:message code="dice144" /></span>
                                </p>
                                <p>
                                    <span><ctx:message code="dice145" /></span>
                                    <span class="gameplayFont-color"><ctx:message code="dice146" /></span>
                                    <span><ctx:message code="dice147" /></span>
                                </p>
                                <p>
                                    <span><ctx:message code="dice148" /></span>
                                    <span class="gameplayFont-color"><ctx:message code="dice149" /></span>
                                    <span><ctx:message code="dice150" /></span>
                                </p>
                            </div>
                            <div>
                                <p><ctx:message code="dice151" /></p>
                                <p><ctx:message code="dice152" /></p>
                                <p><ctx:message code="dice153" /></p>
                                <p><ctx:message code="dice154" /></p>
                            </div>
                        </div>
                    </section>
                    <section class="gameTime">
                        <div class="gameTime1-1">
                            <img src="${baseUrl}/EOS_DICE/img/time.png" alt="">
                            <p>
                                <ctx:message code="dice35" />
                            </p>
                        </div>
                        <div class="gameTime2-1">
                            <p>
                                <ctx:message code="dice31" />
                            </p>

                        </div>
                    </section>
                    <section class="BettingLimits">
                        <div class="BettingLimits1-1">
                            <img src="${baseUrl}/EOS_DICE/img/time.png" alt="">
                            <p>
                                <ctx:message code="dice32" />
                            </p>
                        </div>
                        <div class="BettingLimits2-1">
                            <p>
                                <ctx:message code="dice33" />
                            </p>

                        </div>
                    </section>
                    <div class="rulesFooter">
                        <p>
                            <ctx:message code="dice34" />
                        </p>
                    </div>
                </div>

                <!-- 邀请好友页面 -->
                <div id="invuteFriends">
                    <div class="invuteFriendsTitle">
                        <p>
                            <ctx:message code="p12" />
                        </p>
                        <p>INVITE FRIENDS</p>
                        <p id="getBack1">
                            <ctx:message code="dice81" />
                        </p>
                    </div>
                    <section class="invuteFriendsContent">
                        <div class="TheNewSend">
                            <p>
                                <span>0.1</span>EOS</p>
                            <p>
                                <ctx:message code="p11" />
                            </p>
                            <p>
                                <ctx:message code="p111" />
                            </p>
                        </div>
                        <div class="invuteFriends1">
                            <p>
                                <span>0.1</span>EOS</p>
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
                        <div class="StandCommission">
                            <p>
                                <span>1.0</span> %</p>
                            <p>
                                <ctx:message code="p13" />
                            </p>
                            <p>
                                <ctx:message code="p131" />
                                <ctx:message code="p132" />
                                <ctx:message code="p133" />
                                <ctx:message code="dice86" />
                            </p>
                        </div>
                    </section>
                    <div class="TheInvitationWay">
                        <p>
                            <ctx:message code="p14" />
                        </p>
                        <div class="icon">
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
                        <div class="InvitedToIntroduce">
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
                        <div class="InvitedToIntroduce1">
                            <p>
                                <ctx:message code="P142" />
                            </p>
                            <p>
                                <ctx:message code="P143" />
                            </p>
                            <p>
                                <ctx:message code="P144" />
                            </p>
                        </div>
                        <div class="DuplicateFrame">
                            <input type="text" id="inviteInput" placeholder="<ctx:message code=" dice98 " />">
                            <p id="InviteCopy">
                                <ctx:message code="dice63" />
                            </p>
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
                <footer>
                    <p>EOS Casino@2018 All rights Reserved.</p>
        <div>
            <img src="${baseUrl}/EOS_DICE/img/CASINO .png" alt="">
            <div>
                <p id="GroupOfPlayers"> <ctx:message code="dice155" /></p>
                <p>|</p>
                <p id="ShareholdersOf"> <ctx:message code="dice156" /></p>
            </div>
            <img src="${baseUrl}/EOS_DICE/img/send.svg" alt="">
        </div>
        <div>
            <p>Please arrange your time reasonably, and do not over-indulge.</p>
            <p>If you reside in a location where lottery, gambling, sports betting or betting over the internet is illegal, please do not click on anything related to these activities on this site. You must be 21 years of age to click on any betting or gambling related items even if it is legal to do so in your location. Recognising that the laws and regulations involving online gaming are different everywhere, readers are advised to check with the laws that exist within their own jurisdiction to ascertain the legality of the activities which are covered.</p>
            <p>The games provided by EOS Casino are based on blockchain, fair and transparent. When you start playing these games, please note that online gambling and lottery is an entertainment vehicle and that it carries with it a certain degree of financial risk. Players should be aware of this risk, and govern themselves accordingly.</p>
        </div>
                </footer>
                <script>
                    let dice95 = "<ctx:message code="dice95" />";
                    let dice93 = "<ctx:message code="dice93" />";
                    let dice94 = "<ctx:message code="dice94" />";
                    let dice102 = "<ctx:message code="dice102" />";
                    let dice107 = "<ctx:message code="dice107" />";
                    let dice5 = "<ctx:message code="dice5" />";
                    let dice3 = "<ctx:message code="dice3" />";
                    let dice4 = "<ctx:message code="dice4" />";
                    let dice96 = "<ctx:message code="dice96" />";
                    let dice97 = "<ctx:message code="dice97" />";
                    let dice100 = "<ctx:message code="dice100" />";
                    let dice122 = "<ctx:message code="dice122" />";
                    let dice103 = "<ctx:message code="dice103" />";
                    let dice104 = "<ctx:message code="dice104" />";
                    let inputAmount = "<ctx:message code="inputAmount" />";
                    let dice6 = "<ctx:message code="dice6" />";
                    let dice7 = "<ctx:message code="dice7" />";
                    let lang = '${pageContext.response.locale}';
                </script>
                <script src="${baseUrl}/EOS_DICE/js/zepto.min.js"></script>
                <script src="${baseUrl}/EOS_DICE/js/dialog.min.js"></script>
                <script src="${baseUrl}/EOS_DICE/js/jquery-1.12.4.min.js"></script>
                <script src="${baseUrl}/EOS_DICE/js/message.min.js"></script>
                <script src="${baseUrl}/EOS_DICE/js/wapDiceHome.js"></script>
                <script src="${baseUrl}/EOS_DICE/js/wapball.js"></script>
            </body>

            </html>