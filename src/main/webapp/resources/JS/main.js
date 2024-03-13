window.onload = function (){
    drawGraph(4);
    drawArrow( SIZE / 2, SIZE, SIZE / 2, 0);
    document.getElementById("feedback:x:4").checked = true
    window.sessionStorage.setItem("array", arrayData);

    console.log(arrayData);
}
document.querySelectorAll('input[class="r-button"]').forEach(radio=>{
    radio.addEventListener('click', ()=>{
        lastClickedRadius = radio.value
        drawGraph(radio.value)
    })
})
document.getElementById("feedback:x:1").addEventListener("click", function () {
    alert(arrayData);
})
canvas.addEventListener("click", function (event){
    parseCanvasClick(event);
});
