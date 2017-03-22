package mehwish.ghazi.model;

/**
 * Created by Devprovider on 3/14/2017.
 */

public class FriendsListAndRequestModel {
    private int friendImageId;
    private String friendName;
    private String friendContactNo;

    public FriendsListAndRequestModel() {
    }

    public FriendsListAndRequestModel(int friendImageId, String friendName, String friendContactNo) {
        this.friendImageId = friendImageId;
        this.friendName = friendName;
        this.friendContactNo = friendContactNo;
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
}
