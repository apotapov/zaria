package com.roundtriangles.games.zaria.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.concurrent.atomic.AtomicReference;

public class TimeCharSequence implements CharSequence {

    private static final char[] NUMBERS = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char SEPARATOR = ':';
    private static final int TIME_LENGTH = 9;
    private static final String INITIALIZING_STRING = "000000000";

    final char[] time;
    final AtomicReference<CharBuffer> cbRef;
    final String timeString;

    public TimeCharSequence() {
        time = new char[TIME_LENGTH];
        cbRef = new AtomicReference<CharBuffer>();
        Charset charset = new Charset("foo", null) {
            @Override
            public boolean contains(Charset cs) {
                return false;
            }

            @Override
            public CharsetDecoder newDecoder() {
                CharsetDecoder cd = new CharsetDecoder(this, 1.0f, 1.0f) {
                    @Override
                    protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
                        cbRef.set(out);
                        while(in.remaining()>0) {
                            out.append((char)in.get());
                        }
                        return CoderResult.UNDERFLOW;
                    }
                };
                return cd;
            }

            @Override
            public CharsetEncoder newEncoder() {
                return null;
            }
        };
        timeString = new String(INITIALIZING_STRING.getBytes(), charset);
    }

    @Override
    public char charAt(int arg0) {
        return timeString.charAt(arg0);
    }

    @Override
    public int length() {
        return timeString.length();
    }

    @Override
    public CharSequence subSequence(int arg0, int arg1) {
        return timeString.subSequence(arg0, arg1);
    }

    public void setTime(int gameTime) {
        time[0] = 0;
        time[1] = 0;
        time[2] = 0;
        time[3] = 0;
        time[4] = 0;
        time[5] = 0;
        time[6] = 0;
        time[7] = 0;
        time[8] = 0;
        int seconds = gameTime % 60;
        int minutes = (gameTime / 60) % 60;

        int secondsSize = paddedInt(seconds, 0);
        time[secondsSize] = SEPARATOR;
        paddedInt(minutes, secondsSize + 1);

        for (int i = TIME_LENGTH-1, j = 0; i >= 0; i--, j++) {
            CharBuffer charBuffer = cbRef.get();
            charBuffer.position(j);
            charBuffer.put(time[i]);
        }
    }

    protected int paddedInt(int intVal, int offset) {
        if (intVal == 0) {
            time[offset] = NUMBERS[0];
            time[offset + 1] = NUMBERS[0];
            return 2;
        } else if (intVal < 10) {
            time[offset] = NUMBERS[intVal];
            time[offset + 1] = NUMBERS[0];
            return 2;
        } else {
            int i = offset;
            while (intVal != 0) {
                int rem = intVal % 10;
                time[i++] = NUMBERS[rem];
                intVal = intVal / 10;
            }
            return i - offset;
        }
    }

    @Override
    public String toString() {
        return timeString;
    }
}
