// Hàm kiểm tra token và chuyển hướng
const checkTokenAndRedirect = () => {
    const jwtToken = localStorage.getItem('jwtToken');
    const username = localStorage.getItem('username');

    if (jwtToken && username) {
        // Tạo đối tượng yêu cầu xác thực
        const introspectRequest = {
            token: jwtToken
        };

        // Gửi yêu cầu API để kiểm tra token
        fetch('/auth/introspect', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(introspectRequest)
        })
            .then(response => response.json())
            .then(data => {
                if (data.code === 200 && data.result && data.result.valid === true) {
                    // Kiểm tra nếu username là "admin", chuyển đến /dashboard, nếu không chuyển đến /home
                    if (username === 'admin') {
                        window.location.href = '/dashboard';
                    } else {
                        window.location.href = '/home';
                    }
                } else {
                    // Token không hợp lệ, thực hiện hành động khác nếu cần
                    alert('Token không hợp lệ, vui lòng đăng nhập lại.');
                    localStorage.removeItem('jwtToken')
                }
            })
            .catch(error => {
                console.error('Lỗi kiểm tra token:', error);
            });
    } else {
    }
};

// Gọi hàm kiểm tra token và chuyển hướng khi tải trang
checkTokenAndRedirect();

// Xử lý sự kiện khi submit form đăng nhập
const loginForm = document.getElementById('loginForm');
loginForm.addEventListener('submit', (event) => {
    event.preventDefault(); // Ngăn chặn việc gửi yêu cầu mặc định

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Tạo đối tượng yêu cầu xác thực
    const authenticationRequest = {
        username,
        password
    };

    // Gửi yêu cầu API để gửi thông tin đăng nhập
    fetch('/auth/token', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(authenticationRequest)
    })
        .then(response => response.json())
        .then(data => {
            if (data.code === 200 && data.result) { // Kiểm tra mã phản hồi thành công (200) và sự hiện diện của kết quả
                // Lưu token JWT vào localStorage
                localStorage.setItem('jwtToken', data.result.token);
                localStorage.setItem('username', username);

                // Kiểm tra nếu username là "admin", chuyển đến /dashboard, nếu không chuyển đến /home
                if (username === 'admin') {
                    window.location.href = '/dashboard';
                } else {
                    window.location.href = '/home';
                }
            } else {
                alert('Đăng nhập thất bại! ' + data.message); // Hiển thị thông báo lỗi từ phản hồi
            }
        })
        .catch(error => {
            console.error('Lỗi đăng nhập:', error);
            alert('Đã xảy ra lỗi khi đăng nhập. Vui lòng thử lại sau.');
        });
});

