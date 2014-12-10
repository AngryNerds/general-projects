<?php
$type = $_GET['type'];
$where = $_GET['where'];
$what = $_GET['what'];

$contents = file_get_contents("edits.txt");
file_put_contents("edits.txt", "$contents\n$type$where:$what");
?>