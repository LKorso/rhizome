function showErrors(errors) {
    errors.forEach(handleValidation);
}

function cleanValidation() {
    $(".error-message-span").remove();
    $("input").removeClass("wrong-input");
}

function handleValidation(error) {
    if (error.fields.length === 1) {
        applyErrorSpan("." + error.fields[0], error.message);
    } else {
        appendSpanForFewFields(error.fields, error.message);
    }
    higlightInputs(error.fields);
}

function applyErrorSpan(selector, message) {
    $("<span class='error-message-span'>" + message + "</span>").appendTo(selector);
}

function appendSpanForFewFields(fields, message) {
    $(defineFewFieldsSelector(fields)).append("<span class='error-message-span'>" + message + "</span>");
}

function higlightInputs(fields) {
    fields.forEach(function (field) {
        $("." + field).children("input").addClass("wrong-input");
    });
}

function defineFewFieldsSelector(fields) {
    var selector = ".fewFieldsArea:has(";
    fields.forEach(function (field) {
        selector += "." + field + ", ";
    });
    return selector.substr(0, selector.length - 2) + ")";
}
