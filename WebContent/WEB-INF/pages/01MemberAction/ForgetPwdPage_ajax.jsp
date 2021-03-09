<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<h4 class="mt-3 text-center">我們已經發送了重設密碼的郵件至 ${email}！</h4>
<a class="btn btn-primary btn-lg" href='<c:url value="/welcome/login" />' role="button">回到登入畫面</a>

