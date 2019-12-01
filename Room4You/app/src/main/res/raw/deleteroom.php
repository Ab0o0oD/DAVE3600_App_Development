<?php
$con=mysqli_connect('student.cs.hioa.no','s306631','','s306631');

$in1=$_REQUEST['roomId'];
$roomId=(int)$in1;

$sqlBooking = "DELETE FROM Bookings WHERE RoomID = (SELECT RoomID FROM Rooms WHERE RoomID = '$roomId')";
$sqlRoom = "DELETE FROM Rooms WHERE RoomID = '$roomId'";

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

$con->close();
?>