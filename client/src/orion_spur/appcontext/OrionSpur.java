package orion_spur.appcontext;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;

import juard.injection.Locator;
import juard.log.Logger;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.converter.IUnitConverter;
import orion_spur.level.service.ILevelService;
import orion_spur.particles.service.IParticleService;
import orion_spur.player.service.ILoginService;
import orion_spur.player.service.IPlayerService;
import orion_spur.remoteObjects.Service.IRemoteObjectService;
import orion_spur.screen.game.MainGameScreen;
import orion_spur.screen.mainmenu.MainMenuScreen;

public class OrionSpur extends Game implements ApplicationListener
{
	private static final float WORLD_UNITS_PER_PIXEL = 6f;
	
	private int	_width;
	private int	_height;
	
	public OrionSpur(int width, int height)
	{
		_width = width;
		_height = height;
	}
	
	@Override
	public void create()
	{
		MainMenuScreen screen = new MainMenuScreen();
		screen.PlayButtonClicked.add(() ->
		{
			try
			{
				MainGameScreen newScreen =
				        new MainGameScreen(Locator.get(IUnitConverter.class),
				            Locator.get(ICoordinateConverter.class),
				            Locator.get(ILevelService.class),
				            Locator.get(ILoginService.class),
				            Locator.get(IParticleService.class),
				            Locator.get(IPlayerService.class),
				            Locator.get(IRemoteObjectService.class),
				            _width,
				            _height,
				            WORLD_UNITS_PER_PIXEL);
				// TODO show a loading screen here
				newScreen.MainScreenInitialized.add(() ->
				{
					screen.dispose();
					setScreen(newScreen);
				});
			}
			catch (Exception e)
			{
				Logger.error("Could not create main game screen", e);
			}
		});
		
		setScreen(screen);
	}
	
	@Override
	public void resize(int width, int height)
	{
	}
	
	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		super.render();
	}
	
	@Override
	public void pause()
	{
	}
	
	@Override
	public void resume()
	{
	}
	
	@Override
	public void dispose()
	{
	}
}
