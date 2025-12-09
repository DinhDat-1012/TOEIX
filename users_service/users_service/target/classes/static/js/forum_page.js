// Khi trang load xong
document.addEventListener("DOMContentLoaded", () => {
    const categoryItems = document.querySelectorAll(".category_item");

    // Gán sự kiện click cho từng ô danh mục
    categoryItems.forEach(item => {
        item.addEventListener("click", () => {
            // Bỏ lớp 'active' của các ô khác
            categoryItems.forEach(i => i.classList.remove("active"));
            // Thêm lớp 'active' cho ô đang được nhấn
            item.classList.add("active");
        });
    });
});
