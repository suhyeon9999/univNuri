<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>알림</title>
</head>



<body>


    <script type="text/javascript">
        window.onload = function() {
            alert("${message}");
            setTimeout(function() {
                window.location.href = "${redirectUrl}";
            }, 1000); // 1초 후 리다이렉트
        }
    </script>

</body>
</html>