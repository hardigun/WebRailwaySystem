var DateTimeUtil = {

    "getFormatedDate" : function(timestamp, addMinutes) {
        var date = new Date(timestamp);
        if(addMinutes !== undefined) {
            date = new Date(timestamp + (addMinutes * 60000));
        }
        var day = date.getDate();
        if(day < 10) {
            day = "0" + day;
        }
        var month = date.getMonth() + 1;
        if(month < 10) {
            month = "0" + month;
        }
        var year = date.getFullYear();
        var hour = date.getHours();
        if(hour < 10) {
            hour = "0" + hour;
        }
        var minutes = date.getMinutes();
        if(minutes < 10) {
            minutes = "0" + minutes;
        }
        return day + "." + month + "." + year + " " + hour + ":" + minutes;
    }

}

$(document).ready(function() {
    $(".date-input").datepicker({
        dateFormat: "dd.mm.yy",
        changeYear: true
    });

    $(".date-time-input").datetimepicker({
        dateFormat: "dd.mm.yy",
        timeFormat: "HH:mm"
    })

    $("#passengers-spinner-input").spinner({
        step: 50,
        min: 0,
        max: 1000
    }).val(500);

    $(".timeoffset-spinner-input").spinner({
        step: 60,
        min: 0
    });

});