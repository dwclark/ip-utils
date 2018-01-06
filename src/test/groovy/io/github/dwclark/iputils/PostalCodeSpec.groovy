package io.github.dwclark.iputils;

import spock.lang.*;
import static PostalCode.*;

class PostalCodeSpec extends Specification {

    def 'encode and decode'() {
        expect:

        decode(encode('12345')).toString() == '12345';
        decode(encode('EC1A 1BB')).toString() == 'EC1A 1BB';
        decode(encode('H0H 0H0')).toString() == 'H0H 0H0';
    }
}
