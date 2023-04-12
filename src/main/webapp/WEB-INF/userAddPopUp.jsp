<%--
  Created by IntelliJ IDEA.
  User: KIMSEYOUNG
  Date: 2023-04-12
  Time: 오후 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="common/base.jsp"></jsp:include>

<html>
<head>
</head>
<body>
  <div style="padding: 40px;">
    <div class="input-group mb-3">
      <span class="input-group-text" id="email">이메일</span>
      <input type="text" class="form-control" placeholder="Email" aria-label="Email" aria-describedby="email" />
    </div>
      <div class="input-group">
        <span class="input-group-text" id="password">Password</span>
        <input type="password" class="form-control" placeholder="비밀번호" aria-label="Password" aria-describedby="password" />
        <span class="input-group-text" id="company">업체 명</span>
        <input type="text" class="form-control" placeholder="업체 명" aria-label="업체 명" aria-describedby="company" />
      </div>
      <div style="display: flex; flex-direction: row; margin-top:20px" class="column-gap-3 p-2">
        <div class="form-check form-switch">
          <input class="form-check-input p-2 g-col-6" type="checkbox" role="switch" id="PSuperUser" />
          <label class="form-check-label p-2 g-col-6" for="PSuperUser">SCARF-P 슈퍼유저</label>
        </div>
        <div class="form-check form-switch">
          <input class="form-check-input p-2 g-col-6" type="checkbox" role="switch" id="PUser" />
          <label class="form-check-label p-2 g-col-6" for="PUser">SCARF-P 일반유저</label>
        </div>
        <div class="form-check form-switch">
          <input class="form-check-input p-2 g-col-6" type="checkbox" role="switch" id="RUser" />
          <label class="form-check-label p-2 g-col-6" for="RUser">SCARF-R 일반유저</label>
        </div>
      </div>
    <div style="margin-top:50px;">
      <button type="button" class="btn btn-primary button_w_200" onclick="onClickRegButton()">등록</button>
      <button type="button" class="btn btn-danger button_w_200" onclick="onClickCloseButton()">취소</button>
    </div>
  </div>
</body>
<script>

  const onClickCloseButton = () => {
    window.close();
  }

  const onClickRegButton = () => {
    if(confirm("등록하시겠습니까?") === true){
      alert("등록되었습니다. Fake");
      window.close();
    }
  }

</script>
</html>
