var UserInfo = {
    publicKey: null,//用户公钥
    privateKey: "",//用户私钥
    userCode: "",//邀请码
    jackpot: "mylotterybet",//奖池余额用户名
    contract: "eosio.token",//合约账户
    account: "",//用户名
    userName: "",
    loginType: 0
};
var RoomInfo = {
    roomId: roomNum,
    betType: 0,
    isBet: false
}
// let isBet = false;
function getUserName() {//获取用户名
    UserInfo.userName = UserInfo.account;
}

function getUserCode() {//获取IP栏邀请码
    let url = document.location.href;
    let userCode = url.split("=")[1];
    if (userCode == undefined) {
        userCode = ""
    }
    UserInfo.userCode = userCode;
}

function initUserInfo() {//初始化用户信息
    UserInfo.publicKey = null;
    UserInfo.privateKey = null;
    UserInfo.userCode = null;
    UserInfo.account = null;
    UserInfo.userName = null;
}
var self = this;
$("#defaultGameIcon").click(function () {
    $("#gameItem").toggle();
});
$("#gameItem li").click(function (event) {
    var li = event.currentTarget;
    var img = $(li).children("img")[0];

    var url_res = $(img).attr("src");
    var name = $(img).attr("alt");
    $("#gameItem").toggle();
    $("#defaultGameIcon").html(
        `<img src=${url_res} alt=${name} id="showGameIcon">`
    );
});

//登录函数接口
function loginSend(loginUserId, publicKey, privetKey, userCode, type) {
    // console.log("登录：",loginUserId);
    // console.log("登录publicKey：",publicKey);
    // console.log("登录type：",type);
    if (!type) {
        type = 0;
    }
    let data = {
        loginUserId: loginUserId,
        type: type,
        userCode: userCode
    }
    UserInfo.loginType = type;
    if (type == 1) {
        data.privateKey = privetKey;
    } else {
        data.publicKey = publicKey;
    }
    WebsocketUtil.getWebSocketWithUrl().sendData({
        code: MessageType.Up.userLogin,
        data: data
    })
}
//点击游戏帮助按钮
// $("#rluerIntroduce").click(function () {
//     console.log("玩法介绍")
//     self.show()
// });
//点击邀请好友按钮
// $("#inviteBtn").click(function () {
//     console.log("邀请好友???");
//     // updateEosData();
//     // console.log($("#kafukaNumber"))
//     // var kafukaNumber = document.getElementById("kafukaNumber");

// });

// 语言选择切换
// $("#changeLanguage").click(function () {
//     console.log(12312313)
//     $("#language").toggle();
// });
let languageCPM = 0;
$("#inputBox").click(function () {
    if(languageCPM == 0){
        $("#language").css("display", "block");
        languageCPM = 1;
    }
    else{
        $("#language").css("display", "none");
        languageCPM = 0;
    }
});
$("#Ch").click(function () {
    $("#inputBox>img:nth-of-type(1)").attr("src","../images/Singapore.png");
    $("#inputBox>span").text("简体中文");
    $("#language").css("display", "none");
    languageCPM = 0;
});
$("#En").click(function () {
    $("#inputBox>img:nth-of-type(1)").attr("src","../images/guoqi02.png");
    $("#inputBox>span").text("English");
    $("#language").css("display", "none");
    languageCPM = 0;
});


$(".dropdown").click(function(){//点击跳转至选择房间页面
    WebsocketUtil.getWebSocketWithUrl().sendData({
        code: 1112,
        data: {
            roomId: roomNum
        }
    })
    window.location.href = `../tiger/selectRoom.html`;
});



// 登录弹框内私钥登录和scatter登陆效果切换
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

$("#loginBtn").click(function () {//点击头部导航栏登录按钮
    if ($("#loginBtn").attr("data-state") == "1") {
        $("#loginContainer").css("display", "block");
    } 
    //  scatter.forgetIdentity();
});
$("#quit").click(function(){//点击导航栏退出按钮
    if (UserInfo.loginType == 1) {
        sessionStorage.setItem("privateKey", "")
    } else {
        scatter.forgetIdentity();
    }
    $("#loginBtn").text("登录");
    $("#betSure").text("登录");
    $("#loginBtn").attr("data-state", "1");
    $("#loginBtn").css("display", "block");
    $("#betSure").attr("data-state", "1")
    $("#userInfo").css("visibility", "hidden");
    initUserInfo();//初始化用户信息
    $.message("注销成功！");
    window.location.reload();
})
function privateLogin(privateKey) {//私钥登录函数，并判断私钥输入框是否输入正确
    if (privateKey) {
        if (privateKey.length == 51) {
            UserInfo.privateKey = privateKey;
            getUserCode();
            loginSend(null, UserInfo.publicKey, UserInfo.privateKey, UserInfo.userCode, 1);
            // console.log("开始登录",privateKey)
        } else {
            $.message({
                message: '请输入正确私钥!',
                type: 'warning'
            });
        }
    } else {
        $.message({
            message: '请输入私钥!',
            type: 'warning'
        });
    }
}
$("#loginComfire").click(function () {//私钥登录
    let privateKey = $("#userChainId").val();
    privateLogin(privateKey)
    // console.log("11111111111111", $("#userChainId").val())
});

// 点击scatter登录
$(".scatter_login").click(function () {
    if (!window["scatter"]) {
        $.message({
            type: "warning",
            message: "未安装scatter插件"
        })
        return
    }
    window.ScatterJS.scatter.connect("dt").then(function (connected) {
        console.log("111111", connected)
        if (!connected) return false;
        const scatter = ScatterJS.scatter;
        const requiredFields = {
            accounts: [network]
        };
        // console.log("UserInfo.contract",UserInfo.contract);
        // console.log("UserInfo.account",UserInfo.account);
        // console.log("requiredFields",requiredFields);
        scatter.getIdentity(requiredFields).then(() => {
            const requiredFields = { accounts: [network] };
            const acc = scatter.identity.accounts.find(x => x.blockchain === "eos");
            UserInfo.account = acc.name;
            UserInfo.publicKey = scatter.identity.publicKey;
            // console.log("7897", scatter.identity.publicKey)
            getUserName();
            getUserCode();
            const eosOptions = { expireInSeconds: 60 };
            const eos = scatter.eos(network, Eos, eosOptions);
            $("#userName").text(UserInfo.account);
            loginSend(UserInfo.userName, UserInfo.publicKey, UserInfo.privateKey, UserInfo.userCode, 0);//发送用户名到服务器
            /* eos.getCurrencyBalance(UserInfo.contract, UserInfo.jackpot, "EOS").then(accBalance => {//获取奖池余额
             console.log("奖池余额 :",accBalance[0]);
             $("#allPoolNum").text(accBalance[0]);
             })*/
            eos.getCurrencyBalance(UserInfo.contract, UserInfo.account, "EOS").then(accBalance => {//获取用户余额
                // console.log("获取用户余额 :",accBalance);
                $("#userInfo").css("visibility", "visible");
                $("#userEos").text(accBalance[0]);
            }).catch(function (msg) {
            });
            $("#userInfo").css("visibility", "visible");
            $("#loginBtn").attr("data-state", "2")
            $("#loginBtn").text("注销");
            $("#loginContainer").css("display", "none");
            $("#betSure").attr("data-state", "2")
            $("#betSure").text("投注");
            $.message("登录成功！");
            $("#loginBtn").css("display","none");
        }).catch(function () {
            $.message({
                message: '取消授权',
                type: 'warning'
            });
        });
    })
    // getJackpot();
});


// 点击关闭登录弹窗
$("#loginClose").click(function () {
    $("#loginContainer").css("display", "none");
});

function getJackpot() {//获取奖金池余额
    if (!window["scatter"]) {
        $.message({
            type: "warning",
            message: "未安装scatter插件"
        })
        return
    }
    window.ScatterJS.scatter.connect("dt").then(function (connected) {
        if (!connected) return false;
        const scatter = ScatterJS.scatter;
        const requiredFields = {
            accounts: [network]
        };
        scatter.getIdentity(requiredFields).then(() => {
            const requiredFields = { accounts: [network] };
            const acc = scatter.identity.accounts.find(x => x.blockchain === "eos");
            UserInfo.account = acc.name;
            const eosOptions = { expireInSeconds: 60 };
            const eos = scatter.eos(network, Eos, eosOptions);
            $("#userName").text(UserInfo.account);
            // let jackpot = "aaaaaaaaaaaa"//奖池余额用户名
            /* eos.getCurrencyBalance(UserInfo.contract, UserInfo.jackpot, "EOS").then(accBalance => {//获取奖池余额
             $("#allPoolNum").text(accBalance[0]);
             });*/
        }).catch(function () {

        });
    })
}
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
function UserBalance(callBack) {//获取用户余额
    // console.log("奖池余额 :",UserInfo.loginType);
    if (UserInfo.loginType == 1) {
        /* const eosOptions = {expireInSeconds: 60};
         const eos = scatter.eos(network, Eos, eosOptions);
         eos.getCurrencyBalance(UserInfo.contract, UserInfo.account, "EOS").then(accBalance => {//获取用户余额
             console.log("获取用户余额 :",accBalance[0]);
             $("#userInfo").css("visibility", "visible");
             $("#userEos").text(accBalance[0]);
             $("#userName").text(UserInfo.account);
             $("#loginBtn").text("注销");
             $("#loginBtn").attr("data-state", "2")
             $("#loginContainer").css("display", "none");
             $("#betSure").attr("data-state", "2")
             $("#betSure").text("投注");
         }).catch(function () {
             $.message({
                 message: '取消下注!',
                 type: 'warning'
             });
         });*/
    } else {
        if (!window["scatter"]) {
            $.message({
                type: "warning",
                message: "未安装scatter插件"
            })
            return
        }
        window.ScatterJS.scatter.connect("dt").then(function (connected) {
            if (!connected) return false;
            const scatter = ScatterJS.scatter;
            const requiredFields = {
                accounts: [network]
            };
            scatter.getIdentity(requiredFields).then(() => {
                const requiredFields = { accounts: [network] };
                const acc = scatter.identity.accounts.find(x => x.blockchain === "eos");
                UserInfo.account = acc.name;
                UserInfo.publicKey = scatter.identity.publicKey;
                getUserName();
                const eosOptions = { expireInSeconds: 60 };
                const eos = scatter.eos(network, Eos, eosOptions);
                $("#userName").text(UserInfo.account);
                // loginSend(UserInfo.userName);//发送用户名到服务器
                if (callBack) {
                    callBack();
                }
                eos.getCurrencyBalance(UserInfo.contract, UserInfo.account, "EOS").then(accBalance => {//获取用户余额
                    $("#userEos").text(accBalance[0]);
                });
                $("#userInfo").css("visibility", "visible");
                $("#loginBtn").text("注销");
                $("#betSure").text("投注");
                $("#loginBtn").attr("data-state", "2")
                $("#betSure").attr("data-state", "2")
                $("#loginContainer").css("display", "none");
                // $.message("登录成功！")
            }).catch(function () {
                $.message({
                    type: "warning",
                    message: "取消授权"
                })
            });
        })
    }
}

function checkUserState(callBack) {//用户账号状态
    $.ajax({
        type: "get",
        async: false,
        url: `https://${baseUrl}/lottery/checkUserStatus`,
        data: {
            userName: UserInfo.userName,
            gameType: "tiger"
        },
        success: function (msg) {
            console.log("用户状态：", msg)
            if (msg.status) {
                callBack();
            } else {
                $.message({
                    type: "warning",
                    message: "用户账号出现异常!"
                })
            }
        }
    })
}

function betEos(data) {//投注扣款
    if (this.isCheck) {
        return;
    }
    this.isCheck = true;
    checkUserState(() => {
        let bettingMoney = data.money;//$("#inputBox").val() - 0;
        if (UserInfo.loginType != 1) {//scatter投注
            ScatterJS.scatter.connect('dt').then(function (connected) {//投注扣款
                console.log("connected:", connected);
                if (!connected) return false;

                const scatter = ScatterJS.scatter;
                const requiredFields = { accounts: [network] };
                var accountName = "";
                if (this.currentTime <= 35) {
                    $.message({
                        message: '请等待下一局开始!',
                        type: 'warning'
                    });
                } else {
                    scatter.getIdentity(requiredFields).then(() => {
                        const account = scatter.identity.accounts.find(x => x.blockchain === 'eos');
                        // console.log(account)
                        const eosOptions = { expireInSeconds: 60 };
                        const eos = scatter.eos(network, Eos, eosOptions);
                        // const transactionOptions = { authorization: [`${account.name}@${account.authority}`] };
                        accountName = account.name;
                        var quantity = getAmountByPrecision(bettingMoney, 4) + " " + "EOS";
                        console.log("quantity", quantity);
                        
                        eos.transaction({
                            actions: [
                                {
                                    account: UserInfo.contract,
                                    name: "transfer",
                                    authorization: [{
                                        actor: account.name,
                                        permission: 'active'
                                    }],
                                    data: {
                                        from: account.name,
                                        to: UserInfo.jackpot,
                                        quantity: quantity,
                                        memo: JSON.stringify({
                                            gameType: "tiger",
                                            userName: accountName
                                        })
                                    }
                                }
                            ]
                        }).then(trx => {
                            this.isCheck = false;
                            // console.log("trx:", JSON.stringify(trx));
                            if (trx) {
                                data.txId = trx.transaction_id;
                                data.type = 0;
                                WebsocketUtil.getWebSocketWithUrl().sendData({
                                    code: MessageType.Up.bet,
                                    data: data
                                })
                            }
                        }).catch(error => {
                            $.message({
                                message: '取消下注!',
                                type: 'warning'
                            });
                            console.error(error);
                        });
                    }).catch(function () {
                        $.message({
                            message: '取消下注!',
                            type: 'warning'
                        });
                    })
                }
            });
        } else {//私钥投注
            if (!RoomInfo.isBet) {
                data.type = 1;
                data.privateKey = UserInfo.privateKey;
                RoomInfo.isBet = true;
                WebsocketUtil.getWebSocketWithUrl().sendData({
                    code: MessageType.Up.bet,
                    data: data
                })
            } else {
                $.message({
                    type: "warning",
                    message: "正在下注！"
                });
            }
        }
    })
    // }
}