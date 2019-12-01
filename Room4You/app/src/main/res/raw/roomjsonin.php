<?php
$con=mysqli_connect('student.cs.hioa.no','s306631','','s306631');

$in1=$_REQUEST['buildingId'];
$buildingId=(int)$in1;

$in2=$_REQUEST['name'];
$roomName=(String)$in2;

$in3=$_REQUEST['floor'];
$floor=(int)$in3;

$in4=$_REQUEST['coordinates'];
$coordinates=(String)$in4;

$sql=mysqli_query($con,"INSERT INTO Rooms (BuildingID, Name, Floor, Coordinates) values ('$buildingId', '$roomName', '$floor', '$coordinates')");
mysqli_close($con);
?>