package api_gateway.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RouteValidator {

    private static final List<String> PUBLIC_ROUTES = Arrays.asList(
            "/api/auth/login",
            "/api/auth/forgot-password",
            "/api/auth/reset-password",
            "/api/auth/dev/generate-hash",
            "/actuator/health",
            "/swagger-ui",
            "/v3/api-docs"
    );

    public boolean isSecured(HttpServletRequest request) {
        String path = request.getRequestURI();
        return PUBLIC_ROUTES.stream()
                .noneMatch(path::startsWith);
    }
}
