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
});