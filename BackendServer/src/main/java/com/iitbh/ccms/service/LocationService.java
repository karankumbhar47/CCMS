package com.iitbh.ccms.service;


import com.iitbh.ccms.model.Category;
import com.iitbh.ccms.model.Location;
import com.iitbh.ccms.model_db.LocationDB;
import com.iitbh.ccms.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }

    public void updateLocationDetails(String zoneName, Location locationUpdateRequest) {

        Optional<LocationDB> optionalLocationDB = locationRepository.findByZoneName(zoneName);

        if (optionalLocationDB.isPresent()) {
            LocationDB existingLocation = optionalLocationDB.get();
            existingLocation.setBuildings(locationUpdateRequest.getBuildings());
            List<LocationDB.Category> list = new ArrayList<>();
            for(Category existingCategory : locationUpdateRequest.getCategories()){
                LocationDB.Category  category = new LocationDB.Category();
                category.setName(existingCategory.getName());
                category.setEmails(existingCategory.getEmails());
                list.add(category);
            }
            existingLocation.setCategories(list);

            locationRepository.save(existingLocation);
            System.out.println("Location Updated successfully");
        }else {
            System.out.println("Location with Zone Name " + zoneName + "not found");
        }
    }

}
