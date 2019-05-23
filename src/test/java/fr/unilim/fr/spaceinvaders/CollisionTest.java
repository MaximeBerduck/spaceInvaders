package fr.unilim.fr.spaceinvaders;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.unilim.iut.spaceinvaders.model.Collision;
import fr.unilim.iut.spaceinvaders.model.Dimension;
import fr.unilim.iut.spaceinvaders.model.Position;
import fr.unilim.iut.spaceinvaders.model.SpaceInvaders;

public class CollisionTest {

	private SpaceInvaders spaceinvaders;

	@Before
	public void initialisation() {
		spaceinvaders = new SpaceInvaders(15, 10);
	}

	@Test
	public void test_detecterCollisionHautSprite1DansSprite2() {
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(2, 2), new Position(12, 1), 1);
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7, 2), new Position(5, 9), 1);
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 2);

		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();

		assertEquals(true,
				Collision.hautSprite1DansSprite2(spaceinvaders.recuperMissile(), spaceinvaders.recupererEnvahisseur()));
	}

	@Test
	public void test_detecterCollisionGaucheSprite1ComprisGaucheDroiteSprite2() {
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(7, 2), new Position(3, 1), 1);
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7, 2), new Position(5, 9), 1);
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 2);

		assertEquals(true, Collision.gaucheSprite1ComprisSprite2(spaceinvaders.recuperMissile(),
				spaceinvaders.recupererEnvahisseur()));

	}

	@Test
	public void test_detecterCollisionDroiteSprite1ComprisGaucheDroiteSprite2() {
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(7, 2), new Position(3, 1), 1);
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7, 2), new Position(5, 9), 1);
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 2);

		assertEquals(true, Collision.droiteSprite1ComprisSprite2(spaceinvaders.recuperMissile(),
				spaceinvaders.recupererEnvahisseur()));
	}

	@Test
	public void test_detecterCollisionBasSprite1DansSprite2() {
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(7, 2), new Position(3, 1), 1);
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7, 2), new Position(5, 9), 1);
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 2);

		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();

		assertEquals(true,
				Collision.sprite1BasDansSprite2(spaceinvaders.recuperMissile(), spaceinvaders.recupererEnvahisseur()));
	}

	@Test
	public void test_detecterCollisionSprite1AvecSprite2() {
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(7, 2), new Position(3, 1), 1);
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7, 2), new Position(5, 9), 1);
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 2);

		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();

		assertEquals(true,
				Collision.detecterCollision(spaceinvaders.recuperMissile(), spaceinvaders.recupererEnvahisseur()));
	}

}
