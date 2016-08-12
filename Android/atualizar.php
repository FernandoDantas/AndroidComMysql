<?php	
	$codigo = $_POST["codigo"];
	$nome = $_POST["nome"];
	$email = $_POST["email"];

	$conn = new mysqli("localhost", "root", "", "android");
	$sql = "UPDATE clientes SET nome = ?, email = ? WHERE id = ?";
	$stm = $conn->prepare($sql);
	$stm->bind_param("ssi", $nome, $email, $codigo);
	
	if ($stm->execute()){
		$retorno = array("retorno" => "YES");
	}else{
		$retorno = array("retorno" => "NO");
	}

	echo json_encode($retorno);
	     
	$stm->close();
	$conn->close();
?>