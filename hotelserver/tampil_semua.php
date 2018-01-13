<?php

	require_once 'koneksi.php';

	$sql = "SELECT * FROM tb_hotel";

	$result = array();
	$r = mysqli_query($con,$sql);

	while ($row = mysqli_fetch_array($r)) {

		array_push($result, array(
				"id" => $row['id'],
				"nama_hotel" => $row['nama_hotel'],
        			"harga" => $row['harga'],
				"foto" => $row['foto']
			));

	}

	echo json_encode(array('result' => $result));
	mysqli_close($con);

?>
