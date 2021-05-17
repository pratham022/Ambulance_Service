<?php require ('constants.php');
$con = mysqli_connect($server_name, $user_name, $password, $db_name);

if(!$con) {
	$response = array(
		"status" => "0",
		"data" => "Error connecting to database"
	);
	die(json_encode($response));
}
else {
	$src_lat1 = $_POST['src_lat'];
	$src_lng1 = $_POST['src_lng'];
	$dest_lat1 = $_POST['dest_lat'];
	$dest_lng1 = $_POST['dest_lng'];
	$user_id1= $_POST['user_id'];
	$payment_id1 = $_POST['payment_id'];

	$src_lat=(float)($src_lat1);
	$src_lng=(float)($src_lng1);
	$dest_lat=(float)($dest_lat1);
	$dest_lng=(float)($dest_lng1);
	$user_id=intval($user_id1);
	$payment_id=intval($payment_id1);



	$getUserNamePhoneQuery = "SELECT name, phone FROM customer WHERE cust_id='$user_id'";
	$userNamePhone = mysqli_query($con, $getUserNamePhoneQuery);
	$customer = mysqli_fetch_assoc($userNamePhone);
	$customer_name = $customer['name'];
	$customer_phone = $customer['phone'];


	$getNearbyCabs = "SELECT driver_id, cab_id, license_plate, CAST(CAST(cab_lat AS float) AS decimal(10, 8)), CAST(CAST(cab_lng AS float) AS decimal(10, 8)), car_model_id FROM cab WHERE active=0 ORDER BY 
						((CAST(CAST(cab_lat AS float) AS decimal(10, 8))-$src_lat)*(CAST(CAST(cab_lat AS float) AS decimal(10, 8))-$src_lat) + (CAST(CAST(cab_lng AS float) AS decimal(10, 8))-$src_lng)*(CAST(CAST(cab_lng AS float) AS decimal(10, 8))-$src_lng)) LIMIT 1";

	$result = mysqli_query($con, $getNearbyCabs);
	if($result) {
		$r = mysqli_fetch_assoc($result);
	    $cab_id = $r['cab_id'];
	    $license_plate = $r['license_plate'];
	    $cab_model_id = $r['car_model_id'];
	    $driver_id = $r['driver_id'];

	    $getCabLatLng = "SELECT cab_lat, cab_lng from cab where cab_id='$cab_id'";
	    $result7 = mysqli_query($con, $getCabLatLng);
	    $r7 = mysqli_fetch_assoc($result7);

	    
	    $fare = sqrt(($dest_lat - $src_lat) * ($dest_lat - $src_lat) + ($dest_lng - $src_lng) * ($dest_lng - $src_lng)) * 111 * 20;
	    $fare = (int)$fare;
	    $booked_at = date('m/d/Y H:i:s' ,time());



	    // cab: active
	    // 0 => accepting rides
	    // 1 => currently riding so not accepting


	    // cab_ride: status
	    // 0 => ongoing (cab just booked)
	    // 1 => cab ride cancelled
	    // 2 => cab ride completed successfully

	    $book_ride_query = "INSERT INTO cab_ride (cust_id, src_lat, src_long, dest_lat, dest_long, price, status, payment_id, gps_start_time, cab_id) 
	    					values ('$user_id', '$src_lat1', '$src_lng1', '$dest_lat1', '$dest_lng1', '$fare', 0, '$payment_id', '$booked_at', '$cab_id')";

	    $change_on_trip_query = "UPDATE cab SET active=1 WHERE cab_id='$cab_id'";
	    $book_ride_result = mysqli_query($con, $book_ride_query);
	    $change_on_trip_result = mysqli_query($con, $change_on_trip_query);

	    $get_car_model_name = "SELECT model_name, model_description from car_model WHERE car_model_id='$cab_model_id'";
	    $get_car_model_name_result = mysqli_query($con, $get_car_model_name);

	    $get_driver_name = "SELECT first_name, last_name, phone from driver WHERE driver_id='$driver_id'";
	    $get_driver_name_result = mysqli_query($con, $get_driver_name);


	    if ($book_ride_result && $change_on_trip_result && $get_car_model_name_result)
	    {
	        $get_ride_id = "SELECT cab_ride_id FROM cab_ride WHERE cust_id='$user_id' and gps_start_time='$booked_at'";
	        $get_ride_id_result = mysqli_query($con, $get_ride_id);
	        if ($get_ride_id_result)
	        {
	            $r2 = mysqli_fetch_assoc($get_ride_id_result);
	            $r3 = mysqli_fetch_assoc($get_car_model_name_result);
	            $r4 = mysqli_fetch_assoc($get_driver_name_result);
	            $ride_id = $r2['cab_ride_id'];
	            $model_name = $r3['model_name'];
	            $model_description = $r3['model_description'];

	            $response = array(
	                "status" => "1",
	                "data" => array(
	                    "ride_id" => $ride_id,
	                    "cab_lat" => $r7['cab_lat'],
	                    "cab_lng" => $r7['cab_lng'],
	                    "cab_id" => $r['cab_id'],
	                    "driver_name" => $r4['first_name'] . " " .$r4['last_name'],
	                    "driver_phone" => $r4['phone'],
	                    "cab_no" => $r['license_plate'],
	                    "fare" => $fare,
	                    "model_name" => $model_name,
	                    "model_description" => $model_description,
						"src_lat" => $src_lat,
	            "src_lng" => $src_lng,
	            "dest_lat" => $dest_lat,
	            "dest_lng" => $dest_lng,
				"user_id"=>$user_id,
				"payment_id"=>$payment_id,

	                )
	            );
	            die(json_encode($response));
	        }
	        else
	        {
	            $response = array(
	                "status" => "0",
	                "data" => "Unable to book ride 1"
	            );
	            die(json_encode($response));
	        }
	    }
	    else
	    {
	        $response = array(
	            "status" => "0",
	            "data" => "Unable to book ride 2",
	           	"src_lat" => $src_lat,
	            "src_lng" => $src_lng,
	            "dest_lat" => $dest_lat,
	            "dest_lng" => $dest_lng,
				"user_id"=>$user_id,
				"payment_id"=>$payment_id,
				
	        );
	        die(json_encode($response));
	    }



	}
	else {
		$response = array(
	        "status" => "0",
	        "data" => "Unable to book ride 3"
	    );
	    die(json_encode($response));
	}

}

?>
