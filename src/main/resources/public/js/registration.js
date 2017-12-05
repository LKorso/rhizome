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
        }
    });
}

function handleValidation(error) {
    if (error.fields.length === 1) {
        applyErrorSpan("." + error.fields[0], error.message);
    } else {
        appendSpanForFewFields(error.fields, error.message);
    }
    higlightInputs(error.fields);
}

function createRegistrationDto() {
    return {
        "email"     : $("#email").val(),
        "password"  : $("#password").val(),
        "confirmationPassword" : $("#confirmPassword").val()
    }
}

function cleanPage() {
    $(".error-message-span").remove();
    $("input").removeClass("wrong-input");
}

function defineFewFieldsSelector(fields) {
    var selector = ".fewFieldsArea:has(";
    fields.forEach(function (field) {
        selector += "." + field + ", ";
    });
    return selector.substr(0, selector.length - 2) + ")";
}

function appendSpanForFewFields(fields, message) {
    $(defineFewFieldsSelector(fields)).append("<span class='error-message-span'>" + message + "</span>");
}

function applyErrorSpan(selector, message) {
    $("<span class='error-message-span'>" + message + "</span>").appendTo(selector);
}

function higlightInputs(fields) {
    fields.forEach(function (field) {
        $("." + field).children("input").addClass("wrong-input");
    });
}
