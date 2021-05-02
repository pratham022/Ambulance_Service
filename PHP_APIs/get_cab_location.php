<?php require ('constants.php');
$cab_id = $_POST['cab_id'];
$conn = mysqli_connect($server_name, $user_name, $password, $db_name);
if(!$conn) {
	$response = array(
		"status" => "0",
		"data" => "Error connecting to database"
	);
	die(json_encode($response));
}
else{

    $getCurrentPosition = "SELECT cab_lat,cab_lng from cab where cab_id='$cab_id'";

    $result = mysqli_query($conn, $getCurrentPosition);

    if($result)
    {
        $r2 = mysqli_fetch_assoc($result);

        $response=array(
            "status" => "1",
            "data" => array(
                    "cab_lat" => $r2['cab_lat'],
                    "cab_lng" => $r2['cab_lng'],
            ),
        );
        die(json_encode($response));
    }
    else
    {
        $response = array(
            "status" => "2",
            "data" => "Unable to fetch location",
        );
        die(json_encode($response));
    }

}



?>