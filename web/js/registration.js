$('#submit').click(function (e) {

    e.preventDefault();

    var pass = $('#password');
    var confirm = $('#confirm_password');
    var fname = $('#name');
    var lname = $('#last');
    var login = $('#login');
    var email = $('#email');
    var tel = $('#tel');
    var correct = true;

    if(!checkLength(fname.val(), 2, 25)){
        fname.addClass('alert-danger');
        correct = false;
    }
    if(!checkLength(lname.val(), 3, 25)){
        lname.addClass('alert-danger');
        correct = false;
    }
    if(!checkLength(login.val(), 3, 25)){
        login.addClass('alert-danger');
        correct = false;
    }
    if(!checkLength(email.val(), 5, 32) || !validateEmail(email.val())){
        email.addClass('alert-danger');
        correct = false;
    }
    if(!checkLength(tel.val(), 7, 13) || !validateNumber(tel.val())){
        tel.addClass('alert-danger');
        correct = false;
    }

    if(!checkLength(pass.val(), 4, 20)){
        pass.addClass('alert-danger');
        correct = false;
    }
    if(!checkLength(confirm.val(), 4, 20)){
        confirm.addClass('alert-danger');
        correct = false;
    }
    if (pass.val() !== confirm.val()) {
        confirm.addClass('alert-danger');
        correct = false;
    }
    if(correct) {
        var userData = {
            "first_name": fname.val(),
            "last_name": lname.val(),
            "user_name": login.val(),
            "user_password": pass.val(),
            "email": email.val(),
            "contact_no": tel.val(),
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
                console.log("Error");
                document.location.href = "/jsp/register.jsp"
            }

        });
    }

});

function checkLength(arg, minLength, maxLength) {
    return arg.length >= minLength && arg.length <= maxLength;
}

function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function validateNumber(number) {
    var re = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/im;
    return re.test(number)
}