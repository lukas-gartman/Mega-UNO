package org.megauno.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class Application extends ApplicationAdapter {
	ViewController viewController;
	
	@Override
	public void create () {
		viewController = new ViewController();
	}

	@Override
	public void render () {
		viewController.draw(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		viewController.teardown();
	}

	public static void testFunc () {
		System.out.println("Wow!");
	}
}
