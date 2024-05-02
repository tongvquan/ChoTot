
document.getElementById("rePassword").addEventListener("input", function() {
    var password = document.getElementById("password").value;
    var rePassword = document.getElementById("rePassword").value;
    var registerButton = document.getElementById("registerButton");

    var passwordMismatch = document.getElementById("passwordMismatch");

    if (password !== rePassword) {
        passwordMismatch.style.display = "block";
        registerButton.disabled = true; // Disable button if passwords do not match
    } else {
        passwordMismatch.style.display = "none";
        registerButton.disabled = false; // Enable button if passwords match
    }
});


// var myPhoneRegex = /(?:(?:\+?1\s*(?:[.-]\s*)?)?(?:(\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]‌​)\s*)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\s*(?:[.-]\s*)?)([2-9]1[02-9]‌​|[2-9][02-9]1|[2-9][02-9]{2})\s*(?:[.-]\s*)?([0-9]{4})\s*(?:\s*(?:#|x\.?|ext\.?|extension)\s*(\d+)\s*)?$/i;
// var phoneNumber = document.getElementById("num").value;
// if (!myPhoneRegex.test(phoneNumber)) {
//     alert("phone k hop le");
//     return
// }
