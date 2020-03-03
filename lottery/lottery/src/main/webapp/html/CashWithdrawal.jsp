<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ctx" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>提现</title>
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
        <span style="font-size:0.16rem;color:white;">GOS提现</span>
        <span style="font-size:0.14rem;color:white;" id="finishRegister">完成</span>
    </header>
    <div style="width:3.01rem;color:white;padding-left:0.26rem;padding-top:0.32rem;height:3.5rem;margin:auto;border-radius:0.2rem;background:#20243f;margin-top:0.2rem;font-size:0.16rem;">
       <div>
            <span style="font-size:0.16rem;">可用GOS：</span><span id="userGos" style="font-size:0.16rem;color:#00a3ef;"></span>
       </div>
       <div style="margin-top:0.3rem;">
            <span style="font-size:0.16rem;">GOS提现：</span><input id="sumInput" placeholder="请输入提现的积分数值" type=text; style="border:none;outline:none;font-size:0.12rem;color:#00a3ef;width:1.5rem;padding-left:0.1rem;background-color: #313862;height:0.3rem;"/><span style="font-size:0.16rem;"></span>
       </div>
       <p style="font-size:0.12rem;color:#7a82b6;margin-top:0.05rem;text-align:center;margin-top:0.05rem;">提现GOS(1-50000),1GOS=1元</p> 
       <div style="margin-top:0.1rem;">
           <span>提现所得：</span><span id="outMoney" style="font-size:0.16rem;color:#ff5c4a;"></span>
       </div>
       <div style="margin-top:0.3rem;">
            <span>开户银行：</span>
            <select name="" id="bank" style="width:1.6rem;height:0.3rem;background-color: #313862;border:none;outline:none;color:#00a3ef;">
                <option value='BOC,中国银行' style="width:1.6rem;height:0.2rem;border:none;">中国银行</option>
                <option value='ICBC,中国工商银行' style="width:1.6rem;height:0.2rem;border:none;">中国工商银行</option>
                <option value='CCB,中国建设银行' style="width:1.6rem;height:0.2rem;border:none;">中国建设银行</option>
                <option value='ABC,中国农业银行' style="width:1.6rem;height:0.2rem;border:none;">中国农业银行</option>
                <option value='CMB,招商银行' style="width:1.6rem;height:0.2rem;border:none;">招商银行</option>
                <option value='PSBC,中国邮政储蓄银行' style="width:1.6rem;height:0.2rem;border:none;">中国邮政储蓄</option>
                <option value='TICIC,中信银行' style="width:1.6rem;height:0.2rem;border:none;">中信银行</option>
                <option value='CEB,中国光大银行' style="width:1.6rem;height:0.2rem;border:none;">中国光大银行</option>
                <option value='HXB,华夏银行' style="width:1.6rem;height:0.2rem;border:none;">华夏银行</option>
                <option value='CGB,广发银行' style="width:1.6rem;height:0.2rem;border:none;">广发银行</option>
                <option value='PAB,平安银行' style="width:1.6rem;height:0.2rem;border:none;">平安银行</option>
                <option value='CIB,兴业银行' style="width:1.6rem;height:0.2rem;border:none;">兴业银行</option>
                <option value='SPDB,浦发银行' style="width:1.6rem;height:0.2rem;border:none;">浦发银行</option>
                <option value='BOB",北京银行' style="width:1.6rem;height:0.2rem;border:none;">北京银行</option>
                <option value='EGB,恒丰银行' style="width:1.6rem;height:0.2rem;border:none;">恒丰银行</option>
                <option value='CZB,浙商银行' style="width:1.6rem;height:0.2rem;border:none;">浙商银行</option>
                <option value='CBHB,渤海银行' style="width:1.6rem;height:0.2rem;border:none;">渤海银行</option>
                <option value='HSB,徽商银行' style="width:1.6rem;height:0.2rem;border:none;">徽商银行</option>
                <option value='SRCB,上海农村商业银行' style="width:1.6rem;height:0.2rem;border:none;">上海农村商业银行</option>
                <option value='BOS",上海银行' style="width:1.6rem;height:0.2rem;border:none;">上海银行</option>
                <option value='NBCB,宁波银行' style="width:1.6rem;height:0.2rem;border:none;">宁波银行</option>
                <option value='HZCB,杭州商业银行' style="width:1.6rem;height:0.2rem;border:none;">杭州商业银行</option>
                <option value='WFCCB,潍坊银行' style="width:1.6rem;height:0.2rem;border:none;">潍坊银行</option>
                <option value='BEA,东亚银行' style="width:1.6rem;height:0.2rem;border:none;">东亚银行</option>
                <option value='NCB,南洋商业银行' style="width:1.6rem;height:0.2rem;border:none;">南洋商业银行</option>
                <option value='HSBANK,恒生银行' style="width:1.6rem;height:0.2rem;border:none;">恒生银行</option>
                <option value='SHBB,上海商业银行' style="width:1.6rem;height:0.2rem;border:none;">上海商业银行</option>
                <option value='NJCB,南京银行' style="width:1.6rem;height:0.2rem;border:none;">南京银行</option>
                <option value='QDCCB,青岛银行' style="width:1.6rem;height:0.2rem;border:none;">青岛银行</option>
                <option value='TJCB,天津银行' style="width:1.6rem;height:0.2rem;border:none;">天津银行</option>
                <option value='HBB,河北银行' style="width:1.6rem;height:0.2rem;border:none;">河北银行</option>
                <option value='BOCD,成都银行' style="width:1.6rem;height:0.2rem;border:none;">成都银行</option>
            </select>
      </div>
      <div style="margin-top:0.3rem;">
            <span style="font-size:0.16rem;">银行卡号：</span><input id="carInput" placeholder="请输入银行卡号" type=text; style="border:none;outline:none;font-size:0.12rem;color:#00a3ef;width:1.5rem;padding-left:0.1rem;background-color: #313862;height:0.3rem;"/><span style="font-size:0.16rem;"></span>
      </div>
      <div style="margin-top:0.3rem;">
            <span style="font-size:0.16rem;">开卡姓名：</span><input id="nameInput" placeholder="请输入持卡人姓名" type=text; style="border:none;outline:none;font-size:0.12rem;color:#00a3ef;width:1.5rem;padding-left:0.1rem;background-color: #313862;height:0.3rem;"/><span style="font-size:0.16rem;"></span>
      </div>
    </div>
    <div style="width:2.67rem;height:0.36rem;margin: auto;position:relative;margin-top:0.41rem;">
            <button id="formBtn" style="position:absolute;width:2.67rem;height:0.36rem;color:white;background:linear-gradient(to right, #ff5c4a , #ff8325);border: none;border-radius:0.05rem;">提交</button>
    </div>
    <script src="../js/jquery.js"></script>
    <script src="../js/wapAutoSize.js"></script>
    <script src="../js/circleChart.min.js"></script>
    <script src="../js/zepto.min.js"></script>
    <script src="../js/dialog.min.js"></script>
    <script type="text/javascript">
       window.onload=function(){
        // let baseUrl = "https://myeosgame.com";
        let baseUrl="http://172.16.1.47/lottery"
        let url=window.location.search;
        let phoneStr="";
        let userScore=100;//用户积分余额
        //获取用户账户积分余额
        // $.ajax({
        //     type:"get",
        //     url:`${baseUrl}/account/isLogin`,
        //     success:function(msg){
        //     if(msg==true){
        //         $.ajax({
        //             type:"post",
        //             url:`${baseUrl}/account/getScoreUser`,
        //             success:function(msg){
        //                 $("#userGos").text(msg.account.score);
        //             }
        //         })
        //     }
        //     }
        // });
        //获取提现的积分数值
        let sumInput="";
        let regSum=new RegExp("^[0-9]+(.[0-9]{0,2})?$");
        $("#sumInput").on("change",function(){
            sumInput=$(this).val();
            if(regSum.test(sumInput)==true){
                if(sumInput<=userScore&&sumInput>=1&&sumInput<50000){
                    $("#outMoney").text(sumInput+"元");
                }else{
                    popup({type:'tip',msg:"提现金额有误",delay:1000});
                }
            }else{
                popup({type:'tip',msg:"请正确填写金额",delay:1000});
            }
        });
        //获取银行信息
        let bankMsg="";
        let bankCode="";
        let bankName="";
        $("#bank").on("change",function(){
            bankMsg=$("#bank").find("option:selected").val().split(",");
            bankCode=bankMsg[0];
            bankName=bankMsg[1];
        })
        //获取银行卡号
        let carInput="";
        $("#carInput").on("change",function(){
            carInput=$(this).val();
        })
       //获取开卡人姓名
       let NameInput="";
        $("#nameInput").on("change",function(){
            NameInput=$(this).val();
        })
        //获取卡号
        let carNum="";
        $("#carInput").on("change",function(){
            carNum=$(this).val();
        })
        //点击提交进行提现操作
        $("#formBtn").click(function(){
            $.ajax({
                type:"POST",
                url:`${baseUrl}/lottery/pay/tradeWithdrawApply`,
                data:{
                    score:sumInput,
                    name:NameInput,
                    cardNumber:carNum,
                    bankCode:bankCode,
                    bankName:bankName,
                    tel:17381994682
                },
                success:function(msg){
                    console.log(msg)
                }
            })
        })
       }
    </script>
    </body>
</html>