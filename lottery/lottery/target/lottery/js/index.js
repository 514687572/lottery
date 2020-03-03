// if(window.sessionStorage.getItem("userName")){
//   userName=window.sessionStorage.getItem("userName");
// }else{
//   userName=window.sessionStorage.getItem("phone");
// }
// publicKey=window.sessionStorage.getItem("publicKey");
// loginType=window.sessionStorage.getItem("loginType");


//初始调用scatter
if(!loginType||loginType=="1"){
  ScatterJS.scatter.connect("My-App").then(connected => {
    if (!connected) return false;
    const scatter = ScatterJS.scatter;
    const requiredFields = {accounts: [network]};
    scatter.getIdentity(requiredFields).then(() => {
        const account = scatter.identity.accounts.find(x => x.blockchain === "eos");
        const eosOptions = {expireInSeconds: 60};
        const eos = scatter.eos(network, Eos, eosOptions);
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
        loginType="1";
        window.sessionStorage.setItem("loginType",loginType);
        checkIdentity();
        getHisBets();
      })
      .catch(error => {
      });
  });
}


//判断是英语还是中文，改变个别英语显示样式
if (lang == "en_US") {
  $("#invitePart1").css({
    height: "540px"
  });
  $("#getAward").css({
    height: "240px"
  });
  $("#getAward div:eq(1)").css({
    width: "750px"
  });
  $("#inviteFriends").css({
    height: "180px"
  });
  $("#inviteContainer").css({
    height: "1680px"
  });
  $("#invitePart2").css({
    height: "360px"
  });
  $("#rulerWords").css({
    height: "100px"
  });
  $("#baseModelBtn").css("width","44px");
  $("#higherModelBtn").css("width","90px");
}


history(); //调用获取历史开奖信息
getBlance(); //调用获取奖金池数量


//点击更多获取更多历史开奖记录
$("#more").click(function () {
  pageNum += 1;
  history();
});


let HScrollHight = 0;
let HScrollTop = 0;
let HDivHight = $("#messageBox").height();
$("#messageBox").scroll(function(){
  HScrollHight = $(this)[0].scrollHeight;
  HScrollTop = $(this)[0].scrollTop;
  if(parseInt(HScrollTop) + HDivHight == HScrollHight){
    pageNum += 1;
    history();
  } 
})





//点击更多和滚动条下滑请求历史投注函数
function hisBetsAppend(){
  $("#loadingHisBets").css("display","block");
  $.ajax({
    type: "get",
    url: `${baseUrl}/lottery/getUserBetHis.do`,
    data: {
      userName: window.ScatterJS.scatter.identity==null ?userName:scatter.identity.accounts[0].name,
      pageNum: pageNumMybets
    },
    success: function (msg) {
      $("#loadingHisBets").css("display","none");
      for (let item of msg.userHis) {
        if (item.lotteryBonus) {
          $("#orderContainer").append(`<div class="orderHead">
            <div class="orderHeadInner">
              <div class="recordsId" title=${dice126}>
              ${item.betNum}
              <a 
              href="https://bloks.io/transaction/3333a6acac949244b1231d9e0263191ca6041d1ebd47ce457e704eda8d00a238?${item.transactionId}" 
              class="search" target="_blank"
              />
              </div>
              <div>${item.betId}</div>
              <div>
              ${item.largeNum == "1" && item.largeNum != null? englistBig: ""}
              ${item.largeNum == "0" && item.largeNum != null ? englistSmall : ""}
              ${item.lotterySingle == "1" && item.lotterySingle != null? englistOdd: ""}
              ${item.lotterySingle == "0" && item.lotterySingle != null? englistEven: ""}
              ${item.lotteryFive ? item.lotteryFive : ""}
              ${item.lotteryFour ? item.lotteryFour : ""}
              ${item.lotteryThree ? item.lotteryThree : ""}
              ${item.lotteryTwo ? item.lotteryTwo : ""}
              ${item.lotteryOne ? item.lotteryOne : ""}
              </div>
              <div style="color:#ff564f">${win}</div>
              <div>${item.lotteryBonus}</div>
              <div>${datetimeDay(item.updateTime)}</div>
            </div>
            </div>`);
        } else {
          let betJsonStr1 = `${item.betJson}`;
          if (lang == "en_US") {
            betJsonStr1 = betJsonStr1
              .replace("大", "B")
              .replace("小", "S")
              .replace("单", "O")
              .replace("双", "E");
          }
          $("#orderContainer").append(`<div class="orderHead">
            <div class="orderHeadInner">
              <div class="recordsId" title=${dice126}>${item.betNum}
              <a href="https://bloks.io/transaction/3333a6acac949244b1231d9e0263191ca6041d1ebd47ce457e704eda8d00a238?${
            item.transactionId
          }" class="search" target="_blank"/></div>
              <div >${item.betId}</div>
              <div title=${betJsonStr1} style="overflow: hidden; text-overflow: ellipsis;">
              ${betJsonStr1}
              </div>
              <div style="color:#3083de">${englistBet}</div>
              <div>${item.noteMoney}</div>
              <div>${datetimeDay(item.createTime)}</div>
            </div>
            </div>`);
        }
      }
    }
  });
}


// 点击更多获取更多我的投注
$("#moreBets").click(function () {
  pageNumMybets += 1;
  if (window.ScatterJS.scatter.identity!=null || userName != "") {
    hisBetsAppend();
  }else{
      popup({type:'error',msg:pleaseLogin,delay:2000,bg:true,clickDomCancel:true});
  }
});




let BScrollHight = 0;
let BScrollTop = 0;
let BDivHight = $("#orderContainer").height();
$("#orderContainer").scroll(function(){
  BScrollHight = $(this)[0].scrollHeight;
  BScrollTop = $(this)[0].scrollTop;
  if(parseInt(BScrollTop) + BDivHight == BScrollHight){
    pageNumMybets += 1;
    hisBetsAppend();
  } 
})


// 登录弹框效果切换
$("#myChainId").click(function () {
  $("#myChainId").css("border-bottom", "1px solid #3083de");
  $("#scatterLogin").css("border-bottom", "none");
  $("#privateLogin").css("display", "block");
  $("#loadScatter").css("display", "none");
});
$("#scatterLogin").click(function () {
  $("#scatterLogin").css("border-bottom", "1px solid #3083de");
  $("#myChainId").css("border-bottom", "none");
  $("#privateLogin").css("display", "none");
  $("#loadScatter").css("display", "block");
});




let carouselAry = [];
let stopAry=[];
let isStop = false;
let awardCount=0;
let awardStr="";
let count = 0;
let endAwad = false; //判断开奖号码是否出完
let timeEnter, timeStart;
let awardAry=[];
let hisFirstStr="";

//正常轮播函数
function hashList(data){
        let liFind = "move_li";
        let startAward="find_Li";
        let lotteryNum=data.lottery_num;  //期号
        let blockChainNo = data.block_num;    //区块ID
        let blockChainHash = data.id;    //hash值
        let lastNumber=data.id[data.id.length-1];  //hash值最后一位
        let normalHashNum= `<span>...</span><span class=hashBox>${blockChainHash.slice(25,blockChainHash.length - 1)}</span><span style="color:#86919e">${lastNumber}</span>`;  //没有开奖的
        let changeColorHashNum= `<span>...</span><span class=hashBox>${blockChainHash.slice(25,blockChainHash.length - 1)}</span><span style="color:#ff5c4a">${lastNumber}</span>`;
        let openTime = Date.parse(data.header.timestamp.replace("-", "/").replace("T", "z")); //数据推送的最新时间
        let hashTime=datetimeFormat(openTime);
        let carouselObj = {
            "startAward":data.prize_start==true?startAward:liFind,
            "lotteryNum":data.prize_start==true?lotteryNum:"",
            "blockChainNo": blockChainNo,
            "blockChainHash": data.prize_num==true?changeColorHashNum:normalHashNum,
            "theLotteryTime": hashTime,
        };
        if (carouselAry.length <= 11) {
            carouselAry.unshift(carouselObj);
        } else {
            carouselAry.pop();
            carouselAry.unshift(carouselObj);
        };
       
        let carouselContent = "";
        for (let item of carouselAry) {
            carouselContent += `
            <li class=${item.startAward} style='font-family: consolas,monaco,monospace;font-size=20px'>
            <div>${item.lotteryNum}</div>
            <div class='hashId'>${item.blockChainNo}</div>
            <a class='hashNumber' style='text-align:center'  target="blank" href="https://bloks.io/block/${data.block_num}">${item.blockChainHash} </a>
            <div class='hashDate'>${item.theLotteryTime}</div>
            </li>
                `
        };
        tempul.html(`${carouselContent}`);
        tempul.children(":first").animate({marginTop: "37px"});
}

//鼠标Hover执行的函数
function hashListMouseHover(data){
    let liFind = "move_li";
    let startAward="find_Li";
    let lotteryNum=data.lottery_num;   //期号
    let blockChainNo = data.block_num;    //区块ID
    let blockChainHash = data.id;    //hash值
    let lastNumber=data.id[data.id.length-1];   //hash值最后一位
    let normalHashNum= `<span>...</span><span class=hashBox>${blockChainHash.slice(25,blockChainHash.length - 1)}</span><span style="color:#86919e">${lastNumber}</span>`;  //没有开奖的
    let changeColorHashNum= `<span>...</span><span class=hashBox>${blockChainHash.slice(25,blockChainHash.length - 1)}</span><span style="color:#ff5c4a">${lastNumber}</span>`;
    let openTime = Date.parse(data.header.timestamp.replace("-", "/").replace("T", "z")); //数据推送的最新时间
    let hashTime=datetimeFormat(openTime);
    let carouselObj = {
        "startAward":data.prize_start==true?startAward:liFind,
        "lotteryNum":data.prize_start==true?lotteryNum:"",
        "blockChainNo": blockChainNo,
        "blockChainHash": data.prize_num==true?changeColorHashNum:normalHashNum,
        "theLotteryTime": hashTime,
    };
    if (carouselAry.length <= 11) {
      carouselAry.unshift(carouselObj);
    } else {
      carouselAry.pop();
      carouselAry.unshift(carouselObj);
    };
}

//开奖号码判断推送函数
function newAward(data){
  let lotteryNum=0;
  let openTime = Date.parse(data.header.timestamp.replace(/-/g, "/").replace("T", "z")); //数据推送的最新时间
  let awradTime=datetimeFormat(openTime);
  let startTime=parseInt(awradTime.split(":")[2]);
   //投注区域时间实时计时
   betsTime = 60 - startTime;
   if (betsTime == 0) {
     $("#betsUsers").text("0");
     $("#betsUsers").css({
       width: "0"
     });
     getBlance();
   }
   if (betsTime < 10) {
     $("#second").text(`0${betsTime}`);
   } else {
     $("#second").text(betsTime);
   }
  if(data.prize_start==true){
    window.sessionStorage.removeItem("userBlance");
    $("#betsUsers").text("0");
     $("#betsUsers").css({
       width: "0"
     });
    endAwad = false;
    isStart = true;
    $("#kjhm").text("");
    $("dx").text("");
    $("ds").text("");
    lotteryNum=data.lottery_num;
    $("#kjTime").text(`${awradTime}`); //当前开奖的时间
    $("#qh").text(`${lotteryNum}`); //当前开奖期数
    $("#nextPeriods").text(`${period1}${parseInt(lotteryNum) + 1}${period2}`); //投注下一期的期数显示
    $("#nowAward").css("display","block");
  }
  
  if(data.prize_num==true){
    count+=1;
    $("#current").css({
      color: "white"
    });
    $("#dx").css({
      color: "#3083de"
    });
    $("#ds").css({
      color: "#3083de"
    });
    $("#kjhm").css({
      color: "#ff5c4a"
    });
     
    let awardNumber=data.id[data.id.length-1];
    if(isStart){
      $("#kjhm").append(`${awardNumber}`); //将开奖号码推入当前开奖号码显示区
    }else{
      historyOnePage();
      newHistory();
      count=0;
    }
  }
  //判断开奖号码是否已出完，显示在当前开奖区域,并判断大小单双
  let a, b, c, d;
  if (count == 5) {
    let awardNum = parseInt($("#kjhm").text()[$("#kjhm").text().length - 1]);
    endAwad = true;
    if (awardNum >= 5) {
      $("#dx").text(englistBig);
      b = "resultSmall";
      a = "resultBig";
    } else {
      $("#dx").text(englistSmall);
      a = "resultSmall";
      b = "resultBig";
    }
    if (awardNum % 2 == 0) {
      $("#ds").text(englistEven);
      c = "resultSingle";
      d = "resultDouble";
    } else {
      $("#ds").text(englistOdd);
      d = "resultSingle";
      c = "resultDouble";
    }
    $("#messageBox").prepend(
      `<article>
        <span class="recordsId" title=${dice126}>${$("#qh").text()}</span>
        <span>${$("#kjTime").text()}</span>
        <span style="color:#ff5961;font-weight:bold">${$("#kjhm").text()}</span>
        <div>
        <span><b class=${b}>${englistBig}</b>-<b class=${a}>${englistSmall}</b></span>|<span><b class=${d}>${englistOdd}</b>-<b class=${c}>${englistEven}</b></span>
        </div>
      </article>`
    );
    $("#messageBox").children().last().remove();
    setTimeout(()=>{
      $("#current").css({color: "#9eaab5"});
      $("#dx").css({color: "#9eaab5"});
      $("#ds").css({color: "#9eaab5"});
      $("#kjhm").css({color: "#9eaab5"});
    },1000)
    count = 0;
  }
}


ws.onmessage = function (event) {
  // console.log(event);
  //拿到任何消息都说明当前连接是正常的
  if(event.data!='pong'){
      let obj=eval("("+event.data+")");
  }
  let data = JSON.parse(event.data);
  let dataObj = JSON.parse(data.text);
 
// console.log(data.from);
  //将等到的信息传入前端显示
 if (data.from == "block") {
    newAward(dataObj);
  if(!isStop){
    hashList(dataObj);
  }else{
    hashListMouseHover(dataObj)
  }
  tempul.hover(function(){
    isStop=true;
  },function(){
    isStop=false;
  })
}else if (data.from == "bet") {
    getBlance();
    userBlance = dataObj.limit;
    let betLiveTime = datetimeFormat(dataObj.date.time).split(".");
    if(dataObj.num==parseInt($("#nextPeriods").text().replace(`${period1}`,"").replace(`${period2}`,""))){
      window.sessionStorage.setItem("userBlance",userBlance);
      if(userBlance>=100000){
        $("#betsUsers").text(`${parseFloat(userBlance/1000).toFixed(1)}K`);
      }else{
        $("#betsUsers").text(`${parseFloat(userBlance).toFixed(1)}`);
      }
      $("#betsUsers").css({
        "width": `${(userBlance / totalLimitBets)*betsAllWidth}`
      });
    }
    let monthDay = data.date.split(" ")[0].split("-");
    $("#betsLiveContain").prepend(
      `<div class="userProfit">
        <p class="userDetail"><span>${dataObj.userName}</span><span style="color:purple">${parseFloat(dataObj.count).toFixed(4)}<b>EOS</b></span></p>
        <div>   
        <span>${dataObj.num}</span>-<span>${monthDay[1]}/${monthDay[2]}</span>
        <span>${betLiveTime[0]}</span>
        </div>
      </div>`
    );
  } else if (data.from == "prizeMsg") {
    window.sessionStorage.removeItem("userBlance");
    $("#betsUsers").text("0");
     $("#betsUsers").css({
       width: "0"
     });
    let prizeTime = datetimeFormat(dataObj.time.time).split(".");
    getBlance();
    if (data.fromName == $("#userName").text()){
      popup({type:'success',msg:`+${parseInt(dataObj.pri*10000)/10000}EOS`,delay:1000,callBack:function(){
        getHisBetsPage=1;
        pageNumMybets = 1;
        getHisBets();
        checkIdentity();
       }});
    }
    let monthDay = data.date.split(" ")[0].split("-");
    $("#betsLiveContain").prepend(
      `<div class="userProfit">
            <div class="userDetail"><span>${
              data.fromName
            }</span><div style="color:green"><span>+</span><span style="color:green">${parseFloat(
        dataObj.pri
      ).toFixed(4)}EOS</span></div></div>
            <div>
            <span>${dataObj.num}</span>-<span>${monthDay[1]}/${
        monthDay[2]
      }</span><span>${prizeTime[0]}</span>
            </div>
        </div>`
    );
  }else if(data.from=="invitation"){
    // console.log(dataObj)
    if(dataObj.invitation==$("#userName").text()){
      checkIdentity();
    } 
  }
};


//心跳检测
setInterval(function(){
  ws.send("ping");
}, 10000)



// 点击登录
$("#loginBtn").click(function () {
  $("#loginContainer").css("display", "block");
});
$(".loginBtnBets").click(function () {
  $("#loginContainer").css("display", "block");
});
// 点击关闭
$("#loginClose").click(function () {
  $("#loginContainer").css("display", "none");
});

// 语言选择切换
$("#headRight").click(function (event) {
  event.stopPropagation();
  $("#language").toggle();
});
$("#Ch").click(function () {
  $("#inputBox").html(
    `<img src="../images/Singapore.png" alt="" id="guoqiBox">Chinese`
  );
  $("#language").css("display", "none");
  $.ajax({
    type: "get",
    url: `${baseUrl}/lottery/updLocale?type=1`,
    success: function (mag) {
      window.location.reload();
    }
  });
});
$("#En").click(function () {
  $("#inputBox").html(
    `<img src="../images/guoqi02.png" alt="" id="guoqiBox">English`
  );
  $("#language").css("display", "none");
  $.ajax({
    type: "get",
    url: `${baseUrl}/lottery/updLocale?type=2`,
    success: function (mag) {
      window.location.reload();
    }
  });
});
$(document).click(function(){
  $("#language").css("display","none");
})


// 基础模式和高阶模式之间的切换
// 点击基础模式
$("#baseModelBtn").click(function () {
  modelBollen = "0";
  $(this).css("border-bottom", "1px solid #3083de");
  $("#baseModelBox").css("display", "block");
  $("#higherModelBox").css("display", "none");
  $(this).css("color", "#3083de");
  $("#higherModelBtn").css({
    color: "#4d5569",
    "border-bottom": "none"
  });
});

// 点击切换高阶模式
$("#higherModelBtn").click(function () {
  modelBollen = "1";
  $(this).css("border-bottom", "1px solid #3083de");
  $("#higherModelBox").css("display", "block");
  $("#baseModelBox").css("display", "none");
  $(this).css("color", "#3083de");
  $("#baseModelBtn").css({
    color: "#4d5569",
    "border-bottom": "none"
  });
});



// 邀请好友跳转
$("#inviteBtn").click(function () {
  inviteBoolean=true;
  rulerBoolean=false;
  hisBoolean=false;
  navColor();
  $("#inviteContainer").css("display", "block");
  $("#rulerContainer").css("display", "none");
  $("#app").css("display", "none");
  $("#historyContainer").css("display", "none");
  $("#myFriendsContainner").html("");
  if ($("#userName").text() != "") {
    // 邀请好友接口
    $.ajax({
      type: "post",
      url: `${baseUrl}/user/getUser.do`,
      data: {
        userName: $("#userName").text()
      },
      success: function (msg) {
        $("#linkInput").val(
          `${baseUrl}/lottery?userCode=${msg.user.userCode}`
        );
      }
    });

    //我的下级接口
    myFriends(pageNumFriends,perPageFriends);
  }
});


//点击下一页请求我的下级
$("#nextPage").click(function(){
  pageNumFriends+=1;
  if(pageNumFriends<=friendsPage){
    perPageFriends+=10;
    myFriends(pageNumFriends,perPageFriends)
  }
})
//点击上一页请求我的下级
$("#prePage").click(function(){
  pageNumFriends-=1;
  if(pageNumFriends>=1){
    perPageFriends-=10;
    myFriends(pageNumFriends,perPageFriends);
  }
})


//我的下级函数
function myFriends(pageNumFriends,perPageFriends){
  $.ajax({
    type: "get",
    url: `${baseUrl}/user/getMyChilds.do?pageNum=${pageNumFriends}&records=${perPageFriends}&userName=${$(
      "#userName"
    ).text()}`,
    success: function (msg) {
      friendsPage=msg.totalPage;
      let friendsStr = "";
      $("#totalFriends").text(`${common}${msg.total}${people}`);
      if(msg.childs.length==0){
        $("#pageTurn").css("display","none");
        $("#noFriends").css("display","block");
      }else{
        $("#curpage").text(`${pageNumFriends}/${msg.totalPage}`);
        for (let i = 0; i < msg.childs.length; i++) {
          friendsStr += `<div class="myFriends">
          <div>${i+perPageFriends*(pageNumFriends-1)}</div>
          <div style="color:white;">${msg.childs[i].userName}</div>
          <div style="color:white;">1</div>
          <div style="color:#ec524f;">${msg.childs[i].noteMoney}</div>
          <div style="color:#ec524f;">${msg.childs[i].lotteryBonus}</div>
          <div style="color:#ec524f;">${msg.childs[i].money}</div>
          </div>`;
          $("#myFriendsContainner").html(friendsStr);
        }
      }
    }
  });
}


//点击复制邀请框邀请码
$("#copyUrl").click(function () {
  $("#linkInput").select();
  if (document.execCommand("copy")) {
    document.execCommand("copy");
  }
});

// 玩法介绍跳转
$("#rluerIntroduce").click(function () {
  rulerBoolean=true;
  inviteBoolean=false;
  hisBoolean=false;
  navColor();
});

//邀请好友返回主界面
$("#backToindexI").click(function () {
  rulerBoolean=false;
  inviteBoolean=false;
  hisBoolean=false;
  navColor();
});

//游戏规则返回主界面
$("#backToIndexG").click(function () {
  rulerBoolean=false;
  inviteBoolean=false;
  hisBoolean=false;
  navColor();
});

//历史记录跳转
$("#historyBtn").click(function () {
  hisBoolean=true;
  rulerBoolean=false;
  inviteBoolean=false;
  navColor();
  $("#hisTbody").html("");
  //请求历史开奖记录
  $.ajax({
    type: "get",
    url: `${baseUrl}/lottery/getLotteryHis.do`,
    data: {
      pageNum: 1,
      rowNum: 23
    },
    success: function (msg) {
      for (let item of msg.hisRecords) {
        let a, b, c, d;
        if (item.largeNum == 0) {
          a = "resultSmall";
          b = "resultBig";
        } else {
          b = "resultSmall";
          a = "resultBig";
        }
        if (item.lotterySingle == 0) {
          c = "resultSingle";
          d = "resultDouble";
        } else {
          d = "resultSingle";
          c = "resultDouble";
        }
        $("#hisTbody").append(
          `<div class="historyTabContent">
            <div>${datetimeFormat(item.updateTime)}</div>
            <div>${item.recordsId}</div>
            <div style="color:purple">${item.lotteryFive}${item.lotteryFour}${
            item.lotteryThree
          }${item.lotteryTwo}${item.lotteryOne}</div>
            <div><span class=${b}>${englistBig}</span><span class=${a}>${englistSmall}</span>|<span class=${d}>${englistOdd}</span><span class=${c}>${englistEven}</span></div>
            </div>`
        );
      }
    }
  });
});

//历史记录返回主界面
$("#backToIndexH").click(function () {
  rulerBoolean=false;
  inviteBoolean=false;
  hisBoolean=false;
  navColor();
});





//ajax请求历史开奖记录
function history() {
  $("#loadingHisAward").css("display","block");
  $.ajax({
    type: "get",
    url: `${baseUrl}/lottery/getLotteryHis.do`,
    data: {
      pageNum: pageNum,
      rowNum: 10
    },
    success: function (msg) {
      $("#loadingHisAward").css("display","none");
      for (let item of msg.hisRecords) {
        if (item.largeNum == 0) {
          a = "resultSmall";
          b = "resultBig";
        } else {
          b = "resultSmall";
          a = "resultBig";
        }
        if (item.lotterySingle == 0) {
          c = "resultSingle";
          d = "resultDouble";
        } else {
          d = "resultSingle";
          c = "resultDouble";
        }
        $("#messageBox").append(`<article><span class="recordsId" title=${dice126}>${
          item.recordsId
        }</span><span>${datetimeFormat(item.updateTime)}</span>
        <span style="color:#ff5961;font-weight:bold">${item.lotteryFive}${
          item.lotteryFour
        }${item.lotteryThree}${item.lotteryTwo}${item.lotteryOne}</span>
        <div><span><b class=${b}>${englistBig}</b>-<b class=${a}>${englistSmall}</b></span>|<span><b class=${d}>${englistOdd}</b>-<b class=${c}>${englistEven}</b></span></div></article>`);
        // oMyBar1.setSize();
      }
      $("#nextPeriods").text(
        `${period1}${parseInt($(".recordsId:first").text()) + 1}${period2}`
      );
    }
  });
}

//ajax请求历史开奖记录
function historyOnePage() {
  $.ajax({
    type: "get",
    url: `${baseUrl}/lottery/getLotteryHis.do`,
    data: {
      pageNum: 1,
      rowNum: 10
    },
    success: function (msg) {
      let str="";
      for (let item of msg.hisRecords) {
        if (item.largeNum == 0) {
          a = "resultSmall";
          b = "resultBig";
        } else {
          b = "resultSmall";
          a = "resultBig";
        }
        if (item.lotterySingle == 0) {
          c = "resultSingle";
          d = "resultDouble";
        } else {
          d = "resultSingle";
          c = "resultDouble";
        }
        str+=
        `<article><span class="recordsId" title=${dice126}>${
          item.recordsId
        }</span><span>${datetimeFormat(item.updateTime)}</span>
        <span style="color:#ff5961;font-weight:bold">${item.lotteryFive}${
          item.lotteryFour
        }${item.lotteryThree}${item.lotteryTwo}${item.lotteryOne}</span>
        <div><span><b class=${b}>${englistBig}</b>-<b class=${a}>${englistSmall}</b></span>|<span><b class=${d}>${englistOdd}</b>-<b class=${c}>${englistEven}</b></span></div></article>`;
        // oMyBar1.setSize();
      }
      $("#messageBox").html(str);
      $("#nextPeriods").text(
        `${period1}${parseInt($(".recordsId:first").text()) + 1}${period2}`
      );
    }
  });
}

//首次进入请求最新一条历史记录
function newHistory(){
  $.ajax({
    type: "get",
    url: `${baseUrl}/lottery/getLotteryHis.do`,
    data: {
      pageNum: 1,
      rowNum: 1
    },
    success: function (msg) {
       for(let item of msg.hisRecords){
        $("#qh").text(`${item.recordsId}`);
        $("#kjTime").text(`${datetimeFormat(item.updateTime)}`);
        $("#kjhm").text(`${item.lotteryFive}${item.lotteryFour}${item.lotteryThree}${item.lotteryTwo}${item.lotteryOne}`);
        if (item.lotteryOne >= 5) {
          $("#dx").text(englistBig);
          b = "resultSmall";
          a = "resultBig";
        } else {
          $("#dx").text(englistSmall);
          a = "resultSmall";
          b = "resultBig";
        }
        if (item.lotteryOne % 2 == 0) {
          $("#ds").text(englistEven);
          c = "resultSingle";
          d = "resultDouble";
        } else {
          $("#ds").text(englistOdd);
          d = "resultSingle";
          c = "resultDouble";
        }
        $("#current").css({color: "#9eaab5"});
        $("#dx").css({color: "#9eaab5"});
        $("#ds").css({color: "#9eaab5"});
        $("#kjhm").css({color: "#9eaab5"});
       }
    }
  })
}
newHistory();







//我的投注记录请求函数
function getHisBets() {
  // console.log(userName)
    if (window.ScatterJS.scatter.identity!=null || userName != "") {
      $.ajax({
        type: "get",
        url: `${baseUrl}/lottery/getUserBetHis.do`,
        data: {
          userName: window.ScatterJS.scatter.identity == null ?
            userName :
            scatter.identity.accounts[0].name,
          pageNum: pageNumMybets
        },
        success: function (msg) {
          // console.log(msg)
          let hisbetsStr = "";
          for (let item of msg.userHis) {
            if (item.lotteryBonus) {
              hisbetsStr += `<div class="orderHead">
              <div class="orderHeadInner">
                <div class="recordsId" title=${dice126}>${
                  item.betNum
                }<a href="https://bloks.io/transaction/${
                item.transactionId
              }" class="search" target="_blank"/></div>
                <div>${item.betId}</div>
                <div>
                ${
                  item.largeNum == "1" && item.largeNum != null
                    ? englistBig
                    : ""
                }${
                item.largeNum == "0" && item.largeNum != null
                  ? englistSmall
                  : ""
              }
                ${
                  item.lotterySingle == "1" && item.lotterySingle != null
                    ? englistOdd
                    : ""
                }${
                item.lotterySingle == "0" && item.lotterySingle != null
                  ? englistEven
                  : ""
              }
                ${item.lotteryFive ? item.lotteryFive : ""}
                ${item.lotteryFour ? item.lotteryFour : ""}
                ${item.lotteryThree ? item.lotteryThree : ""}
                ${item.lotteryTwo ? item.lotteryTwo : ""}
                ${item.lotteryOne ? item.lotteryOne : ""}
                </div>
                <div style="color:#ff564f">${win}</div>
                <div>${item.lotteryBonus}</div>
                <div>${datetimeDay(item.updateTime)}</div>
              </div>
              </div>`;
              $("#orderContainer").html(hisbetsStr);
            } else {
              let betJsonStr = `${item.betJson}`;
              if (lang == "en_US") {
                betJsonStr = betJsonStr
                  .replace("大", "B")
                  .replace("小", "S")
                  .replace("单", "O")
                  .replace("双", "E");
              };
              hisbetsStr += `<div class="orderHead">
              <div class="orderHeadInner">
                <div class="recordsId" title=${dice126}>${
                  item.betNum
                }<a href="https://bloks.io/transaction?${
                item.transactionId
              }" class="search" target="_blank"/></div>
                <div>${item.betId}</div>
                <div title=${betJsonStr} style="overflow: hidden; text-overflow: ellipsis;">
                ${betJsonStr}
                </div>
                <div style="color:#3083de">${englistBet}</div>
                <div>${parseInt(item.noteMoney*100)/100}</div>
                <div>${datetimeDay(item.createTime)}</div>
              </div>
              </div>`;
              $("#orderContainer").html(hisbetsStr);
            }
          }
          $("#orderContainer").children(":first").mouseenter(function(){
               $(this).css({
                "background-color": "#313862",
                "border": "1px solid #3083de"
               });
          })
          $("#orderContainer").children(":first").mouseleave(function(){
            $(this).css({
            "background-color": "#252a48",
             "border": "none"
            });
       })
        }
      });
    }
}



$("#closeCpu").click(function(){
  $("#cpuContainer").css("display", "none");
})



function toIndex() {
  window.location.href=`${baseUrl}/`;
}


// 导航访问颜色改变
function navColor(){
  if(rulerBoolean==true){
    $("#rluerIntroduce").css("color","#3083de");
    $("#rulerContainer").css("display", "block");
    
  }else{
    $("#rluerIntroduce").css("color","white");
    $("#rulerContainer").css("display", "none");
  }
  if(inviteBoolean==true){
    $("#inviteBtn").css("color","#3083de");
    $("#inviteContainer").css("display", "block");
    $("#app").css("display", "none");
  }else{
    $("#inviteBtn").css("color","white");
    $("#inviteContainer").css("display", "none");
  }
  if(hisBoolean==true){
    $("#historyBtn").css("color","#3083de");
    $("#historyContainer").css("display", "block");
    $("#app").css("display", "none");
  }else{
    $("#historyBtn").css("color","white");
    $("#historyContainer").css("display", "none");
  }
  if(rulerBoolean==false&&inviteBoolean==false&&hisBoolean==false){
    $("#app").css("display", "block");
  }else{
    $("#app").css("display", "none");
  }
}

$("body").on("click",".recordsId",function(){
  $("#resultContain").html("");
  let recordsId=$(this).text();
  $("#awardBack").css("display","block");
  $("#resultBlockNum").text(`${recordsId}`);
  $.ajax({
    type:"get",
    url:`${baseUrl}/lottery/getBlockNum?betNum=${recordsId}`,
    success:function(data){
      // console.log(data)
      $("#resultId").text(`${data.betNum}`);
      let firstNum=data.fristNum;
      let lastNum=data.lastkNum;
      firstBlock_id=getBlock_id(firstNum);
      lastBlock_id=getBlock_id(lastNum);
      let a;
      a=setInterval(()=>{
        getAwadDtails(lastBlock_id);
        lastBlock_id++;
        if(firstBlock_id<lastBlock_id){
          clearInterval(a);
        }
      },500)
    }
  })
})


function getBlock_id(block_num){
  let block_id=0;
  $.ajax({
    type:"POST",
    url:"https://eosjs.eosplay.com/v1/chain/get_block",
    "contentType":"application/json",
    data:JSON.stringify({"block_num_or_id":`${block_num}`}),
    async:false,
    success:function(msg){
       block_id= msg.block_num;
    }
  })
  return block_id;
}

function getAwadDtails(blockNum){
  $.ajax({
    type:"POST",
    url:"https://eosjs.eosplay.com/v1/chain/get_block",
    "contentType":"application/json",
    data:JSON.stringify({"block_num_or_id":`${blockNum}`}),
    async:false,
    success:function(msg){
       let reg=/^[0-9]*$/;
       block_id= msg.block_num;
       let hashStr="";
       let lastHash="";
       lastHash=msg.id[msg.id.length-1];
       if(reg.test(lastHash)==true){
       hashStr=`<span>...${msg.id.slice(40,msg.id.length-1)}</span><span style="color:#ff5c4a">${lastHash}</span>`;
       }else{
        hashStr=`...${msg.id.slice(40)}`;
       }
       $("#resultContain").prepend(
         `<div style="display:flex;justify-content:space-between;margin-top:5px;font-size:14px;">
            <span>${msg.block_num}</span>
            <span style="font-family: consolas,monaco,monospace;font-size:16px;">${hashStr}</span>
            <span>${datetimeFormat(Date.parse(msg.timestamp.replace("-", "/").replace("T", "z")))}</span>
          </div>`
     )
    }
  })
}

$("#resultClose").click(function(){
  $("#awardBack").css("display","none");
})