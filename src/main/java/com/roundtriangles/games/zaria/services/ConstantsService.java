package com.roundtriangles.games.zaria.services;


public class ConstantsService extends ResourceBundleService {

    private static final String CONSTANTS_FILE = "constants/constants" + FILE_EXTENSION;

    String file;

    public ConstantsService() {
        this(CONSTANTS_FILE);
    }

    public ConstantsService(String file) {
        this.file = file;
    }

    public void load() {
        load(file);
    }

    @Override
    public String get(Object key) {
        return get(file, key);
    }

    @Override
    public String get(Object key, Object...parameters) {
        return get(file, key, parameters);
    }

    @Override
    public void onFinishLoading() {
    }
}
