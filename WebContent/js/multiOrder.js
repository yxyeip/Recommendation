$(document).ready(
    //load guests' info
    function loadGuests() {
        $.ajax({
            type: 'Post',
            url: "LoadGuests",
            data: { guestName: $("#input_find").val() },    //参数（如果没有参数：null）
            error: function (msg) {//请求失败处理函数
                alert("数据加载失败");
            },
            success: function (data) {
                var guestList = eval(data);
                var list = document.getElementById("userCardList");
                var firstChild = list.getElementsByClassName("userCard")[0];
                var numberNeedDelete = list.getElementsByClassName("userCard").length;
                for (var i = 0; i < guestList.length; i++) {
                    var cloneUserCard = firstChild.cloneNode(true);
                    cloneUserCard.id = guestList[i].name;
                    cloneUserCard.getElementsByClassName("userName")[0].innerText = guestList[i].name;
                    cloneUserCard.getElementsByClassName("sex")[0].innerHTML = guestList[i].sex == true ? "♂" : "♀";
                    cloneUserCard.getElementsByClassName("birthday")[0].innerText = guestList[i].birthday;
                    list.appendChild(cloneUserCard);
                }
                for (var i = 0; i < numberNeedDelete; i++) {
                    list.removeChild(list.children[0]);
                }
            }
        })
    }
);
function showRecommendFood() {
    $.ajax({
        type: 'Post',
        //async: false, //同步执行，不然会有问题
        //dataType: "json",
        url: "RecommendFood",
        //data: { userName: $(this).val() },    //参数（如果没有参数：null）
        //contentType: "application/json; charset=utf-8",
        error: function (msg) {//请求失败处理函数
            alert("数据加载失败");
        },
        success: function (food_list) { //请求成功后处理函数
            food_list = eval(food_list);
            var menu_tbody = document.getElementById("menu_tbody");
            var numberNeedDelete = menu_tbody.childElementCount;
            var firstChild = menu_tbody.getElementsByClassName("food_row")[0];
            for (var i = 0; i < food_list.length; i++) {
                //set img unvisiable
                var table_divs = document.getElementsByClassName("table_div");
                table_divs[0].style.display = "block";
                table_divs[1].style.display = "none";

                var cloneFood = firstChild.cloneNode(true);
                cloneFood.id = food_list[i].Id;
                cloneFood.getElementsByClassName("check_item")[0].id = "check_" + food_list[i].Id;
                cloneFood.getElementsByClassName("food_name")[0].innerText = food_list[i].name;
                cloneFood.getElementsByClassName("food_description")[0].innerHTML = "<p>" + food_list[i].description + "</p>";
                //picture

                menu_tbody.appendChild(cloneFood);
            }
            for (var i = 0; i < numberNeedDelete; i++) {
                menu_tbody.removeChild(menu_tbody.children[0]);
            }

        }
    })

}


$(function () {
    $("#input_find").keyup(function (evt) {
        ChangeCoords(); //控制查询结果div坐标
        var k = window.event ? evt.keyCode : evt.which;
        //监听输入框的keyup事件
        //不为空 && 不为上箭头或下箭头或回车
        if ($("#input_find").val() != "" && k != 38 && k != 40 && k != 13) {
            $.ajax({
                type: 'Post',
                //async: false, //同步执行，不然会有问题
                //dataType: "json",
                url: "FindUserNames",
                data: { userName: $(this).val() },    //参数（如果没有参数：null）
                //contentType: "application/json; charset=utf-8",
                error: function (msg) {//请求失败处理函数
                    alert("数据加载失败");
                },
                success: function (data) { //请求成功后处理函数
                    //var objData = eval(data);
                    var userList = eval(data);
                    if (userList.length > 0) {
                        var layer = "";
                        layer = "<table id='result_table'>";
                        for (var i = 0; i < userList.length; i++) {
                            layer += "<tr class='line'><td class='std'>" + userList[i] + "</td></tr>";
                        }
                        layer += "</table>";
                        //将结果添加到div中
                        $("#searchresult").empty();
                        $("#searchresult").append(layer);
                        $(".line:first").addClass("hover");
                        $("#searchresult").css("display", "");
                        //鼠标移动事件
                        $(".line").hover(function () {
                            $(".line").removeClass("hover");
                            $(this).addClass("hover");
                        }, function () {
                            $(this).removeClass("hover");
                            //$("#searchresult").css("display", "none");
                        });
                        //鼠标点击事件
                        $(".line").click(function () {
                            $("#input_find").val($(this).text());
                            var index = $(".line").index($(this));
                            $("#searchresult").css("display", "none");
                        });
                        //上下箭头及回车
                        $(this).keyup(function (evt) {
                            var key = window.event ? evt.keyCode : evt.which;
                            if (key == 38) {//上箭头
                                $('#result_table tr.hover').prev().addClass("hover");
                                $('#result_table tr.hover').next().removeClass("hover");
                                $('#input_find').val($('#result_table tr.hover').text());
                            } else if (key == 40) {//下箭头
                                $('#result_table tr.hover').next().addClass("hover");
                                $('#result_table tr.hover').prev().removeClass("hover");
                                $('#input_find').val($('#result_table tr.hover').text());
                            } else if (key == 13) {//回车
                                $('#input_find').val($('#result_table tr.hover').text());
                                var index = $(".line").index($(this));
                                $("#searchresult").empty();
                                $("#searchresult").css("display", "none");
                            }
                        })
                    } else {
                        $("#searchresult").empty();
                        $("#searchresult").css("display", "none");
                    }

                }
            })
        }
    })
});

/* else if (k == 38) {//上箭头
     $('#result_table tr.hover').prev().addClass("hover");
     $('#result_table tr.hover').next().removeClass("hover");
     $('#input_find').val($('#result_table tr.hover').text());
 } else if (k == 40) {//下箭头
     $('#result_table tr.hover').next().addClass("hover");
     $('#result_table tr.hover').prev().removeClass("hover");
     $('#input_find').val($('#result_table tr.hover').text());
 }
 else if (k == 13) {//回车
     $('#input_find').val($('#result_table tr.hover').text());
     $("#searchresult").empty();
     $("#searchresult").css("display", "none");
 }*/

$("#searchresult").bind("mouseleave", function () {
    $("#searchresult").empty();
    $("#searchresult").css("display", "none");
});

//设置查询结果div坐标
function ChangeCoords() {
    // var left = $("#input_find")[0].offsetLeft; //获取距离最左端的距离，像素，整型
    // var top = $("#input_find")[0].offsetTop + 26; //获取距离最顶端的距离，像素，整型（20为搜索输入框的高度）
    var left = $("#input_find").position().left; //获取距离最左端的距离，像素，整型
    var top = $("#input_find").position().top + 20;; //获取距离最顶端的距离，像素，整型（20为搜索输入框的高度）
    $("#searchresult").css("left", left + "px"); //重新定义CSS属性
    $("#searchresult").css("top", top + "px"); //同上
};

/*add user*/
$("#add_user_btn").click(
    function () {
        $.ajax({
            type: 'Post',
            //async: false, //同步执行，不然会有问题
            //dataType: "json",
            url: "AddGuests",
            data: { guestName: $("#input_find").val() },    //参数（如果没有参数：null）
            //contentType: "application/json; charset=utf-8",
            error: function (msg) {//请求失败处理函数
                alert("添加用户失败");
            },
            success: function (data) { //请求成功后处理函数           
                if (data == null) {
                    alert("添加用户失败");
                }
                if (data == "user not exit!") {
                    alert(data);
                } else if (data == "system error!") {
                    alert(data);
                } else if (data == "you already add this guest!") {
                    alert(data);
                }
                else {
                    var customer = JSON.parse(data);;
                    if (customer.name != null) {
                        //add user card in HTML
                        var userCardList = document.getElementById("userCardList");
                        var userCard = document.getElementsByClassName("userCard")[0];
                        var cloneUserCard = userCard.cloneNode(true);
                        //img
                        //userName
                        cloneUserCard.id = customer.name;
                        cloneUserCard.getElementsByClassName("userName")[0].innerText = customer.name;
                        cloneUserCard.getElementsByClassName("sex")[0].innerHTML = customer.sex == true ? "♂" : "♀";
                        cloneUserCard.getElementsByClassName("birthday")[0].innerText = customer.birthday;
                        userCardList.appendChild(cloneUserCard);
                    } else {
                        alert(customer);
                    }
                }

            }
        }
        )
    });

/*delete user */
$("#userCardList").on('click', '.operation', clickOperationEventFunction);
function clickOperationEventFunction() {
    var parent = $(this).parent();
    var grand_parent = parent.parent();
    var id = parent.attr("id");
    $.ajax({
        type: 'Post',
        //async: false, //同步执行，不然会有问题
        dataType: "text",
        url: "DeleteGuest",
        data: { guestName: id },
        //contentType: "application/json; charset=utf-8",
        error: function (msg) {//请求失败处理函数
            alert("删除好友失败，请稍后重试");
        },
        success: function (data) { //请求成功后处理函数                 
            if (data == "success") {
                //delete node
                parent.remove();
                // parent.innerHTML="";
                // grand_parent.removeChild(parent);
            } else {
                alert(data);
            }
        }
    })
};




/*check all button click evt */
$(function () {
    $("#check_all").click(
        function () {
            var items = document.getElementsByClassName("check_item");
            var ch = this.checked;
            if (ch == false) {
                for (var item in items) {
                    items[item].checked = false;
                }
            } else {
                for (var item in items) {
                    items[item].checked = true;
                }
            }
            updateAmount();
            updateSumPrice();
        }
    )
});
/*update amount */

$(document).ready(updateAmount());
function updateAmount() {
    var item_prices = document.getElementsByClassName("input_menu_number");
    var item_checks = document.getElementById("menu_tbody").getElementsByClassName("check_item");
    var number = 0;
    for (var i = 0; i < item_prices.length; i++) {
        if (item_checks[i].checked) {
            number += Number(item_prices[i].value);
        }
    }
    //set value
    var J_SelectedItemsCount = document.getElementById("J_SelectedItemsCount");
    J_SelectedItemsCount.innerText = number;
}
/*update total price*/
$(document).ready(updateSumPrice);
function updateSumPrice() {
    var sum = 0;
    var item_prices = document.getElementsByClassName("price");
    var item_numbers = document.getElementsByClassName("input_menu_number");
    var item_checks = document.getElementById("menu_tbody").getElementsByClassName("check_item");
    for (var i = 0; i < item_prices.length; i++) {
        if (item_checks[i].checked) {
            sum += Number(item_prices[i].innerText) * item_numbers[i].value;
        }
    }
    //set value
    var J_Total = document.getElementById("J_Total");
    J_Total.innerText = sum.toString();
}

/*page change when add or delete food */
$(document).ready(function () {
    $("#menu_tbody").change(function () {
        // alert("change happen");	
        updateAmount();
        updateSumPrice();
    });
});

/*check change when click check button */
$(document).ready(
    $(".check_item").click(
        function () {
            var checkAll = document.getElementById("check_all");
            if ($(this).attr("id") != "check_all") {
                if ($(this).checked == false) {//change checkAll 's value when uncheck    
                    checkAll.checked = false;
                } else {//change checkAll's value when all are checked
                    var item_checks = document.getElementById("menu_tbody").getElementsByClassName("check_item");
                    var allChecked = true;
                    for (var i = 0; i < item_checks.length; i++) {
                        if (item_checks[i].checked == false) {
                            allChecked = false;
                        }
                    }
                    if (allChecked == true) {
                        checkAll.checked = true;
                    }
                }
                updateAmount();
                updateSumPrice();
            }
        }
    )
);



/*add user ok button */
$(document).ready(
    $("#add_user_ok_btn").click(
        function () {
            var searchbar = document.getElementById("find_user");
            var display = searchbar.style.display;
            if (display != "none") {
                //change css,place it to left and show food, example:document.getElementById("p2").style.color="blue";
                //隐去搜索栏
                searchbar.style.display = "none";
                //隐去删除按钮
                //TODO
                //菜品显示
                showRecommendFood();
                //改变button文字
                this.innerText = "修改用餐人";
            } else {
                //$(this).attr("flag",false);
                //展现搜索栏
                searchbar.style.display = "block";
                //展现删除按钮
                //TODO
                //改变button文字
                this.innerText = "选好了";
                //set recommand page  unvisiable
                var table_divs = document.getElementsByClassName("table_div");
                table_divs[0].style.display = "block";
                table_divs[1].style.display = "none";
            }
        }
    )
);

/*click bottom button evt*/
$(document).ready($(".fixed-bottom").on('click', '#Go_btn', clickBottomButtonEvtFunction));

function clickBottomButtonEvtFunction() {
    var recommand = document.getElementById("recommand_food");
    var confirmOrder = document.getElementById("confirmOrder");
    if (recommand.style.display != "none") {//if in recommand page,go to confirn page      
        //set item to cookie, and check whether has item checked
        if (!setCheckedItemToCookie()) {
            //if has no item be checked, don't 
            alert("请选择至少一件菜品再下单");
            return;
        }

        confirmOrder.style.display = "block";
        recommand.style.display = "none";
        //change bottom button's text
        document.getElementsByClassName("btn-area")[0].innerText = "去支付";
        document.getElementsByClassName("btn-area")[0].innerHTML = "去支付<button type='button'class='btn'id='Go_btn'><span class='glyphicon glyphicon-shopping-cart' aria-hidden='true'></span><b></b></button>";

        //show foods from cookie
        showFoodsFromCookie();
    } else if (confirmOrder.style.display == "block") {//if in confirm order page, go to pay page
        //confirmOrder.style.display = "none";
        //recommand.style.display = "block";
        //change bottom button's text
        // document.getElementsByClassName("btn-area")[0].innerHTML = "结  算<button type='button'class='btn'id='Go_btn'><span class='glyphicon glyphicon-shopping-cart' aria-hidden='true'></span><b></b></button>";
        //go to pay page
        placeOrder();

    } else {

    }
}



$(document).ready(
    $("#to_recommand_page").click(
        function () {
            var recommand = document.getElementById("recommand_food");
            var confirmOrder = document.getElementById("confirmOrder");
            confirmOrder.style.display = "none";
            recommand.style.display = "block";
            //change bottom button's text
            document.getElementsByClassName("btn-area")[0].innerHTML = "结  算<button type='button'class='btn'id='Go_btn'><span class='glyphicon glyphicon-shopping-cart' aria-hidden='true'></span><b></b></button>";
        }
    )
);

function showFoodsFromCookie() {
    var parentNode = document.getElementsByClassName("item_list")[0];
    var itemlist = parentNode.getElementsByClassName("confirm_item");
    //get items from cookie
    var car_cookie = cookieUtil.getCookie("car");
    if (car_cookie == null) {
        //显示没有菜品
    } else {
        var foodlist = JSON.parse(car_cookie);
        var itemNeedDelete = document.getElementsByClassName("confirm_item").length;
        for (var i = 0; i < foodlist.length; i++) {
            //var cloneUserCard = userCard.cloneNode(true);
            var cloneNode = document.getElementsByClassName("confirm_item")[0].cloneNode(true);
            //set visiable
            cloneNode.style.display = "block";
            //set name
            cloneNode.getElementsByClassName("food_name_label")[0].innerText = foodlist[i].name;
            cloneNode.getElementsByClassName("food_number_lable")[0].innerText = foodlist[i].number;
            cloneNode.getElementsByClassName("food_t_price_lable")[0].innerText = foodlist[i].t_price;
            //appendChild
            parentNode.appendChild(cloneNode);
        }
        //delete usesless node
        for (var i = 0; i < itemNeedDelete; i++) {
            parentNode.removeChild(parentNode.children[0]);
        }
    }
}
function setCheckedItemToCookie() {
    var hasChecked = false;
    //delete this cookie if it already exist 
    cookieUtil.unsetCookie("car");

    var foodlist = [];
    //获取每一个checked==true的菜品
    //添加到cookie中
    var food_check_list = $(".check_item");
    for (var i = 0; i < food_check_list.length; i++) {
        if (food_check_list[i].checked == true && food_check_list[i].id != "check_all") {
            hasChecked = true;
            var parent = food_check_list[i].parentNode.parentNode.parentNode;
            var id = parent.id;
            var name = parent.getElementsByClassName("food_name")[0].innerText;
            var s_price = parent.getElementsByClassName("price_txt")[0].getElementsByTagName("strong")[0].innerText;
            var t_price = parseFloat(s_price);
            var number = parent.getElementsByClassName("input_menu_number")[0].value;

            //add to cookie
            var food = {
                "id": id,
                "name": name,
                number: number,
                t_price: t_price
            }
            foodlist.push(food);
        }
    }
    if (!hasChecked) {
        return false;
    }
    var date = new Date();
    date.setDate(date.getDate() + 10); //保存十天
    //保存cookie
    cookieUtil.setCookie("car", JSON.stringify(foodlist), date);
    return true;
}



function placeOrder() {
    var remark =document.getElementById("remark").value;
    var carCookie=cookieUtil.getCookie("car");
    var carList=JSON.parse(carCookie);
    $.ajax({
        type: 'Post',
        //async: false, //同步执行，不然会有问题
        url: "PlaceOrder",
        data: {
           "car": JSON.stringify(carList),
           "remark":remark
        },
        dataType: "json",
        //contentType: "application/json; charset=utf-8",
        error: function (msg) {//请求失败处理函数
            alert("服务器出错");
        },
        success: function (data) { //请求成功后处理函数 
        	order = eval(data);
            if(order.id==null){
                alert("下单失败，请一分钟后重试");
                return;
            }         	
            // document.getElementById("body_multi").style.display = "none";
            // document.getElementById("pay_page").style.display = "block";
            window.location.href="paypage.html";
            //set order to cookie
            cookieUtil.setCookie("order",JSON.stringify(order));
        }
    })

}
