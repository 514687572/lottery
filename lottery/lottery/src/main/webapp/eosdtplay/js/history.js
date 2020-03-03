/**
 *
 *  Created by admin on 2018/11/19.
 */
/*setInterval(function () {
 // console.log($(".userProfit").length)
 updateHistory();
 }, 1000);*/
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
function updateHistory(data) {//投注直播区域

    let parent = $(".userProfita");
    let list = $(".userProfit");
    let clone = $(list[0]).clone();
    // $(list[0]).hide();
    // $(list[0]).animate({"margin-top":'-58px'},1000,function () {
    //     list[0].remove();userProfit
    // })
    var name = data.userId; //"keymean" + parseInt(Math.random() * 10);
    var eosNum = data.money;//(Math.random() * 10).toFixed(3);
    var id = data.qid;//25685511 + parseInt(Math.random() * 99)
    var openTime = new Date(data.time);
    var mad = openTime.format("MM/dd");//openTime.getMonth() + "/" + (openTime.getDate() > 9 ? openTime.getDate() : "0"+ openTime.getDate());
    var sfm = openTime.format("hh:mm:ss");
    if(eosNum > 0){
        eosNum = "+" + eosNum
    }
    // console.log(mad)
    if (data.type == 1) {
        clone.html(`
           <p class="userDetail" style="visibility: visible"><span>${name}</span><span class="winR">${eosNum}<b>EOS</b></span></p>
            <div>
                <span>${id}</span>-
                <span>${mad}</span>
                <span>${sfm}</span>
            </div>
     `)
    } else {
        clone.html(`
           <p class="userDetail" style="visibility: visible"><span>${name}</span><span class="win">${eosNum}<b>EOS</b></span></p>
            <div>
                <span>${id}</span>-
                <span>${mad}</span>
                <span>${sfm}</span>
            </div>
     `)
    }
    parent.prepend(clone);
    if (list.length > 4) {
        list[list.length - 1].remove();
    }
}
// $(".smartmarquee").smartmarquee();