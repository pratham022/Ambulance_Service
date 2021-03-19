<?php
require ('constants.php');

/*$db_name="copy_database";
$mysql_username="root";
$mysql_password="";
$server_name="localhost";*/

$conn=mysqli_connect($server_name,$user_name,$password,$db_name);

$email =$_POST["email"];;

// $id2=(int)$cust_id;

if (!$conn) {
  $response = array(
    "status"=>"0",
    "data"=>"Connection Failed",
  );
die(json_encode($response));
  }




$sql = "SELECT * FROM customer  WHERE email like '$email'";

$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    // output data of each row
    $row = mysqli_fetch_assoc($result) ;

      $response = array(
        "status"=>"0",
        "data"=>array(
          "id"=>$row['cust_id'],
          "name"=>$row['name'],
          "email"=>$row['email'],
          "phone"=>$row['phone'],
          "address"=>$row['address'],
        ) 
    );
    die(json_encode($response));
    
  } else {
    $response = array(
        "status"=>"0",
        "data"=>"Information not fetched",
    );
    die(json_encode($response));
  }

  mysqli_close($conn);

?>
