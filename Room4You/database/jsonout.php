<?php
$con=mysqli_connect("student.cs.hioa.no","s306631","","s306631");

$sql=(" SELECT * FROM Rooms WHERE BuildingID = (SELECT BuildingID FROM Buildings where BuildingID = 1)");
$table=mysqli_query($con,$sql);
while ($row=mysqli_fetch_assoc($table))
{
$output[]=$row;
}
print(json_encode($output));
mysqli_close($con);
?>