var TableHandler = function(tableNameIn, clonedSelectNameIn, checkboxClassIn){
    this.tableName = tableNameIn;
    this.clonedSelectName = clonedSelectNameIn;
    this.clonedOptionsHtml = "";
    this.checkboxClass = checkboxClassIn;

    this.checkboxTempalte = "<tr id='station_{index}'><td><input type='checkbox' class='" + this.checkboxClass + "' " +
        "row_id='station_{index}' /></td>";
    this.stationInfoTemplate = "<td><select id='stationinfo_{index}' name='stationsinfo[]'>" +
                                "{options}</select></td>";
    this.timeOffsetTemplate = "<td><input type='text' id='timeoffset_{index}' name='timeoffsets[]' /></td><td></td></tr>";

    this.addRow = function() {
        var rowCount = $("#" + this.tableName).find("tr").length;
        if(this.clonedOptionsHtml.length == 0) {
            this.fillClonedOptionsHtml();
        }
        var checkbox = this.checkboxTempalte.replace(/{index}/ig, rowCount);
        var stationInfo = this.stationInfoTemplate.replace(/{index}/ig, rowCount).replace(/{options}/ig, this.clonedOptionsHtml);
        var timeOffset = this.timeOffsetTemplate.replace(/{index}/ig, rowCount);

        $("#" + this.tableName).append(checkbox + stationInfo + timeOffset);
    }

    this.fillClonedOptionsHtml = function() {
        var concatClonedOptionsHtml = "";
        $("#" + this.clonedSelectName).find("option").each(function() {
            concatClonedOptionsHtml += "<option value='" + this.value + "'>" + this.text + "</option>"
        });
        this.clonedOptionsHtml = concatClonedOptionsHtml;
    }

    this.removeRow = function() {
        var tn = this.tableName;
        $("." + this.checkboxClass).each(function(tn) {
            if($(this).is(":checked")) {
                var rowId = $(this).attr("row_id");
                $("#" + rowId).remove();
            }
        });
    }
}

$(document).ready(function() {
    $("#stationInfoSelect").css("display", "none");
    var tableHandler = new TableHandler("stationsTable", "stationInfoSelect", "stationCheckboxes");
    $("#addStationButton").on("click", function() {
        tableHandler.addRow();
    });
    $("#removeStationButton").on("click", function() {
        tableHandler.removeRow();
    });
});


