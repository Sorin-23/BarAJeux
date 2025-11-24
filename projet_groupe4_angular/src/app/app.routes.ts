import { Routes } from '@angular/router';
import { authGuard } from './guard/auth-guard';
import { TablePage } from './page/table-page/table-page';
import { AdminPage } from './page/admin-page/admin-page';
import { ClientPage } from './page/client-page/client-page';
import { HomePage } from './page/home-page/home-page';
import { JeuPage } from './page/jeu-page/jeu-page';
import { LoginPage } from './page/login-page/login-page';
export const routes: Routes = [


    {path:'table',component:TablePage /*, canActivate: [authGuard]*/},
    {path:'jeu',component:JeuPage /*, canActivate: [authGuard]*/},
    {path:'client',component:ClientPage /*, canActivate: [authGuard]*/},
    {path:'admin',component:AdminPage /*, canActivate: [authGuard]*/},
    {path:'home',component:HomePage},
    {path:'login',component:LoginPage},
    {path:'', redirectTo:'home' , pathMatch: 'full'}

];
