package io.github.dwclark.iputils;

import spock.lang.*;
import static Ip4Range.*;

class Ip4RangeSpec extends Specification {

    def 'make immmutable'() {
        setup:

        def one = immutable('1.0.0.0/24');
        def two = immutable('5.61.192.0/21');
        
        expect:

        one.lower == 16777216L;
        one.upper == 16777471L;
        two.lower == 87932928L;
        two.upper == 87934975L;
    }

    def 'equals and hash code'() {
        expect:

        immutable('1.0.0.0/32').equals(forSearch('1.0.0.0'));
        forSearch('1.0.0.0').equals(immutable('1.0.0.0/32'));
        immutable('4.69.140.16/32').hashCode() == forSearch('4.69.140.16').hashCode();
    }

    def 'compare to and in range'(){
        expect:

        immutable('1.0.0.0/32') <=> forSearch('1.0.0.0') == 0;
        immutable('1.0.0.0/24') < forSearch('1.0.0.1');
        immutable('1.0.0.0/24').inRange(forSearch('1.0.0.1').lower);
    }
}
