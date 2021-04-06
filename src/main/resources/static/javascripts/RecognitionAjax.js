$(document).on('click', '#recognize', function () {
    console.log("entered");
    recognize();
});
var recognize = function () {
    var recognition = $.ajax({
        url: "/recognition"
    });
    console.log(date);
    date.done(function (date) {
        $('#server').a("<li>" + date + "</li>");
    });
    date.fail(function (jqXHR, textStatus) {
        console.log("Error in fetching date");
    })
};