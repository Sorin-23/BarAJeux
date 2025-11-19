import { HttpInterceptorFn } from '@angular/common/http';

export const jwtHeaderInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req);
};
