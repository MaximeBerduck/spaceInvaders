package fr.unilim.fr.spaceinvaders;

public class Vaisseau {

	int x;
	int y;
	int longueur;
	int hauteur;

	public Vaisseau(int longueur, int hauteur) {
		this(longueur, hauteur, 0, 0);
	}

    public Vaisseau(int longueur, int hauteur, int x, int y) {
	   this.longueur=longueur;
	   this.hauteur=hauteur;
	   this.x = x;
	   this.y = y;
    }

	public boolean occupeLaPosition(int x, int y) {
		return (estAbscisseCouverte(x) && estOrdonneeCouverte(y));
	}

	private boolean estOrdonneeCouverte(int y) {
		return (ordonneeLaPlusHaute()<=y) && (y<=ordonneeLaPlusBasse());
	}

	private int ordonneeLaPlusHaute() {
		return ordonneeLaPlusBasse()-this.hauteur+1;
	}

	private int ordonneeLaPlusBasse() {
		return this.y;
	}

	private boolean estAbscisseCouverte(int x) {
		return (abscisseLaPlusAGauche()<=x) && (x<= abscisseLaPlusADroite());
	}

	public int abscisseLaPlusADroite() {
		return this.x+this.longueur-1;
	}

	public void seDeplacerVersLaDroite() {
		this.x = this.x + 1;
	}
	
	public int abscisseLaPlusAGauche() {
        return this.x;
	}

	public void seDeplacerVersLaGauche() {
		this.x = this.x - 1;
		
	}

	public void positionner(int x, int y) {
		this.x = x;
	    this.y = y;
	}
}