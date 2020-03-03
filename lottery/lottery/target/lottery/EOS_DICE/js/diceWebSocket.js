// ip = "";
ipAdd();
// let ws = new WebSocket(`ws://172.16.1.47/lottery/ws?userId=&gameType=dice`);
userName = getStorage("userName");
console.log("用户名初始化",userName);
let ws = "";
if(userName.length == 0){
    console.log(123)
    ws = new WebSocket(`wss://myeosgame.com/ws?userId=&gameType=dice`);
}
else{
    console.log(456)
    ws = new WebSocket(`wss://myeosgame.com/ws?userId=${userName}&gameType=dice`);
}

function loginSend(userName, key, code) {//scater登录向服务器发送的参数
    let obj = {
        code: 1110,
        data: {
            loginUserId: userName,
            publicKey: key,
            userCode: code,
            type: 0
        }
    }
    let stringObj = JSON.stringify(obj);
    ws.send(stringObj);
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
let userBettingPeriods = "";
function bettingPeriods(bettingPeriods) {//获取投注期数
    userBettingPeriods = bettingPeriods;
}
let jackpotBalance = "";
$(function () {
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
    let TheWinningBetsAry = [];

    // 建立 web socket 连接成功触发事件
    ws.onopen = function () {
        // 使用 send() 方法发送数据
        // alert("数据发送中...");

        let loginType = getStorage("loginType");
       

        if (loginType == 2) {
            userName = getStorage("userName");
            $.ajax({
                type: "get",
                url: `${ip}/account/getAccount.do`,
                data: {
                    account: userName
                },
                success: function (msg) {
                    console.log(msg)
                    type = 1;
                    name = userName;
                    userName = userName;
                    $("#cpu").text(`${parseFloat((msg.cpu.used / msg.cpu.max) * 100).toFixed(2)}%`);
                    $("#userChainId").val("");
                    $("#userName").text(userName);
                    $("#loginContainer").css("display", "none");
                    $(".headerLoginBtn").css("display", "none");
                    $(".userinfo").css("display", "block");
                    popup({ type: 'success', msg: "登录成功！", delay: 1000 });
                    Copy();//邀请好友复制到剪切板
                    $("#loginBtn").text("投注");
                    $("#loginBtn").attr("data-state", "2");
                    accountStatus(userName);
                    $("#userBalance").text(msg.balance);
                }
            });
        }
        else if (loginType == 1) {
            // let loginUserName = getStorage("userName");
            let loginUserName = scatter.identity.accounts[0].name;
            window.ScatterJS.scatter.connect("dice").then(function (connected) {
                if (!connected) return false;
                const scatter = ScatterJS.scatter;
                const requiredFields = {
                    accounts: [network]
                };
                scatter.getIdentity(requiredFields).then(() => {
                    const requiredFields = { accounts: [network] };
                    const acc = scatter.identity.accounts.find(x => x.blockchain === "eos");
                    account = acc.name;
                    getUserName();
                    const eosOptions = { expireInSeconds: 60 };
                    const eos = scatter.eos(network, Eos, eosOptions);
                    userName = account;
                    name = userName;
                    loginSend(userName, scatter.identity.publicKey, loginUserName);//发送用户名到服务器
                    $("#userName").text(account);
                    
                    eos.getCurrencyBalance(contract, jackpot, "EOS").then(accBalance => {//获取奖池余额
                        // $("#jackpotSum").text(accBalance[0]);
                        jackpotBalance = accBalance[0];
                    })
                    eos.getCurrencyBalance(contract, account, "EOS").then(accBalance => {//获取用户余额
                        $("#userBalance").text(accBalance[0]);

                    });
                    $("#loginContainer").css("display", "none");
                    $(".headerLoginBtn").css("display", "none");
                    $(".userinfo").css("display", "block");
                    popup({ type: 'success', msg: `${dice93}`, delay: 1000 });
                    Copy();//邀请好友复制到剪切板
                    TheSubordinateList(1);//获取下级列表和页码
                    $("#loginBtn").text(`${dice94}`);
                    $("#loginBtn").attr("data-state", "2");
                    type = 0;
                    // saveStorage("publicKey",scatter.identity.publicKey);
                    // saveStorage("loginType",1);
                    accountStatus(account);
                });
            })
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
            if (!hovering) {
                theLotteryList();
            }
            else {
                HoverRecord();
            }
        }
        else if (received_msg.from == "diceUserPrize") {//发送给某个用户
            iBetThe();
            if (type == 0) {
                UserBalance();//获取用户余额
            }
        }
        else if (received_msg.from == "diceAllPrize") {//中奖展示
            allPrizeRecord();
        }
        else if (received_msg.code == 10) {
            let wrongPassword = JSON.parse(received_msg.data);
            if (wrongPassword.err == 1) {//私钥输入错误提示
                popup({ type: 'tip', msg: `${dice95}`, delay: 1000 });
                $("#userChainId").val("")
            }
        }
        else if (received_msg.code == 10) {
            let wrongPassword = JSON.parse(received_msg.data);
            if (wrongPassword.err == 7) {//发奖失败错误提示
                popup({ type: 'tip', msg: `${dice119}`, delay: 1000 });
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
            if (userinfo.type == 1) {
                type = userinfo.type;
                name = userinfo.loginUserId;
                $("#userChainId").val("");
                $("#userName").text(userinfo.loginUserId);
                $("#loginContainer").css("display", "none");
                $(".headerLoginBtn").css("display", "none");
                $(".userinfo").css("display", "block");
                popup({ type: 'success', msg: `${dice93}`, delay: 1000 });
                Copy();//邀请好友复制到剪切板
                $("#loginBtn").text(`${dice94}`);
                $("#loginBtn").attr("data-state", "2");
                saveStorage("userName",name);
                saveStorage("loginType",2);
                accountStatus(name);
            }
        }
        else if (received_msg.code == 1125) {//用户余额
            let userinfo = JSON.parse(received_msg.data);
            console.log("用户余额：",userinfo)
            $("#userBalance").text(userinfo.balance);

        }
        else if (received_msg.code == 1201) {//奖池余额
            let userinfo = JSON.parse(received_msg.data);
            // $("#jackpotSum").text(userinfo.balance);
            jackpotBalance = userinfo.balance;
        }
        newPeriods(text_msg.block_num);//调用函数把开奖期数传递到theDiceHomeJackpot.js
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
    //鼠标悬停在开奖滚动栏
    $("#carousel").hover(function () {
        hovering = true;
        $("#carousel").on("mouseenter", ".lotteryTableTr", function () {
            $(".lotteryTableTr").css("border", "none");
            $(this).css({
                "cursor": "pointer",
                "border": "1px solid #3097ff"
            })
        });
        $("#carousel").on("click", ".lotteryTableTr", function () {
            let hash = $(this).attr("data-num");
            window.open(`https://bloks.io/transaction/${hash}`);
        })
    }, function () {

        hovering = false;
    });

    // 最新开奖结果
    function theLotteryResults() {

        text_msg = JSON.parse(received_msg.text);
        // 区块时间
        dateAndTime = text_msg.header.timestamp;
        // time = dateAndTime.split("T");
        time = dateAndTime;
        $("#newTime").text(time);
        // 区块链号
        id = text_msg.id;

        id = id.split('').reverse();
        let content = "";
        let count = 0;
        let num = "";
        for (let i = 0; i < 41; i++) {//判断中奖号
            if (count < 2) {
                if (!isNaN(id[i])) {
                    count++;
                    num += id[i]
                    content += `<span style="color: #FF564F">${id[i]}</span>`;

                } else {
                    content += `<span>${id[i]}</span>`
                }
            }
            else {
                content += `<span>${id[i]}</span>`
            }

        }
        num = num.split('').reverse().join("");
        $("#newWinningNumbers").text(num);
        $("#winningNum").text(num);
        $("#newBlockChainNo").html(content);

        // 最新开奖期数
        $("#newPeriods").text(text_msg.block_num);
        // 最新开奖期数的大小对子显示
        number = num.split("");
        if (number[0] == number[1]) {
            $("#newPair").css("color", "#3097ff");
            $("#newBig").css("color", "#9eaab5");
            $("#newSmall").css("color", "#9eaab5");

        } else {
            number = parseInt(number.join(""));
            if (number >= 50) {
                $("#newBig").css("color", "#3097ff");
                $("#newSmall").css("color", "#9eaab5");
                $("#newPair").css("color", "#9eaab5");
            } else {
                $("#newSmall").css("color", "#3097ff");
                $("#newBig").css("color", "#9eaab5");
                $("#newPair").css("color", "#9eaab5");
            }
        }

    }
    function HoverRecord() {//鼠标悬停时，推送的数据
        let content = "";
        let count = 0;
        let num = "";
        let bigColor = "";
        let smallColor = "";
        let pairColor = "";
        let blockId = id.join("");
        blockId = blockId.split('').reverse().join('');
        for (let i = 0; i < 41; i++) {//判断中奖号
            if (count < 2) {
                if (!isNaN(id[i])) {
                    count++;
                    num += id[i]
                    content += `<span style="color: #FF564F">${id[i]}</span>`;

                } else {
                    content += `<span>${id[i]}</span>`
                }
            }
            else {
                content += `<span>${id[i]}</span>`
            }

        }
        num = num.split('').reverse().join("");
        number = num.split("");
        if (number[0] == number[1]) {
            pairColor = "#3097ff";
            smallColor = "#9eaab5";
            bigColor = "#9eaab5";
        } else {
            number = parseInt(number.join(""));
            if (number >= 50) {
                pairColor = "#9eaab5";
                smallColor = "#9eaab5";
                bigColor = "#3097ff";
            } else {
                pairColor = "#9eaab5";
                smallColor = "#3097ff";
                bigColor = "#9eaab5";
            }
        }
        let carouselObj = {
            "periods": text_msg.block_num,
            "blockChainNo": content,
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

    }
    // 开奖结果表格内容
    function theLotteryList() {
        let content = "";
        let count = 0;
        let num = "";
        let bigColor = "";
        let smallColor = "";
        let pairColor = "";
        let blockId = id.join("");
        blockId = blockId.split('').reverse().join('');
        for (let i = 0; i < 41; i++) {//判断中奖号
            if (count < 2) {
                if (!isNaN(id[i])) {
                    count++;
                    num += id[i]
                    content += `<span style="color: #FF564F">${id[i]}</span>`;

                } else {
                    content += `<span>${id[i]}</span>`
                }
            }
            else {
                content += `<span>${id[i]}</span>`
            }

        }
        num = num.split('').reverse().join("");
        number = num.split("");
        if (number[0] == number[1]) {
            pairColor = "#3097ff";
            smallColor = "#9eaab5";
            bigColor = "#9eaab5";
        } else {
            number = parseInt(number.join(""));
            if (number >= 50) {
                pairColor = "#9eaab5";
                smallColor = "#9eaab5";
                bigColor = "#3097ff";
            } else {
                pairColor = "#9eaab5";
                smallColor = "#3097ff";
                bigColor = "#9eaab5";
            }
        }
        let carouselObj = {
            "periods": text_msg.block_num,
            "blockChainNo": content,
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
        let timer = userBettingPeriods + 2 - 0;
        for (let item of carouselAry) {
            if (item.periods == userBettingPeriods) {
                carouselContent += `
                <ul class="lotteryTableTr" style="background:#313862" data-num=${item.periods}>
                            <li>${item.periods}</li>
                            <li>
                                <p>...</p>
                                <p>${item.blockChainNo}</p>
                            </li>
                            <li>${item.winningNumbers}</li>
                            <li>${item.theLotteryTime}</li>
                            <li>
                                <span style="color:${item.bigColor}">${dice3}</span>
                                <span>|</span>
                                <span style="color: ${item.smallColor}">${dice4}</span>
                                <span>|</span>
                                <span style="color: ${item.pairColor}">${dice5}</span>
                            </li>
                        </ul>
                `

            }
            else if (item.periods == buyIn) {
                carouselContent += `
                <ul class="lotteryTableTr" style="background:#316240" data-num=${item.periods}>
                            <li>${item.periods}</li>
                            <li>
                                <p>...</p>
                                <p>${item.blockChainNo}</p>
                            </li>
                            <li>${item.winningNumbers}</li>
                            <li>${item.theLotteryTime}</li>
                            <li>
                                <span style="color:${item.bigColor}">${dice3}</span>
                                <span>|</span>
                                <span style="color: ${item.smallColor}">${dice4}</span>
                                <span>|</span>
                                <span style="color: ${item.pairColor}">${dice5}</span>
                            </li>
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
            <ul class="lotteryTableTr" data-num=${item.periods}>
                        <li>${item.periods}</li>
                        <li>
                            <p>...</p>
                            <p>${item.blockChainNo}</p>
                        </li>
                        <li>${item.winningNumbers}</li>
                        <li>${item.theLotteryTime}</li>
                        <li>
                            <span style="color:${item.bigColor}">${dice3}</span>
                            <span>|</span>
                            <span style="color: ${item.smallColor}">${dice4}</span>
                            <span>|</span>
                            <span style="color: ${item.pairColor}">${dice5}</span>
                        </li>
                    </ul>
            `
            }

        }
        $("#carousel").html(carouselContent);
        $("#carousel>ul:nth-of-type(1)").animate({ marginTop: "40px" });
    }
    //我的投注表格内容
    function iBetThe() {
        $("#loginBtn").attr("data-state", "2");
        DevoteTheResults = JSON.parse(received_msg.text);
        console.log("开奖结果：",DevoteTheResults);
        let myType = "";//我的投注内容
        let myResult = "";//中奖结果
        let myMoney = "";//奖金
        let myMoneyStyle = "";//金额样式
        let myWinStyle = "";//中奖样式
        let time = "";//时间
        let dateTime = "";
        if (DevoteTheResults.type == 1) {
            myType = DevoteTheResults.prizenumber + "【" + ">" + DevoteTheResults.forecast + "】";
        }
        else if (DevoteTheResults.type == 2) {
            myType = DevoteTheResults.prizenumber + "【" + "<" + DevoteTheResults.forecast + "】";
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
            myResult = "——";
            myMoney = "-" + DevoteTheResults.bettingEOS;
            myMoneyStyle = "#3097ff";
            popup({ type: 'tip', msg: `${dice96}`, delay: 1000 });
        } else {
            myResult = `${dice99}`;
            // myWinStyle = "myWinStyle";
            myMoney = "+" + DevoteTheResults.prizeEOS;
            myMoneyStyle = "#BF4B56";
            popup({ type: 'success', msg: `${dice97}${DevoteTheResults.prizeEOS}`, delay: 1000 });
        }
        time = new Date(DevoteTheResults.time.time);
        let year = time.toLocaleDateString();
        let hours = time.getHours();
        let minutes = time.getMinutes();
        let seconds = time.getSeconds();
        if (minutes.toString().length == 1) {
            minutes = "0" + minutes;
        }
        if (seconds.toString().length == 1) {
            seconds = "0" + seconds;
        }
        dateTime = year + " " + hours + ":" + minutes + ":" + seconds;
        let iBetTheContent = "";
        iBetTheContent = `
        <ul class="devoteTheResults" data-CPMIssue=${DevoteTheResults.termnumber} data-CPMhash=${DevoteTheResults.hash} data-CPMtime=${DevoteTheResults.time.time}  data-CPMnum=${DevoteTheResults.prizenumber}>
                        <li>${dateTime}</li>
                        <li title=${dice126}>${DevoteTheResults.termnumber}</li>
                        <li>${myType}</li>
                        <li>${DevoteTheResults.bettingEOS}</li>
                        <li>${myResult}</li>
                        <li  style="color: ${myMoneyStyle}">${myMoney}</li>
                    </ul>
        `
        $("#iBetThe").prepend(iBetTheContent);


    }
    //中奖投注表格内容
    function allPrizeRecord() {
        $("#allPrizeTable").html("");
        allPrize = JSON.parse(received_msg.text);
        let allPrizeRecordContent = "";//中奖投注信息
        let myType = "";//中奖投注内容
        let time = "";//中奖投注时间
        let dateTime = "";
        if (TheWinningBetsAry.length < 6) {
            TheWinningBetsAry.unshift(allPrize);
            for (let item of TheWinningBetsAry) {
                time = new Date(item.time.time);
                dateTime = time.toLocaleString();
                if (item.type == 1) {
                    myType = item.prizenumber + "【" + ">" + item.forecast + "】";
                }
                else if (item.type == 2) {
                    myType = item.prizenumber + "【" + "<" + item.forecast + "】";
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
                <ul class="theWinningBetsTable" data-CPMIssue1=${item.termnumber} data-CPMhash1=${item.hash} data-CPMtime1=${item.time.time} data-CPMnum1=${item.prizenumber}>
                                <li>${dateTime}</li>
                                <li title=${dice126}>${item.termnumber}</li>
                                <li>${item.account}</li>
                                <li>${myType}</li>
                                <li>${item.bettingEOS}</li>
                                <li>${item.odds}</li>
                                <li style="color: #BF4B56">+${item.prizeEOS}</li>
                            </ul>
                `

            }
        } else {
            TheWinningBetsAry.unshift(allPrize);
            TheWinningBetsAry.pop();
            for (let item of TheWinningBetsAry) {
                time = new Date(item.time.time);
                dateTime = time.toLocaleString();
                if (item.type == 1) {
                    myType = item.prizenumber + "【" + ">" + item.forecast + "】";
                }
                else if (item.type == 2) {
                    myType = item.prizenumber + "【" + "<" + item.forecast + "】";
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
                <ul class="theWinningBetsTable" data-CPMIssue1=${item.termnumber} data-CPMhash1=${item.hash} data-CPMtime1=${item.time.time} data-CPMnum1=${item.prizenumber}>
                                <li>${dateTime}</li>
                                <li title=${dice126}>${item.termnumber}</li>
                                <li>${item.account}</li>
                                <li>${myType}</li>
                                <li>${item.bettingEOS}</li>
                                <li>${item.odds}x</li>
                                <li style="color: #BF4B56">+${item.prizeEOS}</li>
                            </ul>
                `

            }

        }
        $("#allPrizeTable").html(allPrizeRecordContent);
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