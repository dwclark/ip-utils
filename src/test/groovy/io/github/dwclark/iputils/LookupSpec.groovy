package io.github.dwclark.iputils;

import spock.lang.*;
import static Ip4Range.*;

class LookupSpec extends Specification {

    def lookup = new Lookup();
    def one, two, three;
    
    def setup() {
        def treeMap = new TreeMap<>();
        one = new GeoInfo(geoNameId: 1, registeredCountryGeoNameId: 1,
                          representedCountryGeoNameId: 1,
                          postalCode: PostalCode.encode('12345'),
                          latitude: 10f, longitude: 10f, radius: 5f);
        two = new GeoInfo(geoNameId: 2, registeredCountryGeoNameId: 2,
                          representedCountryGeoNameId: 2,
                          postalCode: PostalCode.encode('H0H 0H0'),
                          latitude: 10f, longitude: 60f, radius: 5f);
        three = new GeoInfo(geoNameId: 1, registeredCountryGeoNameId: 1,
                            representedCountryGeoNameId: 1,
                            postalCode: PostalCode.encode('EC1A 1BB'),
                            latitude: 35f, longitude: 0f, radius: 5f);
        
        treeMap.put(immutable('1.0.0.0/24'), one);
        treeMap.put(immutable('2.0.0.0/24'), two);
        treeMap.put(immutable('3.0.0.0/28'), three);
        lookup.replace(treeMap);
    }

    def 'test find matching'() {
        expect:

        lookup.geoForIp4('1.0.0.0') == one;
        lookup.geoForIp4('1.0.0.1') == one;
        lookup.geoForIp4('1.0.0.255') == one;
        lookup.geoForIp4('2.0.0.1') == two;
        lookup.geoForIp4('3.0.0.1') == three;
        lookup.geoForIp4('1.0.0.16').postalCode == '12345';
    }

    def 'test missing'() {
        expect:

        lookup.geoForIp4('4.0.0.1') == null;
        lookup.geoForIp4('3.0.0.252') == null;
    }
}
