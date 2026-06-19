<?php
require_once __DIR__ . '/../config/database.php';
require_once __DIR__ . '/../middleware/AuthMiddleware.php';
require_once __DIR__ . '/../controllers/AuthController.php';

// --- Bootstrap ---
$db     = (new Database())->connect();
$method = $_SERVER['REQUEST_METHOD'];
$uri    = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);

// Strip base path prefix (e.g. /root_api/api or /api)
$path = preg_replace('#^.*/api(?=/|$)#', '', $uri);
$path = rtrim($path, '/') ?: '/';

// --- Shared instances ---
$auth       = new AuthMiddleware();
$authCtrl   = new AuthController($db);

// --- Body parser helper ---
$body = fn() => json_decode(file_get_contents('php://input'), true) ?? [];

// --- Router ---
match (true) {

    // Auth — public routes
    $method === 'POST' && $path === '/auth/register'
        => $authCtrl->register($body()),

    $method === 'POST' && $path === '/auth/login'
        => $authCtrl->login($body()),

    // --- Protected route example (uncomment when needed) ---
    // $method === 'GET' && $path === '/me'
    //     => $profileCtrl->me($auth->requireAuth()),

    // --- Preflight CORS ---
    $method === 'OPTIONS'
        => http_response_code(200),

    // --- 404 fallback ---
    default => (function () use ($path, $method) {
        http_response_code(404);
        echo json_encode([
            'success' => false,
            'data'    => null,
            'message' => "Route [{$method}] {$path} not found",
        ]);
    })()
};