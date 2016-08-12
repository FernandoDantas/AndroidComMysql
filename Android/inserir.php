<?php	
	$nome = $_POST["nome"];
	$email = $_POST["email"];

	$conn = new mysqli("localhost", "root", "", "android");
	$sql = "INSERT INTO clientes (nome,email) VALUES (?, ?)";
	$stm = $conn->prepare($sql);
	$stm->bind_param("ss", $nome, $email);
	
	if ($stm->execute()){
		$retorno = array("retorno" => "YES");
	}else{
		$retorno = array("retorno" => "NO");
	}

	echo json_encode($retorno);
	     
	$stm->close();
	$conn->close();
?>