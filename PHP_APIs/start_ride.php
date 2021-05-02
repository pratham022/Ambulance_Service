<?php require ('constants.php');
$conn = mysqli_connect($server_name, $user_name, $password, $db_name);
$ride_id = $_POST['ride_id'];
if(!$conn) {
	$response = array(
		"status" => "0",
		"data" => "Error connecting to database"
	);
	die(json_encode($response));
}else{
    $startRideQuery = "UPDATE cab_ride SET status=0 WHERE cab_ride_id='$ride_id'";

    $result2 = mysqli_query($conn, $startRideQuery);
    if ($result2)
    {
        $response = array(
            "status" => "1",
            "data" => "Ride started!"
        );
        die(json_encode($response));
    }
    else
    {
        $response = array(
            "status" => "0",
            "data" => "Unable to start ride"
        );
        die(json_encode($response));
    }

}


?>