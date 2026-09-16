<?php
class AuthMiddleware {
    private string $secret;

    public function __construct() {
        $this->secret = $_ENV['JWT_SECRET'] ?? 'CHANGE_THIS_SECRET_KEY_IN_PRODUCTION';
    }

    // Call this at the top of any protected route handler.
    // Returns the decoded payload array, or terminates with 401.
    public function requireAuth(): array {
        $headers = getallheaders();
        $authHeader = $headers['Authorization'] ?? $headers['authorization'] ?? '';

        if (!str_starts_with($authHeader, 'Bearer ')) {
            $this->abort(401, 'No token provided');
        }

        $token = substr($authHeader, 7);
        $payload = $this->verifyJWT($token);

        if (!$payload) {
            $this->abort(401, 'Invalid or expired token');
        }

        return $payload;
    }

    // Same as requireAuth but also checks role
    public function requireRole(string ...$roles): array {
        $payload = $this->requireAuth();

        if (!in_array($payload['role'], $roles, true)) {
            $this->abort(403, 'Forbidden: insufficient permissions');
        }

        return $payload;
    }

    private function verifyJWT(string $token): ?array {
        $parts = explode('.', $token);
        if (count($parts) !== 3) return null;

        [$header, $payload, $signature] = $parts;

        // Rebuild expected signature
        $expectedSig = base64_encode(
            hash_hmac('sha256', "{$header}.{$payload}", $this->secret, true)
        );
        $expectedSig = rtrim(strtr($expectedSig, '+/', '-_'), '=');

        // Constant-time comparison to prevent timing attacks
        if (!hash_equals($expectedSig, $signature)) return null;

        // Decode payload
        $decoded = json_decode(base64_decode(strtr($payload, '-_', '+/')), true);
        if (!$decoded) return null;

        // Check expiry
        if (isset($decoded['exp']) && $decoded['exp'] < time()) return null;

        return $decoded;
    }

    private function abort(int $code, string $message): never {
        http_response_code($code);
        echo json_encode(['success' => false, 'data' => null, 'message' => $message]);
        exit;
    }
}