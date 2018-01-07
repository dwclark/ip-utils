package io.github.dwclark.iputils;

import groovy.transform.*;

@CompileStatic
@Immutable
class GeoInfo {
    static final int MISSING_INT = Integer.MIN_VALUE;
    static final float MISSING_FLOAT = Float.MIN_VALUE;
    
    private int _geoNameId;
    private int _registeredCountryGeoNameId;
    private int _representedCountryGeoNameId;
    private long _postalCode;
    private float _latitude;
    private float _longitude;
    private float _radius;

    private Integer canonicalize(final int val) {
        return val == MISSING_INT ? null : Integer.valueOf(val);
    }

    private Float canonicalize(final float val) {
        return val == MISSING_FLOAT ? null : Float.valueOf(val);
    }

    long rawPostal() {
        return _postalCode;
    }

    float rawLatitude() {
        return _latitude;
    }

    float rawLongitude() {
        return _longitude;
    }

    float rawRadius() {
        return _radius;
    }

    String getPostalCode() {
        return PostalCode.decode(_postalCode).toString();
    }

    Float getLatitude() {
        return canonicalize(_latitude);
    }

    Float getLongitude() {
        return canonicalize(_longitude);
    }

    Float getRadius() {
        return canonicalize(_radius);
    }

    @Override
    String toString() {
        return "GeoInfo(_geoNameId: ${_geoNameId}, _registeredCountryGeoNameId: ${_registeredCountryGeoNameId}, " +
            "_representedCountryGeoNameId: ${_representedCountryGeoNameId}, " +
            "_postalCode: ${_postalCode}, _latitude: ${_latitude}, _longitude: ${_longitude}, " +
            "_radius: ${_radius}, postalCode: ${postalCode}, latitude: ${latitude}, " +
            "longitude: ${longitude}, radius: ${radius})";
    }
}
