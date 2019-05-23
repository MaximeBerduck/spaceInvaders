package fr.unilim.iut.spaceinvaders.model;

public class Collision {

	public static boolean hautSprite1DansSprite2(Sprite sprite1, Sprite sprite2) {
		boolean temp = false;
		if (null != sprite1 && null != sprite2) {
			if (sprite1.ordonneeLaPlusBasse() <= sprite2.ordonneeLaPlusHaute()
					&& sprite1.ordonneeLaPlusBasse() >= sprite2.ordonneeLaPlusBasse()) {
				temp = true;
			}
		}
		return temp;
	}

	public static boolean gaucheSprite1ComprisSprite2(Sprite sprite1, Sprite sprite2) {
		boolean temp = false;
		if (null != sprite1 && null != sprite2) {
			if (sprite1.abscisseLaPlusAGauche() <= sprite2.abscisseLaPlusADroite()
					&& sprite1.abscisseLaPlusAGauche() >= sprite2.abscisseLaPlusAGauche()) {
				temp = true;
			}
		}
		return temp;
	}

	public static boolean droiteSprite1ComprisSprite2(Sprite sprite1, Sprite sprite2) {
		boolean temp = false;
		if (null != sprite1 && null != sprite2) {
			if (sprite1.abscisseLaPlusADroite() <= sprite2.abscisseLaPlusADroite()
					&& sprite1.abscisseLaPlusADroite() >= sprite2.abscisseLaPlusAGauche()) {
				temp = true;
			}
		}

		return temp;
	}

	public static boolean sprite1BasDansSprite2(Sprite sprite1, Sprite sprite2) {
		boolean temp = false;
		if (null != sprite1 && null != sprite2) {
			if (sprite1.ordonneeLaPlusHaute() >= sprite2.ordonneeLaPlusBasse()
					&& sprite1.ordonneeLaPlusHaute() <= sprite2.ordonneeLaPlusHaute()) {
				temp = true;
			}
		}

		return temp;
	}

	public static boolean detecterCollision(Sprite sprite1, Sprite sprite2) {
		boolean temp = false;
		if (null != sprite1 && null != sprite2) {
			if (sprite1DansSprite2(sprite1, sprite2)) {
				temp = true;
			}
		}

		return temp;
	}

	private static boolean sprite1DansSprite2(Sprite sprite1, Sprite sprite2) {
		return hauteurSprite1DansSprite2(sprite1, sprite2)
				&& lateralSprite1DansSprite2(sprite1, sprite2);
	}

	private static boolean lateralSprite1DansSprite2(Sprite sprite1, Sprite sprite2) {
		return Collision.droiteSprite1ComprisSprite2(sprite1, sprite2)
				|| Collision.gaucheSprite1ComprisSprite2(sprite1, sprite2);
	}

	private static boolean hauteurSprite1DansSprite2(Sprite sprite1, Sprite sprite2) {
		return Collision.sprite1BasDansSprite2(sprite1, sprite2)
				|| Collision.hautSprite1DansSprite2(sprite1, sprite2);
	}

}
