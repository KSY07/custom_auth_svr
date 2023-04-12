<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<jsp:include page="base.jsp"></jsp:include>

<head>

</head>
<body>
    <ul class="nav nav-pills nav-fill">
        <li class="nav-item">
            <a
                    class="nav-link active"
                    id="home" aria-current="page" href="/"
                    onclick="onClickHomeTab()">Home</a>
        </li>
        <li class="nav-item">
            <a
                    class="nav-link"
                    href="/userManage" id="userManage"
                    onclick="onClickUserManageTab()">유저 관리</a>
        </li>
        <li class="nav-item">
            <a
                    class="nav-link"
                    href="#"
                    id="authManage"
                    onclick="onClickAuthManageTab()">권한 관리</a>
        </li>
        <li></li>
    </ul>
</body>
<script>

    const onClickHomeTab = () => {
        $("#home").addClass("active");
        $("#userManage").removeClass("active");
        $("#authManage").removeClass("active");
    }

    const onClickUserManageTab = () => {
        $("#home").removeClass("active");
        $("#userManage").addClass("active");
        $("#authManage").removeClass("active");
    }

    const onClickAuthManageTab = () => {
        $("#home").removeClass("active");
        $("#userManage").removeClass("active");
        $("#authManage").addClass("active");
    }

</script>