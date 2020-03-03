let theLlotteryIssue = "";//开奖期数
let status = "";//用户账号状态
let accountInformation = "";//用户账号提示信息
let sendTime = "";

//currentTime();
console.log(sendTime);
function newPeriods(peridos) {
    theLlotteryIssue = peridos;
}
let ip = "";
ipAdd();
function ipAdd() {
    // ip = "http://172.16.1.47/lottery";
    ip = "https://myeosgame.com";
}
//// 登录、账号管理
////--------------
//const network = {
//    blockchain: "eos",
//    protocol: "http",
//    host: "47.91.208.237",
//    port: 8888,
//    chainId: "cf057bbfb72640471fd910bcb67639c22df9f92470936cddc1ade0e2f2e7dc4f"
//};
//let jackpot = "151515151515"//奖池余额用户名
////--------------


//正式链登录、账号管理
//--------------
const network = {
    blockchain: "eos",
    protocol: "https",
    host: "api.eosbeijing.one",
    port: 443,
    chainId: "aca376f206b8fc25a6ed44dbdc66547c36c6c33e3a119ffbeaef943642f0e906"
};
let jackpot = "mylotterybet"//奖池余额用户名
//--------------



// 外网测试登录、账号管理
//--------------
//const network = {
//    blockchain: "eos",
//    protocol: "https",
//    host: "myeosgame.com",
//    port: 8443,
//    chainId: "cf057bbfb72640471fd910bcb67639c22df9f92470936cddc1ade0e2f2e7dc4f"
//};
//let jackpot = "151515151515"//奖池余额用户名
//--------------



let contract = "eosio.token";//合约账户
let account = "";//用户名
let userName = "";

function getUserName() {//获取用户名
    userName = getStorage("userName");
}
let name = "";

let type = 0;//用户登录方式，0：scatter登录；1：私钥登录。
let url = document.location.href;
// url = "http://172.16.1.29/lottery/?userCode=133333111112";
let userCode = url.split("=")[1];
if (userCode == undefined) {
    userCode = ""
}
else {
    userCode = url.split("=")[1];
}
console.log("userCode", userCode);
let privateKey = "";//用户私钥

$(function () {


    // 奖池投注区域
    let bettingType = 0;//投注类型
    let overUnder = 1;//判断是大于还是小于，默认1为大于，2为小于
    $(".selectBet").hover(function () {
        $(this).css({
            "background-color": "#613522",
            "border": "1px solid #FF564F",
            "box-sizing": "border-box",
            "cursor": "pointer"
        });
    }, function () {
        $(".selectBet").css({
            "background-color": "#282E50",
            "border": "1px solid #282E50"
        });
    });
    $("#selectSize").click(function () {
        $(".SelectTypeArea").css("display", "none");
        $(".CrapArea").css("display", "block");
        $("#selectSize").css({
            "color": "#3097ff",
            "border-bottom": "1px solid #3097ff"
        });
        $("#selectInterva").css({
            "color": "#ffffff",
            "border-bottom": "none"
        });
        $(".selectBet").css("background", "#282E50");
        bettingType = 0;

    });
    $("#selectInterva").click(function () {
        $(".SelectTypeArea").css("display", "block");
        $(".CrapArea").css("display", "none");
        $("#selectInterva").css({
            "color": "#3097ff",
            "border-bottom": "1px solid #3097ff"
        });
        $("#selectSize").css({
            "color": "#ffffff",
            "border-bottom": "none"
        });
        $(".selectBet").css("background", "#282E50");
        bettingType = 0;
        if (overUnder == 1) {
            greaterThan();//选择猜区间大于
        }
        else if (overUnder == 2) {
            lessThan();//猜区间小于
        }
    });
    greaterThan();//默认选择猜区间大于
    // 猜区间大小区域
    $(".selectBet").click(function () {
        $(".selectBet").css("background", "#282E50");
        $(this).css({
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)",
            "border": "none"
        });
        //猜区间区域点击大的功能  
        if ($(this).attr("data-id") == 1) { //猜区间大于
            greaterThan();//选择猜区间大于
        } else if ($(this).attr("data-id") == 2) {//小于
            lessThan();//猜区间小于
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
            $("#range1").attr("disabled", true);
            $("#range1").css("opacity", "0.4");
        }
    });
    $("#b").text(50);
    // 投注金额区域

    $("#divide").hover(function () {
        $(this).css({
            "cursor": "pointer",
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)"
        });
    }, function () {
        $("#divide").css("background", "#282E50");
    });
    $("#ride").hover(function () {
        $(this).css({
            "cursor": "pointer",
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)"
        });
    }, function () {
        $("#ride").css("background", "#282E50");
    });
    $("#maximum").hover(function () {
        $(this).css({
            "cursor": "pointer",
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)"
        });
    }, function () {
        $("#maximum").css("background", "#282E50");
    });
    $("#maximum").click(function () {
        $("#inputBox").val(100);
        commissions();
    });
    $("#ride").click(function () {
        let value = $("#inputBox").val() - 0;
        let sum = value * 2;
        $("#inputBox").val(sum);
        if (sum >= 100) {
            $("#inputBox").val(100);
        }
        commissions();
    });
    $("#divide").click(function () {
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
        } else if (inputBoxValue == 0) {
            $("#inputBox").val(0.1);
            inputBoxValue = 0.1;
        }

        let odds = $("#odds").text() - 0;
        let bonus = (inputBoxValue * odds).toFixed(3) - 0;
        $("#bonus").text(bonus);
    };
    // 导航栏退出
    $("#navQuit").hover(function () {
        $(this).css({ "cursor": "pointer" });
    });
    $("#navQuit").click(function () {
        popup({ type: 'success', msg: `${dice101}`, delay: 1000 });
        $("#loginBtn").text("登录");
        $("#loginBtn").attr("data-state", "1");
        $(".userinfo").css("display", "none");
        $(".headerLoginBtn").css("display", "block");
        $("#inviteInput").val(`${dice98}`);
        $("#iBetThe").html("");
        if (type == 0) {
            scatter.forgetIdentity();
        }
        sessionStorage.removeItem('userName');
        sessionStorage.removeItem('loginType');
        sessionStorage.removeItem('publicKey');
        window.location.reload();
    })
    //登录
    $("#navLoginBtn").hover(function () {
        $(this).css({ "cursor": "pointer" });
    });
    $("#navLoginBtn").click(function () {
        $("#loginContainer").css("display", "block");
    })
    $("#loginBtn").hover(function () {
        $(this).css({ "cursor": "pointer" });
    });

    $("#loginBtn").click(function () {

        if ($("#loginBtn").attr("data-state") == 2) {
            Copy();//邀请好友复制到剪切板
            $.ajax({//请求cpu
                type: "get",
                url: `${ip}/account/getAccount.do`,
                async: false,
                data: {
                    account: userName
                },
                success: function (data) {
                    console.log(data)
                    let cpunum = parseFloat((data.cpu.used / data.cpu.max) * 100).toFixed(2);
                    $("#cpu").text(cpunum + `%`);
                    if (cpunum >= 100) {
                        popup({ type: 'tip', msg: `${cpuerror}`, delay: 1000 });
                    }
                    else {
                        console.log(123123)
                        if (type == 0) {
                            if (bettingType == 0) {
                                popup({ type: 'tip', msg: `${dice102}`, delay: 1000 });
                            }
                            else {
                                if (status) {
                                    let userMoney = $("#userBalance").text();//用户余额
                                    userMoney = userMoney.split(" ");
                                    let betMoney = $("#inputBox").val() - 0;//投注金额
                                    let jackpotMoney = jackpotBalance;//奖池金额
                                    jackpotMoney = jackpotMoney.split(" ");
                                    jackpotMoney = parseFloat(jackpotMoney[0]);
                                    jackpotMoney = jackpotMoney * 0.05;
                                    if (betMoney > userMoney[0]) {
                                        popup({ type: 'tip', msg: `${dice103}`, delay: 1000 });
                                    }
                                    else if (betMoney > jackpotMoney) {
                                        popup({ type: 'tip', msg: `${dice104}`, delay: 1000 });
                                    }
                                    else {
                                        $("#mask").css("display", "block");


                                        let bettingMoney = $("#inputBox").val() - 0;

                                        ScatterJS.scatter.connect('dice').then(function (connected) {//投注扣款
                                            if (!connected) return false;
                                            const scatter = ScatterJS.scatter;
                                            const requiredFields = { accounts: [network] };
                                            var accountName = "";

                                            scatter.getIdentity(requiredFields).then(() => {
                                                const account = scatter.identity.accounts.find(x => x.blockchain === 'eos');
                                                const eosOptions = { expireInSeconds: 60 };
                                                const eos = scatter.eos(network, Eos, eosOptions);
                                                const transactionOptions = { authorization: [`${account.name}@${account.authority}`] };
                                                accountName = account.name;
                                                var quantity = getAmountByPrecision(bettingMoney, 4) + " " + "EOS";
                                                let typeOfPlay = {
                                                    gameType: "dice",
                                                    userName: account.name
                                                };
                                                typeOfPlay = JSON.stringify(typeOfPlay);
                                                // $("#mask").css("display", "none");
                                                eos.transaction({
                                                    actions: [
                                                        {
                                                            account: contract,
                                                            name: "transfer",
                                                            authorization: [{
                                                                actor: account.name,
                                                                permission: 'active'
                                                            }],
                                                            data: {
                                                                from: account.name,
                                                                to: jackpot,
                                                                quantity: quantity,
                                                                memo: typeOfPlay
                                                            }
                                                        }
                                                    ]
                                                }).then(trx => {
                                                    if (trx) {
                                                        $.ajax({//投注请求
                                                            type: "post",
                                                            url: `${ip}/dice/getDiceBetting`,
                                                            async: false,
                                                            data: {
                                                                account: userName,
                                                                type: bettingType,
                                                                termnumber: theLlotteryIssue,
                                                                betTime: sendTime,
                                                                forecast: $("#range1").val(),
                                                                bettingEOS: bettingMoney,
                                                                transaction_id: trx.transaction_id,
                                                                isPrivate: 0//投注时传送登录方式，0：scatter登录；1：私钥登录
                                                            },
                                                            success: function (data) {
                                                                $("#mask").css("display", "none");
                                                                $("#loginBtn").attr("data-state", "3");
                                                                bettingPeriods(data.termnumber);//把投注期数传到diceWebSocket.js
                                                                // countDown();//开奖倒计时
                                                                trx.accountName = accountName;
                                                                eos.getCurrencyBalance(contract, trx.accountName, "EOS").then(accBalance => {//获取用户余额
                                                                    $("#userBalance").text(accBalance[0])
                                                                });
                                                                eos.getCurrencyBalance(contract, jackpot, "EOS").then(accBalance => {//获取奖池余额
                                                                    //  $("#jackpotSum").text(accBalance[0]);
                                                                    jackpotBalance = accBalance[0];
                                                                });
                                                                popup({ type: 'success', msg: `${dice105}`, delay: 1000 });
                                                            }
                                                        })

                                                    }
                                                }).catch(error => {
                                                    popup({ type: 'tip', msg: `${dice106}`, delay: 1000 });
                                                    $("#mask").css("display", "none");
                                                });
                                            })
                                        });
                                    }
                                }
                                else {
                                    popup({ type: 'tip', msg: accountInformation, delay: 1000 });
                                }

                            }
                        }
                        else if (type == 1) {
                            if (status) {
                                let userMoney = $("#userBalance").text();//用户余额
                                userMoney = userMoney.split(" ");
                                let betMoney = $("#inputBox").val() - 0;//投注金额
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
                                    //                                     currentTime();

                                    console.log("投注用户名",name);
                                    console.log("投注账号登录方式",bettingType);
                                    console.log("投注期号",theLlotteryIssue);
                                    console.log("投注时间",sendTime);
                                    console.log("投注预测数",$("#range1").val());
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
                                            forecast: $("#range1").val(),
                                            bettingEOS: bettingMoney,
                                            transaction_id: "",
                                            isPrivate: 1//投注时传送登录方式，0：scatter登录；1：私钥登录
                                        },
                                        success: function (data) {
                                            if (data.err) {
                                                popup({ type: 'tip', msg: data.err, delay: 1000 });
                                            }
                                            else {
                                                console.log("投注结果",data)
                                                popup({ type: 'success', msg: data.msg, delay: 1000 });
                                                $("#userBalance").text(data.balance);
                                                bettingPeriods(data.termnumber);//把投注期数传到diceWebSocket.js
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
        else if ($("#loginBtn").attr("data-state") == 3) {
            popup({ type: 'tip', msg: `${dice107}`, delay: 1000 });
        }
        else {
            $("#loginContainer").css("display", "block");
            $("#inviteInput").val(`${dice98}`);
        }

    });


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

    // 点击登录
    $(".scatter_login").click(function () {//scatter登录

        if (!window["scatter"]) {
            popup({ type: 'tip', msg: `${dice108}`, delay: 1000 });
            return
        }
        window.ScatterJS.scatter.connect("My-App").then(function (connected) {
            if (!connected) return false;
            const scatter = ScatterJS.scatter;
            const requiredFields = {
                accounts: [network]
            };
            scatter.getIdentity(requiredFields).then(() => {
                const requiredFields = { accounts: [network] };
                console.log(scatter)
                const acc = scatter.identity.accounts.find(x => x.blockchain === "eos");
                account = acc.name;
                getUserName();
                const eosOptions = { expireInSeconds: 60 };
                const eos = scatter.eos(network, Eos, eosOptions);


                eos.getCurrencyBalance(contract, jackpot, "EOS").then(accBalance => {//获取奖池余额
                    // $("#jackpotSum").text(accBalance[0]);
                    jackpotBalance = accBalance[0];
                })
                eos.getCurrencyBalance(contract, account, "EOS").then(accBalance => {//获取用户余额
                    $("#userBalance").text(accBalance[0]);

                });
                loginSend(userName, scatter.identity.publicKey, userCode);//发送用户名到服务器
                $("#userName").text(account);
                $("#loginContainer").css("display", "none");
                $(".headerLoginBtn").css("display", "none");
                $(".userinfo").css("display", "block");
                popup({ type: 'success', msg: `${dice93}`, delay: 1000 });
                Copy();//邀请好友复制到剪切板
                TheSubordinateList(1);//获取下级列表和页码
                $("#loginBtn").text(`${dice94}`);
                $("#loginBtn").attr("data-state", "2");
                type = 0;
                saveStorage("publicKey", scatter.identity.publicKey);
                saveStorage("userName", account);
                saveStorage("loginType", 1);
                accountStatus(account);
            });
        })

    });

    // 私钥登录
    $("#loginComfire").click(function () {
        privateKey = $("#userChainId").val();
        privateKeyLogin(privateKey, userCode);
    })


    // 点击关闭
    $("#loginClose").click(function () {
        $("#loginContainer").css("display", "none");
    });

    function greaterThan() {//猜区间选择大于
        $("#greaterThan").css({
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)"
        });
        $("#sizeIn").text(`${dice6}`);
        bettingType = 1;
        overUnder = 1;
        if ($("#range1").val() <= 1) {
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


    function lessThan() {//猜区间小于
        $("#lessThan").css({
            "background": "-webkit-linear-gradient(left, #FF564F , #FF8523)",
            "background": "-o-linear-gradient(right, #FF564F, #FF8523)",
            "background": "-moz-linear-gradient(right, #FF564F, #FF8523)",
            "background": "linear-gradient(to right, #FF564F , #FF8523)"
        });
        $("#sizeIn").text(`${dice7}`);
        bettingType = 2;
        overUnder = 2;
        if ($("#range1").val() >= 98) {
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


});

function getAmountByPrecision(amt, precision) {
    let amtFloat = "0.0000";
    let afArr = (amt + "").split(".");
    if (afArr.length == 1) {
        amtFloat = amt + ".0000";
    } else {
        let inte = afArr[0];
        let dec = afArr[1];
        if (dec.length >= precision) {
            dec = dec.substr(0, precision);
        } else {
            let len = dec.length;
            for (let i = 0; i < (precision - len); i++) {
                dec += "0";
            }
        }
        amtFloat = inte + "." + dec;
    }
    return amtFloat;
}
function UserBalance() {//获取用户余额
    window.ScatterJS.scatter.connect("My-App").then(function (connected) {
        if (!connected) return false;
        const scatter = ScatterJS.scatter;
        const requiredFields = {
            accounts: [network]
        };
        scatter.getIdentity(requiredFields).then(() => {
            const requiredFields = { accounts: [network] };
            const acc = scatter.identity.accounts.find(x => x.blockchain === "eos");
            account = acc.name;
            const eosOptions = { expireInSeconds: 60 };
            const eos = scatter.eos(network, Eos, eosOptions);
            $("#userName").text(account);
            // let jackpot = "aaaaaaaaaaaa"//奖池余额用户名
            eos.getCurrencyBalance(contract, jackpot, "EOS").then(accBalance => {//获取奖池余额
                // $("#jackpotSum").text(accBalance[0]);
                jackpotBalance = accBalance[0];
            })
            eos.getCurrencyBalance(contract, account, "EOS").then(accBalance => {//获取用户余额
                $("#userBalance").text(accBalance[0]);
            })
        });
    })
}


function clearNoNum(obj) {//投注金额输入框的正则
    obj.value = obj.value.replace(/[^\d.]/g, "");
    obj.value = obj.value.replace(/^\./g, "");
    obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d).*$/, '$1$2.$3');
}
function accountStatus(name) {//用户账号状态
    let loginType = getStorage("loginType");
    $.ajax({//投注请求
        type: "get",
        url: `${ip}/lottery/checkUserStatus`,
        async: false,
        data: {
            userName: name,
            gameType: "dice",
            loginType: loginType
        },
        success: function (data) {
            console.log("用户账号状态", data)
            status = data.status;
            accountInformation = data.msg;
        }
    })
}