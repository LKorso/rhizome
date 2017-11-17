$("document").ready(function () {
    $("#register").click(register);
});

function register() {
    $.post("/user", createRegistrationDto()).done(function () {
        var r = 0;
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
