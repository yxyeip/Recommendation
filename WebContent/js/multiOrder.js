$(document).ready(
    function loadGuests() {
        $.ajax({
            type: 'Post',
            //async: false, //同步执行，不然会有问题
            //dataType: "json",
            url: "LoadGuests",
            data: { guestName: $("#input_find").val() },    //参数（如果没有参数：null）
            //contentType: "application/json; charset=utf-8",
            error: function (msg) {//请求失败处理函数
                alert("数据加载失败");
            },
            success: function (data) {
                var guestList = eval(data);
                var list = document.getElementById("userCardList");
                var firstChild = list.getElementsByClassName("userCard")[0];
                var cloneUserCard = firstChild.cloneNode(true);

                var remove = list.getElementsByClassName("userCard");
                for (var i = 0; i < list.length; i++) {
                    cloneUserCard.id = userWannaAdd.name;
                    cloneUserCard.getElementsByClassName("userName")[0].innerText = liguestListst[i].name;
                    cloneUserCard.getElementsByClassName("sex")[0].innerText = guestList[i].sex == true ? "男" : "女";
                    cloneUserCard.getElementsByClassName("birthday")[0].innerText = guestList[i].birthday;
                    if (remove != null) {
                        list.removeChild(remove);
                    }
                    list.appendChild(cloneUserCard);
                }


            }
        })

    }
);

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
                alert("数据加载失败");
            },
            success: function (data) { //请求成功后处理函数
                //add user card in HTML
                var userCard = document.getElementsByClassName("userCard")[0];
                var cloneUserCard = userCard.cloneNode(true);
                //img
                //userName
                cloneUserCard.id = userWannaAdd.name;
                cloneUserCard.getElementsByClassName("userName")[0].innerText = userWannaAdd.name;
                cloneUserCard.getElementsByClassName("sex")[0].innerText = userWannaAdd.name == true ? "男" : "女";
                cloneUserCard.getElementsByClassName("birthday")[0].innerText = userWannaAdd.birthday;
            }
        }
        )
    });





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
