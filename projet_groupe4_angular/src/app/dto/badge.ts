export class Badge {

    constructor(
        public _id: number,
        public _nomBadge: string,
        public _pointMin: number,
        public _imgURL: string
    ) {}

    public get id(): number {
        return this._id;
    }

    public set id(value: number) {
        this._id = value;
    }

    public get nomBadge(): string {
        return this._nomBadge;
    }

    public set nomBadge(value: string) {
        this._nomBadge = value;
    }

    public get pointMin(): number {
        return this._pointMin;
    }

    public set pointMin(value: number) {
        this._pointMin = value;
    }

    public get imgURL(): string {
        return this._imgURL;
    }

    public set imgURL(value: string) {
        this._imgURL = value;
    }

    public toJson(): any {
        return {
            nomBadge: this._nomBadge,
            pointMin: this._pointMin,
            imgURL: this._imgURL
        };
    }
}
