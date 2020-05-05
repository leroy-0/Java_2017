<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registration</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.2.3/foundation.min.css" />
    <link rel="stylesheet" href="./assets/css/login.css" />
</head>
<body>
<div class="row expanded header">
    <h2>Welcome to AREA interface</h2>
</div>
<div class="container">
    <div class="small-centered small-12 large-5 columns">
        <form class="login-form" method="post" action="registration.jsp">
            <label>First name<input type="text" name="fname" required/></label>
            <label>Last name<input type="text" name="lname" required/></label>
            <label>Email<input type="text" name="email" required/></label>
            <label>User Name<input type="text" name="uname" required/></label>
            <label>Password<input type="password" name="pass" required/></label>
            <input class="button" type="submit" value="Submit" />
            <p>Already registered ? <a href="index.jsp">Login here</a></p>
        </form>
    </div>
</div>
</body>
</html>