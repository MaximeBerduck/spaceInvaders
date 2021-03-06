package fr.unilim.iut.spaceinvaders.model;

import java.util.ArrayList;
import java.util.List;

import fr.unilim.iut.spaceinvaders.moteurjeu.*;
import fr.unilim.iut.spaceinvaders.utils.DebordementEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.HorsEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.MissileException;

public class SpaceInvaders implements Jeu {

	int longueur;
	int hauteur;
	Vaisseau vaisseau;
	List<Missile> missiles;
	List<Envahisseur> envahisseurs;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
		this.missiles = new ArrayList<Missile>();
		this.envahisseurs = new ArrayList<Envahisseur>();
	}

	private boolean estDansEspaceJeu(int x, int y) {
		return ((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur));
	}

	public String recupererEspaceJeuDansChaineASCII() {
		StringBuilder espaceDeJeu = new StringBuilder();
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < longueur; x++) {
				espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
			}
			espaceDeJeu.append(Constante.MARQUE_FIN_LIGNE);
		}
		return espaceDeJeu.toString();
	}

	private char recupererMarqueDeLaPosition(int x, int y) {
		char marque;
		if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_VAISSEAU;
		else if (this.aUnMissileQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_MISSILE;
		else if (this.aUnEnvahisseurQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_ENVAHISSEUR;
		else
			marque = Constante.MARQUE_VIDE;
		return marque;
	}

	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
		boolean temp = false;
		if (this.aUnEnvahisseur()) {
			for (Envahisseur envahisseur : this.envahisseurs) {
				if (envahisseur.occupeLaPosition(x, y)) {
					temp = true;
				}
			}
		}
		return temp;
	}

	boolean aUnEnvahisseur() {
		return !envahisseurs.isEmpty();
	}

	private boolean aUnMissileQuiOccupeLaPosition(int x, int y) {
		boolean temp = false;
		for (Missile missile : missiles) {
			if (null != missile && missile.occupeLaPosition(x, y)) {
				temp = true;
			}
		}
		return temp;
	}

	boolean aUnMissile() {
		return !missiles.isEmpty();
	}

	private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
		return this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y);
	}

	boolean aUnVaisseau() {
		return vaisseau != null;
	}

	public void deplacerVaisseauVersLaDroite() {
		if (vaisseau.abscisseLaPlusADroite() < (longueur - 1)) {
			vaisseau.deplacerHorizontalementVers(Direction.DROITE);
			if (!estDansEspaceJeu(vaisseau.abscisseLaPlusADroite(), vaisseau.ordonneeLaPlusHaute())) {
				vaisseau.positionner(longueur - vaisseau.longueur(), vaisseau.ordonneeLaPlusHaute());
			}
		}
	}

	public void deplacerVaisseauVersLaGauche() {
		if (0 < vaisseau.abscisseLaPlusAGauche())
			vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
		if (!estDansEspaceJeu(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute())) {
			vaisseau.positionner(0, vaisseau.ordonneeLaPlusHaute());
		}
	}

	public void positionnerUnNouveauVaisseau(Dimension dimension, Position position, int vitesse) {

		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

		int longueurVaisseau = dimension.longueur();
		int hauteurVaisseau = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

		vaisseau = new Vaisseau(dimension, position, vitesse);

	}

	@Override
	public void evoluer(Commande commandeUser) {
		if (commandeUser.gauche) {
			deplacerVaisseauVersLaGauche();
		}

		if (commandeUser.droite) {
			deplacerVaisseauVersLaDroite();
		}

		if (commandeUser.tir) {
			tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
					Constante.MISSILE_VITESSE);
		}

		if (this.aUnMissile()) {
			this.deplacerMissile();

		}

		if (this.aUnEnvahisseur()) {
			this.deplacerEnvahisseur();
		}

	}

	@Override
	public boolean etreFini() {
		if (this.aUnEnvahisseur() && this.aUnMissile()) {
			for (int i = 0; i < this.missiles.size(); i++) {
				for (int j = 0; j < this.envahisseurs.size(); j++) {
					if (Collision.detecterCollision(this.missiles.get(i), this.envahisseurs.get(j))) {
						this.envahisseurs.remove(j);
						this.missiles.remove(i);
					}
				}
			}
		}
		return !this.aUnEnvahisseur();
	}

	public Vaisseau recupererVaisseau() {
		return this.vaisseau;
	}

	public void initialiserJeu() {
		Position positionVaisseau = new Position(this.longueur / 2, this.hauteur - 1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		this.positionnerUnNouvelEnvahisseur(
				new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR),
				new Position(Constante.ESPACEJEU_LONGUEUR / 2, 50), Constante.ENVAHISSEUR_VITESSE);
		this.positionnerUnNouvelEnvahisseur(
				new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR),
				new Position(Constante.ESPACEJEU_LONGUEUR - 50, 50), Constante.ENVAHISSEUR_VITESSE);
		positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);
	}

	public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {

		if ((vaisseau.hauteur() + dimensionMissile.hauteur()) > this.hauteur)
			throw new MissileException(
					"Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");
		boolean temp = true;
		for (Missile missile : missiles) {
			if (vaisseau.ordonneeLaPlusBasse() - dimensionMissile.hauteur < missile.ordonneeLaPlusHaute()
					+ Constante.ESPACE_ENTRE_MISSILE_VAISSEAU) {
				temp = false;
			}
		}
		if (temp) {
			this.missiles.add(this.vaisseau.tirerUnMissile(dimensionMissile, vitesseMissile));
		}
	}

	public List<Missile> recuperMissile() {
		return this.missiles;
	}

	public void deplacerMissile() {
		Missile missile;
		if (this.aUnMissile()) {
			for (int i = 0; i < missiles.size(); i++) {
				missile = missiles.get(i);
				missile.deplacerVerticalementVers(Direction.HAUT_ECRAN);
				if (missile.ordonneeLaPlusBasse() < 0) {
					missiles.remove(i);
				}
			}
		}

	}

	public void positionnerUnNouvelEnvahisseur(Dimension dimension, Position position, int vitesse) {
		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position de l'envahisseur est en dehors de l'espace jeu");

		int longueurEnvahisseur = dimension.longueur();
		int hauteurEnvahisseur = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurEnvahisseur - 1, y))
			throw new DebordementEspaceJeuException(
					"L'envahisseur déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurEnvahisseur + 1))
			throw new DebordementEspaceJeuException(
					"L'envahisseur déborde de l'espace jeu vers le bas à cause de sa hauteur");

		this.envahisseurs.add(new Envahisseur(dimension, position, vitesse));
	}

	public void deplacerEnvahisseurVersLaDroite() {
		for (Envahisseur envahisseur : this.envahisseurs) {
			if (null != envahisseur) {
				if (envahisseur.abscisseLaPlusADroite() < (longueur - 1)) {
					envahisseur.deplacerHorizontalementVers(Direction.DROITE);
					if (!estDansEspaceJeu(envahisseur.abscisseLaPlusADroite(), envahisseur.ordonneeLaPlusHaute())) {
						envahisseur.positionner(longueur - envahisseur.longueur(), envahisseur.ordonneeLaPlusHaute());
					}
				}
			}

		}

	}

	public void deplacerEnvahisseurVersLaGauche() {
		for (Envahisseur envahisseur : this.envahisseurs) {
			if (null != envahisseur) {
				if (0 < envahisseur.abscisseLaPlusAGauche())
					envahisseur.deplacerHorizontalementVers(Direction.GAUCHE);
				if (!estDansEspaceJeu(envahisseur.abscisseLaPlusAGauche(), envahisseur.ordonneeLaPlusHaute())) {
					envahisseur.positionner(0, envahisseur.ordonneeLaPlusHaute());
				}
			}
		}

	}

	public void deplacerEnvahisseur() {
		if (this.aUnEnvahisseur()) {
			for (Envahisseur envahisseur : this.envahisseurs) {
				switch (envahisseur.direction) {
				case Constante.DROITE:
					deplacerEnvahisseurVersLaDroite();
					if (envahisseur.abscisseLaPlusADroite() == this.longueur - 1) {
						envahisseur.changerSensDeDirection();
					}
					break;
				case Constante.GAUCHE:
					deplacerEnvahisseurVersLaGauche();
					if (envahisseur.abscisseLaPlusAGauche() == 0) {
						envahisseur.changerSensDeDirection();
					}
					break;
				}
			}

		}
	}

	public List<Envahisseur> recupererEnvahisseur() {
		return this.envahisseurs;
	}
}