<?php
require_once __DIR__ . '/../config/database.php';
require_once __DIR__ . '/../middleware/AuthMiddleware.php';
require_once __DIR__ . '/../controllers/AuthController.php';
require_once __DIR__ . '/../models/GroupModel.php';
require_once __DIR__ . '/../controllers/GroupController.php';
require_once __DIR__ . '/../models/DevotionModel.php';
require_once __DIR__ . '/../controllers/DevotionController.php';
require_once __DIR__ . '/../models/ItineraryModel.php';
require_once __DIR__ . '/../controllers/ItineraryController.php';
require_once __DIR__ . '/../models/SongModel.php';
require_once __DIR__ . '/../controllers/SongController.php';

$db     = (new Database())->connect();
$method = $_SERVER['REQUEST_METHOD'];

// Use query param instead of path
$route  = $_GET['route'] ?? '';
$body   = fn() => json_decode(file_get_contents('php://input'), true) ?? [];

$auth     = new AuthMiddleware();
$authCtrl = new AuthController($db);

$groupModel = new GroupModel($db);
$groupCtrl  = new GroupController($groupModel);

$accountModel = new AccountModel($db);
$accountCtrl  = new AccountController($accountModel, $auth);

$devotionModel = new DevotionModel($db);
$devotionCtrl  = new DevotionController($devotionModel, $auth);

$itineraryModel = new ItineraryModel($db);
$itineraryCtrl  = new ItineraryController($itineraryModel, $auth);

$songModel = new SongModel($db);
$songCtrl  = new SongController($songModel, $auth);

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

    $method === 'GET' && $route === 'devotion/get'
        => $devotionCtrl->get(),

    $method === 'GET' && $route === 'devotion/getDates'
        => $devotionCtrl->getDates(),

    $method === 'GET' && $route === 'itinerary/getByDate'
        => $itineraryCtrl->getByDate(),

    $method === 'GET' && $route === 'itinerary/getDates'
        => $itineraryCtrl->getDates(),

    $method === 'GET' && $route === 'song/list'
        => $songCtrl->list(),

    $method === 'GET' && 'song/listByDate'
        => $songCtrl->listByDate(),

    $method === 'GET' && 'song/get'
        => $songCtrl->get(),

    $method === 'GET' && 'song/search'
        => $songCtrl->search(),
    
    $method === 'GET' && 'song/browse'
        => $songCtrl->browse(),

    default => (function () use ($route, $method) {
        http_response_code(404);
        echo json_encode([
            'success' => false,
            'data'    => null,
            'message' => "Route [{$method}] {$route} not found",
        ]);
    })()
};