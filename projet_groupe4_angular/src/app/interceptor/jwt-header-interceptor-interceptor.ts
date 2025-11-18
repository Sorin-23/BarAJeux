import { HttpInterceptorFn } from '@angular/common/http';

export const jwtHeaderInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req);
};
