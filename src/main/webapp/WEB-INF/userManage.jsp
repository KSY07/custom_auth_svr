<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<jsp:include page="common/base.jsp"></jsp:include>
<jsp:include page="common/header.jsp"></jsp:include>

<head>
    <link rel="stylesheet" type="text/css" media="screen" href="resource/css/ui.jqgrid.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="resource/jquery-ui.min.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="resource/css/common/common.css" />
    <script src="resource/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="resource/js/i18n/grid.locale-kr.js" type="text/javascript"></script>
    <script src="resource/js/jquery.jqGrid.min.js" type="text/javascript"></script>
</head>
<body>
<div class="global_container">
    <h1>유저 목록</h1>
    <div class="grid full-height full-height-strict row_container">
        <table id="grid" class="full-size-jq-grid"></table>
    </div>
    <div class="row_container">
        <button class="btn btn-primary button_w_200" onclick="onClickUserReg()">유저 등록</button>
        <button class="btn btn-danger button_w_200">유저 삭제</button>
    </div>
</div>
</body>
<script>
    let isPopUpOpen = false;

    $(document).ready(() => {
        $("#home").removeClass("active");
        $("#userManage").addClass("active");
    });

    const onClickUserReg = () => {
        window.open("/userAddPopUp", "유저 등록", "width=700px, height=400px, scrollbars=no, resizable=no");
        isPopUpOpen = true;
    }

    $("#grid").jqGrid({
        url:"/userList.json",
        datatype: "json",
        mtype: "GET",
        colNames:["유저 명", "계정(이메일)", "업체 명"],
        colModel: [
            {name:"username", index:"username", width:300},
            {name:"email", index:"email", width:500},
            {name:"company", index:"company", width:300},
        ],
        rowNum: 10,
        rowList: [10,20,30],
        height: 600,
        scroll: true,
        jsonReader: {
            repeatitems:false
        }
    });
</script>
