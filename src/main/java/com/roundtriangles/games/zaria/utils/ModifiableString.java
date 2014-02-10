package com.roundtriangles.games.zaria.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.concurrent.atomic.AtomicReference;

public class ModifiableString implements CharSequence {


    final AtomicReference<CharBuffer> cbRef;
    final String string;
    String initializingString;

    public ModifiableString(String initializingString) {
        this.initializingString = initializingString;
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
        string = new String(initializingString.getBytes(), charset);
    }

    public void setChar(int index, char character) {
        CharBuffer charBuffer = cbRef.get();
        charBuffer.position(index);
        charBuffer.put(character);
    }

    @Override
    public char charAt(int index) {
        return string.charAt(index);
    }

    @Override
    public int length() {
        return string.length();
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return string.subSequence(start, end);
    }

    public void reset() {
        for (int i = 0; i < initializingString.length(); i++) {
            setChar(i, initializingString.charAt(i));
        }
    }

    @Override
    public String toString() {
        return string;
    }
}
