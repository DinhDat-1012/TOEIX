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
//======================================================
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
            window.location.href = `http://localhost:8080/Course/${course.courseCode}`;
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
//=====================================================================================================
//Chức năng đăng xuất(logOUt function)
function logOut(){
    localStorage.removeItem("authToken");
    localStorage.removeItem("userName");

    window.location.href = "http://localhost:8080/staff";
}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
        "2025-01-01": 2, // Học vừa
        "2025-12-08": 3, // Học cao
        "2025-12-15": 1, // Học nhẹ
        "2025-12-20": 4, // Học rất cao
        "2025-12-25": 2,  // Học vừa
        "2025-12-01": 2, // Học vừa
        "2025-12-02": 3, // Học cao
        "2025-12-18": 1, // Học nhẹ
        "2025-12-19": 4, // Học rất cao
        "2025-12-17": 2
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

const quotes = [
    {
        text: "Cuộc sống không phải là chờ cơn bão đi qua, mà là học cách nhảy múa dưới cơn mưa và bước tiếp dù hoàn cảnh có khắc nghiệt đến đâu.",
        author: "Vivian Greene"
    },
    {
        text: "Thành công thường đến với những người quá bận rộn để tìm kiếm nó, vì họ tập trung vào việc làm đúng mỗi ngày.",
        author: "Henry David Thoreau"
    },
    {
        text: "Đừng so sánh chương một của cuộc đời mình với chương hai mươi của người khác, vì mỗi hành trình đều có nhịp độ riêng.",
        author: "Unknown"
    },
    {
        text: "Không quan trọng bạn đi chậm đến mức nào, miễn là bạn không dừng lại và bỏ cuộc giữa chừng.",
        author: "Khổng Tử"
    },
    {
        text: "Những gì bạn làm hôm nay có thể cải thiện tất cả những ngày mai, vì vậy hãy hành động với mục tiêu rõ ràng.",
        author: "Ralph Marston"
    },
    {
        text: "Sự khác biệt giữa người thành công và người thất bại không nằm ở sức mạnh hay trí thông minh, mà nằm ở sự kiên trì.",
        author: "Napoleon Hill"
    },
    {
        text: "Nếu bạn không sẵn sàng mạo hiểm những điều bình thường, bạn sẽ phải chấp nhận một cuộc sống tầm thường.",
        author: "Jim Rohn"
    },
    {
        text: "Cuộc đời không yêu cầu bạn phải hoàn hảo, nhưng nó đòi hỏi bạn phải chân thành và nỗ lực hết mình.",
        author: "Unknown"
    },
    {
        text: "Thất bại chỉ đơn giản là cơ hội để bắt đầu lại, lần này thông minh hơn và mạnh mẽ hơn trước.",
        author: "Henry Ford"
    },
    {
        text: "Bạn không thể thay đổi hướng gió, nhưng bạn có thể điều chỉnh cánh buồm để đến được nơi mình muốn.",
        author: "Aristotle"
    },
    {
        text: "Những giới hạn duy nhất tồn tại trong cuộc sống là những giới hạn do chính bạn đặt ra trong suy nghĩ của mình.",
        author: "Tony Robbins"
    },
    {
        text: "Mỗi ngày bạn không bỏ cuộc là một ngày bạn tiến gần hơn đến phiên bản tốt hơn của chính mình.",
        author: "Unknown"
    },
    {
        text: "Đừng để nỗi sợ thất bại ngăn cản bạn thử, vì hối tiếc còn nặng nề hơn rất nhiều.",
        author: "Unknown"
    },
    {
        text: "Sự kỷ luật là cầu nối giữa mục tiêu bạn đặt ra và thành quả bạn đạt được trong tương lai.",
        author: "Jim Rohn"
    },
    {
        text: "Cuộc sống sẽ thử thách bạn, nhưng chính cách bạn phản ứng mới quyết định bạn sẽ trở thành ai.",
        author: "Viktor Frankl"
    },
    {
        text: "Không ai có thể quay lại để bắt đầu lại từ đầu, nhưng ai cũng có thể bắt đầu ngay bây giờ để tạo ra một kết thúc khác.",
        author: "Carl Bard"
    },
    {
        text: "Thành công không phải là việc bạn leo cao đến đâu, mà là bạn đã vượt qua bao nhiêu lần muốn bỏ cuộc.",
        author: "Booker T. Washington"
    },
    {
        text: "Những người đi xa nhất không phải là người thông minh nhất, mà là người không dừng lại khi mọi thứ trở nên khó khăn.",
        author: "Unknown"
    },
    {
        text: "Hãy làm việc trong im lặng, để thành công của bạn tự lên tiếng thay cho tất cả.",
        author: "Frank Ocean"
    },
    {
        text: "Cuộc sống trở nên ý nghĩa hơn khi bạn theo đuổi điều đúng đắn, chứ không chỉ là điều dễ dàng.",
        author: "Martin Luther King Jr."
    }
];

function displayDailyQuote() {
    const randomIndex = Math.floor(Math.random() * quotes.length);
    document.querySelector('.quote-content').innerText = `"${quotes[randomIndex].text}"`;
    document.querySelector('.quote-author').innerText = `— ${quotes[randomIndex].author}`;
}

window.onload = displayDailyQuote;

// Nơi thực hiện xử lý khóa học người dùng trong tab persional

async function loadCourseManager() {
    const token = localStorage.getItem("authToken");
    const username = localStorage.getItem("userName");
    const managerContainer = document.getElementById("courseManagerList");

    if (!token || !username) {
        managerContainer.innerHTML = "<p>Vui lòng đăng nhập để xem khóa học.</p>";
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/users/api/v1/my-course", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "token": token,
                "username": username
            },
            body: JSON.stringify({}) // Body trống theo thiết kế API của bạn
        });

        if (!response.ok) throw new Error("Không thể tải danh sách khóa học");

        const courses = await response.json();

        if (courses.length === 0) {
            managerContainer.innerHTML = "<p>Bạn chưa đăng ký khóa học nào.</p>";
            return;
        }

        managerContainer.innerHTML = ""; // Xóa dòng chữ đang tải

        courses.forEach(course => {
            const courseItem = document.createElement("div");
            courseItem.className = "course-item";

            // Giả sử DTO trả về: course_names, courseCode, purchaseDate
            courseItem.innerHTML = `
                <div class="course-info">
                    <h4><span class="course-code-tag">${course.courseCode}</span> ${course.course_names}</h4>
                    <p><i class="far fa-calendar-alt"></i> Ngày đăng ký: ${new Date(course.purchaseDate).toLocaleDateString('vi-VN')}</p>
                </div>
                <button class="delete-course-btn" onclick="deleteMyCourse('${course.courseCode}')">
                    <i class="fas fa-trash-alt"></i> Xóa
                </button>
            `;
            managerContainer.appendChild(courseItem);
        });

    } catch (error) {
        console.error("Lỗi:", error);
        managerContainer.innerHTML = "<p>Có lỗi xảy ra khi tải dữ liệu.</p>";
    }
}

// Hàm xóa khóa học (Placeholder cho API sau này)
async function deleteMyCourse(courseCode) {
    if (confirm(`Bạn có chắc muốn xóa khóa học ${courseCode} khỏi danh sách?`)) {
        console.log("Đang gọi API xóa cho mã:", courseCode);

        // Ví dụ logic sau này:
        // const res = await fetch(`.../api/v1/delete-course`, { method: 'DELETE', headers: ... })

        alert("Tính năng xóa đang được cài đặt API. Mã khóa học: " + courseCode);

        // Sau khi xóa thành công thì load lại danh sách
        // loadCourseManager();
    }
}

// Khởi chạy khi trang web tải xong
document.addEventListener("DOMContentLoaded", loadCourseManager);