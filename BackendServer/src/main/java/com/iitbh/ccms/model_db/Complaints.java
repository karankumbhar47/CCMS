package com.iitbh.ccms.model_db;

import com.iitbh.ccms.model.ComplainOverview;
import com.iitbh.ccms.model.ComplainSubmit;
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
public class Complaints {
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

    public ComplainOverview convertToComplainOverview() {
        ComplainOverview overview = new ComplainOverview();
        overview.setComplaintId(this.getComplaintId());
        overview.setComplainerId(this.getComplainerId());
        overview.setDescription(this.getDescription());
        overview.setRegistrationDate(this.getRegistrationDate());
        overview.setResolutionDate(this.getResolutionDate());
        overview.setBuildingName(this.getBuildingName());
        overview.setLocationDetails(this.getLocationDetails());
        overview.setRemarkByUser(this.getRemarkByUser());
        overview.setRemarkByMaintainer(this.getRemarkByMaintainer());
        overview.setAttachmentIds(this.getAttachmentIds());
        overview.setClosureAttachmentIds(this.getClosureAttachmentIds());
        overview.setZone(this.getZone());
        overview.setPriority(getPriorityEnum(this.getPriority()));
        overview.setStatus(getStatusEnum(this.getStatus()));
        overview.setLevel(getLevelEnum(this.getLevel()));
        overview.setComplaintCriteria(this.getComplaintCriteria());
        return overview;
    }


    private static ComplainOverview.PriorityEnum getPriorityEnum(String priority) {
        return ComplainOverview.PriorityEnum.fromValue(priority);
    }

    private static ComplainOverview.StatusEnum getStatusEnum(String status) {
        return ComplainOverview.StatusEnum.fromValue(status);
    }

    private static ComplainOverview.LevelEnum getLevelEnum(String level) {
        return ComplainOverview.LevelEnum.fromValue(level);
    }


    public void convertToComplaints(ComplainSubmit complainSubmit, String ComplaintId) {
        this.setComplaintId(ComplaintId);
        this.setComplainerId(complainSubmit.getComplainerId());
        this.setRegistrationDate(complainSubmit.getRegistrationDate());

        System.out.println(complainSubmit.getZone());
        System.out.println(complainSubmit.getComplaintCriteria());

        this.setZone(complainSubmit.getZone());
        this.setBuildingName(complainSubmit.getBuildingName());
        this.setLocationDetails(complainSubmit.getLocationDetails());
        this.setAttachmentIds(complainSubmit.getAttachmentIds());
        this.setPriority(complainSubmit.getPriority().getValue());
        this.setResolutionDate(complainSubmit.getResolutionDate());
        this.setStatus(complainSubmit.getStatus().getValue());
        this.setRemarkByUser(complainSubmit.getRemarkByUser());
        this.setRemarkByMaintainer(complainSubmit.getRemarkByMaintainer());
        this.setClosureAttachmentIds(complainSubmit.getClosureAttachmentIds());
        this.setLevel(complainSubmit.getLevel().getValue());
        this.setComplainerId(complainSubmit.getComplainerId());
        this.setComplaintCriteria(complainSubmit.getComplaintCriteria());
        this.setDescription(complainSubmit.getDescription());
    }
}
