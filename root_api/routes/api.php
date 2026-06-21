<?php
require_once __DIR__ . '/../config/database.php';
require_once __DIR__ . '/../middleware/AuthMiddleware.php';
require_once __DIR__ . '/../controllers/AuthController.php';
require_once __DIR__ . '/../models/GroupModel.php';
require_once __DIR__ . '/../controllers/GroupController.php';

$db     = (new Database())->connect();
$method = $_SERVER['REQUEST_METHOD'];

// Use query param instead of path
$route  = $_GET['route'] ?? '';
$body   = fn() => json_decode(file_get_contents('php://input'), true) ?? [];

$auth     = new AuthMiddleware();
$authCtrl = new AuthController($db);

$groupModel = new GroupModel($db);
$groupCtrl  = new GroupController($groupModel);

$auth         = new AuthMiddleware();
$accountModel = new AccountModel($db);
$accountCtrl  = new AccountController($accountModel, $auth);

match (true) {
    $method === 'POST' && $route === 'auth/register'
        => $authCtrl->register($body()),

    $method === 'POST' && $route === 'auth/login'
        => $authCtrl->login($body()),

    $method === 'GET' && $route === 'group/index'
        => $groupCtrl->index(),

    $method === 'GET' && $route === 'group/show'
        => $groupCtrl->show($_GET),

    $method === 'GET' && $route === 'group/history'
        => $groupCtrl->history($_GET, $auth->requireAuth()),

    $method === 'POST' && $route === 'account/order'
        => $accountCtrl->order(),

    $method === 'GET' && $route === 'account/status'
        => $accountCtrl->status(),

    $method === 'GET' && $route === 'account/groupDetail'
        => $accountCtrl->groupDetail(),

    default => (function () use ($route, $method) {
        http_response_code(404);
        echo json_encode([
            'success' => false,
            'data'    => null,
            'message' => "Route [{$method}] {$route} not found",
        ]);
    })()
};