<?php
$con=mysqli_connect('student.cs.hioa.no','s306631','','s306631');

$inn=$_REQUEST['Name'];
$name=(String)$inn;

$sql=mysqli_query($con,"insert into Food (Name) values ('$name');");
mysqli_close($con);
?>