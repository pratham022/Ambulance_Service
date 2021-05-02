<?php require ('constants.php');
$con = mysqli_connect($server_name, $user_name, $password, $db_name);
if (!$con)
{
    $response = array(
        "status" => "0",
        "data" => "Error Connecting to Database!"
    );
    die(json_encode($response));
}

$cust_id = $_POST['cust_id'];

$getCurrentRide = "SELECT cab_ride_id, cab_id, src_lat, src_long, dest_lat, dest_long, price from cab_ride WHERE cust_id='$cust_id' and status=0 LIMIT 1";
$result = mysqli_query($con, $getCurrentRide);
if (mysqli_num_rows($result) > 0)
{
    $r = mysqli_fetch_assoc($result);
    $response = array(
        "status" => "1",
        "data" => array(
            "src_lat" => $r['src_lat'],
            "src_lng" => $r['src_long'],
            "dest_lat" => $r['dest_lat'],
            "dest_lng" => $r['dest_long'],
            "fare" => $r['price'],
            "ride_id" => $r['cab_ride_id'],
            "cab_id" => $r['cab_id']
        )
    );
    die(json_encode($response));
}
else
{
    $response = array(
        "status" => "0",
        "data" => "No active booking"
    );
    die(json_encode($response));
} ?>