
$(function () {
    ipAdd();
    userName = "";//获取用户名
    $("#logo").hover(function () {
        $("#logo").css({
            "cursor": "pointer"
        });
    });
    $("#logo").click(function () {
        window.location.href = `${ip}`;
    });
    // let transform = 1;
    // $(".nav-pull-down").click(function () {//导航栏选择游戏
    //     if (transform == 1) {
    //         $(".SelectGame").css({
    //             "display": "block"
    //         });
    //         $(".nav-pull-down>img").css({
    //             "transform": "rotate(180deg)"
    //         });
    //         transform = 2
    //     }
    //     else {
    //         $(".SelectGame").css({
    //             "display": "none"
    //         });
    //         $(".nav-pull-down>img").css({
    //             "transform": "rotate(360deg)"
    //         });
    //         transform = 1
    //     }

    // });
    // //导航栏切换游戏
    // $("#tiger").css({
    //     "cursor": "pointer"
    // });
    // $("#tiger").click(function () {
    //     window.location.href = `${ip}/eosdtplay/tiger/home.html`;
    // });
    // $("#lottery").css({
    //     "cursor": "pointer"
    // });
    // $("#lottery").click(function () {
    //     window.location.href = `${ip}/lottery`;
    // });

    //悬停logo和点击logo
    $("#logo").css({
        "cursor": "pointer"
    });
    $("#logo").click(function () {
        window.location.href = `${ip}`;
    });

    let languageType = 1;
    $("#language1").click(function () {//导航栏语言切换下拉
        if (languageType == 1) {
            $("#language2").css({
                "display": "block"
            });
            $("#language1>img:nth-of-type(2)").css({
                "transform": "rotate(180deg)"
            });
            languageType = 2;
        }
        else {
            $("#language2").css({
                "display": "none"
            });
            $("#language1>img:nth-of-type(2)").css({
                "transform": "rotate(360deg)"
            });
            languageType = 1;
        }

    });
    $("#Chinese").hover(function () {
        $("#language2").css({
            "cursor": "pointer"
        });
    });
    $("#English").hover(function () {
        $("#language2").css({
            "cursor": "pointer"
        });
    });
    $("#Chinese").click(function () {//切换语言图标
        let language = "";
        let languageImg = "";
        language = $("#Chinese>span").text();
        languageImg = $("#Chinese>img").attr("src");
        $("#language1>p").text(language);
        $("#language1>img:nth-of-type(1)").attr("src", languageImg);
        $("#language2").css({
            "display": "none"
        });
        $("#language1>img:nth-of-type(2)").css({
            "transform": "rotate(360deg)"
        });
        languageType = 1;
        $.ajax({
            type: "get",
            url: `${ip}/lottery/updLocale?type=1`,
            success: function (mag) {
                window.location.reload();
            }
        });
    });
    $("#English").click(function () {//切换语言图标
        let language = "";
        let languageImg = "";
        language = $("#English>span").text();
        languageImg = $("#English>img").attr("src");
        $("#language1>p").text(language);
        $("#language1>img:nth-of-type(1)").attr("src", languageImg);
        $("#language2").css({
            "display": "none"
        });
        $("#language1>img:nth-of-type(2)").css({
            "transform": "rotate(360deg)"
        });
        languageType = 1;
        $.ajax({
            type: "get",
            url: `${ip}/lottery/updLocale?type=2`,
            success: function (mag) {
                TheCurrentLanguage = 1;
                window.location.reload();
            }
        });
    });
    //切换英文后改变样式
    if (lang == "en_US") {
        // $(".CrapArea").css("width","100%");
        $(".SelectGame>p").css("width", "150px");
        $(".SelectGame").css("left", "20px");
        $(".nav-pull-down").css({
            "width": "150px"
        });
        $(".intervalOrSize").css({
            "width": "300px",
            "margin-left": "auto",
            "margin-right": "auto"
        });
        $(".intervalOrSize>div").css({
            "width": "300px",
            "display": "flex",
            "justify-content": "space-between"
        });
        $(".oddsOrBonus>div").css({
            "width": "250px"
        });

        $("#language1").css("width", "170px");
        $("#language2").css("width", "170px");
        $(".briefIntroduction1-1>div>div:nth-of-type(1)").css("width", "150px");
        // $(".lotteryTableTr>li:nth-of-type(5)").css("width", "150px");
        // $(".lotteryTableTh>li:nth-of-type(5)").css("width", "150px");
        $(".crap1-1>div").css({
            "width": "200px"
        });
        $(".crap1-1>div:nth-of-type(1)").css({
            "width": "200px"
        });
        $("#language1>img:nth-of-type(1)").attr("src", $("#English>img").attr("src"));
        $("#language1>p").text($("#English>span").text());
        $("#mask").css("left","568px");
    }




    $("#iBetTheTable").scroll(function (event) {
        let page1 = 0;
        let distance = "";
        let scrollTop = $(this).scrollTop();
        let scrollHeight = event.target.scrollHeight;
        let windowHeight = $(this).height();
        if (scrollTop + windowHeight == scrollHeight) {
            //            $.message({
            //                message: `${dice117}`,
            //                type: 'warning'
            //            });
            setTimeout(function () {
                page1 += 7
                bettingPage(page1);
                $("#iBetTheTable").scrollTop(scrollTop);
            }, 500);

        }
    });


    // 投注表格区域
    $("#theWinningBetsFont").hover(function () {
        $("#theWinningBetsFont").css({
            "cursor": "pointer"
        });
    });
    $("#iBetTheFont").hover(function () {
        $("#iBetTheFont").css({
            "cursor": "pointer"
        });
    });
    $("#iBetTheFont").click(function () {


        $("#iBetThe").html("");
        $("#theWinningTable").css("display", "none");
        $("#iBetTheTable").css("display", "block");
        $("#iBetTheFont").css({
            "color": "#3097ff",
            "border-bottom": "2px solid #3097ff"
        });
        $("#theWinningBetsFont").css({
            "color": "#ffffff",
            "border-bottom": "none"
        });

        getUserName();
        if ($("#loginBtn").attr("data-state") == 1) {
            userName = "";
        }
        if (type == 1) {
            if ($("#loginBtn").attr("data-state") == 1) {
                userName = "";
            } else {
                userName = name;
            }
        }
        bettingPage(0)
    });

    $("#theWinningBetsFont").click(function () {
        $("#iBetTheTable").css("display", "none");
        $("#theWinningTable").css("display", "block");
        $("#theWinningBetsFont").css({
            "color": "#3097ff",
            "border-bottom": "2px solid #3097ff"
        });
        $("#iBetTheFont").css({
            "color": "#ffffff",
            "border-bottom": "none"
        });
    });

    //游戏帮助区域
    $("#gamesHelpBtn").hover(function () {
        $("#gamesHelpBtn").css({
            "cursor": "pointer"
        });
    });
    $("#gamesHelpBtn").click(function () {
        $("#gameHome").css("display", "none");
        $("#Invite").css("display", "none");
        $("#gamesHelp").css("display", "block");
    });
    $("#goBack").hover(function () {
        $("#goBack").css({
            "cursor": "pointer"
        });
    });
    $("#goBack").click(function () {
        $("#gameHome").css("display", "block");
        $("#gamesHelp").css("display", "none");
    });
    $("#goBack1-1").hover(function () {
        $("#goBack1-1").css({
            "cursor": "pointer"
        });
    });
    $("#goBack1-1").click(function () {
        $("#gameHome").css("display", "block");
        $("#gamesHelp").css("display", "none");
    });

    // 邀请好友区域
    $("#InviteFriends").hover(function () {
        $("#InviteFriends").css({
            "cursor": "pointer"
        });
    });
    $("#InviteFriends").click(function () {
        $("#gameHome").css("display", "none");
        $("#gamesHelp").css("display", "none");
        $("#Invite").css("display", "block");
        if ($("#loginBtn").attr("data-state") == 1) {
            $("#inviteInput").val(`${dice98}`);
        }
        else {
            getUserName();
            Copy();//复制
            TheSubordinateList(1);//获取下级列表和页码
        }
    });
    $("#InviteGoBack").hover(function () {
        $("#InviteGoBack").css({
            "cursor": "pointer"
        });
    });
    $("#InviteGoBack").click(function () {
        $("#gameHome").css("display", "block");
        $("#Invite").css("display", "none");
    });


    //点击我的投注区域记录弹出详情弹窗
    let CPMIssue ="";
    $("#iBetThe").on("click", ".devoteTheResults", function () {
        $("#particularsCPM").css("display", "block");
        $("#backgroundMask").css("display", "block");
        let CPMhash = $(this).attr("data-CPMhash");
        CPMIssue = $(this).attr("data-CPMIssue");
        let CPMnum = $(this).attr("data-CPMnum");
        //获取并转换为相应的时间
        let CPMtime = $(this).attr("data-CPMtime") - 0;
        CPMtime = new Date(CPMtime);
        let CPMhours = CPMtime.getHours();
        if (CPMhours.toString().length == 1) {
            CPMhours = "0" + CPMhours;
        }
        let CPMMinutes = CPMtime.getMinutes();
        if (CPMMinutes.toString().length == 1) {
            CPMMinutes = "0" + CPMMinutes;
        }
        let CPMSeconds = CPMtime.getSeconds();
        if (CPMSeconds.toString().length == 1) {
            CPMSeconds = "0" + CPMSeconds;
        }

        CPMtime = CPMhours + ":" + CPMMinutes + ":" + CPMSeconds;

        //截取21位hash值
        $("#CPMhash").html("");
        let id = CPMhash;

        id = id.split('').reverse();
        let content = "";
        let count = 0;
        for (let i = 0; i < 21; i++) {//判断中奖号
            if (count < 2) {
                if (!isNaN(id[i])) {
                    count++;
                    $("#CPMhash").prepend(`<span style="color: #FF564F">${id[i]}</span>`);

                } else {
                    $("#CPMhash").prepend(`<span>${id[i]}</span>`)
                }
            }
            else {
                $("#CPMhash").prepend(`<span>${id[i]}</span>`)
            }

        }

        $("#CPMnum").text(CPMnum);
        $("#CPMIssue").text(CPMIssue);
        $("#CPMtime").text(CPMtime);
    });

    //点击中奖投注弹出详情弹窗
    $("#allPrizeTable").on("click", ".theWinningBetsTable", function () {
        $("#particularsCPM").css("display", "block");
        $("#backgroundMask").css("display", "block");
        let CPMhash = $(this).attr("data-CPMhash1");
        CPMIssue = $(this).attr("data-CPMIssue1");
        let CPMnum = $(this).attr("data-CPMnum1");
        //获取并转换为相应的时间
        let CPMtime = $(this).attr("data-CPMtime1") - 0;
        CPMtime = new Date(CPMtime);
        let CPMhours = CPMtime.getHours();
        if (CPMhours.toString().length == 1) {
            CPMhours = "0" + CPMhours;
        }
        let CPMMinutes = CPMtime.getMinutes();
        if (CPMMinutes.toString().length == 1) {
            CPMMinutes = "0" + CPMMinutes;
        }
        let CPMSeconds = CPMtime.getSeconds();
        if (CPMSeconds.toString().length == 1) {
            CPMSeconds = "0" + CPMSeconds;
        }

        CPMtime = CPMhours + ":" + CPMMinutes + ":" + CPMSeconds;

        //截取21位hash值
        $("#CPMhash").html("");
        let id = CPMhash;

        id = id.split('').reverse();
        let content = "";
        let count = 0;
        for (let i = 0; i < 21; i++) {//判断中奖号
            if (count < 2) {
                if (!isNaN(id[i])) {
                    count++;
                    $("#CPMhash").prepend(`<span style="color: #FF564F">${id[i]}</span>`);

                } else {
                    $("#CPMhash").prepend(`<span>${id[i]}</span>`)
                }
            }
            else {
                $("#CPMhash").prepend(`<span>${id[i]}</span>`)
            }

        }

        $("#CPMnum").text(CPMnum);
        $("#CPMIssue").text(CPMIssue);
        $("#CPMtime").text(CPMtime);
    });

    //关闭我的投注单条信息弹出开奖详情弹窗
    $("#CPMclose").click(function () {
        $("#particularsCPM").css("display", "none");
        $("#backgroundMask").css("display", "none");
    });
    $(".particularsCPM1-2").click(function () {//点击投注弹窗内hash值进行跳转
        window.open(`https://bloks.io/transaction/${CPMIssue}`);
        $("#particularsCPM").css("display", "none");
        $("#backgroundMask").css("display", "none");
    });
    //关闭蒙板和弹窗
    $("#backgroundMask").click(function () {
        $("#particularsCPM").css("display", "none");
        $("#backgroundMask").css("display", "none");
    });




     // 抵押cpu
     let regOne = new RegExp("^[0-9]+(.[0-9]{0,1})?$");
     $("#cpuImg").click(function (event) {
         event.stopPropagation();
         $("#cpuCPM").css("display", "block");
         let cpuInput = 0;
         $("#cpuInput").blur(function () {
             cpuInput = parseInt($("#cpuInput").val() * 10) / 10;
         });
         $("#cpuComfire").click(function () {
             if (regOne.test(cpuInput) == true && cpuInput >= 0.5) {
                 if (type == 0) {
                     const eosOptions = {
                         expireInSeconds: 60
                     };
                     const eos = scatter.eos(network, Eos, eosOptions);
                     eos.transaction(tr => {
                         tr.delegatebw({
                             from: scatter.identity.accounts[0].name,
                             receiver: scatter.identity.accounts[0].name,
                             stake_net_quantity: "0.0000 EOS",
                             stake_cpu_quantity: `${parseFloat(cpuInput).toFixed(4)} EOS`,
                             transfer: 0
                         });
                     }).then(result => {
                         $.ajax({//请求cpu
                             type: "get",
                             url: `${ip}/account/getAccount.do`,
                             async: false,
                             data: {
                                 account: userName
                             },
                             success: function (data) {
                                $("#userBalance").text(data.balance);
                                 let cpunum=parseFloat((data.cpu.used / data.cpu.max) * 100).toFixed(2);
                                 $("#cpu").text(cpunum+`%`);
                             }
                         });
                         $("#cpuCPM").css("display", "none");
                         $("#cpuInput").val("");
                     })
                 } else {
                     $.ajax({
                         url: `${ip}/account/delegatebw.do`,
                         type: "post",
                         data: {
                             account: userName,
                             quantity: `${parseFloat(cpuInput).toFixed(4)} EOS`
                         },
                         success: function (msg) {
                             $.ajax({//请求cpu
                                 type: "get",
                                 url: `${ip}/account/getAccount.do`,
                                 async: false,
                                 data: {
                                     account: userName
                                 },
                                 success: function (data) {
                                    $("#userBalance").text(data.balance);
                                     let cpunum=parseFloat((data.cpu.used / data.cpu.max) * 100).toFixed(2);
                                     $("#cpu").text(cpunum+`%`);
                                 }
                             });
                             $("#cpuCPM").css("display", "none");
                             $("#cpuInput").val("");
                         }
                     })
                 }
             } else {
                 popup({ type: 'tip', msg: inputAmount, delay: 1000 });
             }
         });
     });
     //关闭抵押cpu弹窗
     $("#cpuCPMCloce").click(function () {
         $("#cpuCPM").css("display", "none");
         $("#cpuInput").val("");
     })
 




})
function Copy() {//邀请好友复制到剪切板
    if (type == 1) {
        userName = name
    }
    $("#inviteInput").val(`${ip}/lottery/toDice?userCode=${userName}`);
    $("#InviteCopy").css({
        "cursor": "pointer"
    });
    $("#InviteCopy").click(function () {
        let Url2 = document.getElementById("inviteInput");
        Url2.select(); // 选择对象
        document.execCommand("Copy"); // 执行浏览器复制命令
        popup({ type: 'success', msg:`${dice100}`, delay: 1000 });
    });

}
function TheSubordinateList(page) {//获取邀请好友页面，下级列表和页码
    if (type == 1) {
        userName = name
    }
    $.ajax({
        type: "get",
        url: `${ip}/user/getMyChildsto.do`,
        async: false,
        data: {
            userName: userName,
            pageNum: page
        },
        success: function (data) {
            console.log(data)
            if (data.msg != false && data.msg != null) {
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
function bettingPage(page) {
    console.log(userName);
    $.ajax({
        type: "get",
        url: `${ip}/dice/getAccountDice`,
        async: false,
        data: {
            account: userName,
            begin: page,
            limit: 7
        },
        success: function (data) {
            let myType = "";//我的投注内容
            let myResult = "";//中奖结果
            let myMoney = "";//奖金
            let myMoneyStyle = "";//金额样式
            let iBetTheContent = "";
            let time = "";//时间
            let dateTime = "";
            for (let item of data.accountDice) {
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
                if (item.state == 0) {
                    myResult = "——";
                    myMoney = "-" + item.bettingEOS;
                    myMoneyStyle = "#3097ff";
                } else {
                    myResult = `${dice99}`;
                    myMoney = "+" + item.prizeEOS;
                    myMoneyStyle = "#BF4B56";
                }
                time = new Date(item.time);
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
                iBetTheContent += `
                <ul class="devoteTheResults" data-CPMIssue=${item.termnumber} data-CPMhash=${item.hash} data-CPMtime=${item.time} data-CPMnum=${item.prizenumber}>
                                <li>${dateTime}</li>
                                <li title=${dice126}>${item.termnumber}</li>
                                <li>${myType}</li>
                                <li>${item.bettingEOS}</li>
                                <li>${myResult}</li>
                                <li  style="color: ${myMoneyStyle}">${myMoney}</li>
                            </ul>
                `

            }
            $("#iBetThe").append(iBetTheContent);
        }
    });
}