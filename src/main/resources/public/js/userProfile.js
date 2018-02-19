$("document").ready(function () {
    $.ajax({
        url: '/user',
        type: 'GET',
        dataType: 'json',
        success: initialize
    });
});

function initialize(response) {
    response;
}