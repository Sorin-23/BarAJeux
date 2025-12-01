package projet_groupe4.dto.response;

import projet_groupe4.model.Jeu;

public class TopJeuResponse {

	private Jeu jeu;
	private double count;
	
	
	public TopJeuResponse(Jeu jeu, double count) {
		this.jeu = jeu;
		this.count = count;
	}
	
	public Jeu getJeu() {
		return jeu;
	}
	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}

	
	
}
