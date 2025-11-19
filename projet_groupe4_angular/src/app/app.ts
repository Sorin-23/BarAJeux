import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from "./page/header/header";
import { Footer } from "./page/footer/footer";


@Component({
  selector: 'app-root',
  imports: [RouterOutlet
    ,Header, Footer],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

}
