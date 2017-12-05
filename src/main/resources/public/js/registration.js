$("document").ready(function () {
    $("#register").click(onClick);
});

function onClick() {
    cleanPage();
    register();
}

function register() {
    $.post("/user", createRegistrationDto()).done(function () {
        window.location = "../index.html";
    }).fail(function (response) {
        if(response.status === 400) {
            $("#confirmPassword").val('');
            defineErrors(response.responseJSON).forEach(handleValidation);
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

function defineErrors(responseJSON) {
    return _.isUndefined(responseJSON.errors) ? responseJSON : responseJSON.errors;
}

function handleValidation(errorDto) {
    $("<span class='errorSpan'>" + errorDto.message + "</span>").appendTo("." + errorDto.field);
    $(".errorSpan").css("color", "red");
}

function cleanPage() {
    $(".errorSpan").remove();
}
