package orion_spur.player.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import juard.contract.Contract;
import juard.event.EventArgs;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.common.view.ImageActor;
import orion_spur.player.service.IPlayerService;

public class Player extends ImageActor
{
	public EventArgs PositionChanged = new EventArgs(); // sending the offset at [0]
	
	private float	_acceleration;	// m/s²
	private float	_maxSpeed;		// m/s
	private float	_rotationSpeed;	// degree per second
	private float	_rotationDegree;// degree the player is rotated
	
	private Vector2 _movementVector;
	
	private IPlayerService _playerService;
	
	public Player(IPlayerService playerService, IUnitConverter unitConverter, String file)
	{
		super(file);
		
		Contract.NotNull(playerService);
		
		_playerService = playerService;
		
		setBounds(0, 0, 20, 20);
		
		_acceleration = unitConverter.convertFromWorld(3);
		_maxSpeed = unitConverter.convertFromWorld(100);
		_rotationSpeed = 250;
		_rotationDegree = 0;
		
		_movementVector = new Vector2();
		
		_sprite.setBounds(getX(), getY(), getWidth(), getHeight());
		_sprite.setOrigin(getWidth() / 2, getHeight() / 2);
		_sprite.rotate(_rotationDegree);
		
		Contract.Satisfy(_sprite != null);
		Contract.Satisfy(_sprite.getTexture() != null);
		Contract.Satisfy(_acceleration > 0);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		// System.out.println(_movementVector.len() * 0.2f * 3.6f + " km/h");
		_sprite.draw(batch);
	}
	
	@Override
	public void act(float delta)
	{
		Vector2 position = new Vector2(getX(), getY());
		Vector2 movementAdjustion = new Vector2();
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
		{
			movementAdjustion = movementAdjustion.add(_acceleration * delta, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
		{
			movementAdjustion = movementAdjustion.add(-_acceleration * delta, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
		{
			movementAdjustion = movementAdjustion.add(0, _acceleration * delta);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
		{
			movementAdjustion = movementAdjustion.add(0, -_acceleration * delta);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.Q))
		{
			_rotationDegree += _rotationSpeed * delta;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E))
		{
			_rotationDegree -= _rotationSpeed * delta;
		}
		
		movementAdjustion.rotate(_rotationDegree);
		
		_movementVector = _movementVector.add(movementAdjustion);
		
		if (_movementVector.len() > _maxSpeed)
		{
			_movementVector.setLength(_maxSpeed);
		}
		
		position.add(_movementVector.x * delta / getScaleX(), _movementVector.y * delta / getScaleY());
		
		if (position.x != getX() || position.y != getY())
		{
			setPosition(position.x, position.y);
		}
		
		_sprite.setRotation(_rotationDegree);
	}
	
	@Override
	public void setPosition(float x, float y)
	{
		Vector2 offset = new Vector2(x - getX(), y - getY());
		
		super.setPosition(x, y);
		
		// TODO set position on player service
		PositionChanged.fireEvent(offset);
	}
	
	public Vector2 getCenterPosition()
	{
		return new Vector2(getX(), getY());
	}
}
