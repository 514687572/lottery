<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ctx" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../css/reset.css" />
    <link rel="stylesheet" href="../css/dialog.css">
    <title>注册第一步</title>
    <style>
    *{
        margin:0;
        padding:0;
    }
    input::-webkit-input-placeholder{
        color:#485186;
    }
    </style>
</head>
<body style="background:#12141e;font-family:Arial, Helvetica, sans-serif;">
    <header style="display:flex;justify-content:space-between;padding:0.1rem;background:#161823;height:0.27rem;line-height:0.47rem;align-items:center;margin-bottom:0.5rem;">
       <img src="../images/leftArrow.svg" alt="backToPrepage" style="width:0.18rem;height:0.18rem;" id="backToPrepage">
       <span style="font-size:0.16rem;color:white;">EOS账号注册</span>
       <span style="font-size:0.14rem;color:white;" id="finishRegister">完成</span>
    </header>
    <div style="width:3.27rem;height:2.7rem;margin:auto;border-radius:0.2rem;background:#20243f;margin-top:0.2rem;">
        <div style="width:2.68rem;height:0.51rem;line-height:0.51rem;text-align:center;font-size:0.14rem;color:white;display: flex;margin:auto;">
            <div id="phoneBtn" style="width:50%;height:0.5rem;border-bottom:0.01rem solid #889aff;">手机注册</div>
            <div id="mailBtn" style="width:50%;height:0.5rem;border-bottom:0.01rem solid #373a53;">邮箱注册</div>
        </div>
        <div style="width:2.27rem;height:2rem;margin:auto;margin-top:0.24rem;display:block;" id="phoneRegister">
            <div style="width:2.27rem;height:0.39rem;border-bottom:0.01rem solid #373a53;display:flex;align-items: center;">
                <img src="../images/phone.svg" alt="" style="width:0.2rem;height:0.24rem;">
                <input id="phoneInput" type="text" placeholder="phoneNumber" style="background:#20243f;border:none;outline:none;height:0.38rem;width:1.8rem;padding-left:0.2rem;color:#485186;font-size:0.14rem;">
            </div>
            <div style="width:2.27rem;height:0.39rem;border-bottom:0.01rem solid #373a53;display:flex;align-items: center;">
                    <img src="../images/password.svg" alt="" style="width:0.2rem;height:0.23rem;">
                    <input class="passwordInput" type="password" placeholder="password" style="background:#20243f;border:none;outline:none;height:0.38rem;width:1.6rem;padding-left:0.2rem;color:#485186;font-size:0.14rem;">
                    <img class="eyespasswordInput" src="../images/eyesHideden.svg" alt="" style="width:0.2rem;height:0.24rem;">
            </div>
            <div style="width:2.27rem;height:0.39rem;border-bottom:0.01rem solid #373a53;display:flex;align-items: center;">
                    <img src="../images/password.svg" alt="" style="width:0.2rem;height:0.23rem;">
                    <input class="passwordComfire" type="password" placeholder="Comfirepassword" style="background:#20243f;border:none;outline:none;height:0.38rem;width:1.6rem;padding-left:0.2rem;color:#485186;font-size:0.14rem;">
                    <img class="eyespasswordComfire" src="../images/eyesHideden.svg" alt="" style="width:0.2rem;height:0.24rem;">
            </div>
            <div style="width:2.27rem;height:0.39rem;border-bottom:0.01rem solid #373a53;display:flex;align-items: center;">
                    <img src="../images/code.svg" alt="" style="width:0.2rem;height:0.23rem;">
                    <input id="phoneCordeInput" type="text" placeholder="verification code" style="background:#20243f;border:none;outline:none;height:0.38rem;width:1.1rem;padding-left:0.2rem;color:#3083de;font-size:0.14rem;">
                    <button id="sendPhoneCode" style="width:0.72rem;height:0.26rem;background:#3097ff;color:white;font-size:0.12rem;text-align:center;border:none;outline:none;border-radius:0.05rem;">获取验证码</button>
            </div>
        </div>
        <div style="width:2.27rem;height:2rem;margin:auto;margin-top:0.24rem;display:none;" id="mailRegister">
            <div style="width:2.27rem;height:0.39rem;border-bottom:0.01rem solid #373a53;display:flex;align-items: center;">
                <img src="../images/mail.svg" alt="" style="width:0.2rem;height:0.24rem;">
                <input id="emailInput" type="text" placeholder="email" style="background:#20243f;border:none;outline:none;height:0.38rem;width:1.8rem;padding-left:0.2rem;color:#485186;font-size:0.14rem;">
            </div>
            <div style="width:2.27rem;height:0.39rem;border-bottom:0.01rem solid #373a53;display:flex;align-items: center;">
                    <img src="../images/password.svg" alt="" style="width:0.2rem;height:0.24rem;">
                    <input class="passwordInput" type="password" placeholder="password" style="background:#20243f;border:none;outline:none;height:0.38rem;width:1.6rem;padding-left:0.2rem;color:#485186;font-size:0.14rem;">
                    <img class="eyespasswordInput" src="../images/eyesHideden.svg" alt="" style="width:0.2rem;height:0.24rem;">
            </div>
            <div style="width:2.27rem;height:0.39rem;border-bottom:0.01rem solid #373a53;display:flex;align-items: center;">
                    <img src="../images/password.svg" alt="" style="width:0.2rem;height:0.24rem;">
                    <input class="passwordComfire" type="password" placeholder="Comfirepassword" style="background:#20243f;border:none;outline:none;height:0.38rem;width:1.6rem;padding-left:0.2rem;color:#485186;font-size:0.14rem;">
                    <img class="eyespasswordComfire" src="../images/eyesHideden.svg" alt="" style="width:0.2rem;height:0.24rem;">
            </div>
            <div style="width:2.27rem;height:0.39rem;border-bottom:0.01rem solid #373a53;display:flex;align-items: center;">
                    <img src="../images/dunpai.svg" alt="" style="width:0.2rem;height:0.24rem;">
                    <input id="mailCordeInput" type="text" placeholder="verification code" style="background:#20243f;border:none;outline:none;height:0.38rem;width:1.1rem;padding-left:0.2rem;color:#485186;font-size:0.14rem;">
                    <button id="sendMailCode" style="width:0.72rem;height:0.26rem;background:#3097ff;color:white;font-size:0.14rem;text-align:center;border:none;outline:none;border-radius:0.05rem;">获取验证码</button>
            </div>
        </div>
    </div>
    <div style="width:2.67rem;height:0.36rem;margin: auto;position:relative;margin-top:0.41rem;">
        <button id="register" style="position:absolute;width:2.67rem;height:0.36rem;color:white;background:linear-gradient(to right, #ff5c4a , #ff8325);border: none;border-radius:0.05rem;">注册</button>
    </div>
    
    <script src="../js/jquery.js"></script>
    <script src="../js/wapAutoSize.js"></script>
    <script src="../js/zepto.min.js"></script>
    <script src="../js/dialog.min.js"></script>

    <script stype="text/javascript">
        window.onload=function(){
            let baseUrl = "https://myeosgame.com";
            // let baseUrl="http://172.16.1.47/lottery";
            $("#backToPrepage").click(function(){
                window.location.href=`${baseUrl}/wap/lottery`
            });
            $("#phoneBtn").click(function(){
                $("#phoneRegister").css("display","block");
                $("#mailRegister").css("display","none");
                $(this).css("borderBottom","0.01rem solid #889aff");
                $("#mailBtn").css("borderBottom","0.01rem solid #373a53");
            });
            $("#mailBtn").click(function(){       
                popup({type:'tip',msg:"即将开放",delay:1000});
                // $("#mailRegister").css("display","block");
                // $("#phoneRegister").css("display","none");
                // $(this).css("borderBottom","0.01rem solid #889aff");
                // $("#phoneBtn").css("borderBottom","0.01rem solid #373a53");
            });
            //用户名验证
            let regUserName=/^[a-z1-5]{12}$/g;
            let userNameBoolean=false;
            let userNameInput="";
            $(".userNameInput").bind("input propertychange",function(event){
                userNameInput=$(this).val();
                if(regUserName.test(userNameInput)==true){
                    userNameBoolean=true;
                    $(this).css("color","#3083de");
                }else{
                    $(this).css("color","red");
                    userNameBoolean=true;
                }
            })
            //密码明文隐藏
            let isHiddenInput=true;
            let isHiddenComfire=true;
            $(".eyespasswordInput").click(function(){
                isHiddenInput=!isHiddenInput;
                if(isHiddenInput==true){
                    $(this).attr("src","../images/eyesHideden.svg");
                    $(".passwordInput").attr("type","password");
                }else{
                    $(this).attr("src","../images/eyesOpen.svg");
                    $(".passwordInput").attr("type","text");
                }
            })
            $(".eyespasswordComfire").click(function(){
                isHiddenComfire=!isHiddenComfire;
                if(isHiddenComfire==true){
                    $(this).attr("src","../images/eyesHideden.svg");
                    $(".passwordComfire").attr("type","password");
                }else{
                    $(this).attr("src","../images/eyesOpen.svg");
                    $(".passwordComfire").attr("type","text");
                }
            })
            //密码验证
            let regPassword=/^(?=.*[a-z])(?=.*\d)[^]{8,12}$/
            let passWordBoolean=false;
            let passwordInput="";
            $(".passwordInput").bind("input propertychange",function(event){
                passwordInput=$(this).val();
                if(regPassword.test(passwordInput)==true){
                    passWordBoolean=true;
                    $(this).css("color","#3083de");
                }else{
                    $(this).css("color","red");
                    passWordBoolean=false;
                }
            })
            //密码确认
            $(".passwordComfire").bind("input propertychange",function(event){
                passwordComfire=$(this).val();
                if(regPassword.test(passwordComfire)==true&&passwordComfire===passwordInput){
                    passWordBoolean=true;
                    $(this).css("color","#3083de");
                }else{
                    $(this).css("color","red");
                    passWordBoolean=false;
                }
            })
            //手机号码验证
            let phoneReg=/^[1]{1}[0-9]{10}/g;
            let phoneBoolean=false;
            let phoneNum="";
            $("#phoneInput").bind("input propertychange",function(event){
                phoneNum=$(this).val();
                if(phoneReg.test(phoneNum)==true){
                    phoneBoolean=true;
                    $(this).css("color","#3083de");
                }else{
                    $(this).css("color","red");
                    phoneBoolean=false;
                }
            })
            //获取手机号验证码
            let phoneCode="";
            $("#sendPhoneCode").click(function(){
                if(phoneBoolean==true){
                    $.ajax({
                        type:"get",
                        url:`${baseUrl}/lottery/account/sendCode.do`,
                        data:{
                            "tel":phoneNum
                        },
                        success:function(msg){
                            popup({type:'success',msg:"验证码发送成功",delay:1000});
                            phoneCode=msg.yzm
                        }
                    })
                }else{
                    popup({type:'tip',msg:"请输入正确的手机号码",delay:1000});
                }
            })
            //短信验证码验证
            // let phoneCodeBoolean=false;
            let phoneCodeInput="";
            $("#phoneCordeInput").bind("input propertychange",function(event){
                phoneCodeInput=$(this).val();
                // if(phoneCodeInput==phoneCode){
                //     phoneCodeBoolean=true;
                //     $(this).css("color","#3083de");
                // }else{
                //     $(this).css("color","red");
                //     phoneCodeBoolean=false;
                // }
            })
            //点击注册完成注册并跳转页面
            $("#register").click(function(){
                if(passWordBoolean==true&&phoneBoolean==true){
                    $.ajax({
                        type:"post",
                        url:`${baseUrl}/lottery/account/regist`,
                        data:{
                            mobilePhone:phoneNum,
                            password:passwordInput,
                            idencode:phoneCodeInput,
                            email:null,
                            referrer:null
                        },
                        success:function(msg){
                            if(msg.success==true){
                                popup({type:'success',msg:"注册成功",delay:1000,callBack:function(){
                                    window.location.href=`${baseUrl}/lottery/wap/lottery`;
                                }});
                            }else{
                                popup({type:'error',msg:`${msg.msg}`,delay:1000});
                            }
                        }
                    })
                    
                }else{
                    popup({type:'tip',msg:"请正确填写注册信息",delay:1000});
                }
            })
            // 点击完成跳转页面
            $("#finishRegister").click(function(){
                if(passWordBoolean==true&&phoneBoolean==true){
                    $.ajax({
                        type:"post",
                        url:`${baseUrl}/lottery/regist`,
                        data:{
                            mobilePhone:`${phoneNum}`,
                            password:`${passwordInput}`,
                            idencode:`${phoneCodeInput}`,
                            email:null,
                            referrer:null
                        },
                        success:function(msg){
                            if(msg.success==true){
                                popup({type:'success',msg:"注册成功",delay:1000,callBack:function(){
                                    window.location.href=`${baseUrl}/lottery/wap/lottery`;
                                }});
                            }else{
                                popup({type:'error',msg:`${msg.msg}`,delay:1000});
                            }
                        }
                    })
                    
                }else{
                    popup({type:'tip',msg:"请正确填写注册信息",delay:1000});
                }
            })
        }
    </script>
</body>
</html>