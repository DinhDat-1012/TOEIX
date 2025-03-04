//Các hàm thực hiện thao tác với form đăng nhập và đăng ký tài khoản.===================================================================================================
//loginform

function openLoginform() {
    let loginForm = document.querySelector(".login_form_wrapper");
    let login_button_dplay = loginForm.style.display;

    if (login_button_dplay == "none") {
        loginForm.style.display = "block";
    } else {
        loginForm.style.display = "none";
    }
}
function signInButton_Click(){
    //thực hiện thao tác kiểm tra dữ liệu đầu vào.
    //Các thao tác cần thiết khi đăng nhập.

}

function openLoginformToSignIn(){
    let regis_form = document.querySelector(".register_form_wrapper");
    let login_form = document.querySelector(".login_form_wrapper");

    regis_form.style.display = "none";
    login_form.style.display = "block";
}
document.addEventListener("click", function (event) {
    var loginForm = document.querySelector(".login_form_wrapper");
    var loginButton = document.querySelector(".login_button");
    var registerTextInRegisterForm = document.querySelector(".openLoginForm")
    if (!loginForm.contains(event.target) && !loginButton.contains(event.target)&&!registerTextInRegisterForm.contains(event.target)) {
        loginForm.style.display = "none";
    }
});
//register form_________________________________________________________________________________________________________________________________________________________
function openRegisterform(){
    let regis_form = document.querySelector(".register_form_wrapper");

    if (regis_form.style.display == "none") {
        regis_form.style.display = "block";
    } else {
        regis_form.style.display = "none";
    }
}


function openRegisterFormToCreateAccount() {


    let regis_form = document.querySelector(".register_form_wrapper");
    let login_form = document.querySelector(".login_form_wrapper");

    regis_form.style.display = "block";
    login_form.style.display = "none";
}

//Thực hiện thao tác đăng ký tài khoản================================
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("registerForm").addEventListener("submit", async function (event) {
        event.preventDefault();

        const username = document.getElementById("register_user_name").value;
        const password = document.getElementById("register_password").value;
        const password_repeat = document.getElementById("register_password_repeat").value;
        const email = document.getElementById("register_email").value;
        //kiểm tra định dạng email
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if(regex.test(email)){
            if(password == password_repeat){

                const gender = true;
                const fullName = "anonymous";
                const role = "USER";
                const address = "anonymous";
                const birthday = "2000-01-01"

                const requestData = {
                    username,
                    password,
                    email,
                    fullName,
                    gender,
                    birthday,
                    address,
                    role
                };
                alert(requestData.email+"|"+requestData.username);
                try {
                    const response = await fetch("http://localhost:8080/users/register", {
                        method: "Post",
                        headers: {
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify(requestData),
                    });

                    const result = await response.json();

                    if (response.ok) {
                        alert("Đăng ký thành công!  đăng nhập...");
                        let regis_form = document.querySelector(".register_form_wrapper");
                        let login_form = document.querySelector(".login_form_wrapper");

                        regis_form.style.display = "none";
                        login_form.style.display = "block";

                        document.getElementById("register_user_name").value = "";
                        document.getElementById("register_password").value = "";
                        document.getElementById("register_password_repeat").value = "";
                        document.getElementById("register_email").value ="";
                    } else {
                        alert("Đăng ký thất bại: " + (result.message || "Có lỗi xảy ra!"));
                    }
                } catch (error) {
                    console.error("Lỗi:", error);
                    alert("Không thể kết nối đến máy chủ!");
                }
            }else{

                alert("Nhập lại mật khẩu không chính xác!");

            }}else{
            alert("địa chỉ email không tồn tại!")
        }
    });

});



function signUpButtonClick(){
    //Thao tác kiểm tra dữ liệu đầu vào
    //Thao tác đăng nhập.
}

document.addEventListener("click", function (event) {
    var regis_form = document.querySelector(".register_form_wrapper");
    var regis_btn  = document.querySelector(".register_button");
    var signUpTextInLogInform = document.querySelector(".openRegisterForm");

    if (!regis_form.contains(event.target) && !regis_btn.contains(event.target)) {
        if(!signUpTextInLogInform.contains(event.target)){
            regis_form.style.display = "none";
        }
    }
});

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("login_form").addEventListener("submit", async function (event) {
        event.preventDefault();
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        const requestData = {
            username,
            password
        };
        alert(requestData.username +"|"+requestData.username);
        try {
            const response = await fetch("http://localhost:8000/auth/login", {
                method: "Post",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(requestData),
            });

            const result = await response.json();

            if (response.ok) {
                let login_form = document.querySelector(".login_form_wrapper");
                localStorage.setItem("authToken", result.token);//lưu token vào bộ nhớ.
                alert("Đăng nhập thành công, token: "+ result.token);

                alert("token đã được lưu vào bô nhớ: " + localStorage.getItem("authToken"));//lấy token từ bộ nhớ.

                login_form.style.display = "none";
            } else {
                alert("Đăng nhập thất bại: " + (result.message || "Có lỗi xảy ra!"));
            }
        } catch (error) {
            console.error("Lỗi:", error);
            alert("Không thể kết nối đến máy chủ!");
        }
    });

});




//Các hàm thực hiện thao tác với giao diện người dùng:==================================================================================================================
//Hiển thị slide banner.

let slideIndex = 0;
function showSlides() {
    let slides = document.getElementsByClassName("slides");
    for (let i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slideIndex++;
    if (slideIndex > slides.length) { slideIndex = 1; }
    slides[slideIndex - 1].style.display = "block";
    slides[slideIndex - 1].classList.add("fade");
    setTimeout(showSlides, 6000);
}
document.addEventListener("DOMContentLoaded", showSlides);