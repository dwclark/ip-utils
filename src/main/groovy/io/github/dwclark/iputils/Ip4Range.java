package io.github.dwclark.iputils;

import static io.github.dwclark.iputils.Conversions.*;

public abstract class Ip4Range implements Comparable<Ip4Range> {

    private static final int PHI = 0x9E3779B9;

    public abstract long getLower();
    public abstract long getUpper();

    @Override
    final public int hashCode() {
        final int h = ((int) getUpper()) * ((int) getLower()) * PHI;
		return h ^ (h >>> 16);
    }

    @Override
    final public boolean equals(final Object o) {
        if(!(o instanceof Ip4Range)) {
            return false;
        }

        final Ip4Range rhs = (Ip4Range) o;
        return getLower() == rhs.getLower() && getUpper() == rhs.getUpper();
    }

    @Override
    public String toString() {
        return String.format("%s - %s", toIp4String(getLower()), toIp4String(getUpper()));
    }

    @Override
    public int compareTo(final Ip4Range rhs) {
        return Long.compare(getLower(), rhs.getLower());
    }

    public boolean inRange(final long val) {
        return getLower() <= val && val <= getUpper();
    }

    private static class Immutable extends Ip4Range {
        private final long lower;
        private final long upper;

        public Immutable(final long lower, final long upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public long getLower() {
            return lower;
        }

        public long getUpper() {
            return upper;
        }
    }

    public static Ip4Range immutable(final CharSequence cs) {
        final int slash = slashLocation(cs);
        final long ip = ip4ToLong(cs, 0, slash);
        final long mask = parseLong(cs, slash+1, cs.length());
        return new Immutable(ip, ip + (1L << (32L - mask)) - 1);
    }

    private static class Mutable extends Ip4Range {
        private long lower;
        private long upper;

        public long getLower() {
            return lower;
        }

        public void setLower(final long val) {
            this.lower = val;
        }

        public long getUpper() {
            return upper;
        }

        public void setUpper(final long val) {
            this.upper = val;
        }
    }

    private static final ThreadLocal<Mutable> _tl = ThreadLocal.withInitial(Mutable::new);
    
    public static Ip4Range forSearch(final CharSequence cs) {
        final long val = ip4ToLong(cs, 0, cs.length());
        return forSearch(val, val);
    }

    public static Ip4Range forSearch(final long lower, final long upper) {
        Mutable ret = _tl.get();
        ret.setLower(lower);
        ret.setUpper(upper);
        return ret;
    }
}
