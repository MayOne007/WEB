//连接成功建立的回调方法
websocket.onopen = function() {
	$("#message").append("WebSocket连接成功<br/>");
}

// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function() {
	websocket.close();
}

// 连接关闭的回调方法
websocket.onclose = function() {
	$("#message").append("WebSocket连接关闭<br/>");
}
// 连接发生错误的回调方法
websocket.onerror = function() {
	$("#message").append("WebSocket连接发生错误<br/>");
};

// 发送消息
function send() {	
	websocket.send($("#text").val());
}

//接收到消息的回调方法
websocket.onmessage = function(event) {

	if(/^[0-9a-zA-Z\u4e00-\u9fa5]*$/.test(event.data)){
		$("#message").append(event.data+"<br/>");
		return;
	}	   
	var obj = JSON.parse(event.data);
	if(obj.type=="add"){
		if(obj.isSuccess){
			$("#message").append("websocket返回订单处理成功信息，跳转链接...<br/>");
		}else{
			$("#message").append("websocket返回订单处理失败信息，跳转链接...<br/>");
		}
	}
	
}

function addOrder(){	
	if(confirm("提交订单")){
		var url = contextPath + "/order/add";		
		$("#message").append("正通过AJAX异步添加订单到MQ列...<br/>")		
		sendPost(url,{},function(data){
			if(data.isSuccess){
				$("#message").append("成功通过AJAX异步添加订单到MQ列，等待处理...<br/>")
			}			
		},function(er){console.log(er);});
	}
	
}