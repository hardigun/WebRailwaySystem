var ShowStationsHandler = function(tableIn) {

    this.table = tableIn;

    this.requestUrl = "/rest/get/route-stations/{id}";

    this.departureDate = -1;

    this.showStationsTable = function(data, depDateIn) {
        $("#routeStationsTable").find("tr").remove();
        $("#routeStationsTable").append("<tr><td>Station</td><td>Date and time</td></tr>")

        var i = 0;
        while(i <= data.length - 1) {
            var dateTime = data[i].timeOffset;
            if(depDateIn != -1) {
                dateTime = DateTimeUtil.getFormatedDate(depDateIn, data[i].timeOffset);
            }
            var appendRow = "<tr><td>" + data[i].stationInfo.title + "</td><td>" + dateTime + "</td></tr>"
            $("#routeStationsTable").append(appendRow);

            i += 1;
        }

        $("#routeStationsTable").removeAttr("style");
    }

    this.sendRequest = function() {
        var successFunc = this.showStationsTable;
        var depDate = this.departureDate;
        $("#loading-stations").removeAttr("style");
        $.ajax({
            type: "GET",
            url: this.requestUrl,
            dataType: "json",
            success: function(data) {
                successFunc(data, depDate);
                $("#loading-stations").attr("style", "visibility: hidden;");
            },
            error: function() {
                alert("Server error!")
                $("#loading-stations").attr("style", "visibility: hidden;");
            }
        });
    }

    this.prepareRequest = function() {
        var routeId = -1;
        var depDate = -1;
        $(".sheduleItemRadio").each(function() {
            if($(this).is(":checked")) {
                routeId = $(this).attr("route_id");
                depDate = $(this).attr("departure_date");
                return false;
            }
        });
        if(routeId > -1) {
            this.requestUrl = this.requestUrl.replace(/{id}/ig, routeId);
            this.departureDate = depDate * 1;
            return true;
        }
        return false;
    }

    this.handle = function() {
        if(this.prepareRequest()) {
            this.sendRequest();
        }
    }

}

$(document).ready(function() {
    $("#buyButton").on("click", function(e) {
        $(".sheduleItemRadio").each(function() {
            if($(this).is(":checked")) {
                var link = $("#buyForm").attr("action")
                link = link.replace(/{id}/ig, $(this).attr("shedule_item_id"))
                $("#buyForm").attr("action", link).submit();
            }
        });
    });

    $("#showStationsButton").click(function() {
        var showStationsHandler = new ShowStationsHandler();
        showStationsHandler.handle();
    });
});