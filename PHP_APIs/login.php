<?php require ('constants.php');

// $phone = "7030584432";
// $pass = "pict@123";
// $type = "customer";

// $phone = "9898987867";
// $pass = "kamlesh@123";
// $type = "driver";

$phone = $_POST["phone"];
$pass = $_POST["pass"];
$type = $_POST["type"];



$conn = mysqli_connect($server_name, $user_name, $password, $db_name);

if (!$conn) {
  $response = array(
  		"status" => "0",
  		"data" => "Error connecting to the database!"
  );
  die(json_encode($response));
}
else {
	if($type == "customer") {
		$login_query = "SELECT * FROM customer WHERE phone='$phone' AND password='$pass'";	
	}
	else {
		$login_query = "SELECT * FROM driver WHERE phone='$phone' AND password='$pass'";
	}
	
	$result = mysqli_query($conn, $login_query);
	if(mysqli_num_rows($result) > 0) {
		$r = mysqli_fetch_assoc($result);
		if($type == "customer") {
			$response = array(
				"status" => "1",
				"data" => array(
					"id" => $r["cust_id"],
					"name" => $r["name"],
					"email" => $r["email"],
					"phone" => $r["phone"]
				)
			);
		}
		else {
			$response = array(
				"status" => "1",
				"data" => array(
					"id" => $r["driver_id"],
					"first_name" => $r["first_name"],
					"last_name" => $r["last_name"],
					"phone" => $r["phone"]
				)
			);
		}
		die(json_encode($response));
	}
	else {
		$response = array(
			"status" => "0",
			"data" => "Invalid phone or password!"
		);
		die(json_encode($response));
	}
}
echo "Connected successfully";
?>