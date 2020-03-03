/**
 * Created by admin on 2018/11/20.
 */

 //走势图
function drawGrid() {
    // var parent = $(".tableRoot");
    var drawList = $(".tableItem");
    for (let i = 0; i < drawList.length; ++i) {
        let drawItem = drawList[i];
        drawRow(drawItem, 1);
    }

    var drawList = $(".tableItem2");
    for (let i = 0; i < drawList.length; ++i) {
        let drawItem = drawList[i];
        drawRow(drawItem, 2);
    }

    var drawList = $(".tableItem3");
    for (let i = 0; i < drawList.length; ++i) {
        let drawItem = drawList[i];
        drawRow(drawItem, 3);
    }
    // var data = [1,2];
    // var diveData = updateTableData(JSON.parse(JSON.stringify(data)));
    // updateTableData3(data)
    // updateBigEye(diveData);
}

function drawFromWebRoomInfo(data,betObj) {
    var arr = [];
    for(let i = 0; i < data.length;++i){
        let item = data[i];
        // console.log(item);
        for(let j = 0; j < item.length;j++){//  5: "龙",6: "和",7: "虎"
            if(item[j] == 5){
                arr.push(1);
            }else if(item[j] == 6){
                arr.push(3);
            }else if(item[j] == 7){
                arr.push(2)
            }
        }
    }
    console.log(betObj.road_arr);
    betObj.road_arr = betObj.road_arr.concat(arr);
    var diveData = updateTableData(JSON.parse(JSON.stringify(arr)));
    updateBigEye(diveData);
    updateTableData3(JSON.parse(JSON.stringify(arr)))
}

function drawFromWebResult(res,betObj) {
    // console.log("重新绘制 2：" ,betObj.road_arr)
    if(res == 5){
        betObj.road_arr = betObj.road_arr.concat([1]);
    }else if(res == 6){
        betObj.road_arr = betObj.road_arr.concat([3]);
    }else if(res == 7){
        betObj.road_arr = betObj.road_arr.concat([2]);
    }
    var diveData = updateTableData(JSON.parse(JSON.stringify(betObj.road_arr)));
    updateBigEye(diveData);
    updateTableData3(JSON.parse(JSON.stringify(betObj.road_arr)))
}

function drawRow(item, type) {
    var list = [];
    let length = 40;
    if (type == 1) {
        list = $(".tableGrid");
        length = 39;
    } else if (type == 2) {
        length = 20;
        list = $(".tableGrid2");
    }else if(type == 3){
        length = 9;
        list = $(".tableGrid3");
    }
    for (let i = 0; i < length; ++i) {
        $(item).append($(list[0]).clone());
    }
}

function updateTableData3(data) {//珠盘路
    var drawList = $(".tableItem3");
    for(let i = 0; i < 6;++i){
        let drawItem = drawList[i];
        for(let j = 0; j < 10; j++){
            let grid = drawItem.children[j];
            let type = grid.children[0];
            type.style.display = "none";
        }
    }
    // console.log(data)
    if(data && data.length > 0){
        let arr = divideData3(JSON.parse(JSON.stringify(data)));
        for(let i = 0; i < arr.length; ++i){
            let item = arr[i];
            let drawItem = drawList[i];
            for(let j = 0; j < item.length;j++){
                // console.log(item[j])
                var grid = drawItem.children[j];
                var girdType = grid.children[0];
                let num = item[j]
                switch (num) {
                    case 1:
                        girdType.style.display = "block";
                        girdType.style.backgroundColor = "#FF5C4A"
                        girdType.innerHTML = "龙";
                        break
                    case 2:
                        girdType.style.display = "block";
                        girdType.style.backgroundColor = "#3499FF";
                        girdType.innerHTML = "虎";
                        break
                }
            }
        }
    }
}

function divideData3(data) {
    let girdNum = 10;
    var arr = [
        Array.dim(girdNum, 0),
        Array.dim(girdNum, 0),
        Array.dim(girdNum, 0),
        Array.dim(girdNum, 0),
        Array.dim(girdNum, 0),
        Array.dim(girdNum, 0),
    ];
    let v_index = 0;
    let h_index = 0;
    while (data.length != 0){
        let num = data.shift();
        if(num != 3){
            if(!arr[v_index]){
                arr[v_index] = [];
            }
            arr[v_index][h_index] = num;
            if(v_index >= 5){
                v_index = 0;
                h_index++;
            }else{
                v_index++;
            }
        }
    }
    if(h_index >= 7){
        arr[0] = arr[0].splice(h_index - 7,h_index);
        arr[1] = arr[1].splice(h_index - 7,h_index);
        arr[2] = arr[2].splice(h_index - 7,h_index);
        arr[3] = arr[3].splice(h_index - 7,h_index);
        arr[4] = arr[4].splice(h_index - 7,h_index);
        arr[5] = arr[5].splice(h_index - 7,h_index);
    }
    console.log(h_index)
    return arr;
    // console.log(arr)
}

function updateTableData(data) {
    var drawList = $(".tableItem");
    // let drawItem = drawList[0];
    console.log(1,data)
    var divideData = divideDataArrNew(data);
    for(let i = 0; i < 6;++i){
        let drawItem = drawList[i];
        for(let j = 0; j < 39; j++){
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
                if(typeData){
                    switch (typeData.type) {
                        case 1:
                            red.style.display = "block";
                            blue.style.display = "none";
                            yellow.style.display = "none";
                            if (typeData.num > 0){
                                red.innerHTML = typeData.num;
                            }else {
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
                            if (typeData.num > 0){
                                blue.innerHTML = typeData.num;
                            }else {
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

                            if (typeData.num > 0){
                                yellow.innerHTML = typeData.num;
                            }else {
                                yellow.innerHTML = "";
                            }
                            yellow.style.color = "white";
                            yellow.style.fontSize = "4px";
                            yellow.style.lineHeight = "14px";
                            yellow.style.textAlign = "center";
                            break;
                    }
                }else{
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

/*function divideDataArr(data) {
 // var data =  fixArrToDouble([1,1,3,3,3,3,3,2,1,2,1,1,1,1,1,1,1,1,1,2,2,2,3,3,3,3,2,2,1]);
 // fixArrToDouble([1,3,3,3,2,1,2,3,3,2]);
 let girdNum = 50;
 var arr = [
 Array.dim(girdNum, 0),
 Array.dim(girdNum, 0),
 Array.dim(girdNum, 0),
 Array.dim(girdNum, 0),
 Array.dim(girdNum, 0),
 Array.dim(girdNum, 0),
 ];
 let h_index = 0;
 let v_index = 0;
 var isFinish = false;
 var direction = "down";
 while (!isFinish) {
 let num = data.shift();
 let heNum = 0;
 if(typeof num == "object"){
 heNum = num.length;
 num = 3;
 }
 if (v_index == 5) {//当走到竖排最后一个的时候
 let beforeNum = checkLastType(arr);
 if(typeof beforeNum == "object"){
 beforeNum = 3;
 }
 if (num == 3 || num == beforeNum || beforeNum == 3) {//当前类型为和的时候或者和之前的数字相同的时候
 if (direction == "down") {//第一次走到最后一个同时类型没有装入数组，只改变下次运动的方向
 direction = "right";
 }
 } else {//当该类型不是之前类型时
 v_index = 0;//往下方向的类型必须从0开始
 h_index = checkFirstEmpytIndex(arr);
 direction = "down";//换列装入时，方向重新从上往下
 }
 } else {
 if (direction == "down") {
 let beforeNum = checkLastType(arr);
 if(typeof beforeNum == "object"){
 beforeNum = 3;
 }
 if (num != 3 && beforeNum != num && v_index > 0) {
 v_index = 0;
 h_index += 1;
 }
 } else if (direction == "right") {//如果是没有走到最底部且在横向移动的时候
 let beforeNum = checkLastType(arr);
 if(typeof beforeNum == "object"){
 beforeNum = 3;
 }
 if (num != 3 && beforeNum != num && v_index > 0) {
 v_index = 0;
 h_index = checkFirstEmpytIndex(arr);
 direction = "down";
 }else{
 let nextVNum = arr[v_index + 1][h_index];
 if (data.length == 0 ) {
 console.log("direction :", direction, "h_index :" + h_index, "v_index :", v_index,"currentNum :",arr[v_index][h_index])
 }
 if (!nextVNum) {
 direction = "down";
 }
 }
 }
 }
 let currentNum = arr[v_index][h_index];//获取当前要装入的格子是否有数字
 if (currentNum) {//当前位置存在值的时候
 v_index -= 1;//倒回一个，方向向右据需
 if (arr[v_index - 1][h_index]) {
 h_index += 1;
 }
 direction = "right"
 }
 if(heNum > 0){
 arr[v_index][h_index] = {
 num: heNum
 }
 }else{
 arr[v_index][h_index] = num;
 }
 if (v_index != 5 && direction == "down")v_index++;
 if (direction == "right"){
 if(v_index < 5 && !arr[v_index + 1][h_index]){
 direction = "down";
 v_index+=1;
 }else{
 h_index++;
 }
 }
 if (data.length == 0) {
 isFinish = true;
 }
 }
 return arr;
 }*/

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
            if(!arr[v_index]){
                arr[v_index] = [];
            }
            if (arr[v_index][h_index]) {//如果存在值，则网上退一格，往右进一个
                v_index -= 1; //这里进行重新赋值是应为当前要绘制的空格已经被占用
                h_index += 1;
            }
        } else {
            if(!arr[v_index + 1]){
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
                    if(!arr[v_index + 1]){
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
                    if(!arr[v_index - 1]){
                        arr[v_index - 1] = [];
                    }
                    if(!arr[v_index]){
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
            if(!arr[v_index]){
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
    if(h_index >= 30){
        arr[0] = arr[0].splice(h_index - 30,h_index);
        arr[1] = arr[1].splice(h_index - 30,h_index);
        arr[2] = arr[2].splice(h_index - 30,h_index);
        arr[3] = arr[3].splice(h_index - 30,h_index);
        arr[4] = arr[4].splice(h_index - 30,h_index);
        arr[5] = arr[5].splice(h_index - 30,h_index);

    }
    // console.log(h_index)
    return arr;
}

function updateBigEye(data) {
    var drawList = $(".tableItem2");
    var bigEyeList = [drawList[0], drawList[1], drawList[2]];
    var newBigEyeList = [[], [], [], [], [], []];
    for (let i = 0; i < bigEyeList.length; ++i) {
        let drawItem = bigEyeList[i];
        for (let j = 0; j < drawItem.children.length; j++) {
            let item = drawItem.children[j];
            if (i == 0) {
                newBigEyeList[0].push(item.children[0]);
                newBigEyeList[0].push(item.children[2]);
                newBigEyeList[1].push(item.children[1]);
                newBigEyeList[1].push(item.children[3])
            } else if (i == 1) {
                newBigEyeList[2].push(item.children[0]);
                newBigEyeList[2].push(item.children[2]);
                newBigEyeList[3].push(item.children[1]);
                newBigEyeList[3].push(item.children[3])
            } else if (i == 2) {
                newBigEyeList[4].push(item.children[0]);
                newBigEyeList[4].push(item.children[2]);
                newBigEyeList[5].push(item.children[1]);
                newBigEyeList[5].push(item.children[3])
            }
        }
    }
    divideBigEyeData(data);
    for (let i = 0; i < newBigEyeList.length; ++i) {
        let item = newBigEyeList[i];
        if (i == 0) {
            for (let j = 0; j < item.length; ++j) {
                let grid = item[j];
                // grid.style.visibility = "visible";
            }
            // console.log(grid)
        }
    }
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
    if (!beforeObj)return 0;
    if (!data)return 0;
    while (!isQueryEnd) {
        var obj = data[v_index][h_index];
        if (obj) {
            if (beforeObj && beforeObj.type != obj.type && beforeObj.type != 3) {
                if(direction == "down"){
                    h_index+=1;
                    v_index-=1;
                    direction = "right";
                    let rightObj = data[v_index][h_index];
                    if (beforeObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
                        isQueryEnd = true;
                        return length;
                    }
                    beforeObj = rightObj;
                }else if(direction == "right"){
                    if(v_index >= 5){
                        isQueryEnd = true;
                        return length;
                    }
                    v_index+=1;
                    direction = "down";
                    let downObj = data[v_index][h_index];
                    if (beforeObj.type != downObj.type && beforeObj.type != 3) {
                        isQueryEnd = true;
                        return length;
                    }
                    beforeObj = downObj;
                }
            }else{
                beforeObj = obj
            }
            // beforeObj = obj;
        } else {
            if(direction == "down"){
                h_index+=1;
                v_index-=1;
                direction = "right";
                let rightObj = data[v_index][h_index];
                if(!data[v_index]){
                    data[v_index] = [];
                }
                if (rightObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
                    isQueryEnd = true;
                    return length;
                }
                beforeObj = rightObj;
            }else{
                if(v_index == 5){
                    direction = "right";
                    h_index+=1;
                    v_index-=1;
                    if(!data[v_index]){
                        data[v_index] = [];
                    }
                    let rightObj = data[v_index][h_index];
                    if(!beforeObj){//TODO ???
                        isQueryEnd = true;
                        return length
                    }
                    if(!rightObj){//TODO ???
                        rightObj = {};
                    }
                    if (beforeObj && beforeObj.type != rightObj.type && beforeObj.type != 3) {
                        isQueryEnd = true;
                        return length;
                    }
                    beforeObj = rightObj;
                }else{
                    v_index+=1;
                    h_index-=1;
                    direction = "down";
                    // if(data[v_index]){
                    //     data[v_index] = [];
                    // }
                    let downObj = data[v_index][h_index];
                    if (beforeObj &&downObj&& beforeObj.type != downObj.type && beforeObj.type != 3) {
                        isQueryEnd = true;
                        return length;
                    }
                    beforeObj = downObj;
                }
            }
        }
        if(direction == "down"){
            if(v_index>= 5){
                v_index = 5;
                h_index+=1;
                direction = "right";
            }else{
                v_index++;
            }
        }else{
            h_index++;
        }
        length++;
    }
    return length;
}
drawGrid();