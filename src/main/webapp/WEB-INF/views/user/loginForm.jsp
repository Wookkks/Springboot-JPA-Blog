<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">
    <form action="/auth/loginProc" method="post">
        <div class="form-group">
            <label for="username">Username :</label>
            <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
        </div>
        <button type="submit" id="btn-login" class="btn btn-primary">로그인</button>
        <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=bba7d48765d3de2aac99eca775a3f294&redirect_uri=http://localhost:8000/auth/kakao/callback"><img src="/img/kakao_login_button.png" height="38px"></a>
    </form>
</div>
<%@ include file="../layout/footer.jsp"%>