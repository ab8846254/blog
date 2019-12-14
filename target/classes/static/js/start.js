$(function(){

    $("#typed").typed({
        strings: ["梁超，JAVA爱好者，折腾在 0和 1 世界的菜鸡, 希望结识可以共同成长的小伙伴热爱编程，喜欢折腾，正在探索高效学习编程技术的方法..."],

        typeSpeed: 100,
        callback: function(){
            shift();
        }
    });

});
function shift(){
    $(".head-wrap").addClass("shift-text");
    terminalHeight();
}

function terminalHeight(){
    var termHeight = $(".terminal-height");
    var value = termHeight.text();
    value = parseInt(value);
    setTimeout(function(){
        if (value > 10){
            value = value-1;
            this.txtValue = value.toString();
            termHeight.text(this.txtValue);
            self.terminalHeight();
        }
        else{
            clearTimeout();
        }
    }, 10);
}