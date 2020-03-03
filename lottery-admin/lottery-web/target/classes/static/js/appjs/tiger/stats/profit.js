$(function() {
	getBalance();
	loadTable(1);
});

/**
 * 获取官方账号余额
 */
function getBalance() {
	$.ajax({
		cache : true,
		type : "GET",
		url : "https://myeosgame.com/account/getSysInfo.do",
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			$(".account").html(data.balance);
		}
	});
}

function loadTable(state) {

	var barChart = echarts.init(document.getElementById("echarts-bar-chart"));

	var baroption = {
		title : {
			text : '利润统计'
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			show : true,
			data : [ '利润(EOS)' ]
		},
		grid : {
			x : 30,
			x2 : 40,
			y2 : 24
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			data : [

			]
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : [ {
			name : '利润(EOS)',
			type : 'bar',
			data : [

			],
			markPoint : {
				data : [ {
					type : 'max',
					name : '最大值'
				}, {
					type : 'min',
					name : '最小值'
				} ]
			},
			markLine : {
				data : [ {
					type : 'average',
					name : '平均值'
				} ]
			}
		} ]
	};

	getData(barChart, state);
	barChart.setOption(baroption);
	window.onresize = barChart.resize;
}

$(".radioItem").change(function() {
	var $selectedvalue = $("input[name='type']:checked").val();
	loadTable($selectedvalue);
});

function getData(obj, state) {
	obj.showLoading(); // 数据加载完之前先显示一段简单的loading动画
	var baroption;
	$.ajax({
		cache : true,
		type : "POST",
		url : "/tiger/stats/profit",
		data : {
			"state" : state
		},
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			// 累计交易额
			if (data != null && data.total != null) {
				$(".balance").html(data.total + " EOS");
			}

			var dates = []; // 时间数组（实际用来盛放X轴坐标值）
			var profits = []; // 用户活跃量数组（实际用来盛放Y坐标值）

			if (data.code == 0) {
				$.each(data.list, function(i, item) {
					if (data.state == 3) {
						dates.push(moment(item.dt).format("YYYY-MM-DD HH点"));
					} else {
						dates.push(moment(item.dt).format("YYYY-MM-DD"));
					}
					profits.push(item.profit);
				});

				obj.hideLoading(); // 隐藏加载动画
				obj.setOption({ // 加载数据图表
					xAxis : {
						data : dates
					},
					// 根据名字对应到相应的系列
					series : [ {
						name : '利润(EOS)',
						data : profits
					} ]
				});
			} else {
				parent.layer.alert(data.msg)
			}
		}
	});
	return baroption;
}