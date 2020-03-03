var prefix = "/lottery/userBerecords"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : prefix + "/userList", // 服务器数据的加载地址
                	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize : 'outline',
                toolbar : '#exampleToolbar',
                striped : true, // 设置为true会有隔行变色效果
                dataType : "json", // 服务器返回的数据类型
                pagination : true, // 设置为true会在底部显示分页条
                sortable: false,                     //是否启用排序
                // sortOrder: "asc",                   //排序方式
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect : true, // 设置为true将禁止多选
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
                        sort: params.sort,      //排序列名
                        sortOrder: params.order, //排位命令（desc，asc）
                        // name:$('#searchName').val(),
                        userName : $('#userName').val(),
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
                        field: 'userName',
                        align: 'center',
                        title: '用户名'
                    },
                    {
                        field: 'noteMoney',
                        align: 'center',
                        title: '总下注金额(EOS)',
                    },
                    {
                        field: 'lotteryBonus',
                        align: 'center',
                        title: '总奖金(EOS)',
                    },
                    {
                        field: 'winning ',
                        align: 'center',
                        title: '胜率 ',
                        sortable: true,
                        formatter:function (value, row, index) {
                            return (parseFloat(row.winning)*100).toFixed(4)+'%';
                        }
                    },
                    {
                        field: 'status',
                        title: '账号状态',
                        align: 'center',
                        formatter:function (value, row, index) {
                            var status;
                            if(row.status == '0' ){
                                status = "<span class='badge bg-red' style='padding:5px 10px; background-color: #f30632;color: #f5f1f1;'>冻结</span>";
                            }else if(row.status == '1' ){
                                status = "<span class='badge bg-red' style='padding:5px 10px;background-color: #1f8a28;color: #f5f1f1;'>正常</span>";
                            }
                            return status;
                        }
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var str = "";
                            if(row.status == '0' ){
                                str = "激活";
                            }else if(row.status == '1' ){
                                str = "冻结";
                            }
                            var f = '<a class="btn btn-success btn-sm ' + s_detail_h + '" title="查看明细" href="'+prefix+'/userDetail?userName='+row.userName+'">' +
                                '<i class="fa">查看明细</i></a> ';
                            var d = '<a class="btn btn-success btn-sm ' + s_userStatus_h + '" href="#" title="'+str+'"  mce_href="#" onclick="edit(\''
                                + row.userId
                                + '\')"><i class="fa"></i>'+str+'</a> ';
                            var c = '<a class="btn btn-success btn-sm ' + s_childs_h + '" title="TA的下级" href="'+prefix+'/userChilds?userName='+row.userName+'">' +
                                '<i class="fa">TA的下级</i></a> ';
                               return f+d+c;
                        }
                    }]
            });
}

function reLoad() {
    $("#exampleTable").bootstrapTable('refresh');
}

function edit(id) {
    $.ajax({
        url:prefix+'/userEdit',
        type:"post",
        data:{"userId":id},
        dataType:"json",
        success:function(data){
            reLoad();
        }
    });
}