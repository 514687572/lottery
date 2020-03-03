// 导航的跳转
$("#more").click(function() {
    $("#navContainer").css("display", "block");
    $(this).css("display", "none");
    $("#close").css("display", "block");
  });
$("#close").click(function() {
$("#navContainer").css("display", "none");
$(this).css("display", "none");
$("#more").css("display", "block");
});

// 点击导航图标回到主页
$("#navLeft").click(function(){
    // $("#App").css("display","block");
    // $("#gameRuler").css("display","none");
    // $("#inviteFriends").css("display","none");
    // $("#more").css("display", "block");
    // $("#close").css("display", "none");
    window.location.href=`${baseUrl}/wap`;
})

// 游戏规则跳转
$("#TogameRuler").click(function(){
    $("#App").css("display","none");
    $("#gameRuler").css("display","block");
    $("#inviteFriends").css("display","none");
    $("#navContainer").css("display","none");
    $("#more").css("display", "block");
    $("#close").css("display", "none");
})

// 邀请好友跳转
$("#ToinviteFriends").click(function(){
    $("#App").css("display","none");
    $("#gameRuler").css("display","none");
    $("#inviteFriends").css("display","block");
    $("#navContainer").css("display","none");
    $("#more").css("display", "block");
    $("#close").css("display", "none");
    if ($("#userName").text() != ""){
      // 邀请好友接口
      $.ajax({
        type: "post",
        url: `${baseUrl}/user/getUser.do`,
        data: {
          userName: $("#userName").text()
        },
        success: function (msg) {
          $("#linkInput").val(
            `${baseUrl}/wap?userCode=${msg.user.userCode}`
          );
        }
      });
      myFriends(pageNumFriends,perPageFriends);
    }
})


//点击下一页请求我的下级
$("#nextPage").click(function(){
  pageNumFriends+=1;
  if(pageNumFriends<=friendsPage){
    perPageFriends+=5;
    myFriends(pageNumFriends,perPageFriends)
  }
})
//点击上一页请求我的下级
$("#prePage").click(function(){
  pageNumFriends-=1;
  if(pageNumFriends>=1){
    perPageFriends-=5;
    myFriends(pageNumFriends,perPageFriends);
  }
})

function myFriends(pageNumFriends,perPageFriends){
  //我的下级接口
  $.ajax({
    type: "get",
    url: `${baseUrl}/user/getMyChilds.do?pageNum=${pageNumFriends}&records=${perPageFriends}&userName=${$(
      "#userName"
    ).text()}`,
    success: function (msg) {
      let friendsStr = "";
      if(msg.childs.length==0){
        $("#pageTurn").css("display","none");
        $("#noFriends").css("display","block");
      }
      $("#curPage").text(`${pageNumFriends}/${msg.totalPage}`);
      for (let i = 0; i < msg.childs.length; i++) {
        friendsStr += `
          <div style="display: flex;justify-content: space-between;height:0.41rem;line-height: 0.42rem;border-bottom: 0.01rem dotted #4d4f56;">
              <div style="width:0.4rem;">${i+perPageFriends*(pageNumFriends-1)}</div>
              <div style="width:0.9rem;">${msg.childs[i].userName}</div>
              <div style="width:0.4rem;">1</div>
              <div style="width:1.0rem;color:#b44141;">${msg.childs[i].noteMoney}</div>
              <div style="width:1.0rem;color:#b44141;">${msg.childs[i].lotteryBonus}</div>
          </div>`
      }
      $("#myFriendsContainer").html(friendsStr);
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

//开奖记录和投注记录之间的切换
$("#hisBets").click(function(){
  if(loginType){
    if(loginType=="1"||loginType=="2"){
      $("#myHisBets").css("display","block");
      $("#myHisAward").css("display","none");
      $(this).css("color","white");
      $("#hisAward").css("color","#6a727d");
      getHisBetsPage=1;
      getHisBets();
    }
    else if(loginType=="3"){

    }
  }else{
    popup({type:'tip',msg:"请先登录",delay:1000,clickDomCancel:true});
  }
  })
$("#hisAward").click(function(){
    $("#myHisAward").css("display","block");
    $("#myHisBets").css("display","none");
    $(this).css("color","white");
    $("#hisBets").css("color","#6a727d");
    getHisAwardTen();
})

//点击切换基础模式
$(".baseBetsBtn").click(function () {
    // console.log(00)
    modelBollen = "0";
    $(".baseBetsBtn").css("background", "#ff564f");
    $("#baseModelBox").css("display", "block");
    $("#higherModelBox").css("display", "none");
    $(".higherBetsBtn").css("background", "#252a48");
    modelBollen="0";
});
  
//点击切换高阶模式
$(".higherBetsBtn").click(function () {
    // console.log(11)
    modelBollen = "1";
    $(".higherBetsBtn").css("background", "#ff564f");
    $("#baseModelBox").css("display", "none");
    $("#higherModelBox").css("display", "block");
    $(".baseBetsBtn").css("background", "#252a48");
    modelBollen="1";
});

//切换语言
$("#inputBox").click(function (event) {
    event.stopPropagation();
    $("#language").toggle();
    $("#language").css("z-index","10000");
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

//判断是英语还是中文，改变个别英语显示样式
// console.log(lang);
if (lang == "en_US") {
 $("#gameRulerPartTwo").css("height","1.3rem");
 $("#gameRulerPartThree").css("height","4.7rem");
 $("#gameRulerPartFour").css("height","4.8rem");
 $("#inviteFriendsPartTwo").css("height","2.8rem");
 $("#inviteFriendsPartThree").css("height","3.8rem");
 $("#invitePart2").css("height","4.0rem");
 $("#rulerWords").css("height","2.0rem");
 $(".myBets").css("width","0.8rem");
 $("#inputBox").html(
  `<img src="../images/guoqi02.png" alt="" id="guoqiBox">English`
 );
}


