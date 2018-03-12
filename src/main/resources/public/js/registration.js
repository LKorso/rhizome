$("document").ready(function () {
    $("#register").click(onClick);
});

function onClick() {
    cleanValidation();
    register();
}

function register() {
    $.ajax({
        url: '/users',
        type: 'POST',
        headers: {
          "content-type": 'application/json'
        },
        data: prepareUserData(),
        success: function () {
            window.location = "../";
        },
        error: onError
    });
}

function onError(response) {
    if(response.status === 400) {
        $("#confirmPassword").val('');
        showErrors(response.responseJSON.errors);
    }
}

function prepareUserData() {
    return JSON.stringify({
        "email"     : $("#email").val(),
        "password"  : $("#password").val(),
        "roles": ["USER"]
    });
}
