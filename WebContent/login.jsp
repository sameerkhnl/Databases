<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<style type="text/css">
#login {
	position: relative;
	top: 80px;
}

.align-right {
	text-align: right;
}

table {
	border: 1px solid gray;
	padding: 20px;
	background-color: #CCCCFF;
}

.login-error {
	font-size: 16px;
	font-weight: bold;
	font-color: red;
}
</style>
</head>
<body>

	<center>
		<div id="login">
			<h3>Log In</h3>
			<form method="post"
				action="<%=response.encodeUrl(request.getContextPath() + "/Connect?action=dologin")%>">
				<input type="hidden" name="action" value="dologin">

				<table>
					<tr>
						<td class="align-right">Email:</td>
						<td><input type="text" name="email"
							value="<%=request.getAttribute("email") == null? "" : request.getAttribute("email")%>" /></td>
					</tr>
					<tr>
						<td class="align-right">Password:</td>
						<td><input type="password" name="password"
							value="<%=request.getAttribute("password")==null? "" : request.getAttribute("password") %>" /></td>
					</tr>
					<tr>
						<td class="align-right" colspan="2"><input type="submit"
							value="Log in"></td>
					</tr>

					
				</table>
				<p class="login-error"><%=request.getAttribute("message")==null? "" : request.getAttribute("message")%></p>
			</form>
		</div>
	</center>

</body>
</html>