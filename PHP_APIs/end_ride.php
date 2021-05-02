<?php require ('constants.php');
$ride_id = $_POST['ride_id'];
$cab_id = $_POST['cab_id'];
$con = mysqli_connect($server_name, $user_name, $password, $db_name);
if (!$con)
{
    $response = array(
        "status" => "0",
        "data" => "Error Connecting to Database!"
    );
    die(json_encode($response));
}
$endRideQuery = "UPDATE cab_ride SET status=2 WHERE cab_ride_id='$ride_id'";
$endRideQuery2 = "UPDATE cab SET active=0 WHERE cab_id='$cab_id'";
$result2 = mysqli_query($con, $endRideQuery);
$result3 = mysqli_query($con, $endRideQuery2);
if ($result2 && $result3)
{
    $response = array(
        "status" => "1",
        "data" => "End of Ride!"
    );
    die(json_encode($response));
}
else
{
    $response = array(
        "status" => "0",
        "data" => "Unable to end ride"
    );
    die(json_encode($response));
} ?>