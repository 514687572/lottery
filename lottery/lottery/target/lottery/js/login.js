// let baseUrl = "https://myeosgame.com";
// let websocketUrl = "myeosgame.com";
let baseUrl="http://172.16.1.47/lottery";
let websocketUrl = "//172.16.1.47/lottery";

//全局变量
let userName="";//用户名，存session
let publicKey="";//用户公钥，存session
let phone="";//用户手机号，存session
// let loginBtn=1;
let totalCpuWidth=$("#totalCpu").width();//cpu总量DIV的宽度
let betsTime=0;
let loginType = "1"; //登录的方式
let betsAllWidth=$("#betsAll").width();//投注限额的DIV宽度
let userEos=0;//用户EOS余额
let getHisAwardPage=1;//请求历史开奖的页码
let getHisBetsPage=1;//请求投注记录的页码
let pageNumGos=1;//请求GOS投注记录的页码
let getHisAwardRow=10;//请求开奖记录的每页数据条数
let modelBollen = "0";  //玩家选择的游戏模式，默认基础模式
let userPriKey=""; //用户的私钥
let enterLotteryNum=0;//当前的期号
let perPageFriends=10;//我的下级请求每页数据条数
let pageNumFriends=1;//我的下级请求的页数
let isStart=false;//判断是否已经开始开奖
let awrding=false;//判断开奖是否已经结束
let firstBlock_id=0;//结果查询的第一个区块ID
let lastBlock_id=0;//结果查询的最后一个区块ID
let isPrivate = "1";//是否是私钥登录(投注要用)
let totalLimitBets = 0; //投注限额总数
let userLimitBets = 0; //个人投注限额
let balance = 0; //奖金池金额
let userBlance = 0; //用户投注金额
let baseBetsNum=0;//基础模式的投注总金额
let higherBetsNum=0;//高阶模式的投注总金额
let isEos=true;  //是否选择的EOS作为单位下注
let baselssdStr="";//基础模式大小单双选择的结果
let baseNumStr="";//基础模式数字选择的结果
let higherBetsStrOne="";//高阶模式个位选择结果
let higherBetsStrTwo="";//高阶模式十位选择结果
let higherBetsStrThree="";//高阶模百各位选择结果
let higherBetsStrFour="";//高阶模式千位选择结果
let higherBetsStrFive="";//高阶模式万位选择结果
let Numstr = ""; //实时开奖的号码
let pageNum = 1; //历史开奖请求页数
let pageNumMybets = 1; //我的投注历史页数
let uid; //websocket用户名
let hashArr = []; //hash值信息数组
let tempul = $("#Marquee"); //整个hash值推送区域li的容器
let changedTime; //最新数据的时间
let strHashNum1 = ""; //不包含开奖号码的内容
let strHashNum2 = ""; //包含开奖号码的内容
let hashNumbers; //hash值号码
let lastNUmber; //hash值最后一位
let historyRowNum = 10; //历史记录请求条数
let nextPeriods=0;
let yBarTop=0;
let friendsPage=0;
let rulerBoolean=false;
let inviteBoolean=false;
let hisBoolean=false;
const network = {
  blockchain: "eos",
  protocol: "https",
  host: "api.eosbeijing.one",
  port: 443,
  chainId: "aca376f206b8fc25a6ed44dbdc66547c36c6c33e3a119ffbeaef943642f0e906"
};//scatter需要的网络配置

// let logOut = "退出登录";
// let userCode = "111";
// let people="人";
// let englistBig = '大';
// let englistSmall = '小';
// let englistOdd = '单';
// let englistEven = '双';
// let englistBet = '投注';
// let period1 = '第';
// let period2 = '期';
// let loginSuccess="登录成功";
// let success="投注成功";
// let cpuerorr="CPU不足";
// let coming="即将开放"
// let win = "中奖";
// let lang = "zh";
// let ucerror="金额错误或未选组合";
// let common="共";
// let dice126="可点击查看";

userName=window.sessionStorage.getItem("userName");
publicKey=window.sessionStorage.getItem("publicKey");
phone=window.sessionStorage.getItem("phone");
loginType=window.sessionStorage.getItem("loginType");

//建立websocket链接
let chars = ["0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];
let path = baseUrl;
uid = generateMixed(6);
let from = uid;
let fromName = generateMixed(6);
var lockReconnect = false;  //避免ws重复连接
var ws = null;          // 判断当前浏览器是否支持WebSocket
// var wsUrl = `wss://${websocketUrl}/ws?userId=${uid}&gameType=lottery`;
var wsUrl = `ws:${websocketUrl}/ws?userId=${uid}&gameType=lottery`;
function createWebSocket(url) {
  try{
      if('WebSocket' in window){
          ws = new WebSocket(url);
      }else if('MozWebSocket' in window){  
          ws = new MozWebSocket(url);
      }
      initEventHandle();
  }catch(e){
      reconnect(url);
  }     
}
function generateMixed(n) {
  var res = "";
  for (var i = 0; i < n; i++) {
    var id = Math.ceil(Math.random() * 35);
    res += chars[id];
  }
  return res;
}
function initEventHandle(){
  ws.onopen = function (event) {
  };
  ws.onerror = function (event) {
    reconnect(wsUrl);
  };
  ws.onclose = function (event) {
    reconnect(wsUrl);
  };
  window.onbeforeunload = function() {
    ws.close();
  }
}

function reconnect(url) {
  if(lockReconnect) return;
  lockReconnect = true;
  setTimeout(function () {     //没连接上会一直重连，设置延迟避免请求过多
    createWebSocket(url);
    lockReconnect = false;
  }, 2000);
}
function createWebSocket(url) {
  try{
      if('WebSocket' in window){
          ws = new WebSocket(url);
      }else if('MozWebSocket' in window){  
          ws = new MozWebSocket(url);
      }
      initEventHandle();
  }catch(e){
      reconnect(url);
  }     
}
createWebSocket(wsUrl);   //连接ws


$(".loginBtnBets").click(function(){
    $("#loginContainer").css("display","block");
})

$("#privateTitle").click(function(){
  $(this).css({
    "color":"#3083de",
    "borderBottom":"0.01rem solid #3083de"
  });
  $("#privateContain").css("display","block");
  $("#phoneContain").css("display","none");
  $("#phoneTitle").css({
    "color":"white",
    "borderBottom":"0.01rem solid #484956"
  })
})

$("#phoneTitle").click(function(){
  $(this).css({
    "color":"#3083de",
    "borderBottom":"0.01rem solid #3083de"
  });
  $("#privateContain").css("display","none");
  $("#phoneContain").css("display","block");
  $("#privateTitle").css({
    "color":"white",
    "borderBottom":"0.01rem solid #484956"
  })
})



// scatter登录
$(".scatter_login").click(function () {
  if (!window["scatter"]) {
    popup({type:'tip',msg:notInstalled,delay:1000,clickDomCancel:true});
    return;
  }
  window.ScatterJS.scatter.connect("My-App").then(function (connected) {
    if (!connected) return false;
    const scatter = window.ScatterJS.scatter;
    const requiredFields = {
      accounts: [network]
    };
    scatter.getIdentity(requiredFields).then(() => {
      const account = scatter.identity.accounts.find(x => x.blockchain === "eos");
      userName=scatter.identity.accounts[0].name;
      window.sessionStorage.setItem("userName",userName);
      loginType = "1";
      window.sessionStorage.setItem("loginType",loginType);
      popup({type:'success',msg:loginSuccess,delay:1000,callBack:function(){
        checkIdentity();
        getHisBets();
      }});
      $.ajax({
        type: "post",
        url: `${baseUrl}/account/addUserInfo.do`,
        data: {
          account: scatter.identity?scatter.identity:userName,
          userCode: userCode ? userCode : ""
        },
        success: function (msg) {
        }
      });
    });
  });
});

//私钥登录
$("#loginComfire").click(function () {
    let reg = new RegExp("^[A-Za-z0-9]{51}$");
    if (reg.test($("#userChainId").val()) == true) {
      $.ajax({
        type: "post",
        url: `${baseUrl}/account/loginWidthPriKey.do`,
        data: {
          private_key: $("#userChainId").val(),
          userCode: userCode ? userCode : ""
        },
        success: function (date) {
            popup({type:'success',msg:loginSuccess,delay:1000,callBack:function(){
                $("#userChainId").val("");
                userName = date.userName;
                window.sessionStorage.setItem("userName",`${userName}`);
                publicKey = date.pubKey;
                window.sessionStorage.setItem("publicKey",`${publicKey}`);
                loginType = "2";
                window.sessionStorage.setItem("loginType",loginType);
             }});
          
          $.ajax({
            type: "get",
            url: `${baseUrl}/account/getAccount.do`,
            data: {
              account: date.userName
            },
            success: function (msg) {
              uid = date.userName;
              $("#userInfo").css("display", "block");
              $("#userInfoContainer").css("display", "block");
              $("#userInfomation").css("display", "block");
              $("#loginBtn").css("display", "none");
              $("#loginContainer").css("display", "none");
              $(".loginBtnBets").css("display", "none");
              $(".betsBtn").css("display", "block");
              $("#cpu").text(`${parseFloat((msg.cpu.used / msg.cpu.max) * 100).toFixed(2)}%`);
              $("#userCpu").css("width",`${parseInt((msg.cpu.used / msg.cpu.max))*totalCpuWidth/100}rem`);
              $("#userName").text(`${date.userName}`);
              userEos=parseFloat(msg.balance.replace("EOS",""));
              $("#EosMoney").text(`${msg.balance.replace("EOS","")}`);
              $("#GOSMoney").text(`${msg.top.replace("GOS","")}`);
              // checkIdentity();
              getHisBets();
            }
          });
        }
      });
    } else {
        popup({type:'error',msg:placeholderWap,delay:1000,bg:true,clickDomCancel:true});
        $("#userChainId").val("");
    }
});


//初始进入页面获取账户信息
function checkIdentity() {
  switch (loginType){
    case "1":
    $.ajax({
      type: "get",
      url: `${baseUrl}/account/getAccount.do`,
      data: {
        account: userName
      },
      success: function (msg) {
        if(msg.balance){
            userEos=parseFloat(msg.balance.replace("EOS",""));
            userGos=msg.top.replace("GOS","");
        }
        if (userName!=""&&userName!=null) {
          uid =userName
          $("#userInfo").css("display", "block");
          $("#userInfoContainer").css("display", "block");
          $("#userInfomation").css("display", "block");
          $("#loginBtn").css("display", "none");
          $("#loginContainer").css("display", "none");
          $(".loginBtnBets").css("display", "none");
          $(".betsBtn").css("display", "block");
          $("#cpu").text(`${parseFloat((msg.cpu.used / msg.cpu.max) * 100).toFixed(2)}%`);
          if(msg.cpu.used / msg.cpu.max<0.5&&msg.cpu.used / msg.cpu.max>=0){
            $("#cpu").css("color","#3083de");
          }
          else if(msg.cpu.used / msg.cpu.max>=0.5&&msg.cpu.used / msg.cpu.max<0.9){
            $("#cpu").css("color","#ff8325");
          }
          else if(msg.cpu.used / msg.cpu.max>=0.9){
            $("#cpu").css("color","red");
          }
          if((msg.cpu.used / msg.cpu.max)*totalCpuWidth>totalCpuWidth&&(msg.cpu.used / msg.cpu.max)*totalCpuWidth>0){
            $("#userCpu").css("width",`${totalCpuWidth/100}rem`);
          }
          $("#userCpu").css("width",`${(msg.cpu.used / msg.cpu.max)*totalCpuWidth/100}rem`);
          if((msg.cpu.used / msg.cpu.max)>0.5&&(msg.cpu.used / msg.cpu.max)<0.9){
            $("#userCpu").css("color","#ff564f");
          }
          else if((msg.cpu.used / msg.cpu.max)>=0.9){
            $("#userCpu").css("color","red");
          }
          $("#userName").text(userName);
          $("#EosMoney").text(`${userEos}`);
          $("#GOSMoney").text(userGos);
        } else {
          $("#userInfo").css("display", "none");
          $(".loginBtn").css("display", "block");
          $(".loginBtnBets").css("display", "block");
          $(".betsBtn").css("display", "none");
        }
        getHisBets();
      }
    });
    break;
    case "2":
    $.ajax({
      type: "get",
      url: `${baseUrl}/account/getAccount.do`,
      data: {
        account: userName
      },
      success: function (msg) {
        if(msg.balance){
            userEos=parseFloat(msg.balance.replace("EOS",""));
            userGos=msg.top.replace("GOS","");
        }
        if (userName!=""&&userName!=null) {
          uid =userName
          $("#userInfo").css("display", "block");
          $("#userInfoContainer").css("display", "block");
          $("#userInfomation").css("display", "block");
          $("#loginBtn").css("display", "none");
          $("#loginContainer").css("display", "none");
          $(".loginBtnBets").css("display", "none");
          $(".betsBtn").css("display", "block");
          $("#cpu").text(`${parseFloat((msg.cpu.used / msg.cpu.max) * 100).toFixed(2)}%`);
          if(msg.cpu.used / msg.cpu.max<0.5&&msg.cpu.used / msg.cpu.max>=0){
            $("#cpu").css("color","#3083de");
            $("#userCpu").css("width",`${(msg.cpu.used / msg.cpu.max)*totalCpuWidth/100}rem`);
          }
          else if(msg.cpu.used / msg.cpu.max>=0.5&&msg.cpu.used / msg.cpu.max<0.9){
            $("#cpu").css("color","#ff8325");
            $("#userCpu").css("color","#ff564f");
            $("#userCpu").css("width",`${(msg.cpu.used / msg.cpu.max)*totalCpuWidth/100}rem`);
          }
          else if(msg.cpu.used / msg.cpu.max>=0.9&&msg.cpu.used / msg.cpu.max<=1){
            $("#cpu").css("color","red");
            $("#userCpu").css("color","red");
            $("#userCpu").css("width",`${(msg.cpu.used / msg.cpu.max)*totalCpuWidth/100}rem`);
          }
          else if(msg.cpu.used / msg.cpu.max>1){
            $("#userCpu").css("width",`${totalCpuWidth/100}rem`);
          }
          $("#userName").text(userName);
          $("#EosMoney").text(`${userEos}`);
          $("#GOSMoney").text(userGos);
        } else {
          $("#userInfo").css("display", "none");
          $(".loginBtn").css("display", "block");
          $(".loginBtnBets").css("display", "block");
          $(".betsBtn").css("display", "none");
        }
        getHisBets();
      }
    });
    break;
    case "3":
    $.ajax({
      type:"get",
        url:`${baseUrl}/account/isLogin`,
        success:function(msg){
          if(msg==true){
            $.ajax({
              type:"post",
              url:`${baseUrl}/account/getScoreUser`,
              success:function(msg){
                console.log(msg);
                let userPhone=msg.account.mobilePhone;
                userPhone=userPhone.substr(0,3)+"****"+userPhone.substr(7);  
                $("#loginContainer").css("display","none");
                $("#userInfo").css("display", "block");
                $("#phoneContainer").css("display", "block");
                $("#telephone").text(userPhone);
                $("#integral").text(msg.account.score+"GOS");
                $("#loginBtn").css("display", "none");
                $("#loginContainer").css("display", "none");
                $(".loginBtnBets").css("display", "none");
                $(".betsBtn").css("display", "block");
                $(".moneyUnit").text("GOS");
                $(".chooseUnit").css("display","none");
                $(".unitList").css("display","none");
                getGosBalance();
                getGosHis();
              }
            })
          }
        }
    });
    break;
    default:
    $("#userInfo").css("display", "none");
    $(".loginBtn").css("display", "block");
    $(".loginBtnBets").css("display", "block");
    $(".betsBtn").css("display", "none");
    break;
  }
};
checkIdentity();
//手机端退出登录
$("#QuickOut").click(function(){
  $.ajax({
    type:"get",
    url:`${baseUrl}/account/scoreUserLoginOut.do`,
    success:function(msg){
      console.log(msg)
    }
  })
  $("#loginComfire").attr("disabled",false);
  popup({type:'error',msg:logOut,delay:1000,bg:true,clickDomCancel:true});
  $("#userInfo").css("display","none");
  $(".loginBtnBets").css("display", "block");
  $(".betsBtn").css("display", "none");
  $("#myHisBetTab").html("");
  userName="";
  userPriKey="";
  window.sessionStorage.removeItem("userName");
  window.sessionStorage.removeItem("publicKey");
  window.sessionStorage.removeItem("loginType");
  checkIdentity();
});

//PC端退出登录
$("#loginOut").click(function () {
  popup({type:'error',msg:logOut,delay:2000,bg:true,clickDomCancel:true});
  if (loginType=="1") {
    scatter.forgetIdentity();
    $("#orderContainer").empty();
    $("#userInfomation").css("display", "none");
    $(".loginBtn").css("display", "block");
    $(".loginBtnBets").css("display", "block");
    $(".betsBtn").css("display", "none");
    $("#loginBtn").css("display", "block");
    $("#cpuContainer").css("display", "none");
    userName="";
    userPriKey="";
    window.sessionStorage.removeItem("userName");
    window.sessionStorage.removeItem("publicKey");
    window.sessionStorage.removeItem("loginType");
  }
  else if (loginType=="2") {
    $("#orderContainer").empty();
    $("#userInfomation").css("display", "none");
    $(".loginBtn").css("display", "block");
    $(".loginBtnBets").css("display", "block");
    $(".betsBtn").css("display", "none");
    $("#loginBtn").css("display", "block");
    $("#cpuContainer").css("display", "none");
    userName="";
    userPriKey="";
    window.sessionStorage.removeItem("userName");
    window.sessionStorage.removeItem("publicKey");
    window.sessionStorage.removeItem("loginType");
  }
});

//获取奖金池的数量
function getBlance() {
    $.ajax({
      type: "get",
      url: `${baseUrl}/account/getSysInfo.do`,
      success: function (msg) {
        if(msg.size!=0){
          balance = parseInt(msg.balance.replace("EOS", "")*10000)/10000;
          totalLimitBets =parseFloat(balance * 0.05).toFixed(1);
          if(totalLimitBets>=1000){
              $("#percent").text(`/${+(parseInt(totalLimitBets / 1000*10000)/10000)}K`)
            }else{
              $("#percent").text(`/${totalLimitBets}`)
            }
          if(balance>=10000){
            $("#bonus").text(`${parseInt((balance/1000)*10000)/10000}K`);
          }else{
            $("#bonus").text(`${parseInt(balance*10000)/10000}`);
          }
        }
        if(window.sessionStorage.getItem("userBlance")){
          let enterUserBlance=parseInt(window.sessionStorage.getItem("userBlance")*10)/10;
          if(enterUserBlance>=100000){
            $("#betsUsers").text(`${parseFloat(enterUserBlance/1000).toFixed(1)}K`);
          }else{
            $("#betsUsers").text(`${parseInt(enterUserBlance*10)/10}`);
          }
          $("#betsUsers").css({
            "width": `${(userBlance / totalLimitBets)*betsAllWidth}px`
          });
        }else{
          $("#betsUsers").text(0);
          $("#betsUsers").css({
            "width": `0`
          });
        }
      }
    });
};

//请求GOS的奖池
function getGosBalance(){
  $.ajax({
    type:"get",
    url:`${baseUrl}/account/getscorePoolBalance`,
    data:{
      gameType:"lottery"
    },
    success:function(msg){
      balance=msg;
      totalLimitBets =parseFloat(balance * 0.05).toFixed(1);
      if(totalLimitBets>=1000){
        $("#percent").text(`/${+(parseInt(totalLimitBets / 1000*10000)/10000)}K`)
      }else{
        $("#percent").text(`/${totalLimitBets}`)
      }
      if(balance>=10000){
        $("#bonus").text(`${parseInt((balance/1000)*10000)/10000}K`);
      }else{
        $("#bonus").text(`${parseInt(balance*10000)/10000}`);
      }
    }
  })
};

// 抵押cpu
let regOne = new RegExp("^[0-9]+(.[0-9]{0,1})?$");
$("#cpuImg").click(function (event) {
  event.stopPropagation();
  $("#cpuContainer").toggle()
  // $("#cpuContainer").css("display","block");
  let cpuInput = 0;
    $("#cpuInput").blur(function () {
      cpuInput = parseInt($("#cpuInput").val()*10)/10;
    });
        $("#cpuComfire").click(function () {
          if(regOne.test(cpuInput)==true&&cpuInput>=0.5){
          if(loginType=="1"){
          const eosOptions = {
            expireInSeconds: 60
          };
          const eos = scatter.eos(network, Eos, eosOptions);
          eos.transaction(tr => {
            tr.delegatebw({
              from: scatter.identity.accounts[0].name,
              receiver: scatter.identity.accounts[0].name,
              stake_net_quantity: "0.0000 EOS",
              stake_cpu_quantity: `${parseFloat(cpuInput).toFixed(4)} EOS`,
              transfer: 0
            });
            }).then(result => {
              checkIdentity();
              $("#cpuContainer").css("display", "none");
            })
          }else{
              $.ajax({
                url:`${baseUrl}/account/delegatebw.do`,
                type:"post",
                data:{
                  account:userName,
                  quantity:`${cpuInput} EOS`
                },
                success:function(msg){
                  checkIdentity();
                  $("#cpuContainer").css("display", "none");
                }
              })
        }
      }else{
        popup({type:'error',msg:inputAmount,delay:1000,bg:true,clickDomCancel:true});
      }
    });
});

//关闭CPU抵押窗口
$("#cpuClose").click(function(){
  $("#cpuContainer").css("display", "none");
  $("#cpuInput").val("");
});


//手机号登录
let phoneReg=/^[1]{1}[0-9]{10}/g;
let phoneBoolean=false;
let phoneNum="";
$("#userPhone").bind("input propertychange",function(event){
  phoneNum=$(this).val();
  if(phoneReg.test(phoneNum)==true){
      phoneBoolean=true;
      $(this).css("color","#3083de");
  }else{
      $(this).css("color","red");
      phoneBoolean=false;
  }
});


 //密码验证
 let regPassword=/^(?=.*[a-z])(?=.*\d)[^]{8,12}$/;
 let passWordBoolean=false;
 let passwordInput="";
 $("#userPassword").bind("input propertychange",function(event){
     passwordInput=$(this).val();
     if(regPassword.test(passwordInput)==true){
         passWordBoolean=true;
         $(this).css("color","#3083de");
     }else{
         $(this).css("color","red");
         passWordBoolean=false;
     }
 });

 //手机号登录
$("#quickComfire").click(function(){
  $.ajax({
    type:"post",
    url:`${baseUrl}/account/scoreUserLogin.do`,
    data:{
      data:`${phoneNum}`,
      password:`${passwordInput}`
    },
    success:function(msg){
      let userPhone=msg.data;
      userPhone=userPhone.substr(0,3)+"****"+userPhone.substr(7);  
      // console.log(userPhone);
      if(msg.success==true){
        $("#loginContainer").css("display","none");
        $("#userInfo").css("display", "block");
        $("#phoneContainer").css("display", "block");
        $("#telephone").text(userPhone);
        $("#integral").text(msg.score+"GOS");
        $("#loginBtn").css("display", "none");
        $("#loginContainer").css("display", "none");
        $(".loginBtnBets").css("display", "none");
        $(".betsBtn").css("display", "block");
        $(".moneyUnit").text("GOS");
        $(".chooseUnit").css("display","none");
        $(".unitList").css("display","none");
        getGosBalance();
        getGosHis();
        loginType="3";
        window.sessionStorage.setItem("loginType",loginType);
        window.sessionStorage.setItem("phone",msg.data);
      }
    }
  })
});



//请求积分投注记录
function getGosHis(){
  $.ajax({
    type:"get",
    url:`${baseUrl}/lottery/getUserBetHisScore.do`,
    data:{
      pageNum:pageNumGos
    },
    success:function(msg){
      console.log(msg);
    }
  })
};



//注册跳转
$(".registerComfire").click(function(){
  window.location.href=`${baseUrl}/wap/registerstep`;
});
//充值跳转
$("#chongzhi").click(function(){
  let phone=window.sessionStorage.getItem("phone");
  if(!phone){
   $("#tips").css("display","block");
   $("#tips").click(function(){
     $(this).css("display","none");
   })
   $("#gotoLogin").click(function(){
    $("#loginContainer").css("display","block");
    $("#tips").css("display","none");
   })
   $("#gotoRegister").click(function(){
    window.location.href=`${baseUrl}/wap/registerstep`;
  })
  }else{
    window.location.href=`${baseUrl}/wap/payfor?phone=${phone}`;
  }
});
//提现跳转
$("#tixian").click(function(){
  window.location.href=`${baseUrl}/wap/cashWithdrawal`;
})

//刷新页面请求奖池数量
if(loginType){
  if(loginType=="1"||loginType=="2"){
    getBlance();
  }
  else if(loginType=="3"){
    getGosBalance();
  }
}else{
  getBlance();
}


$.ajax({
  type:"get",
    url:`${baseUrl}/account/isLogin`,
    success:function(msg){
      if(msg==true){
        $.ajax({
          type:"post",
          url:`${baseUrl}/account/getScoreUser`,
          success:function(msg){
            console.log(msg);
            let userPhone=msg.account.mobilePhone;
            userPhone=userPhone.substr(0,3)+"****"+userPhone.substr(7);  
            $("#loginContainer").css("display","none");
            $("#userInfo").css("display", "block");
            $("#phoneContainer").css("display", "block");
            $("#telephone").text(userPhone);
            $("#integral").text(msg.account.score+"GOS");
            $("#loginBtn").css("display", "none");
            $("#loginContainer").css("display", "none");
            $(".loginBtnBets").css("display", "none");
            $(".betsBtn").css("display", "block");
            $(".moneyUnit").text("GOS");
            $(".chooseUnit").css("display","none");
            $(".unitList").css("display","none");
            getGosBalance();
            getGosHis();
          }
        })
      }
    }
});
