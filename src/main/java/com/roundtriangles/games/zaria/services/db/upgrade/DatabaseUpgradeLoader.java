package com.roundtriangles.games.zaria.services.db.upgrade;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class DatabaseUpgradeLoader extends AsynchronousAssetLoader<DatabaseUpgrade, AssetLoaderParameters<DatabaseUpgrade>> {

    private static final String SQL_COMMENT = "--";

    public DatabaseUpgradeLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName,
            FileHandle file, AssetLoaderParameters<DatabaseUpgrade> parameter) {
    }

    @Override
    public DatabaseUpgrade loadSync(AssetManager manager, String fileName,
            FileHandle file, AssetLoaderParameters<DatabaseUpgrade> parameter) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.read()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty() && !line.startsWith(SQL_COMMENT)) {
                    builder.append(line);
                }
            }
            return new DatabaseUpgrade(builder.toString());
        } catch (Exception e) {
            throw new GdxRuntimeException("Error loading resource bundle: " + fileName, e);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName,
            FileHandle file, AssetLoaderParameters<DatabaseUpgrade> parameter) {
        return null;
    }

}
