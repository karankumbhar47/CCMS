package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.UpdateLocationApiDelegate;
import com.iitbh.ccms.model.Location;
import com.iitbh.ccms.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UpdateLocationDelegateImpl implements UpdateLocationApiDelegate {

    private final LocationService locationService;

    @Autowired
    public UpdateLocationDelegateImpl(LocationService locationUpdateService){
        this.locationService = locationUpdateService;
    }

    @Override
    public ResponseEntity<Void>  updateLocation(Location location){
       locationService.updateLocationDetails(location.getZoneName(),  location);
       return ResponseEntity.ok().build();
    }
}
