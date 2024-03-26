package com.iitbh.ccms.model_db;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.iitbh.ccms.model.Announcement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "announcements")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDoc {
    @Id
    ObjectId _id;
    public Long id;
    public String title;
    public String description;
    public String time;

    public Announcement toApiAnnouncement() {
        Announcement announcement = new Announcement();
        announcement.setId(id);
        announcement.setTitle(title);
        announcement.setDescription(description);
        announcement.setTime(time);

        return announcement;
    }
}
