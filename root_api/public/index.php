<?php
include_once '../config/database.php';
$db = new Database;
echo $db -> connect();