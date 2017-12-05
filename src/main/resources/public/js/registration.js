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
            response.responseJSON.errors.forEach(handleValidation);
            $(".errorSpan").css("color", "red");
        }
    });
}

function handleValidation(error) {
    if (error.fields.length === 1) {
        applyErrorSpan("." + error.fields[0], error.message);
    } else {
        appendSpanForFewFields(error.fields, error.message);
    }
}

function createRegistrationDto() {
    return {
        "email"     : $("#email").val(),
        "password"  : $("#password").val(),
        "confirmationPassword" : $("#confirmPassword").val()
    }
}

function cleanPage() {
    $(".errorSpan").remove();
}

function defineFewFieldsSelector(fields) {
    var selector = "";
    fields.forEach(function (field) {
        selector += "." + field + " ";
    });
    return selector;
}

function appendSpanForFewFields(fields, message) {
    $("div").parent(defineFewFieldsSelector(fields)).prepend("<span class='errorSpan'>" + message + "</span>");
}

function applyErrorSpan(selector, message) {
    $("<span class='errorSpan'>" + message + "</span>").appendTo(selector);
}
