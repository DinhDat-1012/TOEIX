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
