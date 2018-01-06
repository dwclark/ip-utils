package io.github.dwclark.iputils;

import spock.lang.*;
import static Conversions.*;

class ConversionsSpec extends Specification {

    def 'parse long'() {
        expect:

        parseLong("18") == 18L;
        parseLong("192") == 192L;
        parseLong("192", 0, 2) == 19L;
    }

    def 'slash location'(){
        expect:

        slashLocation("192.0.0.0/29") == 9;
    }

    def 'ip 4 to long'(){
        expect:

        ip4ToLong("1.0.0.0") == 16777216L;
        ip4ToLong("1.0.0.255") == 16777471;
        ip4ToLong("4.69.140.16") == 71666704L;
        ip4ToLong("4.69.140.23") == 71666711L;
    }

    def 'to ip 4 string'() {
        expect:
            
        toIp4String(ip4ToLong("1.0.0.0")) == "1.0.0.0";
        toIp4String(ip4ToLong("1.0.0.255")) == "1.0.0.255";
        toIp4String(ip4ToLong("4.69.140.16")) == "4.69.140.16";
        toIp4String(ip4ToLong("4.69.140.23")) == "4.69.140.23";
    }
}
