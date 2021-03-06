package orion_spur.common.factory;

import com.badlogic.gdx.scenes.scene2d.Actor;

import juard.contract.Contract;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.common.view.ImageActor;
import orion_spur.level.material.LevelElement;
import orion_spur.particles.service.IParticleService;
import orion_spur.player.material.SpaceShip;
import orion_spur.player.service.IPlayerService;
import orion_spur.player.view.PlayerView;
import orion_spur.remoteObjects.View.RemoteObjectView;

public class ActorFactoryImpl implements IActorFactory
{
	private IPlayerService			_playerService;
	private IUnitConverter			_unitConverter;
	private ICoordinateConverter	_coordinateConverter;
	private IParticleService		_particleService;
	
	public ActorFactoryImpl(IPlayerService playerService, IUnitConverter unitConverter, ICoordinateConverter coordinateConverter, IParticleService particleService)
	{
		Contract.NotNull(playerService);
		Contract.NotNull(unitConverter);
		Contract.NotNull(coordinateConverter);
		Contract.NotNull(particleService);
		
		_playerService = playerService;
		_unitConverter = unitConverter;
		_coordinateConverter = coordinateConverter;
		_particleService = particleService;
	}
	
	@Override
	public Actor convert(LevelElement levelElement) throws Exception
	{
		Actor result = null;
		
		switch (levelElement.getType())
		{
			case IMAGE:
				result = new ImageActor(levelElement);
				setPosition(levelElement, result);
				break;
			case PLAYER:
				result = new PlayerView(_playerService,
				    _unitConverter,
				    _particleService,
				    (SpaceShip) levelElement,
				    _coordinateConverter.universeToWorld(_playerService.getPosition()));
				break;
			case REMOTE_OBJECT:
				result = new RemoteObjectView(levelElement, _coordinateConverter);
				break;
			default:
				result = new Actor();
				// setPosition(levelElement, result);
				break;
		}
		
		Contract.NotNull(result);
		return result;
	}
	
	private void setPosition(LevelElement levelElement, Actor actor)
	{
		actor.setPosition(levelElement.getPosition().x, levelElement.getPosition().y);
	}
}
