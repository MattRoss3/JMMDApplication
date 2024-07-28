package com.example.jmmdapplication;

import com.example.jmmdapplication.R;
import com.example.jmmdapplication.databinding.ActivityMainUserInterfaceBinding;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainUserInterface extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.jmmdapplication.MAIN_ACTIVITY_USER_ID";
    static  final  String SHARED_PREFERENCE_USERID_KEY = "com.example.jmmdapplication.SHARED_PREFERENCE_USERID_KEY";
    static  final  String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.jmmdapplication.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;

    private ActivityMainUserInterfaceBinding binding;
    //private [appRepo] repository; //TODO: Repository not yet exists

    //private [appViewModel]ViewModel gymLogViewModel; //TODO: app's ViewModel not yet exists

    public static final String TAG = "MAIN_USER_INTERFACE_TAG";

    private int loggedInUserID = -1;
    //private User user; //TODO: User class not yet exists

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainUserInterfaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //[appViewModel]ViewModel = new ViewModelProvider(this).get(GymLogViewModel.class); //TODO: app's ViewModel not yet exists

        //TODO: RecyclerView / viewHolders
//        RecyclerView recyclerView = binding.logDisplayRecyclerView;
//        final GymLogAdapterOR[AppAdapter] adapter = new AppAdapter(new AppAdapter.GymLogDiffOR[AppDiff]());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //loginUser(); //TODO: implement loginUser() method
        invalidateOptionsMenu();

        if (loggedInUserID == -1) {
            //Intent intent = signInPageActivity.loginIntentFactory(getApplicationContext()); //TODO: implement loginIntentFactory method
            //startActivity(intent);
        }

        //repository = [appRepo].getRepository(getApplication()); //TODO: Repository not yet exists

//        binding.logoutButton.setOnClickListener(new View.OnClickListener() { // UNSURE IF NEEDED
            @Override
            public void onClick(View v) {
                Intent intent= signInPageActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=Settings_page.intentFactory(getApplicationContext());
            }
        });

        //TODO: to scroll
//        binding.logDisplayTextViewOR[layoutXML_TEXTVIEW].setMovementMethod(new ScrollingMovementMethod());
    }

}