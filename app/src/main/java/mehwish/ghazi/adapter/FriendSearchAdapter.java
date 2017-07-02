package mehwish.ghazi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mehwish.ghazi.R;
import mehwish.ghazi.model.FriendsListAndRequestModel;

/**
 * Created by Devprovider on 21/06/2017.
 */

public class FriendSearchAdapter extends ArrayAdapter<FriendsListAndRequestModel> {
    private List<FriendsListAndRequestModel> dataList;
    private Context context;

    public FriendSearchAdapter(Context context, List<FriendsListAndRequestModel> data) {
        super(context, R.layout.friend_list_row_item,data);
        dataList = data;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FriendsListAndRequestModel obj = dataList.get(position);
        FriendsSearchViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new FriendsSearchViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.friend_list_row_item,parent,false);
            viewHolder.friendsImage = (ImageView) convertView.findViewById(R.id.track_friend_image_request);
            viewHolder.friendsName = (TextView) convertView.findViewById(R.id.friend_name_track);
            viewHolder.friendsContactNo = (TextView) convertView.findViewById(R.id.friend_contact_no_track);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (FriendsSearchViewHolder) convertView.getTag();
        }
        if(obj.getFriendImageId()!= 0)
            Picasso.with(context).load(obj.getFriendImageId()).error(R.mipmap.ic_launcher_round)
                    .into(viewHolder.friendsImage);
        viewHolder.friendsName.setText(obj.getFriendName());
        viewHolder.friendsContactNo.setText(obj.getFriendContactNo());
        return convertView;
    }


    public class FriendsSearchViewHolder{
        ImageView friendsImage;
        TextView friendsName;
        TextView friendsContactNo;
    }
}
