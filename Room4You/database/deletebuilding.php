<?php
$con=mysqli_connect('student.cs.hioa.no','s306631','','s306631');

$in1=$_REQUEST['buildingId'];
$buildingId=(int)$in1;

$sqlBooking = "DELETE FROM Bookings WHERE BuildingID = (SELECT BuildingID FROM Buildings WHERE BuildingID = '$buildingId')";
$sqlRoom = "DELETE FROM Rooms WHERE BuildingID = (SELECT BuildingID FROM Buildings WHERE BuildingID = '$buildingId')";
$sqlBuilding="DELETE FROM Buildings WHERE BuildingID = '$buildingId'";

if ($con->query($sqlBooking)=== TRUE) {
	echo "Bookings Deleted Successfully";
} else {
	echo "Error deleting Bookings: " . $con->error;
}

if ($con->query($sqlRoom)=== TRUE) {
	echo "Rooms Deleted Successfully";
} else {
	echo "Error deleting Rooms: " . $con->error;
}

if ($con->query($sqlBuilding)=== TRUE) {
	echo "Building Deleted Successfully";
} else {
	echo "Error deleting Building: " . $con->error;
}

$con->close();
?>