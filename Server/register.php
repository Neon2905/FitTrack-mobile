<?php
// require 'db.php';

$username = $_POST['username'] ?? '';
$password = $_POST['password'] ?? '';

error_log("username: {$username} password: {$password}");

// if (!$username || !$password) {
//     echo json_encode(['status' => 'error', 'message' => 'Email and password required']);
//     exit;
// }

// $hashed = password_hash($password, PASSWORD_DEFAULT);

// $stmt = $conn->prepare("INSERT INTO users (email, password) VALUES (?, ?)");
// $stmt->bind_param("ss", $email, $hashed);

// if ($stmt->execute()) {
//     echo json_encode(['status' => 'success', 'message' => 'User registered']);
// } else {
//     echo json_encode(['status' => 'error', 'message' => 'Registration failed']);
// }

echo json_encode(['success' => true, 'message' => 'User registered']);
?>