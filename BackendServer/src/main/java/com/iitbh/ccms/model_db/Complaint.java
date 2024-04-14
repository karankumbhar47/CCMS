package com.iitbh.ccms.model_db;

import com.iitbh.ccms.model.ComplaintDetails;
import com.iitbh.ccms.model.ComplaintInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="Complaints")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Complaint {
    @Id
    ObjectId objectId;

    private String complaintId;
    private String registrationDate;
    private String zone;
    private String buildingName;
    private String locationDetails;
    private List<String> attachmentIds;
    private String priority;
    private String resolutionDate;
    private String status;
    private String remarkByUser;
    private String remarkByMaintainer;
    private List<String> closureAttachmentIds;
    private String level;
    private String complainerId;
    private String complaintCriteria;
    private String description;
    private List<String> mailIds;

    public ComplaintDetails convertToComplainOverview() {
        ComplaintDetails overview = new ComplaintDetails();
        overview.setComplaintId(this.getComplaintId());
        ComplaintInfo complaintInfo = new ComplaintInfo();
        complaintInfo.setComplainerId(this.getComplainerId());
        complaintInfo.setDescription(this.getDescription());
        complaintInfo.setRegistrationDate(this.getRegistrationDate());
        complaintInfo.setResolutionDate(this.getResolutionDate());
        complaintInfo.setBuildingName(this.getBuildingName());
        complaintInfo.setLocationDetails(this.getLocationDetails());
        complaintInfo.setRemarkByUser(this.getRemarkByUser());
        complaintInfo.setRemarkByMaintainer(this.getRemarkByMaintainer());
        complaintInfo.setAttachmentIds(this.getAttachmentIds());
        complaintInfo.setClosureAttachmentIds(this.getClosureAttachmentIds());
        complaintInfo.setZone(this.getZone());
        complaintInfo.setPriority(getPriorityEnum(this.getPriority()));
        complaintInfo.setStatus(getStatusEnum(this.getStatus()));
        complaintInfo.setLevel(getLevelEnum(this.getLevel()));
        complaintInfo.setComplaintCriteria(this.getComplaintCriteria());
        overview.setComplaintInfo(complaintInfo);
        return overview;
    }


    private static ComplaintInfo.PriorityEnum getPriorityEnum(String priority) {
        return ComplaintInfo.PriorityEnum.fromValue(priority);
    }

    private static ComplaintInfo.StatusEnum getStatusEnum(String status) {
        return ComplaintInfo.StatusEnum.fromValue(status);
    }

    private static ComplaintInfo.LevelEnum getLevelEnum(String level) {
        return ComplaintInfo.LevelEnum.fromValue(level);
    }


    public void convertToComplaints(ComplaintInfo complaintInfo, String ComplaintId) {
        this.setComplaintId(ComplaintId);
        this.setComplainerId(complaintInfo.getComplainerId());
        this.setRegistrationDate(complaintInfo.getRegistrationDate());
        this.setZone(complaintInfo.getZone());
        this.setBuildingName(complaintInfo.getBuildingName());
        this.setLocationDetails(complaintInfo.getLocationDetails());
        this.setAttachmentIds(complaintInfo.getAttachmentIds());
        this.setPriority(complaintInfo.getPriority().getValue());
        this.setResolutionDate(complaintInfo.getResolutionDate());
        this.setStatus(complaintInfo.getStatus().getValue());
        this.setRemarkByUser(complaintInfo.getRemarkByUser());
        this.setRemarkByMaintainer(complaintInfo.getRemarkByMaintainer());
        this.setClosureAttachmentIds(complaintInfo.getClosureAttachmentIds());
        this.setLevel(complaintInfo.getLevel().getValue());
        this.setComplainerId(complaintInfo.getComplainerId());
        this.setComplaintCriteria(complaintInfo.getComplaintCriteria());
        this.setDescription(complaintInfo.getDescription());
    }
}
