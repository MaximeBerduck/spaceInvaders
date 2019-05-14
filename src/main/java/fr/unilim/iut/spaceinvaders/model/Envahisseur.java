package fr.unilim.iut.spaceinvaders.model;

public class Envahisseur extends Sprite {

	String direction;
	
	public Envahisseur(Dimension dimension, Position positionOrigine, int vitesse) {
		super(dimension, positionOrigine, vitesse);
		this.direction = Constante.DROITE;
	}

	public void changerSensDeDirection(String direction) {
		this.direction = direction;
	}

}
