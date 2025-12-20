// Quản lý hiển thị forms
const FormManager = {
    forms: {
        login: null,
        register: null,
        resetPassword: null
    },

    init() {
        this.forms.login = document.querySelector(".login_form_wrapper");
        this.forms.register = document.querySelector(".register_form_wrapper");
        this.forms.resetPassword = document.querySelector(".reset_pw_form_wrapper");
    },

    hideAll() {
        Object.values(this.forms).forEach(form => {
            if (form) form.style.display = "none";
        });
    },

    show(formType) {
        this.hideAll();
        if (this.forms[formType]) {
            this.forms[formType].style.display = "block";
        }
    },

    toggle(formType) {
        if (this.forms[formType]) {
            const isVisible = this.forms[formType].style.display === "block";
            this.hideAll();
            if (!isVisible) {
                this.forms[formType].style.display = "block";
            }
        }
    }
};

// Validation helpers
const Validator = {
    isValidEmail(email) {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    },

    isValidPassword(password) {
        return password && password.length >= 6;
    },

    passwordsMatch(password, confirmPassword) {
        return password === confirmPassword;
    }
};

// API Service
const AuthService = {
    async register(userData) {
        const response = await fetch("http://localhost:8080/users/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(userData),
        });

        const result = await response.json();

        if (!response.ok) {
            throw new Error(result.message || "Đăng ký thất bại!");
        }

        return result;
    },

    async login(credentials) {
        const response = await fetch("http://localhost:8000/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(credentials),
        });

        const result = await response.json();

        if (!response.ok) {
            throw new Error(result.message || "Đăng nhập thất bại!");
        }

        return result;
    }
};

// Storage Manager (sử dụng state thay vì localStorage)
const StorageManager = {
    setToken(token) {
        localStorage.setItem("authToken", token);
    },

    setUserName(userName) {
        localStorage.setItem("userName", userName);
    },

    getToken() {
        return localStorage.getItem("authToken");
    },

    getUserName() {
        return localStorage.getItem("userName");
    },

    clear() {
        localStorage.removeItem("authToken");
        localStorage.removeItem("userName");
    }
};

// Form handlers
function openLoginform() {
    FormManager.toggle('login');
}

function openRegisterform() {
    FormManager.toggle('register');
}

function openLoginformToSignIn() {
    FormManager.show('login');
}

function openRegisterFormToCreateAccount() {
    FormManager.show('register');
}

function openResetPasswordFormToCreateAccount() {
    FormManager.show('resetPassword');
}

// Xử lý đăng ký
async function handleRegister(event) {
    event.preventDefault();

    const username = document.getElementById("register_user_name").value.trim();
    const password = document.getElementById("register_password").value;
    const passwordRepeat = document.getElementById("register_password_repeat").value;
    const email = document.getElementById("register_email").value.trim();

    // Validation
    if (!Validator.isValidEmail(email)) {
        alert("Địa chỉ email không hợp lệ!");
        return;
    }

    if (!Validator.isValidPassword(password)) {
        alert("Mật khẩu phải có ít nhất 6 ký tự!");
        return;
    }

    if (!Validator.passwordsMatch(password, passwordRepeat)) {
        alert("Mật khẩu nhập lại không khớp!");
        return;
    }

    const requestData = {
        username,
        password,
        email,
        fullName: "anonymous",
        gender: true,
        birthday: "2000-01-01",
        address: "anonymous",
        role: "USER"
    };

    try {
        await AuthService.register(requestData);
        alert("Đăng ký thành công! Vui lòng đăng nhập.");

        // Reset form
        document.getElementById("registerForm").reset();

        // Chuyển sang form đăng nhập
        FormManager.show('login');
    } catch (error) {
        console.error("Lỗi đăng ký:", error);
        alert(error.message || "Không thể kết nối đến máy chủ!");
    }
}

// Xử lý đăng nhập
async function handleLogin(event) {
    event.preventDefault();

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value;

    if (!username || !password) {
        alert("Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    const requestData = { username, password };

    try {
        const result = await AuthService.login(requestData);

        // Lưu token và username
        alert(result.token);
        alert(username)

        StorageManager.setToken(result.token);
        StorageManager.setUserName(username);

        alert("Đăng nhập thành công!");

        // Ẩn form
        FormManager.hideAll();

        // Chuyển hướng
        window.location.href = 'http://localhost:8080/personal';
    } catch (error) {
        console.error("Lỗi đăng nhập:", error);
        alert(error.message || "Không thể kết nối đến máy chủ!");
    }
}

// Xử lý click bên ngoài form để đóng
function handleOutsideClick(event) {
    const loginForm = FormManager.forms.login;
    const registerForm = FormManager.forms.register;
    const loginButton = document.querySelector(".login_button");
    const registerButton = document.querySelector(".register_button");
    const openLoginFormLink = document.querySelector(".openLoginForm");
    const openRegisterFormLink = document.querySelector(".openRegisterForm");

    // Đóng login form nếu click bên ngoài
    if (loginForm && loginForm.style.display === "block") {
        if (!loginForm.contains(event.target) &&
            !loginButton?.contains(event.target) &&
            !openLoginFormLink?.contains(event.target)) {
            loginForm.style.display = "none";
        }
    }

    // Đóng register form nếu click bên ngoài
    if (registerForm && registerForm.style.display === "block") {
        if (!registerForm.contains(event.target) &&
            !registerButton?.contains(event.target) &&
            !openRegisterFormLink?.contains(event.target)) {
            registerForm.style.display = "none";
        }
    }
}

// Slide banner
let slideIndex = 0;
function showSlides() {
    const slides = document.getElementsByClassName("slides");

    if (slides.length === 0) return;

    // Ẩn tất cả slides
    Array.from(slides).forEach(slide => {
        slide.style.display = "none";
        slide.classList.remove("fade");
    });

    // Hiển thị slide tiếp theo
    slideIndex = (slideIndex + 1) % slides.length;
    slides[slideIndex].style.display = "block";
    slides[slideIndex].classList.add("fade");

    setTimeout(showSlides, 6000);
}

// Khởi tạo khi DOM loaded
document.addEventListener("DOMContentLoaded", function () {
    // Khởi tạo Form Manager
    FormManager.init();

    // Gắn sự kiện đăng ký
    const registerForm = document.getElementById("registerForm");
    if (registerForm) {
        registerForm.addEventListener("submit", handleRegister);
    }

    // Gắn sự kiện đăng nhập
    const loginForm = document.getElementById("login_form");
    if (loginForm) {
        loginForm.addEventListener("submit", handleLogin);
    }

    // Xử lý click bên ngoài
    document.addEventListener("click", handleOutsideClick);

    // Khởi động slideshow
    showSlides();
});
function reset_password() {

}

async function get_OTP() {
    const btn = document.querySelector(".get_otp_button");
    const emailInput = document.querySelector("#_user_email");
    const email = emailInput.value.trim();

    if (!email) {
        alert("Vui lòng nhập email trước khi nhận OTP!");
        return;
    }

    btn.disabled = true;
    btn.textContent = "Đang gửi...";

    try {
        // Gửi email qua query string, bỏ body
        const response = await fetch(
            "http://localhost:8000/auth/send-otp?email=" + encodeURIComponent(email),
            { method: "GET" } // chỉ cần GET, bỏ body
        );

        if (!response.ok) {
            throw new Error("Gửi OTP thất bại");
        }

        const result = await response.json();

        btn.textContent = "Đã gửi mã OTP, gửi lại?";
        btn.disabled = false;

        console.log("OTP sent:", result);
    } catch (error) {
        console.error(error);
        btn.textContent = "Nhận mã OTP.";
        btn.disabled = false;
        alert("Không thể gửi OTP. Vui lòng thử lại.");
    }
}

