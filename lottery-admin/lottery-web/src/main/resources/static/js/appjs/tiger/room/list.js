var prefix = "/tiger/room"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/query", // 服务器数据的加载地址
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
                        offset: params.offset
                    };
                },
                columns: [
                    {
                        field: 'id',
                        title: '房间号'
                    },
                    {
                        field: 'x',
                        title: '龙牌偏移'
                    },
                    {
                        field: 'y',
                        title: '虎牌偏移'
                    },
                    {
                    	field: 'qid',
                    	title: '期号'
                    },
                    {
                    	field: 'cardNum',
                    	title: '剩余牌数'
                    }]
            });
}