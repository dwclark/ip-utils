package io.github.dwclark.iputils;

import groovy.transform.*;

@Immutable @CompileStatic
class GeoInfo {
    private int geoNameId;
    private int registeredCountryGeoNameId;
    private int representedCountryGeoNameId;
    private long postalCode;
    
    float latitude;
    float longitude;
    float radius;

    String getPostalCode() {
        return PostalCode.decode(postalCode).toString();
    }

    long encodedPostal() {
        return postalCode;
    }
}
