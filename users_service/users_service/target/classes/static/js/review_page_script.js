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
//Lấy danh sách khóa học của người dùng
async function fetchCourse() {
    const token = localStorage.getItem("authToken");
    const username = localStorage.getItem("userName");
    try {
        const response = await fetch('http://localhost:8080/users/api/v1/my-course', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "username": username,
                "token": token
            },
            body: JSON.stringify({}) // Nếu API yêu cầu dữ liệu trong body
        });

        if (!response.ok) {
            throw new Error('Failed to fetch user course');
        }

        const course = await response.json();
        displayUserCourse(course);
    } catch (error) {
        console.error('Error fetching user course:', error);
    }
}
function displayUserCourse(course) {
    const container = document.getElementById('user-course-container');
    container.innerHTML = '';
    course.forEach(course => {
        const courseElement = document.createElement('div');
        courseElement.classList.add('user-course');
        courseElement.innerHTML = `
            <div style="display: flex; padding: 15px">
            <h4 style="color: black">${" "+ course.courseCode}</h4>
            <p style="color: black">${" "+ course.course_names}</p>
            </div>
            <small style="color: black;padding-left: 15px">${new Date(course.purchaseDate).toLocaleString()}</small> 
        `;
        courseElement.addEventListener("click", () => {
            window.location.href = `http://localhost:8080/Course/${course.course_code}`;
        });
        courseElement.addEventListener("mouseover", () => {
            courseElement.style.backgroundColor = "#d5dbdb"; });
        courseElement.addEventListener("mouseout", () => {
            courseElement.style.backgroundColor = "#fff"; });
        container.appendChild(courseElement);
    });
}
fetchCourse();
setInterval(fetchCourse, 3000);

const user_courses_title = document.getElementById("user_courses_title");
const container = document.getElementById('user-course-container');
document.addEventListener('click', (event) => {
    const isClicktagetTitle = user_courses_title.contains(event.target);
    const isClickTagetContainer = container.contains(event.target);
    if (isClicktagetTitle||isClickTagetContainer) {
        container.style.display = "block";
    }else{
        container.style.display = "none";
    }
})
//Chức năng đăng xuất
function logOut(){
    localStorage.removeItem("authToken");
    localStorage.removeItem("userName");

    window.location.href = "http://localhost:8080/staff";
}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
