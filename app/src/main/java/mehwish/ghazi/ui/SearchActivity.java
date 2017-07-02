package mehwish.ghazi.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mehwish.ghazi.R;
import mehwish.ghazi.adapter.FriendsListAdapter;
import mehwish.ghazi.helper.UtilHelpers;
import mehwish.ghazi.model.FriendsListAndRequestModel;
import mehwish.ghazi.model.UserAccountModel;

public class SearchActivity extends AppCompatActivity implements TextWatcher, AdapterView.OnItemClickListener {

    protected RadioGroup searchTypeRG;
    protected EditText searchBoxEt;
    protected ListView searchResultLv;
    private HashMap<String, HashMap<String, String>> dataList = new HashMap<>();
    private List<FriendsListAndRequestModel> displayedDataList = new ArrayList<>();
    private List<FriendsListAndRequestModel> storedDataList = new ArrayList<>();
    private FriendsListAdapter adapter;
    int checked = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_search);
        UtilHelpers.showWaitDialog(this, "Loading Data", "please wait...");
        initView();
        DatabaseReference mRef = FirebaseDatabase.getInstance().
                getReferenceFromUrl("https://friendsband-a3dc9.firebaseio.com/root/userData");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UtilHelpers.dismissWaitDialog();
                try {
                    dataList = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                    storedDataList = convertList(dataList);
                }catch (ClassCastException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                UtilHelpers.dismissWaitDialog();
            }
        });
    }

    private List<FriendsListAndRequestModel> convertList(HashMap<String, HashMap<String, String>> inputList){
        List<FriendsListAndRequestModel> outputList = new ArrayList<>();
        for(HashMap.Entry<String, HashMap<String, String>> entry : inputList.entrySet()){
            HashMap<String, String> userData = entry.getValue();
            outputList.add(new FriendsListAndRequestModel(1, userData.get("firstName") + userData.get("lastName"), userData.get("mobileNo"), userData.get("email")));
        }
        return outputList;
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        searchTypeRG = (RadioGroup) findViewById(R.id.search_type_rg);
        searchBoxEt = (EditText) findViewById(R.id.search_box_et);
        searchResultLv = (ListView) findViewById(R.id.search_result_lv);
        adapter = new FriendsListAdapter(this, displayedDataList);
        searchResultLv.setAdapter(adapter);
        searchResultLv.setOnItemClickListener(this);
        searchBoxEt.addTextChangedListener(SearchActivity.this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        checked = searchTypeRG.getCheckedRadioButtonId();
        displayedDataList.clear();
        if(TextUtils.isEmpty(s)){
            displayedDataList.clear();
            adapter.notifyDataSetChanged();
        }
        String query = s.toString().toLowerCase();
        switch (checked){
            case R.id.mobile_no_rb:
                for(int i = 0; i < storedDataList.size(); i++){
                    String match = storedDataList.get(i).getFriendContactNo();
                    if(match.startsWith(query) || match.contains(query)){
                        displayedDataList.add(storedDataList.get(i));
                    }
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.name_rb:
                for(int i = 0; i < storedDataList.size(); i++){
                    String match = storedDataList.get(i).getFriendName().toLowerCase();
                    if(match.startsWith(query) || match.contains(query)){
                        displayedDataList.add(storedDataList.get(i));
                    }
                }
                adapter.notifyDataSetChanged();
                break;
            case -1:
                UtilHelpers.showAlertDialog(this, "Search Criteria", "please select a search criteria...");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FriendsListAndRequestModel model = displayedDataList.get(position);
        String email = model.getFriendEmail().replace(".", "_");
        DatabaseReference reqRef = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://friendsband-a3dc9.firebaseio.com/root/requestRecords/" + email);
        HashMap<String, String> f= new HashMap<>();
        f.put(UtilHelpers.getLoggedInUser().getEmail(), "true");
        reqRef.setValue(f);
        Toast.makeText(this, "Request Sent!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
