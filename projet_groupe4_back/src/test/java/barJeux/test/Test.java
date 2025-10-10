package barJeux.test;

import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import barJeux.model.*;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Création d'un client
		Client client = new Client("Dupont", "Jean", "jean@mail.com", "1234", "0600000000","Nieppe","59850","100 rue du jeu");
		// Création d'un jeu
		Jeu jeu = new Jeu(
				"Catan",
				Arrays.asList(TypeJeu.PLATEAU),
				10,    // age minimum
				3,     // nb joueur min
				4,     // nb joueur max
				75,    // durée
				2,     // nb exemplaires
				4.6,   // note
				Arrays.asList(CategorieJeu.STRATEGIE),
				"", //url vide
				true // besoin d'un GM

				);

		Jeu jeu2 = new Jeu(
				"Catan2",
				Arrays.asList(TypeJeu.PLATEAU),
				10,    // age minimum
				3,     // nb joueur min
				4,     // nb joueur max
				75,    // durée
				2,     // nb exemplaires
				4.6,   // note
				Arrays.asList(CategorieJeu.STRATEGIE),
				"", //url vide
				true // besoin d'un GM

				);
		Jeu jeu3 = new Jeu(
				"Catan3",
				Arrays.asList(TypeJeu.PLATEAU),
				10,    // age minimum
				3,     // nb joueur min
				4,     // nb joueur max
				75,    // durée
				2,     // nb exemplaires
				4.6,   // note
				Arrays.asList(CategorieJeu.STRATEGIE),
				"", //url vide
				true // besoin d'un GM

				);
		// Création d'un emprunt pour ce client et jeu
		Emprunt emprunt = new Emprunt(client, jeu);
		Emprunt emprunt2 = new Emprunt(client, jeu2);
		Emprunt emprunt3 = new Emprunt(client, jeu3);

		// Simuler le retard
		System.out.println("Avant vérification du retard : " + emprunt.getStatutLocation());
		emprunt.verifierRetard(); // Vérifie si le jeu est en retard
		System.out.println("Après vérification du retard : " + emprunt.getStatutLocation());

		// retour
		emprunt3.rendreJeu();
		// liste jeu emprunté 
		System.out.println("jeu emprunté :");
		for(Emprunt c : client.getEmpruntsStatut(StatutLocation.enCours)) {
			System.out.println(c.toString());
		};

		// liste jeu rendu 
		System.out.println("jeu rendu :");
		for(Emprunt c : client.getEmpruntsStatut(StatutLocation.rendu)) {
			System.out.println(c.toString());
		};





		// Création d'une table
		TableJeu table = new TableJeu("Table du Roi", 4, true);

		// Création d'un employé (GameMaster)
		Employe gm = new Employe("Martin", "Luc", "luc.martin@mail.com", "mdp456", "0605040302", "GameMaster", true);

		// Définir les dates de la réservation
		LocalDateTime debut = LocalDateTime.now().plusDays(1); // demain
		LocalDateTime fin = debut.plusHours(2); // durée 2h

		// Création de la réservation
		Reservation reservation = new Reservation(
				debut,
				fin,
				4,       // nombre de joueurs
				table,
				jeu,
				client,
				gm
				);

		Reservation reservation2 = new Reservation(
				debut,
				fin,
				4,       // nombre de joueurs
				table,
				jeu,
				client,
				gm
				);

		Reservation reservation3 = new Reservation(
				debut,
				fin,
				4,       // nombre de joueurs
				table,
				jeu,
				client,
				gm
				);
		//
		//toutes les reservations 

		List<Reservation> reservations = new ArrayList();
		reservations.add(reservation);
		reservations.add(reservation2);
		reservations.add(reservation3);

		// Vérification de l'état initial
		System.out.println(reservation);

		reservation2.annuler();

		reservation3.terminer();

		for (Reservation r : reservations) {
			System.out.println(r.toString() );
		}
		 
		
		/*
		// lien avec la bdd
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("BarJeuxJPA");
		EntityManager em = emf.createEntityManager();

		// démarre une transaction
		em.getTransaction().begin();


		Client clientJonahGruson = new Client("Gruson","Jonah","gruson5@gmail.com","mdp","0123456789","aa","aa","56765");

		// prépare un insert
		em.persist(clientJonahGruson);



		// envoie en bdd
		em.getTransaction().commit();
		em.close();
		emf.close();
*/


	}

}
