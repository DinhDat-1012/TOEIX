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

        document.getElementById("user-email").textContent = data.email || "N/A";
        document.getElementById("title-user-email").textContent = data.email || "N/A";

    } catch (error) {
        console.error("Error fetching user profile:", error);
    }
});
//++++++++++++++++++++++++Lấy thông báo
async function fetchNotifications() {
    const token = localStorage.getItem("authToken");
    const username = localStorage.getItem("userName");
    try {
        const response = await fetch('http://localhost:8080/users/api/v1/notification', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "username": username,
                "token": token
            },
            body: JSON.stringify({}) // Nếu API yêu cầu dữ liệu trong body
        });

        if (!response.ok) {
            throw new Error('Failed to fetch notifications');
        }

        const notifications = await response.json();
        displayNotifications(notifications);
    } catch (error) {
        console.error('Error fetching notifications:', error);
    }
}
function displayNotifications(notifications) {
    const container = document.getElementById('notification-container');
    container.innerHTML = '';
    notifications.forEach(notification => {
        const notificationElement = document.createElement('div');
        notificationElement.classList.add('notification');
        notificationElement.innerHTML = `
            <h4>${notification.title}</h4>
            <p>${notification.message}</p>
            <small>${new Date(notification.createdAt).toLocaleString()}</small>
            </div>
            
        `;

        if (notification.isRead) {
            notificationElement.classList.add('read');
        }

        container.appendChild(notificationElement);
    });
}
fetchNotifications();
setInterval(fetchNotifications, 3000);//update after 3 seconds
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
//heatmap=======================================
function renderCalendar() {
    const studyData = {
        "2025-03-01": 2, // Học vừa
        "2025-03-08": 3, // Học cao
        "2025-03-15": 1, // Học nhẹ
        "2025-03-20": 4, // Học rất cao
        "2025-03-25": 2  // Học vừa
    };

    const calendar = document.getElementById("calendar");
    calendar.innerHTML = "";

    const daysInMonth = 31; // Giả sử tháng có 31 ngày
    const today = new Date();
    const year = today.getFullYear();
    const month = today.getMonth() + 1; // Tháng trong JS bắt đầu từ 0

    document.getElementById("activity-heatmap-title").textContent = "Hoạt động tháng "+ month;


    for (let day = 1; day <= daysInMonth; day++) {
        const dateStr = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
        const activityLevel = studyData[dateStr]||0;

        const div = document.createElement("div");
        div.classList.add("day");
        div.textContent = day;

        // Thêm class màu theo mức độ học

        div.classList.add("level-" + activityLevel);


        calendar.appendChild(div);
    }
}
document.addEventListener("DOMContentLoaded", renderCalendar);
