<?php

	$id = $_GET['id'];

	require_once 'koneksi.php';

	$sql = "SELECT * FROM tb_hotel WHERE id = '$id'";

	$result = array();
	$r = mysqli_query($con,$sql);

	while ($row = mysqli_fetch_array($r)) {

		array_push($result, array(
				"id" => $row['id'],
				"nama_hotel" => $row['nama_hotel'],
                                "harga" => $row['harga'],
                                "foto" => $row['foto'],
																"alamat" => $row['alamat'],
																"foto1" => $row['foto1'],
																"foto2" => $row['foto2'],
																"foto3" => $row['foto3'],
																"foto4" => $row['foto4'],
																"foto5" => $row['foto5']
			));

	}

	echo json_encode(array('result' => $result));
	mysqli_close($con);

?>
