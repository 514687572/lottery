// 基础模式玩法
let flag1 = false;
let flag2 = false;
let flag3 = false;
let flag4 = false;
let arr = [];
let str = "";
let betsparseFloat = 0; //投注数
let large = -1;
let single = -1;

//游戏星级选择
let starCheck1 = false;
let starCheck2 = false;
let starCheck3 = false;
let starCheck4 = false;
let starCheck5 = true;
let starRank = "5"; //高阶模式星级，默认五星

//大小选择
$("#small").click(function () {
  flag1 = !flag1;
  flag2 = false;
  if (flag1 == true) {
    $(this).css({
      "background-color": "#613522",
      border: "1px solid #ff5f47"
    });
    $("#big").css({
      "background-color": "#282e50",
      border: "none"
    });
    arr.push(englistSmall);
    for (let i = 0; i < arr.length; i++) {
      if (arr[i] == englistBig) {
        arr.splice(i, 1);
      }
    }
    large = 0;
  } else {
    large = -1;
    $(this).css({
      "background-color": "#282e50",
      border: "none"
    });
    for (let i = 0; i < arr.length; i++) {
      if (arr[i] == englistSmall) {
        arr.splice(i, 1);
      }
    }
  }
  if (arr.length == 0) {
    $("#sbsd").text("[ ]");
    baselssdStr="";
  } else {
    for (let item of arr) {
      str += item;
    }
    $("#sbsd").text(`[${str}]`);
    baselssdStr=str;
  }
  str = "";
  currentparseFloat();
  totalCalculation();
});
$("#big").click(function () {
  flag2 = !flag2;
  flag1 = false;
  if (flag2 == true) {
    $(this).css({
      "background-color": "#613522",
      border: "1px solid #ff5f47"
    });
    $("#small").css({
      "background-color": "#282e50",
      border: "none"
    });
    arr.push(englistBig);
    for (let i = 0; i < arr.length; i++) {
      if (arr[i] == englistSmall) {
        arr.splice(i, 1);
      }
    }
    large = 1;
  } else {
    large = -1;
    $(this).css({
      "background-color": "#282e50",
      border: "none"
    });
    for (let i = 0; i < arr.length; i++) {
      if (arr[i] == englistBig) {
        arr.splice(i, 1);
      }
    }
  }
  if (arr.length == 0) {
    $("#sbsd").text("[ ]");
    baselssdStr="";
  } else {
    for (let item of arr) {
      str += item;
    }
    $("#sbsd").text(`[${str}]`);
    baselssdStr=str;
  }
  str="";
  currentparseFloat();
  totalCalculation();
});

//单双选择
$("#single").click(function () {
  flag3 = !flag3;
  flag4 = false;
  if (flag3 == true) {
    $(this).css({
      "background-color": "#613522",
      border: "1px solid #ff5f47"
    });
    $("#double").css({
      "background-color": "#282e50",
      border: "none"
    });
    arr.push(englistOdd);
    for (let i = 0; i < arr.length; i++) {
      if (arr[i] == englistEven) {
        arr.splice(i, 1);
      }
    }
    single = 1;
  } else {
    single = -1;
    $(this).css({
      "background-color": "#282e50",
      border: "none"
    });
    for (let i = 0; i < arr.length; i++) {
      if (arr[i] == englistOdd) {
        arr.splice(i, 1);
      }
    }
  }
  
  if (arr.length == 0) {
    $("#sbsd").text("[ ]");
    baselssdStr="";
  } else {
    for (let item of arr) {
      str += item;
    }
    $("#sbsd").text(`[${str}]`);
    baselssdStr=str;
  }
  str = "";
  currentparseFloat();
  totalCalculation();
});
$("#double").click(function () {
  flag4 = !flag4;
  flag3 = false;
  if (flag4 == true) {
    $(this).css({
      "background-color": "#613522",
      border: "1px solid #ff5f47"
    });
    $("#single").css({
      "background-color": "#282e50",
      border: "none"
    });
    arr.push(englistEven);
    for (let i = 0; i < arr.length; i++) {
      if (arr[i] == englistOdd) {
        arr.splice(i, 1);
      }
    }
    single = 0;
  } else {
    single = -1;
    $(this).css({
      "background-color": "#282e50",
      border: "none"
    });
    for (let i = 0; i < arr.length; i++) {
      if (arr[i] == englistEven) {
        arr.splice(i, 1);
      }
    }
  }
  if (arr.length == 0) {
    $("#sbsd").text("[ ]");
    baselssdStr="";
  } else {
    for (let item of arr) {
      str += item;
    }
    $("#sbsd").text(`[${str}]`);
    baselssdStr=str;
  }
  str = "";
  currentparseFloat();
  totalCalculation();
});


// 单双重置功能
$("#resetOne").click(function () {
    large=-1;
    single=-1;
    arr.length = 0;
    if (arr.length == 0) {
      $("#sbsd").text("[ ]");
      baselssdStr="";
    } else {
      for (let item of arr) {
        str += item;
      }
      $("#sbsd").text(`[${str}]`);
      baselssdStr=str;
    }
    str = "";
    cssReset("#small");
    cssReset("#big");
    cssReset("#single");
    cssReset("#double");
    flag1 = false;
    flag2 = false;
    flag3 = false;
    flag4 = false;
    currentparseFloat();
    totalCalculation();
});


// 点击一星模式
$("#oneStar").click(function () {
  starRank = "1";
  starCheck1 = true;
  starCheck2 = false;
  starCheck3 = false;
  starCheck4 = false;
  starCheck5 = false;
  judgeStarCheck();
  $("#num2Box").css("display", "none");
  $("#num3Box").css("display", "none");
  $("#num4Box").css("display", "none");
  $("#num5Box").css("display", "none");
  $("#h2box").css("display", "none");
  $("#h3box").css("display", "none");
  $("#h4box").css("display", "none");
  $("#h5box").css("display", "none");
  currentparseFloat();
  totalCalculation();
});

// 点击二星模式
$("#twoStar").click(function () {
  starRank = "2";
  starCheck1 = false;
  starCheck2 = true;
  starCheck3 = false;
  starCheck4 = false;
  starCheck5 = false;
  judgeStarCheck();
  $("#num2Box").css("display", "block");
  $("#num3Box").css("display", "none");
  $("#num4Box").css("display", "none");
  $("#num5Box").css("display", "none");
  $("#h2box").css("display", "block");
  $("#h3box").css("display", "none");
  $("#h4box").css("display", "none");
  $("#h5box").css("display", "none");
  currentparseFloat();
  totalCalculation();
});

// 点击三星模式
$("#threeStar").click(function () {
  starRank = "3";
  starCheck1 = false;
  starCheck2 = false;
  starCheck3 = true;
  starCheck4 = false;
  starCheck5 = false;
  judgeStarCheck();
  $("#num2Box").css("display", "block");
  $("#num3Box").css("display", "block");
  $("#num4Box").css("display", "none");
  $("#num5Box").css("display", "none");
  $("#h2box").css("display", "block");
  $("#h3box").css("display", "block");
  $("#h4box").css("display", "none");
  $("#h5box").css("display", "none");
  currentparseFloat();
  totalCalculation();
});

// 点击四星模式
$("#fourStar").click(function () {
  starRank = "4";
  starCheck1 = false;
  starCheck2 = false;
  starCheck3 = false;
  starCheck4 = true;
  starCheck5 = false;
  judgeStarCheck();
  $("#num2Box").css("display", "block");
  $("#num3Box").css("display", "block");
  $("#num4Box").css("display", "block");
  $("#num5Box").css("display", "none");
  $("#h2box").css("display", "block");
  $("#h3box").css("display", "block");
  $("#h4box").css("display", "block");
  $("#h5box").css("display", "none");
  currentparseFloat();
  totalCalculation();
});

// 点击五星模式
$("#fiveStar").click(function () {
  starRank = "5";
  starCheck1 = false;
  starCheck2 = false;
  starCheck3 = false;
  starCheck4 = false;
  starCheck5 = true;
  judgeStarCheck();
  $("#num2Box").css("display", "block");
  $("#num3Box").css("display", "block");
  $("#num4Box").css("display", "block");
  $("#num5Box").css("display", "block");
  $("#h2box").css("display", "block");
  $("#h3box").css("display", "block");
  $("#h4box").css("display", "block");
  $("#h5box").css("display", "block");
  currentparseFloat();
  totalCalculation();
});

// $(".numberBtn").click(function(){
//   $(this).css({
//     "background-color": "#613522",
//     "border": "1px solid #ff5f47",
//     "color": "white"
//   })
//   str+=$(this).text()
//   $("#numbersBox").text(`[${str}]`)
// })
// 数字选择函数
function numberChoose(nodename, resetBtnName,allBtnName) {
    let numberAry = [];
    let numberStr = "";
    let numberBtn = document.getElementsByClassName(nodename);
    for (let item of numberBtn) {
      item.onclick = function () {
        numberStr="";
        if (this.dataset.check == "off") {
          this.dataset.check = "on";
          $(this).css({
            "background-color": "#613522",
            "border": "1px solid #ff5f47",
            "color": "white"
          });
          numberAry.push(item.innerText);
          // console.log(numberAry)
          numberAry.sort((a, b) => a - b);
        } else {
          this.dataset.check = "off";
          $(this).css({
            "background-color": "#282e50",
            border: "none",
            color: "white"
          });
          for (let i = 0; i < numberAry.length; i++) {
            if (numberAry[i] == item.innerText) {
              numberAry.splice(i, 1);
            }
          }
          // console.log(numberAry)
        }
        if (numberAry.length == 0) {
          switch (nodename){
            case "numberBtn":
            baseNumStr="";
            $("#numbersBox").text(`[ ]`);
            break;
            case "number1Btn":
            higherBetsStrOne="";
            $("#h1box").text(`[ ]`)
            break;
            case "number2Btn":
            higherBetsStrTwo="";
            $("#h2box").text(`[ ]`)
            break;
            case "number3Btn":
            higherBetsStrThree="";
            $("#h3box").text(`[ ]`)
            break;
            case "number4Btn":
            higherBetsStrFour="";
            $("#h4box").text(`[ ]`)
            break;
            case "number5Btn":
            higherBetsStrFive="";
            $("#h5box").text(`[ ]`)
            break;
          }
        } else {
          for (let j = 0; j < numberAry.length; j++) {
            if (j == numberAry.length - 1) {
              numberStr += `${numberAry[j]}`;
            } else {
              numberStr += `${numberAry[j]},`;
            }
          }
          switch (nodename){
            case "numberBtn":
            baseNumStr=numberStr;
            $("#numbersBox").text(`[${numberStr}]`);
            break;
            case "number1Btn":
            higherBetsStrOne=numberStr;
            $("#h1box").text(`[${numberStr}]`)
            break;
            case "number2Btn":
            higherBetsStrTwo=numberStr;
            $("#h2box").text(`[${numberStr}]`)
            break;
            case "number3Btn":
            higherBetsStrThree=numberStr;
            $("#h3box").text(`[${numberStr}]`)
            break;
            case "number4Btn":
            higherBetsStrFour=numberStr;
            $("#h4box").text(`[${numberStr}]`)
            break;
            case "number5Btn":
            higherBetsStrFive=numberStr;
            $("#h5box").text(`[${numberStr}]`)
            break;
          }
        }
        numberStr = "";
        currentparseFloat();
        totalCalculation();
      };
    }
    // 数字选项重置
    $(resetBtnName).click(function () {
      for (let item of numberBtn) {
        item.dataset.check = "off"; //改变自定义属性值
      }
      $(`.${nodename}`).css({
        "background-color": "#282e50",
        border: "none",
        color: "white"
      }); //还原样式
      numberAry.length = 0;
      numberStr = "";
      switch (resetBtnName){
        case "#resetTwo":
        baseNumStr="";
        $("#numbersBox").text(`[ ]`);
        break;
        case "#resetTr":
        higherBetsStrOne="";
        $("#h1box").text(`[ ]`)
        break;
        case "#resetFr":
        higherBetsStrTwo="";
        $("#h2box").text(`[ ]`)
        break;
        case "#resetFv":
        higherBetsStrThree="";
        $("#h3box").text(`[ ]`)
        break;
        case "#resetSx":
        higherBetsStrFour="";
        $("#h4box").text(`[ ]`)
        break;
        case "#resetSv":
        higherBetsStrFive="";
        $("#h5box").text(`[ ]`)
        break;
      }
      currentparseFloat();
      totalCalculation();
    });
    $(allBtnName).click(function () {
      numberAry.length=0;
      numberStr="";
        let arrNew="0,1,2,3,4,5,6,7,8,9".split(",");
        for(let item of arrNew){
          numberAry.push (item);
        }
        // console.log(numberAry)
      for (let item of numberBtn) {
        item.dataset.check = "on"; //改变自定义属性值
      }
      $(`.${nodename}`).css({
        "background-color": "#613522",
        "border": "1px solid #ff5f47",
        "color": "white"
      }); //改变样式
      for (let k = 0; k < numberAry.length; k++) {
        if (k == numberAry.length - 1) {
          numberStr += `${numberAry[k]}`;
        } else {
          numberStr += `${numberAry[k]},`;
        }
      }
      switch (allBtnName){
        case "#allOne":
        baseNumStr=numberStr;
        $("#numbersBox").text(`[${numberStr}]`);
        break;
        case "#allTwo":
        higherBetsStrOne=numberStr;
        $("#h1box").text(`[${numberStr}]`)
        break;
        case "#allThree":
        higherBetsStrTwo=numberStr;
        $("#h2box").text(`[${numberStr}]`)
        break;
        case "#allFour":
        higherBetsStrThree=numberStr;
        $("#h3box").text(`[${numberStr}]`)
        break;
        case "#allFive":
        higherBetsStrFour=numberStr;
        $("#h4box").text(`[${numberStr}]`)
        break;
        case "#allSix":
        higherBetsStrFive=numberStr;
        $("#h5box").text(`[${numberStr}]`)
        break;
      }
      currentparseFloat();
      totalCalculation();
    });
}


function resetBtnStyle(nodename){
  $(nodename).click(function(){
    $(this).css("background","#ff8325");
    setTimeout(()=>{
      $(this).css("background","#282e50");
    },500)
  })
};

resetBtnStyle("#resetOne");
resetBtnStyle("#resetTwo");
resetBtnStyle("#resetTr");
resetBtnStyle("#resetTr");
resetBtnStyle("#resetFr");
resetBtnStyle("#resetFv");
resetBtnStyle("#resetSx");
resetBtnStyle("#resetSv");


// 选择数字函数调用
numberChoose("numberBtn","#resetTwo","#allOne");
numberChoose("number1Btn", "#resetTr","#allTwo");
numberChoose("number2Btn", "#resetFr","#allThree");
numberChoose("number3Btn", "#resetFv","#allFour");
numberChoose("number4Btn", "#resetSx","#allFive");
numberChoose("number5Btn", "#resetSv","#allSix");

//星级切换清除被隐藏的样式
function cssReset(nodename) {
    $(nodename).css({
      "background-color": "#282e50",
      border: "none"
    });
}


// 选择投注方式：EOS/虚拟货币
$(".chooseUnit").click(function(event){
  event.stopPropagation();
  $(".unitList").toggle();
  $(".Eos").click(function(){
    isEos=true;
    $(".moneyUnit").text("EOS")
  })
  $(".Gos").click(function(){
    isEos=false;
    $(".moneyUnit").text("GOS")
  })
})
$(document).click(function(){
  $(".unitList").css("display","none");
})

//一半，两倍，最大选择改变输入框的金额
$(".half").click(function () {
  $(this).css("background","linear-gradient(to right, #ff5c4a , #ff8325)")
  $(".doubles").css("background","none");
  $(".max").css("background","none");
  $(".myBets").val(parseFloat($(".myBets").val() / 2).toFixed(1));
  if( $(".myBets").val()<0.1){
    $(".myBets").val("1");
  }
  totalCalculation();
});
$(".doubles").click(function () {
  $(this).css("background","linear-gradient(to right, #ff5c4a , #ff8325)")
  $(".half").css("background","none");
  $(".max").css("background","none");
  $(".myBets").val(parseFloat($(".myBets").val() * 2).toFixed(1));
  if( $(".myBets").val()>100){
    $(".myBets").val("1");
  }
  totalCalculation();
});
$(".max").click(function () {
  $(this).css("background","linear-gradient(to right, #ff5c4a , #ff8325)")
  $(".doubles").css("background","none");
  $(".half").css("background","none");
  $(".myBets").val(100);
  totalCalculation();
});

//改变两个输入框的值
let reg = new RegExp("^[0-9]+(.[0-9]{0,1})?$");
$(".myBets:first").change(function () {
  if (reg.test($(this).val()) == false||$(this).val()>100) {
    $(this).val("1");
    $(".myBets:first").val($(".myBets:last").val());
  }else{
    $(".myBets:last").val($(".myBets:first").val());
  }
  totalCalculation();
});
$(".myBets:last").change(function () {
  if (reg.test($(this).val()) == false||$(this).val()>100) {
    $(this).val("1");
    $(".myBets:first").val($(".myBets:last").val());
  }else{
    $(".myBets:first").val($(".myBets:last").val());
  }
  totalCalculation();
});


//当前注数计算函数
function currentparseFloat() {
    let baseBet1 = arr.length;
    let baseBet2 = baseNumStr.replace(/,/g, "").length;
    let h1Num = higherBetsStrOne.replace(/,/g, "").length;
    let h2Num = higherBetsStrTwo.replace(/,/g, "").length;
    let h3Num = higherBetsStrThree.replace(/,/g, "").length;
    let h4Num = higherBetsStrFour.replace(/,/g, "").length;
    let h5Num = higherBetsStrFive.replace(/,/g, "").length;
    
    //基础模式计算注数
    if (modelBollen == "0") {
      $("#baseBets").text(`${baseBet1 + baseBet2}`);
    }
  
    //选择高阶模式的注数计算
    if (modelBollen == "1") {
      if (
        starCheck1 == true &&
        (starCheck2 = starCheck3 = starCheck4 = starCheck5) == false
      ) {
        $("#higherBets").text(`${h1Num}`);
      }
      if (
        starCheck2 == true &&
        (starCheck1 = starCheck3 = starCheck4 = starCheck5) == false
      ) {
        $("#higherBets").text(`${h1Num * h2Num}`);
      }
      if (
        starCheck3 == true &&
        (starCheck1 = starCheck2 = starCheck4 = starCheck5) == false
      ) {
        $("#higherBets").text(`${h1Num * h2Num * h3Num}`);
      }
      if (
        starCheck4 == true &&
        (starCheck1 = starCheck2 = starCheck3 = starCheck5) == false
      ) {
        $("#higherBets").text(`${h1Num * h2Num * h3Num * h4Num}`);
      }
      if (
        starCheck5 == true &&
        (starCheck1 = starCheck2 = starCheck3 = starCheck4) == false
      ) {
        $("#higherBets").text(`${h1Num * h2Num * h3Num * h4Num * h5Num}`);
      }
    }
}

  
//计算总投注数函数
function totalCalculation() {
    $("#baseTotalBets").text(
        parseFloat($(".myBets").val() * parseFloat($("#baseBets").text())).toFixed(1)
    );
    $("#higherTotalBets").text(
        parseFloat(
        $(".myBets").val() * parseFloat($("#higherBets").text())).toFixed(1)
    );
        baseBetsNum = $("#baseTotalBets").text() == "0" ?0 :parseFloat($("#baseTotalBets").text());
        higherBetsNum =$("#higherTotalBets").text() == "0" ? 0 :parseFloat($("#higherTotalBets").text());
};


// 改变选中按钮及未选中按钮样式函数
function judgeStarCheck() {
    if (
      starCheck1 == true &&
      (starCheck2 = starCheck3 = starCheck4 = starCheck5) == false
    ) {
      $("#oneStar").css({
        background: "linear-gradient(to right, #ff5c4a , #ff8325)",
        border: "1px solid #ff5f47",
        color: "white"
      });
      $("#twoStar").css({
        background: "#323761",
        border: "none"
      });
      $("#threeStar").css({
        background: "#323761",
        border: "none"
      });
      $("#fourStar").css({
        background: "#323761",
        border: "none"
      });
      $("#fiveStar").css({
        background: "#323761",
        border: "none"
      });
    }
    if (
      starCheck2 == true &&
      (starCheck1 = starCheck3 = starCheck4 = starCheck5) == false
    ) {
      $("#twoStar").css({
        background: "linear-gradient(to right, #ff5c4a , #ff8325)",
        border: "1px solid #ff5f47",
        color: "white"
      });
      $("#oneStar").css({
        background: "#323761",
        border: "none"
      });
      $("#threeStar").css({
        background: "#323761",
        border: "none"
      });
      $("#fourStar").css({
        background: "#323761",
        border: "none"
      });
      $("#fiveStar").css({
        background: "#323761",
        border: "none"
      });
    }
    if (
      starCheck3 == true &&
      (starCheck1 = starCheck2 = starCheck4 = starCheck5) == false
    ) {
      $("#threeStar").css({
        background: "linear-gradient(to right, #ff5c4a , #ff8325)",
        border: "1px solid #ff5f47",
        color: "white"
      });
      $("#oneStar").css({
        background: "#323761",
        border: "none"
      });
      $("#twoStar").css({
        background: "#323761",
        border: "none"
      });
      $("#fourStar").css({
        background: "#323761",
        border: "none"
      });
      $("#fiveStar").css({
        background: "#323761",
        border: "none"
      });
    }
    if (
      starCheck4 == true &&
      (starCheck1 = starCheck2 = starCheck3 = starCheck5) == false
    ) {
      $("#fourStar").css({
        background: "linear-gradient(to right, #ff5c4a , #ff8325)",
        border: "1px solid #ff5f47",
        color: "white"
      });
      $("#oneStar").css({
        background: "#323761",
        border: "none"
      });
      $("#twoStar").css({
        background: "#323761",
        border: "none"
      });
      $("#threeStar").css({
        background: "#323761",
        border: "none"
      });
      $("#fiveStar").css({
        background: "#323761",
        border: "none"
      });
    }
    if (
      starCheck5 == true &&
      (starCheck1 = starCheck2 = starCheck3 = starCheck4) == false
    ) {
      $("#fiveStar").css({
        background: "linear-gradient(to right, #ff5c4a , #ff8325)",
        border: "1px solid #ff5f47",
        color: "white"
      });
      $("#oneStar").css({
        background: "#323761",
        border: "none"
      });
      $("#twoStar").css({
        background: "#323761",
        border: "none"
      });
      $("#threeStar").css({
        background: "#323761",
        border: "none"
      });
      $("#fourStar").css({
        background: "#323761",
        border: "none"
      });
    }
};

// 下注与后台对接
$(".betsBtn").click(function () {
    let phone=window.sessionStorage.getItem("phone");
    let btnObj = $(this); //获取当前按钮对象
    let userNamePost=""; //用户名
    let userKeyPost="";  //用户公钥
    let userEos=parseFloat($("#EosMoney").text());//用户EOS余额
    let userGos=parseFloat($("#integral").text());//用户GOS余额
    let userIputEos=parseFloat($(".myBets").val());//用户输入的每注投注金额
    let userCpu=(parseFloat($("#cpu").text().replace("%","")));
    userLimitBets = totalLimitBets - userBlance;//投注限额(总投注限额减去已经投注的金额)
    const sid = `${new Date().getTime()}${Math.random()}${generateMixed(6)}`;
    if(loginType=="1"){
      userNamePost=scatter.identity.accounts[0].name;
      userKeyPost=scatter.identity.publicKey;
    }
    else if(loginType=="2"){
      userNamePost=userName;
      userKeyPost=publicKey;
    }
    else{
      userNamePost=phone;
    }
    const remark = {
      gameType: "lottery",
      id: sid,
      userName: userNamePost
    };
    $.ajax({
      type: "get",
      url: `${baseUrl}/lottery/checkUserStatus`,
      data: {
        userName: userNamePost,  //用户名
        loginType:loginType  //判断登录的模式，空为EOS用户，有值代表积分用户;
      },
      success:function(msg){
        //账户状态正常则继续下注
        if(msg.status==true){
          switch (loginType){
            //scatter登录
            case "1":
            if(userCpu<100){
              $.ajax({
                type:"POST",
                url:`${baseUrl}/oauth/token`,
                data:{
                  userName: userNamePost,
                  appKey: "90dc11a7ce5f8c6f7c337b8f9eaac12397ezde",
                  userKey: userKeyPost
                },
                success:function(msg){
                  //基础模式
                  if(modelBollen=="0"){
                    if (baseBetsNum != 0&&baseBetsNum<=userLimitBets&&baseBetsNum<userEos&&userIputEos>= 0.1&&userIputEos<=100){
                      scatterBaseBets(sid,msg.token.access_token,remark,btnObj);
                    }else{
                      btnObj.attr("disabled", false);
                      btnObj.css("background","linear-gradient(to right, #ff5c4a , #ff8325)");
                      popup({type:'tip',msg:ucerror,delay:1000,clickDomCancel:true});
                      $("#stop").css({
                        display: "none"
                      });
                    }
                  }
                  else{
                    if(higherBetsNum!= 0&&higherBetsNum<=userLimitBets&&higherBetsNum<userEos&&userIputEos>= 0.1&&userIputEos<=100){
                      scatterHigherBets(sid,msg.token.access_token,remark,btnObj,starRank);
                    }else{
                      btnObj.attr("disabled", false);
                      btnObj.css("background","linear-gradient(to right, #ff5c4a , #ff8325)");
                      popup({type:'error',msg:ucerror,delay:1000,bg:true,clickDomCancel:true});
                      $("#stop").css({
                        display: "none"
                      });
                    }
                  }
                }
              });
            }else{
              popup({type:'tip',msg:cpuerror,delay:1000,clickDomCancel:true});
            };
            break;
            //私钥登录
            case "2":
            if(userCpu<100){
              $.ajax({
                type:"POST",
                url:`${baseUrl}/oauth/token`,
                data:{
                  userName: userNamePost,
                  appKey: "90dc11a7ce5f8c6f7c337b8f9eaac12397ezde",
                  userKey: userKeyPost
                },
                success:function(msg){
                  //基础模式
                  if(modelBollen=="0"){
                    if (baseBetsNum != 0&&baseBetsNum<=userLimitBets&&baseBetsNum<userEos&&userIputEos>= 0.1&&userIputEos<=100){
                      privateBaseBets(sid,msg.token.access_token,btnObj);
                    }else{
                      btnObj.attr("disabled", false);
                      btnObj.css("background","linear-gradient(to right, #ff5c4a , #ff8325)");
                      popup({type:'tip',msg:ucerror,delay:1000,clickDomCancel:true});
                      $("#stop").css({display: "none"});
                    }
                  }
                  else{
                    if(higherBetsNum!= 0&&higherBetsNum<=userLimitBets&&higherBetsNum<userEos&&userIputEos>= 0.1&&userIputEos<=100){
                      privateHigherBets(sid,msg.token.access_token,btnObj,starRank);
                    }else{
                      btnObj.attr("disabled", false);
                      btnObj.css("background","linear-gradient(to right, #ff5c4a , #ff8325)");
                      popup({type:'error',msg:ucerror,delay:1000,bg:true,clickDomCancel:true});
                      $("#stop").css({display: "none"});
                    }
                  }
                }
              });
            }else{
              popup({type:'tip',msg:cpuerror,delay:1000,clickDomCancel:true});
            };
            break;
            case "3":
            if(modelBollen=="0"){
              phoneBaseBets(btnObj);
              // if (baseBetsNum != 0&&baseBetsNum<=userLimitBets&&baseBetsNum<userGos&&userIputEos>= 0.1&&userIputEos<=100){
              //   phoneBaseBets(btnObj);
              // }
            }else{
              phoneHigherBets(btnObj);
              // if(higherBetsNum!= 0&&higherBetsNum<=userLimitBets&&higherBetsNum<userGos&&userIputEos>= 0.1&&userIputEos<=100){
              //   phoneHigherBets(btnObj);
              // }
            }
          }
        }else{
          btnObj.attr("disabled", false);
          btnObj.css("background","linear-gradient(to right, #ff5c4a , #ff8325)");
          popup({type:'tip',msg:msg.msg,delay:1000,clickDomCancel:true});
        }
      }
    })
});
  
  
//scatter登录的基础模式下注
function scatterBaseBets(sid,access_token,remark,btnObj){
    const eosOptions = {
    expireInSeconds: 60
    };
    const eos = scatter.eos(network, Eos, eosOptions);
    $.ajax({
    type: "post",
    url: `${baseUrl}/lottery/goBet.do`,
    data: {
        userName: scatter.identity.accounts[0].name, //用户账户名
        model: "0", //模式类型
        large: large, //大小
        single: single, //单双
        baseNum: $("#numbersBox")
        .text()
        .replace("[", "")
        .replace("]", ""),
        perBets: $(".myBets").val(),
        starRank: "",
        firstNums: "",
        secondNums: "",
        thirdNums: "",
        forthNums: "",
        fifthNums: "",
        higherTotalBets: "",
        sid: sid,
        access_token: access_token,
        userCode: userCode ? userCode : ""
    },
    success: function (msg) {
        $("#stop").css({display: "block"});
        eos.transfer(
            scatter.identity.accounts[0].name,
            "mylotterybet",
            `${parseFloat(baseBetsNum).toFixed(4)} EOS`,
            JSON.stringify(remark)
        )
        .then(result => {
            $("#stop").css({display: "none"});
            if (msg.status == "true") {
            popup({type:'success',msg:msg.msg,delay:1000,callBack:function(){
              getBlance();
              pageNumMybets = 1;
              getHisBetsPage=1
              checkIdentity();
              getHisBets();
            }
            });
            } else {
            popup({type:'tip',msg:msg.msg,delay:1000,clickDomCancel:true});
            }
            
            btnObj.attr("disabled", false);
            btnObj.css(
            "background",
            "linear-gradient(to right, #ff5c4a , #ff8325)"
            );
        })
        .catch(e => {
            $("#stop").css({
            display: "none"
            });
            btnObj.attr("disabled", false);
            btnObj.css(
            "background",
            "linear-gradient(to right, #ff5c4a , #ff8325)"
            );
            popup({type:'tip',msg:e.message,delay:1000,clickDomCancel:true});
        });
    },
    error: function () {
        btnObj.attr("disabled", false);
        btnObj.css(
        "background",
        "linear-gradient(to right, #ff5c4a , #ff8325)"
        );
    }
    });
};

//私钥登录的基础模式下注
function privateBaseBets(sid,access_token,btnObj){
    $.ajax({
        type: "post",
        url: `${baseUrl}/lottery/goBet.do`,
        data: {
        userName: userName, //用户账户名
        model: "0", //模式类型
        large: large, //大小
        single: single, //单双
        baseNum: baseNumStr,
        perBets: $(".myBets").val(),
        starRank: "",
        firstNums: "",
        secondNums: "",
        thirdNums: "",
        forthNums: "",
        fifthNums: "",
        higherTotalBets: "",
        sid: sid,
        access_token:access_token,
        userCode: userCode ? userCode : "",
        isPrivate: "1"
        },
        success: function (msg) {
        $("#stop").css({
            display: "none"
        });
         if(msg.status=="true"){
          popup({type:'success',msg:success,delay:1000,callBack:function(){
            getBlance();
            pageNumMybets = 1;
            getHisBetsPage=1
            checkIdentity();
            getHisBets();
             }
          });
        }else{
          popup({type:'error',msg:msg.msg,delay:1000,bg:true,clickDomCancel:true});
        }
        btnObj.attr("disabled", false);
        btnObj.css(
            "background",
            "linear-gradient(to right, #ff5c4a , #ff8325)"
        );
        },
        error: function () {
        btnObj.attr("disabled", false);
        btnObj.css(
            "background",
            "linear-gradient(to right, #ff5c4a , #ff8325)"
        );
        }
    });
};

  //私钥登录的高阶模式下注
function privateHigherBets(sid,access_token,btnObj){
    let betNumOne = "";
    let betNumTwo = "";
    let betNumThree = "";
    let betNumFour = "";
    let betNumFive = "";
    let higherNumOne = higherBetsStrOne;
    let higherNumTwo = higherBetsStrTwo;
    let higherNumThree =higherBetsStrThree;
    let higherNumFour =higherBetsStrFour;
    let higherNumFive =higherBetsStrFive;
    switch (starRank) {
        case "1":
            betNumOne = higherNumOne;
            break;
        case "2":
            betNumOne = higherNumOne;
            betNumTwo = higherNumTwo;
            break;
        case "3":
            betNumOne = higherNumOne;
            betNumTwo = higherNumTwo;
            betNumThree = higherNumThree;
            break;
        case "4":
            betNumOne = higherNumOne;
            betNumTwo = higherNumTwo;
            betNumThree = higherNumThree;
            betNumFour = higherNumFour;
            break;
        case "5":
            betNumOne = higherNumOne;
            betNumTwo = higherNumTwo;
            betNumThree = higherNumThree;
            betNumFour = higherNumFour;
            betNumFive = higherNumFive;
            break;
        }
    $.ajax({
        type: "post",
        url: `${baseUrl}/lottery/goBet.do`,
        data: {
        userName: userName, //用户账户名
        model: "1", //模式类型
        large: -1, //大小
        single: -1, //单双
        baseNum: "",
        perBets: $(".myBets").val(),
        starRank: starRank,
        firstNums: betNumOne,
        secondNums: betNumTwo,
        thirdNums: betNumThree,
        forthNums: betNumFour,
        fifthNums: betNumFive,
        higherTotalBets: $("#higherBets").text(),
        sid: sid,
        access_token:access_token,
        userCode: userCode ? userCode : "",
        isPrivate: "1"
        },
        success: function (msg) {
        $("#stop").css({
            display: "none"
        });
        if(msg.status=="true"){
          popup({type:'success',msg:success,delay:1000,callBack:function(){
            getBlance();
            pageNumMybets = 1;
            getHisBetsPage=1
            checkIdentity();
            getHisBets();
             }
          });
        }else{
          popup({type:'error',msg:msg.msg,delay:1000,bg:true,clickDomCancel:true});
        }
        btnObj.attr("disabled", false);
        btnObj.css(
            "background",
            "linear-gradient(to right, #ff5c4a , #ff8325)"
        );
        },
        error: function () {
        btnObj.attr("disabled", false);
        btnObj.css(
            "background",
            "linear-gradient(to right, #ff5c4a , #ff8325)"
        );
        }
    });
};

//scatter登录的高阶模式投注
function scatterHigherBets(sid,access_token,remark,btnObj,starRank){
    const eosOptions = {
        expireInSeconds: 60
    };
    const eos = scatter.eos(network, Eos, eosOptions);
    let betNumOne = "";
    let betNumTwo = "";
    let betNumThree = "";
    let betNumFour = "";
    let betNumFive = "";
    let higherNumOne = $("#h1box")
    .text()
    .replace("[", "")
    .replace("]", "");
    let higherNumTwo = $("#h2box")
        .text()
        .replace("[", "")
        .replace("]", "");
    let higherNumThree = $("#h3box")
        .text()
        .replace("[", "")
        .replace("]", "");
    let higherNumFour = $("#h4box")
        .text()
        .replace("[", "")
        .replace("]", "");
    let higherNumFive = $("#h5box")
        .text()
        .replace("[", "")
        .replace("]", "");
        switch (starRank) {
        case "1":
            betNumOne = higherNumOne;
            break;
        case "2":
            betNumOne = higherNumOne;
            betNumTwo = higherNumTwo;
            break;
        case "3":
            betNumOne = higherNumOne;
            betNumTwo = higherNumTwo;
            betNumThree = higherNumThree;
            break;
        case "4":
            betNumOne = higherNumOne;
            betNumTwo = higherNumTwo;
            betNumThree = higherNumThree;
            betNumFour = higherNumFour;
            break;
        case "5":
            betNumOne = higherNumOne;
            betNumTwo = higherNumTwo;
            betNumThree = higherNumThree;
            betNumFour = higherNumFour;
            betNumFive = higherNumFive;
            break;
        }
        $.ajax({
        type: "post",
        url: `${baseUrl}/lottery/goBet.do`,
        data: {
            userName: scatter.identity.accounts[0].name, //用户账户名
            model: "1", //模式类型
            large: -1, //大小
            single: -1, //单双
            baseNum: "",
            perBets: $(".myBets").val(),
            starRank: starRank,
            firstNums: betNumOne,
            secondNums: betNumTwo,
            thirdNums: betNumThree,
            forthNums: betNumFour,
            fifthNums: betNumFive,
            higherTotalBets: $("#higherBets").text(),
            sid: sid,
            access_token:access_token,
            userCode: userCode ? userCode : ""
        },
        success: function (msg) {
            eos
            .transfer(
                scatter.identity.accounts[0].name,
                "mylotterybet",
                `${parseFloat(higherBetsNum).toFixed(4)} EOS`,
                JSON.stringify(remark)
            )
            .then(result => {
                $("#stop").css({
                display: "none"
                });
                if (msg.status == "true") {
                popup({type:'success',msg:msg.msg,delay:1000,callBack:function(){
                  getBlance();
                  pageNumMybets = 1;
                  getHisBetsPage=1
                  checkIdentity();
                  getHisBets();
                  
                 }});
                } else {
                popup({type:'tip',msg:msg.msg,delay:1000,clickDomCancel:true});
                }
                btnObj.attr("disabled", false);
                btnObj.css(
                "background",
                "linear-gradient(to right, #ff5c4a , #ff8325)"
                );
            })
            .catch(e => {
                $("#stop").css({
                display: "none"
                });
                btnObj.attr("disabled", false);
                btnObj.css(
                "background",
                "linear-gradient(to right, #ff5c4a , #ff8325)"
                );
                popup({type:'tip',msg:e.message,delay:1000,clickDomCancel:true});
            });
        },
        error: function () {
            btnObj.attr("disabled", false);
            btnObj.css(
            "background",
            "linear-gradient(to right, #ff5c4a , #ff8325)"
            );
        }
        });
};

//手机号基础模式
function phoneBaseBets(btnObj){
  $.ajax({
    type:"post",
    url:`${baseUrl}/lottery/goBetScore.do`,
    data:{
      data: userName, //手机号或邮箱
      model: "0", //模式类型
      large: large, //大小
      single: single, //单双
      baseNum: baseNumStr,
      perBets: $(".myBets").val(),
      starRank: "",
      firstNums: "",
      secondNums: "",
      thirdNums: "",
      forthNums: "",
      fifthNums: "",
      higherTotalBets: "",
      userCode: userCode ? userCode : "",
      score:$("#baseTotalBets").text()
    },
    success:function(msg){
      $("#stop").css({display: "none"});
      if(msg.status=="true"){
        popup({type:'success',msg:success,delay:1000,callBack:function(){
         
          }
        });
      }else{
        popup({type:'error',msg:msg.msg,delay:1000,bg:true,clickDomCancel:true});
      }
      btnObj.attr("disabled", false);
      btnObj.css("background","linear-gradient(to right, #ff5c4a , #ff8325)");
    }
  })
};

function phoneHigherBets(btnObj){
  let betNumOne = "";
    let betNumTwo = "";
    let betNumThree = "";
    let betNumFour = "";
    let betNumFive = "";
    let higherNumOne = higherBetsStrOne;
    let higherNumTwo = higherBetsStrTwo;
    let higherNumThree =higherBetsStrThree;
    let higherNumFour =higherBetsStrFour;
    let higherNumFive =higherBetsStrFive;
    switch (starRank) {
        case "1":
            betNumOne = higherNumOne;
            break;
        case "2":
            betNumOne = higherNumOne;
            betNumTwo = higherNumTwo;
            break;
        case "3":
            betNumOne = higherNumOne;
            betNumTwo = higherNumTwo;
            betNumThree = higherNumThree;
            break;
        case "4":
            betNumOne = higherNumOne;
            betNumTwo = higherNumTwo;
            betNumThree = higherNumThree;
            betNumFour = higherNumFour;
            break;
        case "5":
            betNumOne = higherNumOne;
            betNumTwo = higherNumTwo;
            betNumThree = higherNumThree;
            betNumFour = higherNumFour;
            betNumFive = higherNumFive;
            break;
        }
  $.ajax({
    type:"post",
    url:`${baseUrl}/lottery/goBetScore.do`,
    data:{
      data: userName, //手机号或邮箱
      model: "1", //模式类型
      large: -1, //大小
      single: -1, //单双
      baseNum: "",
      perBets: $(".myBets").val(),
      starRank: starRank,
      firstNums: betNumOne,
      secondNums: betNumTwo,
      thirdNums: betNumThree,
      forthNums: betNumFour,
      fifthNums: betNumFive,
      score: $("#higherTotalBets").text(),
    },
    success:function(msg){
      $("#stop").css({
        display: "none"
    });
    if(msg.status=="true"){
      popup({type:'success',msg:success,delay:1000,callBack:function(){
         }
      });
    }else{
      popup({type:'error',msg:msg.msg,delay:1000,bg:true,clickDomCancel:true});
    }
    btnObj.attr("disabled", false);
    btnObj.css(
        "background",
        "linear-gradient(to right, #ff5c4a , #ff8325)"
    );
    },
    error: function () {
    btnObj.attr("disabled", false);
    btnObj.css(
        "background",
        "linear-gradient(to right, #ff5c4a , #ff8325)"
    );
    }
  })
};