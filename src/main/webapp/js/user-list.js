$(document).ready(function() {
    $("#removeUser").click(function(e) {
        e.preventDefault();
        $("#remove-loading").removeAttr("style");
        var userId = $(this).attr("user_id");
        $.ajax({
            type: "GET",
            url: $(this).attr("href"),
            dataType: "json",
            success: function(data) {
                if(data == true) {
                    $("#resultMessage").html("Operation execute success!");
                    $("#user_" + userId).remove();
                }
                $("#remove-loading").attr("style", "display: none;");
            },
            error: function() {
                $("#resultMessage").html("Server error!");
                $("#remove-loading").attr("style", "display: none;");
            }
        });
        return false;
    });
});