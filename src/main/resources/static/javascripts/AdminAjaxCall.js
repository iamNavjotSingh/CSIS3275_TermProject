$(document).on('click', '#serverTime', function () {
    console.log("entered");
    serverTime();
});
var serverTime = function () {
    var date = $.ajax({
        url: "/servertime"
    });
    console.log(date);
    date.done(function (date) {
        $('#server').append("<li>" + date + "</li>");
    });
    date.fail(function (jqXHR, textStatus) {
        console.log("Error in fetching date");
    })
};