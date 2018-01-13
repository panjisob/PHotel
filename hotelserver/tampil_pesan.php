<?php
$id = $_GET['id'];

require_once 'koneksi.php';

$sql = "select pesan.nama , pesan.email, pesan.jumlah_kamar, tb_hotel.nama_hotel, tb_hotel.foto
        from pesan inner join tb_hotel on pesan.id_hotel = tb_hotel.id where pesan.id = '$id'";

        $result = array();
        $r = mysqli_query($con,$sql);

        while ($row = mysqli_fetch_array($r)) {

          array_push($result, array(
              "nama" => $row['nama'],
              "email" => $row['email'],
              "jumlah_kamar" => $row['jumlah_kamar'],
              "nama_hotel" => $row['nama_hotel'],
              "foto" => $row['foto']
            ));

        }

        echo json_encode(array('result' => $result));
        mysqli_close($con);

 ?>
