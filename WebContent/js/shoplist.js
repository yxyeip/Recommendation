$("#J_SelectAllCbx1").click(
    function(){
        var items=document.getElementsByClassName("J_CheckBoxItem");
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
);

/*get amount */

$(document).ready(updateAmount());
function updateAmount(){

   var items=document.getElementsByClassName("item-body");
   var number=items.length;
   //set value
   var J_SelectedItemsCount=document.getElementById("J_SelectedItemsCount");
   J_SelectedItemsCount.innerText=number;
}

$(document).ready(updateSumPrice);
function updateSumPrice(){
    var sum=0;
    var item_prices=document.getElementsByClassName("J_Price");
    for(var i in item_prices){
        sum+=Number(item_prices[i].innerText);
    }
    //set value
    var J_Total=document.getElementById("J_Total");
    J_Total.innerText=sum.toString();
}
