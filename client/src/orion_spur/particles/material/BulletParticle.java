package orion_spur.particles.material;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import orion_spur.common.service.LayerActor.LayerType;
import orion_spur.level.domainvalue.LevelType;

public class BulletParticle extends Particle
{
	private static Texture _texture;
	
	protected BulletParticle(String id, Vector2 position, Vector2 movementVector, float rotation)
	{
		super(id, position, movementVector, rotation, LayerType.LAYER_ANIMATION, LevelType.IMAGE, "assets/textures/bullet.png");
		
		if (_texture == null)
		{
			_texture = new Texture(Gdx.files.internal(getAssetPath()));
		}
		
		Contract.NotNull(_texture);
	}
	
	@Override
	public Texture getTexture()
	{
		Contract.NotNull(_texture);
		return _texture;
	}
	
}