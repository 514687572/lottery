// let ws = new WebSocket(`ws://172.16.1.47/lottery/ws?userId=&gameType=dice`);
// let ip = "http://172.16.1.47/lottery";
let userId = getStorage("userName");
let ws = "";
if (userId.length == 0) {
    console.log(123)
    let userPhone = getStorage("phone");
    if (userPhone.length == 0) {
        ws = new WebSocket(`wss://myeosgame.com/ws?userId=&gameType=dice`);
    }
    else {
        ws = new WebSocket(`wss://myeosgame.com/ws?userId=${userPhone}&gameType=dice`);
    }
}
else {
    ws = new WebSocket(`wss://myeosgame.com/ws?userId=${userId}&gameType=dice`);
}
let ip = "https://myeosgame.com";
let jackpotBalance = "";
$(function () {
    let bettingType = 0;//投注类型
    let hovering = false;//鼠标悬停
    let received_msg = "";//接收区块信息
    let text_msg = "";//区块时间
    let dateAndTime = "";
    let time = "";
    let id = "";//区块链号
    let carouselAry = [];//开奖轮播
    let listNum = 0;
    let timer = "";//时间函数
    // let num = "";//中奖号
    let number = "";//判断大小对子
    let DevoteTheResults = "";//玩家投注服务器返回信息
    let allPrize = "";//中奖投注信息
    let GOSallPrize = "";//GOS中奖投注信息
    let TheWinningBetsAry = [];
    let theLlotteryIssue = "";//开奖期数
    let status = "";//用户账号状态
    let privateKey = "";//私钥
    let accountInformation = "";//用户账号提示信息
    let type = 1;//用户登录方式，0：scatter登录；1：私钥登录。
    let url = document.location.href;//获取邀请好友用户名
    let name = "";//用户名
    let userName = "";//获取我的投注区域用户名
    let sendTime = "";//投注时间
    let userBettingPeriods = "";//投注期数
    let rangeState = false;//滑动条是否可以滑动
    let sizeIn = 1;//判断是大于还是小于，默认1为大于，2为小于
    let loginType = "";//判断用户登录方式,2为私钥登录，3为快捷登录
    let userCode = url.split("=")[1];
    if (userCode == undefined) {
        userCode = ""
    }
    else {
        userCode = url.split("=")[1];
    }
    console.log("userCode", userCode);
    // 建立 web socket 连接成功触发事件
    ws.onopen = function () {
        // 使用 send() 方法发送数据
        // alert("数据发送中...");
        loginType = getStorage("loginType");


        if (loginType == 2) {
            $("#currency").text("EOS");
            name = getStorage("userName");
            $.ajax({
                type: "get",
                url: `${ip}/account/getAccount.do`,
                data: {
                    account: name
                },
                success: function (msg) {
                    msg.balance = msg.balance.split(" ")[0];
                    $("#userBalance").text(msg.balance);
                    msg.top = msg.top.split(" ")[0];
                    $("#userIntegral").text(msg.top);
                    $("#cpu").text(`${parseFloat((msg.cpu.used / msg.cpu.max) * 100).toFixed(2)}%`);
                    let cpuImg = $("#cpuImg").width();
                    let cpuNumber = (parseFloat((msg.cpu.used / msg.cpu.max)) * cpuImg);
                    if (cpuNumber < cpuImg) {
                        $("#cpuPlan").css("width", cpuNumber / 100 + "rem");
                    }
                    $("#userChainId").val("");
                    $("#userName").text(name);
                    $(".loginCPM").css("display", "none");
                    popup({ type: 'success', msg: "登录成功", delay: 1000 });
                    // Copy();//邀请好友复制到剪切板
                    $("#loginBtn").text("投注");
                    $("#loginBtn").attr("data-state", "2");
                    accountStatus(name);
                    $("#logOut").css("display", "block");
                    $("#CPMmask").css("display", "none");
                    $("#userMessage").css("display", "block");
                    $("#login").css("display", "none");
                }
            });
        }
        else if (loginType == 3) {
            $("#currency").text("GOS");
            $.ajax({
                type: "get",
                url: `${ip}/account/isLogin`,
                success: function (msg) {
                    if (msg == true) {
                        $.ajax({
                            type: "post",
                            url: `${ip}/account/getScoreUser`,
                            success: function (msg) {
                                console.log(msg)
                                getGosBalance();
                                let userPhone = msg.account.mobilePhone;
                                userPhone = userPhone.substr(0, 3) + "****" + userPhone.substr(7);
                                $("#gosUserMessage").css("display", "block");
                                $("#phone").val("");
                                $("#password").val("");
                                $("#telephone").text(userPhone);
                                $("#integral").text(msg.account.score + "GOS");
                                $("#loginBtn").text(`${dice94}`);
                                $("#loginBtn").attr("data-state", "2");
                                $("#login").css("display", "none");
                                accountStatus(msg.data);
                            }
                        })
                    }
                }
            });
        }
        // console.log(loginType)
        console.log("链接已开启")
    };
    // 接收服务端数据时触发事件
    ws.onmessage = function (evt) {
        mag = evt;
        // console.log("mag",evt)
        received_msg = evt.data;
        received_msg = JSON.parse(received_msg);
        // console.log("from", received_msg);
        if (received_msg.from == "diceBlock") {//区块消息
            sendTime = received_msg.date;
            theLotteryResults();
            theLotteryList();

        }
        else if (received_msg.from == "diceUserPrize") {//发送给某个用户
            if (loginType == 2) {
                iBetThe();
            }
            else if (loginType == 3) {
                GOSiBetThe();
            }
        }
        else if (received_msg.from == "diceAllPrize") {//EOS中奖展示
            allPrizeRecord();
        }
        else if (received_msg.from == "diceUserPrizeScore") {//GOS中奖展示
            console.log(received_msg)
            GOSallPrizeRecord();
        }
        else if (received_msg.code == 10) {
            let wrongPassword = JSON.parse(received_msg.data);
            if (wrongPassword.err == 1) {//私钥输入错误提示
                popup({ type: 'error', msg: `${dice95}`, delay: 1000, bg: true, clickDomCancel: true });
                $("#userChainId").val("")
            }
        }
        else if (received_msg.code == 10) {
            let wrongPassword = JSON.parse(received_msg.data);
            if (wrongPassword.err == 8) {//扣钱消息回滚错误提示
                popup({ type: 'tip', msg: `${dice122}`, delay: 1000 });
                $("#loginBtn").attr("data-state", "2");
            }
        }
        else if (received_msg.code == 1110) {
            let userinfo = JSON.parse(received_msg.data);
            $("#cpu").text(`${parseFloat((userinfo.cpu.used / userinfo.cpu.max) * 100).toFixed(2)}%`);
            let cpuImg = $("#cpuImg").width();
            let cpuNumber = (parseFloat((userinfo.cpu.used / userinfo.cpu.max)) * cpuImg);
            if (cpuNumber < cpuImg) {
                $("#cpuPlan").css("width", cpuNumber / 100 + "rem");
            }
            if (userinfo.type == 1) {
                type = userinfo.type;
                name = userinfo.loginUserId;
                console.log(userinfo)
                $("#userChainId").val("");
                $("#userName").text(name);
                $(".loginCPM").css("display", "none");
                popup({ type: 'success', msg: `${dice93}`, delay: 1000 });
                $("#currency").text("EOS");
                // Copy();//邀请好友复制到剪切板
                $("#loginBtn").text(`${dice94}`);
                $("#loginBtn").attr("data-state", "2");
                loginType = 2;
                saveStorage("userName", name);
                saveStorage("publicKey", userinfo.publicKey);
                saveStorage("loginType", 2);
                accountStatus(name);
                $("#logOut").css("display", "block");
                $("#CPMmask").css("display", "none");
                $("#userMessage").css("display", "block");
                $("#login").css("display", "none");
            }
        }
        else if (received_msg.code == 1125) {//用户余额
            let userinfo = JSON.parse(received_msg.data);
            userinfo.balance = userinfo.balance.split(" ")[0];
            $("#userBalance").text(userinfo.balance);
            userinfo.top = userinfo.top.split(" ")[0];
            $("#userIntegral").text(userinfo.top);

        }
        else if (received_msg.code == 1201) {//奖池余额
            let userinfo = JSON.parse(received_msg.data);
            userinfo = userinfo.balance.split(" ");
            let jackpotNum = parseInt(userinfo[0]);
            let jackpotMoney = jackpotNum + ` ${userinfo[1]}`;
            if (jackpotNum.toString().length > 3) {
                jackpotMoney = jackpotNum / 1000 + "k" + ` ${userinfo[1]}`;
            }
            $("#jackpotSum").text(jackpotMoney);
            jackpotBalance = jackpotMoney;
        }
        else if (received_msg.code == 1401) {//积分用户余额
            let userGOSMoney = JSON.parse(received_msg.data);
            console.log(1401, userGOSMoney);
            $("#integral").text(userGOSMoney.score);

        }
        else if (received_msg.code == 1403) {//积分奖池

            let jackpotGOS = JSON.parse(received_msg.data);
            console.log(1403, jackpotGOS)
            $("#jackpotSum").text(jackpotGOS.score);
        }
        theLlotteryIssue = text_msg.block_num;
    };

    // 断开 web socket 连接成功触发事件
    ws.onclose = function () {
        // alert("连接已关闭...");
        console.log("链接已关闭")
    };
    ws.onerror = function (e) {
        console.log('WebSocket发生错误: ' + e.code)
        console.log(e)
    }
    setInterval(function () {//webSocket心跳
        let heartbeat = {
            code: 0,
            data: {}
        }
        let stringheartbeat = JSON.stringify(heartbeat);
        ws.send(stringheartbeat);
    }, 60000);
    //点击LOGO返回游戏大厅
    $("#logo").click(function () {
        window.location.href = `${ip}/wap`;
    });
    //点击国旗图标切换语言
    $("#banner").click(function () {
        if ($("#banner").attr("data-banner") == 0) {
            $(".switchTheLanguage").css("display", "block");
            $("#banner").attr("data-banner", 1);
        }
        else {
            $(".switchTheLanguage").css("display", "none");
            $("#banner").attr("data-banner", 0);
        }
    });
    $("#chinese").click(function () {//切换中文
        $.ajax({
            type: "get",
            url: `${ip}/lottery/updLocale?type=1`,
            success: function (mag) {
                window.location.reload();
            }
        });
    });
    $("#english").click(function () {//切换英文
        $.ajax({
            type: "get",
            url: `${ip}/lottery/updLocale?type=2`,
            success: function (mag) {
                window.location.reload();
            }
        });
    });

    //切换英文后改变样式
    if (lang == "en_US") {
        $("#banner>img").attr("src", $("#english>img").attr("src"));
        $("#GuessInterval").css("width", "0.8rem");
        $("#crap").css("width", "1rem");
        $(".guessInterval1-1>div:first-child>p").css("width", "0.55rem");
        $("#minimum").css("left", "0.75rem");
        $("#max").css("left", "2.66rem");
        $("#banner>span").text("English");
    }

    //点击右上角更多操作图标弹出弹窗
    $("#MoreOperations").click(function () {
        if ($("#MoreOperations").attr("data-more") == 0) {
            $("#operation").css("display", "block");
            $("#MoreOperations").attr("data-more", 1);
        }
        else {
            $("#operation").css("display", "none");
            $("#MoreOperations").attr("data-more", 0);
        }
    });
    //点击游戏帮助跳转帮助页面
    $("#gameHelpBtn").click(function () {
        $("#gamePage").css("display", "none");
        $("#invuteFriends").css("display", "none");
        $("#gameRules").css("display", "block");
        $("#operation").css("display", "none");
        $("#MoreOperations").attr("data-more", 0);

    });
    $("#getBack").click(function () {
        $("#gamePage").css("display", "block");
        $("#gameRules").css("display", "none");
        $("#invuteFriends").css("display", "none");
        $("#operation").css("display", "none");
        $("#MoreOperations").attr("data-more", 0);
    });
    //点击邀请好友跳转到邀请好友页面
    $("#InviteBtn").click(function () {
        $("#gamePage").css("display", "none");
        $("#gameRules").css("display", "none");
        $("#invuteFriends").css("display", "block");
        $("#operation").css("display", "none");
        $("#MoreOperations").attr("data-more", 0);
        if ($("#loginBtn").attr("data-state") != 1) {
            Copy();
            TheSubordinateList(1);
        }
    });
    $("#getBack1").click(function () {
        $("#gamePage").css("display", "block");
        $("#gameRules").css("display", "none");
        $("#invuteFriends").css("display", "none");
        $("#operation").css("display", "none");
        $("#MoreOperations").attr("data-more", 0);
    });

    //切换投注选项（猜区间，猜大小）

    $("#crap").click(function () {
        $(".SelectTypeArea").css("display", "none");
        $(".crap1-1").css("display", "block");
        $("#crap").css({
            "color": "#3097ff",
            "border-bottom": "1px solid #3097ff"
        });
        $("#GuessInterval").css({
            "color": "#ffffff",
            "border-bottom": "none"
        });
        $(".CrapBtn").css("background", "#313862");
        bettingType = 0;
        $("#range").css("opacity", 0.5);
    });
    $("#GuessInterval").click(function () {
        $(".SelectTypeArea").css("display", "block");
        $(".crap1-1").css("display", "none");
        $("#GuessInterval").css({
            "color": "#3097ff",
            "border-bottom": "1px solid #3097ff"
        });
        $("#crap").css({
            "color": "#ffffff",
            "border-bottom": "none"
        });
        $(".CrapBtn").css("background", "#313862");
        bettingType = 0;
        if (sizeIn == 1) {
            greaterThan();//默认选中猜区间区域的大于按钮
        }
        else if (sizeIn == 2) {
            lessThan();//猜区间区域小于
        }

    });
    greaterThan();//默认选中猜区间区域的大于按钮

    //切换中奖投注和我的投注
    $("#iBetTheFont").click(function () {
        $("#theWinningTable").css("display", "none");
        $("#iBetTheTable").css("display", "block");
        $("#iBetTheFont").css({
            "color": "white"
        });
        $("#theWinningBetsFont").css({
            "color": "#424A55"
        });
        if ($("#loginBtn").attr("data-state") == 1) {
            userName = "";
        }
        if (type == 1) {
            if ($("#loginBtn").attr("data-state") == 1) {
                userName = "";
            } else {
                userName = name;
            }
        }
        bettingPage(0)
    });


    $("#theWinningBetsFont").click(function () {
        $("#iBetTheTable").css("display", "none");
        $("#theWinningTable").css("display", "block");
        $("#theWinningBetsFont").css({
            "color": "white"
        });
        $("#iBetTheFont").css({
            "color": "#424A55"
        });
    });

    //我的投注区域下拉刷新
    let nScrollHight = 0;
    let nScrollTop = 0;
    let nDivHight = $("#iBetThe").height();
    let page1 = 0;
    let scrollTop = 0;
    $("#iBetThe").on("touchmove", function () {
        nScrollHight = $(this)[0].scrollHeight;
        nScrollTop = $(this)[0].scrollTop;
    });
    $("#iBetThe").on("touchend", function () {
        if (nScrollTop + nDivHight == nScrollHight) {
            $("#loading").css("display", "block");
            page1 += 18;
            bettingPage(page1);
            setTimeout(() => {
                $("#iBetThe").scrollTop(nScrollTop);
                $("#loading").css("display", "none");
            }, 500)
        }
    });
    //点击头部登录按钮弹出登录窗口
    $("#login").click(function () {
        $(".loginCPM").css("display", "block");
        $("#CPMmask").css("display", "block");
    })
    //弹出登录弹窗后选择私钥登录或者快捷登录
    $("#shortcutLogin").click(function () {
        $(".shortcutPage").css("display", "block");
        $(".privateKeyPage").css("display", "none");
        $("#shortcutLogin").css({
            "color": "#3083de",
            "border-color": "#3083de"
        });
        $("#privateKeyLogin").css({
            "color": "white",
            "border-color": "#484956"
        });
        $("#userChainId").val("");
    });
    $("#privateKeyLogin").click(function () {
        $(".shortcutPage").css("display", "none");
        $(".privateKeyPage").css("display", "block");
        $("#privateKeyLogin").css({
            "color": "#3083de",
            "border-color": "#3083de"
        });
        $("#shortcutLogin").css({
            "color": "white",
            "border-color": "#484956"
        });
        $("#phone").val("");
        $("#password").val("");
    });
    //点击登录按钮弹出登录窗口
    $("#loginBtn").click(function () {
        if ($("#loginBtn").attr("data-state") == 1) {
            $(".loginCPM").css("display", "block");
            $("#CPMmask").css("display", "block");
        }
        else if ($("#loginBtn").attr("data-state") == 2) {
            if (loginType == 3) {
                if (status) {
                    let userMoney = $("#userBalance").text();//用户余额
                    let betMoney = $("#inputBox").val() - 0;//投注金额
                    let jackpotMoney = jackpotBalance;//奖池金额
                    jackpotMoney = jackpotMoney * 0.05;

                    if (bettingType == 0) {
                        popup({ type: 'tip', msg: `${dice102}`, delay: 1000 });
                    }
                    else if (betMoney > userMoney[0]) {
                        popup({ type: 'tip', msg: `${dice103}`, delay: 100000 });
                    }
                    else if (betMoney > jackpotMoney) {
                        popup({ type: 'tip', msg: `${dice104}`, delay: 1000 });

                    }
                    else {
                        let bettingMoney = $("#inputBox").val() - 0;
                        $.ajax({//投注请求
                            type: "post",
                            url: `${ip}/dice/getDiceBettingScore`,
                            async: false,
                            data: {
                                // account: name,
                                type: bettingType,
                                termnumber: theLlotteryIssue,
                                betTime: sendTime,
                                forecast: $("#b").text(),
                                bettingScore: bettingMoney,
                                // transaction_id: "",
                                // isPrivate: 1//投注时传送登录方式，0：scatter登录；1：私钥登录
                            },
                            success: function (data) {
                                if (data.status == false) {
                                    popup({ type: 'tip', msg: data.msg, delay: 1000 });
                                }
                                else {
                                    console.log("投注结果", data)
                                    popup({ type: 'success', msg: data.msg, delay: 1000 });
                                    // data.top = data.top.split(" ")[0];
                                    // $("#userIntegral").text(data.top);
                                    userBettingPeriods = data.termnumber

                                    // $("#loginBtn").attr("data-state", "3");


                                }

                            }
                        })
                    }
                }
                else {

                    popup({ type: 'tip', msg: accountInformation, delay: 1000 });
                }
            }
            else {
                $.ajax({//请求cpu
                    type: "get",
                    url: `${ip}/account/getAccount.do`,
                    async: false,
                    data: {
                        account: name
                    },
                    success: function (data) {
                        let cpunum = parseFloat((data.cpu.used / data.cpu.max) * 100).toFixed(2);
                        $("#cpu").text(cpunum + `%`);
                        let cpuImg = $("#cpuImg").width();
                        let cpuNumber = (parseFloat((data.cpu.used / data.cpu.max)) * cpuImg);
                        if (cpuNumber < cpuImg) {
                            $("#cpuPlan").css("width", cpuNumber / 100 + "rem");
                        }
                        if (cpunum >= 100) {
                            popup({ type: 'tip', msg: cpuerror, delay: 1000 });
                        }
                        else {
                            if (loginType == 2) {
                                if (status) {
                                    let userMoney = $("#userBalance").text();//用户余额
                                    console.log(userMoney);
                                    let betMoney = $("#inputBox").val() - 0;//投注金额
                                    console.log(betMoney)
                                    let jackpotMoney = jackpotBalance;//奖池金额
                                    jackpotMoney = jackpotMoney.split(" ");
                                    jackpotMoney = parseFloat(jackpotMoney[0]);
                                    jackpotMoney = jackpotMoney * 0.05;
                                    if (bettingType == 0) {
                                        popup({ type: 'tip', msg: `${dice102}`, delay: 1000 });
                                    }
                                    else if (betMoney > userMoney[0]) {
                                        popup({ type: 'tip', msg: `${dice103}`, delay: 1000 });
                                    }
                                    else if (betMoney > jackpotMoney) {
                                        popup({ type: 'tip', msg: `${dice104}`, delay: 1000 });
                                    }
                                    else {
                                        let bettingMoney = $("#inputBox").val() - 0;
                                        $.ajax({//投注请求
                                            type: "post",
                                            url: `${ip}/dice/getDiceBetting`,
                                            async: false,
                                            data: {
                                                account: name,
                                                type: bettingType,
                                                termnumber: theLlotteryIssue,
                                                betTime: sendTime,
                                                forecast: $("#b").text(),
                                                bettingEOS: bettingMoney,
                                                transaction_id: "",
                                                isPrivate: 1//投注时传送登录方式，0：scatter登录；1：私钥登录
                                            },
                                            success: function (data) {
                                                if (data.err) {
                                                    popup({ type: 'tip', msg: data.err, delay: 1000 });
                                                }
                                                else {
                                                    popup({ type: 'success', msg: data.msg, delay: 1000 });
                                                    data.balance = data.balance.split(" ")[0];
                                                    $("#userBalance").text(data.balance);
                                                    data.top = data.top.split(" ")[0];
                                                    $("#userIntegral").text(data.top);
                                                    userBettingPeriods = data.termnumber

                                                    $("#loginBtn").attr("data-state", "3");


                                                }

                                            }
                                        })
                                    }
                                }
                                else {
                                    popup({ type: 'tip', msg: accountInformation, delay: 1000 });
                                }
                            }
                        }
                    }
                });
            }

        }
        else if ($("#loginBtn").attr("data-state") == 3) {
            popup({ type: 'tip', msg: `${dice107}`, delay: 1000 });
        }
    });


    $("#closeCPM").click(function () {
        $(".loginCPM").css("display", "none");
        $("#CPMmask").css("display", "none");
    });


    //退出登录
    $("#logOut").click(function () {
        sessionStorage.removeItem('loginType');
        sessionStorage.removeItem('privateKey');
        sessionStorage.removeItem('userName');
        window.location.reload();
    })


    //点击登录
    $("#loginComfire").click(function () {
        privateKey = $("#userChainId").val();
        privateKeyLogin(privateKey, userCode);
    })

    //猜区间默认预测数
    $("#b").text(50);

    // 猜区间大小区域
    $(".CrapBtn").click(function (event) {
        $(".CrapBtn").css("background", "#313862");
        $(this).css({
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)"
        });
        //猜区间区域点击大的功能  
        if ($(this).attr("data-id") == 1) { //猜区间大于
            greaterThan();//默认选中猜区间区域的大于按钮
            // bettingType = 1;
            // rangeState = true;
            // if ($("#range1").val() < 1) {
            //     $("#range1").val(2);
            //     $("#b").text(2);
            // }
            // else if ($("#range1").val() > 98) {
            //     $("#range1").val(98);
            //     $("#b").text(98);
            // }
            // $("#range1").attr("min", 2);
            // $("#range1").attr("max", 98);
            // $("#range1").css({
            //     "opacity": "1",
            //     "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            //     "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            //     "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            //     "background": "linear-gradient(to right, #FF564F , #FF8523)"
            // });
            // let value = $("#range1").val();

            // let price = 99 - value;
            // let odds = (100 / price) * 0.98;
            // odds = odds.toFixed(2) - 0;
            // $("#odds").text(odds);
            // $("#minimum").text(2);
            // $("#max").text(98);
            // let moneyValue = $("#inputBox").val() - 0;
            // let bonus = (moneyValue * odds).toFixed(3) - 0;
            // $("#bonus").text(bonus);


            // $("#range1").on('input propertychange', function () {
            //     value = $("#range1").val();
            //     price = 99 - value;
            //     odds = (100 / price) * 0.98;
            //     odds = odds.toFixed(2) - 0;
            //     $("#odds").text(odds);
            //     moneyValue = $("#inputBox").val() - 0;
            //     bonus = (moneyValue * odds).toFixed(3) - 0;
            //     $("#bonus").text(bonus);
            //     $("#b").text(value);
            // });



        } else if ($(this).attr("data-id") == 2) {//小于
            lessThan();//猜区间区域小于
            // bettingType = 2;
            // rangeState = true;
            // $("#overUnder").text(`${dice7}`);
            // if ($("#range1").val() > 97) {
            //     $("#range1").val(97);
            //     $("#b").text(97);
            // }
            // else if ($("#range1").val() < 1) {
            //     $("#range1").val(1);
            //     $("#b").text(1);
            // }
            // $("#range1").attr("min", 1);
            // $("#range1").attr("max", 97);
            // $("#range1").css({
            //     "opacity": "1",
            //     "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            //     "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            //     "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            //     "background": "linear-gradient(to right, #FF564F , #FF8523)"
            // });
            // let value = $("#range1").val();
            // let odds = (98 / value).toFixed(2) - 0;
            // $("#odds").text(odds);
            // $("#minimum").text(1);
            // $("#max").text(97);
            // let moneyValue = $("#inputBox").val() - 0;
            // let bonus = (moneyValue * odds).toFixed(3) - 0;
            // $("#bonus").text(bonus);

            // $("#range1").on('input propertychange', function () {
            //     value = $("#range1").val();
            //     odds = (98 / value).toFixed(2) - 0;
            //     $("#odds").text(odds);
            //     moneyValue = $("#inputBox").val() - 0;
            //     bonus = (moneyValue * odds).toFixed(3) - 0;
            //     $("#bonus").text(bonus);
            //     $("#b").text(value);
            // });


        }
        else if ($(this).attr("data-id") == 3) {//大
            bettingType = 3;
            $("#odds").text("2.18");
            let value = $("#inputBox").val() - 0;
            let bonus = (value * 2.18).toFixed(3) - 0;
            $("#bonus").text(bonus);
        }
        else if ($(this).attr("data-id") == 4) {//小
            bettingType = 4;
            $("#odds").text("2.18");
            let value = $("#inputBox").val() - 0;
            let bonus = (value * 2.18).toFixed(3) - 0;
            $("#bonus").text(bonus);
        }
        else if ($(this).attr("data-id") == 5) {//等于
            bettingType = 5;
            $("#odds").text("9.80");
            let value = $("#inputBox").val() - 0;
            let bonus = (value * 9.8).toFixed(3) - 0;
            $("#bonus").text(bonus);
        }
        else {
            bettingType = 0;
        }

    });
    //使用加按钮调节滑动条
    $("#add").bind("touchstart", function () {
        if (rangeState) {
            if (bettingType == 1) {
                let rangeVal = $("#range1").val();
                rangeVal = parseInt(rangeVal);
                if (rangeVal < 98) {
                    let rangeNumber = parseInt($("#b").text());
                    ++rangeNumber;
                    $("#range1").val(rangeNumber);
                    $("#b").text($("#range1").val());
                    let price = 99 - $("#range1").val();
                    let odds = (100 / price) * 0.98;
                    odds = odds.toFixed(2) - 0;
                    $("#odds").text(odds);
                    let moneyValue = $("#inputBox").val() - 0;
                    let bonus = (moneyValue * odds).toFixed(3) - 0;
                    $("#bonus").text(bonus);
                }
            }
            else if (bettingType == 2) {
                let rangeVal = $("#range1").val();
                rangeVal = parseInt(rangeVal);
                console.log(rangeVal);
                if (rangeVal < 97) {
                    let rangeNumber = parseInt($("#b").text());
                    // rangeNumber = rangeNumber + 1;
                    ++rangeNumber;
                    $("#range1").val(rangeNumber);
                    $("#b").text($("#range1").val());
                    let odds = (98 / $("#range1").val()).toFixed(2) - 0;
                    $("#odds").text(odds);
                    let moneyValue = $("#inputBox").val() - 0;
                    let bonus = (moneyValue * odds).toFixed(3) - 0;
                    $("#bonus").text(bonus);
                }
            }
        }
    });
    //使用减按钮调节滑动条
    $("#subtract").bind("touchstart", function () {
        if (rangeState) {
            if (bettingType == 1) {
                let rangeVal = $("#range1").val();
                rangeVal = parseInt(rangeVal);
                if (rangeVal <= 98) {
                    let rangeVal = $("#range1").val();
                    rangeVal = parseInt(rangeVal);
                    console.log(rangeVal)
                    if (rangeVal > 2) {
                        let rangeNumber = parseInt($("#b").text());
                        // rangeNumber = rangeNumber - 1;
                        --rangeNumber;
                        $("#range1").val(rangeNumber);
                        $("#b").text($("#range1").val());
                        let price = 99 - $("#range1").val();
                        let odds = (100 / price) * 0.98;
                        odds = odds.toFixed(2) - 0;
                        $("#odds").text(odds);
                        let moneyValue = $("#inputBox").val() - 0;
                        let bonus = (moneyValue * odds).toFixed(3) - 0;
                        $("#bonus").text(bonus);
                    }
                }
            }
            else if (bettingType == 2) {
                let rangeVal = $("#range1").val();
                rangeVal = parseInt(rangeVal);
                console.log(rangeVal);
                if (rangeVal <= 97) {
                    let rangeVal = $("#range1").val();
                    rangeVal = parseInt(rangeVal);
                    console.log(rangeVal)
                    if (rangeVal > 1) {
                        let rangeNumber = parseInt($("#b").text());
                        // rangeNumber = rangeNumber - 1;
                        rangeNumber--;
                        $("#range1").val(rangeNumber);
                        $("#b").text($("#range1").val());
                        let odds = (98 / $("#range1").val()).toFixed(2) - 0;
                        $("#odds").text(odds);
                        let moneyValue = $("#inputBox").val() - 0;
                        let bonus = (moneyValue * odds).toFixed(3) - 0;
                        $("#bonus").text(bonus);
                    }
                }
            }
        }
    });



    // 投注金额区域
    $("#maximum").click(function () {
        $("#inputBox").val(100);
        $("#maximum").css({
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)"
        });
        $("#divide").css({
            "background": "#313862"
        });
        $("#ride").css({
            "background": "#313862"
        });
        commissions();
    });
    $("#ride").click(function () {
        $("#ride").css({
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)"
        });
        $("#divide").css({
            "background": "#313862"
        });
        $("#maximum").css({
            "background": "#313862"
        });
        let value = $("#inputBox").val() - 0;
        let sum = value * 2;
        $("#inputBox").val(sum);
        if (sum >= 100) {
            $("#inputBox").val(100);
        }
        commissions();
    });
    $("#divide").click(function () {
        $("#divide").css({
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)"
        });
        $("#ride").css({
            "background": "#313862"
        });
        $("#maximum").css({
            "background": "#313862"
        });
        let value = $("#inputBox").val() - 0;
        let sum = (value / 2).toFixed(1) - 0;
        $("#inputBox").val(sum);
        if (sum <= 0.1) {
            $("#inputBox").val(0.1);
        }
        commissions();
    });

    $("#inputBox").on('input propertychange', function () {
        commissions();
    });


    function commissions() {//获取投注金额，显示预计奖金
        let inputBoxValue = $("#inputBox").val() - 0;
        if (inputBoxValue >= 100) {
            $("#inputBox").val(100);
            inputBoxValue = 100;
        } else if (inputBoxValue < 0) {
            $("#inputBox").val(0.1);
            inputBoxValue = 0.1;
        }
        //  else if (inputBoxValue == 0) {
        //     $("#inputBox").val(0.1);
        //     inputBoxValue = 0.1;
        // }

        let odds = $("#odds").text() - 0;
        let bonus = (inputBoxValue * odds).toFixed(3) - 0;
        $("#bonus").text(bonus);
    };


    let betHash = "";
    //点击我的投注单条信息，弹出开奖详情弹窗
    $("#iBetThe").on("click", ".devoteTheResults", function () {
        $("#CheckThePopover").css("display", "block");
        $("#CPMmask").css("display", "block");
        let betTime1 = $(this).attr("data-betTime") - 0;
        betHash = $(this).attr("data-betHash");
        let betIssus = $(this).attr("data-betIssus");
        let betResult = $(this).attr("data-betResult");
        let betTime = new Date(betTime1);
        let betHours = betTime.getHours();//获取小时

        let betMinutes = betTime.getMinutes();//获取分钟
        if (betMinutes.toString().length == 1) {
            betMinutes = "0" + betMinutes;
        }
        let betSeconds = betTime.getSeconds();//获取秒钟
        if (betSeconds.toString().length == 1) {
            betSeconds = "0" + betSeconds;
        }
        betTime = betHours + ":" + betMinutes + ":" + betSeconds;
        // 区块链号
        $("#betHash").html("");
        let id = betHash;
        id = id.split('').reverse();
        let count = 0;
        let num = "";
        for (let i = 0; i < 21; i++) {//判断中奖号
            if (count < 2) {
                if (!isNaN(id[i])) {
                    count++;
                    num += id[i]
                    $("#betHash").prepend(`<span style="color: #FF564F">${id[i]}</span>`);

                } else {
                    $("#betHash").prepend(`<span>${id[i]}</span>`)
                }
            }
            else {
                $("#betHash").prepend(`<span>${id[i]}</span>`)
            }

        }
        $("#betIssus").text(betIssus);
        $("#betIssus1").text(betIssus);
        $("#betTime").text(betTime);
        $("#CheckThePopover-result").text(betResult);

    });

    //点击中奖投注单条信息，弹出开奖详情弹窗
    $("#allPrizeTable").on("click", ".theWinningBetsTable", function () {
        $("#CheckThePopover").css("display", "block");
        $("#CPMmask").css("display", "block");
        let betTime1 = $(this).attr("data-betTime1") - 0;
        betHash = $(this).attr("data-betHash1");
        let betIssus = $(this).attr("data-betIssus1");
        let betResult = $(this).attr("data-betResult1");
        let betTime = new Date(betTime1);
        let betHours = betTime.getHours();//获取小时

        let betMinutes = betTime.getMinutes();//获取分钟
        if (betMinutes.toString().length == 1) {
            betMinutes = "0" + betMinutes;
        }
        let betSeconds = betTime.getSeconds();//获取秒钟
        if (betSeconds.toString().length == 1) {
            betSeconds = "0" + betSeconds;
        }
        betTime = betHours + ":" + betMinutes + ":" + betSeconds;
        // 区块链号
        $("#betHash").html("");
        let id = betHash;
        id = id.split('').reverse();
        let count = 0;
        let num = "";
        for (let i = 0; i < 21; i++) {//判断中奖号
            if (count < 2) {
                if (!isNaN(id[i])) {
                    count++;
                    num += id[i]
                    $("#betHash").prepend(`<span style="color: #FF564F">${id[i]}</span>`);

                } else {
                    $("#betHash").prepend(`<span>${id[i]}</span>`)
                }
            }
            else {
                $("#betHash").prepend(`<span>${id[i]}</span>`)
            }

        }
        $("#betIssus").text(betIssus);
        $("#betIssus1").text(betIssus);
        $("#betTime").text(betTime);
        $("#CheckThePopover-result").text(betResult);

    });
    //关闭我的投注单条信息弹出开奖详情弹窗
    $("#CheckThePopoverClose").bind("touchstart", function () {
        $("#CheckThePopover").css("display", "none");
        $("#CPMmask").css("display", "none");
    });
    $("#tohash").click(function () {//点击投注弹窗内hash值进行跳转
        window.open(`https://bloks.io/transaction/${betHash}`);
        $("#CheckThePopover").css("display", "none");
        $("#CPMmask").css("display", "none");
    });


    // 抵押cpu
    let regOne = new RegExp("^[0-9]+(.[0-9]{0,1})?$");
    $("#cpuImg").click(function (event) {
        if ($("#loginBtn").attr("data-state") != 1) {
            event.stopPropagation();
            $("#pledgeCPU").css("display", "block");
            $("#CPMmask").css("display", "block");
            let cpuInput = 0;
            $("#cpuInput").blur(function () {
                cpuInput = parseInt($("#cpuInput").val() * 10) / 10;
            });
            $("#cpuComfire").click(function () {

                if (regOne.test(cpuInput) == true && cpuInput >= 0.5) {
                    $.ajax({
                        url: `${ip}/account/delegatebw.do`,
                        type: "post",
                        data: {
                            account: name,
                            quantity: `${parseFloat(cpuInput).toFixed(4)} EOS`
                        },
                        success: function (msg) {
                            $.ajax({//请求cpu
                                type: "get",
                                url: `${ip}/account/getAccount.do`,
                                async: false,
                                data: {
                                    account: name
                                },
                                success: function (data) {
                                    data.balance = data.balance.split(" ")[0];
                                    $("#userBalance").text(data.balance);
                                    let cpunum = parseFloat((data.cpu.used / data.cpu.max) * 100).toFixed(2);
                                    $("#cpu").text(cpunum + `%`);
                                    let cpuImg = $("#cpuImg").width();
                                    let cpuNumber = (parseFloat((userinfo.cpu.used / userinfo.cpu.max)) * cpuImg);
                                    if (cpuNumber < cpuImg) {
                                        $("#cpuPlan").css("width", cpuNumber / 100 + "rem");
                                    }
                                }
                            });
                            $("#pledgeCPU").css("display", "none");
                            $("#CPMmask").css("display", "none");
                            $("#cpuInput").val("");
                        }
                    })

                }
                else {
                    $.message({
                        message: inputAmount,
                        type: 'warning'
                    });
                }
            });
        }


    });
    //关闭抵押cpu弹窗
    $("#cpuCPMCloce").click(function () {
        $("#pledgeCPU").css("display", "none");
        $("#CPMmask").css("display", "none");
        $("#cpuInput").val("");
    });

    //点击footer区域玩家群和股东群进行跳转
    $("#GroupOfPlayers").click(function () {
        window.location.href = "https://t.me/eoscasinosg";
    });
    $("#ShareholdersOf").click(function () {
        window.location.href = "https://t.me/eoscasinosg";
    });




    //手机号登录
    let phoneReg = /^[1]{1}[0-9]{10}/g;
    let phoneBoolean = false;
    let phoneNum = "";
    $("#phone").bind("input propertychange", function (event) {
        phoneNum = $(this).val();
        if (phoneReg.test(phoneNum) == true) {
            phoneBoolean = true;
            $(this).css("color", "#3083de");
        } else {
            $(this).css("color", "red");
            phoneBoolean = false;
        }
    });


    //密码验证
    let regPassword = /^(?=.*[a-z])(?=.*\d)[^]{8,12}$/;
    let passWordBoolean = false;
    let passwordInput = "";
    $("#password").bind("input propertychange", function (event) {
        passwordInput = $(this).val();
        if (regPassword.test(passwordInput) == true) {
            passWordBoolean = true;
            $(this).css("color", "#3083de");
        } else {
            $(this).css("color", "red");
            passWordBoolean = false;
        }
    });
    //注册跳转
    $("#register").click(function () {
        window.location.href = `${ip}/wap/registerstep?gameType=dice`;
    });

    //手机号登录
    $("#shortcutLoginBtn").click(function () {
        console.log("点击快捷登录")
        $.ajax({
            type: "post",
            url: `${ip}/account/scoreUserLogin.do`,
            data: {
                data: phoneNum,
                password: passwordInput
            },
            success: function (msg) {
                console.log(msg)
                if (msg.success == false) {
                    popup({ type: 'tip', msg: msg.msg, delay: 1000 });
                    $("#phone").val("");
                    $("#password").val("");
                }
                else if (msg.success == true) {
                    popup({
                        type: 'success', msg: `${dice93}`, delay: 1000, callBack: function () {
                            getGosBalance();
                            let userPhone = msg.data;
                            userPhone = userPhone.substr(0, 3) + "****" + userPhone.substr(7);
                            $(".loginCPM").css("display", "none");
                            $("#gosUserMessage").css("display", "block");
                            $("#CPMmask").css("display", "none");
                            $("#phone").val("");
                            $("#password").val("");
                            $("#currency").text("GOS");
                            $("#telephone").text(userPhone);
                            $("#integral").text(msg.score + "GOS");
                            loginType = 3;
                            saveStorage("phone", msg.data);
                            saveStorage("loginType", 3);
                            $("#loginBtn").text(`${dice94}`);
                            $("#loginBtn").attr("data-state", "2");
                            $("#login").css("display", "none");
                            accountStatus(msg.data);
                            window.location.reload();
                        }
                    });

                }
            }
        })
    });

    function getGosBalance() {//快捷登录成功，获取奖池
        $.ajax({
            type: "get",
            url: `${ip}/account/getscorePoolBalance`,
            data: {
                gameType: "dice"
            },
            success: function (msg) {
                console.log("初始奖池", msg)
                jackpotBalance = msg + "GOS";
                if (msg >= 10000) {
                    $("#jackpotSum").text(`${parseInt((msg / 1000) * 10000) / 10000}K`);
                } else {
                    $("#jackpotSum").text(`${parseInt(msg * 10000) / 10000}`);
                }

            }
        });
    }
    //充值跳转
    $("#topUp").click(function () {
        window.location.href = `${ip}/wap/payfor?gameType=dice`;

    });
    //提现跳转
    $("#withdrawDeposit").click(function () {
        window.location.href = `${ip}/wap/cashWithdrawal?gameType=dice`;
    });
    //gos点击退出
    $("#gosQuit").click(function () {
        $.ajax({
            type:"get",
            url:`${ip}/account/scoreUserLoginOut.do`,
            success:function(msg){
                sessionStorage.removeItem('loginType');
                sessionStorage.removeItem('phone');
                $("#gosUserMessage").css("display", "none");
                window.location.reload();
            }
          })
    });














    // 函数接口区域

    function greaterThan() {//默认选中猜区间区域的大于按钮
        $("#greaterThan").css({
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)"
        });
        sizeIn = 1;
        bettingType = 1;
        rangeState = true;
        $("#overUnder").text(`${dice6}`);
        if ($("#range1").val() < 1) {
            $("#range1").val(2);
            $("#b").text(2);
        }
        else if ($("#range1").val() > 98) {
            $("#range1").val(98);
            $("#b").text(98);
        }
        $("#range1").attr("min", 2);
        $("#range1").attr("max", 98);
        $("#range1").css({
            "opacity": "1",
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)"
        });
        let value = $("#range1").val();

        let price = 99 - value;
        let odds = (100 / price) * 0.98;
        odds = odds.toFixed(2) - 0;
        $("#odds").text(odds);
        $("#minimum").text(2);
        $("#max").text(98);
        let moneyValue = $("#inputBox").val() - 0;
        let bonus = (moneyValue * odds).toFixed(3) - 0;
        $("#bonus").text(bonus);


        $("#range1").on('input propertychange', function () {
            value = $("#range1").val();
            price = 99 - value;
            odds = (100 / price) * 0.98;
            odds = odds.toFixed(2) - 0;
            $("#odds").text(odds);
            moneyValue = $("#inputBox").val() - 0;
            bonus = (moneyValue * odds).toFixed(3) - 0;
            $("#bonus").text(bonus);
            $("#b").text(value);
        });
    }

    function lessThan() {//猜区间区域小于
        $("#lessThan").css({
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)"
        });
        sizeIn = 2;
        bettingType = 2;
        rangeState = true;
        $("#overUnder").text(`${dice7}`);
        if ($("#range1").val() > 97) {
            $("#range1").val(97);
            $("#b").text(97);
        }
        else if ($("#range1").val() < 1) {
            $("#range1").val(1);
            $("#b").text(1);
        }
        $("#range1").attr("min", 1);
        $("#range1").attr("max", 97);
        $("#range1").css({
            "opacity": "1",
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)"
        });
        let value = $("#range1").val();
        let odds = (98 / value).toFixed(2) - 0;
        $("#odds").text(odds);
        $("#minimum").text(1);
        $("#max").text(97);
        let moneyValue = $("#inputBox").val() - 0;
        let bonus = (moneyValue * odds).toFixed(3) - 0;
        $("#bonus").text(bonus);

        $("#range1").on('input propertychange', function () {
            value = $("#range1").val();
            odds = (98 / value).toFixed(2) - 0;
            $("#odds").text(odds);
            moneyValue = $("#inputBox").val() - 0;
            bonus = (moneyValue * odds).toFixed(3) - 0;
            $("#bonus").text(bonus);
            $("#b").text(value);
        });
    }

    function accountStatus(name) {//用户账号状态
        let loginType = getStorage("loginType")
        $.ajax({
            type: "get",
            url: `${ip}/lottery/checkUserStatus`,
            async: false,
            data: {
                userName: name,
                gameType: "dice",
                loginType: loginType
            },
            success: function (data) {
                console.log(data)
                status = data.status;
                accountInformation = data.msg;
            }
        })
    }


    function privateKeyLogin(key, code) {//私钥登录向服务器发送的参数
        let obj = {
            code: 1110,
            data: {
                privateKey: key,
                userCode: code,
                type: 1
            }
        }
        let stringObj = JSON.stringify(obj);
        ws.send(stringObj);
    }


    function Copy() {//邀请好友复制到剪切板
        $("#inviteInput").val(`${ip}/lottery/toDice?userCode=${name}`);
        $("#InviteCopy").css({
            "cursor": "pointer"
        });
        $("#InviteCopy").click(function () {
            let Url2 = document.getElementById("inviteInput");
            Url2.select(); // 选择对象
            document.execCommand("Copy"); // 执行浏览器复制命令
            popup({ type: 'success', msg: `${dice100}`, delay: 1000 });
        });
    }


    // 最新开奖结果
    function theLotteryResults() {
        $("#newBlockChainNo").html("");
        text_msg = JSON.parse(received_msg.text);
        // 区块时间
        dateAndTime = text_msg.header.timestamp;
        time = dateAndTime;
        time = time.slice(5, 16);
        $("#newTime").text(time);
        // 区块链号
        id = text_msg.id;
        id = id.split('').reverse();

        let content = "";
        let count = 0;
        let num = "";
        for (let i = 0; i < 21; i++) {//判断中奖号
            // console.log(id)
            if (count < 2) {
                if (!isNaN(id[i])) {
                    count++;
                    num += id[i]
                    $("#newBlockChainNo").prepend(`<span style="color: #FF564F">${id[i]}</span>`);

                } else {
                    $("#newBlockChainNo").prepend(`<span>${id[i]}</span>`)
                }
            }
            else {
                $("#newBlockChainNo").prepend(`<span>${id[i]}</span>`)
            }

        }
        num = num.split('').reverse().join("");
        $("#newWinningNumbers").text(num);
        $("#winningNum").text(num);
        // $("#newBlockChainNo").html(content);
        // console.log(content)

        // 最新开奖期数
        $("#newPeriods").text(text_msg.block_num);
        // 最新开奖期数的大小对子显示
        number = num.split("");
        if (number[0] == number[1]) {
            $("#result").text(`${dice5}`);

        } else {
            number = parseInt(number.join(""));
            if (number >= 50) {
                $("#result").text(`${dice3}`);
            } else {
                $("#result").text(`${dice4}`);
            }
        }

    }
    //点击滚动区域hash值跳转
    $("#carousel").on("click", ".TheLotteryData", function () {
        let hash = $(this).attr("data-num");
        window.open(`https://bloks.io/transaction/${hash}`);
    })
    //开奖滚动区域
    function theLotteryList() {
        let content = [];
        let content1 = [];
        let count = 0;
        let num = "";
        let bigColor = "";
        let smallColor = "";
        let pairColor = "";
        let blockId = id.join("");
        blockId = blockId.split('').reverse().join('');
        for (let i = 0; i < 21; i++) {//判断中奖号
            if (count < 2) {
                if (!isNaN(id[i])) {
                    count++;
                    num += id[i]
                    content += `<span style="color: #FF564F">${id[i]}</span>~`;

                } else {
                    content += `<span>${id[i]}</span>~`
                }
            }
            else {
                content += `<span>${id[i]}</span>~`
            }

        }
        content = content.split('~').reverse().join();

        content = content.split(',');
        for (let n = 0; n < content.length; n++) {
            content1.push(content[n])
        }
        num = num.split('').reverse().join("");
        content1 = content1.join("");
        let carouselObj = {
            "periods": text_msg.block_num,
            "blockChainNo": content1,
            "winningNumbers": num,
            "theLotteryTime": time,
            "bigColor": bigColor,
            "smallColor": smallColor,
            "pairColor": pairColor,
            "blockId": blockId
        }

        if (carouselAry.length <= 8) {
            carouselAry.unshift(carouselObj);
        } else {
            carouselAry.pop();
            carouselAry.unshift(carouselObj);
        }
        let carouselContent = "";

        let buyIn = userBettingPeriods + 10 - 0;
        for (let item of carouselAry) {
            if (item.periods == userBettingPeriods) {
                carouselContent += `
                <ul class="TheLotteryData" style="background:#313862" data-num=${item.periods}>
                            <li>${item.periods}</li>
                            <li>
                                <p>...</p>
                                <p class="content">${item.blockChainNo}</p>
                            </li>
                            <li>${item.theLotteryTime}</li>
                            
                        </ul>
                `

            }
            else if (item.periods == buyIn) {
                carouselContent += `
                <ul class="TheLotteryData" style="background:#316240" data-num=${item.periods}>
                            <li>${item.periods}</li>
                            <li>
                                <p>...</p>
                                <p class="content">${item.blockChainNo}</p>
                            </li>
                            <li>${item.theLotteryTime}</li>
                            
                        </ul>
                `
                $("#betNum").text(item.winningNumbers);
                $("#betNum").css({
                    "display": "block",
                    "color": "#1dff22"
                });
                $("#winningNum").css("display", "none");
                setTimeout(function () {
                    $("#winningNum").css("display", "block");
                    $("#betNum").css("display", "none");
                }, 5000);

            }
            else {
                carouselContent += `
            <ul class="TheLotteryData" data-num=${item.periods}>
                        <li>${item.periods}</li>
                        <li>
                            <p>...</p>
                            <p class="content">${item.blockChainNo}</p>
                        </li>
                        <li>${item.theLotteryTime}</li>
                        
                    </ul>
            `
            }

        }
        $("#carousel").html(carouselContent);
        $("#carousel>ul:nth-of-type(1)").animate({ marginTop: ".22rem" });
    }


    //EOS中奖投注表格内容
    function allPrizeRecord() {
        $("#allPrizeTable").html("");
        allPrize = JSON.parse(received_msg.text);
        console.log(allPrize)
        let allPrizeRecordContent = "";//中奖投注信息
        let myType = "";//中奖投注内容
        let time = "";//时间
        let oTime = "";//日期
        let hours = "";//小时
        let minutes = "";//分钟
        let dateTime = "";
        if (TheWinningBetsAry.length < 15) {
            TheWinningBetsAry.unshift(allPrize);
            for (let item of TheWinningBetsAry) {
                time = new Date(item.time.time);
                hours = time.getHours(); //获取当前小时数(0-23)
                minutes = time.getMinutes(); //获取当前分钟数(0-59)
                let oMonth = time.getMonth() + 1;
                let oDay = time.getDate();
                if (minutes.toString().length == 1) {
                    minutes = "0" + minutes;
                }
                oTime = oMonth + '/' + oDay;//最后拼接时间
                dateTime = oTime + " " + hours + ":" + minutes;
                if (item.type == 1) {
                    myType = item.prizenumber + ">" + item.forecast;
                }
                else if (item.type == 2) {
                    myType = item.prizenumber + "<" + item.forecast;
                }
                else if (item.type == 3) {
                    myType = `${dice3}`;
                }
                else if (item.type == 4) {
                    myType = `${dice4}`;
                }
                else if (item.type == 5) {
                    myType = `${dice5}`;
                }
                console.log(DevoteTheResults)
                allPrizeRecordContent += `
                <ul class="theWinningBetsTable" data-betResult1=${DevoteTheResults.prizenumber} data-betTime1=${DevoteTheResults.time.time} data-betHash1=${DevoteTheResults.hash} data-betIssus1=${DevoteTheResults.termnumber}>
                                <li>${dateTime}</li>
                                <li>${item.termnumber}</li>
                                <li>${item.account}</li>
                                <li>${myType}</li>
                                <li style="color: #BF4B56">+${item.prizeEOS}</li>
                            </ul>
                `

            }
        } else {
            TheWinningBetsAry.unshift(allPrize);
            TheWinningBetsAry.pop();
            for (let item of TheWinningBetsAry) {
                time = new Date(item.time.time);
                hours = time.getHours(); //获取当前小时数(0-23)
                minutes = time.getMinutes(); //获取当前分钟数(0-59)
                let oMonth = time.getMonth() + 1;
                let oDay = time.getDate();
                if (minutes.toString().length == 1) {
                    minutes = "0" + minutes;
                }
                oTime = oMonth + '/' + oDay;//最后拼接时间
                dateTime = oTime + " " + hours + ":" + minutes;
                if (item.type == 1) {
                    myType = item.prizenumber + ">" + item.forecast;
                }
                else if (item.type == 2) {
                    myType = item.prizenumber + "<" + item.forecast;
                }
                else if (item.type == 3) {
                    myType = `${dice3}`;
                }
                else if (item.type == 4) {
                    myType = `${dice4}`;
                }
                else if (item.type == 5) {
                    myType = `${dice5}`;
                }
                allPrizeRecordContent += `
                <ul class="theWinningBetsTable" data-betResult1=${DevoteTheResults.prizenumber} data-betTime1=${DevoteTheResults.time.time} data-betHash1=${DevoteTheResults.hash} data-betIssus1=${DevoteTheResults.termnumber}>
                                <li>${dateTime}</li>
                                <li>${item.termnumber}</li>
                                <li>${item.account}</li>
                                <li>${myType}</li>
                                <li style="color: #BF4B56">+${item.prizeEOS}</li>
                            </ul>
                `

            }

        }
        $("#allPrizeTable").html(allPrizeRecordContent);
    }




    //GOS中奖投注表格内容
    function GOSallPrizeRecord() {
        $("#allPrizeTable").html("");
        GOSallPrize = JSON.parse(received_msg.text);
        console.log(GOSallPrize)
        let allPrizeRecordContent = "";//中奖投注信息
        let myType = "";//中奖投注内容
        let time = "";//时间
        let oTime = "";//日期
        let hours = "";//小时
        let minutes = "";//分钟
        let dateTime = "";
        if (TheWinningBetsAry.length < 15) {
            TheWinningBetsAry.unshift(GOSallPrize);
            for (let item of TheWinningBetsAry) {
                time = new Date(item.time.time);
                hours = time.getHours(); //获取当前小时数(0-23)
                minutes = time.getMinutes(); //获取当前分钟数(0-59)
                let oMonth = time.getMonth() + 1;
                let oDay = time.getDate();
                if (minutes.toString().length == 1) {
                    minutes = "0" + minutes;
                }
                oTime = oMonth + '/' + oDay;//最后拼接时间
                dateTime = oTime + " " + hours + ":" + minutes;
                if (item.type == 1) {
                    myType = item.prizenumber + ">" + item.forecast;
                }
                else if (item.type == 2) {
                    myType = item.prizenumber + "<" + item.forecast;
                }
                else if (item.type == 3) {
                    myType = `${dice3}`;
                }
                else if (item.type == 4) {
                    myType = `${dice4}`;
                }
                else if (item.type == 5) {
                    myType = `${dice5}`;
                }
                console.log(DevoteTheResults)
                allPrizeRecordContent += `
                <ul class="theWinningBetsTable" data-betResult1=${DevoteTheResults.prizenumber} data-betTime1=${DevoteTheResults.time.time} data-betHash1=${DevoteTheResults.hash} data-betIssus1=${DevoteTheResults.termnumber}>
                                <li>${dateTime}</li>
                                <li>${item.termnumber}</li>
                                <li>${item.account}</li>
                                <li>${myType}</li>
                                <li style="color: #BF4B56">+${item.prizeEOS}</li>
                            </ul>
                `

            }
        } else {
            TheWinningBetsAry.unshift(GOSallPrize);
            TheWinningBetsAry.pop();
            for (let item of TheWinningBetsAry) {
                time = new Date(item.time.time);
                hours = time.getHours(); //获取当前小时数(0-23)
                minutes = time.getMinutes(); //获取当前分钟数(0-59)
                let oMonth = time.getMonth() + 1;
                let oDay = time.getDate();
                if (minutes.toString().length == 1) {
                    minutes = "0" + minutes;
                }
                oTime = oMonth + '/' + oDay;//最后拼接时间
                dateTime = oTime + " " + hours + ":" + minutes;
                if (item.type == 1) {
                    myType = item.prizenumber + ">" + item.forecast;
                }
                else if (item.type == 2) {
                    myType = item.prizenumber + "<" + item.forecast;
                }
                else if (item.type == 3) {
                    myType = `${dice3}`;
                }
                else if (item.type == 4) {
                    myType = `${dice4}`;
                }
                else if (item.type == 5) {
                    myType = `${dice5}`;
                }
                allPrizeRecordContent += `
                <ul class="theWinningBetsTable" data-betResult1=${DevoteTheResults.prizenumber} data-betTime1=${DevoteTheResults.time.time} data-betHash1=${DevoteTheResults.hash} data-betIssus1=${DevoteTheResults.termnumber}>
                                <li>${dateTime}</li>
                                <li>${item.termnumber}</li>
                                <li>${item.account}</li>
                                <li>${myType}</li>
                                <li style="color: #BF4B56">+${item.prizeEOS}</li>
                            </ul>
                `

            }

        }
        $("#allPrizeTable").html(allPrizeRecordContent);
    }



    //我的投注表格内容
    function iBetThe() {
        $("#loginBtn").attr("data-state", "2");
        DevoteTheResults = JSON.parse(received_msg.text);
        console.log(DevoteTheResults)
        let myType = "";//我的投注内容
        let myMoney = "";//奖金
        let myMoneyStyle = "";//金额样式
        let myWinStyle = "";//中奖样式
        let time = "";//时间
        let oTime = "";//日期
        let hours = "";//小时
        let minutes = "";//分钟
        let dateTime = "";
        if (DevoteTheResults.type == 1) {
            myType = DevoteTheResults.prizenumber + ">" + "[" + DevoteTheResults.forecast + "]";
        }
        else if (DevoteTheResults.type == 2) {
            myType = DevoteTheResults.prizenumber + "<" + "[" + DevoteTheResults.forecast + "]";
        }
        else if (DevoteTheResults.type == 3) {
            myType = `${dice3}`;
        }
        else if (DevoteTheResults.type == 4) {
            myType = `${dice4}`;
        }
        else if (DevoteTheResults.type == 5) {
            myType = `${dice5}`;
        }
        if (DevoteTheResults.state == 0) {
            myMoney = "-" + DevoteTheResults.bettingEOS;
            myMoneyStyle = "#3097ff";
            popup({ type: 'tip', msg: `${dice96}`, delay: 1000 });
        } else {
            // myWinStyle = "myWinStyle";
            myMoney = "+" + DevoteTheResults.prizeEOS;
            myMoneyStyle = "#BF4B56";
            popup({ type: 'tip', msg: `${dice97}${DevoteTheResults.prizeEOS}`, delay: 1000 });
        }
        time = new Date(DevoteTheResults.time.time);
        hours = time.getHours(); //获取当前小时数(0-23)
        minutes = time.getMinutes(); //获取当前分钟数(0-59)
        let oMonth = time.getMonth() + 1;
        let oDay = time.getDate();
        if (minutes.toString().length == 1) {
            minutes = "0" + minutes;
        }
        oTime = oMonth + '/' + oDay;//最后拼接时间
        dateTime = oTime + " " + hours + ":" + minutes;
        let iBetTheContent = "";
        iBetTheContent = `
    <ul class="devoteTheResults"  data-betResult=${DevoteTheResults.prizenumber} data-betTime=${DevoteTheResults.time.time} data-betHash=${DevoteTheResults.hash} data-betIssus=${DevoteTheResults.termnumber}>
                    <li>${dateTime}</li>
                    <li>${DevoteTheResults.termnumber}</li>
                    <li>${myType}</li>
                    <li>${DevoteTheResults.bettingEOS}</li>
                    <li  style="color: ${myMoneyStyle}">${myMoney}</li>
                </ul>
    `
        $("#iBetThe").prepend(iBetTheContent);


    }
    //GOS我的投注表格内容
    function GOSiBetThe() {
        $("#loginBtn").attr("data-state", "2");
        DevoteTheResults = JSON.parse(received_msg.text);
        console.log(DevoteTheResults)
        let myType = "";//我的投注内容
        let myMoney = "";//奖金
        let myMoneyStyle = "";//金额样式
        let myWinStyle = "";//中奖样式
        let time = "";//时间
        let oTime = "";//日期
        let hours = "";//小时
        let minutes = "";//分钟
        let dateTime = "";
        if (DevoteTheResults.type == 1) {
            myType = DevoteTheResults.prizenumber + ">" + "[" + DevoteTheResults.forecast + "]";
        }
        else if (DevoteTheResults.type == 2) {
            myType = DevoteTheResults.prizenumber + "<" + "[" + DevoteTheResults.forecast + "]";
        }
        else if (DevoteTheResults.type == 3) {
            myType = `${dice3}`;
        }
        else if (DevoteTheResults.type == 4) {
            myType = `${dice4}`;
        }
        else if (DevoteTheResults.type == 5) {
            myType = `${dice5}`;
        }
        if (DevoteTheResults.state == 0) {
            myMoney = "-" + DevoteTheResults.bettingScore;
            myMoneyStyle = "#3097ff";
            popup({ type: 'tip', msg: `${dice96}`, delay: 1000 });
        } else {
            // myWinStyle = "myWinStyle";
            myMoney = "+" + DevoteTheResults.prizeScore;
            myMoneyStyle = "#BF4B56";
            popup({ type: 'tip', msg: `${dice97}${DevoteTheResults.prizeScore}`, delay: 1000 });
        }
        time = new Date(DevoteTheResults.time.time);
        hours = time.getHours(); //获取当前小时数(0-23)
        minutes = time.getMinutes(); //获取当前分钟数(0-59)
        let oMonth = time.getMonth() + 1;
        let oDay = time.getDate();
        if (minutes.toString().length == 1) {
            minutes = "0" + minutes;
        }
        oTime = oMonth + '/' + oDay;//最后拼接时间
        dateTime = oTime + " " + hours + ":" + minutes;
        let iBetTheContent = "";
        iBetTheContent = `
    <ul class="devoteTheResults"  data-betResult=${DevoteTheResults.prizenumber} data-betTime=${DevoteTheResults.time.time} data-betHash=${DevoteTheResults.hash} data-betIssus=${DevoteTheResults.termnumber}>
                    <li>${dateTime}</li>
                    <li>${DevoteTheResults.termnumber}</li>
                    <li>${myType}</li>
                    <li>${DevoteTheResults.bettingScore}</li>
                    <li  style="color: ${myMoneyStyle}">${myMoney}</li>
                </ul>
    `
        $("#iBetThe").prepend(iBetTheContent);


    }

    function bettingPage(page) {//点击我的投注，从服务器获取投注记录
        $.ajax({
            type: "get",
            url: `${ip}/dice/getAccountDice`,
            async: false,
            data: {
                account: userName,
                begin: page,
                limit: 18
            },
            success: function (data) {
                let myType = "";//我的投注内容
                let myMoney = "";//奖金
                let myMoneyStyle = "";//金额样式
                let iBetTheContent = "";
                let time = "";//时间
                let oTime = "";//日期
                let hours = "";//小时
                let minutes = "";//分钟
                let dateTime = "";
                for (let item of data.accountDice) {
                    if (item.type == 1) {
                        myType = item.prizenumber + ">" + "[" + item.forecast + "]";
                    }
                    else if (item.type == 2) {
                        myType = item.prizenumber + "<" + "[" + item.forecast + "]";
                    }
                    else if (item.type == 3) {
                        myType = `${dice3}`;
                    }
                    else if (item.type == 4) {
                        myType = `${dice4}`;
                    }
                    else if (item.type == 5) {
                        myType = `${dice5}`;
                    }
                    if (item.state == 0) {
                        myMoney = "-" + item.bettingEOS;
                        myMoneyStyle = "#3097ff";
                    } else {
                        myMoney = "+" + item.prizeEOS;
                        myMoneyStyle = "#BF4B56";
                    }
                    time = new Date(item.time);
                    hours = time.getHours(); //获取当前小时数(0-23)
                    minutes = time.getMinutes(); //获取当前分钟数(0-59)
                    let oMonth = time.getMonth() + 1;
                    let oDay = time.getDate();
                    if (minutes.toString().length == 1) {
                        minutes = "0" + minutes;
                    }
                    oTime = oMonth + '/' + oDay;//最后拼接时间
                    dateTime = oTime + " " + hours + ":" + minutes;
                    iBetTheContent += `
                    <ul class="devoteTheResults"  data-betResult=${item.prizenumber} data-betTime=${item.time.time} data-betHash=${item.hash} data-betIssus=${item.termnumber}>
                                    <li>${dateTime}</li>
                                    <li>${item.termnumber}</li>
                                    <li>${myType}</li>
                                    <li>${item.bettingEOS}</li>
                                    <li  style="color: ${myMoneyStyle}">${myMoney}</li>
                                </ul>
                    `

                }
                $("#iBetThe").append(iBetTheContent);
            }
        });
    }



    function TheSubordinateList(page) {//获取邀请好友页面，下级列表和页码

        $.ajax({
            type: "get",
            url: `${ip}/user/getMyChildsto.do`,
            async: false,
            data: {
                userName: name,
                pageNum: page
            },
            success: function (data) {

                if (data.msg != false) {
                    let subuserFormCount = "";
                    $("#headcount").text(data.total);
                    $("#TotalPages").text(data.totalPage);
                    $("#currentPage").text(page);
                    let subordinateAry = data.msg;
                    let commission = "";
                    for (let i = 0; i < subordinateAry.length; i++) {
                        commission = subordinateAry[i].money * 0.01;
                        subuserFormCount += `
                        <ul class="subordinateTable">
                                <li>${subordinateAry[i].num}</li>
                                <li>${subordinateAry[i].name}</li>
                                <li>1</li>
                                <li>${subordinateAry[i].money}</li>
                                <li>${commission}</li>
                            </ul>
                        `
                    }
                    $(".subuserForm").html(subuserFormCount);
                    pageing();
                }
                else {
                    console.log(789798)
                }

            }
        });
    }
    function pageing() {//下级好友分页
        let currentPage = $("#currentPage").text() - 0;
        let TotalPages = $("#TotalPages").text() - 0;
        if (currentPage > 1) {
            $("#upPage").hover(function () {
                $("#upPage").css({
                    "cursor": "pointer",
                    "background-color": "#3097FE"
                });
            }, function () {
                $("#upPage").css({
                    "background-color": "#343D68"
                });
            })

            $("#upPage").click(function () {
                TheSubordinateList(currentPage - 1);
            })
        }
        else if (currentPage < TotalPages) {
            $("#nextPage").hover(function () {
                $("#nextPage").css({
                    "cursor": "pointer",
                    "background-color": "#3097FE"
                });
            }, function () {
                $("#nextPage").css({
                    "background-color": "#343D68"
                });
            });
            $("#nextPage").click(function () {
                TheSubordinateList(currentPage + 1 - 0);
            })
        }
        if (currentPage == 1) {
            $("#upPage").hover(function () {
                $("#upPage").css({
                    "cursor": "auto",
                    "background-color": "#343D68"
                });
            });
            $("#upPage").unbind("click");
        } else if (currentPage == TotalPages) {
            $("#nextPage").hover(function () {
                $("#nextPage").css({
                    "cursor": "auto",
                    "background-color": "#343D68"
                });
            });
            $("#nextPage").unbind("click");
        }
    }

});
// 获取数据（参数说明：key --> 要获取的属性名）
function getStorage(key) {
    let data = sessionStorage[key];
    if (!data) {
        return [];
    }
    return JSON.parse(data);
}

// 存储数据（参数说明：key --> 要存储的属性名，data：要存储的属性值）
function saveStorage(key, data) {
    sessionStorage[key] = JSON.stringify(data);
}
function clearNoNum(obj) {//投注金额输入框的正则
    obj.value = obj.value.replace(/[^\d.]/g, "");
    obj.value = obj.value.replace(/^\./g, "");
    obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d).*$/, '$1$2.$3');
}