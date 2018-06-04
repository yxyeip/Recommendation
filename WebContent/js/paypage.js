/*************pay page function*******************/

/***count down***/
var time = 30 * 60;
var t1 = setInterval(function () {
    time--;
    if (time < 0) {
        clearInterval(t1);
        show(0);
        console.log("时间到！");
        return;
    }
    show(time);
}, 1000);

function show(n) {
    var timeNode = document.getElementById("showtime");
    var s = parseInt(n / 60) + "分" + n % 60 + "秒";
    timeNode.innerText = s;
}
/*show order id and price */

$(document).ready(
    showIdAndPrice()
);

function showIdAndPrice(){
    var orderCookie=cookieUtil.getCookie("order");
    var order=JSON.parse(orderCookie);
    document.getElementById("orderId").innerText=order.id;
    document.getElementById("amount-price").innerText=order.price;
}

/*pay action */
$(document).ready(
    $("#pay_btn").click(
        function () {
            var orderId = document.getElementById("orderId").innerText;
            $.ajax({
                type: 'Post',
                //async: false, //同步执行，不然会有问题
                dataType: "text",
                url: "PayAction",
                data: { orderId: orderId },
                //contentType: "application/json; charset=utf-8",
                error: function (msg) {//请求失败处理函数
                    alert("支付失败，请稍后重试");
                },
                success: function (data) { //请求成功后处理函数 
                    if (data == 3) {
                        window.location.href = "order.html";
                    } else {
                        alert("支付失败");
                    }
                }
            })
        }
    )
);