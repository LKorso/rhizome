var context;
$("document").ready(function () {
    $.ajax({
        url: '/user',
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            context = response;
            initialize(response);
        }
    });
});

function initialize(response) {
    $("#f-name").text(valueOrDefault(response.firstName, 'FirstName is not set'));
    $("#l-name").text(valueOrDefault(response.lastName, 'LastName is not set'));
    $("#change-profile").click(openChangeProfilePage);
}

function openChangeProfilePage() {
    $(".container-fluid").load("../html/userProfile-changeData.html", initChangeDataPage);
}

function initChangeDataPage() {
    $("#firstName").val(valueOrDefault(context.firstName, ''));
    $("#lastName").val(valueOrDefault(context.lastName, ''));
    $("#save-changes").click(updateProfileData);
}

function updateProfileData() {
    $.ajax({
        url: '/user',
        type: 'PUT',
        dataType: 'json',
        data: {
            "firstName": $("#firstName").val(),
            "lastName": $("#lastName").val()
        },
        success: toUserProfile
    })
}

function toUserProfile() {
    window.location = "../html/userProfile.html";
}

function valueOrDefault(value, defaultValue) {
    return value === null || value === undefined ? defaultValue : value;
}
