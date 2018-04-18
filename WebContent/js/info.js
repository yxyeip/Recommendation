function loadCustomerInfo(){
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
			var json_user = eval("(" + xmlhttp.responseText + ")");			
			var name = json_user.name;
            var ismale=json_user.sex;
            var birthday=json_user.birthday;
            var height=json_user.height;
            var weight=json_user.weight;
            //填充内容,name
            document.getElementById("name").value=name;
            //sex
            if(ismale){
                 document.getElementById("male").setAttribute("checked",true);
            }else{
                document.getElementById("female").setAttribute("checked",true);
            }
           
            //birthday
             document.getElementById("birthday").value=birthday;
             //height
             document.getElementById("height").value=height;
             //weight
             document.getElementById("weight").value=weight;
		}
	}
	xmlhttp.open("POST", "LoadCustomerInfo", true);
	xmlhttp.send();
}