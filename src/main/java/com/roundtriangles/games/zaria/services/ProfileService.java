package com.roundtriangles.games.zaria.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.roundtriangles.games.zaria.services.utils.ProfileFactory;


public class ProfileService<T extends Serializable> implements Disposable {

    private static final String LOG_TAG = ProfileService.class.getSimpleName();

    private String dataFile;
    private ProfileFactory<T> factory;
    private T profile;
    private Json json;

    public ProfileService(ProfileFactory<T> factory) {
        json = new Json();
        this.factory = factory;
    }

    public T retrieveProfile() {
        if (profile == null) {
            // create the handle for the profile data file
            FileHandle profileDataFile = Gdx.files.local(dataFile);
            Gdx.app.log(LOG_TAG,
                    "Retrieving profile from: " + profileDataFile.path());

            if (profileDataFile.exists()) {
                try {
                    String profileAsText = profileDataFile.readString().trim();
                    profile = json.fromJson(factory.getProfileClass(), profileAsText);
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
            //persist(profile);
        }
        return profile;
    }

    /**
     * Persists the given profile.
     */
    protected void persist(T profile) {
        // create the handle for the profile data file
        FileHandle profileDataFile = Gdx.files.local(dataFile);
        Gdx.app.log(LOG_TAG,
                "Persisting profile in: " + profileDataFile.path());

        // convert the given profile to text
        String profileAsText = json.toJson(profile);

        // write the profile data file
        profileDataFile.writeString(profileAsText, false);
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
