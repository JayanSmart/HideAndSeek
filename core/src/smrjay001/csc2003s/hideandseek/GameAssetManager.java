package smrjay001.csc2003s.hideandseek;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class GameAssetManager {

	public final AssetManager assetManager = new AssetManager();

	//Textures
	private final String player1Image = "assets/characters/player1.png";
	private final String player2Image = "assets/characters/player2.png";
	private final String player3Image = "assets/characters/player3.png";
	private final String player4Image = "assets/characters/player4.png";
	private final String player5Image = "assets/characters/player5.png";
	private final String player6Image = "assets/characters/player6.png";
	private final String coinImage = "assets/items/BlueCoin.png";

	//Maps
	public final String entranceHallMap = "assets/maps/EntranceHall2.tmx";

	public void queueAddImage(){
		assetManager.load(player1Image, Texture.class);
		assetManager.load(player2Image, Texture.class);
		assetManager.load(player3Image, Texture.class);
		assetManager.load(player4Image, Texture.class);
		assetManager.load(player5Image, Texture.class);
		assetManager.load(player6Image, Texture.class);
		assetManager.load(coinImage, 	Texture.class);
	}
}
