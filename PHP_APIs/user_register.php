<?php require("constants.php");
$conn = mysqli_connect($server_name, $user_name, $password, $db_name);

$phone = $_POST["phone"];
$name = $_POST["name"];
$pass = $_POST["pass"];

if(!$conn) {
	$response = array(
		"status" => "0",
		"data" => "Error connecting to database"
	);
	die(json_encode($response));
}
else {
	$already_exists = "SELECT * FROM customer WHERE phone='$phone'";
	$result = mysqli_query($conn, $already_exists);
	if(mysqli_num_rows($result) > 0) {
		$response = array(
			"status" => "0",
			"data" => "User already exists"
		);
		die(json_encode($response));
	}
	else {
		$register_user_query = "INSERT INTO customer(name, phone, password) VALUES ('$name', '$phone', '$pass')";
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
				"data" => "Unable to Register"
			);
		}
		die(json_encode($response));
	}
}
?>