export class AuthResponse {
      constructor(private _token: string) { }

    public get token(): string {
        return this._token;
    }

    public set token(value: string) {
        this._token = value;
    }
}
