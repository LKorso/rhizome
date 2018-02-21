$("document").ready(function () {
    $.ajax({
        url: '/user',
        type: 'GET',
        dataType: 'json',
        success: initialize
    });
});

function initialize(response) {
    $("#f-name").text(valueOrDefault(response.firstName, 'FirstName is not set'));
    $("#l-name").text(valueOrDefault(response.lastName, 'LastName is not set'));
    $("#change-profile").click(openChangeProfilePage);
}

function valueOrDefault(value, defaultValue) {
    return value === null || value === undefined ? defaultValue : value;
}

function openChangeProfilePage() {
    window.location = "../html/userProfile-changeData.html";
}