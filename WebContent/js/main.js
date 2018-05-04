var login = false;
function toLoginPage() {
	window.location.href = "login.html";
	//window.open("index.html");
}
function toLogonPage() {
	window.location.href = "logon.html";
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
			if (state == "log in") {
				var userName = json_userstate.username;
				logCheck.innerHTML = "<form action='LogoutServlet' method='post'>" +
					"<button type='submit' class='btn  navbar-btn'  id='logout'> " + userName
					+ "，退出</button>" +
					"</form>";
				login = true;
			} else {
				logCheck.innerHTML = "<button type='button' class='btn  navbar-btn' onclick='toLoginPage()' id='logon'> 登录</button>" + " " + "<button type='button' class='btn  navbar-btn' onclick='toLogonPage()' id='logon'>注册</button>";
			}
		}
	}
	xmlhttp.open("POST", "CheckUserState", true);
	xmlhttp.send();
}

$("li.food_kind a").click(
	function(){
		ShowFood($(this).attr("id"),applyRank);
		
	}
);

function applyRank(){
	$(".rank").raty({
		hints: ['1', '2', '3', '4', '5'],
		path: "./raty/images",
		starOn: 'star-on.png',
		starOff: 'star-off.png',
		number: 5,
		halfShow: false,
		click: function (score, evt) {
			//alert($(this).attr('data_score'));
			$.ajax({
				url: "UploadRank",
				type: "post",
				//dataType: "json",
				data: {
					score: score,
					id: $(this).attr('id')
				},
				success: function (response) {
					
					alert(response);
					
				},
				error: function(){

				}
			});
		},
		//callback get score
		score: function () {
			return $(this).attr('data_score');
		}
	});
}
function ShowFood(kind,callback) {
	
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	}
	else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function () {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			//alert(data);

			var json_food_list = eval(xmlhttp.responseText);
			var ul = document.getElementById("food");
			var number = ul.childElementCount;
			for (var i in json_food_list) {
				var li = ul.children[0];
				var cloneLi = li.cloneNode(true);
				//change data
				var nameNode = cloneLi.getElementsByClassName("food_name")[0];
				nameNode.innerText = json_food_list[i].name;
				var priceNode = cloneLi.getElementsByClassName("food_price")[0];
				if (json_food_list[i].price != null) {
					priceNode.innerHTML = "<span>¥</span><strong>" + json_food_list[i].price + "</strong>";
				} else {
					priceNode.innerHTML = "<span> </span><strong>" + "时价" + "</strong>";
				}

				var descriptionNode = cloneLi.getElementsByClassName("food_description")[0];
				var text = json_food_list[i].description == null ? "" : json_food_list[i].description;
				descriptionNode.innerHTML = "<p>" + text + "</P>";
				//rank
				var rank = document.createElement("div");
				rank.setAttribute("class","rank");
				rank.setAttribute("data_score",json_food_list[i].rank == null ? 0 : json_food_list[i].rank);
				console.log(rank.getAttribute("data_score"));
				//除去之前的rank标签
				var inner=cloneLi.getElementsByClassName("food_inner")[0];
				var remove=inner.getElementsByClassName("rank")[0];
				if(remove!=null)
				inner.removeChild(remove);
				inner.appendChild(rank);
				//rank.id = json_food_list[i].name;
				rank.id = json_food_list[i].id;
				ul.appendChild(cloneLi);
			}
			for (var i = 0; i < number; i++) {
				ul.removeChild(ul.children[0]);
			}
			callback();
		}
	}
	xmlhttp.open("GET", "./ShowFood?kind=" + kind, true);
	xmlhttp.send();
	
}

$(document).ready(function () {
	$(":radio").click(function () {
		$(this).parents(".rating-form-2")[0].submit();
	});
});



function setCookie(cname, cvalue, exdays) {
	var d = new Date();
	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	var expires = "expires=" + d.toGMTString();
	document.cookie = cname + "=" + cvalue + "; " + expires;
}
function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i].trim();
		if (c.indexOf(name) == 0) { return c.substring(name.length, c.length); }
	}
	return "";
}
function checkCookie() {
	var user = getCookie("username");
	if (user != "") {
		alert("欢迎 " + user + " 再次访问");
	}
	else {
		user = prompt("请输入你的名字:", "");
		if (user != "" && user != null) {
			setCookie("username", user, 30);
		}
	}
}



