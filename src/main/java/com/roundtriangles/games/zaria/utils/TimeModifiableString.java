package com.roundtriangles.games.zaria.utils;


public class TimeModifiableString extends ModifiableString {

    private static final char[] NUMBERS = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char SEPARATOR = ':';
    private static final int TIME_LENGTH = 9;

    final char[] buffer;

    public TimeModifiableString() {
        super(TIME_LENGTH);
        buffer = new char[TIME_LENGTH];
    }

    public void setTime(int gameTime) {
        buffer[0] = 0;
        buffer[1] = 0;
        buffer[2] = 0;
        buffer[3] = 0;
        buffer[4] = 0;
        buffer[5] = 0;
        buffer[6] = 0;
        buffer[7] = 0;
        buffer[8] = 0;
        int seconds = gameTime % 60;
        int minutes = (gameTime / 60) % 60;

        int secondsSize = paddedInt(seconds, 0);
        buffer[secondsSize] = SEPARATOR;
        paddedInt(minutes, secondsSize + 1);

        for (int i = TIME_LENGTH-1, j = 0; i >= 0; i--, j++) {
            setChar(j, buffer[i]);
        }
    }

    protected int paddedInt(int intVal, int offset) {
        if (intVal == 0) {
            buffer[offset] = NUMBERS[0];
            buffer[offset + 1] = NUMBERS[0];
            return 2;
        } else if (intVal < 10) {
            buffer[offset] = NUMBERS[intVal];
            buffer[offset + 1] = NUMBERS[0];
            return 2;
        } else {
            int i = offset;
            while (intVal != 0) {
                int rem = intVal % 10;
                buffer[i++] = NUMBERS[rem];
                intVal = intVal / 10;
            }
            return i - offset;
        }
    }
}
