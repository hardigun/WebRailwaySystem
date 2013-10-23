var ConfirmSaleHandler = function() {

    this.ajaxUrl = "/rest/ticket/confirm";

    this.handleResult = function(data) {
        var saleConfirmedCount = 0;
        var i = data.length - 1;
        while(i >= 0) {
            if(data[i].saleConfirmed == true) {
                saleConfirmedCount += 1;
                $("#confirm-status_" + data[i].id).html("true");
            }
            i -= 1;
        }
        $("#last-operation-result").html("Обработано билетов: " + data.length + ". Подтверждено " + saleConfirmedCount + ".");
    }

    this.sendRequest = function(requestParams) {
        $("#loading-confirm").removeAttr("style");
        var handleResultFunc = this.handleResult;
        $.ajax({
            type: "POST",
            url: this.ajaxUrl,
            data : requestParams,
            dataType: "json",
            success: function(data) {
                handleResultFunc(data)
                $("#loading-confirm").attr("style", "visibility: hidden;");
            },
            error: function() {
                alert("Server error!")
                $("#loading-confirm").attr("style", "visibility: hidden;");
            }
        });
    }

    this.prepareRequest = function() {
        var tickets = "";
        $(".ticketConfirmCheckbox").each(function() {
            if($(this).is(":checked")) {
                var keyParts = $(this).attr("id").split("_");
                if(tickets.length > 0) {
                    tickets += "&";
                }
                tickets += "tickets[]=" + keyParts[1];
                $(this).removeAttr("checked");
            }
        });
        if(tickets.length > 0) {
            this.sendRequest(tickets);
        }
    }

}

$(document).ready(function() {
    var confirmSaleHandler = new ConfirmSaleHandler();
    $("#confirmSaleButton").click(function() {
        confirmSaleHandler.prepareRequest();
    });
});