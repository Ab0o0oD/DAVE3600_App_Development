<?php
$con=mysqli_connect('student.cs.hioa.no','s306631','','s306631');

$in1=$_REQUEST['roomid'];
$roomid=(int)$in1;

$in2=$_REQUEST['name'];
$name=(String)$in2;

$in3=$_REQUEST['fromtime'];
$fromtime=(String)$in3;

$in4=$_REQUEST['totime'];
$totime=(String)$in4;

$in5=$_REQUEST['date'];
$date=(String)$in5;

$sql=mysqli_query($con,"INSERT INTO Bookings (RoomID, BookerName, FromTime, ToTime, Date) values ('$roomid', '$name', '$fromtime', '$totime', '$date')");
mysqli_close($con);
?>