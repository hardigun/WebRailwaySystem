var TreeHandler = function (treeIn) {

    this.tree = treeIn;

    this.showSheduleItemsTable = function(ticketsInfoArray) {
        $("#sheduleItemsTable").find("tr:gt(0)").remove();
        var i = 0;
        while(i <= ticketsInfoArray.length - 1) {
            var depDate = DateTimeUtil.getFormatedDate(ticketsInfoArray[i].departureDate);
            var appendRowContent = "<tr><td><input type='radio' name='showPassengersRadio' shedule_item_id='" +
                            ticketsInfoArray[i].sheduleItemId + "' class='showPassengersRadio' /></td>" +
                            "<td>" + ticketsInfoArray[i].trainNumber + "</td>" +
                            "<td>" + depDate + "</td>" +
                            "<td>" + ticketsInfoArray[i].ticketsCount + "</td>" +
                            "<td>" + ticketsInfoArray[i].ticketsConfirmed + "</td></tr>";
            $("#sheduleItemsTable").append(appendRowContent);

            i += 1;
        }
        $("#sheduleItemsTable").removeAttr("style");
    };

    this.refreshRoute = function(node, data) {
        var ticketsInfoArray = [];
        node.removeChildren();
        var i = 0;
        while(i <= data.length - 1) {
            var train = data[i].train;
            node.addChild({
                "key" : "train_" + data[i].departureDate,
                "title" : "#" + train.trainNumber + " (" + train.capacity + " pass.)",
            });

            /*add train tickets info to array for display in the sheduleItemsTable*/
            var saleConfirmedCount = 0;
            var tList = data[i].ticketsList;
            var j = tList.length - 1;
            while(j >= 0) {
                if(tList[j].saleConfirmed == true) {
                    saleConfirmedCount += 1;
                }
                j -= 1;
            }
            var ticketInfo = {
                "sheduleItemId" : data[i].id,
                "trainNumber" : train.trainNumber,
                "departureDate" : data[i].departureDate,
                "ticketsCount" : tList.length + " from " + train.capacity,
                "ticketsConfirmed" : saleConfirmedCount + " from " + tList.length
            };
            ticketsInfoArray.push(ticketInfo);
            /*---------*/

            i += 1;
        }
        this.showSheduleItemsTable(ticketsInfoArray);
    }

    this.refreshTree = function(data) {
        var tree = $("#routesTrainsTree").dynatree("getTree");
        var rootNode = tree.getNodeByKey("route_all");
        rootNode.removeChildren();
        for(var route in data) {
            /*data[route][0] must exist because otherwise it will be unused route and will not display in this tree*/
            var routeKey = "route_" + data[route][0].route.id;

            var children = [];
            var i = 0;
            while(i <= data[route].length - 1) {
                var train = data[route][i].train;
                children.push({
                    "key" : "train_" + data[route][i].departureDate,
                    "title" : "#" + train.trainNumber + " (" + train.capacity + " pass.)"
                });

                i += 1;
            }

            rootNode.addChild({
                "key" : routeKey,
                "title" : route,
                "isFolder" : true,
                "children" : children
            });
        }
    };

    this.handle = function(routeId, node, data) {
        if(routeId == "all") {
            this.refreshTree(data);
            $("#sheduleItemsTable").attr("style", "display: none;");
            $("#routeStationsTable").attr("style", "display: none;");
        } else {
            this.refreshRoute(node, data);
            $("#routeStationsTable").attr("style", "display: none;");
        }
    };

}

var ShowStationsHandler = function(tableIn) {

    this.table = tableIn;

    this.requestUrl = "/rest/get/route-stations/{id}";

    this.departureDate = -1;

    this.showStationsTable = function(data, departureDateIn) {
        $("#routeStationsTable").find("tr").remove();
        if(departureDateIn != -1) {
            $("#routeStationsTable").append("<tr><td>Station</td><td>Date and time</td></tr>")
        } else {
            $("#routeStationsTable").append("<tr><td>Station</td><td>Time offset(minutes)</td></tr>")
        }
        var i = 0;
        while(i <= data.length - 1) {
            var dateTime = data[i].timeOffset;
            if(departureDateIn != -1) {
                dateTime = DateTimeUtil.getFormatedDate(departureDateIn, data[i].timeOffset);
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
        $("#loading-routes").removeAttr("style");
        $.ajax({
            type: "GET",
            url: this.requestUrl,
            dataType: "json",
            success: function(data) {
                successFunc(data, depDate);
                $("#loading-routes").attr("style", "visibility: hidden;");
            },
            error: function() {
                alert("Server error!")
                $("#loading-routes").attr("style", "visibility: hidden;");
            }
        });
    }

    this.prepareRequest = function(activeTreeNode) {
        var nodeId = activeTreeNode.data.key;
        if(nodeId == "route_all" || nodeId.indexOf("shedule_item") >= 0) {
            return false;
        }
        var routeId = 0;
        if(nodeId.indexOf("route") >= 0) {
            routeId = nodeId.split("_")[1];
        } else {
            routeId = activeTreeNode.parent.data.key.split("_")[1];
            this.departureDate = nodeId.split("_")[1] * 1;
        }
        this.requestUrl = this.requestUrl.replace("{id}", routeId);
        return true;
    }

    this.handle = function(activeTreeNode) {
        if(this.prepareRequest(activeTreeNode)) {
            this.sendRequest();
        }
    }

}

$(document).ready(function() {

    $("#routesTrainsTree").dynatree({
        debugLevel: 0,

        onExpand: function(expanded, node) {
            if(!expanded) {
                return;
            }
            var nodeKey = node.data.key;
            var nodeKeyParts = nodeKey.split("_");
            if(nodeKeyParts[0].toLowerCase().indexOf("route") >= 0) {
                var ajaxUrl = "/rest/get/trains-by-routes/" + nodeKeyParts[1];
                if(nodeKeyParts[1] == "all") {
                    ajaxUrl = "/rest/get/trains-by-routes/"
                }
                var dynaTree = $("#routesTrainsTree").dynatree("getTree");
                var treeHandler = new TreeHandler(dynaTree);
                $("#loading-routes").removeAttr("style");
                $.ajax({
                        type: "GET",
                        url: ajaxUrl,
                        dataType: "json",
                        success: function(data) {
                            treeHandler.handle(nodeKeyParts[1], node, data)
                            $("#loading-routes").attr("style", "visibility: hidden;");
                        },
                        error: function() {
                            alert("Server error!")
                            $("#loading-routes").attr("style", "visibility: hidden;");
                        }
                });
            }
        }
    });

    $("#showPassengersButton").click(function() {
        $(".showPassengersRadio").each(function() {
            if($(this).is(":checked")) {
                var sheduleItemId = $(this).attr("shedule_item_id");
                var submitForm = $("#showTicketsRedirectForm");
                var submitFormLink = submitForm.attr("action");
                submitForm.attr("action", submitFormLink.replace("{id}", sheduleItemId));
                submitForm.submit();
                return false;
            }
        });
    });

    $("#showRouteStationsButton").click(function() {
        var showStationsHandler = new ShowStationsHandler();
        showStationsHandler.handle($("#routesTrainsTree").dynatree("getActiveNode"));
    });

});

