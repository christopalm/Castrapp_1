package data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Christopher on 27/03/2017.
 */

public class GroupItem implements Comparable<GroupItem>{
    private String id, name, status, image, profilePic, timeStamp, url;

    public GroupItem(String id, String name, String image, String status,
                     String profilePic, String timeStamp, String url) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.status = status;
        this.profilePic = profilePic;
        this.timeStamp = timeStamp;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImge() {
        return image;
    }

    public void setImge(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int compareTo(GroupItem o){
        try{
            Date parsedDate1 = new Date(Long.parseLong(timeStamp)*1000);
            Date parsedDate2 = new Date(Long.parseLong(o.timeStamp)*1000);

            if(parsedDate1.before(parsedDate2)){
                return 1;
            }
            if(parsedDate1.after(parsedDate2)){
                return -1;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
