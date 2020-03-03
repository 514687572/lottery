/**
 * Created by admin on 2018/11/14.
 */
//弹窗的开关
// function show()
// {
//     var login = document.getElementById('popWindow');
//     var over = document.getElementById('cover');
//     login.style.display = "block";
//     over.style.display = "block";
// }
// function hide()
// {
//     var login = document.getElementById('popWindow');
//     var over = document.getElementById('cover');
//     login.style.display = "none";
//     over.style.display = "none";
// }

// function showInvitation()
// {
//     var login = document.getElementById('popInvitation');
//     var over = document.getElementById('cover');
//     login.style.display = "block";
//     over.style.display = "block";
// }
// function hideInvitation()
// {
//     var login = document.getElementById('popInvitation');
//     var over = document.getElementById('cover');
//     login.style.display = "none";
//     over.style.display = "none";
// }
$(function () {
    //游戏帮助区域
    $("#rluerIntroduce").hover(function () {
        $("#rluerIntroduce").css({
            "cursor": "pointer"
        });
    });
    $("#rluerIntroduce").click(function () {
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
     $("#inviteBtn").hover(function () {
        $("#inviteBtn").css({
            "cursor": "pointer"
        });
    });
    $("#inviteBtn").click(function () {
        $("#gameHome").css("display", "none");
        $("#gamesHelp").css("display", "none");
        $("#Invite").css("display", "block");
        if ($("#loginBtn").attr("data-state") == 1) {
            $("#inviteInput").val("请先登录，才能获取邀请链接");
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
});
function Copy() {//邀请好友复制到剪切板
   
    $("#inviteInput").val(`${baseUrl}/eosdtplay/tiger/home?userCode=${UserInfo.userName}`);
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
    console.log(123456,UserInfo.userName)
    $.ajax({
        type: "get",
        url: `${ip}/user/getMyChildsto.do`,
        async: false,
        data: {
            userName: UserInfo.userName,
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