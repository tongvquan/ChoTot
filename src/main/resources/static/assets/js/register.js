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