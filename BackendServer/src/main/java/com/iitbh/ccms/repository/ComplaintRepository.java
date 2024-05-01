package com.iitbh.ccms.repository;

import com.iitbh.ccms.model_db.Complaint;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
public interface
ComplaintRepository extends MongoRepository<Complaint, ObjectId> {
    Complaint findByComplaintId(String id);
    Page<Complaint> findComplaintByComplainerId(PageRequest pageRequest, String complainerId);
    long countByComplainerId(String complainerId);
    long countByComplaintCriteriaAndComplaintId(String tag, String dateRegex);
    List<Complaint> findByBuildingNameAndStatus(String buildingName, String status);
    List<Complaint> findByStatus(String status);
    Page<Complaint> findComplaintByBuildingNameAndStatus(String buildingName, String status, PageRequest pageRequest);
    List<Complaint> findComplaintsByStatus(String status);
    long countByStatus(String status);

}
