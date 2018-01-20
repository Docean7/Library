$('#contact-form').submit(function (e) {

    e.preventDefault();
    var pass = $('#password');
    var confirm = $('#confirm_password');

    if (pass.val() !== confirm.val()) {
        confirm.addClass('alert-danger');
        return;
    }
    else {
        var userData = {
            "first_name": $('#name').val(),
            "last_name": $('#last').val(),
            "user_name": $('#login').val(),
            "user_password": pass.val(),
            "email": $('#email').val(),
            "contact_no": $('#tel').val(),
            "command": "register"
        };
        $.ajax({
            url: "/controller",
            method: "POST",
            data: userData,
            success: function () {
                document.location.href = "/jsp/login.jsp"
            },
            error: function () {
                document.location.href = "/jsp/register.jsp"
            }

        });
    }
});
