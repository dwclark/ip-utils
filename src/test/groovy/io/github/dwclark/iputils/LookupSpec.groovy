package io.github.dwclark.iputils;

import spock.lang.*;
import static Ip4Range.*;
import static GeoInfo.*;

class LookupSpec extends Specification {

    def csvText = '''network,geoname_id,registered_country_geoname_id,represented_country_geoname_id,is_anonymous_proxy,is_satellite_provider,postal_code,latitiude,longitude,accuracy_radius
1.0.0.0/24,2077456,2077456,,0,0,12345,10,10,5
4.69.140.16/29,6252001,6252001,,0,0,H0H 0H0,17,26,6
5.61.192.0/21,2635167,2635167,,0,0,75033,,,''';
    
    def lookup = new Lookup();
    def one, two, three;
    
    def setup() {
        def treeMap = new TreeMap<>();
        one = new GeoInfo(_geoNameId: 1, _registeredCountryGeoNameId: 1,
                          _representedCountryGeoNameId: 1,
                          _postalCode: PostalCode.encode('12345'),
                          _latitude: 10f, _longitude: 10f, _radius: 5f);
        two = new GeoInfo(_geoNameId: 2, _registeredCountryGeoNameId: 2,
                          _representedCountryGeoNameId: 2,
                          _postalCode: PostalCode.encode('H0H 0H0'),
                          _latitude: 10f, _longitude: 60f, _radius: 5f);
        three = new GeoInfo(_geoNameId: 1, _registeredCountryGeoNameId: 1,
                            _representedCountryGeoNameId: 1,
                            _postalCode: PostalCode.encode('EC1A 1BB'),
                            _latitude: MISSING_FLOAT, _longitude: MISSING_FLOAT, _radius: MISSING_FLOAT);
        
        treeMap.put(immutable('1.0.0.0/24'), one);
        treeMap.put(immutable('2.0.0.0/24'), two);
        treeMap.put(immutable('3.0.0.0/28'), three);
        lookup.replaceIp4Geo(treeMap);
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

    def 'test parsing'() {
        setup:

        def myLookup = new Lookup();
        myLookup.replaceIp4Geo(new StringReader(csvText));
        println(three);
        expect:

        myLookup.ip4Geo.get().size() == 3;
        myLookup.ip4Geo.get().get(immutable('1.0.0.0/24')) ==  new GeoInfo(_geoNameId: 2077456, _registeredCountryGeoNameId: 2077456,
                                                                           _representedCountryGeoNameId: MISSING_INT,
                                                                           _postalCode: PostalCode.encode('12345'),
                                                                           _latitude: 10f, _longitude: 10f, _radius: 5f);
        myLookup.ip4Geo.get().get(immutable('5.61.192.0/21')) == new GeoInfo(_geoNameId: 2635167, _registeredCountryGeoNameId: 2635167,
                                                                             _representedCountryGeoNameId: MISSING_INT,
                                                                             _postalCode: PostalCode.encode('75033'),
                                                                             _latitude: MISSING_FLOAT, _longitude: MISSING_FLOAT, _radius: MISSING_FLOAT);
    }
}
