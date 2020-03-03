let baseUrl = "https://myeosgame.com";
$("#goToLottery").click(function(){
    window.location.href=`${baseUrl}/wap/lottery`;
})
$("#gotoDice").click(function(){
  window.location.href=`${baseUrl}/wap/dice`;
})
//切换语言
$("#LanguageBox").click(function () {
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

  if (lang == "en_US") {
    $("#goToLottery").css("height","1rem");
    $("#inputBox").html(
      `<img src="../images/guoqi02.png" alt="" id="guoqiBox">English`
     );
  }