
var date = new Date();
var weekday = new Array(7);
weekday[0] = "Chủ Nhật";
weekday[1] = "Thứ Hai";
weekday[2] = "Thứ Ba";
weekday[3] = "Thứ Tư";
weekday[4] = "Thứ Năm";
weekday[5] = "Thứ Sáu";
weekday[6] = "Thứ Bảy";

var dayOfWeek = weekday[date.getDay()];

var monthNames = [
    "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
    "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8",
    "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
];

var day = date.getDate();
var monthIndex = date.getMonth();
var year = date.getFullYear();

document.getElementById("dateTime").innerHTML = dayOfWeek + ', ' + day + ' ' + monthNames[monthIndex] + ' ' + year;

