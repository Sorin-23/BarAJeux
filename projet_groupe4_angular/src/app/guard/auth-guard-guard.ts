import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../service/auth-service';

export const authGuardGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  if (authService.token) {
    // Ici, si on a un token, c'est qu'on est connecté
    return true;
  }

  // Navigation à utiliser dans les guard
  return router.createUrlTree([ '/login' ]);
};
