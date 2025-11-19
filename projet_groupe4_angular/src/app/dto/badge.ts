export class Badge {

    constructor(
        private _id: number,
        private _nomBadge: string,
        private _pointMin: number,
        private _imgURL?: string
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

    public get imgURL(): string | undefined {
        return this._imgURL;
    }

    public set imgURL(value: string | undefined) {
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
