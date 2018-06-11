function applyRank() {

    $(".rank").raty({
        hints: ['1', '2', '3', '4', '5'],
        path: "./raty/images",
        starOn: 'star-on.png',
        starOff: 'star-off.png',
        number: 5,
        halfShow: false,
        click: function (score, evt) {
            //alert($(this).attr('data_score'));
            var parent = $(this).parent().parent().parent().parent();
            var id = parent.attr("id");

            $.ajax({
                url: "RankOrder",
                type: "post",
                //dataType: "json",
                data: {
                    score: score,
                    OrderId: id
                },
                success: function (response) {
                    if (response == "0") {
                        alert(response);
                    }
                },
                error: function () {

                }
            });
        },
        //callback get score
        score: function () {
            return $(this).attr('data_score');
        }
    });
}
checkState();
function checkState() {
    var userName = cookieUtil.getCookie("userName");
    var state = cookieUtil.getCookie("state");
    if (state != "login" && state != "logon") {
        //重定向
        window.location.href = "login.html";
        return;
    }
}
$(document).ready(
    function () {
        document.getElementById("all").className = "active";
        showOrder("all", applyRank);
    }
);

$("#order-nav ul li a").click(
    function () {
        for (var i = 0; i < 3; i++) {
            var li = document.getElementById("order_ul").children[i];
            li.firstChild.className = "";
        }
        $(this).className = "active";
        showOrder($(this).attr("id"), applyRank);
    }
);

$(".go_pay").click(
    function () {
        var orderId
    }
);

function showOrder(i, callback) {
    $.ajax({
        type: 'Post',
        //async: false, //同步执行，不然会有问题
        url: "ShowOrder",
        data: {
            "orderKind": i,
        },
        dataType: "json",
        //contentType: "application/json; charset=utf-8",
        error: function (msg) {//请求失败处理函数
            alert("服务器出错");
        },
        success: function (data) { //请求成功后处理函数 
            var orderList = eval(data);
            var parentNode = document.getElementsByClassName("orderList")[0];
            //删除原来的所有子节点
            var len = parentNode.childNodes.length;
            for (var m = 0; m < len; m++) {
                parentNode.removeChild(parentNode.firstChild);
            }
            parentNode.innerText = "";
            //生成order节点
            for (var i = 0; i < orderList.length; i++) {
                appendOrder(orderList[i], parentNode);
                /*// orderListItem
                var orderListItem = document.createElement("div");
                orderListItem.className = "orderListItem";
                orderListItem.id = orderList[i].id;
                parentNode.appendChild(orderListItem);
                // orderhead
                var orderhead = document.createElement("div");
                orderhead.className = "orderhead";
                orderListItem.appendChild(orderhead);

                //head_first_line
                var head_first_line = document.createElement("div");
                head_first_line.className = "head_first_line";
                orderhead.appendChild(head_first_line);
                // orderTime
                var orderTime = document.createElement("span");
                orderTime.className = "orderTime";
                orderTime.innerText = orderList[i].time;
                head_first_line.appendChild(orderTime);
                // orderId
                var txt = document.createTextNode("订单号:");
                head_first_line.appendChild(txt);
                var orderId = document.createElement("span");
                orderId.className = "orderId";
                orderId.innerText = orderList[i].id;
                head_first_line.appendChild(orderId);


                //head_second_line
                var head_second_line = document.createElement("div");
                head_second_line.className = "head_second_line";
                orderhead.appendChild(head_second_line);

                //推荐满意度
                if (orderList[i].state == 1) {
                    //rateandtxt
                    var rankandtxt = document.createElement("span");
                    rankandtxt.className = "rateandtxt";
                    head_second_line.appendChild(rankandtxt);
                    if (orderList[i].rate == null || orderList[i].rate == 0) {
                        var txt2 = document.createTextNode("请选择您对订单自动生成的满意度:");
                        rankandtxt.appendChild(txt2);
                        var rate = document.createElement("span");
                        rate.className = "rank";
                        rate.setAttribute("data_score", 0);
                    } else {
                        var txt2 = document.createTextNode("您对订单自动生成的满意度：");
                        rankandtxt.appendChild(txt2);
                        var rate = document.createElement("span");
                        rate.className = "rank";
                        rate.setAttribute("data_score", orderList[i].rate);
                    }

                    rankandtxt.appendChild(rate);
                } else {
                    var go_pay = document.createElement("a");
                    go_pay.className = "go_pay";
                    go_pay.innerText = "未支付？去支付";
                    head_second_line.appendChild(go_pay);
                }

                // t_price
                var t_price = document.createElement("span");
                t_price.className = "t_price";
                t_price.innerText = "总价：￥" + orderList[i].price;
                head_second_line.appendChild(t_price);

                var orderItems = orderList[i].orderItems;
                var orderItemsNode = document.createElement("div");
                orderItemsNode.className = "orderItems";
                orderListItem.appendChild(orderItemsNode);
                //生成orderitem节点
                for (var j = 0; j < orderItems.length; j++) {
                    var orderItem = document.createElement("div");
                    orderItem.className = "orderItem";
                    orderItemsNode.appendChild(orderItem);

                    var food_name_label = document.createElement("span");
                    food_name_label.className = "col-sm-6 control-label food_name_label";
                    food_name_label.innerText = orderItems[j].food.name;
                    orderItem.appendChild(food_name_label);

                    var food_number_lable = document.createElement("span");
                    food_number_lable.className = "col-sm-3 control-label food_number_lable";
                    food_number_lable.innerText = orderItems[j].foodNumber;
                    orderItem.appendChild(food_number_lable);

                    var food_t_price_lable = document.createElement("span");
                    food_t_price_lable.className = "col-sm-3 control-label food_t_price_lable";
                    food_t_price_lable.innerText = "￥" + orderItems[j].food.price;
                    orderItem.appendChild(food_t_price_lable);

                }*/
            }
            callback();
        }
    })
}

function appendOrder(order, parentNode) {
    // orderListItem
    var orderListItem = document.createElement("div");
    orderListItem.className = "orderListItem";
    orderListItem.id = order.id;
    parentNode.appendChild(orderListItem);
    // orderhead
    var orderhead = document.createElement("div");
    orderhead.className = "orderhead";
    orderListItem.appendChild(orderhead);

    //head_first_line
    var head_first_line = document.createElement("div");
    head_first_line.className = "head_first_line";
    orderhead.appendChild(head_first_line);
    // orderTime
    var orderTime = document.createElement("span");
    orderTime.className = "orderTime";
    orderTime.innerText = order.time;
    head_first_line.appendChild(orderTime);
    // orderId
    var txt = document.createTextNode("订单号:");
    head_first_line.appendChild(txt);
    var orderId = document.createElement("span");
    orderId.className = "orderId";
    orderId.innerText = order.id;
    head_first_line.appendChild(orderId);


    //head_second_line
    var head_second_line = document.createElement("div");
    head_second_line.className = "head_second_line";
    orderhead.appendChild(head_second_line);

    //推荐满意度
    if (order.state == 1) {
        //rateandtxt
        var rankandtxt = document.createElement("span");
        rankandtxt.className = "rateandtxt";
        head_second_line.appendChild(rankandtxt);
        if (order.rate == null || order.rate == 0) {
            var txt2 = document.createTextNode("请选择您对订单自动生成的满意度:");
            rankandtxt.appendChild(txt2);
            var rate = document.createElement("span");
            rate.className = "rank";
            rate.setAttribute("data_score", 0);
        } else {
            var txt2 = document.createTextNode("您对订单自动生成的满意度：");
            rankandtxt.appendChild(txt2);
            var rate = document.createElement("span");
            rate.className = "rank";
            rate.setAttribute("data_score", order.rate);
        }

        rankandtxt.appendChild(rate);
    } else {
        var go_pay = document.createElement("a");
        go_pay.className = "go_pay";
        go_pay.innerText = "未支付？去支付";
        head_second_line.appendChild(go_pay);
    }

    // t_price
    var t_price = document.createElement("span");
    t_price.className = "t_price";
    t_price.innerText = "总价：￥" + order.price;
    head_second_line.appendChild(t_price);

    var orderItems = order.orderItems;
    var orderItemsNode = document.createElement("div");
    orderItemsNode.className = "orderItems";
    orderListItem.appendChild(orderItemsNode);
    //生成orderitem节点
    for (var j = 0; j < orderItems.length; j++) {
        var orderItem = document.createElement("div");
        orderItem.className = "orderItem";
        orderItemsNode.appendChild(orderItem);

        var food_name_label = document.createElement("span");
        food_name_label.className = "col-sm-6 control-label food_name_label";
        food_name_label.innerText = orderItems[j].food.name;
        orderItem.appendChild(food_name_label);

        var food_number_lable = document.createElement("span");
        food_number_lable.className = "col-sm-3 control-label food_number_lable";
        food_number_lable.innerText = orderItems[j].foodNumber;
        orderItem.appendChild(food_number_lable);

        var food_t_price_lable = document.createElement("span");
        food_t_price_lable.className = "col-sm-3 control-label food_t_price_lable";
        food_t_price_lable.innerText = "￥" + orderItems[j].food.price;
        orderItem.appendChild(food_t_price_lable);

    }

}


function findOrderById() {
    var id = document.getElementById("input_find").value;
    $.ajax({
        type: 'Post',
        //async: false, //同步执行，不然会有问题
        url: "FindOrder",
        data: {
            "method": "id",
            "orderId": id
        },
        dataType: "json",
        //contentType: "application/json; charset=utf-8",
        error: function (msg) {//请求失败处理函数
            alert("服务器出错");
        },
        success: function (data) { //请求成功后处理函数
            var order = eval(data);
            var parentNode = document.getElementsByClassName("orderList")[0];
            //删除原来的所有子节点
            var len = parentNode.childNodes.length;
            for (var m = 0; m < len; m++) {
                parentNode.removeChild(parentNode.firstChild);
            }
            parentNode.innerText = "";
            appendOrder(order, parentNode);
            applyRank();
        }
    })
}
/*
function showOrder( i,callback){
 $.ajax({
        type: 'Post',
        //async: false, //同步执行，不然会有问题
        url: "ShowOrder",
        data: {
           "orderKind": i,
        },
        dataType: "json",
        //contentType: "application/json; charset=utf-8",
        error: function (msg) {//请求失败处理函数
            alert("服务器出错");
        },
        success: function (data) { //请求成功后处理函数 
            var orderList=eval(data);
            var parentNode=document.getElementsByClassName("orderList")[0];
            var fristChild=parentNode.getElementsByClassName("orderListItem")[0];
            var numberNeedDelete=parentNode.childElementCount;
            for(var i=0;i<orderList.length;i++){
                var cloneNode=fristChild.cloneNode(true);
                cloneNode.style.display="block";
                cloneNode.id=orderList[i].id;
                var orderhead= cloneNode.getElementsByClassName("orderhead")[0];
                orderhead.getElementsByClassName("orderTime")[0].innerText=orderList[i].time;
                orderhead.getElementsByClassName("orderId")[0].innerText=orderList[i].id;
                orderhead.getElementsByClassName("t_price")[0].innerText=orderList[i].price;
                var rank=orderhead.getElementsByClassName("rate")[0];

                if(orderList[i].state==1){
                    var rateandtxt=orderhead.getElementsByClassName("rankandtxt")[0];
                    rateandtxt.style.display="inline";
                    if(orderList[i].rate==null||orderList[i].rate==0){
                        rateandtxt.innerText="请选择您对订单自动生成的满意度:";
                    }else{
                         rateandtxt.innerText="您对订单自动生成的满意度：";
                    }
                    var rank=document.createElement("span");
                    rank.className="rank";
                    rateandtxt.appendChild(rank);
                }else{
                    var go_pay=orderhead.getElementsByClassName("go_pay")[0];
                    go_pay.style.display="block";
                }

                var orderItems=cloneNode.getElementsByClassName("orderItems")[0];
                var firstOrderItem=orderItems.getElementsByClassName("orderItem")[0];
                 var numberNeedDelete2=orderItems.childElementCount;
                 var orderItemsObject=orderList[i].orderItems;
                 for(var j=0;j<orderItemsObject.length;j++){
                     var cloneOrderItem=firstOrderItem.cloneNode(true);
                     cloneOrderItem.getElementsByClassName("food_name_label")[0].innerText=orderItemsObject[j].food.name;
                     cloneOrderItem.getElementsByClassName("food_number_lable")[0].innerText=orderItemsObject[j].foodNumber;
                    cloneOrderItem.getElementsByClassName("food_t_price_lable")[0].innerText=orderItemsObject[j].food.price;
                    orderItems.appendChild(cloneOrderItem);
                 }
                 parentNode.appendChild(cloneNode);
                  for(var m=0;m<numberNeedDelete2;m++){
                     orderItems.removeChild(orderItems.children[0]);
                 }
                 
                               
            }
            for(var n=0;n<numberNeedDelete;n++){
                parentNode.removeChild(parentNode.children[0]);
            }
            callback();
        }
 })
}*/

