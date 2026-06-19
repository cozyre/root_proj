<?php
require_once __DIR__ . '/../config/database.php';
require_once __DIR__ . '/../middleware/AuthMiddleware.php';
require_once __DIR__ . '/../controllers/AuthController.php';

$db     = (new Database())->connect();
$method = $_SERVER['REQUEST_METHOD'];

// Use query param instead of path
$route  = $_GET['route'] ?? '';
$body   = fn() => json_decode(file_get_contents('php://input'), true) ?? [];

$auth     = new AuthMiddleware();
$authCtrl = new AuthController($db);

match (true) {
    $method === 'POST' && $route === 'auth/register'
        => $authCtrl->register($body()),

    $method === 'POST' && $route === 'auth/login'
        => $authCtrl->login($body()),

    default => (function () use ($route, $method) {
        http_response_code(404);
        echo json_encode([
            'success' => false,
            'data'    => null,
            'message' => "Route [{$method}] {$route} not found",
        ]);
    })()
};