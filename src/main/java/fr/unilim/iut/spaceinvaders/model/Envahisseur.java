package fr.unilim.iut.spaceinvaders.model;

public class Envahisseur extends Sprite {

	static String direction;

	public Envahisseur(Dimension dimension, Position positionOrigine, int vitesse) {
		super(dimension, positionOrigine, vitesse);
		this.direction = Constante.DROITE;
	}

	public void changerSensDeDirection() {
		if (this.direction == Constante.DROITE)
			this.direction = Constante.GAUCHE;
		else
			this.direction = Constante.DROITE;
	}

}
