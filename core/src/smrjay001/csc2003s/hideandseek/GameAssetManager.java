package smrjay001.csc2003s.hideandseek;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class GameAssetManager {

	public final AssetManager assetManager = new AssetManager();

	//Textures
	public final String player1Image = "assets/characters/player1.png";

	//Maps
	public final String entranceHallMap = "assets/maps/EntranceHall2.tmx";

	public void queueAddImage(){
		assetManager.load(player1Image, Texture.class);
	}
}
