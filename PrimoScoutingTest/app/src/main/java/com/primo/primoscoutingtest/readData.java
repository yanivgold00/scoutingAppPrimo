package com.primo.primoscoutingtest;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.util.ServiceException;
import com.primo.primoscoutingtest.Team;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by user on 27/11/2017.
 */

public class readData {
    public static final String SPREADSHEET_URL = "https://docs.google.com/spreadsheets/d/1gj2E2RaZw4l5-VL59j4-fhQCl4sCoFh2jx5IRetyhEk";

    private static ArrayList<String> teams = new ArrayList<>();


    public String getData(){
        String status="";

        try{
            /** Our view of Google Spreadsheets as an authenticated Google user. */
            SpreadsheetService service = new SpreadsheetService("Print Google Spreadsheet Demo");


            // Load sheet
            URL metafeedUrl = new URL(SPREADSHEET_URL);
            SpreadsheetEntry spreadsheet = service.getEntry(metafeedUrl,SpreadsheetEntry.class);
            URL listFeedUrl = spreadsheet.getWorksheets().get(0).getListFeedUrl();

            // Print entries
            ListFeed feed = service.getFeed(listFeedUrl, ListFeed.class);

            /*for (ListEntry entry : feed.getEntries()) {
                System.out.println("new row");
                for (String tag : entry.getCustomElements().getTags())) {
                    System.out.println("     " + tag + ": "
                            + entry.getCustomElements().getValue(tag));
                    status=entry.getCustomElements().getValue(tag);
                    }
            }
*/
            return feed.getEntries().get(1).getCustomElements().getTags().toString();




        }catch(Exception e){
            return e.toString();
        }
        //System.out.println(status);
        //return(status);
    }
}