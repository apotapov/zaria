package com.roundtriangles.games.zaria.services;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class GraphicsService implements Disposable {
    private static final String LOG_TAG = GraphicsService.class.getSimpleName();

    private static final float DEFAULT_ANIMATION_DURATION = 0.1f;

    private AssetManager assetManager;
    private Map<String, Float> animationDuration;
    private Array<String> graphicalElements;

    private Map<String, Sprite> spriteMap;
    private Map<String, Map<Integer, Sprite>> indexedSpriteMap;
    private Map<String, Array<Sprite>> spriteArrayMap;
    private Map<String, Animation> animationMap;

    public GraphicsService(AssetManager assetManager, Map<String, Float> animationDuration) {
        this.assetManager = assetManager;
        this.animationDuration = animationDuration;
        this.graphicalElements = new Array<String>();
        this.spriteMap = new HashMap<String, Sprite>();
        this.indexedSpriteMap = new HashMap<String, Map<Integer,Sprite>>();
        this.spriteArrayMap = new HashMap<String, Array<Sprite>>();
        this.animationMap = new HashMap<String, Animation>();
    }

    public GraphicsService() {
        this(null, null);
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void setAnimationDuration(Map<String, Float> animationDuration) {
        this.animationDuration = animationDuration;
    }

    @Override
    public void dispose() {
        Gdx.app.log(LOG_TAG, "Disposing graphics manager");
        for (int i = 0; i < graphicalElements.size; i++) {
            String element = graphicalElements.get(i);
            if (assetManager.isLoaded(element)) {
                assetManager.unload(element);
            }
        }
        graphicalElements.clear();
    }

    public Texture getTexture(String name) {
        if (assetManager.isLoaded(name)) {
            Texture texture = assetManager.get(name, Texture.class);
            return texture;
        } else {
            throw new GdxRuntimeException("Could not find specified skin: " + name);
        }
    }

    public Skin getSkin(String name) {
        if (assetManager.isLoaded(name)) {
            return assetManager.get(name, Skin.class);
        } else {
            throw new GdxRuntimeException("Could not find specified skin: " + name);
        }
    }

    public BitmapFont getFont(String name) {
        if (assetManager.isLoaded(name)) {
            return assetManager.get(name, BitmapFont.class);
        } else {
            throw new GdxRuntimeException("Could not find specified skin: " + name);
        }
    }

    public TextureAtlas getTextureAtlas(String name) {
        if (assetManager.isLoaded(name)) {
            TextureAtlas atlas = assetManager.get(name, TextureAtlas.class);
            return atlas;
        } else {
            throw new GdxRuntimeException("Could not find specified skin: " + name);
        }
    }

    public Sprite getSprite(String atlasName, String name) {
        Sprite sprite = null;
        if (!spriteMap.containsKey(name)) {
            TextureAtlas atlas = getTextureAtlas(atlasName);
            if (atlas != null) {
                sprite = atlas.createSprite(name);
                if (sprite != null) {
                    spriteMap.put(name, sprite);
                }
            }
        } else {
            sprite = spriteMap.get(name);
        }
        return sprite;
    }

    public Sprite getSprite(String atlasName, String name, int index) {
        Sprite sprite = null;
        if (!indexedSpriteMap.containsKey(name)) {
            indexedSpriteMap.put(name, new HashMap<Integer, Sprite>());
        }
        Map<Integer, Sprite> indexedSprites = indexedSpriteMap.get(name);
        if (!indexedSprites.containsKey(index)) {
            TextureAtlas atlas = getTextureAtlas(atlasName);
            if (atlas != null) {
                sprite = atlas.createSprite(name, index);
                if (sprite != null) {
                    indexedSprites.put(index, sprite);
                }
            }
        } else {
            sprite = indexedSprites.get(index);
        }
        return sprite;
    }

    public Array<Sprite> getSprites(String atlasName, String name) {
        Array<Sprite> spriteArray = null;
        if (!spriteArrayMap.containsKey(name)) {
            TextureAtlas atlas = getTextureAtlas(atlasName);
            if (atlas != null) {
                spriteArray = atlas.createSprites(name);
                if (spriteArray != null) {
                    spriteArrayMap.put(name, spriteArray);
                }
            }
        } else {
            spriteArray = spriteArrayMap.get(name);
        }
        return spriteArray;
    }

    public Animation getAntimaion(String atlasName, String name) {
        Animation animation = null;
        if (!animationMap.containsKey(name)) {
            Array<Sprite> sprites = getSprites(atlasName, name);
            if (sprites != null) {
                animation = new Animation(getAnimationDuration(name), sprites);
                animationMap.put(name, animation);
            }
        } else {
            animation = animationMap.get(name);
        }
        return animation;
    }

    protected float getAnimationDuration(String name) {
        if (animationDuration != null && animationDuration.containsKey(name)) {
            return animationDuration.get(name);
        }
        return DEFAULT_ANIMATION_DURATION;
    }

    public void loadTextures(String...textureList) {
        int len = textureList.length;
        for (int i = 0; i < len; i++) {
            String name = textureList[i];

            TextureParameter param = new TextureParameter();
            param.minFilter = TextureFilter.Linear;
            param.magFilter = TextureFilter.Linear;
            param.genMipMaps = true;
            assetManager.load(name, Texture.class, param);
            graphicalElements.add(name);
        }
    }

    public void loadTextureAtlases(String...atlases) {
        for (String atlas : atlases) {
            assetManager.load(atlas, TextureAtlas.class);
            graphicalElements.add(atlas);
        }
    }

    public void loadSkins(String...skinList) {
        int len = skinList.length;
        for (int i = 0; i < len; i++) {
            String name = skinList[i];
            assetManager.load(name, Skin.class);
            graphicalElements.add(name);
        }
    }

    public void loadFonts(String...fonts) {
        for (String font : fonts) {
            assetManager.load(font, BitmapFont.class);
            graphicalElements.add(font);
        }
    }

    public void onFinishLoading() {
    }


}
