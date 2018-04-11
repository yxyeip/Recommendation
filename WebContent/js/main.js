function showFood(str) {
	var xmlhttp;
	if (str == "") {
		document.getElementById("food").innerHTML = "";
		return;
	}
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	}
	else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function () {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			document.getElementById("food").innerHTML = xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET", "./ShowFood?kind=" + str, true);
	xmlhttp.send();
}
function toLoginPage() {
	window.location.href="login.html";
	//window.open("index.html");
}
function toLogonPage() {
	window.location.href="logon.html";
	//window.open("index.html");
}

function checkUserState() {
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	}
	else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function () {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			//将 JSON 文本转换为 JavaScript 对象
			var json_userstate = eval("(" + xmlhttp.responseText + ")");			
			var state = json_userstate.state;
			var logCheck = document.getElementById("logCheck");
			if (state=="log in") {
				var userName = json_userstate.username;
				logCheck.innerHTML="<form action='LogoutServlet' method='post'>"+
				"<button type='submit' class='btn  navbar-btn'  id='logout'> "+userName
				+"，退出</button>"+
				"</form>";
			}else{
				logCheck.innerHTML="<button type='button' class='btn  navbar-btn' onclick='toLoginPage()' id='logon'> 登录</button>" +" " + "<button type='button' class='btn  navbar-btn' onclick='toLogonPage()' id='logon'>注册</button>";
			}
		}
	}
	xmlhttp.open("POST", "CheckUserState", true);
	xmlhttp.send();
}

