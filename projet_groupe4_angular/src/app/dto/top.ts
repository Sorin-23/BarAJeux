import { Jeu } from "./jeu";

export class Top {
  constructor(public _jeu: Jeu, public _count: number) {}

  public get jeu(): Jeu {
    return this._jeu;
  }

  public set jeu(value: Jeu) {
    this._jeu = value;
  }

  public get count(): number {
    return this._count;
  }

  public set count(value: number) {
    this._count = value;
  }

}
