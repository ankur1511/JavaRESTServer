<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Crunchify - Upload Multiple Files Example</title>
<style type="text/css">
body {
	background-image:
		url('https://cdn.crunchify.com/wp-content/uploads/2013/03/Crunchify.bg_.300.png');
}
</style>
</head>
<body>
	<br>
	<br>
	<div align="center">

		<h1>Crunchify - Spring MVC Upload Multiple Files Example</h1>
		<p>Awesome.. Following files are uploaded successfully.</p>
		<ol>
			<c:forEach items="${files}" var="file">
           - ${file} <br>
			</c:forEach>
		</ol>
		<a href="http://localhost:8080/CrunchifySpringMVC3.2.1/upload.html"><input
			type="button" value="Go Back" /></a> <br /> <br /> <br />

	</div>
</body>
</html>