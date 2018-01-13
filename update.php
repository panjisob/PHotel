<?php  

	if ($_SERVER['REQUEST_METHOD'] == 'POST') {
		$id = $_POST['id'];
		$nama = $_POST['nama'];
		$email = $_POST['email'];
		$jumk = $_POST['jumk'];

		$sql = "UPDATE pesan SET nama = '$nama',email = '$email', jumlah_kamar = '$jumk' WHERE id = '$id';";

		require_once('koneksi.php');

		if (mysqli_query($con,$sql)) {
			echo "Berhasil";
		}else{
			echo mysqli_error();
		}

		mysqli_close($con);
	}
?>
