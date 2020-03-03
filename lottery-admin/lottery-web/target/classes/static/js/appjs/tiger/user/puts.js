$(function () {
    load();
});

Date.prototype.format = function(fmt) { 
    var o = { 
       "M+" : this.getMonth()+1,                 //月份 
       "d+" : this.getDate(),                    //日 
       "H+" : this.getHours(),                   //小时 
       "m+" : this.getMinutes(),                 //分 
       "s+" : this.getSeconds(),                 //秒 
       "q+" : Math.floor((this.getMonth()+3)/3), //季度 
       "S"  : this.getMilliseconds()             //毫秒 
   }; 
   if(/(y+)/.test(fmt)) {
           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   }
    for(var k in o) {
       if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
   return fmt; 
}

function dateFormatter(value, row, index) {
	var date = new Date(value);
	return date.format("yyyy-MM-dd HH:mm:ss");
}

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: "/tiger/record/query", // 服务器数据的加载地址
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
                        userId : userId
                    };
                },
                columns: [
                    {
                        field: 'roomId',
                        title: '房间号'
                    },
                    {
                    	field: 'qid',
                    	title: '期号'
                    },
                    {
                    	field: 'userId',
                    	title: '用户名'
                    },
                    {
                    	field: 'opt',
                    	title: '投注项',
                    	formatter: function(value, row, index) {
                    		switch (value) {
							case 1:
								return '龙单';
							case 2:
								return '龙双';
							case 3:
								return '虎单';
							case 4:
								return '虎双';
							case 5:
								return '龙';
							case 6:
								return '和';
							case 7:
								return '虎';
							default:
								return '';
							}
                    	}
                    },
                    {
                    	field: 'txStatus',
                    	title: '区块确认',
                        formatter: function(value, row, index) {
                    		switch (value) {
							case 0:
								return '<font color="red">否</font>';
							case 1:
								return '<font color="green">是</font>';
							default:
								return '';
							}
                    	}
                    },
                    {
                    	field: 'putMoney',
                    	title: '投注EOS'
                    },
                    {
                    	field: 'putTimeSpt',
                    	title: '投注时间',
                        formatter: dateFormatter
                    },
                    {
                        field: 'status',
                        title: '状态',
                        formatter: function(value, row, index) {
                    		switch (value) {
							case 0:
								return '未开奖';
							case 1:
								return '<font color="red">未中奖</font>';
							case 2:
								return '中奖未发';
							case 3:
								return '<font color="green">发奖成功</font>';
							case -1:
								return '<font color="blue">发奖失败</font>';
							case -2:
								return '<b>回滚</b>';
							default:
								return '';
							}
                    	}
                    },
                    {
                    	field: 'gainMoney',
                    	title: '中奖EOS'
                    },
                    {
                    	field: 'openTimeSpt',
                    	title: '开奖时间',
                    	formatter: dateFormatter
                    },
                    {
                    	field: 'type',
                    	title: '投注方式',
                        formatter: function(value, row, index) {
                    		switch (value) {
							case 0:
								return 'scatter';
							case 1:
								return '<b>私钥</b>';
							default:
								return '';
							}
                    	}
                    },
                    {
                    	field: 'topStatus',
                    	title: '已发代币',
                        formatter: function(value, row, index) {
                    		switch (value) {
							case 0:
								return '<font color="red">否</font>';
							case 1:
								return '<font color="green">是</font>';
							default:
								return '';
							}
                    	}
                    }]
            });
}

function back() {
    window.location.href = "/tiger/user";
}