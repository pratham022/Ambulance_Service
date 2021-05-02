<?php require ('constants.php');

$conn = mysqli_connect($server_name, $user_name, $password, $db_name);

$cab_no = $_POST['cab_id'];
$cab_lat = $_POST['lat'];
$cab_lng = $_POST['lng'];

if(!$conn) {
	$response = array(
		"status" => "0",
		"data" => "Error connecting to database"
	);
	die(json_encode($response));
}else{
    $updateCabLocationQuery = "UPDATE cab SET cab_lat='$cab_lat', cab_lng='$cab_lng' WHERE cab_id='$cab_no'";
    $result2 = mysqli_query($conn, $updateCabLocationQuery);

    if ($result2)
    {
        $response = array(
            "status" => "1",
            "data" => "Location updated"
        );
        die(json_encode($response));
    }
    else
    {
        $response = array(
            "status" => "0",
            "data" => "Unable to update location"
        );
        die(json_encode($response));
    }


}


?>