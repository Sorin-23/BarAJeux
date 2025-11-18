import { HttpInterceptorFn } from '@angular/common/http';

export const apiUrlInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req);
};
