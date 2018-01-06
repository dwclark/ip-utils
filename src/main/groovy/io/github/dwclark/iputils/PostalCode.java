package io.github.dwclark.iputils;

public class PostalCode {

    public static final int MAX = 9;
    public static final long SHIFT_BY = 7L;

    private static long convert(final char c) {
        long ret = (long) c;
        if(ret < 1L || ret > 127L) {
            throw new IllegalArgumentException("Can only encode characters 1 <= c <= 127");
        }

        return ret;
    }
    
    public static long encode(final CharSequence val) {
        if(val.length() > MAX) {
            throw new IllegalArgumentException("String too long for post code encoding");
        }

        int shift = 0;
        long ret = 0L;
        for(int i = val.length() - 1; i >= 0; --i) {
            ret |= (convert(val.charAt(i)) << shift);
            shift += SHIFT_BY;
        }

        return ret;
    }
    
    public static CharSequence decode(final long val) {
        final StringBuilder sb = new StringBuilder(9);
        int shift = 56;
        for(int i = 0; i < 9; ++i) {
            int tmp = ((int) (val >> shift)) & 0x7F;
            if(tmp != 0) {
                sb.append((char) tmp);
            }

            shift -= 7;
        }

        return sb;
    }
}
