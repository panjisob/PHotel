<?php

	require_once 'koneksi.php';

	$sql = "SELECT * FROM pesan";

	$result = array();
	$r = mysqli_query($con,$sql);

	while ($row = mysqli_fetch_array($r)) {

		array_push($result, array(
			"id"  => $row['id'],
			"nama_pemesan" => $row['nama'],
  			"email" => $row['email']
			));

	}

	echo json_encode(array('result' => $result));
	mysqli_close($con);

?>
