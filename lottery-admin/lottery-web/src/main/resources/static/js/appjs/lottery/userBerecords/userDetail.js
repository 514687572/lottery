var prefix = "/lottery/userBerecords"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : prefix + "/list", // 服务器数据的加载地址
                	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize : 'outline',
                toolbar : '#exampleToolbar',
                striped : true, // 设置为true会有隔行变色效果
                dataType : "json", // 服务器返回的数据类型
                pagination : true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect : false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize : 10, // 如果设置了分页，每页数据条数
                pageNumber : 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns : false, // 是否显示内容下拉框（选择显示的列）
                sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams : function(params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit : params.limit,
                        offset : params.offset,
                        // name:$('#searchName').val(),
                        type : $('#searchName').val(),
                        userName:userName
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns: [
                    {
                        checkbox: true,
                        class:"custom-control-label"
                    },
                    {
                        field: 'userId',
                        title: '用户ID',
                        visible: false,
                        align: 'center',
                        valign: 'center',
                        width: '5%'
                    },
                    {
                        field: 'transaction_id',
                        align: 'center',
                        title: '交易编号'
                    },
                    {
                        field: 'bet_num',
                        align: 'center',
                        title: '期号'
                    },
                    {
                        field: 'lotteryOne',
                        align: 'center',
                        title: '个'
                    },
                    {
                        field: 'lotteryTwo',
                        align: 'center',
                        title: '十'
                    },
                    {
                        field: 'lotteryThree',
                        align: 'center',
                        title: '百'
                    },
                    {
                        field: 'lotteryFour',
                        align: 'center',
                        title: '千'
                    },
                    {
                        field: 'lotteryFive',
                        align: 'center',
                        title: '万'
                    },
                    {
                        field: 'largeNum',
                        align: 'center',
                        title: '大小'
                    },
                    {
                        field: 'lotterySingle',
                        align: 'center',
                        title: '单双'
                    },
                    {
                        field: 'noteNum',
                        align: 'center',
                        title: '星级'
                    },
                    {
                        field: 'noteMoney',
                        align: 'center',
                        title: '下注金额(EOS)'
                    },
                    {
                        field: 'lotteryBonus',
                        align: 'center',
                        title: '奖金(EOS)'
                    },
                    {
                        field: 'lotteryStatus',
                        align: 'center',
                        title: '下注状态',
                        formatter:function (value, row, index) {
                            var status;
                            if(row.lotteryStatus == '0' ){
                                status = "<span class='badge bg-red' style='padding:5px 10px;'>待确认</span>";
                            }else if(row.lotteryStatus == '1' ){
                                status = "<span class='badge bg-red' style='padding:5px 10px;'>成功</span>";
                            }
                            return status;
                        }
                    }]
            });
}

function reLoad() {
    $("#exampleTable").bootstrapTable('refresh',{url : prefix + "/userDetailList"});
}

function back(){
    window.location.href=prefix+"/userMange";
}