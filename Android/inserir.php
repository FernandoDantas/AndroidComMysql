<?php
	
	@$nome = $_GET["nome"];
	@$email = $_GET["email"];

	$conn = new mysqli("localhost", "root", "", "android");
	$sql = "INSERT INTO clientes (nome,email) VALUES (?, ?)";
	$stm = $conn->prepare($sql);
	$stm->bind_param("ss", $nome, $email);
	
	$stm->execute();
	$stm->close();
	$conn->close();
?>