import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../service/auth-service';

export const roleGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  const expectedRole = route.data['role'] as string; 
  const userRole = authService.role;

  if (authService.token && userRole === expectedRole) {
    return true; // accès autorisé
  }

  // sinon, rediriger vers la page login ou home
  return router.createUrlTree(['/login']);
};
