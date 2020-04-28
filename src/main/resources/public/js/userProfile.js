var context;
$("document").ready(function () {
    $.ajax({
        url: '/users/' + parseUri().id,
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
    $("#find-friends").click(openUsersPage);
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
        url: context._links.update.href,
        type: 'PATCH',
        headers: {
            "content-type": 'application/json'
        },
        dataType: 'json',
        data: JSON.stringify({
            "firstName": $("#firstName").val(),
            "lastName": $("#lastName").val()
        }),
        success: toUserProfile
    })
}

function toUserProfile() {
    window.location = "../html/userProfile.html?id=" + parseUri().id;
}

function openUsersPage() {
    $(".container-fluid").load("../html/users.html", initUsersPage);
}

function initUsersPage() {
    $.ajax({
        url: '/users/0/10',
        type: 'GET',
        dataType: 'json',
        success: initializeUsersData
    })
}

function initializeUsersData(response) {
    response.forEach(function (value) {
        $(".users").append($("<li></li>")).text(value.firstName);
    })
}

function valueOrDefault(value, defaultValue) {
    return value === null || value === undefined ? defaultValue : value;
}

function parseUri() {
    var result = {};
    if ("" !== window.location.search) {
        var valuePairs = window.location.search.substr(1).split("&"), i;
        for (i in valuePairs) {
            i = valuePairs[i].split("=");
            result[decodeURIComponent(i[0])] = decodeURIComponent(i[1]);
        }
    }
    return result;
}
