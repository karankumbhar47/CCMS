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

@Document(collection="complains")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Complains {
    @Id
    ObjectId objectId;

    String complainId;
    long complainerId;
    String complain;
    String dateTime;
    String location;
    String complainerName;
    List<String> tags;
    List<String> fileIds;
    String status;
    String severity;

    public ComplainOverview.StatusEnum getStatus() {
        if(this.status.equalsIgnoreCase("completed")){
            return ComplainOverview.StatusEnum.COMPLETED;
        }
        else if (this.status.equalsIgnoreCase("OnHold")) {
            return ComplainOverview.StatusEnum.ONHOLD;
        }
        else if(this.status.equalsIgnoreCase("InProgress")){
            return ComplainOverview.StatusEnum.INPROGRESS;
        }
        else{
            return ComplainOverview.StatusEnum.UNSEEN;
        }
    }

    public ComplainOverview.SeverityEnum getSeverity() {
        if (severity.equalsIgnoreCase("high")) {
            return ComplainOverview.SeverityEnum.HIGH;
        } else if (severity.equalsIgnoreCase("critical")) {
            return ComplainOverview.SeverityEnum.CRITICAL;
        } else if (severity.equalsIgnoreCase("medium")) {
            return ComplainOverview.SeverityEnum.MEDIUM;
        } else {
            return ComplainOverview.SeverityEnum.LOW;
        }
    }

    public void convertToComplains(ComplainSubmit complainSubmit, String complainId){
        this.setComplainId(complainId);
        this.setFileIds(complainSubmit.getFileIds());
        this.setComplain(complainSubmit.getComplain());
        this.setComplainerName(complainSubmit.getComplainerName());
        this.setComplainerId(complainSubmit.getComplainerId());
        this.setLocation(complainSubmit.getLocation());
        this.setTags(complainSubmit.getTags());
        this.setSeverity(complainSubmit.getSeverity().getValue());
        this.setStatus(complainSubmit.getStatus().getValue());
        this.setDateTime(complainSubmit.getDateTime());
    }

    public ComplainOverview convertToComplainOverView(){
        ComplainOverview complainOverview = new ComplainOverview();
        complainOverview.setFileIds(this.getFileIds());
        complainOverview.setComplainerId(this.getComplainerId());
        complainOverview.setComplainerName(this.getComplainerName());
        complainOverview.setComplain(this.getComplain());
        complainOverview.setComplainId(this.getComplainId());
        complainOverview.setTags(this.getTags());
        complainOverview.setStatus(this.getStatus());
        complainOverview.setSeverity(this.getSeverity());
        complainOverview.setDateTime(this.getDateTime());
        complainOverview.setLocation(this.getLocation());
        return complainOverview;
    }

}
