$("document").ready(function () {
    $("#register").click(onClick);
});

function onClick() {
    cleanValidation();
    register();
}

function register() {
    $.post("/users", createRegistrationDto()).done(function () {
        window.location = "../";
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
