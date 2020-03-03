/**
 * Created by admin on 2018/11/29.
 */
let MessageType = {};
MessageType.Up = {
    userLogin: "1110",
    enterRoom: "1111",//进入房间
    leaveRoom: "1112",//离开房间
    bet: "1113",    //投注
    roomInfo: "1118",
    qureySelf: "1121"//查询我的投注
};
MessageType.Down = {
    userLogin: 1110,
    bet: 1113,
    roomStateChange: 1114,//房间状态变更
    roomTime: 1115,//房间倒计时
    gameResult: 1116,//开奖信息下行
    betGoldChange: 1117,//投注引起房间金额变动
    roomInfo: 1118,       //房间信息
    betLive: 1119,       //投注直播
    betSelf: 1120,        //我的投注
    qureySelfDown: 1121,
    eosMsg: 1122,
    getReward: 1123,
    poolEOS: 1124,
    userEOS: 1125,
    shuffle: 1126.//洗牌

};

//获取进入房间号
let roomNum = document.location.href;
roomNum = roomNum.split("?")[1];
roomNum = roomNum.split("=")[1];


let websocketUrl = "";
// let WebsocketUtil = {
//     initSocket:function () {
//        
//     }
// };
function WebsocketBase() {
    this._websocket = null;
    this.initSocket = function (url) {
        this.url = url;
        this._websocket = new WebSocket(url);
        this.bindEvent();
    };
    this.bindEvent = function () {
        this._websocket.onopen = this.onSocketOpen;
        this._websocket.onmessage = this.onSocketMsg;
        this._websocket.onerror = this.onSocketError;
        this._websocket.onclose = this.onSocketClose;
    };

    this.sendData = function (data) {
        // console.log("请求消息 ：" , data)
        console.log("发送消息的SOCKET :" + this.url)
        if (this._websocket.readyState == WebSocket.OPEN) {
            this._websocket.send(JSON.stringify(data));
        } else {
            $.message({
                message: '网络连接断开!!!',
                type: 'warning'
            });
            // var self = this;
            // showAlt(function (type) {
            //     console.log("type :", type)
            //     self._websocket = new WebSocket(self.url);
            // }, "网络连接断开")
        }
    };

    this.onSocketOpen = function (msg) {
        console.log("----------------onSocketOpen-------------------");
        for (let key in WS_EVENT_OPEN) {
            let obj = WS_EVENT_OPEN[key];
            if (obj.callback) {
                obj.callback(msg.data);
            }

        }
    };

    this.onSocketMsg = function (msg) {
        for (let key in WS_EVENT_MSG) {
            let obj = WS_EVENT_MSG[key];
            if (obj.callback) {
                obj.callback(JSON.parse(msg.data));
            }

        }
    };

    this.onSocketError = function (msg) {
        console.log("----------------onSocketError-------------------");
        for (let key in WS_EVENT_ERROR) {
            let obj = WS_EVENT_ERROR[key];
            if (obj.callback) {
                obj.callback(msg.data);
            }

        }
    };

    this.onSocketClose = function (msg) {
        console.log("----------------onSocketClose-------------------", msg)
        for (let key in WS_EVENT_CLOSE) {
            let obj = WS_EVENT_CLOSE[key];
            if (obj.callback) {
                obj.callback(msg.data);
            }

        }
    }
};
const KEY_EVENT_OPEN = "open";
const KEY_EVENT_MSG = "msg";
const KEY_EVENT_ERROR = "error";
const KEY_EVENT_CLOSE = "close";
let WS_EVENT_OPEN = {};
let WS_EVENT_MSG = {};
let WS_EVENT_ERROR = {};
let WS_EVENT_CLOSE = {};
let WS_EVENT = {
    "open": WS_EVENT_OPEN,
    "msg": WS_EVENT_MSG,
    "error": WS_EVENT_ERROR,
    "close": WS_EVENT_CLOSE
};
var SocketKey = "SocketCall";
const baseUrl = "myeosgame.com";
// const baseUrl = "10.0.0.171:8080/lottery";
const ip = "https://myeosgame.com";
// const ip = "http://10.0.0.171:8080/lottery";
const network = {
    blockchain: "eos",
    protocol: "https",
    host: "api.eosbeijing.one",
    port: 443,
    chainId: "aca376f206b8fc25a6ed44dbdc66547c36c6c33e3a119ffbeaef943642f0e906"
};
const url = `wss://${baseUrl}/ws?gameType=tiger`;
// const url = `ws://${baseUrl}/ws?gameType=tiger`;
let WebsocketUtil = {
    SOCKET_URL: {},
    getWebSocketWithUrl: function (/*url,*/ opt) {
        // url = "ws://myeosgame.com/ws";
        // url = "wss://myeosgame.com/ws";
        // url = "wss://myeosgame.com/ws";
        // let userId = UserInfo.userName;
        // url = url +  userId;
        if (!this.SOCKET_URL[SocketKey]) {
            this.SOCKET_URL[SocketKey] = new WebsocketBase();
            this.SOCKET_URL[SocketKey].initSocket(url)
        }
        return this.SOCKET_URL[SocketKey];
    },

    /**
     * 添加websocket消息回调监听
     * @param type 消息类型 KEY_EVENT_OPEN|KEY_EVENT_MSG|KEY_EVENT_ERROR|KEY_EVENT_CLOSE
     * @param cb Function
     * @param tag 回调消息TAG
     */
    addWSEventListener: function (type, cb, tag) {
        let obj = WS_EVENT[type][tag];
        if (!obj) {
            WS_EVENT[type][tag] = {
                key: tag,
                callback: cb
            };
        } else {
            WS_EVENT[type][tag].callback = cb;
        }
    },
    removeWSEventListener: function (tag) {
        delete WS_EVENT[KEY_EVENT_OPEN][tag];
        delete WS_EVENT[KEY_EVENT_MSG][tag];
        delete WS_EVENT[KEY_EVENT_ERROR][tag];
        delete WS_EVENT[KEY_EVENT_CLOSE][tag];
    },

    removeWSEventType: function (type, tag) {
        delete WS_EVENT[type][tag];
    },

    getSocketAddress() {

    },
};
