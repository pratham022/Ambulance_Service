<?php require("constants.php");
$conn = mysqli_connect($server_name, $user_name, $password, $db_name);

$name = $_POST["name"];
$license_no=$_POST["license_no"];
$expiry_date=$_POST["expiry_date"];
$phone = $_POST["phone"];
$password = $_POST["password"];
$address = $_POST["address"];

if(!$conn) {
	$response = array(
		"status" => "0",
		"data" => "Error connecting to database"
	);
	die(json_encode($response));
}
else {
	$already_exists = "SELECT * FROM driver WHERE license_no='$license_no' OR phone='$phone'";
	$result = mysqli_query($conn, $already_exists);
	if(mysqli_num_rows($result) > 0) {
		$response = array(
			"status" => "0",
			"data" => "Email or Phone already exists"
		);
		die(json_encode($response));
	}
	else {
		$register_user_query = "INSERT INTO driver(name,phone,password,address,license_no,expiry_date,working) VALUES ('$name','$phone', '$password','$address','$license_no','$expiry_date',1);";
		$result = mysqli_query($conn, $register_user_query);

	if ($result) {
			$response = array(
				"status" => "1",
				"data" => "Registered Successfully"
			);
		}
		else {
			$response = array(
				"status" => "0",
				"data" => "Not able to register"

			);
		}
		die(json_encode($response));

	}
}
?>