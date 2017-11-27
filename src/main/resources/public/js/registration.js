$("document").ready(function () {
    $("#register").click(register);
});

function register() {
    $.post("/user", createRegistrationDto()).done(function () {
        window.location = "../index.html";
    }).fail(function (response) {
        alert(response);
    });
}

function createRegistrationDto() {
    return {
        "email"     : $("#email").val(),
        "password"  : $("#password").val(),
        "confirmationPassword" : $("#confirmPassword").val()
    }
}
