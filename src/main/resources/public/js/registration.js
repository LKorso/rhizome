$("document").ready(function () {
    $("#register").click(onClick);
});

function onClick() {
    cleanValidation();
    register();
}

function register() {
    $.post("/user", createRegistrationDto()).done(function () {
        window.location = "../index.html";
    }).fail(function (response) {
        if(response.status === 400) {
            $("#confirmPassword").val('');
            showErrors(response.responseJSON.errors);
        }
    });
}

function createRegistrationDto() {
    return {
        "email"     : $("#email").val(),
        "password"  : $("#password").val(),
        "confirmationPassword" : $("#confirmPassword").val()
    }
}
