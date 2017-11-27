$("document").ready(function () {
    $("#registration").click(showRegistrationWindow);
    $("#login").click(login);
});

function showRegistrationWindow() {
    window.location = "../html/registration.html";
}

function login() {
    $.post("/login", createLoginDto()).done(function () {
        window.location = "../html/userProfile.html";
    }).fail(function (response) {
        alert(response);
    });
}

function createLoginDto() {
    return {
        "email"     : $("#email").val(),
        "password"  : $("#password").val()
    }
}
