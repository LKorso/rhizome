$("document").ready(function () {
    $.ajax({
        url: '/user',
        type: 'GET',
        dataType: 'json',
        success: initialize
    });
});

function initialize(response) {
    $("#firstName").val(valueOrDefault(response.firstName, ''));
    $("#lastName").val(valueOrDefault(response.lastName, ''));
    $("#change-profile").click(updateProfileData);
}

function valueOrDefault(value, defaultValue) {
    return value === null || value === undefined ? defaultValue : value;
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

function toUserProfile(response) {
    window.location = "../html/userProfile.html";
}