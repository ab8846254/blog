$(function(){

    $("#typed").typed({
        strings: ["爱上大家安静地卢卡斯觉得卢卡斯几点啦时刻记得卢卡斯角度看辣椒水的拉升阶段爱上大家安静地卢卡斯觉得卢卡斯几点啦时刻记得卢卡斯角度看辣椒水的拉升阶段爱上大家安静地卢卡斯觉得卢卡斯几点啦时刻记得卢卡斯角度看辣椒水的拉升阶段爱上大家安静地卢卡斯觉得卢卡斯几点啦时刻记得卢卡斯角度看辣椒水的拉升阶段爱上大家安静地卢卡斯觉得卢卡斯几点啦时刻记得卢卡斯角度看辣椒水的拉升阶段爱上大家安静地卢卡斯觉得卢卡斯几点啦时刻记得卢卡斯角度看辣椒水的拉升阶段爱上大家安静地卢卡斯觉得卢卡斯几点啦时刻记得卢卡斯角度看辣椒水的拉升阶段爱上大家安静地卢卡斯觉得卢卡斯几点啦时刻记得卢卡斯角度看辣椒水的拉升阶段爱上大家安静地卢卡斯觉得卢卡斯几点啦时刻记得卢卡斯角度看辣椒水的拉升阶段爱上大家安静地卢卡斯觉得卢卡斯几点啦时刻记得卢卡斯角度看辣椒水的拉升阶段爱上大家安静地卢卡斯觉得卢卡斯几点啦时刻记得卢卡斯角度看辣椒水的拉升阶段"],
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