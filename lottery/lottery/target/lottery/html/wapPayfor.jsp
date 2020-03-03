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
    <title>充值</title>
    <style>
    *{
        margin:0;
        padding:0;
    }
    input::-webkit-input-placeholder{
        color:#7a82b6;
    }
    </style>
</head>
<body style="background:#12141e;font-family:Arial, Helvetica, sans-serif;">
    <header style="display:flex;justify-content:space-between;padding:0.1rem;background:#161823;height:0.27rem;line-height:0.47rem;align-items:center;margin-bottom:0.5rem;">
            <img src="../images/leftArrow.svg" alt="backToPrepage" style="width:0.18rem;height:0.18rem;" id="backToPrepage">
            <span style="font-size:0.16rem;color:white;">充值</span>
            <span style="font-size:0.14rem;color:white;" id="finishRegister">完成</span>
    </header>
    <div style="width:3.01rem;color:white;padding-left:0.26rem;padding-top:0.32rem;height:2.6rem;margin:auto;border-radius:0.2rem;background:#20243f;margin-top:0.2rem;font-size:0.16rem;">
        <div>
            <span style="font-size:0.16rem;">充值账号：</span><span id="userPayPhone" style="font-size:0.16rem;color:#00a3ef;"></span>
        </div>
        <div style="margin-top:0.3rem;">
            <span style="font-size:0.16rem;">账户余额：</span><span id="userBalance" style="font-size:0.16rem;color:#00a3ef;"></span>
        </div>
        <div style="margin-top:0.3rem;">
            <span style="font-size:0.16rem;">充值金额：</span><input id="sumInput" placeholder="请输入充值金额" type=text; style="padding-left:0.1rem;font-size:0.16rem;color:#00a3ef;width:1.5rem;background-color: #313862;height:0.3rem;"/><span style="font-size:0.16rem;"> GOS</span>
        </div>
        <p style="font-size:0.14rem;color:#7a82b6;margin-top:0.05rem;text-align:center;">(1GOS=1元)</p> 
        <div style="margin-top:0.3rem;">
            <span>应付金额：</span><span id="payMoney" style="font-size:0.16rem;color:#ff5c4a;"></span>
        </div>
    </div>
    <div style="width:2.67rem;height:0.36rem;margin: auto;position:relative;margin-top:0.41rem;">
        <button id="payFor" style="position:absolute;width:2.67rem;height:0.36rem;color:white;background:linear-gradient(to right, #ff5c4a , #ff8325);border: none;border-radius:0.05rem;">点击充值</button>
    </div>
    <!-- <script src="../js/jquery.js"></script> -->
    <script>window.jQuery || document.write('<script src="../js/jquery-1.12.4.min.js"><\/script>')</script>
    <script src="../js/wapAutoSize.js"></script>
    <script src="../js/circleChart.min.js"></script>
    <script src="../js/zepto.min.js"></script>
    <script src="../js/dialog.min.js"></script>
    <script type="text/javascript">
      window.onload=function(){
        let baseUrl = "https://myeosgame.com";
        // let baseUrl="http://172.16.1.47/lottery";
        let url=window.location.search;
        let phoneStr="";
        if(url){
            let urlStr=url.substr(1);
            console.log(url);
            phoneStr=urlStr.split("&")[0].split("=")[1];
            console.log(phoneStr);
            $("#getUserName").text(phoneStr);
            $.ajax({
                type:"post",
                url: `${baseUrl}/account/getScoreUser`,
                data: {
                    data: phoneStr
                },
                success:function(msg){
                    $("#userPayPhone").text(msg.account.mobilePhone);
                    $("#userBalance").text(msg.account.score+"GOS");
                }
            })
        }else{
            // window.location.href="https://myeosgame.com/wap"
        }
        //获取输入的充值金额
        let sumInput="";
        $("#sumInput").blur(function(){
            sumInput=parseFloat($(this).val()).toFixed(2);
            $("#payMoney").text(sumInput+"元");
        })
        //充值接口
        $("#payFor").click(function(){
            console.log(sumInput);
            if(sumInput>=1&&sumInput<=5000){
                $.ajax({
                type:"post",
                url:`${baseUrl}/pay/getPayLink`,
                data:{
                   "data":phoneStr,
                   "faceMoney":sumInput,
                   "name":"测试",
                   "isPC":"0",
                   "type":"0",
                   "gameType":"wap/lottery"
                },
                success:function(msg){
                    // console.log(msg)
                    window.location.href=msg.url;
                }
            })
            }else{
                popup({type:'tip',msg:"充值金额1-5000",delay:1000,callBack:function(){
                    $("#sumInput").val(0);
                }
                });
            }
        })
      }
    </script>
</body>
</html>