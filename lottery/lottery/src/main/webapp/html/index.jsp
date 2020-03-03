<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ctx" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Compatible" content="ie=edge" />
  <link rel="stylesheet" href="../css/reset.css" />
  <link rel="stylesheet" href="../css/index.css" />
  <link rel="stylesheet" href="../css/gameRuler.css">
  <link rel="stylesheet" href="../css/invite.css">
  <link rel="stylesheet" href="../css/history.css">
  <link rel="stylesheet" href="../css/dialog.css">
  <link rel="icon" type="image/x-icon" href="/favicon.ico" />
  <title><ctx:message code="eosLottery1"/></title>
  <meta name="Keywords" content="<ctx:message code='eosLottery'/>"/>
  <script src="https://cdn.scattercdn.com/file/scatter-cdn/js/latest/scatterjs-core.min.js"></script>
  <script src="../js/eos.min.js"></script>
  <script src="https://cdn.scattercdn.com/file/scatter-cdn/js/latest/scatterjs-plugin-eosjs.min.js"></script>
  <script src="https://cdn.scattercdn.com/file/scatter-cdn/js/latest/scatterjs-plugin-web3.min.js"></script>
  <script src="https://cdn.scattercdn.com/file/scatter-cdn/js/latest/scatterjs-plugin-tron.min.js"></script>
  <script src="../js/jquery-1.12.4.min.js"></script>
  <script src="../js/jquery-ui-1.10.4.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/2.0.2/TweenMax.min.js"></script>
  <script src="../js/reconnecting-websocket.min.js"></script>
</head>

<body>
  <!-- 头部导航 -->
  <header id="guid">
    <div id="head">
      <div id="headLeft">
        <img src="../images/../images/CASINO .png" alt="logo" onclick="toIndex();"/>
        <div>
          <span id="rluerIntroduce"><ctx:message code="howTo"/></span>
          <span id="inviteBtn"><ctx:message code="invite"/></span>
          <span id="historyBtn"><ctx:message code="history"/></span>
        </div>
      </div>
      <div id="headMiddle" style="position:relative;">
        <div id="userInfomation" style="display:none">
          <div style="height:31px;line-height: 31px;">
            <i id="cpuImg"></i>
            <span id="cpu" style="font-size:14px;color:#3083de;"></span>
            <span id="userName" style="font-size:14px;color:white;font-weight: bolder;"></span>
            <i id="loginOut"></i>
          </div>
          <div style="height:31px;line-height:31px;">
            <img src="../images/eos.svg" alt="eos" />
            <span id="EosMoney" style="font-size:14px;color:#3083de;"></span>
            <img src="../images/2.png" alt="gos">
            <span id="GOSMoney" style="font-size:14px;color: #ff5c4a"></span>
          </div>
        </div>
        <div id="cpuContainer" style="display:none;background:#333a60;width:200px;height:100px;position:absolute;bottom:-100px;left:0px;text-align: center;box-shadow:1px -1px 1px #0b0d18;z-index:999;">
          <span style="font-size:14px;color:white;text-align:center;"><ctx:message code="cpuMortgage"/></span>
          <div style="display:flex;justify-content: space-between;width:140px;margin: auto;">
            <div style="display:flex;justify-content: space-between;width:100px;height:25px;background-color: #515778;line-height:25px;align-items: center;">
              <img src="../images/eos.svg" alt="" style="width:12px;height:12px;vertical-align:middle;">
              <input id="cpuInput" type="text" placeholder="输入对应的数值" style="width:86px;height: 25px;line-height: 25px;padding:0 2px;outline:none;color:white;background-color: #515778;font-size:12px;">
            </div>
            <div  id="cpuComfire" style="width:40px;height:25px;background: linear-gradient(to right, #ff5c4a , #ff8325); color: white;font-size: 12px;text-align: center;line-height: 25px;margin: auto;"><ctx:message code="sure"/></div>
          </div>
        </div>
        <div id="loginBtn"><ctx:message code="login"/></div>
    </div>
        
        <div id="headRight">
        <div id="LanguageBox" class="langCut">
          <div id="inputBox">
            <c:if test="${pageContext.response.locale eq 'zh_CN'}">
              <img src="../images/Singapore.png" alt="" id="guoqiBox" /><ctx:message code="chinese"/>
            </c:if>
            <c:if test="${pageContext.response.locale eq 'en_US'}">
              <img src="../images/guoqi02.png" alt="" id="guoqiBox" />English
            </c:if>
          </div>
          <ul id="language">
            <li id="Ch">
              <i></i><ctx:message code="chinese"/></li>
            <li id="En">
              <i></i>English</li>
          </ul>
        </div>
        <img src="../images/down.png" alt="down" id="changeLanguage" class="langCut"/>
      </div>
    </div>
  </header>

  
 <!-- 登录弹窗 -->
 <div id="loginContainer">
  <div id="loginBox">
    <div id="loginHead">
      <img src="../images/Lottery_logo.png" alt="lotteryLogo" />
      <i id="loginClose"></i>
    </div>
    <div id="loginStyleContainer">
      <div id="loginStyle">
        <div>
          <div id="myChainId"><ctx:message code="sydl"/></div>
          <div id="scatterLogin">
            <i id="scatterLogo"></i></div>
        </div>
        <div id="privateLogin">
          <input placeholder="<ctx:message code='placeholder'/>" id="userChainId" />
          <button id="loginComfire"><ctx:message code="login"/></button>
        </div>
        <div id="loadScatter">
          <div id="loadScatterMiidle">
            <img src="../images/scatterLoad.png" alt="scatterLoad" />
            <p><ctx:message code="clickScatterLogin"/></p>
            <button class="scatter_login"><ctx:message code="login"/></button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
  <!-- 主页内容 -->
  <div id="app">
      <!-- 开奖号码/记录显示 -->
      <div id="awardNumber">
        <!-- 开奖记录 -->
        <div id="lotteryRecord">
          <header><ctx:message code="results"/>
            <span id="more"><ctx:message code="more"/></span>
          </header>
          <section id="current">
            <div id="currentPeriods">
              <p id="qh"></p>
              <div style="display:flex;margin-top:10px;align-items:center;">
                  <p id="kjTime"></p>
                  <div id="nowAward" style="width:30px;height:12px;font-size:12px;color:white;text-align:center;display:none;line-height:10px;background-color:red;margin-left:5px;">now</div>
              </div>
            </div>
            <div id="currentNumber">
              <p>
                <span id="dx"></span>-
                <span id="ds"></span>
              </p>
              <p id="kjhm"></p>
            </div>
          </section>

          <section id="history">
            <header>
              <span><ctx:message code="nper"/></span>
              <span><ctx:message code="Time"/></span>
              <span><ctx:message code="Number"/></span>
              <span><ctx:message code="type"/></span>
            </header>
            <div id="messageBox">
              <div id="loadingHisAward" style="width:300px;height:300px;position:absolute;bottom:0;text-align:center;line-height:300px;display:none;"><img src="../images/loading.gif" alt="" style="width:40px;height:40px;"></div>
            </div>
          </section>
        </div>
        <!-- kafuka传过来的号码滚动展示 -->
        <div id="kafukaNumber">
          <header>
            <div><ctx:message code="nper"/></div>
            <div><ctx:message code="block"/></div>
            <div><ctx:message code="num"/></div>
            <div><ctx:message code="date"/></div>
          </header>
          <!-- 号码滚动显示区域 -->
          <div class="list_lh">
            <ul id="Marquee">
            
            </ul>
          </div>
        </div>
      </div>
      <!-- 下注奖金池区域 -->
      <div id="game">
        <!-- 奖金池 -->
        <div id="goldPool">
          <h3 id="nextPeriods"></h3>
          <div id="gameTime">
            <span><ctx:message code="endCurrent"/></span>
            &nbsp
            <div>00</div>
            <span>:</span>
            <div id="second"></div>
          </div>
          <p id="limit"><ctx:message code="limit"/></p>
          <div id="betsContainer">
            <div id="betsAll">
              <div id="betsUsers">0</div>
              <span id="percent"></span>
            </div>
          </div>
          <p id="betsBallTitle"><ctx:message code="pool"/></p>
          <div id="betsBall">
            <div>
              <canvas id="c"></canvas>
            </div>
          </div>
        </div>
        <!-- 下注 -->
        <div id="bets">
          <div id="modelChoice">
            <div id="baseModelBtn"><ctx:message code="basic"/></div>
            <div id="higherModelBtn"><ctx:message code="advanced"/></div>
          </div>
          <!-- 基础模式 -->
          <div id="baseModelBox">
            <div id="baseModel">
              <div id="betBase">
                <p><ctx:message code="bet"/>：</p>
                <div id="chooseNumber">
                  <div id="chooseNumberL">
                    <div id="small">
                      <p style="margin-top:4px;"><ctx:message code="small"/></p>
                      <p>0~4</p>
                    </div>
                    <div id="big">
                      <p style="margin-top:4px;"><ctx:message code="big"/></p>
                      <p>5~9</p>
                    </div>
                    <div id="single">
                      <p><ctx:message code="odd"/></p>
                    </div>
                    <div id="double">
                      <p><ctx:message code="even"/></p>
                    </div>
                  </div>
                  <div id="resetOne"><ctx:message code="reset"/></div>
                </div>
                <div id="numbers">
                  <div id="numberContainer">
                    <div class="numberBtn" data-check="off">0</div>
                    <div class="numberBtn" data-check="off">1</div>
                    <div class="numberBtn" data-check="off">2</div>
                    <div class="numberBtn" data-check="off">3</div>
                    <div class="numberBtn" data-check="off">4</div>
                    <div class="numberBtn" data-check="off">5</div>
                    <div class="numberBtn" data-check="off">6</div>
                    <div class="numberBtn" data-check="off">7</div>
                    <div class="numberBtn" data-check="off">8</div>
                    <div class="numberBtn" data-check="off">9</div>
                  </div>
                  <div id="allOne"><ctx:message code="selectAll"/></div>
                  <div id="resetTwo"><ctx:message code="reset"/></div>
                </div>
              </div>
              <div id="middle">
                <p>
                  <i></i><ctx:message code="combination"/></p>
                <p id="sbsd">[ ]</p>
                <p id="numbersBox">[ ]</p>
              </div>
              <div id="right">
                <p>
                  <i></i><ctx:message code="amount"/></p>
                <div class="Multiple">
                  <div style="width:130px;height:31px;background-color: #313862;display:flex;justify-content: space-between;position:relative;align-items: center;">
                    <input type="text" class="myBets" value="1"  style="display:flex;justify-content: space-between" />
                    <span style="font-size:12px;color:white;" class="moneyUnit">EOS</span>
                    <i class="chooseUnit" style="background-image: url('../images/arrowDown.svg');display: inline-block;width:16px;height: 16px;background-size: 16px 16px;"></i>
                    <ul class="unitList" style="width:122px;height:61px;display:none;background: #313862;position: absolute;bottom:-61px;;left:0;border-top:1px solid #24252c;">
                      <li class="Eos" style="width:112px;height:30px;padding-right:10px;text-align: right;color:white;font-size:12px;line-height: 31px;border-bottom: 1px solid #515778;">EOS</li>
                      <li class="Gos" style="width:112px;height:30px;padding-right:10px;text-align: right;color:white;font-size:12px;line-height: 30px;">GOS</li>
                    </ul>
                  </div>
                  <div id="MultipleChoose">
                    <div class="half">1/2</div>
                    <div class="doubles">2X</div>
                    <div class="max">MAX</div>
                  </div>
                </div>
                <div id="totalMoney">
                  <p><ctx:message code="count"/>
                    <span id="baseBets">0</span>
                  </p>
                  <p><ctx:message code="total"/>
                    <span id="baseTotalBets">0</span>
                  </p>
                </div>
                <button class="loginBtnBets"><ctx:message code="login"/></button>
                <button class="betsBtn"><ctx:message code="bet"/></button>
              </div>
            </div>
          </div>
          <!-- 高阶模式 -->
          <div id="higherModelBox" style="display:none">
            <div id="higherModel">
              <div id="betHigher">
                <p><ctx:message code="bet"/>：</p>
                <div id="num1Box">
                <div id="Hnumbers1">
                  <div id="HnumberContainer">
                    <div class="number1Btn" data-check="off">0</div>
                    <div class="number1Btn" data-check="off">1</div>
                    <div class="number1Btn" data-check="off">2</div>
                    <div class="number1Btn" data-check="off">3</div>
                    <div class="number1Btn" data-check="off">4</div>
                    <div class="number1Btn" data-check="off">5</div>
                    <div class="number1Btn" data-check="off">6</div>
                    <div class="number1Btn" data-check="off">7</div>
                    <div class="number1Btn" data-check="off">8</div>
                    <div class="number1Btn" data-check="off">9</div>
                  </div>
                  <div id="allTwo"><ctx:message code="selectAll"/></div>
                  <div id="resetTr"><ctx:message code="reset"/></div>
                </div>
              </div>
              <div id="num2Box">
                <div id="Hnumbers2">
                  <div id="HnumberContainer">
                    <div class="number2Btn" data-check="off">0</div>
                    <div class="number2Btn" data-check="off">1</div>
                    <div class="number2Btn" data-check="off">2</div>
                    <div class="number2Btn" data-check="off">3</div>
                    <div class="number2Btn" data-check="off">4</div>
                    <div class="number2Btn" data-check="off">5</div>
                    <div class="number2Btn" data-check="off">6</div>
                    <div class="number2Btn" data-check="off">7</div>
                    <div class="number2Btn" data-check="off">8</div>
                    <div class="number2Btn" data-check="off">9</div>
                  </div>
                  <div id="allThree"><ctx:message code="selectAll"/></div>
                  <div id="resetFr"><ctx:message code="reset"/></div>
                </div>
              </div>
              <div id="num3Box">
                <div id="Hnumbers3">
                  <div id="HnumberContainer">
                    <div class="number3Btn" data-check="off">0</div>
                    <div class="number3Btn" data-check="off">1</div>
                    <div class="number3Btn" data-check="off">2</div>
                    <div class="number3Btn" data-check="off">3</div>
                    <div class="number3Btn" data-check="off">4</div>
                    <div class="number3Btn" data-check="off">5</div>
                    <div class="number3Btn" data-check="off">6</div>
                    <div class="number3Btn" data-check="off">7</div>
                    <div class="number3Btn" data-check="off">8</div>
                    <div class="number3Btn" data-check="off">9</div>
                  </div>
                  <div id="allFour"><ctx:message code="selectAll"/></div>
                  <div id="resetFv"><ctx:message code="reset"/></div>
                </div>
              </div>
              <div id="num4Box">
                <div id="Hnumbers4">
                  <div id="HnumberContainer">
                    <div class="number4Btn" data-check="off">0</div>
                    <div class="number4Btn" data-check="off">1</div>
                    <div class="number4Btn" data-check="off">2</div>
                    <div class="number4Btn" data-check="off">3</div>
                    <div class="number4Btn" data-check="off">4</div>
                    <div class="number4Btn" data-check="off">5</div>
                    <div class="number4Btn" data-check="off">6</div>
                    <div class="number4Btn" data-check="off">7</div>
                    <div class="number4Btn" data-check="off">8</div>
                    <div class="number4Btn" data-check="off">9</div>
                  </div>
                  <div id="allFive"><ctx:message code="selectAll"/></div>
                  <div id="resetSx"><ctx:message code="reset"/></div>
                </div>
              </div>
                <div id="num5Box">
                <div id="Hnumbers5">
                  <div id="HnumberContainer">
                    <div class="number5Btn" data-check="off">0</div>
                    <div class="number5Btn" data-check="off">1</div>
                    <div class="number5Btn" data-check="off">2</div>
                    <div class="number5Btn" data-check="off">3</div>
                    <div class="number5Btn" data-check="off">4</div>
                    <div class="number5Btn" data-check="off">5</div>
                    <div class="number5Btn" data-check="off">6</div>
                    <div class="number5Btn" data-check="off">7</div>
                    <div class="number5Btn" data-check="off">8</div>
                    <div class="number5Btn" data-check="off">9</div>
                  </div>
                  <div id="allSix"><ctx:message code="selectAll"/></div>
                  <div id="resetSv"><ctx:message code="reset"/></div>
                </div>
              </div>
              </div>
              <div id="Hmiddle">
                <p>
                  <i></i><ctx:message code="combination"/></p>
                <p id="h1box">[ ]</p>
                <p id="h2box">[ ]</p>
                <p id="h3box">[ ]</p>
                <p id="h4box">[ ]</p>
                <p id="h5box">[ ]</p>
              </div>
              <div id="Hright">
                <p>
                  <i></i><ctx:message code="bits"/></p>
                <div id="stars">
                  <button id="oneStar">1</button>
                  <button id="twoStar">2</button>
                  <button id="threeStar">3</button>
                  <button id="fourStar">4</button>
                  <button id="fiveStar">5</button>
                </div>
                <p>
                  <i></i><ctx:message code="amount"/></p>
                <div class="Multiple">
                  <div style="width:130px;height:31px;background-color: #313862;display:flex;justify-content: space-between;position:relative;align-items: center;">
                    <input type="text" class="myBets" value="1"  style="display:flex;justify-content: space-between" />
                    <span style="font-size:12px;color:white;" class="moneyUnit">EOS</span>
                    <i class="chooseUnit" style="background-image: url('../images/arrowDown.svg');display: inline-block;width:16px;height: 16px;background-size: 16px 16px;"></i>
                    <ul class="unitList" style="width:122px;height:61px;display:none;background: #313862;position: absolute;bottom:-61px;;left:0;border-top:1px solid #24252c;">
                      <li class="Eos" style="width:112px;height:30px;padding-right:10px;text-align: right;color:white;font-size:12px;line-height: 31px;border-bottom: 1px solid #515778;">EOS</li>
                      <li class="Gos" style="width:112px;height:30px;padding-right:10px;text-align: right;color:white;font-size:12px;line-height: 30px;">GOS</li>
                    </ul>
                  </div>
                  <div id="MultipleChoose">
                    <div class="half">1/2</div>
                    <div class="doubles">2X</div>
                    <div class="max">MAX</div>
                  </div>
                </div>
                <div id="totalMoney">
                  <p><ctx:message code="count"/>
                    <span id="higherBets">0</span>
                  </p>
                  <p><ctx:message code="total"/>
                    <span id="higherTotalBets">0</span>
                  </p>
                </div>
                <button class="loginBtnBets"><ctx:message code="login"/></button>
                <button class="betsBtn"><ctx:message code="bet"/></button>
              </div>
            </div>
          </div>
        </div>
      </div>
     
      <!-- 投注直播和我的投注 -->
      <div id="live">
        <!-- 投注直播 -->
        <div id="betsLive">
          <p><ctx:message code="live"/></p>
        <div id="betsLiveContain"></div>
        
        </div>
        <!-- 我的投注 -->
        <div id="myOrders">
          <p><ctx:message code="mybets"/> <span id="moreBets"><ctx:message code="more"/></span></p>
          <div class="orderHead">
            <div class="orderHeadInner">
              <div><ctx:message code="issue"/></div>
              <div><ctx:message code="orderNum"/></div>
              <div><ctx:message code="Content"/></div>
              <div><ctx:message code="result"/></div>
              <div><ctx:message code="Amount"/></div>
              <div><ctx:message code="date"/></div>
            </div>
          </div>
          <div id="orderContainer"> </div>
          <div id="loadingHisBets" style="width:700px;height:260px;position:absolute;bottom:0;text-align:center;line-height:240px;display:none;"><img src="../images/loading.gif" alt="" style="width:40px;height:40px;"></div>
        </div>
      </div>
    </div>


  <!-- 游戏玩法介绍 -->
  <div id="rulerContainer">
    <!-- 标题 -->
    <div id="rulerTitle">
      <h1><ctx:message code="GameRule"/></h1>
      <span id="backToIndexG"><ctx:message code="back"/></span>
    </div>
    <!-- 开奖号码 -->
    <div id="awardNumberI">
      <p>
        <i></i><ctx:message code="EOSLottery"/> </p>
      <p><ctx:message code="p1"/> </p>
      <p>
        <ctx:message code="p2"/>
      </p>
      <%--<p>EOS区块号的ID是一个32字节的数字</p>--%>
      <%--<p>--%>
        <%--它是根据一系列非常复杂的加密算法随机生成的，通常的显示方式为16进制，而我们的开奖号码正是利用了这样一个真正的--%>
        <%--<br />随机的结果来作为开奖号码。--%>
      <%--</p>--%>
    </div>
    <!-- 游戏玩法 -->
    <div id="gameTimeI">
      <h3>
        <i></i><ctx:message code="p3"/></h3>
      <p><ctx:message code="p4"/>。</p>
    </div>
    <div id="gameRule">
      <h3>
        <i></i><ctx:message code="p5"/></h3>
      <h5><ctx:message code="p6"/></h5>
      <p><ctx:message code="p7"/></p>
      <p>
        <ctx:message code="p71"/>
      </p>
      <p>
        <ctx:message code="p72"/>
      </p>
      <h5><ctx:message code="p8"/></h5>
      <p>
        <ctx:message code="p81"/>
      </p>
      <p>
        <ctx:message code="p82"/>
      </p>
    </div>
    <div id="howToPlay">
      <h3>
        <i></i><ctx:message code="p10"/></h3>
      <p><ctx:message code="p101"/></p>
      <p><ctx:message code="p102"/></p>
      <p><ctx:message code="p103"/></p>
      <p><ctx:message code="p104"/></p>
      <p><ctx:message code="p105"/></p>
      <p><ctx:message code="p106"/></p>
      <p><ctx:message code="p107"/></p>
      <p><ctx:message code="p108"/></p>
      <p><ctx:message code="p109"/></p>
      <%--<h5>单双</h5>--%>
      <%--<p>以最后1个号码为兑奖号码，如果投注号码与兑奖号码的单双完全相同(0,2,4,6,8为双，1,3,5,7,9为单)，则视为中奖，返奖--%>
        <%--<br>倍数为2</p>--%>
      <%--<h5>大小</h5>--%>
      <%--<p>以最后1个号码为兑奖号码，如果投注号码与兑奖号码的大小完全相同(0-4为小，5-9为大)，则视为中奖，返奖倍数为2</p>--%>
      <%--<h5>五星</h5>--%>
      <%--<p>以全部5个号码为兑奖号码，如果投注号码与兑奖号码完全相同，则视为中奖，返奖倍数为100,000</p>--%>
      <%--<h5>四星</h5>--%>
      <%--<p>以最后4个号码为兑奖号码，如果投注号码与兑奖号码完全相同，则视为中奖，返奖倍数为10,000</p>--%>
      <%--<h5>三星</h5>--%>
      <%--<p>以最后3个号码为兑奖号码，如果投注号码与兑奖号码完全相同，则视为中奖，返奖倍数为1,000</p>--%>
      <%--<h5>二星</h5>--%>
      <%--<p>以最后2个号码为兑奖号码，如果投注号码与兑奖号码完全相同，则视为中奖，返奖倍数为100</p>--%>
      <%--<h5>一星</h5>--%>
      <%--<p> 以最后1个号码为兑奖号码，如果投注号码与兑奖号码完全相同，则视为中奖，返奖倍数为10</p>--%>
    </div>
  </div>


  <!-- 邀请好友 -->
  <div id="inviteContainer">
    <!-- 内容部分头部 -->
    <section id="inviteHead">
      <p><ctx:message code="p12"/> </p>
      <span id="backToindexI"><ctx:message code="back"/></span>
    </section>
    <!-- 第一部分，邀请奖励 -->
    <div id="inviteContent">
      <section id="invitePart1">
        <div id="newCome">
          <div>
            <span>0.1</span>&nbsp;
            <span>EOS</span>
          </div>
          <div>
            <h3><ctx:message code="p12"/></h3>
            <p><ctx:message code="p111"/></p>
          </div>
        </div>
        <div id="inviteFriends">
          <div>
            <span>0.1</span>&nbsp;
            <span>EOS</span>
          </div>
          <div>
            <h3><ctx:message code="p12"/></h3>
            <p><ctx:message code="p121"/></p>
            <p>
              <ctx:message code="p122"/>
            </p>
            <p>
              <ctx:message code="p123"/>
            </p>
          </div>
        </div>
        <div id="getAward">
          <div>
            <span>1.0</span>&nbsp;
            <span>%</span>
          </div>
          <div>
            <h3><ctx:message code="p13"/></h3>
            <p><ctx:message code="p131"/></p>
            <p>
              <ctx:message code="p132"/>
            </p>
            <p>
              <ctx:message code="p133"/>
            </p>
            <p><ctx:message code="p134"/></p>
          </div>
        </div>
      </section>
      <!-- 第二部分，邀请方式 -->
      <section id="invitePart2">
        <h1><ctx:message code="p12"/></h1>
        <div id="inviteDetails">
          <div id="icon">
            <i></i>
            <hr class="line" />
            <i></i>
            <hr class="line" />
            <i></i>
          </div>
          <div id="rulerWords">
            <p>
              <span><ctx:message code="copy"/> </span>
              <br />
              <span><ctx:message code="P142"/></span>
            </p>
            <p>
              <span><ctx:message code="loginBet"/> </span>
              <br />
              <span><ctx:message code="P143"/></span>
            </p>
            <p>
              <span><ctx:message code="refe"/></span>
              <br />
              <span><ctx:message code="P144"/></span>
            </p>
          </div>
          <div id="inviteLink">
              <input id="linkInput" type="text" placeholder='<ctx:message code="P15"/>' />
              <button id="copyUrl"><ctx:message code="copy1"/></button>
          </div>
        </div>
      </section>
      <!-- 第三部分，我的下级 -->
      <section id="invitePart3">
        <p><ctx:message code="mySubordinate"/>
          <span id="totalFriends"></span>
        </p>
        <div id="myFriendsBox" style="position:relative;">
          <div id="myFriendsHead">
              <div><ctx:message code="NO"/></div>
              <div><ctx:message code="userName"/></div>
              <div><ctx:message code="child"/></div>
              <div><ctx:message code="betAmount"/></div>
              <div><ctx:message code="money"/></div>
              <div><ctx:message code="rebate"/></div>
          </div>
        <div id="myFriendsContainner"></div>
          <div id="pageTurn">
            <div id="prePage"><img src="../images/arrowL.png" alt=""></div>
            <span id="curpage"></span>
            <div id="nextPage"><img src="../images/arrowR.png" alt=""></div>
          </div>
          <img id="noFriends" src="../images/noFriends.svg" alt="" style="width:300px;height:248px;position:absolute;left:50%;top:50%;margin-left:-150px;margin-top:-80px;display:none;">
        </div>
      </section>
    </div>
  </div>

<!-- 历史记录 -->
  <div id="historyContainer" style="display:none">
    <div id="historyHead">
      <h1><ctx:message code="HISTORY"/> <span id="backToIndexH"><ctx:message code="back"/></span></h1>
      <div id="historyTab">
        <div id="historyTabHead">
          <div><ctx:message code="TIME"/></div>
          <div><ctx:message code="qihao"/></div>
          <div>
            <div><ctx:message code="kjhm"/></div>
          </div>
          <div><ctx:message code="b"/><ctx:message code="s"/>|<ctx:message code="o"/><ctx:message code="e"/></div>
        </div>
        <div id="hisTbody"></div>
      </div>
    </div>
  </div>
  <div id="stop" style="width:100%;height:1260px;background:black;position:absolute;top:0;z-index:999;opacity:0;display:none;"></div>
  <div id="awardBack" style="display:none;width:100%;height:1260px;background:rgba(0,0,0,0.5);position:absolute;top:0;">
    <div id="awardContainer" style="text-align:center;position:absolute;top:50%;left:50%;background-color:#212540;width:600px;height:auto;;margin-left: -300px;margin-top:-150px;border-radius: 20px;">
        <p style="font-size:18px;margin-top:20px;color:#ff5c4a;"><ctx:message code="resultValidation"/></p>
        <div id="resultContain" style="width:450px;margin:auto;height:auto;margin-top:20px;margin-bottom:20px;color:#9fa0a7;">
          
        </div>
        <div style="width:260px;margin:auto;display:flex;justify-content: space-between;margin-bottom:20px;">
        <div style="font-size:14px;color:#9fa0a7;width:120px;"><span><ctx:message code="nper"/>：</span><span id="resultBlockNum" style="color:#3083de;"></span></div>
        <div style="font-size:14px;color:#9fa0a7;width:120px;"><span><ctx:message code="Number"/>：</span><span id="resultId" style="color:#ff5c4a;"></span></div>
        </div>
        <img id="resultClose" src="../images/close(2).svg" alt="" style="width:30px;height:30px;position:absolute;top:10px;right:10px;">
    </div>
  </div> 
</body>

<script>
  var englistBig = '<ctx:message code="b"/>';
  var englistSmall = '<ctx:message code="s"/>';
  var englistOdd = '<ctx:message code="o"/>';
  var englistEven = '<ctx:message code="e"/>';

  var englistBet = '<ctx:message code="bet"/>';
  var period1 = '<ctx:message code="period1"/>';
  var period2 = '<ctx:message code="period2"/>';
  var userCode = '${userCode}';
  var notInstalled = '<ctx:message code="notInstalled"/>';
  var logOut = '<ctx:message code="logOut"/>';
  var win = '<ctx:message code="win"/>';
  var ucerror = '<ctx:message code="ucerror"/>';
  var common = '<ctx:message code="common"/>';
  var people = '<ctx:message code="people"/>';
  var success = '<ctx:message code="success"/>';
  var loginSuccess = '<ctx:message code="loginSuccess"/>';
  var pleaseLogin = '<ctx:message code="pleaseLogin"/>';
  var cpuFail = '<ctx:message code="cpuFail"/>';
  var placeholderWap = '<ctx:message code="placeholderWap"/>';
  var cpuerror = '<ctx:message code="cpuerror"/>';
  var lang = '${pageContext.response.locale}';
  let dice126 = "<ctx:message code='dice126' />";
</script>
 
 <script src="../js/changeTime.js"></script>
 <script src="../js/login.js"></script>
 <script src="../js/index.js"></script>
 <script src="../js/bets.js"></script>
 <script src="../js/ball.js"></script>
 <script src="../js/timeDay.js"></script>
 <script src="../js/reconnecting-websocket.min.js"></script>
 <script src="../js/zepto.min.js"></script>
 <script src="../js/dialog.min.js"></script>
</html>