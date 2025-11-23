package projet_groupe4.dto.response;

import projet_groupe4.model.Personne;

public class AuthResponse {
	private String token;
	private String role;
	private String nom;
	private String prenom;
	private String username;

	public AuthResponse(String token, Personne personne) {
		if (personne == null) {
			throw new IllegalArgumentException("Personne ne peut pas être null !");
		}
		this.token = token;
		this.username = personne.getMail();
		this.nom = personne.getNom();
		this.prenom = personne.getPrenom();
		if (personne instanceof projet_groupe4.model.Client) {
			this.role = "ROLE_CLIENT";
		} else if (personne instanceof projet_groupe4.model.Employe) {
			this.role = "ROLE_EMPLOYE";
		}
	}

	public String getToken() {
		return token;
	}

	public String getRole() {
		return role;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getUsername() {
		return username;
	}
}
