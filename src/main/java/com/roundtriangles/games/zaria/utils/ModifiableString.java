package com.roundtriangles.games.zaria.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.concurrent.atomic.AtomicReference;

public class ModifiableString implements CharSequence {

    final char[] buffer;
    final AtomicReference<CharBuffer> cbRef;
    final String string;

    public ModifiableString(String initializingString) {
        buffer = new char[initializingString.length()];
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
        buffer[index] = character;
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

    @Override
    public String toString() {
        return string;
    }
}