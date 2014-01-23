package com.roundtriangles.games.zaria.services;


public class AssetsService extends ConstantsService {

    private static final String ASSETS_FILE = "assets/assets" + FILE_EXTENSION;

    public AssetsService() {
        this(ASSETS_FILE);
    }

    public AssetsService(String file) {
        super(file);
    }
}
