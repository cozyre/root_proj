<?php
require_once __DIR__ . '/../config/database.php';
require_once __DIR__ . '/../middleware/AuthMiddleware.php';
require_once __DIR__ . '/../controllers/AuthController.php';

require_once __DIR__ . '/../models/GroupModel.php';
require_once __DIR__ . '/../controllers/GroupController.php';

require_once __DIR__ . '/../models/AccountModel.php';
require_once __DIR__ . '/../controllers/AccountController.php';

require_once __DIR__ . '/../models/DevotionModel.php';
require_once __DIR__ . '/../controllers/DevotionController.php';

require_once __DIR__ . '/../models/ItineraryModel.php';
require_once __DIR__ . '/../controllers/ItineraryController.php';

require_once __DIR__ . '/../models/SongModel.php';
require_once __DIR__ . '/../controllers/SongController.php';

require_once __DIR__ . '/../models/JournalModel.php';
require_once __DIR__ . '/../controllers/JournalController.php';

require_once __DIR__ . '/../models/GroupImageModel.php';
require_once __DIR__ . '/../controllers/GroupImageController.php';

require_once __DIR__ . '/../models/MemberModel.php';
require_once __DIR__ . '/../controllers/MemberController.php';

require_once __DIR__ . '/../models/ProfileModel.php';
require_once __DIR__ . '/../controllers/ProfileController.php';

require_once __DIR__ . '/../models/AdminModel.php';
require_once __DIR__ . '/../controllers/AdminController.php';

$db     = (new Database())->connect();
$method = $_SERVER['REQUEST_METHOD'];
$route  = $_GET['route'] ?? '';
$body   = fn() => json_decode(file_get_contents('php://input'), true) ?? [];

$auth     = new AuthMiddleware();
$authCtrl = new AuthController($db);

$groupCtrl     = new GroupController(new GroupModel($db));
$accountCtrl   = new AccountController(new AccountModel($db), $auth);
$devotionCtrl  = new DevotionController(new DevotionModel($db), $auth);
$itineraryCtrl = new ItineraryController(new ItineraryModel($db), $auth);
$songCtrl      = new SongController(new SongModel($db), $auth);
$journalCtrl   = new JournalController(new JournalModel($db), $auth);
$galleryCtrl   = new GroupImageController(new GroupImageModel($db), $auth);
$memberCtrl    = new MemberController(new MemberModel($db), $auth);
$profileCtrl   = new ProfileController(new ProfileModel($db), $auth);
$adminCtrl = new AdminController(new AdminModel(($db)), new AccountModel($db),$auth);

match (true) {
    // Auth
    $method === 'POST' && $route === 'auth/register'
        => $authCtrl->register($body()),
    $method === 'POST' && $route === 'auth/login'
        => $authCtrl->login($body()),
        $method === 'POST' && $route === 'auth/refresh'
    => $authCtrl->refresh($auth->requireAuth()),

    // Groups
    $method === 'GET' && $route === 'group/index'
        => $groupCtrl->index(),
    $method === 'GET' && $route === 'group/show'
        => $groupCtrl->show($_GET),
    $method === 'GET' && $route === 'group/history'
        => $groupCtrl->history($_GET, $auth->requireAuth()),

    // Accounts
    $method === 'POST' && $route === 'account/order'
        => $accountCtrl->order(),
    $method === 'GET' && $route === 'account/status'
        => $accountCtrl->status(),
    $method === 'GET' && $route === 'account/groupDetail'
        => $accountCtrl->groupDetail(),

    // Devotions
    $method === 'GET' && $route === 'devotion/get'
        => $devotionCtrl->get(),
    $method === 'GET' && $route === 'devotion/getDates'
        => $devotionCtrl->getDates(),

    // Itinerary
    $method === 'GET' && $route === 'itinerary/getByDate'
        => $itineraryCtrl->getByDate(),
    $method === 'GET' && $route === 'itinerary/getDates'
        => $itineraryCtrl->getDates(),

    // Songs
    $method === 'GET' && $route === 'song/list'
        => $songCtrl->list(),
    $method === 'GET' && $route === 'song/listByDate'
        => $songCtrl->listByDate(),
    $method === 'GET' && $route === 'song/get'
        => $songCtrl->get(),
    $method === 'GET' && $route === 'song/search'
        => $songCtrl->search(),
    $method === 'GET' && $route === 'song/browse'
        => $songCtrl->browse(),

    // Journal
    $method === 'POST' && $route === 'journal/create'
        => $journalCtrl->create(),
    $method === 'GET'  && $route === 'journal/list'
        => $journalCtrl->list(),
    $method === 'POST' && $route === 'journal/update'
        => $journalCtrl->update(),
    $method === 'POST' && $route === 'journal/delete'
        => $journalCtrl->delete(),

    // Gallery
    $method === 'GET'  && $route === 'gallery/list'
        => $galleryCtrl->list(),
    $method === 'POST' && $route === 'gallery/upload'
        => $galleryCtrl->upload(),

    // Members
    $method === 'GET' && $route === 'member/list'
        => $memberCtrl->list(),
    $method === 'GET' && $route === 'member/detail'
        => $memberCtrl->detail(),

    // Profile
    $method === 'GET'  && $route === 'profile/get'
        => $profileCtrl->get(),
    $method === 'POST' && $route === 'profile/update'
        => $profileCtrl->update(),

    // Admin
    $method === 'GET'  && $route === 'admin/trips'
        => $adminCtrl->trips(),
    $method === 'POST' && $route === 'admin/trip/create'
        => $adminCtrl->tripCreate(),
    $method === 'POST' && $route === 'admin/trip/update'
        => $adminCtrl->tripUpdate(),
    $method === 'POST' && $route === 'admin/trip/delete'
        => $adminCtrl->tripDelete(),
    $method === 'POST' && $route === 'admin/order/approve'
        => $adminCtrl->orderApprove(),
    $method === 'POST' && $route === 'admin/order/reject'
        => $adminCtrl->orderReject(),
    $method === 'POST' && $route === 'admin/member/remove'
        => $adminCtrl->memberRemove(),
    $method === 'POST' && $route === 'admin/gallery/removeImage'
        => $adminCtrl->galleryRemoveImage(),
    $method === 'POST' && $route === 'admin/song/create'
        => $adminCtrl->songCreate(),
    $method === 'POST' && $route === 'admin/song/addToGroup'
        => $adminCtrl->songAddToGroup(),
    $method === 'POST' && $route === 'admin/devotion/create'
        => $adminCtrl->devotionCreate(),
    $method === 'POST' && $route === 'admin/devotion/update'
        => $adminCtrl->devotionUpdate(),
    $method === 'GET' && $route === 'admin/accounts/pending'
        => $adminCtrl->pendingAccounts(),


    default => (function () use ($route, $method) {
        http_response_code(404);
        echo json_encode([
            'success' => false,
            'data'    => null,
            'message' => "Route [{$method}] {$route} not found",
        ]);
    })()
};