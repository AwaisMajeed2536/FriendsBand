package mehwish.ghazi.model;

/**
 * Created by Devprovider on 3/14/2017.
 */

public class FriendsListAndRequestModel {
    private int friendImageId;
    private String friendName;
    private String friendContactNo;
    private String friendEmail;

    public FriendsListAndRequestModel() {
    }

    public FriendsListAndRequestModel(int friendImageId, String friendName, String friendContactNo, String friendEmail) {
        this.friendImageId = friendImageId;
        this.friendName = friendName;
        this.friendContactNo = friendContactNo;
        this.friendEmail = friendEmail;
    }

    public int getFriendImageId() {
        return friendImageId;
    }

    public void setFriendImageId(int friendImageId) {
        this.friendImageId = friendImageId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendContactNo() {
        return friendContactNo;
    }

    public void setFriendContactNo(String friendContactNo) {
        this.friendContactNo = friendContactNo;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }
}
