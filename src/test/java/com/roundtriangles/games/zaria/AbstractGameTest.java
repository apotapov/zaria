package com.roundtriangles.games.zaria;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.graphics.FPSLogger;
import com.roundtriangles.games.zaria.screen.AbstractScreen;
import com.roundtriangles.games.zaria.screen.LoadingScreen;
import com.roundtriangles.games.zaria.services.SoundService;

public class AbstractGameTest {

    private class TestGame extends AbstractGame<TestGame> {

        @Override
        public LoadingScreen<TestGame> getLoadingScreen() {
            return null;
        }

        @Override
        public AbstractScreen<TestGame> getMainMenuScreen() {
            return null;
        }

        @Override
        public void initialize() {
        }

        @Override
        public FPSLogger createFPSLogger() {
            return null;
        }

        @Override
        public SoundService createSoundService() {
            return null;
        }

    }

    @BeforeClass
    public static void beforeClass() {
    }

    @AfterClass
    public static void afterClass() {
    }

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    public void testGame() {
        AbstractGame<TestGame> testGame = new TestGame();
        Assert.assertNull(testGame.getMainMenuScreen());
    }

}
