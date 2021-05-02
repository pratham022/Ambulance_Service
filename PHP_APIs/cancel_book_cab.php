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
$getRideStatus = "SELECT status FROM cab_ride WHERE cab_ride_id='$ride_id' and status=0";
$result = mysqli_query($con, $getRideStatus);
if ($result)
{
    $cancelCabQuery = "UPDATE cab_ride SET status=1 WHERE cab_ride_id='$ride_id'";
    $cancelCabQuery2 = "UPDATE cab SET active=0 WHERE cab_id='$cab_id'";
    $result2 = mysqli_query($con, $cancelCabQuery);
    $result3 = mysqli_query($con, $cancelCabQuery2);
    if ($result2 && $result3)
    {
        $response = array(
            "status" => "1",
            "data" => "Booking Cancelled"
        );
        die(json_encode($response));
    }
    else
    {
        $response = array(
            "status" => "0",
            "data" => "Unable to cancel ride 1"
        );
        die(json_encode($response));
    }
}
else
{
    $response = array(
        "status" => "0",
        "data" => "Unable to cancel ride 2"
    );
    die(json_encode($response));
} ?>