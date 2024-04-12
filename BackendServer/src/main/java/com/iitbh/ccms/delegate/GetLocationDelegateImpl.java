package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.GetLocationApiDelegate;
import com.iitbh.ccms.model.Location;
import com.iitbh.ccms.model_db.LocationDB;
import com.iitbh.ccms.repository.LocationRepository;
import com.iitbh.ccms.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetLocationDelegateImpl implements GetLocationApiDelegate {

    private final LocationRepository locationRepository;

    @Autowired
    public GetLocationDelegateImpl( LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public ResponseEntity<List<Location>> getLocation(){
        List<LocationDB> locationDBList = locationRepository.findAll();
        List<Location> locationList = new ArrayList<>();
        for(LocationDB locationDB : locationDBList){
            Location location =  locationDB.convertToLocation();
            locationList.add(location);
        }
        return ResponseEntity.ok(locationList);
    }
}
