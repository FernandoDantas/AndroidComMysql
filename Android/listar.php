<?php 

	$conn = new mysqli("localhost", "root", "", "android");
	$sql = "SELECT * FROM clientes";	
	$result = $conn->query($sql);

	$result_json = array();

	while($row = $result->fetch_assoc()){
		$result_json[] = $row;
	}

	echo json_encode($result_json);

	$conn->close();

?>