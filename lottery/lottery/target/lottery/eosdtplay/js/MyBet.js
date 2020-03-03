/**
 * Created by admin on 2018/12/4.
 */
var myScroll,
    pullDownEl,
    pullDownOffset,
    pullUpEl,
    pullUpOffset,
    generatedCount = 0;

function pullDownAction() {
    setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
        /*  var el, li, i;
         el = document.getElementById('thelist');

         for (i = 0; i < 5; i++) {
         li = document.createElement('li');
         li.innerText = 'Generated row ' + (++generatedCount);
         el.insertBefore(li, el.childNodes[0]);
         }
         document.getElementById("pullUp").style.display = "";*/
        // this.updateItem()
        // myScroll.refresh();
    }, 1000);
}
let oldTime = null;
function pullUpAction() {
    setTimeout(function () {
        /* var el, li, i;
         el = document.getElementById('thelist');

         for (i=0; i<1; i++) {
         li = document.createElement('li');
         li.innerText = 'Generated row ' + (++generatedCount);
         el.appendChild(li, el.childNodes[0]);
         }*/
        // myScroll.refresh();
        // this.updateItem()
        // if (!oldTime) {
        //     oldTime = new Date().getTime();
        // }
        WebsocketUtil.getWebSocketWithUrl().sendData({
            code: MessageType.Up.qureySelf,
            data: {
                time: oldTime
            }
        })
    }, 1000);
}

function updateMyBetItem(data) {//我的投注区域
    var el = $("#thelist");
    let clone = $("#listItemBase").clone();
    let qid = data.qid;
    let opt = data.opt;
    let bet = data.put;
    let time = data.time;
    let reward = data.gain;
    let all = bet;
    let result = "输";
    let className = "win"
    if (reward > 0) {
        reward = "+" + (reward + bet);
        result = "赢"
        className = "winR"
    } else {
        reward = "-"
        result = "输"
        className = "win"
    }
    let optName = BET_NAME[opt];
    var dateTime = new Date(time);
    var sfm = dateTime.format("yyyy/MM/dd hh:mm:ss");
    clone.html(`
          <div class="orderHead">
                       <div class="orderHeadInner">
                            <div>${qid}</div>
                            <div>${optName}</div>
                            <div ><p  class=${className}>${result}</p></div>
                            <div >${bet}</div>
                            <div><p class=${className}>${reward}</p></div>
                            <div>${sfm}</div>
                        </div>
                    </div>
     `)
    el.prepend(clone);
    myScroll.refresh();

}

function updateItemOld(list) {
    if (list && list.length > 0) {
        list = list.sort(function (a, b) {
            return a.time < b.time
        });
        var el = $("#thelist");
        for (let i = 0; i < list.length; ++i) {
            let data = list[i];
            if(i == list.length - 1){
                oldTime = data.time;
            }
            // var el = $("#thelist");
            let clone = $("#listItemBase").clone();
            let qid = data.qid;
            let opt = data.opt;
            let bet = data.put;
            let time = data.time;
            let reward = data.gain;
            // let all = bet;
            let result = "输";
            let className = "win";
            if (reward > 0) {
                reward = "+" + reward;
                result = "赢";
                className = "winR"
            } else {
                reward = "-";
                result = "输";
                className = "win"
            }
            let optName = BET_NAME[opt];
            var dateTime = new Date(time);
            var sfm = dateTime.format("yyyy/MM/dd hh:mm:ss");
            clone.html(`
          <div class="orderHead">
                       <div class="orderHeadInner">
                            <div>${qid}</div>
                            <div>${optName}</div>
                            <div ><p  class=${className}>${result}</p></div>
                            <div >${bet}</div>
                            <div><p class=${className}>${reward}</p></div>
                            <div>${sfm}</div>
                        </div>
                    </div>
     `)
            el.append(clone);
        }
    }
    if(myScroll)myScroll.refresh()
    // myScroll.refresh();
}

function loaded() {//我的投注区域下拉刷新
    pullDownEl = document.getElementById('pullDown');
    pullDownOffset = pullDownEl.offsetHeight;
    pullUpEl = document.getElementById('pullUp');
    pullUpOffset = 10;
    //pullUpOffset = pullUpEl.offsetHeight;
    myScroll = new iScroll('wrapper', {
        useTransition: true,
        topOffset: pullDownOffset,
        onRefresh: function () {
            //that.maxScrollY = that.wrapperH - that.scrollerH + that.minScrollY;
            //that.minScrollY = -that.options.topOffset || 0;
            //alert(this.wrapperH);
            //alert(this.scrollerH);
            if (pullDownEl.className.match('loading')) {
                pullDownEl.className = '';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Pull down to refresh...';
            }
            if (pullUpEl.className.match('loading')) {
                pullUpEl.className = '';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Pull up to load more...';
            }

            document.getElementById("pullUp").style.display = "none";
        },
        onScrollMove: function () {
            /* if (this.y > 0) {
             pullDownEl.className = 'flip';
             pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Release to refresh...';
             this.minScrollY = 0;
             }
             if (this.y < 0 && pullDownEl.className.match('flip')) {
             pullDownEl.className = '';
             pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Pull down to refresh...';
             this.minScrollY = -pullDownOffset;
             }*/

            if (this.scrollerH < this.wrapperH && this.y < (this.minScrollY - pullUpOffset) || this.scrollerH > this.wrapperH && this.y < (this.maxScrollY - pullUpOffset)) {
                document.getElementById("pullUp").style.display = "";
                pullUpEl.className = 'flip';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Release to refresh...';
            }
            if (this.scrollerH < this.wrapperH && this.y > (this.minScrollY - pullUpOffset) && pullUpEl.className.match('flip') || this.scrollerH > this.wrapperH && this.y > (this.maxScrollY - pullUpOffset) && pullUpEl.className.match('flip')) {
                document.getElementById("pullUp").style.display = "none";
                pullUpEl.className = '';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Pull up to load more...';
            }
        },
        onScrollEnd: function () {
            /*  if (pullDownEl.className.match('flip')) {
             pullDownEl.className = 'loading';
             pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Loading...';
             pullDownAction();	// Execute custom function (ajax call?)
             }*/
            if (pullUpEl.className.match('flip')) {
                pullUpEl.className = 'loading';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Loading...';
                pullUpAction();	// Execute custom function (ajax call?)
            }
        }
    });

    //setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);
}

document.addEventListener('touchmove', function (e) {
    e.preventDefault();
}, false);

document.addEventListener('DOMContentLoaded', function () {
    setTimeout(loaded, 200);
}, false);
// updateMyBetItem({"roomId":1001,"qid":1392,"time":1543906350440,"opt":2,"put":10.1,"gain":7.575});