var prefix = "/tiger/user"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/query", // 服务器数据的加载地址
                showRefresh : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                singleSelect: false, // 设置为true将禁止多选
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams: function (params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset: params.offset,
                        userId : $('#userId').val(),
                    };
                },
                columns: [
                    {
                        field: 'userId',
                        title: '用户名'
                    },
                    {
                        field: 'putMoney',
                        title: '总下注（EOS）'
                    },
                    {
                        field: 'winMoney',
                        title: '总奖金（EOS）'
                    },
                    {
                    	field: 'wins',
                    	title: '胜场'
                    },
                    {
                    	field: 'fails',
                    	title: '败场'
                    },
                    {
                    	field: 'winPer',
                    	title: '胜率'
                    },
                    {
                    	field: 'status',
                    	title: '状态',
                    	formatter: function(value, row, index) {
                    		switch (value) {
							case 0:
								return "<font color='red'>冻结</font>";
							case 1:
								return "<font color='green'>正常</font>";
							default:
								return "";
							}
                    	}
                    },
                    {
                        title: '操作',
                        field: 'userId',
                        align: 'center',
                        formatter: function (value, row, index) {
							var str = "";
							if (row.status == 0) {
							    str = "激活";
							} else if (row.status == 1) {
							    str = "冻结";
							}
							var a1 = '<a href="' + prefix + '/puts?userId=' + row.userId + '">查看明细</a> | ';
							var a2 = '<a href="#" onclick="edit(\'' + row.userId + '\')">' + str + '</a> | ';
							var a3 = '<a href="' + prefix + '/child?userId=' + row.userId + '">TA的下级</a>';
							return a1 + a2 + a3;
                        }
                    }]
            });
}

function reLoad() {
	$("#exampleTable").bootstrapTable("refresh");
}

function edit(userId) {
	$.ajax({
		url : prefix + "/edit",
		type : "post",
		data : {
			"userId" : userId
		},
		dataType : "json",
		success : function(data) {
			reLoad();
		}
	});
}