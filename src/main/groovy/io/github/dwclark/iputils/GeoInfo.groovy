package io.github.dwclark.iputils;

import groovy.transform.*;

@Immutable @CompileStatic
class GeoInfo {
    private int geoNameId;
    private int registeredCountryGeoNameId;
    private int representedCountryGeoNameId;
    
    String postalCode;
    float latitude;
    float longitude;
    int accuracyRadius;
}
