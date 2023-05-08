<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Netty-Websocket</title>
    <style type="text/css">
        #image-preview {
        	border: 1px solid #ccc;
        	width: 100%;
        	height: 100%;
        	max-width: 200px;
        	max-height: 200px;
        	background-size: contain;
        	background-repeat: no-repeat;
        	background-position: center center;
        }
    </style>
    <script type="text/javascript">
        var socket;
        if(!window.WebSocket){
            window.WebSocket = window.MozWebSocket;
        }
        if(window.WebSocket){
            socket = new WebSocket("ws://127.0.0.1:12345/ws");
            socket.onmessage = function(event){
                var ta = document.getElementById('responseText');
                ta.value += event.data+"\r\n";
            };
            socket.onopen = function(event){
                var ta = document.getElementById('responseText');
                ta.value = "Netty-WebSocket服务器。。。。。。连接  \r\n";
            };
            socket.onclose = function(event){
                var ta = document.getElementById('responseText');
                ta.value = "Netty-WebSocket服务器。。。。。。关闭 \r\n";
            };
        }else{
            alert("您的浏览器不支持WebSocket协议！");
        }
        function send(message){
            if(!window.WebSocket){return;}
            if(socket.readyState == WebSocket.OPEN){
                socket.send(message);
            }else{
                alert("WebSocket 连接没有建立成功！");
            }

        }

        // 文件上传
        let fileInput = document.getElementById('file');
        			let info = document.getElementById('info');
        			let preview = document.getElementById('image-preview');
        			// 监听change事件:
        			fileInput.addEventListener('change', function() {
        				// 清除背景图片:
        				preview.style.backgroundImage = '';
        				if (!fileInput.value) {
        					info.innerHTML = '没有选择文件';
        					return;
        				}
        				let file = fileInput.files[0];
        				let size = file.size;
        				if (size >= 1 * 1024 * 1024) {
        					alert('文件大小超出限制');
        					info.innerHTML = '文件大小超出限制';
        					return false;
        				}
        				// 获取File信息:
        				info.innerHTML = `文件名称:  + ${file.name}<br>文件大小: ${file.size} <br>上传时间: ${file.lastModifiedDate}`;
        				if (!['image/jpeg', 'image/png', 'image/gif'].includes(file.type)) {
        					alert('不是有效的图片文件!');
        					return;
        				}
        				// 读取文件:
        				let reader = new FileReader();
        				reader.onload = function(e) {
        					let data = e.target.result;
        					console.log(preview, 'a标签')
        					preview.src = data
        				};
        				// 以DataURL的形式读取文件:
        				reader.readAsDataURL(file);

        			});

        			function save() {

        				var url = preview.src; // 获取图片地址
        				var a = document.createElement('a'); // 创建一个a节点插入的document
        				var event = new MouseEvent('click') // 模拟鼠标click点击事件
        				a.download = 'beautifulGirl' // 设置a节点的download属性值
        				a.href = url; // 将图片的src赋值给a节点的href
        				a.dispatchEvent(event)
        			}
    </script>
</head>
<body>
<form onSubmit="return false;" method="post" enctype="multipart/form-data" id="file_upload">
    <p>图片预览：</p>
    	<img id="image-preview">
    	<p>
    	<input type="file" id="file" name="upload_image" accept="image/gif, image/jpeg, image/png, image/jpg">
    	<input type="button" value="下载图片" onclick="save()" />
    	</p>
        <p id="info"></p>
    <hr color="black" />
    <h3>服务端返回的应答消息</h3>
    <textarea id="responseText" style="width: 1024px;height: 300px;"></textarea>
</form>
</body>
</html>