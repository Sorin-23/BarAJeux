import { HttpInterceptorFn } from '@angular/common/http';

export const jwtHeaderInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');

  // If no token, just let the request pass through
  if (!token) {
    return next(req);
  }

  // Detect auth/login calls so we DON'T attach the token to them
  const isAuthCall =
    req.url.endsWith('/auth') || req.url.endsWith('/api/auth');

  if (isAuthCall) {
    return next(req);
  }

  // Only attach token to calls that actually go to your API
  const isApiCall =
    req.url.startsWith('/api') ||
    req.url.startsWith('http://localhost:8080/api') ||
    req.url.startsWith('https://localhost:8080/api');

  if (!isApiCall) {
    return next(req);
  }

  const authReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`,
    },
  });

  return next(authReq);
};
