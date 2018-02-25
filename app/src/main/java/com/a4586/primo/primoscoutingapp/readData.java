package com.a4586.primo.primoscoutingapp;

import android.util.Log;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by user on 27/11/2017.
 */


public class readData {
    private static final String SPREADSHEET_URL = "https://spreadsheets.google.com/feeds/spreadsheets/1QByvIKx6I2uvKUyeRKT5OUfFfrbxDczZ-LlGrote1YM";
    private static final String key = "AIzaSyBwjhAyF5zCBggjXlKfI-2lG9eLTvXBSts";
    private static final String client_id = "323330018118-ffkvsmivo8mm5n8orrvmbt7rgh7ovo6g.apps.googleusercontent.com";
    private static final String client_id2 = "297503243419";
    private static final String userName = "yanivgold00@gmail.com";
    private static final String password = "00yanivgold";
    private static ArrayList<String> teams = new ArrayList<>();
    private static GoogleCredential credential = new GoogleCredential();


    public void getData() {
        String status = "";
        int num = 0;
        try {
            /*credential.setAccessToken(client_id);
            num=1;
            /** Our view of Google Spreadsheets as an authenticated Google user. *
            SpreadsheetService service = new SpreadsheetService("primo scouting");
            num=2;
            service.setUserToken(key);
            num=3;
            service.setAuthSubToken(client_id);
            num=4;
            service.setUserToken(client_id);
            num=-1;
//            service.setUserCredentials(userName,password);
            num=5;
            URL metafeedUrl = new URL(SPREADSHEET_URL);

            // Load sheet
            num = 6;
            service.createEntryRequest(metafeedUrl);
            num=7;
            service.setUserToken(credential.getAccessToken());
//            service.getEntry(metafeedUrl,SpreadsheetEntry.class);
            num = 8;
            SpreadsheetEntry spreadsheet = service.getEntry(metafeedUrl,SpreadsheetEntry.class);
            num=9;
            URL listFeedUrl = spreadsheet.getWorksheets().get(0).getListFeedUrl();
            num=10;
            // Print entries
            ListFeed feed = service.getFeed(listFeedUrl, ListFeed.class);
            num=11;
            /*for (ListEntry entry : feed.getEntries()) {
                System.out.println("new row");
                for (String tag : entry.getCustomElements().getTags())) {
                    System.out.println("     " + tag + ": "
                            + entry.getCustomElements().getValue(tag));
                    status=entry.getCustomElements().getValue(tag);
                    }
            }
*/
            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("message");

            myRef.setValue("Hello, World!");
            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                    Log.d(TAG, "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
//            return feed.getEntries().get(0).getCustomElements().getTags().toString();

        } catch (Exception e) {
//            return e.toString()+num;
        }
        //System.out.println(status);
        //return(status);
    }
}