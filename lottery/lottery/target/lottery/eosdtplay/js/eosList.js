/**
 * Created by admin on 2018/11/15.
 */
/**
 * 替换显示列表颜色
 * @param fullStr 完整数据字符串
 * @param selectStr 中奖字符
 * @returns {*}
 */
function changeTextColor(fullStr, selectStr) {
    var index = fullStr.lastIndexOf(selectStr);
    if (index >= 0) {
        var start = fullStr.substring(0, index);
        var end = fullStr.substring(index + 1, fullStr.length);
        var newStr = `${start}<span style="color: red">${selectStr}</span>${end} `;//将中奖字母或数字标记为红色
        return newStr;
    }
    return fullStr;
}

function changeTextColor2(fullStr, selectIndex) {
    // console.log(selectIndex)
    if(selectIndex >= 0){
        // console.log("????" + (selectIndex == null))
        var start = fullStr.substring(0, (fullStr.length - 1) - selectIndex);
        var end = fullStr.substring(fullStr.length - selectIndex, fullStr.length);
        return `${start}<span style="color: red">${fullStr[fullStr.length - 1 - selectIndex]}</span>${end}`;//将中奖字母或数字标记为红色;
    }
    return fullStr
}


// /**
//  * 展示中奖数字
//  */
// function showReward(arr) {
//     var list = $("section.kafkItem");
//     for (let i = 0; i < list.length; ++i) {
//         var num1 = $(list[i]).children(".num1");
//         var num2 = $(list[i]).children(".num2");
//         if(i < arr.length){
//             var item = arr[i];
//             var str = this.changeTextColor(item.eosId, item.rewarId);
//             num2.html(`<div class="num2">${str}</div>`)
//         }
//     }
// }

/**
 * 更新EOS数据
 * @param data [{id : 0,eosId : "",rewardId:""}]
 */
/*
 function updateEosData(data) {
 data = [
 {
 id: 11,
 eosId: md5(new Date().getTime() + 1),
 rewarId: parseInt(Math.random() * 10),
 time: new Date().getTime()
 },
 {
 id: 12,
 eosId: md5(new Date().getTime() + 2),
 rewarId: parseInt(Math.random() * 10),
 time: new Date().getTime()
 },
 {
 id: 13,
 eosId: md5(new Date().getTime() + 3),
 rewarId: parseInt(Math.random() * 10),
 time: new Date().getTime()
 },
 {
 id: 14,
 eosId: md5(new Date().getTime() + 4),
 rewarId: parseInt(Math.random() * 10),
 time: new Date().getTime()
 },
 {
 id: 15,
 eosId: md5(new Date().getTime() + 5),
 rewarId: parseInt(Math.random() * 10),
 time: new Date().getTime()
 },
 {
 id: 16,
 eosId: md5(new Date().getTime() + 6),
 rewarId: parseInt(Math.random() * 10),
 time: new Date().getTime()
 },
 {
 id: 17,
 eosId: md5(new Date().getTime() + 7),
 rewarId: parseInt(Math.random() * 10),
 time: new Date().getTime()
 },
 {
 id: 18,
 eosId: md5(new Date().getTime() + 8),
 rewarId: parseInt(Math.random() * 10),
 time: new Date().getTime()
 },
 {
 id: 19,
 eosId: md5(new Date().getTime() + 9),
 rewarId: parseInt(Math.random() * 10),
 time: new Date().getTime()
 },
 {
 id: 20,
 eosId: md5(new Date().getTime() + 9),
 rewarId: parseInt(Math.random() * 10),
 time: new Date().getTime()
 }
 ]
 data = data.sort(function (a, b) {
 return a.id > b.id
 });
 let parent = $("#kfkScroll");
 let list = $("section.kafkItem");
 for (let i = 0; i < data.length; ++i) {
 let clone = $(list[0]).clone();
 let itemData = data[i];
 var num1 = clone.children(".num1");
 num1.text("" + itemData.id);
 var num2 = clone.children(".num2");
 var str = this.changeTextColor(itemData.eosId, itemData.rewarId);
 num2.html(`<div class="num2">${str}</div>`);
 var num3 = clone.children(".num3");
 var timeData = new Date(itemData.time);
 num3.text(/!*itemData.time*!/timeData.getHours() + ":" + timeData.getMinutes() + ":" + timeData.getSeconds());
 parent.append(clone);
 // parent.prepend(clone);
 }
 // showReward(data)
 }*/


 //开奖滚动区域
let carouselAry = [];
let timer = "";
let isStop = false;
function theLotteryList(data) {
    if(!isStop){
        let gameNum = data.open ? data.open.qid : "";
        let blockChainNo = data.num;
        let blockChainHash = "..."+data.hash.substring(data.hash.length - 40,data.hash.length);
        var openTime = new Date(data.time);
        var sfm = openTime.format("hh:mm:ss");
        let carouselObj = {
            "gameNum": gameNum,
            "blockChainNo": blockChainNo,
            "blockChainHash": changeTextColor2(blockChainHash, (data.open ? data.open.index : -1)),
            "theLotteryTime": sfm,
        };
        // console.log(text_msg.block_num);
        if (carouselAry.length <= 7) {
            carouselAry.unshift(carouselObj);
        } else {
            carouselAry.pop();
            carouselAry.unshift(carouselObj);
        }
        let carouselContent = "";
        for (let item of carouselAry) {
            carouselContent += `
                <ul class="lotteryTableTr">
                            <li>${item.gameNum}</li>
                            <li>${item.blockChainNo}</li>
                            <li>${item.blockChainHash}</li>
                            <li>${item.theLotteryTime}</li>
                        </ul>
                `

        }
        // timer = setInterval(() => {
        // }, 400);
        $("#carousel").html(carouselContent);
        $("#carousel>ul:nth-of-type(1)").animate({marginTop: "40px"});
    }
}
//鼠标悬停开奖滚动区域
$(document).ready(function () {
    $("#carousel").on("mouseenter",".lotteryTableTr",function (){
        $(".lotteryTableTr").css("border","none");
        $(this).css({
            "cursor": "pointer",
            "border":"1px solid #3097ff"
        })
        isStop = true;
    });
    $("#carousel").on("mouseleave",".lotteryTableTr",function (){
        isStop = false;
    });
    $("#carousel").on("click",".lotteryTableTr",function(){
        console.log($(this).attr("data-num"));
    })
})