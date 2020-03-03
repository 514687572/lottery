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
    <link rel="stylesheet" href="../css/register.css">
    <link rel="stylesheet" href="../css/dialog.css">
    <title>Register</title>
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
          快捷账号注册
          <span id="back">返回</span>
        </div>
        <div id="registerContain">
            <div id="titleContain">
                <div id="phoneBtn">手机注册</div>
                <div id="mailBtn">邮箱注册</div>
            </div>
            <div id="phoneRegister" style="display:block;">
                <div class="inputContain">
                  <div>
                      <img src="../images/phone.svg" alt="">
                  </div>
                  <input id="phoneInput" type="text" placeholder="请输入11位手机号码">
                </div>
                <div class="inputContain">
                  <div>
                      <img src="../images/password.svg" alt="">
                  </div>
                  <input class="passwordInput" type="password" placeholder="请输入8-12位密码，包含字母数字">
                  <img class="eyespasswordInput" src="../images/eyesHideden.svg" alt="eyes">
                </div>
                <div class="inputContain">
                  <div>
                      <img src="../images/password.svg" alt="">
                  </div>
                  <input class="passwordComfire" type="password" placeholder="请确认密码" >
                  <img class="eyespasswordComfire" src="../images/eyesHideden.svg" alt="eyes" >
                </div>
                <div style="display:flex;align-items:center;margin-top:17px;">
                    <div class="inputContain">
                        <div>
                            <img src="../images/code.svg" alt="">
                        </div>
                        <input id="phoneCordeInput" type="text" placeholder="请输入短信验证码" >
                      </div>
                      <button id="sendPhoneCode">获取验证码</button>
                </div>
            </div>
            <div id="mailRegister" style="display:none;">
                <div class="inputContain">
                  <div>
                      <img src="../images/mail.svg" alt="">
                  </div>
                  <input id="emailInput" type="text" placeholder="请输入邮箱号码">
                </div>
                <div  class="inputContain">
                  <div>
                      <img src="../images/password.svg" alt="">
                  </div>
                  <input class="passwordInput" type="password" placeholder="请输入8-12位密码，包含数字、字母">
                  <img class="eyespasswordInput" src="../images/eyesHideden.svg" alt="">
                </div>
                <div  class="inputContain">
                  <div>
                      <img src="../images/password.svg" alt="">
                  </div>
                  <input class="passwordComfire" type="password" placeholder="请确认密码">
                  <img class="eyespasswordComfire" src="../images/eyesHideden.svg" alt="">
                </div>
                <div style="display:flex;align-items:center;margin-top:17px;">
                <div  class="inputContain">
                  <div>
                      <img src="../images/dunpai.svg" alt="">
                  </div>
                  <input id="mailCordeInput" type="text" placeholder="请输入手机验证码">
                </div>
                <button id="sendMailCode">获取验证码</button>
              </div>
            </div>
            <div id="btnContain">
                <button id="register">注册</button>
            </div>
        </div>
       
    </section>

    <script src="../js/jquery.js"></script>
    <script src="../js/zepto.min.js"></script>
    <script src="../js/dialog.min.js"></script>
    <script src="../js/register.js"></script>
</body>
</html>