import { Routes } from '@angular/router';
import { authGuard } from './guard/auth-guard';
import { TablePage } from './page/table-page/table-page';
import { AdminPage } from './page/admin-page/admin-page';
import { ClientPage } from './page/client-page/client-page';
import { HomePage } from './page/home-page/home-page';
import { JeuPage } from './page/jeu-page/jeu-page';
import { LoginPage } from './page/login-page/login-page';
import { InscriptionPage } from './page/inscription-page/inscription-page';
import { roleGuard } from './guard/role-guard';
import { ReservationPage } from './page/reservation-page/reservation-page';
import { EmployePage } from './page/employe-page/employe-page';

export const routes: Routes = [
  {
    path: 'table',
    component: TablePage,
    canActivate: [authGuard, roleGuard],
    data: { role: 'ROLE_CLIENT' },
  },
  {
    path: 'jeu',
    component: JeuPage,
    canActivate: [authGuard, roleGuard],
    data: { role: 'ROLE_CLIENT' },
  },
  {
    path: 'client',
    component: ClientPage,
    canActivate: [authGuard, roleGuard],
    data: { role: 'ROLE_CLIENT' },
  },
  { path: 'reservations/:id', component: ReservationPage },
  {
    path: 'admin',
    component: AdminPage,
    canActivate: [authGuard, roleGuard],
    data: { role: 'ROLE_EMPLOYE' },
  },
  {
    path: 'employe',
    component: EmployePage,
    canActivate: [authGuard, roleGuard],
    data: { role: 'ROLE_EMPLOYE' },
  },
  { path: 'home', component: HomePage },
  { path: 'login', component: LoginPage },
  { path: 'inscription', component: InscriptionPage },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
];
