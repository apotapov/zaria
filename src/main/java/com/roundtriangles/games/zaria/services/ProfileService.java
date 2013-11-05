package com.roundtriangles.games.zaria.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.roundtriangles.games.zaria.services.utils.ProfileFactory;


public class ProfileService<T> implements Disposable {

    private static final String LOG_TAG = ProfileService.class.getSimpleName();

    private String dataFile;
    private ProfileFactory<T> factory;
    private T profile;
    private Json json;

    public ProfileService(ProfileFactory<T> factory) {
        this.factory = factory;
        this.json = factory.getJson();
    }

    public T retrieveProfile() {
        if (profile == null) {
            // create the handle for the profile data file
            FileHandle profileDataFile = Gdx.files.external(dataFile);
            Gdx.app.log(LOG_TAG,
                    "Retrieving profile from: " + profileDataFile.path());

            if (profileDataFile.exists()) {
                try {
                    profile = json.fromJson(factory.getProfileClass(), profileDataFile);
                } catch (Exception e) {
                    // log the exception
                    Gdx.app.error(LOG_TAG,
                            "Unable to parse existing profile data file", e);
                }
            }
        }

        // if unable to retrieve, create a new profile
        if (profile == null) {
            profile = factory.newProfile();
            persist(profile);
        }
        return profile;
    }

    /**
     * Persists the given profile.
     */
    protected void persist(T profile) {
        try {
            // create the handle for the profile data file
            FileHandle profileDataFile = Gdx.files.external(dataFile);
            Gdx.app.log(LOG_TAG,
                    "Persisting profile in: " + profileDataFile.path());

            // convert the given profile to text
            json.toJson(profile, profileDataFile);
        } catch (Exception e) {
            Gdx.app.error(LOG_TAG, "Could not write profile file: " + dataFile, e);
        }
    }

    public void persist() {
        if (profile != null) {
            persist(profile);
        }
    }

    @Override
    public void dispose() {
        persist();
    }

    public void initialize(String dataFile) {
        this.dataFile = dataFile;
        retrieveProfile();
    }
}
