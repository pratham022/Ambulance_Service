<?php require ('constants.php');
$conn = mysqli_connect($server_name, $user_name, $password, $db_name);
$user_lat = $_POST['user_lat'];
$user_lng = $_POST['user_lng'];
if(!$conn) {
	$response = array(
		"status" => "0",
		"data" => "Error connecting to database"
	);
	die(json_encode($response));
}else{

    $getNearbyCabs = "SELECT cab_id,cab_lat,cab_lng,driver.first_name,driver.phone from cab ,driver where driver.driver_id=cab.driver_id ORDER BY ((cab_lat-$user_lat)*(cab_lat-$user_lat) + (cab_lng-$user_lng)*(cab_lng-$user_lng)) LIMIT 1";

    $result = mysqli_query($conn, $getNearbyCabs);
        if ($result)
        {
            $r = mysqli_fetch_assoc($result);
            $response = array(
                "status" => "1",
                "data" => array(
                    "cab_lat" => $r['cab_lat'],
                    "cab_lng" => $r['cab_lng'],
                    "cab_id" => $r['cab_id'],
                    "name"=>$r['first_name'],
                    "phone"=>$r['phone'],
                )
            );
            echo (json_encode($response));
        } else{
            $response = array(
                "status" => "0",
                "data" => "Unable to get cabs",
            );
            die(json_encode($response));
        }
}


?>