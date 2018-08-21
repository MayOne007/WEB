<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% String websocketPath = request.getServerName()+":"+request.getServerPort()+request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script src="${contextPath}/static/common/js/jquery-1.4.4.min.js"></script>
<script src="${contextPath}/static/common/js/sockjs-1.1.4.min.js"></script>
<script src="${contextPath}/static/page/js/common.js"></script>
<script type="text/javascript">
	var contextPath = '${contextPath}';
	var websocketPath = "<%=websocketPath%>";
	var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://"+websocketPath+"/order");

    } else {
        websocket = new SockJS("http://"+websocketPath+"/sockjs/order");
    }
</script>
<script src="${contextPath}/static/page/js/order/list.js"></script>
</head>
<body>
	<input id="text" type="text"/><button onclick="send()">发送消息</button>
	<hr/>
	<button onclick="addOrder()">添加订单</button>
    <hr/>
    <div id="message"></div>
</body>
</html>