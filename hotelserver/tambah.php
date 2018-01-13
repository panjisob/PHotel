<?php

	if ($_SERVER['REQUEST_METHOD'] == 'POST') {

		$name = $_POST['nama'];
		$email = $_POST['email'];
		$id_hotel = $_POST['id'];
		$jmkm = $_POST['jmkm'];

		$sql = "INSERT INTO pesan (nama,email,id_hotel, jumlah_kamar) VALUES ('$name','$email','$id_hotel','$jmkm')";

		require_once('koneksi.php');

		if (mysqli_query($con,$sql)) {
			echo "Berhasil ditambah";
		}else{
			echo mysqli_error();
		}

		mysqli_close($con);
	}
?>
