//=====================================================================================================
//hàm khởi chạy nhận thông tin ngời dùng
document.addEventListener("DOMContentLoaded", async function () {
    // Lấy token và username từ localStorage
    const token = localStorage.getItem("authToken");
    const username = localStorage.getItem("userName");

    if (!token || !username) {
        window.location.href = "http://localhost:8080/staff";
    }
    try {
        const response = await fetch("http://localhost:8080/users/get-user-profile", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "token": token,
                "username": username
            },
            body: JSON.stringify({})
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        document.getElementById("fullName").textContent = data.username.toUpperCase();
        document.getElementById("username").textContent ="@"+data.username || "N/A";


    } catch (error) {
        console.error("Error fetching user profile:", error);
    }
});
//=====================================================================================================
function toggleMenu() {
    let menu = document.getElementById("profileMenu");
    menu.style.display = (menu.style.display === "block") ? "none" : "block";
}

// Đóng menu khi click ra ngoài
document.addEventListener("click", function (event) {
    let menu = document.getElementById("profileMenu");
    let button = document.querySelector(".user_ico");

    if (!button.contains(event.target) && !menu.contains(event.target)) {
        menu.style.display = "none";
    }
});

function toggleNotifications() {
    var list = document.getElementById("notification-list");
    if (list.style.display === "block") {
        list.style.display = "none";
    } else {
        list.style.display = "block";
    }
}

// Đóng danh sách nếu click bên ngoài
document.addEventListener("click", function (event) {
    var notification = document.querySelector(".noti_box");
    var list = document.getElementById("notification-list");

    if (!notification.contains(event.target) && !list.contains(event.target)) {
        list.style.display = "none";
    }
});
