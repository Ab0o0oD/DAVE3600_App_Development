<?php
$con=mysqli_connect('student.cs.hioa.no','s306631','','s306631');

$in1=$_REQUEST['buildingId'];
$buildingId=(int)$in1;

$sql="DELETE FROM Buildings WHERE BuildingID = '$buildingId'";

if ($con->query($sql)=== TRUE) {
	echo "Building Deleted Successfully"; 
} else {
	echo "Error deleting Building: " . $con->error;
}

$con->close();
?>