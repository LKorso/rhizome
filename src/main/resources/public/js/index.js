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
    $.post("/login", createLoginDto()).done(function () {
        window.location = "../html/userProfile.html";
    }).fail(function (response) {
        if (response.status === 400) {
            $("#password").val('');
            showErrors(response.responseJSON.errors);
        }
    });
}

function createLoginDto() {
    return {
        "email"     : $("#email").val(),
        "password"  : $("#password").val()
    }
}
