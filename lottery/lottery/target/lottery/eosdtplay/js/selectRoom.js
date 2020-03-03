let ws = new WebSocket(`wss://myeosgame.com/ws?gameType=tiger`);
// let ws = new WebSocket(`ws://10.0.0.171:8080/lottery/ws?gameType=tiger`);
let userName = "";
let Ip = "https://myeosgame.com";
// let Ip = "http://10.0.0.171:8080/lottery";
$(function () {
    let obj = {
        road_arr: []
    }

    ws.onopen = function () {
        // 使用 send() 方法发送数据
        let roomInformation = {//向服务器请求房间大厅信息
            code: 1127,
            data: {}
        }
        roomInformation = JSON.stringify(roomInformation);
        ws.send(roomInformation);
        console.log("房间大厅链接已开启")
    };
    // 接收服务端数据时触发事件
    ws.onmessage = function (evt) {
        console.log(evt)
        let roomData = JSON.parse(evt.data);
        if (roomData.code == 1127) {//房间大厅接收服务器返回所有信息
            roomData = JSON.parse(roomData.data);
            let rooms = roomData.rooms;//每个房间的总信息
            console.log("from", roomData);
            console.log("from", rooms);
            $("#InxJackpot").text(roomData.balance);//为奖金池赋值
            //为每个房间号和房间人数赋值
            $("#roomOne").text(rooms[0].id);
            $("#roomTwo").text(rooms[1].id);
            $("#roomTherr").text(rooms[2].id);
            $("#roomOneNum").text(rooms[0].num);
            $("#roomTwoNum").text(rooms[1].num);
            $("#roomTherrNum").text(rooms[2].num);
            console.log(obj)
            // drawFromWebRoomInfo(rooms[0].road, obj);
            // drawFromWebRoomInfo2(rooms[1].road, obj);
            // drawFromWebRoomInfo3(rooms[2].road, obj);
            drawFromWebRoomInfo(rooms[0].road, obj, 1);
            drawFromWebRoomInfo(rooms[1].road, obj, 2);
            drawFromWebRoomInfo(rooms[2].road, obj, 3);
        }

    };

    // 断开 web socket 连接成功触发事件
    ws.onclose = function () {
        // alert("连接已关闭...");
        console.log("房间大厅链接已关闭")
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




    //绘制龙虎斗选择房间页面table tr td
    let table = "";
    for (let i = 0; i < 6; i++) {
        table += `<tr class="room1Table" >`
        for (let j = 0; j < 32; j++) {
            table += `<td width="20px"></td>`
        }
        table += `</tr>`
    }
    $(".room1Trend").html(table);
    //点击导航栏logo切换游戏下拉框
    $("#selectTitle").click(function () {
        if ($("#selectTitle").attr("data-logo") == 0) {
            showList();
            $("#selectTitle").attr("data-logo", 1);
        }
        else {
            hideList();
            $("#selectTitle").attr("data-logo", 0);
        }
    });
    $(".dropdown").click(function () {//点击跳转至游戏首页
        window.location.href = `${Ip}`;
    });

    // 语言选择切换
    $("#changeLanguage").click(function () {
        $("#language").toggle();
    });
    $("#Ch").click(function () {
        $("#inputBox").html(
            `<img src="../images/Singapore.png" alt="" id="guoqiBox">简体中文`
        );
        $("#language").css("display", "none");
    });
    $("#En").click(function () {
        $("#inputBox").html(
            `<img src="../images/guoqi02.png" alt="" id="guoqiBox">English`
        );
        $("#language").css("display", "none");
    });
    //游戏帮助区域
    $("#rluerIntroduce").hover(function () {
        $("#rluerIntroduce").css({
            "cursor": "pointer"
        });
    });
    $("#rluerIntroduce").click(function () {
        $("#SelectRoom").css("display", "none");
        $("#Invite").css("display", "none");
        $("#gamesHelp").css("display", "block");
    });
    $("#goBack").hover(function () {
        $("#goBack").css({
            "cursor": "pointer"
        });
    });
    $("#goBack").click(function () {
        $("#SelectRoom").css("display", "block");
        $("#gamesHelp").css("display", "none");
    });
    $("#goBack1-1").hover(function () {
        $("#goBack1-1").css({
            "cursor": "pointer"
        });
    });
    $("#goBack1-1").click(function () {
        $("#SelectRoom").css("display", "block");
        $("#gamesHelp").css("display", "none");
    });

    // 邀请好友区域
    $("#inviteBtn").hover(function () {
        $("#inviteBtn").css({
            "cursor": "pointer"
        });
    });
    $("#inviteBtn").click(function () {
        $("#SelectRoom").css("display", "none");
        $("#gamesHelp").css("display", "none");
        $("#Invite").css("display", "block");
        $("#inviteInput").val("请先登录，才能获取邀请链接");
        if ($("#loginBtn").attr("data-state") == 1) {
            $("#inviteInput").val("请先登录，才能获取邀请链接");
        }
        else {
            // Copy();//复制
            // TheSubordinateList(1);//获取下级列表和页码
        }
    });
    $("#InviteGoBack").hover(function () {
        $("#InviteGoBack").css({
            "cursor": "pointer"
        });
    });
    $("#InviteGoBack").click(function () {
        $("#SelectRoom").css("display", "block");
        $("#Invite").css("display", "none");
    });


    //点击进入房间
    $("#Enter1").click(function () {
        let room = $("#roomOne").text();
        window.location.href = `../tiger/home.html?room=${room}`;
    });
    $("#Enter2").click(function () {
        let room = $("#roomTwo").text();
        window.location.href = `../tiger/home.html?room=${room}`;
    });
    $("#Enter3").click(function () {
        let room = $("#roomTherr").text();
        window.location.href = `../tiger/home.html?room=${room}`;
    });
});
function Copy() {//邀请好友复制到剪切板

    $("#inviteInput").val(`${Ip}/eosdtplay/tiger/home?userCode=${userName}`);
    $("#InviteCopy").css({
        "cursor": "pointer"
    });
    $("#InviteCopy").click(function () {
        let Url2 = document.getElementById("inviteInput");
        Url2.select(); // 选择对象
        document.execCommand("Copy"); // 执行浏览器复制命令
        $.message("已复制到剪切板！");
    });

}
function TheSubordinateList(page) {//获取邀请好友页面，下级列表和页码
    console.log(123456, userName)
    $.ajax({
        type: "get",
        url: `${Ip}/user/getMyChildsto.do`,
        async: false,
        data: {
            userName: userName,
            pageNum: page
        },
        success: function (data) {
            console.log(data)
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
function pageing() {//分页
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




















/**
 * Created by admin on 2018/11/20.
 */

//走势图
function drawGrid() {
    var drawList = $(".tableItem");
    for (let i = 0; i < drawList.length; ++i) {
        let drawItem = drawList[i];
        drawRow(drawItem, 1, 1);
    }
    var drawList = $(".tableItem2");
    for (let i = 0; i < drawList.length; ++i) {
        let drawItem = drawList[i];
        drawRow(drawItem, 1, 2);
    }

    var drawList = $(".tableItem3");
    for (let i = 0; i < drawList.length; ++i) {
        let drawItem = drawList[i];
        drawRow(drawItem, 1, 3);
    }

}

function drawFromWebRoomInfo(data, betObj, roomNum) {
    var arr = [];
    for (let i = 0; i < data.length; ++i) {
        let item = data[i];
        for (let j = 0; j < item.length; j++) {//  5: "龙",6: "和",7: "虎"
            if (item[j] == 5) {
                arr.push(1);
            } else if (item[j] == 6) {
                arr.push(3);
            } else if (item[j] == 7) {
                arr.push(2)
            }
        }
    }
    console.log(betObj.road_arr);
    betObj.road_arr = betObj.road_arr.concat(arr);
    var diveData = updateTableData(JSON.parse(JSON.stringify(arr)), roomNum);
}

function drawFromWebResult(res, betObj) {
    // console.log("重新绘制 2：" ,betObj.road_arr)
    if (res == 5) {
        betObj.road_arr = betObj.road_arr.concat([1]);
    } else if (res == 6) {
        betObj.road_arr = betObj.road_arr.concat([3]);
    } else if (res == 7) {
        betObj.road_arr = betObj.road_arr.concat([2]);
    }
    var diveData = updateTableData(JSON.parse(JSON.stringify(betObj.road_arr)), roomNum);
}

function drawRow(item, type, roomNum) {
    var list = [];
    let length = 38;
    if (type == 1) {
        if (roomNum == 1) {
            list = $(".tableGrid");
            length = 38;
            for (let i = 0; i < length; ++i) {
                $(item).append($(list[0]).clone());
            }
        }
        else if (roomNum == 2) {
            list = $(".tableGrid2");
            length = 38;
            for (let i = 0; i < length; ++i) {
                $(item).append($(list[0]).clone());
            }
        }
        else if (roomNum == 3) {
            list = $(".tableGrid3");
            length = 38;
            for (let i = 0; i < length; ++i) {
                $(item).append($(list[0]).clone());
            }
        }
    }


}




function updateTableData(data,roomNum) {
    if(roomNum == 1){
        var drawList = $(".tableItem");
    }
    else if(roomNum == 2){
        var drawList = $(".tableItem2");
    }
    else if(roomNum == 3){
        var drawList = $(".tableItem3");
    }
    // let drawItem = drawList[0];
    console.log(1, data)
    var divideData = divideDataArrNew(data);
    for (let i = 0; i < 6; ++i) {
        let drawItem = drawList[i];
        for (let j = 0; j < 38; j++) {
            let grid = drawItem.children[j];
            let red = grid.children[0];
            let blue = grid.children[1];
            let yellow = grid.children[2];
            red.style.display = "none";
            blue.style.display = "none";
            yellow.style.display = "none";
        }
    }
    // console.log(divideData);
    for (let i = 0; i < divideData.length; ++i) {
        // console.log("11111111")
        let drawItem = drawList[i];
        let itemData = divideData[i];
        for (let j = 0; j < itemData.length; ++j) {
            let typeData = itemData[j];
            var grid = drawItem.children[j];
            // console.log(typeData)
            if (grid) {
                var red = grid.children[0];
                var blue = grid.children[1];
                var yellow = grid.children[2];
                if (typeData) {
                    switch (typeData.type) {
                        case 1:
                            red.style.display = "block";
                            blue.style.display = "none";
                            yellow.style.display = "none";
                            if (typeData.num > 0) {
                                red.innerHTML = typeData.num;
                            } else {
                                red.innerHTML = "";
                            }
                            red.style.color = "white";
                            red.style.fontSize = "4px";
                            red.style.lineHeight = "14px";
                            red.style.textAlign = "center";
                            break;
                        case 2:
                            red.style.display = "none";
                            blue.style.display = "block";
                            yellow.style.display = "none";
                            if (typeData.num > 0) {
                                blue.innerHTML = typeData.num;
                            } else {
                                blue.innerHTML = "";
                            }
                            blue.style.color = "white";
                            blue.style.fontSize = "4px";
                            blue.style.lineHeight = "14px";
                            blue.style.textAlign = "center";
                            break;
                        case 3:
                            red.style.display = "none";
                            blue.style.display = "none";
                            yellow.style.display = "block";

                            if (typeData.num > 0) {
                                yellow.innerHTML = typeData.num;
                            } else {
                                yellow.innerHTML = "";
                            }
                            yellow.style.color = "white";
                            yellow.style.fontSize = "4px";
                            yellow.style.lineHeight = "14px";
                            yellow.style.textAlign = "center";
                            break;
                    }
                } else {
                    red.style.display = "none";
                    blue.style.display = "none";
                    yellow.style.display = "none";
                }
            }
        }
    }
    return divideData;
}

Array.dim = function (dimension, initial) {
    var a = [], i;
    for (i = 0; i < dimension; i++) {
        a[i] = initial;
    }
    return a;
};


function checkFirstEmpytIndex(arr) {
    let itemArr = arr[0]//只检测最上排的
    for (let i = 0; i < itemArr.length; ++i) {
        if (!itemArr[i]) {
            return i;
        }
    }
    return itemArr.length
}

/**
 * 大路数据的拆分
 * @param data
 * @returns {*[]}
 */
function divideDataArrNew(data) {
    console.log(data)
    let girdNum = 39;
    var arr = [
        Array.dim(girdNum, 0),
        Array.dim(girdNum, 0),
        Array.dim(girdNum, 0),
        Array.dim(girdNum, 0),
        Array.dim(girdNum, 0),
        Array.dim(girdNum, 0),
    ];
    console.log(arr)
    var isFinish = false;
    arr[0][0] = {
        type: data.shift(),
        num: 0
    };
    var clearFinish = false;
    var v_index = 1;
    var h_index = 0;
    var bh_index = 0;
    var bv_index = 0;
    while (!clearFinish) {
        var t = data[0];
        if (t == 3) {
            t = data.shift();
        }
        if (t == 3 && arr[0][0].type == 3) {
            arr[v_index > 5 ? 5 : v_index][h_index] = {
                type: t,
                num: 0
            };
            bv_index = v_index;
            v_index++;
            if (v_index > 5) {
                bh_index = h_index;
                bv_index = 0;
                h_index++;
            }
        } else {
            clearFinish = true;
        }
    }
    var direction = "down";
    if (v_index >= 5) {
        v_index = 5;
        direction = "right";
    }
    while (!isFinish) {
        let num = data.shift();
        if (direction == "down") { //向下运动时
            if (!arr[v_index]) {
                arr[v_index] = [];
            }
            if (arr[v_index][h_index]) {//如果存在值，则网上退一格，往右进一个
                v_index -= 1; //这里进行重新赋值是应为当前要绘制的空格已经被占用
                h_index += 1;
            }
        } else {
            if (!arr[v_index + 1]) {
                arr[v_index + 1] = [];
            }
            if (v_index < 5 && !arr[v_index + 1][h_index]) {//向右边移动的时候如果该格子的下方已经不存在时，下次向下移动
                direction = "down"; //该处不需要重新赋值是应为判断的格子是下一个回合绘制的点
            }
        }

        if (num != 3) {
            var beforeObj = arr[bv_index][bh_index];
            if (beforeObj && beforeObj.type != num) {
                if (beforeObj.type != 3) {
                    h_index = checkFirstEmpytIndex(arr);
                    v_index = 0;
                    direction = "down";
                }
            } else {
                if (v_index < 5 && h_index > 1) {
                    if (!arr[v_index + 1]) {
                        arr[v_index + 1] = [];
                    }
                    let downObj = arr[v_index + 1][h_index];
                    let leftDownObj = arr[v_index + 1][h_index - 1];
                    if (downObj && downObj.type == num) {
                        v_index -= 1;
                        if (v_index < 0) {
                            v_index = 0;
                        } else {
                            h_index += 1;
                        }
                    } else if ((leftDownObj && leftDownObj.type == num)) {
                        direction = "right";
                    }
                } else if (v_index == 5) {
                    if (!arr[v_index - 1]) {
                        arr[v_index - 1] = [];
                    }
                    if (!arr[v_index]) {
                        arr[v_index] = [];
                    }
                    let leftObj = arr[v_index][h_index - 1];
                    let upObj = arr[v_index - 1][h_index];
                    if (leftObj && leftObj.type == num && upObj && upObj.type == num) {
                        v_index -= 1;
                        h_index += 1;
                    }
                }
            }
            bh_index = h_index;
            bv_index = v_index;
            if (!arr[v_index]) {
                arr[v_index] = [];
            }
            arr[v_index][h_index] = {
                type: num,
                num: 0
            }
        } else {
            h_index = bh_index;
            v_index = bv_index;
            arr[bv_index][bh_index].num += 1;
        }
        if (direction == "down") {
            if (v_index == 5) {
                direction = "right";
                h_index++;
            } else {
                v_index++;
            }
        } else {
            h_index++;
        }
        if (data.length == 0) {
            isFinish = true;
        }
    }
    if (h_index >= 30) {
        arr[0] = arr[0].splice(h_index - 30, h_index);
        arr[1] = arr[1].splice(h_index - 30, h_index);
        arr[2] = arr[2].splice(h_index - 30, h_index);
        arr[3] = arr[3].splice(h_index - 30, h_index);
        arr[4] = arr[4].splice(h_index - 30, h_index);
        arr[5] = arr[5].splice(h_index - 30, h_index);

    }
    // console.log(h_index)
    return arr;
}



function divideBigEyeData(data) {
    // var daluData = divideDataArrNew(data);
    // console.log(data)
    var beginIndex = 1;
    var isFinish = false;
    var length = queryRoadLength(2, data);
    // console.log(length)
    // while (!isFinish){
    //
    // }
}

function queryRoadLength(index, data) {
    var isQueryEnd = false;
    var h_index = index;
    var v_index = 0;
    var length = 0;
    var direction = "down";
    var beforeObj = data[v_index][h_index];
    v_index = 1;
    length = 1;
    if (!beforeObj) return 0;
    if (!data) return 0;
    while (!isQueryEnd) {
        var obj = data[v_index][h_index];
        if (obj) {
            if (beforeObj && beforeObj.type != obj.type && beforeObj.type != 3) {
                if (direction == "down") {
                    h_index += 1;
                    v_index -= 1;
                    direction = "right";
                    let rightObj = data[v_index][h_index];
                    if (beforeObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
                        isQueryEnd = true;
                        return length;
                    }
                    beforeObj = rightObj;
                } else if (direction == "right") {
                    if (v_index >= 5) {
                        isQueryEnd = true;
                        return length;
                    }
                    v_index += 1;
                    direction = "down";
                    let downObj = data[v_index][h_index];
                    if (beforeObj.type != downObj.type && beforeObj.type != 3) {
                        isQueryEnd = true;
                        return length;
                    }
                    beforeObj = downObj;
                }
            } else {
                beforeObj = obj
            }
            // beforeObj = obj;
        } else {
            if (direction == "down") {
                h_index += 1;
                v_index -= 1;
                direction = "right";
                let rightObj = data[v_index][h_index];
                if (!data[v_index]) {
                    data[v_index] = [];
                }
                if (rightObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
                    isQueryEnd = true;
                    return length;
                }
                beforeObj = rightObj;
            } else {
                if (v_index == 5) {
                    direction = "right";
                    h_index += 1;
                    v_index -= 1;
                    if (!data[v_index]) {
                        data[v_index] = [];
                    }
                    let rightObj = data[v_index][h_index];
                    if (!beforeObj) {//TODO ???
                        isQueryEnd = true;
                        return length
                    }
                    if (!rightObj) {//TODO ???
                        rightObj = {};
                    }
                    if (beforeObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
                        isQueryEnd = true;
                        return length;
                    }
                    beforeObj = rightObj;
                } else {
                    v_index += 1;
                    h_index -= 1;
                    direction = "down";
                    // if(data[v_index]){
                    //     data[v_index] = [];
                    // }
                    let downObj = data[v_index][h_index];
                    if (beforeObj && downObj && beforeObj.type != downObj.type && beforeObj.type != 3) {
                        isQueryEnd = true;
                        return length;
                    }
                    beforeObj = downObj;
                }
            }
        }
        if (direction == "down") {
            if (v_index >= 5) {
                v_index = 5;
                h_index += 1;
                direction = "right";
            } else {
                v_index++;
            }
        } else {
            h_index++;
        }
        length++;
    }
    return length;
}
drawGrid();






















// /**
//  * Created by admin on 2018/11/20.
//  */

// //走势图
// function drawGrid() {
//     var drawList = $(".tableItem");
//     for (let i = 0; i < drawList.length; ++i) {
//         let drawItem = drawList[i];
//         drawRow(drawItem, 1);
//     }
// }

// function drawFromWebRoomInfo(data, betObj) {
//     var arr = [];
//     for (let i = 0; i < data.length; ++i) {
//         let item = data[i];
//         for (let j = 0; j < item.length; j++) {//  5: "龙",6: "和",7: "虎"
//             if (item[j] == 5) {
//                 arr.push(1);
//             } else if (item[j] == 6) {
//                 arr.push(3);
//             } else if (item[j] == 7) {
//                 arr.push(2)
//             }
//         }
//     }
//     console.log(betObj.road_arr);
//     betObj.road_arr = betObj.road_arr.concat(arr);
//     var diveData = updateTableData(JSON.parse(JSON.stringify(arr)));
// }

// function drawFromWebResult(res, betObj) {
//     // console.log("重新绘制 2：" ,betObj.road_arr)
//     if (res == 5) {
//         betObj.road_arr = betObj.road_arr.concat([1]);
//     } else if (res == 6) {
//         betObj.road_arr = betObj.road_arr.concat([3]);
//     } else if (res == 7) {
//         betObj.road_arr = betObj.road_arr.concat([2]);
//     }
//     var diveData = updateTableData(JSON.parse(JSON.stringify(betObj.road_arr)));
// }

// function drawRow(item, type) {
//     var list = [];
//     let length = 38;
//     if (type == 1) {
//         list = $(".tableGrid");
//         length = 38;
//     }

//     for (let i = 0; i < length; ++i) {
//         $(item).append($(list[0]).clone());
//     }
// }




// function updateTableData(data) {
//     var drawList = $(".tableItem");
//     // let drawItem = drawList[0];
//     console.log(1, data)
//     var divideData = divideDataArrNew(data);
//     for (let i = 0; i < 6; ++i) {
//         let drawItem = drawList[i];
//         for (let j = 0; j < 38; j++) {
//             let grid = drawItem.children[j];
//             let red = grid.children[0];
//             let blue = grid.children[1];
//             let yellow = grid.children[2];
//             red.style.display = "none";
//             blue.style.display = "none";
//             yellow.style.display = "none";
//         }
//     }
//     // console.log(divideData);
//     for (let i = 0; i < divideData.length; ++i) {
//         // console.log("11111111")
//         let drawItem = drawList[i];
//         let itemData = divideData[i];
//         for (let j = 0; j < itemData.length; ++j) {
//             let typeData = itemData[j];
//             var grid = drawItem.children[j];
//             // console.log(typeData)
//             if (grid) {
//                 var red = grid.children[0];
//                 var blue = grid.children[1];
//                 var yellow = grid.children[2];
//                 if (typeData) {
//                     switch (typeData.type) {
//                         case 1:
//                             red.style.display = "block";
//                             blue.style.display = "none";
//                             yellow.style.display = "none";
//                             if (typeData.num > 0) {
//                                 red.innerHTML = typeData.num;
//                             } else {
//                                 red.innerHTML = "";
//                             }
//                             red.style.color = "white";
//                             red.style.fontSize = "4px";
//                             red.style.lineHeight = "14px";
//                             red.style.textAlign = "center";
//                             break;
//                         case 2:
//                             red.style.display = "none";
//                             blue.style.display = "block";
//                             yellow.style.display = "none";
//                             if (typeData.num > 0) {
//                                 blue.innerHTML = typeData.num;
//                             } else {
//                                 blue.innerHTML = "";
//                             }
//                             blue.style.color = "white";
//                             blue.style.fontSize = "4px";
//                             blue.style.lineHeight = "14px";
//                             blue.style.textAlign = "center";
//                             break;
//                         case 3:
//                             red.style.display = "none";
//                             blue.style.display = "none";
//                             yellow.style.display = "block";

//                             if (typeData.num > 0) {
//                                 yellow.innerHTML = typeData.num;
//                             } else {
//                                 yellow.innerHTML = "";
//                             }
//                             yellow.style.color = "white";
//                             yellow.style.fontSize = "4px";
//                             yellow.style.lineHeight = "14px";
//                             yellow.style.textAlign = "center";
//                             break;
//                     }
//                 } else {
//                     red.style.display = "none";
//                     blue.style.display = "none";
//                     yellow.style.display = "none";
//                 }
//             }
//         }
//     }
//     return divideData;
// }

// Array.dim = function (dimension, initial) {
//     var a = [], i;
//     for (i = 0; i < dimension; i++) {
//         a[i] = initial;
//     }
//     return a;
// };


// function checkFirstEmpytIndex(arr) {
//     let itemArr = arr[0]//只检测最上排的
//     for (let i = 0; i < itemArr.length; ++i) {
//         if (!itemArr[i]) {
//             return i;
//         }
//     }
//     return itemArr.length
// }

// /**
//  * 大路数据的拆分
//  * @param data
//  * @returns {*[]}
//  */
// function divideDataArrNew(data) {
//     console.log(data)
//     let girdNum = 39;
//     var arr = [
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//     ];
//     console.log(arr)
//     var isFinish = false;
//     arr[0][0] = {
//         type: data.shift(),
//         num: 0
//     };
//     var clearFinish = false;
//     var v_index = 1;
//     var h_index = 0;
//     var bh_index = 0;
//     var bv_index = 0;
//     while (!clearFinish) {
//         var t = data[0];
//         if (t == 3) {
//             t = data.shift();
//         }
//         if (t == 3 && arr[0][0].type == 3) {
//             arr[v_index > 5 ? 5 : v_index][h_index] = {
//                 type: t,
//                 num: 0
//             };
//             bv_index = v_index;
//             v_index++;
//             if (v_index > 5) {
//                 bh_index = h_index;
//                 bv_index = 0;
//                 h_index++;
//             }
//         } else {
//             clearFinish = true;
//         }
//     }
//     var direction = "down";
//     if (v_index >= 5) {
//         v_index = 5;
//         direction = "right";
//     }
//     while (!isFinish) {
//         let num = data.shift();
//         if (direction == "down") { //向下运动时
//             if (!arr[v_index]) {
//                 arr[v_index] = [];
//             }
//             if (arr[v_index][h_index]) {//如果存在值，则网上退一格，往右进一个
//                 v_index -= 1; //这里进行重新赋值是应为当前要绘制的空格已经被占用
//                 h_index += 1;
//             }
//         } else {
//             if (!arr[v_index + 1]) {
//                 arr[v_index + 1] = [];
//             }
//             if (v_index < 5 && !arr[v_index + 1][h_index]) {//向右边移动的时候如果该格子的下方已经不存在时，下次向下移动
//                 direction = "down"; //该处不需要重新赋值是应为判断的格子是下一个回合绘制的点
//             }
//         }

//         if (num != 3) {
//             var beforeObj = arr[bv_index][bh_index];
//             if (beforeObj && beforeObj.type != num) {
//                 if (beforeObj.type != 3) {
//                     h_index = checkFirstEmpytIndex(arr);
//                     v_index = 0;
//                     direction = "down";
//                 }
//             } else {
//                 if (v_index < 5 && h_index > 1) {
//                     if (!arr[v_index + 1]) {
//                         arr[v_index + 1] = [];
//                     }
//                     let downObj = arr[v_index + 1][h_index];
//                     let leftDownObj = arr[v_index + 1][h_index - 1];
//                     if (downObj && downObj.type == num) {
//                         v_index -= 1;
//                         if (v_index < 0) {
//                             v_index = 0;
//                         } else {
//                             h_index += 1;
//                         }
//                     } else if ((leftDownObj && leftDownObj.type == num)) {
//                         direction = "right";
//                     }
//                 } else if (v_index == 5) {
//                     if (!arr[v_index - 1]) {
//                         arr[v_index - 1] = [];
//                     }
//                     if (!arr[v_index]) {
//                         arr[v_index] = [];
//                     }
//                     let leftObj = arr[v_index][h_index - 1];
//                     let upObj = arr[v_index - 1][h_index];
//                     if (leftObj && leftObj.type == num && upObj && upObj.type == num) {
//                         v_index -= 1;
//                         h_index += 1;
//                     }
//                 }
//             }
//             bh_index = h_index;
//             bv_index = v_index;
//             if (!arr[v_index]) {
//                 arr[v_index] = [];
//             }
//             arr[v_index][h_index] = {
//                 type: num,
//                 num: 0
//             }
//         } else {
//             h_index = bh_index;
//             v_index = bv_index;
//             arr[bv_index][bh_index].num += 1;
//         }
//         if (direction == "down") {
//             if (v_index == 5) {
//                 direction = "right";
//                 h_index++;
//             } else {
//                 v_index++;
//             }
//         } else {
//             h_index++;
//         }
//         if (data.length == 0) {
//             isFinish = true;
//         }
//     }
//     if (h_index >= 30) {
//         arr[0] = arr[0].splice(h_index - 30, h_index);
//         arr[1] = arr[1].splice(h_index - 30, h_index);
//         arr[2] = arr[2].splice(h_index - 30, h_index);
//         arr[3] = arr[3].splice(h_index - 30, h_index);
//         arr[4] = arr[4].splice(h_index - 30, h_index);
//         arr[5] = arr[5].splice(h_index - 30, h_index);

//     }
//     // console.log(h_index)
//     return arr;
// }



// function divideBigEyeData(data) {
//     // var daluData = divideDataArrNew(data);
//     // console.log(data)
//     var beginIndex = 1;
//     var isFinish = false;
//     var length = queryRoadLength(2, data);
//     // console.log(length)
//     // while (!isFinish){
//     //
//     // }
// }

// function queryRoadLength(index, data) {
//     var isQueryEnd = false;
//     var h_index = index;
//     var v_index = 0;
//     var length = 0;
//     var direction = "down";
//     var beforeObj = data[v_index][h_index];
//     v_index = 1;
//     length = 1;
//     if (!beforeObj) return 0;
//     if (!data) return 0;
//     while (!isQueryEnd) {
//         var obj = data[v_index][h_index];
//         if (obj) {
//             if (beforeObj && beforeObj.type != obj.type && beforeObj.type != 3) {
//                 if (direction == "down") {
//                     h_index += 1;
//                     v_index -= 1;
//                     direction = "right";
//                     let rightObj = data[v_index][h_index];
//                     if (beforeObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     beforeObj = rightObj;
//                 } else if (direction == "right") {
//                     if (v_index >= 5) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     v_index += 1;
//                     direction = "down";
//                     let downObj = data[v_index][h_index];
//                     if (beforeObj.type != downObj.type && beforeObj.type != 3) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     beforeObj = downObj;
//                 }
//             } else {
//                 beforeObj = obj
//             }
//             // beforeObj = obj;
//         } else {
//             if (direction == "down") {
//                 h_index += 1;
//                 v_index -= 1;
//                 direction = "right";
//                 let rightObj = data[v_index][h_index];
//                 if (!data[v_index]) {
//                     data[v_index] = [];
//                 }
//                 if (rightObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
//                     isQueryEnd = true;
//                     return length;
//                 }
//                 beforeObj = rightObj;
//             } else {
//                 if (v_index == 5) {
//                     direction = "right";
//                     h_index += 1;
//                     v_index -= 1;
//                     if (!data[v_index]) {
//                         data[v_index] = [];
//                     }
//                     let rightObj = data[v_index][h_index];
//                     if (!beforeObj) {//TODO ???
//                         isQueryEnd = true;
//                         return length
//                     }
//                     if (!rightObj) {//TODO ???
//                         rightObj = {};
//                     }
//                     if (beforeObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     beforeObj = rightObj;
//                 } else {
//                     v_index += 1;
//                     h_index -= 1;
//                     direction = "down";
//                     // if(data[v_index]){
//                     //     data[v_index] = [];
//                     // }
//                     let downObj = data[v_index][h_index];
//                     if (beforeObj && downObj && beforeObj.type != downObj.type && beforeObj.type != 3) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     beforeObj = downObj;
//                 }
//             }
//         }
//         if (direction == "down") {
//             if (v_index >= 5) {
//                 v_index = 5;
//                 h_index += 1;
//                 direction = "right";
//             } else {
//                 v_index++;
//             }
//         } else {
//             h_index++;
//         }
//         length++;
//     }
//     return length;
// }
// drawGrid();






























// /**
//  * Created by admin on 2018/11/20.
//  */

// //走势图
// function drawGrid2() {
//     var drawList = $(".tableItem2");
//     for (let i = 0; i < drawList.length; ++i) {
//         let drawItem = drawList[i];
//         drawRow2(drawItem, 1);
//     }
// }

// function drawFromWebRoomInfo2(data, betObj) {
//     var arr = [];
//     for (let i = 0; i < data.length; ++i) {
//         let item = data[i];
//         for (let j = 0; j < item.length; j++) {//  5: "龙",6: "和",7: "虎"
//             if (item[j] == 5) {
//                 arr.push(1);
//             } else if (item[j] == 6) {
//                 arr.push(3);
//             } else if (item[j] == 7) {
//                 arr.push(2)
//             }
//         }
//     }
//     console.log(betObj.road_arr);
//     betObj.road_arr = betObj.road_arr.concat(arr);
//     var diveData = updateTableData2(JSON.parse(JSON.stringify(arr)));
// }

// function drawFromWebResult2(res, betObj) {
//     // console.log("重新绘制 2：" ,betObj.road_arr)
//     if (res == 5) {
//         betObj.road_arr = betObj.road_arr.concat([1]);
//     } else if (res == 6) {
//         betObj.road_arr = betObj.road_arr.concat([3]);
//     } else if (res == 7) {
//         betObj.road_arr = betObj.road_arr.concat([2]);
//     }
//     var diveData = updateTableData2(JSON.parse(JSON.stringify(betObj.road_arr)));
// }

// function drawRow2(item, type) {
//     var list = [];
//     let length = 38;
//     if (type == 1) {
//         list = $(".tableGrid2");
//         length = 38;
//     }

//     for (let i = 0; i < length; ++i) {
//         $(item).append($(list[0]).clone());
//     }
// }




// function updateTableData2(data) {
//     var drawList = $(".tableItem2");
//     // let drawItem = drawList[0];
//     console.log(1, data)
//     var divideData = divideDataArrNew2(data);
//     for (let i = 0; i < 6; ++i) {
//         let drawItem = drawList[i];
//         for (let j = 0; j < 38; j++) {
//             let grid = drawItem.children[j];
//             let red = grid.children[0];
//             let blue = grid.children[1];
//             let yellow = grid.children[2];
//             red.style.display = "none";
//             blue.style.display = "none";
//             yellow.style.display = "none";
//         }
//     }
//     // console.log(divideData);
//     for (let i = 0; i < divideData.length; ++i) {
//         // console.log("11111111")
//         let drawItem = drawList[i];
//         let itemData = divideData[i];
//         for (let j = 0; j < itemData.length; ++j) {
//             let typeData = itemData[j];
//             var grid = drawItem.children[j];
//             // console.log(typeData)
//             if (grid) {
//                 var red = grid.children[0];
//                 var blue = grid.children[1];
//                 var yellow = grid.children[2];
//                 if (typeData) {
//                     switch (typeData.type) {
//                         case 1:
//                             red.style.display = "block";
//                             blue.style.display = "none";
//                             yellow.style.display = "none";
//                             if (typeData.num > 0) {
//                                 red.innerHTML = typeData.num;
//                             } else {
//                                 red.innerHTML = "";
//                             }
//                             red.style.color = "white";
//                             red.style.fontSize = "4px";
//                             red.style.lineHeight = "14px";
//                             red.style.textAlign = "center";
//                             break;
//                         case 2:
//                             red.style.display = "none";
//                             blue.style.display = "block";
//                             yellow.style.display = "none";
//                             if (typeData.num > 0) {
//                                 blue.innerHTML = typeData.num;
//                             } else {
//                                 blue.innerHTML = "";
//                             }
//                             blue.style.color = "white";
//                             blue.style.fontSize = "4px";
//                             blue.style.lineHeight = "14px";
//                             blue.style.textAlign = "center";
//                             break;
//                         case 3:
//                             red.style.display = "none";
//                             blue.style.display = "none";
//                             yellow.style.display = "block";

//                             if (typeData.num > 0) {
//                                 yellow.innerHTML = typeData.num;
//                             } else {
//                                 yellow.innerHTML = "";
//                             }
//                             yellow.style.color = "white";
//                             yellow.style.fontSize = "4px";
//                             yellow.style.lineHeight = "14px";
//                             yellow.style.textAlign = "center";
//                             break;
//                     }
//                 } else {
//                     red.style.display = "none";
//                     blue.style.display = "none";
//                     yellow.style.display = "none";
//                 }
//             }
//         }
//     }
//     return divideData;
// }

// // Array.dim = function (dimension, initial) {
// //     var a = [], i;
// //     for (i = 0; i < dimension; i++) {
// //         a[i] = initial;
// //     }
// //     return a;
// // };


// function checkFirstEmpytIndex2(arr) {
//     let itemArr = arr[0]//只检测最上排的
//     for (let i = 0; i < itemArr.length; ++i) {
//         if (!itemArr[i]) {
//             return i;
//         }
//     }
//     return itemArr.length
// }

// /**
//  * 大路数据的拆分
//  * @param data
//  * @returns {*[]}
//  */
// function divideDataArrNew2(data) {
//     console.log(data)
//     let girdNum = 39;
//     var arr = [
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//     ];
//     console.log(arr)
//     var isFinish = false;
//     arr[0][0] = {
//         type: data.shift(),
//         num: 0
//     };
//     var clearFinish = false;
//     var v_index = 1;
//     var h_index = 0;
//     var bh_index = 0;
//     var bv_index = 0;
//     while (!clearFinish) {
//         var t = data[0];
//         if (t == 3) {
//             t = data.shift();
//         }
//         if (t == 3 && arr[0][0].type == 3) {
//             arr[v_index > 5 ? 5 : v_index][h_index] = {
//                 type: t,
//                 num: 0
//             };
//             bv_index = v_index;
//             v_index++;
//             if (v_index > 5) {
//                 bh_index = h_index;
//                 bv_index = 0;
//                 h_index++;
//             }
//         } else {
//             clearFinish = true;
//         }
//     }
//     var direction = "down";
//     if (v_index >= 5) {
//         v_index = 5;
//         direction = "right";
//     }
//     while (!isFinish) {
//         let num = data.shift();
//         if (direction == "down") { //向下运动时
//             if (!arr[v_index]) {
//                 arr[v_index] = [];
//             }
//             if (arr[v_index][h_index]) {//如果存在值，则网上退一格，往右进一个
//                 v_index -= 1; //这里进行重新赋值是应为当前要绘制的空格已经被占用
//                 h_index += 1;
//             }
//         } else {
//             if (!arr[v_index + 1]) {
//                 arr[v_index + 1] = [];
//             }
//             if (v_index < 5 && !arr[v_index + 1][h_index]) {//向右边移动的时候如果该格子的下方已经不存在时，下次向下移动
//                 direction = "down"; //该处不需要重新赋值是应为判断的格子是下一个回合绘制的点
//             }
//         }

//         if (num != 3) {
//             var beforeObj = arr[bv_index][bh_index];
//             if (beforeObj && beforeObj.type != num) {
//                 if (beforeObj.type != 3) {
//                     h_index = checkFirstEmpytIndex2(arr);
//                     v_index = 0;
//                     direction = "down";
//                 }
//             } else {
//                 if (v_index < 5 && h_index > 1) {
//                     if (!arr[v_index + 1]) {
//                         arr[v_index + 1] = [];
//                     }
//                     let downObj = arr[v_index + 1][h_index];
//                     let leftDownObj = arr[v_index + 1][h_index - 1];
//                     if (downObj && downObj.type == num) {
//                         v_index -= 1;
//                         if (v_index < 0) {
//                             v_index = 0;
//                         } else {
//                             h_index += 1;
//                         }
//                     } else if ((leftDownObj && leftDownObj.type == num)) {
//                         direction = "right";
//                     }
//                 } else if (v_index == 5) {
//                     if (!arr[v_index - 1]) {
//                         arr[v_index - 1] = [];
//                     }
//                     if (!arr[v_index]) {
//                         arr[v_index] = [];
//                     }
//                     let leftObj = arr[v_index][h_index - 1];
//                     let upObj = arr[v_index - 1][h_index];
//                     if (leftObj && leftObj.type == num && upObj && upObj.type == num) {
//                         v_index -= 1;
//                         h_index += 1;
//                     }
//                 }
//             }
//             bh_index = h_index;
//             bv_index = v_index;
//             if (!arr[v_index]) {
//                 arr[v_index] = [];
//             }
//             arr[v_index][h_index] = {
//                 type: num,
//                 num: 0
//             }
//         } else {
//             h_index = bh_index;
//             v_index = bv_index;
//             arr[bv_index][bh_index].num += 1;
//         }
//         if (direction == "down") {
//             if (v_index == 5) {
//                 direction = "right";
//                 h_index++;
//             } else {
//                 v_index++;
//             }
//         } else {
//             h_index++;
//         }
//         if (data.length == 0) {
//             isFinish = true;
//         }
//     }
//     if (h_index >= 30) {
//         arr[0] = arr[0].splice(h_index - 30, h_index);
//         arr[1] = arr[1].splice(h_index - 30, h_index);
//         arr[2] = arr[2].splice(h_index - 30, h_index);
//         arr[3] = arr[3].splice(h_index - 30, h_index);
//         arr[4] = arr[4].splice(h_index - 30, h_index);
//         arr[5] = arr[5].splice(h_index - 30, h_index);

//     }
//     // console.log(h_index)
//     return arr;
// }



// function divideBigEyeData2(data) {
//     // var daluData = divideDataArrNew(data);
//     // console.log(data)
//     var beginIndex = 1;
//     var isFinish = false;
//     var length = queryRoadLength2(2, data);
//     // console.log(length)
//     // while (!isFinish){
//     //
//     // }
// }

// function queryRoadLength2(index, data) {
//     var isQueryEnd = false;
//     var h_index = index;
//     var v_index = 0;
//     var length = 0;
//     var direction = "down";
//     var beforeObj = data[v_index][h_index];
//     v_index = 1;
//     length = 1;
//     if (!beforeObj) return 0;
//     if (!data) return 0;
//     while (!isQueryEnd) {
//         var obj = data[v_index][h_index];
//         if (obj) {
//             if (beforeObj && beforeObj.type != obj.type && beforeObj.type != 3) {
//                 if (direction == "down") {
//                     h_index += 1;
//                     v_index -= 1;
//                     direction = "right";
//                     let rightObj = data[v_index][h_index];
//                     if (beforeObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     beforeObj = rightObj;
//                 } else if (direction == "right") {
//                     if (v_index >= 5) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     v_index += 1;
//                     direction = "down";
//                     let downObj = data[v_index][h_index];
//                     if (beforeObj.type != downObj.type && beforeObj.type != 3) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     beforeObj = downObj;
//                 }
//             } else {
//                 beforeObj = obj
//             }
//             // beforeObj = obj;
//         } else {
//             if (direction == "down") {
//                 h_index += 1;
//                 v_index -= 1;
//                 direction = "right";
//                 let rightObj = data[v_index][h_index];
//                 if (!data[v_index]) {
//                     data[v_index] = [];
//                 }
//                 if (rightObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
//                     isQueryEnd = true;
//                     return length;
//                 }
//                 beforeObj = rightObj;
//             } else {
//                 if (v_index == 5) {
//                     direction = "right";
//                     h_index += 1;
//                     v_index -= 1;
//                     if (!data[v_index]) {
//                         data[v_index] = [];
//                     }
//                     let rightObj = data[v_index][h_index];
//                     if (!beforeObj) {//TODO ???
//                         isQueryEnd = true;
//                         return length
//                     }
//                     if (!rightObj) {//TODO ???
//                         rightObj = {};
//                     }
//                     if (beforeObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     beforeObj = rightObj;
//                 } else {
//                     v_index += 1;
//                     h_index -= 1;
//                     direction = "down";
//                     // if(data[v_index]){
//                     //     data[v_index] = [];
//                     // }
//                     let downObj = data[v_index][h_index];
//                     if (beforeObj && downObj && beforeObj.type != downObj.type && beforeObj.type != 3) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     beforeObj = downObj;
//                 }
//             }
//         }
//         if (direction == "down") {
//             if (v_index >= 5) {
//                 v_index = 5;
//                 h_index += 1;
//                 direction = "right";
//             } else {
//                 v_index++;
//             }
//         } else {
//             h_index++;
//         }
//         length++;
//     }
//     return length;
// }
// drawGrid2();

















// /**
//  * Created by admin on 2018/11/20.
//  */

// //走势图
// function drawGrid3() {
//     var drawList = $(".tableItem3");
//     for (let i = 0; i < drawList.length; ++i) {
//         let drawItem = drawList[i];
//         drawRow3(drawItem, 1);
//     }
// }

// function drawFromWebRoomInfo3(data, betObj) {
//     var arr = [];
//     for (let i = 0; i < data.length; ++i) {
//         let item = data[i];
//         for (let j = 0; j < item.length; j++) {//  5: "龙",6: "和",7: "虎"
//             if (item[j] == 5) {
//                 arr.push(1);
//             } else if (item[j] == 6) {
//                 arr.push(3);
//             } else if (item[j] == 7) {
//                 arr.push(2)
//             }
//         }
//     }
//     console.log(betObj.road_arr);
//     betObj.road_arr = betObj.road_arr.concat(arr);
//     var diveData = updateTableData3(JSON.parse(JSON.stringify(arr)));
// }

// function drawFromWebResult3(res, betObj) {
//     // console.log("重新绘制 2：" ,betObj.road_arr)
//     if (res == 5) {
//         betObj.road_arr = betObj.road_arr.concat([1]);
//     } else if (res == 6) {
//         betObj.road_arr = betObj.road_arr.concat([3]);
//     } else if (res == 7) {
//         betObj.road_arr = betObj.road_arr.concat([2]);
//     }
//     var diveData = updateTableData3(JSON.parse(JSON.stringify(betObj.road_arr)));
// }

// function drawRow3(item, type) {
//     var list = [];
//     let length = 38;
//     if (type == 1) {
//         list = $(".tableGrid3");
//         length = 38;
//     }

//     for (let i = 0; i < length; ++i) {
//         $(item).append($(list[0]).clone());
//     }
// }




// function updateTableData3(data) {
//     var drawList = $(".tableItem3");
//     // let drawItem = drawList[0];
//     console.log(1, data)
//     var divideData = divideDataArrNew3(data);
//     for (let i = 0; i < 6; ++i) {
//         let drawItem = drawList[i];
//         for (let j = 0; j < 38; j++) {
//             let grid = drawItem.children[j];
//             let red = grid.children[0];
//             let blue = grid.children[1];
//             let yellow = grid.children[2];
//             red.style.display = "none";
//             blue.style.display = "none";
//             yellow.style.display = "none";
//         }
//     }
//     // console.log(divideData);
//     for (let i = 0; i < divideData.length; ++i) {
//         // console.log("11111111")
//         let drawItem = drawList[i];
//         let itemData = divideData[i];
//         for (let j = 0; j < itemData.length; ++j) {
//             let typeData = itemData[j];
//             var grid = drawItem.children[j];
//             // console.log(typeData)
//             if (grid) {
//                 var red = grid.children[0];
//                 var blue = grid.children[1];
//                 var yellow = grid.children[2];
//                 if (typeData) {
//                     switch (typeData.type) {
//                         case 1:
//                             red.style.display = "block";
//                             blue.style.display = "none";
//                             yellow.style.display = "none";
//                             if (typeData.num > 0) {
//                                 red.innerHTML = typeData.num;
//                             } else {
//                                 red.innerHTML = "";
//                             }
//                             red.style.color = "white";
//                             red.style.fontSize = "4px";
//                             red.style.lineHeight = "14px";
//                             red.style.textAlign = "center";
//                             break;
//                         case 2:
//                             red.style.display = "none";
//                             blue.style.display = "block";
//                             yellow.style.display = "none";
//                             if (typeData.num > 0) {
//                                 blue.innerHTML = typeData.num;
//                             } else {
//                                 blue.innerHTML = "";
//                             }
//                             blue.style.color = "white";
//                             blue.style.fontSize = "4px";
//                             blue.style.lineHeight = "14px";
//                             blue.style.textAlign = "center";
//                             break;
//                         case 3:
//                             red.style.display = "none";
//                             blue.style.display = "none";
//                             yellow.style.display = "block";

//                             if (typeData.num > 0) {
//                                 yellow.innerHTML = typeData.num;
//                             } else {
//                                 yellow.innerHTML = "";
//                             }
//                             yellow.style.color = "white";
//                             yellow.style.fontSize = "4px";
//                             yellow.style.lineHeight = "14px";
//                             yellow.style.textAlign = "center";
//                             break;
//                     }
//                 } else {
//                     red.style.display = "none";
//                     blue.style.display = "none";
//                     yellow.style.display = "none";
//                 }
//             }
//         }
//     }
//     return divideData;
// }

// // Array.dim = function (dimension, initial) {
// //     var a = [], i;
// //     for (i = 0; i < dimension; i++) {
// //         a[i] = initial;
// //     }
// //     return a;
// // };


// function checkFirstEmpytIndex3(arr) {
//     let itemArr = arr[0]//只检测最上排的
//     for (let i = 0; i < itemArr.length; ++i) {
//         if (!itemArr[i]) {
//             return i;
//         }
//     }
//     return itemArr.length
// }

// /**
//  * 大路数据的拆分
//  * @param data
//  * @returns {*[]}
//  */
// function divideDataArrNew3(data) {
//     console.log(data)
//     let girdNum = 39;
//     var arr = [
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//         Array.dim(girdNum, 0),
//     ];
//     console.log(arr)
//     var isFinish = false;
//     arr[0][0] = {
//         type: data.shift(),
//         num: 0
//     };
//     var clearFinish = false;
//     var v_index = 1;
//     var h_index = 0;
//     var bh_index = 0;
//     var bv_index = 0;
//     while (!clearFinish) {
//         var t = data[0];
//         if (t == 3) {
//             t = data.shift();
//         }
//         if (t == 3 && arr[0][0].type == 3) {
//             arr[v_index > 5 ? 5 : v_index][h_index] = {
//                 type: t,
//                 num: 0
//             };
//             bv_index = v_index;
//             v_index++;
//             if (v_index > 5) {
//                 bh_index = h_index;
//                 bv_index = 0;
//                 h_index++;
//             }
//         } else {
//             clearFinish = true;
//         }
//     }
//     var direction = "down";
//     if (v_index >= 5) {
//         v_index = 5;
//         direction = "right";
//     }
//     while (!isFinish) {
//         let num = data.shift();
//         if (direction == "down") { //向下运动时
//             if (!arr[v_index]) {
//                 arr[v_index] = [];
//             }
//             if (arr[v_index][h_index]) {//如果存在值，则网上退一格，往右进一个
//                 v_index -= 1; //这里进行重新赋值是应为当前要绘制的空格已经被占用
//                 h_index += 1;
//             }
//         } else {
//             if (!arr[v_index + 1]) {
//                 arr[v_index + 1] = [];
//             }
//             if (v_index < 5 && !arr[v_index + 1][h_index]) {//向右边移动的时候如果该格子的下方已经不存在时，下次向下移动
//                 direction = "down"; //该处不需要重新赋值是应为判断的格子是下一个回合绘制的点
//             }
//         }

//         if (num != 3) {
//             var beforeObj = arr[bv_index][bh_index];
//             if (beforeObj && beforeObj.type != num) {
//                 if (beforeObj.type != 3) {
//                     h_index = checkFirstEmpytIndex3(arr);
//                     v_index = 0;
//                     direction = "down";
//                 }
//             } else {
//                 if (v_index < 5 && h_index > 1) {
//                     if (!arr[v_index + 1]) {
//                         arr[v_index + 1] = [];
//                     }
//                     let downObj = arr[v_index + 1][h_index];
//                     let leftDownObj = arr[v_index + 1][h_index - 1];
//                     if (downObj && downObj.type == num) {
//                         v_index -= 1;
//                         if (v_index < 0) {
//                             v_index = 0;
//                         } else {
//                             h_index += 1;
//                         }
//                     } else if ((leftDownObj && leftDownObj.type == num)) {
//                         direction = "right";
//                     }
//                 } else if (v_index == 5) {
//                     if (!arr[v_index - 1]) {
//                         arr[v_index - 1] = [];
//                     }
//                     if (!arr[v_index]) {
//                         arr[v_index] = [];
//                     }
//                     let leftObj = arr[v_index][h_index - 1];
//                     let upObj = arr[v_index - 1][h_index];
//                     if (leftObj && leftObj.type == num && upObj && upObj.type == num) {
//                         v_index -= 1;
//                         h_index += 1;
//                     }
//                 }
//             }
//             bh_index = h_index;
//             bv_index = v_index;
//             if (!arr[v_index]) {
//                 arr[v_index] = [];
//             }
//             arr[v_index][h_index] = {
//                 type: num,
//                 num: 0
//             }
//         } else {
//             h_index = bh_index;
//             v_index = bv_index;
//             arr[bv_index][bh_index].num += 1;
//         }
//         if (direction == "down") {
//             if (v_index == 5) {
//                 direction = "right";
//                 h_index++;
//             } else {
//                 v_index++;
//             }
//         } else {
//             h_index++;
//         }
//         if (data.length == 0) {
//             isFinish = true;
//         }
//     }
//     if (h_index >= 30) {
//         arr[0] = arr[0].splice(h_index - 30, h_index);
//         arr[1] = arr[1].splice(h_index - 30, h_index);
//         arr[2] = arr[2].splice(h_index - 30, h_index);
//         arr[3] = arr[3].splice(h_index - 30, h_index);
//         arr[4] = arr[4].splice(h_index - 30, h_index);
//         arr[5] = arr[5].splice(h_index - 30, h_index);

//     }
//     // console.log(h_index)
//     return arr;
// }



// function divideBigEyeData3(data) {
//     // var daluData = divideDataArrNew(data);
//     // console.log(data)
//     var beginIndex = 1;
//     var isFinish = false;
//     var length = queryRoadLength3(2, data);
//     // console.log(length)
//     // while (!isFinish){
//     //
//     // }
// }

// function queryRoadLength3(index, data) {
//     var isQueryEnd = false;
//     var h_index = index;
//     var v_index = 0;
//     var length = 0;
//     var direction = "down";
//     var beforeObj = data[v_index][h_index];
//     v_index = 1;
//     length = 1;
//     if (!beforeObj) return 0;
//     if (!data) return 0;
//     while (!isQueryEnd) {
//         var obj = data[v_index][h_index];
//         if (obj) {
//             if (beforeObj && beforeObj.type != obj.type && beforeObj.type != 3) {
//                 if (direction == "down") {
//                     h_index += 1;
//                     v_index -= 1;
//                     direction = "right";
//                     let rightObj = data[v_index][h_index];
//                     if (beforeObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     beforeObj = rightObj;
//                 } else if (direction == "right") {
//                     if (v_index >= 5) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     v_index += 1;
//                     direction = "down";
//                     let downObj = data[v_index][h_index];
//                     if (beforeObj.type != downObj.type && beforeObj.type != 3) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     beforeObj = downObj;
//                 }
//             } else {
//                 beforeObj = obj
//             }
//             // beforeObj = obj;
//         } else {
//             if (direction == "down") {
//                 h_index += 1;
//                 v_index -= 1;
//                 direction = "right";
//                 let rightObj = data[v_index][h_index];
//                 if (!data[v_index]) {
//                     data[v_index] = [];
//                 }
//                 if (rightObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
//                     isQueryEnd = true;
//                     return length;
//                 }
//                 beforeObj = rightObj;
//             } else {
//                 if (v_index == 5) {
//                     direction = "right";
//                     h_index += 1;
//                     v_index -= 1;
//                     if (!data[v_index]) {
//                         data[v_index] = [];
//                     }
//                     let rightObj = data[v_index][h_index];
//                     if (!beforeObj) {//TODO ???
//                         isQueryEnd = true;
//                         return length
//                     }
//                     if (!rightObj) {//TODO ???
//                         rightObj = {};
//                     }
//                     if (beforeObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     beforeObj = rightObj;
//                 } else {
//                     v_index += 1;
//                     h_index -= 1;
//                     direction = "down";
//                     // if(data[v_index]){
//                     //     data[v_index] = [];
//                     // }
//                     let downObj = data[v_index][h_index];
//                     if (beforeObj && downObj && beforeObj.type != downObj.type && beforeObj.type != 3) {
//                         isQueryEnd = true;
//                         return length;
//                     }
//                     beforeObj = downObj;
//                 }
//             }
//         }
//         if (direction == "down") {
//             if (v_index >= 5) {
//                 v_index = 5;
//                 h_index += 1;
//                 direction = "right";
//             } else {
//                 v_index++;
//             }
//         } else {
//             h_index++;
//         }
//         length++;
//     }
//     return length;
// }
// drawGrid3();