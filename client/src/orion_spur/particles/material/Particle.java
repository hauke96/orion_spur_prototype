package orion_spur.particles.material;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import orion_spur.level.domainvalue.LevelElementType;
import orion_spur.level.material.LevelElement;
import orion_spur.level.view.LevelView.LayerZIndex;

public abstract class Particle extends LevelElement
{
	// TODO add timestamt. Just use the local time in milliseconds.
	
	protected Particle(String id, Vector2 position, Vector2 movementVector, float rotation, LayerZIndex layer, LevelElementType type, String assetPath)
	{
		super(id, position, movementVector, rotation, layer, type, assetPath);
	}
	
	public abstract TextureRegion getTexture();
}
