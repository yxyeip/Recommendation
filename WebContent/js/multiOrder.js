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
                data: {userName:$(this).val()},    //参数（如果没有参数：null）
                //contentType: "application/json; charset=utf-8",
                error: function (msg) {//请求失败处理函数
                    alert("数据加载失败");
                },
                success: function (data) { //请求成功后处理函数
                    var objData = eval(data);
                    if (objData.length > 0) {
                        var layer = "";
                        layer = "<table id='result_table'>";
                        for(var i=0;i<objData.length;i++){
                            layer += "<tr class='line'><td class='std'>" + objData[i] + "</td></tr>";
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
                            $("#searchresult").css("display", "none");
                        });
                    } else {
                        $("#searchresult").empty();
                        $("#searchresult").css("display", "none");
                    }
                }
            });
        }
        else if (k == 38) {//上箭头
            $('#aa tr.hover').prev().addClass("hover");
            $('#aa tr.hover').next().removeClass("hover");
            $('#input_find').val($('#aa tr.hover').text());
        } else if (k == 40) {//下箭头
            $('#aa tr.hover').next().addClass("hover");
            $('#aa tr.hover').prev().removeClass("hover");
            $('#input_find').val($('#aa tr.hover').text());
        }
        else if (k == 13) {//回车
            $('#input_find').val($('#aa tr.hover').text());
            $("#searchresult").empty();
            $("#searchresult").css("display", "none");
        }
        else {
            $("#searchresult").empty();
            $("#searchresult").css("display", "none");
        }
    });
    $("#searchresult").bind("mouseleave", function () {
        $("#searchresult").empty();
        $("#searchresult").css("display", "none");
    });
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
/*check all button click evt */
$(function () {
$("#check_all").click(
    function(){
        var items=document.getElementsByClassName("check_item");
        var ch=this.checked;
        if(ch==false){
            for(var item in items){
                items[item].checked=false;
            }
        }else{
            for(var item in items){
                items[item].checked=true;
            }
        }
    }
)
});
/*update amount */

$(document).ready(updateAmount());
function updateAmount(){

   var item_prices=document.getElementsByClassName("input_menu_number");
   var number=0;
   for(var i=0;i< item_prices.length;i++){
       
        number+=Number(item_prices[i].value);
    }
   //set value
   var J_SelectedItemsCount=document.getElementById("J_SelectedItemsCount");
   J_SelectedItemsCount.innerText=number;
}
/*update total price*/
$(document).ready(updateSumPrice);
function updateSumPrice(){
    var sum=0;
    var item_prices=document.getElementsByClassName("price");
    var item_numbers=document.getElementsByClassName("input_menu_number");
    for(var i=0;i<item_prices.length;i++){
        sum+=Number(item_prices[i].innerText)*item_numbers[i].value;
    }
    //set value
    var J_Total=document.getElementById("J_Total");
    J_Total.innerText=sum.toString();
}
$(document).ready(function(){
    		$("#menu_tbody").change(function(){
        			alert("change happen");	
    		});	
	});