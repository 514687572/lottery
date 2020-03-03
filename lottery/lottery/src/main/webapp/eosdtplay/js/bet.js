/**
 * Created by admin on 2018/11/16.
 */
const ENUM_BET_TYPE = {
    betLongSingle: 1,
    betLongDouble: 2,
    betHuSingle: 3,
    betHuDouble: 4,
    betLong: 5,
    betHe: 6,
    betHu: 7
};
const BET_NAME = {
    1: "龙单",
    2: "龙双",
    3: "虎单",
    4: "虎双",
    5: "龙",
    6: "和",
    7: "虎"
}
const ENUM_BET_TYPE_ID = {
    1: "betLongSingle",
    2: "betLongDouble",
    3: "betHuSingle",
    4: "betHuDouble",
    5: "betLong",
    6: "betHe",
    7: "betHu"
};
const DEFAULT_INIT_BET_NUM = 0.1;
function BetObj() {
    this.selectType = 0;//1龙单2龙双3虎单4虎双5龙6和7虎
    this.selectBetNum = 0;
    this._websocket = null;
    this._betState = 0;
    this._allTime = 60;
    this._roomId = 0;
    this.road_arr = [];
    this.initUI = () => {
        $("#betEditBox").val(DEFAULT_INIT_BET_NUM);
        this.bindEvent();
        // this.updateBetTime();
        this.initBet();
        WebsocketUtil.getWebSocketWithUrl();
        WebsocketUtil.addWSEventListener(KEY_EVENT_OPEN, this.onSocketOpen.bind(this), "bet");
        WebsocketUtil.addWSEventListener(KEY_EVENT_MSG, this.onSocketMsg.bind(this), "bet");
        WebsocketUtil.addWSEventListener(KEY_EVENT_ERROR, this.onSocketError.bind(this), "bet");
        WebsocketUtil.addWSEventListener(KEY_EVENT_CLOSE, this.onSocketClose.bind(this), "bet")
    };

    this.onSocketOpen = (data) => {//建立webSocket连接成功触发事件函数
        
        let roomData = {
            roomId: roomNum
        }
        WebsocketUtil.getWebSocketWithUrl().sendData({
            code: MessageType.Up.enterRoom,
            data: roomData
        })
        setTimeout(() => {
            let privateKey = sessionStorage.getItem("privateKey");
            if (!privateKey) {
                UserBalance(() => {
                    // loginSend(UserInfo.userName);
                    loginSend(UserInfo.userName, UserInfo.publicKey, UserInfo.privateKey, UserInfo.userCode, 0);//发送用户名到服务器
                });
            } else {
                privateLogin(privateKey);//私钥登录
            }
            // getJackpot();
        }, 500)
    };
    this.onSocketMsg = (msg) => {//收到服务器发送的webSocket信息触发事件，并进行相应的处理的函数接口
        if (msg.data) {
            msg.data = JSON.parse(msg.data)
        }
        if (msg.code != MessageType.Down.roomTime && msg.code != MessageType.Down.eosMsg) {
            //console.log("房间信息 ：" + JSON.stringify(msg))
        }
        switch (msg.code) {
            case MessageType.Down.bet://下注下行
                console.log("下注下行 ：" + JSON.stringify(msg))
                this.updateNor(msg.data);
                RoomInfo.isBet = false;
                UserBalance();
                $.message("下注成功！")
                break;
            case MessageType.Down.userLogin:
                // console.log("用户登录下行 ：" + JSON.stringify(msg));
                console.log(msg.data)
                if (msg.data.type == 1) {
                    UserInfo.account = msg.data.loginUserId;
                    UserInfo.publicKey = msg.data.publicKey;
                    sessionStorage.setItem("privateKey", UserInfo.privateKey);
                    getUserName();
                    UserBalance();
                    $.message("登录成功！");
                    $("#loginBtn").css("display","none");
                }
                WebsocketUtil.getWebSocketWithUrl().sendData({
                    code: MessageType.Up.qureySelf,
                    data: { time: 0 }
                });
                
                let roomData = {
                    roomId: roomNum
                };
                WebsocketUtil.getWebSocketWithUrl().sendData({
                    code: MessageType.Up.roomInfo,
                    data: roomData
                });
                $("#cpu").text(`${parseFloat((msg.data.cpu.used / msg.data.cpu.max) * 100).toFixed(2)}%`);//导航栏CPU显示
                break;
            case MessageType.Down.roomInfo://房信息
                console.log("房信息 ：" + JSON.stringify(msg));
                this.updateRoomView(msg.data)
                console.log(msg.data)
                drawFromWebRoomInfo(msg.data.road, this); //todo
                console.log("this",this)
                // this.showOpenWinType([5,2,3])
                // this.showUserWinText({});
                // this.showUserWinText({"roomId":1001,"qid":2915,"reward":[{"opt":1,"m":"[0.1075]"},{"opt":3,"m":"[0.1075]"},{"opt":5,"m":"[0.1]"}]})
                break;
            case MessageType.Down.roomStateChange:
                this._betState = msg.data.state;//0禁止游戏，1投注段2开奖段
                this._allTime = msg.data.time;
                // console.log("房间状态变更 ：" + JSON.stringify(msg));
                if (this._betState == 1) {
                    this.initBet();
                    this.setBetUnCheck();
                    this.resetWinType();
                    this.resetUserWinText();
                    RoomInfo.isBet = false;
                    this.updateReawardNum({
                        type: 0
                    });
                    this.updateReawardNum({
                        type: 1
                    });
                } else if (this._betState == 2) {//开奖段

                } else if (this._betState == 3) {//洗牌阶段

                }
                break;
            case MessageType.Down.betGoldChange:
                // console.log("betGoldChange ：" + JSON.stringify(msg));
                this.updateBetChange(msg.data);
                break;
            case MessageType.Down.roomTime://房间倒计时
                this.updateBetTime(msg.data.djs);
                break;
            case MessageType.Down.gameResult:
                // console.log("开奖消息 ：" + JSON.stringify(msg));
                this.updateReawardNum(msg.data);
                if (msg.data.type == 1) {//龙牌开奖还未出最终结果，只有虎牌时，才知道开的是龙 和 虎当中的一种
                    drawFromWebResult(msg.data.res, this);
                }
                break;
            case MessageType.Down.betLive:
                // console.log("投注直播 ：" + JSON.stringify(msg));
                updateHistory(msg.data);
                break;
            case MessageType.Down.eosMsg:
                // console.log("区块链数据 ：" + JSON.stringify(msg));
                theLotteryList(msg.data);
                break
            case MessageType.Down.betSelf:
                // console.log("我的投注推送 ：" + JSON.stringify(msg));
                updateMyBetItem(msg.data)
                break;
            case MessageType.Down.qureySelfDown:
                // console.log("我的投注查询 ：" + JSON.stringify(msg));
                updateItemOld(msg.data.list);
                break;
            case MessageType.Down.getReward:
                // console.log("getReward ：" + JSON.stringify(msg));
                this.showUserWinText(msg.data);
                break;
            case MessageType.Down.poolEOS:
                // console.log("奖金池 ：" + JSON.stringify(msg));
                $("#allPoolNum").text(msg.data.balance);
                break;
            case MessageType.Down.userEOS:
                console.log("用户余额 ：" + JSON.stringify(msg));
                $("#userEos").text(msg.data.balance);
                $("#userInfo").css("visibility", "visible");
                $("#userName").text(UserInfo.account);
                $("#loginBtn").text("注销");
                $("#loginBtn").attr("data-state", "2");
                $("#loginContainer").css("display", "none");
                $("#betSure").attr("data-state", "2")
                $("#betSure").text("投注");
                break;
            case MessageType.Down.shuffle:
                // console.log("洗牌 ：" + JSON.stringify(msg));
                break;

        }
    };
    this.onSocketError = (data) => {//webSocket发生错误时触发事件
        console.log("onSocketError:", data)
    };
    this.onSocketClose = (data) => {//webSocket关闭连接时触发事件
        console.log("onSocketClose:", data)
    };

    this.initBet = () => {
        let baseMul = [//定义所有赔率
            0.75,
            1.075,
            0.75,
            1.075,
            1,
            8,
            1
        ];
        for (let i = 0; i < baseMul.length; ++i) {
            this.updateBetNum({
                total: 0,
                self: 0,
                base: 1,
                mul: baseMul[i],
                opt: (i + 1)
            });
        }
        this.flyPoker()
    };

    this.updateRoomView = (data) => {
        // this._roomId = data.roomId;
        RoomInfo.roomId = data.roomId;
        this._betState = data.state;
        var myBet = data.myBet;
        for (let i = 0; i < myBet.length; ++i) {
            let item = myBet[i];
            this.updateSelf({
                opt: item.op,
                self: item.m

            });
        }
        var allBet = data.allBet;
        for (let i = 0; i < allBet.length; ++i) {
            let item = allBet[i];
            this.updateAll({
                opt: item.op,
                total: item.m

            });
        }

        if (data.open && data.open.length > 0) {
            for (let i = 0; i < data.open.length; ++i) {
                this.updateReawardNum(data.open[i])
            }
        }
    };

    this.flyPoker = () => {//龙虎两张牌从上到下的动画
        for (let i = 0; i < 2; ++i) {
            var id1 = "";
            var id2 = "";
            if (i == 0) {
                id1 = "longFront";
                id2 = "longBg"
                $("#" + id1).css("margin-top", "-150px");
                $("#" + id1).animate({ "margin-top": '0px' });
            } else if (i == 1) {
                id1 = "huFront";
                id2 = "huBg"
                $("#" + id1).css("margin-top", "-150px");
                $("#" + id1).animate({ "margin-top": '0px' });
            }
            $("#" + id1).css("display", "");
            $("#" + id2).css("display", "none");
            $("#" + id1).attr("src", "../images/poker/" + this.getPokerSrc());
        }
    }

    this.openPoker = (data) => {//开牌结果
        if (data.hua) {
            var id1 = "";
            var id2 = "";
            if (data.type == 0) {
                id1 = "longFront";
                id2 = "longBg"
            } else if (data.type == 1) {
                id1 = "huFront";
                id2 = "huBg"
            }
            $("#" + id1).css("display", "");
            $("#" + id2).css("display", "none");
            this.doRotation(id1, 90, (id, pokerData) => {
                $("#" + id).attr("src", "../images/poker/" + this.getPokerSrc(pokerData.hua, pokerData.p));
                this.doRotation(id, 0, (endId) => {
                    console.log("endId:", endId)
                }, {});
            }, data)
        }
    };

    this.doRotation = (id, rotation, callBack, data) => {//翻牌动画
        $(function () {
            var t = 1000;
            $("#" + id).animate(
                { borderSpacing: rotation }, //180 指旋转度数
                {

                    step: function (now, fix) {

                        $(this).css('-webkit-transform', 'rotateY(' + now + 'deg)');

                        $(this).css('-ms-transform', 'rotateY(' + now + 'deg)');

                        $(this).css('-moz-transform', 'rotateY(' + now + 'deg)');

                        $(this).css('-o-transform', 'rotateY(' + now + 'deg)');

                        $(this).css('-transform', 'rotateY(' + now + 'deg)');
                        if (now == 90 && callBack) {
                            callBack(id, data);
                        } else if (now == 180 && callBack) {
                            callBack(id, data);
                        }
                    },
                    durantion: t
                }, 'swing')

        })
    }

    this.getPokerSrc = (h, p) => {//定义给那张牌
        if (!h || !p) {
            return "card_background_new.png";
        }
        var type = {
            1: "_h_",
            2: "_r_",
            3: "_m_",
            4: "_f_"
        };
        var pn = function (p) {
            switch (p) {
                case 11:
                    return "J";
                case 12:
                    return "Q";
                case 13:
                    return "K";
                default:
                    return p
            }
        }(p);
        return "card" + type[h] + pn + "_new.png"
    }

    this.bindEvent = () => {
        $(".betBtn").click(this.changeBetBtnSelectState);
        let self = this;
        $("#betSure").click(function () {//点击登录按钮
            // betEos(self.getInputBetNum())
            console.log(($("#betSure").attr("data-state")));
            if ($("#betSure").attr("data-state") != "2") {//判断未登录，并弹出登录窗口
                $("#loginContainer").css("display", "block");
                return;
            }
            if (self.selectType) {//点击投注
                if (self._betState > 1) {
                    $.message({
                        message: '请等待下一局开始!',
                        type: 'warning'
                    });
                } else {
                    var betNum = self.getInputBetNum();
                    if (isNaN(betNum)) {//判断投注输入框是否为数字
                        $.message({
                            message: '输入不正确!',
                            type: 'warning'
                        });
                    }
                    else {
                        var data = {
                            roomId: RoomInfo.roomId,
                            money: self.getInputBetNum(),
                            opt: self.selectType
                        };
                        if (this.currentTime <= 35) {
                            $.message({
                                message: '请等待下一局开始!',
                                type: 'warning'
                            });
                        } else {
                            betEos(data);//投注扣款
                        }
                    }
                }
            } else {
                if (self._betState > 1) {
                    $.message({
                        message: '请等待下一局开始!',
                        type: 'warning'
                    });
                } else {
                    $.message({
                        message: '请选择要投注的内容!',
                        type: 'warning'
                    });
                }
            }
        })
        $(".betItem").click(this.changeBetBtnSelectState2);
        $(".mItem").click(this.changeBetBtnSelectState3)
        $("#betEditBox").on("input", this.editBoxChange);
    }

    this._updateBetEditBox = () => {//投注区输入框最大投注额
        let beforeNum = this.getInputBetNum();//调用获取输入框金额函数
        if (!beforeNum) {
            beforeNum = DEFAULT_INIT_BET_NUM;//获取投注区域输入框输入金额
        }
        let current = parseFloat(beforeNum) + parseFloat(this.selectBetNum);
        current = current.toFixed(1);
        let beforeBetNum = this.getBetNum();
        if (current > 100 - beforeBetNum) current = 100 - beforeBetNum;
        $("#betEditBox").val(current)
    }

    this.editBoxChange = (event) => {
        let betNum = this.getBetNum();
        let current = $(event.currentTarget).val();
        if (!isNaN(current)) {
            if (current > 100 - betNum) {
                current = 100 - betNum;
                // console.log(current)
                $(event.currentTarget).val(current);
            }
        }
        // console.log(current)
    }

    this.changeBetBtnSelectState = (event) => {
        $(".betBtn").css(
            "background-color", ""
        )
        $(event.currentTarget).css(
            "background-color", "#613522"
        )
        this.selectBetNum = event.target.innerHTML == "MAX" ? 100 : event.target.innerHTML;
        this._updateBetEditBox()
    }

    this.setBetUnCheck = () => {//投注区，几个选项的样式
        $(".betItem").css(
            {
                "width": "150px",
                "height": "150px",
                "color": "red",
                "text-align": "center",
                "background-color": "#20243F",
            }
        );

        $(".mItem").css(
            {
                "width": "180px",
                "height": "180px",
                "color": "white",
                "background-color": "#20243F",
            }
        )
        $("#betHe").css(
            {
                "width": "180px",
                "height": "135px",
                "color": "white",
                "background-color": "#20243F",
            }
        )
    };

    this.changeBetBtnSelectState2 = (event) => {
        this.setBetUnCheck();
        if (this.selectType != ENUM_BET_TYPE[event.currentTarget.id]) {
            // event.currentTarget.isCheck = true;
            $(event.currentTarget).css(
                {
                    "background-color": "#323966"
                }
            )
            this.checkClickType(event.currentTarget)
        } else {
            // event.currentTarget.isCheck = false;
            this.selectType = 0;
            $(event.currentTarget).css(
                {
                    "width": "150px",
                    "height": "150px",
                    "color": "red",
                    "text-align": "center",
                    "background-image": ""
                }
            )
            $(".mItem").css(
                {
                    "width": "180px",
                    "height": "180px",
                    "color": "white",
                    "background-color": "#20243F",
                }
            )
            $("#betHe").css(
                {
                    "width": "180px",
                    "height": "135px",
                    "color": "white",
                    "background-color": "#20243F",
                }
            )
        }
    }

    this.changeBetBtnSelectState3 = (event) => {
        this.setBetUnCheck();
        if (this.selectType != ENUM_BET_TYPE[event.currentTarget.id]) {
            // event.currentTarget.isCheck = true;
            $(event.currentTarget).css(
                {
                    // "background-image": "url(../images/select.png)",
                    // "background-size": "100% 100% "
                    "background-color": "#323966"
                }
            )
            this.checkClickType(event.currentTarget)
        } else {
            this.selectType = 0;
            // event.currentTarget.isCheck = false;
            if (event.currentTarget.id == "betHe") {
                $(event.currentTarget).css(
                    {
                        "width": "180px",
                        "height": "135px",
                        "color": "white",
                        "background-color": "#20243F"
                    }
                )
            } else {
                $(event.currentTarget).css(
                    {
                        "width": "180px",
                        "height": "180px",
                        "color": "white",
                        "background-color": "#20243F"
                    }
                )
            }
        }
    }

    this.checkClickType = (target) => {
        this.selectType = ENUM_BET_TYPE[target.id];
    }

    /**
     * 获取玩家输入的下注数量
     * @returns {*|jQuery}
     */
    this.getInputBetNum = () => {
        return $("#betEditBox").val()
    }

    this.getBetNum = () => {//自己已下注的总金额
        let betList = $(".betSelf").find(".betNum");
        let betScore = 0;
        for (let i = 0; i < betList.length; ++i) {
            betScore += parseFloat(betList[i].innerHTML)
        }
        return betScore.toFixed(1);
    }

    this.updateBetTime = (time) => {
        let currentTime = time;
        // console.log(this._betState)
        if (this._betState == 1) {//TODO 时间需要和服务器时间一样
            currentTime = this._allTime - (30 - time);
            this.currentTime = currentTime;
        } else if (this._betState == 2) {
            currentTime = this._allTime - (60 - time);
        } else if (this._betState == 3) {
            currentTime = this._allTime - (60 - time);
        }
        let betTimeNode = $("#betOverTime");
        if (currentTime < 10) {
            betTimeNode.text("0" + currentTime + "s")
        } else {
            betTimeNode.text(currentTime + "s")
        }
    };

    this.updateBetNum = (data) => {
        this.updateNor(data)
    };
    //给投注区域自己投注和总投注赋值
    this.updateNor = (data) => {
        let id = ENUM_BET_TYPE_ID[data.opt];
        let item = $("#" + id);
        let betNum = item.find(".betNum", item);
        let betType = item.find(".betOdds", item);//遍历赔率节点
        let baseMul = betType.find("p", betType);
        let currentBaseMul = baseMul[0];
        let currentMul = baseMul[2];
        betNum[0].innerHTML = data.total;
        betNum[1].innerHTML = data.self;
        if (data.self > 0) {
            betNum[1].innerHTML = data.self;
            let betSelf = item.find(".betSelf", item);
            betSelf[0].style.visibility = "visible";
        } else {
            let betSelf = item.find(".betSelf", item);
            betSelf[0].style.visibility = "hidden";
        }
        if (data.base >= 0) currentBaseMul.innerHTML = data.base;
        if (data.mul >= 0) currentMul.innerHTML = data.mul;
    };

    this.updateSelf = (data) => {
        let id = ENUM_BET_TYPE_ID[data.opt];
        let item = $("#" + id);
        let betNum = item.find(".betNum", item);
        if (data.self > 0) {
            betNum[1].innerHTML = data.self;
            let betSelf = item.find(".betSelf", item);
            betSelf[0].style.visibility = "visible";
        } else {
            let betSelf = item.find(".betSelf", item);
            betSelf[0].style.visibility = "hidden";
        }
    };

    this.updateAll = (data) => {
        let id = ENUM_BET_TYPE_ID[data.opt];
        let item = $("#" + id);
        let betNum = item.find(".betNum", item);
        if (data.total >= 0) betNum[0].innerHTML = data.total;
    };

    this.updateBetChange = (data) => {
        // roomId  房间号，int
        // opt     变动投注项：1龙单2龙双3虎单4虎双5龙6和7虎
        // total   此投注项总金额，小数
        // pei     赔率，整数，客户端拿到再除以1000为真实赔率小数
        let id = ENUM_BET_TYPE_ID[data.opt];
        let item = $("#" + id);
        let betNum = item.find(".betNum", item);
        let betType = item.find(".betOdds", item);
        let baseMul = betType.find("p", betType);
        let currentBaseMul = baseMul[0];
        let currentMul = baseMul[2];
        if (data.total >= 0) betNum[0].innerHTML = data.total;
        if (data.base >= 0) currentBaseMul.innerHTML = "1";
        if (data.mul >= 0) currentMul.innerHTML = (data.pei / 1000.0).toFixed(2);
    }

    /**
     * 更新开奖号码
     */
    this.updateReawardNum = (data) => {
        let id1 = "";
        let id2 = "";
        if (data.type == 0) {
            id1 = "longReward";
            id2 = "rewardNumLong";
        } else if (data.type == 1) {
            id1 = "huReward";
            id2 = "rewardNumHu";
        }
        if (!data.hash) {
            $("#" + id1).html(`<p id=${id1}></p>`);
        } else {
            $("#" + id1).html(`<p id=${id1}>${changeTextColor2("..." + data.hash.substring(data.hash.length - 40, data.hash.length), data.index)}</p>`);
        }
        if (!data.p) {
            $("#" + id2).text("0");
        } else {
            $("#" + id2).text(data.p);
        }
        this.openPoker(data);
        if (data.win) this.showOpenWinType(data.win)
    };

    this.resetWinType = () => {
        for (let i = 1; i <= 7; ++i) {
            let id = ENUM_BET_TYPE_ID[i];
            let item = $("#" + id);
            item.css("background-image", "");
        }
    };

    this.showOpenWinType = (data) => {
        // console.log("输赢信息 ：",data)
        if (data) {
            let url = "";
            for (let i = 0; i < data.length; ++i) {
                if (data[i] == 1 || data[i] == 2 || data[i] == 5) {
                    url = "url(../images/C8.png)"
                } else if (data[i] == 3 || data[i] == 4 || data[i] == 7) {
                    url = "url(../images/C6.png)"
                } else {
                    url = "url(../images/C7.png)"//
                }
                let id = ENUM_BET_TYPE_ID[data[i]];
                let item = $("#" + id);
                item.css("background-image", url);
            }
        }
    };

    this.resetUserWinText = () => {
        for (let i = 1; i <= 7; ++i) {
            let id = ENUM_BET_TYPE_ID[i];
            let item = $("#" + id);
            let winTexts = item.find(".winText", item);
            if (winTexts && winTexts[0]) winTexts[0].style.display = "none"
        }
    }

    this.showUserWinText = (data) => {
        console.log("输赢文字 ：", data)
        if (data) {
            let rewards = data.reward;
            for (let i = 0; i < rewards.length; ++i) {
                let itemData = rewards[i];
                let id = ENUM_BET_TYPE_ID[itemData.opt];
                let item = $("#" + id);
                let winTexts = item.find(".winText", item);
                if (winTexts && winTexts[0]) winTexts[0].style.display = "flex"
            }
        }
    }

}
// let Bet = new BetObj();
$(document).ready(function () {
    new BetObj().initUI();
});
showAlt = (cb, title) => {
    $('.bg_bg').show();
    $('.bg_main').show();
    $('.qxxz').click(function () {
        $('.bg_bg').hide();
        $('.bg_main').hide();
        if (cb) cb(1)
    })
    $('.qrxz').click(function () {
        var id = $(this).parent().parent().find('input').val();
        //alert(id);
        $('.bg_bg').hide();
        $('.bg_main').hide();
        if (cb) cb(2)
    })

    $('.info').text(title)
}

// function changeBetBtnSelectState() {
//
// }