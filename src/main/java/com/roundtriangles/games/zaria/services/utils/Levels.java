package com.roundtriangles.games.zaria.services.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

public class Levels {
    public Array<String> levels;

    @SuppressWarnings("rawtypes")
    public static class LevelsAssetLoader extends SynchronousAssetLoader<Levels, AssetLoaderParameters<Levels>> {

        Json json;
        Array<AssetDescriptor> dependencies;

        public LevelsAssetLoader() {
            super(new InternalFileHandleResolver());
            json = new Json();
            dependencies = new Array<AssetDescriptor>();
        }

        @Override
        public Levels load(AssetManager assetManager, String fileName,
                FileHandle file, AssetLoaderParameters<Levels> parameter) {
            return json.fromJson(Levels.class, file);
        }

        @Override
        public Array<AssetDescriptor> getDependencies(String fileName,
                FileHandle file, AssetLoaderParameters<Levels> parameter) {
            // TODO Auto-generated method stub
            return dependencies;
        }
    }
}
