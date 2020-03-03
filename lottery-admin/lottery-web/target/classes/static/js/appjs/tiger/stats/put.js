$(function() {
	loadTable(1);
});

function loadTable(state) {
	var barChart = echarts.init(document.getElementById("echarts-bar-chart"));

	var baroption = {
		title : {
			text : '用户下注统计'
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			show : true,
			data : [ '下注次数', '总下注额', '总奖金' ]
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
			name : '下注次数',
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
		}, {
			name : '总下注额',
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
		}, {
			name : '总奖金',
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
		url : "/tiger/stats/put",
		data : {
			"state" : state
		},
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			var dates = []; // 时间数组（实际用来盛放X轴坐标值）
			var times = []; // 下注次数（实际用来盛放Y坐标值）
			var putSum = []; // 总下注金额（实际用来盛放Y坐标值）
			var sendSum = []; // 总奖金（实际用来盛放Y坐标值）

			if (data.code == 0) {
				$.each(data.list, function(i, item) {
					if (data.state == 3) {
						dates.push(moment(item.dt).format("YYYY-MM-DD HH点"));
					} else {
						dates.push(moment(item.dt).format("YYYY-MM-DD"));
					}
					times.push(item.times);
					putSum.push(item.putSum);
					sendSum.push(item.sendSum);
				});

				obj.hideLoading(); // 隐藏加载动画
				obj.setOption({ // 加载数据图表
					xAxis : {
						data : dates
					},
					// 根据名字对应到相应的系列
					series : [{
						name : '下注次数',
						data : times
					},
					{
						name : '总下注额',
						data : putSum
					},
					{
						name : '总奖金',
						data : sendSum
					}]
				});
			} else {
				parent.layer.alert(data.msg)
			}
		}
	});
	return baroption;
}