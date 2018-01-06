package io.github.dwclark.iputils;

public class Conversions {

    public static int nextPeriod(final CharSequence cs, final int start) {
        int i = start;
        while(i < cs.length() && cs.charAt(i) != '.') ++i;

        if(i == cs.length()) {
            throw new IllegalArgumentException("Illegal octet parsed");
        }
        
        return i;
    }

    public static long parseLong(final CharSequence cs) {
        return parseLong(cs, 0, cs.length());
    }

    public static long parseLong(final CharSequence cs, final int start, final int end) {
        long accum = 0L;
        long mult = 1L;
        for(int i = end - 1; i >= start; --i) {
            accum += mult * Character.digit(cs.charAt(i), 10);
            mult *= 10;
        }

        return accum;
    }

    public static int slashLocation(final CharSequence cs) {
        for(int i = 0; i < cs.length(); ++i) {
            if(cs.charAt(i) == '/') {
                return i;
            }
        }

        return -1;
    }

    public static long ip4ToLong(final CharSequence cs) {
        return ip4ToLong(cs, 0, cs.length());
    }
    
    public static long ip4ToLong(final CharSequence cs, final int start, final int end) {
        final int p1 = nextPeriod(cs, start);
        final int p2 = nextPeriod(cs, p1+1);
        final int p3 = nextPeriod(cs, p2+1);

        return ((parseLong(cs, 0, p1) << 24L) +
                (parseLong(cs, p1+1, p2) << 16L) +
                (parseLong(cs, p2+1, p3) << 8L) +
                (parseLong(cs, p3+1, end)));
    }

    public static String toIp4String(final long val) {
        return String.format("%d.%d.%d.%d",
                             (int) ((val >>> 24) & 0xFF),
                             (int) ((val >>> 16) & 0xFF),
                             (int) ((val >>> 8) & 0xFF),
                             (int) (val & 0xFF));
    }
}
