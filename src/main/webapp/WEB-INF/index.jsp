<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<h1>Welcome</h1>
	<p>Join our growing community</p>
	<form:form action="/register" method="post" modelAttribute="newUser">
		<h3>Register</h3>
		<div>
			<form:label path="userName">User Name</form:label>
			<form:errors path="userName"></form:errors>
			<form:input path="userName"></form:input>		
		</div>
		<div>
			<form:label path="email">Email</form:label>
			<form:errors path="email"></form:errors>
			<form:input type="email" path="email"></form:input>		
		</div>
		<div>
			<form:label path="password">Password</form:label>
			<form:errors path="password"></form:errors>
			<form:input path="password" type="password"></form:input>		
		</div>
		<div>
			<form:label path="confirm">Password</form:label>
			<form:errors path="confirm"></form:errors>
			<form:input path="confirm" type="password"></form:input>		
		</div>		
		<input type="submit" value="Create"/>
	</form:form >
	<div>
		<h1>Log in</h1>
		<form:form action="/login" method="post" modelAttribute="newLogin">
			<div>
				<form:label path="email">Email</form:label>
				<form:errors path="email"></form:errors>
				<form:input path="email" type="email"></form:input>
			</div>
			<div>
				<form:label path="password">Password</form:label>
				<form:errors path="password"></form:errors>
				<form:input path="password" type="password"></form:input>
			</div>
			<input type="submit" value="Login"/>
		</form:form>
	</div>
</body>
</html>