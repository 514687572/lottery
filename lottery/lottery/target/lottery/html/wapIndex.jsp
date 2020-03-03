<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ctx" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Compatible" content="ie=edge" />
  <title>手机端主页</title>
  <script src="../js/jquery.js"></script>
  <script src="../js/wapAutoSize.js"></script>
  <link rel="stylesheet" href="../css/reset.css" />
  <link rel="stylesheet" href="../css/wapIndex.css" />
  <link rel="stylesheet" href="../css/dialog.css">
</head>
<body>
<nav>
  <div id="navLeft">
    <img src="../images/lottery_logo.svg" alt="logo" /> <span>LOTTERY</span>
  </div>
  <button class="loginBtnBets" style="width:0.6rem;height:0.24rem;line-height:0.24rem;font-size:0.14rem;margin-top:0;border-radius:0.04rem;"><ctx:message code="login"/></button>
  <div id="navRight" style="position:relative;">
    <div id="LanguageBox">
      <div id="inputBox">
        <img
                src="../images/Singapore.png"
                alt="Singapore"
                id="guoqiBox"
        />&nbsp&nbsp中文
      </div>
      <ul id="language">
        <li id="Ch"><i></i>中文</li>
        <li id="En"><i></i>English</li>
      </ul>
    </div>
    <img src="../images/more.svg" alt="more" id="more"/>
    <img src="../images/close(01).svg" alt="close" id="close" >
  </div>
</nav>
<!-- 导航跳转 -->
<div id="navContainer" style="display:none;width:100%;height:11.2rem;background:rgba(0,0,0,0.9);position:absolute;z-index:999;">
  
  <div id="TogameRuler" style="padding:0 0.1rem;height:0.53rem;line-height:0.53rem;font-size:0.14rem;color: white;border-bottom: 1px solid #313862;">
    <i style="background-image:url('../images/ruler.svg');background-size:cover;width:0.2rem;height:0.2rem;display: inline-block;vertical-align: middle;"></i>
    <ctx:message code="howTo"/>
  </div>
  <div id="ToinviteFriends" style="padding:0 0.1rem;height:0.53rem;line-height:0.53rem;font-size:0.14rem;color: white;">
    <i style="background-image:url('../images/usersInfo.svg');background-size:cover;width:0.2rem;height:0.2rem;display: inline-block;vertical-align: middle;"></i>
    <ctx:message code="invite"/>
  </div>
</div>
<!-- 登录弹窗 -->
<div id="loginContainer" style="display:none;width:100%;height:11.2rem;background:rgba(0,0,0,0.9);position:absolute;z-index:999;">
  <div style="width:3.03rem;height:2.2rem;padding-top:0.2rem;background:#313862;margin:auto;border-radius:0.1rem;text-align:center;margin-top:1.6rem;">
      <div style="display:flex;justify-content: space-between;padding:0 0.2rem;">
        <div id="privateTitle" style="font-size:0.14rem;color:#3083de;;width:1.5rem;height:0.3rem;text-align: center;line-height:0.3rem;border-bottom: 0.01rem solid #3083de;"><ctx:message code='sydl'/></div>
        <div id="phoneTitle" style="font-size:0.14rem;color:white;width:1.5rem;height:0.3rem;text-align: center;line-height:0.3rem;border-bottom: 0.01rem solid #484956;"><ctx:message code='QuickLogin'/></div>
      </div>
      <div id="privateContain" style="display:block;">
          <input type="text" placeholder="<ctx:message code='placeholder'/>" id="userChainId" style="width:2.64rem;height:0.3rem;line-height:0.3rem;color:#4d578f;border:none;font-size:0.1rem;background:#313862;border-bottom: 1px solid #4d578f;outline:none;float:left;margin-left:0.2rem;margin-top: 0.3rem;">
          <div style="width:2.64rem;height:0.3rem;margin-top:0.2rem;">
            <input type="button" value="<ctx:message code='login'/>" id="loginComfire" style="width:2.64rem;height:0.3rem;font-size:0.14rem;color:white;border-radius:0.08rem;border:none;text-align:center;line-height:0.3rem;outline:none;background:linear-gradient(to right, #ff5c4a , #ff8325);float:left;margin-top: 0.4rem;margin-left:0.2rem;">
          </div>
      </div>
      <div id="phoneContain" style="display:none;">
        <input type="text" placeholder="<ctx:message code='phoneMail'/>" id="userPhone" style="width:2.64rem;height:0.3rem;line-height:0.3rem;color:#4d578f;border:none;font-size:0.1rem;background:#313862;border-bottom: 1px solid #4d578f;outline:none;float:left;margin-left:0.2rem;margin-top: 0.1rem;">
        <input type="password" placeholder="<ctx:message code='passwordInput'/>" id="userPassword" style="width:2.64rem;height:0.3rem;line-height:0.3rem;color:#4d578f;border:none;font-size:0.1rem;font-size:0.14rem;background:#313862;border-bottom: 1px solid #4d578f;outline:none;float:left;margin-left:0.2rem;margin-top: 0.1rem;">
        <div style="width:2.64rem;height:0.3rem;">
          <input type="button" value="<ctx:message code='login'/>" id="quickComfire" style="width:2.64rem;height:0.3rem;font-size:0.14rem;color:white;border-radius:0.08rem;border:none;text-align:center;line-height:0.3rem;outline:none;background:linear-gradient(to right, #ff5c4a , #ff8325);float:left;margin-top: 0.2rem;margin-left:0.2rem;">
        </div>
        <div style="width:2.64rem;height:0.3rem;">
          <input type="button" value="<ctx:message code='Register'/>" class="registerComfire" style="width:2.64rem;height:0.3rem;font-size:0.14rem;color:white;border-radius:0.08rem;border:none;text-align:center;line-height:0.3rem;outline:none;background:linear-gradient(to right, #ff5c4a , #ff8325);float:left;margin-top: 0.1rem;margin-left:0.2rem;">
        </div> 
      </div>
         
  </div>
  <div id="loginClose" style="width:0.32rem;height:0.32rem;background-image:url('../images/close(2).svg');margin:auto;margin-top: 0.4rem;background-size:0.32rem 0.32rem;background-repeat: no-repeat;"></div>
</div>

 <!-- 抵押CPU弹窗 -->
 <div id="cpuContainer" style="display:none;width:100%;height:11.2rem;background:rgba(0,0,0,0.9);position:absolute;z-index:999;">
  <div style="width:3.03rem;height:1.75rem;padding-top:0.2rem;background:#313862;margin:auto;border-radius:0.1rem;text-align:center;margin-top:1.6rem;">
       <p style="font-size:0.14rem;color:white;"><ctx:message code="cpuMortgage"/></p>
       <input type="text" placeholder="<ctx:message code='inputCupMoney'/>" id="cpuInput" style="width:2.64rem;height:0.3rem;line-height:0.3rem;color:#4d578f;border:none;font-size:0.1rem;background:#313862;border-bottom: 1px solid #4d578f;outline:none;float:left;margin-left:0.2rem;margin-top: 0.4rem;">
       <input type="button" value="<ctx:message code='sure'/>" id="cpuComfire" style="width:2.64rem;height:0.3rem;font-size:0.14rem;color:white;border-radius:0.08rem;border:none;text-align:center;line-height:0.3rem;outline:none;background:linear-gradient(to right, #ff5c4a , #ff8325);float:left;margin-top: 0.4rem;margin-left:0.2rem;">
  </div>
  <div id="cpuClose" style="width:0.32rem;height:0.32rem;background-image:url('../images/wapClose.png');margin:auto;margin-top: 0.4rem;background-size:0.32rem 0.32rem;background-repeat: no-repeat;"></div>
</div>

<div id="tips" style="display:none;width:100%;height:11.2rem;background:rgba(0,0,0,0.9);position:absolute;z-index:999;">
  <div style="width:3.03rem;height:2rem;padding-top:0.2rem;background:#313862;margin:auto;border-radius:0.1rem;text-align:center;margin-top:1.6rem;">
       <img src="../images/tipsIcon.svg" alt="" style="width:1rem;height:1rem;">
       <p style="font-size:0.14rem;color:white;text-align: center;"><ctx:message code="loginJudge"/></p>
       <div style="width:2.64rem;display:flex;justify-content: space-between;vertical-align: center;margin:auto;margin-top:0.2rem;">
        <input type="button" value="<ctx:message code='wantLogin'/>" id="gotoLogin" style="width:1.2rem;height:0.3rem;font-size:0.14rem;color:white;border-radius:0.08rem;border:none;text-align:center;line-height:0.3rem;outline:none;background:linear-gradient(to right, #ff564f , #ff8523);float:left;margin-left:0.2rem;">
        <input type="button" value="<ctx:message code='wantRegister'/>" id="gotoRegister" style="width:1.2rem;height:0.3rem;font-size:0.14rem;color:white;border-radius:0.08rem;border:none;text-align:center;line-height:0.3rem;outline:none;background:linear-gradient(to right, #ff564f , #ff8523);float:left;margin-left:0.2rem;">
       </div>
       
  </div>
</div>

<!-- 用户信息 -->
<div id="userInfo" style="display:none;">
  <div id="userInfoContainer" style="display:none">
    <div id="userEos">
    <div id="userInfoLeft">
      <img src="../images/usersInfo.svg" alt="user" />
      <span id="userName" style="font-weight:bolder;"></span>
      <img src="../images/eos.svg" alt="eos" />
      <span id="EosMoney" style="font-size:14px;color:#3083de;"></span>
      <img src="../images/2.png" alt="gos" >
      <span id="GOSMoney" style="font-size:14px;color: #ff5c4a"></span>
    </div>
    <div id="cpuImg">
      <div id="totalCpu" style="width:0.6rem;height:0.1rem;background:#252a48;border-radius:0.08rem;position: relative;">
        <div id="userCpu" style="position:absolute;left:0;top:0;height:0.1rem;background:#3097ff;border-radius:0.08rem;"></div>
      </div>
      <span id="cpu"></span>
    </div>
    <img src="../images/loginOut.svg" alt="" style="width:0.16rem;height:0.16rem;background-size:0.16rem 0.16rem;background-repeat:no-repeat;vertical-align:middle;" id="exitLogin">
  </div>
  </div>
  <div style="display:none;font-size:0.14rem;color:white;" id="phoneContainer">
      <div style=" height:0.32rem;width:2rem;display: flex;justify-content: space-between; align-items: center;">
        <img src="../images/usersInfo.svg" alt="user1" style="margin-right:0.05rem;"/>
        <span id="telephone" style="color:#3083de;margin-right:0.1rem;font-size:0.14rem;"></span>
        <img src="../images/jifen.svg" alt="eos1" style="margin-right:0.05rem;"/>
        <span style="font-size:0.14rem;color:#ff564f;" id="integral"></span>
        <img src="../images/chongzhi.svg" alt="" style="margin-left:0.5rem;width:0.2rem;height:0.2rem;vertical-align:middle;" id="chongzhi">
        <img src="../images/tixian.svg" alt="" style="margin-left:0.2rem;width:0.22rem;height:0.22rem;background-size:0.14rem 0.16rem;background-repeat:no-repeat;vertical-align:middle;" id="tixian">
        <img src="../images/loginOutWap.svg" alt="" style="margin-left:0.2rem;width:0.2rem;height:0.2rem;background-size:0.14rem 0.16rem;background-repeat:no-repeat;vertical-align:middle;" id="QuickOut">
      </div>
    </div>
</div>
<div id="App" style="align-items:center;position:relative;display:block;">
  <!-- 投注直播 -->
  <div id="betsLiveRow" style="padding:0 0.1rem;">
    <div id="awardContainer" style="height:1rem;width:1.2rem;">
    </div>
    <div style="height:0.85rem;width:0.01rem;background:#292e4e;vertical-align: middle;">
    </div>
    <div id="betLiveContainer" style="height:1rem;width:2.2rem;overflow: hidden;">
    </div>
  </div>
  <!-- hash值轮播 -->
  <div id="hash" style="padding:0 0.1rem;">
    <div id="hashPlay"></div>
  </div>
  <!-- 投注计时 -->
  <div id="betTime">
    <div id="betTimeLeft">
      <p id="periods"></p>
      <div style="align-items:middle;text-align: center;">
          <span><ctx:message code="endCurrent"/> </span>
          <span style="width:0.16rem;height:0.16rem;display:inline-block;background:#ff564f;margin-left:0.02rem;line-height: 0.16rem;">00</span>
          <span style="margin-right:0.04rem;">:</span><span id="timeLeft" style="width:0.16rem;height:0.16rem;display:inline-block;background:#ff564f;line-height: 0.16rem;">00</span>
      </div>
    </div>
    <div id="betTimeMiddle">
      <p><ctx:message code="limit"/> </p>
      <div  id="betsAll"style="width:1rem;height:0.15rem;background:#252a48;position: relative;line-height: 0.15rem;text-align: center;">
        <div id="betsUsers" style="position:absolute;left:0;top:0;height:0.15rem;background:#ff8523;"></div>
        <span id="percent" style="z-index:999"></span>
      </div>
    </div>
    <div id="betTimeRight" style="align-items:center;">
        <div>
            <canvas id="c"></canvas>
        </div>
      <div>
        <p><ctx:message code="pool"/> </p>
        <p id="bonus"></p>
      </div>
    </div>
  </div>
  <!-- 玩家投注 -->
  <div>
    <!-- 基础模式 -->
    <div id="baseModelBox" style="display:block;">
      <div id="baseModel">
        <div id="bet">
          <div id="chooseNumber">
            <div id="chooseNumberL">
              <div id="small">
                <p><ctx:message code="s"/> </p>
              </div>
              <div id="big">
                <p><ctx:message code="b"/></p>
              </div>
              <div id="single">
                <p><ctx:message code="o"/></p>
              </div>
              <div id="double">
                <p><ctx:message code="e"/></p>
              </div>
            </div>
            <div id="resetOne"><ctx:message code="reset"/></div>
            <span><ctx:message code="model"/>:</span>
            <div style="width:0.48rem;height:0.24rem;background:#ff564f;border-radius:0.4rem;display:flex;justify-content: space-between">
              <div class="baseBetsBtn" style="width:0.24rem;height:0.24rem;text-align: center;line-height: 0.24rem;border-radius:0.1rem 0 0 0.1rem;background: #ff564f"><ctx:message code="early"/></div>
              <div class="higherBetsBtn" style="width:0.24rem;height:0.24rem;text-align: center;line-height: 0.24rem;border-radius:0 0.1rem 0.1rem 0;background:#252a48"><ctx:message code="hight"/></div>
            </div>
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
        <div class="bottom" style="text-align:center;">
          <div style="display:flex;justify-content: space-between;align-items: center;background-color: #282e50;padding: 0 0.1rem">
              <p><i style="vertical-align:middle;width:0.13rem;height:0.2rem;background-image:url('../images/eos.svg');background-size:0.13rem 0.2rem;display: inline-block;"></i><ctx:message code="amount"/>:</p>
            <div class="Multiple">
              <div style="width:1.4rem;height:0.2rem;background-color: #313862;display:flex;justify-content: space-between;position:relative;align-items: center;">
                <input type="text" class="myBets" value="1"  style="width:0.8rem;height:0.2rem;background:#313862;border:none;color: white;padding-left:0.1rem;outline:none;"/>
                <span style="font-size:0.12rem;color:white;" class="moneyUnit">EOS</span>
                <i class="chooseUnit" style="background-image: url('../images/arrowDown.svg');display: inline-block;width:0.12rem;height:0.12rem;background-size:0.12rem 0.12rem;"></i>
                <ul class="unitList" style="width:1.4rem;height:0.61rem;display:none;background: #313862;position: absolute;bottom:-61px;;left:0;border-top:1px solid #24252c;">
                  <li class="Eos" style="width:1.2rem;height:0.3rem;padding:0 0.1rem;text-align: right;color:white;font-size:0.12rem;line-height:0.31rem;border-bottom: 1px solid #515778;">EOS</li>
                  <li class="Gos" style="width:1.2rem;height:0.3rem;padding:0 0.1rem;text-align: right;color:white;font-size:0.12rem;line-height:0.3rem;">GOS</li>
                </ul>
              </div>
              <div id="MultipleChoose">
                <div class="half" style="width:0.2rem;height:0.2rem;text-align: center;line-height: 0.2rem;border-radius:0.02rem">1/2</div>
                <div class="doubles" style="width:0.2rem;height:0.2rem;text-align: center;line-height: 0.2rem;border-radius:0.02rem">2X</div>
                <div class="max" style="width:0.4rem;height:0.2rem;text-align: center;line-height: 0.2rem;border-radius:0.02rem">MAX</div>
              </div>
            </div>
          </div>
          <div id="totalMoney">
            <p><ctx:message code="count"/> <span id="baseBets" style="color:#b44141;">0</span></p>
            <p><ctx:message code="total"/> <span id="baseTotalBets" style="color:#b44141;">0</span></p>
          </div>
          <button class="loginBtnBets" style="margin:auto"><ctx:message code="login"/> </button>
          <button class="betsBtn" style="margin:auto"><ctx:message code="bet" /> </button>
        </div>
      </div>
    </div>
    <div id="higherModelBox" style="display:none">
      <div id="higherModel" style="color:white;">
        <div id="bet">
          <div id="num1Box">
            <div id="Hnumbers1">
              <div class="HnumberContainer">
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
              <div class="HnumberContainer">
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
              <div class="HnumberContainer">
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
              <div class="HnumberContainer">
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
              <div class="HnumberContainer">
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
        <div style="display:flex;justify-content:space-between;align-items:center;height:0.32rem;color: white;margin-bottom: 0.1rem;margin-top: 0.1rem">
          <div style="display:flex;justify-content:space-between;align-items:center;height:0.32rem;width: 2rem">
            <p><ctx:message code="bits"/> :</p>
            <div id="stars">
              <button id="oneStar">1</button>
              <button id="twoStar">2</button>
              <button id="threeStar">3</button>
              <button id="fourStar">4</button>
              <button id="fiveStar"  style="background:#ff8325;">5</button>
            </div>
          </div>
          <div style="display:flex;justify-content: space-between;align-items:center">
            <span><ctx:message code="model"/> :</span>
            <div style="width:0.48rem;height:0.24rem;background:#ff564f;border-radius:0.4rem;display:flex;justify-content: space-between">
              <div class="baseBetsBtn" style="width:0.24rem;height:0.24rem;text-align: center;line-height: 0.24rem;border-radius:0.1rem 0 0 0.1rem;background: #252a48"><ctx:message code="early"/></div>
              <div class="higherBetsBtn" style="width:0.24rem;height:0.24rem;text-align: center;line-height: 0.24rem;border-radius:0 0.1rem 0.1rem 0;"><ctx:message code="hight"/></div>
            </div>
          </div>
        </div>
        <div class="bottom" style="text-align:center;">
          <div style="display:flex;justify-content: space-between;align-items: center;background-color: #282e50;padding: 0 0.1rem">
            <p><i style="vertical-align:middle;width:0.13rem;height:0.2rem;background-image:url('../images/eos.svg');background-size:0.13rem 0.2rem;display: inline-block;"></i><ctx:message code="amount"/>:</p>
            <div class="Multiple">
              <div style="width:1.4rem;height:0.2rem;background-color: #313862;display:flex;justify-content: space-between;position:relative;align-items: center;">
                <input type="text" class="myBets" value="1"  style="width:0.8rem;height:0.2rem;background:#313862;border:none;color: white;padding-left:0.1rem;outline:none;"/>
                <span style="font-size:0.12rem;color:white;" class="moneyUnit">EOS</span>
                <i class="chooseUnit" style="background-image: url('../images/arrowDown.svg');display: inline-block;width:0.12rem;height:0.12rem;background-size:0.12rem 0.12rem;"></i>
                <ul class="unitList" style="width:1.4rem;height:0.61rem;display:none;background: #313862;position: absolute;bottom:-61px;;left:0;border-top:1px solid #24252c;">
                  <li class="Eos" style="width:1.2rem;height:0.3rem;padding:0 0.1rem;text-align: right;color:white;font-size:0.12rem;line-height:0.31rem;border-bottom: 1px solid #515778;">EOS</li>
                  <li class="Gos" style="width:1.2rem;height:0.3rem;padding:0 0.1rem;text-align: right;color:white;font-size:0.12rem;line-height:0.3rem;">GOS</li>
                </ul>
              </div>
              <div id="MultipleChoose">
                <div class="half" style="width:0.2rem;height:0.2rem;text-align: center;line-height: 0.2rem;border-radius:0.02rem">1/2</div>
                <div class="doubles" style="width:0.2rem;height:0.2rem;text-align: center;line-height: 0.2rem;border-radius:0.02rem">2X</div>
                <div class="max" style="width:0.2rem;height:0.2rem;text-align: center;line-height: 0.2rem;border-radius:0.02rem">MAX</div>
              </div>
            </div>
          </div>
          <div id="totalMoney">
            <p><ctx:message code="count"/> <span id="higherBets" style="color:#b44141;">0</span></p>
            <p><ctx:message code="total"/> <span id="higherTotalBets" style="color:#b44141;">0</span></p>
          </div>
          <button class="loginBtnBets"><ctx:message code="login"/> </button>
          <button class="betsBtn"><ctx:message code="bet"/> </button>
        </div>
      </div>
    </div>
  </div>
  <!-- 开奖记录和我的投注 -->
  <div id="myHis" style="position:relative;">
      <div id="loading" style="width:100%;height:3.3rem;background-color:rgba(0,0,0,0.5);position:absolute;bottom:0;text-align:center;line-height: 3.3rem;display:none;"><img src="../images/loading.gif" alt="" style="width:0.6rem;height:0.6rem"></div>
    <div style="height:0.38rem;line-height:0.38rem;color: white;font-size:0.14rem;display: flex;justify-content: space-between;">
      <div id="hisAward" style="width:50%;text-align:center;"><ctx:message code="results"/> </div>
      <div id="hisBets" style="width:50%;text-align:center;color:#6a727d;"><ctx:message code="mybets"/> </div>
    </div>
    <div id="myHisBets" style="display:none;height:3rem">
      <div style="padding:0 0.1rem;height:0.3rem;line-height: 0.3rem;font-size:0.12rem;color:white;text-align: center;display: flex;justify-content: space-between">
        <div style="width: 0.64rem;"><ctx:message code="issue"/> </div>
        <div style="width: 0.60rem;"><ctx:message code="Content"/></div>
        <div style="width: 0.28rem;"><ctx:message code="type"/></div>
        <div style="width: 0.60rem;"><ctx:message code="Amount"/> </div>
        <div style="width: 0.68rem;"><ctx:message code="date"/></div>
      </div>
      <div id="myHisBetTab" style="height:2.8rem;overFlow:auto;position:relative;" >

      </div>
      <div style="position:absolute;top:50%;left:50%;margin-left:-1.5rem;margin-top:-0.2rem;width:3rem;background-color:rgba(0,0,0,0.5);height:0.4rem;line-height:0.4rem;text-align:center;color:white;display:block;font-size:0.12rem;display:none;" id="totalBetsNum"></div>
    </div>
    <div id="myHisAward" style="display:block;height:3rem">
      <div style="padding:0 0.1rem;height:0.3rem;line-height: 0.3rem;font-size:0.12rem;color:white;text-align: center;display: flex;justify-content: space-between">
        <div style="width: 0.72rem;"><ctx:message code="date"/></div>
        <div style="width: 0.68rem;"><ctx:message code="issue"/></div>
        <div style="width: 0.58rem;"><ctx:message code="kjhm"/> </div>
        <div style="width: 0.70rem;"><ctx:message code="b"/><ctx:message code="s"/>|<ctx:message code="o"/><ctx:message code="e"/></div>
      </div>
      <div id="myHisAwardTab" style="height:2.8rem;overFlow:auto">

      </div>
    </div>
  </div>
</div>

<!-- 游戏规则 -->
<div id="gameRuler" style="display:none;background-color:#1c1e2d">
  <h1 style="font-size:0.2rem;color:white;text-align:center;height:0.82rem;line-height: 0.82rem;"><ctx:message code="GameRule"/> </h1>

  <div id="gameRulerPartOne" style="height:2.22rem;">
    <p style="font-size:0.16rem;color:white;"><i style="background-image:url('../images/img01.png'); background-size:0.3rem 0.3rem;width:0.3rem;height:0.3rem;display:inline-block;vertical-align: middle;"></i>&nbsp<ctx:message code="kjhm"/></p>
    <div style="margin-left:0.34rem;margin-top:0.1rem;">
      <p style="font-size:0.12rem;color:white;"><ctx:message code="p1"/> </p>
      <div style="font-size:0.12rem;color:#4d578f;width:3.12rem;line-height: 0.2rem;"><ctx:message code="p2"/> </div>
    </div>
  </div>

  <div id="gameRulerPartTwo" style="height:0.8rem;">
    <p style="font-size:0.16rem;color:white;"><i style="background-image:url('../images/img03.png'); background-size:0.3rem 0.3rem;width:0.3rem;height:0.3rem;display:inline-block;vertical-align: middle;"></i>&nbsp<ctx:message code="p3"/></p>
    <div style="margin-left:0.34rem;margin-top:0.1rem;">
      <div style="font-size:0.12rem;color:#4d578f;width:3.12rem;line-height: 0.2rem;"><ctx:message code="p4"/></div>
    </div>
  </div>

  <div id="gameRulerPartThree" style="height:4.55rem;">
    <p style="font-size:0.16rem;color:white;"><i style="background-image:url('../images/img04.png'); background-size:0.3rem 0.3rem;width:0.3rem;height:0.3rem;display:inline-block;vertical-align: middle;"></i>&nbsp<ctx:message code="p5"/></p>
    <div style="margin-left:0.34rem;margin-top:0.1rem;">
      <p style="font-size:0.12rem;color:white;"><ctx:message code="p6"/></p>
      <div style="font-size:0.12rem;color:#4d578f;width:3.12rem;line-height: 0.2rem;"><ctx:message code="p7"/>
        <br><ctx:message code="p71"/>
        <br><ctx:message code="p72"/></div>
    </div>
    <div style="margin-left:0.34rem;margin-top:0.1rem;">
      <p style="font-size:0.12rem;color:white;"><ctx:message code="p8"/></p>
    </div>
    <div style="margin-left:0.34rem;margin-top:0.1rem;">
      <div style="font-size:0.12rem;color:#4d578f;width:3.12rem;line-height: 0.2rem;"><ctx:message code="p81"/></div>
    </div>
    <div style="margin-left:0.34rem;margin-top:0.1rem;">
      <div style="font-size:0.12rem;color:#4d578f;width:3.12rem;line-height: 0.2rem;"><ctx:message code="p82"/></div>
    </div>
  </div>

  <div id="gameRulerPartFour" style="height:6.0rem;">
    <p style="font-size:0.16rem;color:white;"><i style="background-image:url('../images/img02.png'); background-size:0.3rem 0.3rem;width:0.3rem;height:0.3rem;display:inline-block;vertical-align: middle;"></i>&nbsp<ctx:message code="p10"/> </p>
    <p style="font-size:0.12rem;color:#4d578f;margin-left:0.34rem;margin-top:0.1rem;"><ctx:message code="p101"/></p>
    <p style="font-size:0.12rem;color:#4d578f;margin-left:0.34rem;margin-top:0.1rem;"><ctx:message code="p102"/></p>
    <p style="font-size:0.12rem;color:#4d578f;margin-left:0.34rem;margin-top:0.1rem;"><ctx:message code="p103"/></p>
    <p style="font-size:0.12rem;color:#4d578f;margin-left:0.34rem;margin-top:0.1rem;"><ctx:message code="p104"/></p>
    <p style="font-size:0.12rem;color:#4d578f;margin-left:0.34rem;margin-top:0.1rem;"><ctx:message code="p105"/></p>
    <p style="font-size:0.12rem;color:#4d578f;margin-left:0.34rem;margin-top:0.1rem;"><ctx:message code="p106"/></p>
    <p style="font-size:0.12rem;color:#4d578f;margin-left:0.34rem;margin-top:0.1rem;"><ctx:message code="p107"/></p>
    <p style="font-size:0.12rem;color:#4d578f;margin-left:0.34rem;margin-top:0.1rem;"><ctx:message code="p108"/></p>
    <p style="font-size:0.12rem;color:#4d578f;margin-left:0.34rem;margin-top:0.1rem;"><ctx:message code="p109"/></p>
  </div>
</div>

<!-- 邀请好友 -->
<div id="inviteFriends" style="display:none;background-color:#1c1e2d;padding:0 0.1rem;">
  <h1 style="font-size:0.2rem;color:white;text-align:center;height:0.82rem;line-height: 0.82rem;"><ctx:message code="p12"/> </h1>
  <div style="width:3.06rem;height:1.3rem;margin:auto;border-bottom:0.01rem dotted #4d4f56;margin-top: 0.1rem;">
    <p style="display:flex;align-items: baseline;"><span style="font-size:0.3rem;color:white;">0.1</span><span style="font-size:0.16rem;color:white;">EOS</span></p>
    <p style="font-size:0.12rem;color:white;margin-top: 0.1rem;"><ctx:message code="p11"/> </p>
    <p style="font-size:0.12rem;color:#494f78;margin-top:0.1rem;"><ctx:message code="p111"/></p>
  </div>

  <div id="inviteFriendsPartTwo" style="width:3.06rem;height:2.0rem;margin:auto;border-bottom:0.01rem dotted #4d4f56;margin-top: 0.1rem;">
    <p style="display:flex;align-items: baseline;"><span style="font-size:0.3rem;color:white;">0.1</span><span style="font-size:0.16rem;color:white;">EOS</span></p>
    <p style="font-size:0.12rem;color:white;margin-top: 0.1rem;"><ctx:message code="p12"/> </p>
    <p style="font-size:0.12rem;color:#494f78;margin-top:0.1rem;line-height: 0.2rem;">
      <ctx:message code="p121"/>
      <ctx:message code="p122"/>
      <ctx:message code="p123"/>
    </p>
  </div>

  <div id="inviteFriendsPartThree" style="width:3.06rem;height:2.0rem;margin:auto;border-bottom:0.01rem dotted #4d4f56;margin-top: 0.1rem;">
    <p style="display:flex;align-items: baseline;"><span style="font-size:0.3rem;color:white;">1.0</span><span style="font-size:0.16rem;color:white;">%</span></p>
    <p style="font-size:0.12rem;color:white;margin-top: 0.1rem;"><ctx:message code="p13"/> </p>
    <p style="font-size:0.12rem;color:#494f78;margin-top:0.1rem;line-height: 0.2rem;">
      <ctx:message code="p131"/>
      <ctx:message code="p132"/>
      <ctx:message code="p133"/>
      <ctx:message code="p134"/>
    </p>
  </div>

  <section id="invitePart2" style="height:3.14rem;margin-top:0.2rem;font-size:0.12rem;color:white;text-align: center;">
    <h1 style="font-weight: lighter;margin-top:0.2rem;font-size:0.2rem;"><ctx:message code="p12"/> </h1>
    <div id="inviteDetails" style="height:1.87rem;margin:auto;margin-top:0.25rem;">
        <div id="icon" style="height:0.44rem;margin:auto;display:flex;justify-content: space-between;">
            <i style="width:0.4rem;height:0.4rem;border-radius:0.4rem;display:inline-block;background:#3e425c url('../images/send.svg') no-repeat center;background-size:0.2rem 0.2rem;"></i>
            <hr class="line" style="width:1rem;height:0.01rem;border-top:0.01rem solid #4d5569;margin-top:0.2rem;"/>
            <i style="width:0.4rem;height:0.4rem;border-radius:0.4rem;display:inline-block;background:#3e425c url('../images/loginOut.svg') no-repeat center;background-size:0.2rem 0.2rem;"></i>
            <hr class="line" style="width:1rem;height:0.01rem;border-top:0.01rem solid #4d5569;margin-top:0.2rem;"/>
            <i style="width:0.4rem;height:0.4rem;border-radius:0.4rem;display:inline-block;background:#3e425c url('../images/friends.svg') no-repeat center;background-size:0.2rem 0.2rem;"></i>
        </div>
      <div id="rulerWords" style="height:1.02rem;display:flex;justify-content: space-between;margin: auto;margin-top:0.16rem;font-size:0.12rem">
        <p style="width:0.7rem;text-align: left;">
          <span><ctx:message code="copy"/> </span>
          <br />
          <span style="color:#4d578f;"><ctx:message code="P142"/> </span>
        </p>
        <p style="width:0.9rem;text-align: center;">
          <span><ctx:message code="loginBet"/></span>
          <br />
          <span style="color:#4d578f;">
            <ctx:message code="P143"/>
          </span>
        </p>
        <p  style="width:0.64rem;text-align:right;">
          <span><ctx:message code="refe"/> </span>
          <br />
          <span style="color:#4d578f;"> <ctx:message code="P144"/></span>
        </p>
      </div>
      <div id="inviteLink" style="margin-top:0.2rem;height:0.25rem;display:flex;justify-content: space-between;background:#282938;align-items: center;padding:0.1rem;">
        <input id="linkInput" type="text" placeholder="<ctx:message code='P15'/>" style="width:2.76rem;height:0.3rem;border: none;outline: none;background:#484956;padding-left:0.2rem;"/>
        <button id="copyUrl" style="width:0.74rem;height:0.3rem;text-align:center;line-height:0.3rem;font-size:0.16rem;background: linear-gradient(to right, #ff5c4a, #ff8325);border:none;color:white;"><ctx:message code="copy1"/></button>
      </div>
    </div>
  </section>

  <div id="myFriends" style="position:relative;height:auto;margin-bottom:0.2rem;padding:0.2rem 0;font-size:0.12rem;color:#4d4f56;text-align: center;">
    <p style="font-size:0.2rem;color:white;"><ctx:message code="mySubordinate"/></p>
    <div style="display: flex;justify-content: space-between;margin-top: 0.2rem;height:0.42rem;line-height: 0.42rem;">
      <div style="width:0.4rem;"><ctx:message code="NO"/></div>
      <div style="width:0.9rem;"><ctx:message code="userName"/></div>
      <div style="width:0.4rem;"><ctx:message code="child"/></div>
      <div style="width:1.0rem;"><ctx:message code="betAmount"/></div>
      <div style="width:1.0rem;"><ctx:message code="rebate"/></div>
    </div>
    <div id="myFriendsContainer">
      
    </div>
    <div style="margin:auto;margin-top:0.2rem;width:1rem;height:0.3rem;line-height:0.3rem;text-align:center;display: flex;justify-content: space-between;">
      <div style="background-color:#3e425c;width:0.3rem;height:0.3rem;border-radius: 0.04rem;">
        <i style="width:0.16rem;height:0.16rem;vertical-align:middle;background-image:url('../images/arrowL.png');background-size:0.16rem 0.16rem;display:inline-block;"></i>
      </div>
      <span style="color:#4d578f;">1/1</span>
      <div style="background-color:#3e425c;width:0.3rem;height:0.3rem;border-radius: 0.04rem;">
        <i style="width:0.16rem;height:0.16rem;vertical-align:middle;background-image:url('../images/arrowR.png');background-size:0.16rem 0.16rem;display:inline-block;"></i>
      </div>
    </div>
    <img id="noFriends" src="../images/noFriends.svg" alt="" style="width:1rem;height:0.83rem;position:absolute;left:50%;top:50%;margin-left:-0.5rem;margin-top:-0.25rem;display:none;">
  </div>
</div>

<footer style="color:#4d4f56;text-align:center;font-size:0.14rem;padding:0.15rem;">
    <p style="height:0.4rem;line-height:0.4rem;">EOS Casino@2018 All rights Reserved</p>
    <div style="border-bottom: 0.01rem solid #1c2147;display:flex;justify-content:space-between;align-items: center;">
      <img src="../images/lottery_logo.svg" alt="" style="width:0.2rem;height:0.2rem;">
        <div style="display:flex;width:1rem;margin:auto;justify-content:space-between;height:0.4rem;line-height: 0.4rem;align-items: center;">
            <a href="https://t.me/eoscasinosg" target="blank" style="color:#4d4f56"><ctx:message code="PLAYERSTo"/></a>
            <div  style="width:0.02rem;height:0.12rem;background-color:#6a727d;"></div>
            <a href="https://t.me/eoscasinosg" target="blank" style="color:#4d4f56"><ctx:message code="SHAREHOLDERSTo"/></a>
        </div>
        <img src="../images/send.svg" alt="" style="height:0.2rem;width:0.2rem;">
    </div>
    <p style="font-size:0.12rem;text-align:left;margin-top:0.1rem;line-height:0.16rem;">
        Please arrange your time reasonably, and do not over-indulge.<br>
        If you reside in a location where lottery, gambling, sports betting or betting over the internet is illegal, please do not click on anything related to these activities on this site. You must be 21 years of age to click on any betting or gambling related items even if it is legal to do so in your location. Recognising that the laws and regulations involving online gaming are different everywhere, readers are advised to check with the laws that exist within their own jurisdiction to ascertain the legality of the activities which are covered.<br>
        The games provided by EOS Casino are based on blockchain, fair and transparent. When you start playing these games, please note that online gambling and lottery is an entertainment vehicle and that it carries with it a certain degree of financial risk. Players should be aware of this risk, and govern themselves accordingly.
    </p>
</footer>
<div id="awardBack" style="display:none;width:100%;height:11.2rem;background:rgba(0,0,0,0.5);position:absolute;top:0;">
  <div id="awardContainer" style="text-align:center;position:absolute;top:50%;left:50%;background-color:#212540;width:3rem;height:auto;;margin-left: -1.5rem;margin-top:-1.5rem;border-radius:0.1rem">
      <p style="font-size:0.14rem;margin-top:0.2rem;color:#ff5c4a;"><ctx:message code="resultValidation"/></p>
      <div id="resultContain" style="width:2.8rem;margin:auto;height:auto;margin-top:0.2rem;margin-bottom:0.2rem;color:#9fa0a7;">
        
      </div>
      <div style="width:2.2rem;margin:auto;display:flex;justify-content: space-between;margin-bottom:0.2rem;">
      <div style="font-size:0.12rem;color:#9fa0a7;width:1rem;"><span><ctx:message code="nper"/>：</span><span id="resultBlockNum" style="color:#3083de;"></span></div>
      <div style="font-size:0.12rem;color:#9fa0a7;width:1rem;"><span><ctx:message code="Number"/>：</span><span id="resultId" style="color:#ff5c4a;"></span></div>
      </div>
      <img id="resultClose" src="../images/close(2).svg" alt="" style="width:0.2rem;height:0.2rem;position:absolute;top:0.1rem;right:0.1rem;">
  </div>
</div> 
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
    var inputAmount='<ctx:message code="inputAmount"/>';
    let coming='<ctx:message code="coming"/>';
</script>

<script src="../js/changeTime.js"></script>
<script src="../js/login.js"></script>
<script src="../js/bets.js"></script>
<script src="../js/wapindex.js"></script>
<script src="../js/timeDay.js"></script>
<script src="../js/wapView.js"></script>
<script src="../js/Wapball.js"></script>
<script src="../js/zepto.min.js"></script>
<script src="../js/dialog.min.js"></script>
</body>
</html>