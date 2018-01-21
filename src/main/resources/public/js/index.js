$("document").ready(function () {
    $("#registration").click(showRegistrationWindow);
    $("#login").click(onClickLogin);
});

function showRegistrationWindow() {
    window.location = "../html/registration.html";
}

function onClickLogin() {
    cleanValidation();
    login();
}

function login() {
    $.ajax({
        url: '/oauth/token',
        type: 'POST',
        data: createLoginDto(),
        headers: createLoginHeader(),
        dataType: 'json',
        success: toUserProfile,
        error: handleLogInErrors
    });
    // $.post("/login", createLoginDto())
    //     .done(function (data, status, xhr) {
    //         console.log(xhr.getAllResponseHeaders());
    //         window.location = "../html/userProfile.html";
    //     })
    //     .fail(function (response) {
    //         if (response.status === 401) {
    //             $("#password").val('');
    //             showErrors(response.responseJSON.errors);
    //         }
    //     });
}

function createLoginDto() {
    return {
        "grant_type": 'password',
        "username": $("#email").val(),
        "password": $("#password").val()
    }
}

function createLoginHeader() {
    return {
        "Authorization": 'Basic ' + btoa('client-app:secret'),
        "Content-Type": 'application/x-www-form-urlencoded'
    }
}

function toUserProfile(data) {
    window.location = "../html/userProfile.html";
}

function handleLogInErrors(response) {
    if (response.status === 401 && response.responseJSON != undefined) {
        $("#password").val('');
        showErrors(response.responseJSON.errors);
    }
}