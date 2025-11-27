import { Personne } from "./personne";

export class Employe extends Personne {

    constructor(id: number,
        nom: string,
        prenom: string,
        mail: string,
        mdp: string,
        telephone: string | undefined,
        public _job:string,
        public _gameMaster : boolean
    
    ){
        super(id, nom, prenom, mail, mdp, telephone);
    }

    public get job(): string {
        return this._job;
    }
    public set job(value: string) {
        this._job = value;
    }

    public get gameMaster(): boolean {
        return this._gameMaster;
    }
    public set gameMaster(value: boolean) {
        this._gameMaster = value;
    }

    public override toJson(): any {
        return {
            ...super.toJson(),
            job: this._job,
            gameMaster: this._gameMaster
        };
    }
}
