<?php
$con=mysqli_connect('student.cs.hioa.no','s306631','','s306631');

$in1=$_REQUEST['bName'];
$buildingName=(String)$in1;

$in2=$_REQUEST['floors'];
$floors=(String)$in2;

$in3=$_REQUEST['latLng'];
$coordinates=(String)$in3;

$sql=mysqli_query($con,"INSERT INTO Buildings (Name, Floors, Coordinates) values ('$buildingName', '$floors', '$coordinates')");
mysqli_close($con);
?>