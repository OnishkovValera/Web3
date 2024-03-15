window.onload = function (){
    getPoints();
    drawGraph(4);
    drawArrow( SIZE / 2, SIZE, SIZE / 2, 0);
    document.getElementById("feedback:x:4").checked = true

}
document.querySelectorAll('input[class="r-button"]').forEach(radio=>{
    radio.addEventListener('click', ()=>{
        lastClickedRadius = radio.value
        drawGraph(radio.value)
    })
})

document.getElementById("feedback:clear-button").addEventListener("click", function (){
    arrData = []
    drawGraph(lastClickedRadius)
})
document.getElementById("feedback:check-button").addEventListener("click", function (){
    let x = 0;
    const buttons = document.querySelectorAll('input[type=radio]')
    for(const button of buttons){
        if(button.checked){
           x = Number(button.value);
        }
    }
    let y;
    let string_y = document.getElementById("feedback:yInput").value;
    if(string_y === ""){
        alert("Значения невалидны")
        return;
    }else{
        y = Number(string_y)
    }
    let r = Number(lastClickedRadius);

    if(isDataValid(x, y, r)){
        arrData.push({"x":x, "y":y, "r":r, "success": isHit(x, y,r)})
        drawGraph(r);
    }else{
        alert("Значения невалидны")
    }

})

document.getElementById("feedback:x:1").addEventListener("click", function () {
    alert(arrayData);
})
canvas.addEventListener("click", function (event){
    parseCanvasClick(event);
});

function isDataValid(x, y, r){
    return (r === 1 || r === 2 || r === 3 || r === 4)
        && (y >= -3 && y <= 3)
        && (x >= -2 && x <= 2 );
}
function isHit(x, y, r){
    if(x > 0 & y > 0){
        return false;
    }else if(x <= 0 & y >= 0){
        return y <= x + r;
    }else if(x <= 0 & y <= 0) {
        return x * x + y * y <= (r * r);
    } else if (x >= 0 & y <= 0){
        if(x <= r & Math.abs(y) <= r/2){
            return true;
        }
        return false
    }else{
        return false;
    }
}