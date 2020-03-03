$("#loginClose").click(function(){
  $("#loginContainer").css("display","none");
})

//获取历史开奖记录函数(3条)
function getHisAwardThree(){
  $.ajax({
    type:"get",
    url:`${baseUrl}/lottery/getLotteryHis.do`,
    data:{
      pageNum:1,
      rowNum:4
    },
    success:function(msg){
      let str="";
      for(let i=0;i<msg.hisRecords.length; i++){
      enterLotteryNum=msg.hisRecords[0].recordsId;
      $("#periods").text(`${period1}${enterLotteryNum+1}${period2}`);     
      str+=
      `<div style="height:0.24rem;line-height: 0.24rem;display: flex;justify-content: space-between;">
        <span style="color:#b44141;font-size:0.12rem;">
        ${msg.hisRecords[i].lotteryFive}
        ${msg.hisRecords[i].lotteryFour}
        ${msg.hisRecords[i].lotteryThree}
        ${msg.hisRecords[i].lotteryTwo}
        ${msg.hisRecords[i].lotteryOne}
        </span>
        <span style="color:white;font-size:0.12rem;">${datetimeFormat(msg.hisRecords[i].updateTime)}</span>
      </div>`
      }
      $("#awardContainer").html(str);
    }
  })
}
getHisAwardThree();

//历史开奖(10条)
function getHisAwardTen(){
  let a,b,c,d;
  $.ajax({
    type:"get",
    url:`${baseUrl}/lottery/getLotteryHis.do`,
    data:{
      pageNum:getHisAwardPage,
      rowNum:getHisAwardRow
    },
    success:function(msg){
      let str="";
      for(let item of msg.hisRecords){
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
        let hisTime=datetimeDay(item.updateTime);
        let month=hisTime.split(" ")[1].split("/");
        let hour=hisTime.split(" ")[2].split(":");
        $("#myHisAwardTab").append(
          ` 
          <div style="padding:0 0.1rem;height:0.3rem;line-height: 0.3rem;font-size:0.12rem;color:white;text-align: center;display: flex;justify-content: space-between">
          <div style="width: 0.72rem;">${month[1]}/${month[2]} ${hour[0]}:${hour[1]}</div>
          <div style="width: 0.68rem;" class="recordsId">${item.recordsId}</div>
          <div style="width: 0.58rem;color:#ff5c4a">${item.lotteryFive}${item.lotteryFour}${item.lotteryThree}${item.lotteryTwo}${item.lotteryOne}</div>
          <div style="width: 0.70rem;"><span><b class=${a}>${englistBig}</b><b class=${b}>${englistSmall}</b></span><span style="color:#ff5c4a">|</span><span><b class=${d}>${englistOdd}</b><b class=${c}>${englistEven}</b></span></div>
        </div>`)
      }
    }
  })
}

function getHisAwardNew(){
  let a,b,c,d;
  $.ajax({
    type:"get",
    url:`${baseUrl}/lottery/getLotteryHis.do`,
    data:{
      pageNum:1,
      rowNum:1
    },
    success:function(msg){
      let str="";
      for(let item of msg.hisRecords){
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
        let hisTime=datetimeDay(item.updateTime);
        let month=hisTime.split(" ")[1].split("/");
        let hour=hisTime.split(" ")[2].split(":");
        $("#myHisAwardTab").prepend(
          ` 
          <div style="padding:0 0.1rem;height:0.3rem;line-height: 0.3rem;font-size:0.12rem;color:white;text-align: center;display: flex;justify-content: space-between">
          <div style="width: 0.72rem;" class="recordsId">${month[1]}/${month[2]} ${hour[0]}:${hour[1]}</div>
          <div style="width: 0.68rem;">${item.recordsId}</div>
          <div style="width: 0.58rem;color:#ff5c4a">${item.lotteryFive}${item.lotteryFour}${item.lotteryThree}${item.lotteryTwo}${item.lotteryOne}</div>
          <div style="width: 0.70rem;"><span><b class=${a}>${englistBig}</b><b class=${b}>${englistSmall}</b></span><span style="color:#ff5c4a">|</span><span><b class=${d}>${englistOdd}</b><b class=${c}>${englistEven}</b></span></div>
        </div>`);
        $("#myHisAwardTab").children().last().remove();
      }
    }
  })
}
getHisAwardTen();
let nScrollHight = 0;
let nScrollTop = 0;
let nDivHight = $("#myHisAwardTab").height();

$("#myHisAwardTab").on("touchmove",function () {
  nScrollHight = $(this)[0].scrollHeight;
  nScrollTop = $(this)[0].scrollTop;
});
$("#myHisAwardTab").on("touchend",function(){
  if(nScrollTop + nDivHight == nScrollHight){
    $("#loading").css("display","block");
    getHisAwardPage++;
    getHisAwardTen();
    setTimeout(()=>{
      $("#loading").css("display","none");
    },500)
  }
})

//历史投注记录
function getHisBets(){
    $.ajax({
      type:"get",
      url:`${baseUrl}/lottery/getUserBetHis.do`,
      data: {
        userName:userName,
        pageNum: getHisBetsPage
      },
      success:function(msg){
         let str="";
         for(let item of msg.userHis){
          let hisTime=datetimeDay(item.updateTime);
          let month=hisTime.split(" ")[1].split("/");
          let hour=hisTime.split(" ")[2].split(":");
           if(item.lotteryBonus){
            str+=`  
            <div class="myBetsRow" style="padding:0 0.1rem;height:0.3rem;line-height: 0.3rem;font-size:0.12rem;color:white;text-align: center;display: flex;justify-content: space-between">
              <div style="width: 0.64rem;" class="recordsId">${item.betNum}</div>
              <div style="width: 0.60rem;">
              ${item.largeNum == "1" && item.largeNum != null? englistBig: ""}
              ${item.largeNum == "0" && item.largeNum != null? englistSmall: ""}
              ${item.lotterySingle == "1" && item.lotterySingle != null? englistOdd: ""}
              ${item.lotterySingle == "0" && item.lotterySingle != null? englistEven: ""}
              ${item.lotteryFive ? item.lotteryFive : ""}
              ${item.lotteryFour ? item.lotteryFour : ""}
              ${item.lotteryThree ? item.lotteryThree : ""}
              ${item.lotteryTwo ? item.lotteryTwo : ""}
              ${item.lotteryOne ? item.lotteryOne : ""}
              </div>
              <div style="width: 0.28rem;color:#ff5c4a;">${win}</div>
              <div style="width: 0.60rem;color:#ff5c4a;">+${item.lotteryBonus}</div>
              <div style="width: 0.68rem;">${month[1]}/${month[2]} ${hour[0]}:${hour[1]}</div>
            </div>`
            $("#myHisBetTab").html(str);
           }else{
            let betJsonStr = `${item.betJson}`;
            if (lang == "en_US") {
              betJsonStr = betJsonStr
                .replace("大", "B")
                .replace("小", "S")
                .replace("单", "O")
                .replace("双", "E");
            }
            str+=`  
            <div class="myBetsRow" style="padding:0 0.1rem;height:0.3rem;line-height: 0.3rem;font-size:0.12rem;color:white;text-align: center;display: flex;justify-content: space-between;">
              <div style="width: 0.64rem;" class="recordsId">${item.betNum}</div>
              <div  style="width: 0.60rem;overflow: hidden; text-overflow: ellipsis;" class="totalNumBtn">
              ${betJsonStr}
              </div>
              <div style="width: 0.28rem;color:#3083de;">${englistBet}</div>
              <div style="width: 0.60rem;color:#3083de;">-${parseInt(item.noteMoney*100)/100}</div>
              <div style="width: 0.68rem;">${month[1]}/${month[2]} ${hour[0]}:${hour[1]}</div>
            </div>`
            $("#myHisBetTab").html(str);
           }
         }
         
      }
    })
}

function getHisBetsAppend(){
  $("#loading").css("display","block");
  $.ajax({
    type:"get",
    url:`${baseUrl}/lottery/getUserBetHis.do`,
    data: {
      userName:userName,
      pageNum: getHisBetsPage
    },
    success:function(msg){
      $("#loading").css("display","none");
       let str="";
       for(let item of msg.userHis){
        let hisTime=datetimeDay(item.updateTime);
        let month=hisTime.split(" ")[1].split("/");
        let hour=hisTime.split(" ")[2].split(":");
         if(item.lotteryBonus){
          $("#myHisBetTab").append(`  
          <div class="myBetsRow" style="padding:0 0.1rem;height:0.3rem;line-height: 0.3rem;font-size:0.12rem;color:white;text-align: center;display: flex;justify-content: space-between">
            <div style="width: 0.64rem;" class="recordsId">${item.betNum}</div>
            <div style="width: 0.60rem;">
            ${item.largeNum == "1" && item.largeNum != null? englistBig: ""}
            ${item.largeNum == "0" && item.largeNum != null? englistSmall: ""}
            ${item.lotterySingle == "1" && item.lotterySingle != null? englistOdd: ""}
            ${item.lotterySingle == "0" && item.lotterySingle != null? englistEven: ""}
            ${item.lotteryFive ? item.lotteryFive : ""}
            ${item.lotteryFour ? item.lotteryFour : ""}
            ${item.lotteryThree ? item.lotteryThree : ""}
            ${item.lotteryTwo ? item.lotteryTwo : ""}
            ${item.lotteryOne ? item.lotteryOne : ""}
            </div>
            <div style="width: 0.28rem;color:#ff5c4a;">${win}</div>
            <div style="width: 0.60rem;color:#ff5c4a;">+${item.lotteryBonus}</div>
            <div style="width: 0.68rem;">${month[1]}/${month[2]} ${hour[0]}:${hour[1]}</div>
          </div>`)
         }else{
          let betJsonStr = `${item.betJson}`;
          if (lang == "en_US") {
            betJsonStr = betJsonStr
              .replace("大", "B")
              .replace("小", "S")
              .replace("单", "O")
              .replace("双", "E");
          }
          $("#myHisBetTab").append(`  
          <div class="myBetsRow" style="padding:0 0.1rem;height:0.3rem;line-height: 0.3rem;font-size:0.12rem;color:white;text-align: center;display: flex;justify-content: space-between">
            <div style="width: 0.64rem;" class="recordsId">${item.betNum}</div>
            <div  title=${betJsonStr} style="width: 0.60rem;overflow: hidden; text-overflow: ellipsis;" class="totalNumBtn">
            ${betJsonStr}
            </div>
            <div style="width: 0.28rem;color:#3083de;">${englistBet}</div>
            <div style="width: 0.60rem;color:#3083de;">-${parseInt(item.noteMoney*100)/100}</div>
            <div style="width: 0.68rem;">${month[1]}/${month[2]} ${hour[0]}:${hour[1]}</div>
          </div>`)
         }
       } 
    }
  })

}



let HScrollHight = 0;
let HScrollTop = 0;
let HDivHight = $("#myHisBetTab").height();
$("#myHisBetTab").on("touchmove",function(){
  HScrollHight = $(this)[0].scrollHeight;
  HScrollTop = $(this)[0].scrollTop;
});
$("#myHisBetTab").on("touchend",function(){
  if(HScrollHight-parseInt((HScrollTop + HDivHight))>=0&&HScrollHight-(HScrollTop + HDivHight)<=2){
    getHisBetsPage+=1;
    getHisBetsAppend();
  } 
});

let carouselAry = [];
let awardCount=0;
let awardStr="";
function theLotteryList(data) {
        let lotteryNum=0;
        let blockChainNo = data.block_num;
        let blockChainHash = data.id.slice(40,data.id.length - 1);
        let lastparseFloat=data.id[data.id.length-1]
        let normalHashNum=`<b>${lastparseFloat}</b>`
        let changeColorHashNum=`<b style="color:#ff5c4a">${lastparseFloat}</b>`
        let openTime = datetimeFormat(data.timestamp); //数据推送的最新时间
        let timeStart = parseInt(openTime.split(":")[2]); //最新数据的推送时间只取秒
        //投注区域时间实时计时
        betsTime = 60 - timeStart;
        if (betsTime == 1) {
          $("#betsUsers").text("0");
          $("#betsUsers").css({
            width: "0"
          });
          if(loginType=="3"){
            getGosBalance();
          }else{
            getBlance();
          }
        }
        if (betsTime < 10) {
          $("#timeLeft").text(`0${betsTime}`);
        } else {
          $("#timeLeft").text(betsTime);
        }
        let carouselObj = {
            "blockChainNo": blockChainNo,
            "lastparseFloat":data.prize_num==true?changeColorHashNum:normalHashNum,
            "blockChainHash": blockChainHash,
            "theLotteryTime": openTime,
        };
        if (carouselAry.length <= 10) {
            carouselAry.unshift(carouselObj);
        } else {
            carouselAry.pop();
            carouselAry.unshift(carouselObj);
        }
       
        let carouselContent = "";
        for (let item of carouselAry) {
            carouselContent += `
            <div class="hashContainer">
                <span style="color:#6a727d;font-size:0.12rem;">${item.blockChainNo}</span>
                <a  target="blank" href="https://bloks.io/block/${data.block_num}" style="color:#6a727d;font-size:0.12rem;text-decoration:none;">...${item.blockChainHash}${item.lastparseFloat}</a>
                <span style="color:#6a727d;font-size:0.12rem;">${item.theLotteryTime}</span>
            </div>
                `
        }
        lastNumbClass="normalLastNum";
        $("#hashPlay").html(carouselContent);
        if(data.prize_num==true){
          awardStr+=`${lastparseFloat}`;
          awardCount+=1;
        };
        if(data.prize_start==true){
          window.sessionStorage.removeItem("userBlance");
          $("#betsUsers").text("0");
          $("#betsUsers").css({
            width: "0"
          });
          lotteryNum=data.lottery_num
          isStart=true;
        $("#periods").text(`${period1}${parseInt(lotteryNum) + 1}${period2}`)  
        }
        if(awardCount==5){
          if(isStart){
            $("#awardContainer").prepend(
              `<div style="height:0.24rem;line-height: 0.24rem;display: flex;justify-content: space-between;">
           <span style="color:#b44141;font-size:0.12rem;">
          ${awardStr[0]}
          ${awardStr[1]}
          ${awardStr[2]}
          ${awardStr[3]}
          ${awardStr[4]}
           </span>
           <span style="color:white;font-size:0.12rem;">${openTime}</span>
         </div>`)
         $("#awardContainer>div:last").remove();
         awardCount=0;
         awardStr="";
         getHisAwardNew();
          }
        }
        else if(awardCount>0&&awardCount<5&&isStart==false){
          awrding=true;
          getHisAwardThree();
          awardCount=0;
          $("#myHisAwardTab").html("");
          getHisAwardTen();
        }
        $("#hashPlay>div:nth-of-type(1)").animate({marginTop: "0.24rem"});
}
getHisAwardThree();


let count = 0;
let endAwad = false; //判断开奖号码是否出完
let timeEnter, timeStart;

//wensocket推送信息到前端
ws.onmessage = function (event) {
  if(event.data!='pong'){
      var obj=eval("("+event.data+")");
  }
  let data = JSON.parse(event.data);
  let dataObj;
  if(data.text){
    dataObj = JSON.parse(data.text);
  }
  //将等到的信息传入前端显示
  if (data.from == "block") {
    theLotteryList(dataObj);
  }
  else if (data.from == "bet") {
    userBlance = dataObj.limit;
    let betLiveTime = datetimeFormat(dataObj.date.time).split(".");
    if(dataObj.num==parseInt($("#periods").text().replace(`${period1}`,"").replace(`${period2}`,""))){
    window.sessionStorage.setItem("userBlance",userBlance);
    if(loginType=="3"){
      getGosBalance();
    }else{
      getBlance();
    }
    if(userBlance>=10000){
      $("#betsUsers").text(`${parseFloat(userBlance/1000).toFixed(1)}K`);
    }else{
      $("#betsUsers").text(`${parseInt(userBlance*10)/10}`);
    }
    $("#betsUsers").css({width: `${(userBlance / balance)*betsAllWidth}px`});
  }
    $("#betLiveContainer").prepend(`
      <div style="height:0.24rem;line-height: 0.24rem;display: flex;justify-content: space-between;">
      <span style="color:#6a727d;font-size:0.12rem;">${dataObj.userName}</span>
      <span style="color:purple;font-size:0.12rem;">${parseFloat(dataObj.count).toFixed(4)}</span>
      <span style="color:#6a727d;font-size:0.12rem;">${betLiveTime[0]}</span>
   </div>`
    );
  }
  //用户已中奖中奖
  else if (data.from == "prizeMsg") {
    let prizeTime = datetimeFormat(dataObj.time.time).split(".");
    if (data.fromName == $("#userName").text()) {
      popup({type:'success',msg:`+${parseFloat(dataObj.pri).toFixed(4)}`,delay:1000,callBack:function(){
        getBlance();
        getHisBetsPage=1;
        pageNumMybets = 1;
        checkIdentity();
        getHisBets();
       }});
    }
    $("#betLiveContainer").prepend(`
    <div style="height:0.24rem;line-height: 0.24rem;display: flex;justify-content: space-between;">
        <span style="color:#6a727d;font-size:0.12rem;">${data.fromName}</span>
        <span style="color:green;font-size:0.12rem;">+${parseFloat(dataObj.pri).toFixed(4)}</span>
        <span style="color:#6a727d;font-size:0.12rem;">${prizeTime[0]}</span>
    </div>`
  );
  }
  //下级投注刷新账户
  else if(data.form=="invitation"){
    checkIdentity();
  }
  if(data.code==1100){
    popup({type:'success',msg:"注册成功,请完成支付",delay:1000,callBack:function(){
      checkIdentity();
    }});
  }else if(data.code==10){
      if(data.data.err==1){
        popup({type:'error',msg:"密码输入有误",delay:1000,bg:true,clickDomCancel:true});
      }else if(data.data.err==10){
        popup({type:'error',msg:"账户名输入有误",delay:1000,bg:true,clickDomCancel:true});
      }
  }
}




//心跳检测
//心跳检测
setInterval(function(){
  ws.send("ping");
}, 10000)



$("body").on("click",".totalNumBtn",function(){
  let totalNum=$(this).text();
  $("#totalBetsNum").css("display","block");
  $("#totalBetsNum").text(totalNum);
  setTimeout(()=>{
    $("#totalBetsNum").css("display","none");
  },1000)
})

//开奖结果检查
$("body").on("click",".recordsId",function(){
  $("#resultContain").html("");
  let recordsId=$(this).text();
  $("#awardBack").css("display","block");
  $("#resultBlockNum").text(`${recordsId}`);
  $.ajax({
    type:"get",
    url:`${baseUrl}/lottery/getBlockNum?betNum=${recordsId}`,
    success:function(data){
      $("#resultId").text(`${data.betNum}`);
      let firstNum=data.fristNum;
      let lastNum=data.lastkNum;
      firstBlock_id=getBlock_id(firstNum);
      lastBlock_id=getBlock_id(lastNum);
      let a;
      a=setInterval(()=>{
        getAwadDtails(lastBlock_id)
        lastBlock_id++;
        // console.log(firstBlock_id>lastBlock_id)
        if(firstBlock_id<lastBlock_id){
          clearInterval(a)
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
       block_id= msg.block_num
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
      // console.log(msg)
       let reg=/^[0-9]*$/
       block_id= msg.block_num
       let hashStr="";
       let lastHash=""
       lastHash=msg.id[msg.id.length-1];
      //  console.log(lastHash,reg.test(lastHash))
       if(reg.test(lastHash)==true){
       hashStr=`<span>...${msg.id.slice(45,msg.id.length-1)}</span><span style="color:#ff5c4a">${lastHash}</span>`
       }else{
        hashStr=`...${msg.id.slice(45)}`
       }
       $("#resultContain").prepend(
         `<div style="display:flex;justify-content:space-between;margin-top:0.05rem;font-size:0.12rem;">
            <span>${msg.block_num}</span>
            <span style="font-family: consolas,monaco,monospace;font-size:0.12rem;">${hashStr}</span>
            <span>${datetimeFormat(Date.parse(msg.timestamp.replace("-", "/").replace("T", "z")))}</span>
          </div>`
     )
    }
  })
}

$("#resultClose").click(function(){
  $("#awardBack").css("display","none");
})

