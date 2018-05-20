package com.a4586.primo.primoscoutingapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity implements ListView.OnItemClickListener, View.OnClickListener {

    FirebaseFirestore database = FirebaseFirestore.getInstance(); // Connection to FireBase

    Context context; // Context

    Menu mainMenu = null; // Menu

    // Music
    Intent musicService;
    private boolean mIsBound = false;
    private MusicThread mServ;
    boolean pauseMusic = true;
    private ServiceConnection Scon = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicThread.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    // UI
    TextView titleTV;
    ListView resultListView;
    Button backBtn;


    ArrayList<String> viewList; // The list shown in the list view
    ArrayList<Team> teams; // Array list of all teams
    ArrayAdapter adapter; // Adapter for the list view
    String viewLevel; // The level of view in currently
    ArrayList<GameComment> comments; // Array list of game comments
    String team; // The team viewed
    int teamPos; // Position in teams list
    String type; // Type of info needed (Pit, Game or Comments)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        context = this; // This screen

        //Music handle
        musicService = new Intent();
        mServ = new MusicThread();
        doBindService();
        musicService.setClass(this, MusicThread.class);
        startService(musicService);

        // Connection to UI
        titleTV = findViewById(R.id.resultTV);
        resultListView = findViewById(R.id.resultListView);
        backBtn = findViewById(R.id.backBtn);

        type = getIntent().getStringExtra("type"); // Get type from prev activity
        viewLevel = type; // View level is the most basic and is type
        titleTV.setText(type); // Set screen title
        team = getIntent().getStringExtra("team"); // Set team viewed
        comments = new ArrayList<>();

        // Set click listeners
        resultListView.setOnItemClickListener(this);
        backBtn.setOnClickListener(this);

        if (team.equals("")) {
            gameTeamNull(); // Set screen in case of no team
        }

        readData(); // Read Data from FireBase + Set screen for team
    }

    // Creates options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bulletmenu, menu);
        mainMenu = menu;
        return true;
    }

    // Menu press should open 3 dot menu
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            mainMenu.performIdentifierAction(R.id.call, 0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // Menu options click listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.call:
                Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ""));
                startActivity(call);
                break;
            case R.id.exit:
                //Close the app
                finish();
                break;
            case R.id.toggleMusic:
                mServ.toggleMusic();
        }
        return true;
    }

    // Music binder and Unbinder
    private void doBindService() {
        bindService(new Intent(context, MusicThread.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    private void doUnbindService() {
        if (mIsBound) {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    // Back press handle
    @Override
    public void onBackPressed() {
        Intent intent;
        switch (viewLevel) {
            case "game":
                // Exits screen
                intent = new Intent(this, ScoutingChooseActivity.class);
                intent.putExtras(getIntent());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);
                break;
            case "noTeam":
                // Exits screen
                intent = new Intent(this, ScoutingChooseActivity.class);
                intent.putExtras(getIntent());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);
                break;
            case "pit":
                // Exits screen
                intent = new Intent(this, ScoutingChooseActivity.class);
                intent.putExtras(getIntent());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);
                break;
            case "comments":
                // Exits screen
                intent = new Intent(this, ScoutingChooseActivity.class);
                intent.putExtras(getIntent());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);
                break;
            case "gameAvg":
                gameTeamNull(); // Set screen in case of no team
                break;
            case "gamesAutoTeam":
                gameTeamNonNull(); // Set screen in case of team for game info
                break;
            case "gamesRestTeam":
                gameTeamNonNull(); // Set screen in case of team for game info
                break;
            case "gameAuto":
                // Set screen for team games for auto screen
                titleTV.setText(team + " אוטונומי ");
                teamGames();
                viewLevel = "gamesAutoTeam";
                break;
            case "gameRest":
                // Set screen for team games for rest of game screen
                titleTV.setText(" שאר המשחק " + team);
                teamGames();
                viewLevel = "gamesRestTeam";
                break;
            case "pitRobot":
                pitMainTable(); // Sets screen for basic pit info
                break;
            case "pitFunctinalities":
                pitMainTable(); // Sets screen for basic pit info
                break;
            case "pitAutonomus":
                pitMainTable(); // Sets screen for basic pit info
                break;
        }
    }

    // Music handle with activity
    @Override
    public void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (pauseMusic) {
            mServ.stopMusic();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mServ.startMusic();
        doBindService();
    }

    // Set screen in case of team for game info
    private void gameTeamNonNull() {
        viewLevel = type;
        if (!team.equals("")) {
            boolean hasTeam = false;
            for (int i = 0; i < teams.size(); i++) {
                if (teams.get(i).getTeamNumber().equals(team)) {
                    teamPos = i;
                    hasTeam = true;
                    break;
                }
            }
            if (hasTeam) {
                titleTV.setText(teams.get(teamPos).getTeamNumber() + " - " + teams.get(teamPos).getTeamName());
                viewList = new ArrayList<>();
                viewList.add("אוטונומי");
                viewList.add("שאר המשחק");
                adapter = new ArrayAdapter(ResultsActivity.this, android.R.layout.simple_list_item_1, viewList);
                resultListView.setAdapter(adapter);
            } else {
                viewLevel = "noTeam";
                viewList = new ArrayList<>();
                viewList.add("אין קבוצה כזאת");
                adapter = new ArrayAdapter(ResultsActivity.this, android.R.layout.simple_list_item_1, viewList);
                resultListView.setAdapter(adapter);
                Log.d("teams", teams.size() + " size");
            }
        }
    }

    // Set screen in case of no team
    private void gameTeamNull() {
        titleTV.setText(type);
        viewLevel = type;
        viewList = new ArrayList<>();
        viewList.add("סקייל");
        viewList.add("סוויץ'");
        viewList.add("אקסצ'יינג'");
        viewList.add("תלייה");
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList);
        resultListView.setAdapter(adapter);
    }

    // Sort teams that climb by climb percentage in games
    private void gameAvgClimb() {
        ArrayList<Team> tempTeams = new ArrayList();
        for (Team t : teams) {
            if (t.getClimbs().equals("true")) {
                tempTeams.add(t);
            }


        }

        for (int i = (tempTeams.size() - 1); i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (tempTeams.get(j - 1).getAvgClimb() < tempTeams.get(j).getAvgClimb()) {
                    tempTeams.add(j - 1, tempTeams.remove(j));
                }
            }
        }

        viewList = new ArrayList<>();
        for (Team t : tempTeams) {
            viewList.add(t.getTeamNumber() + " - " + (t.getAvgClimb() * 100) + "% - " + t.getTimesClimbed());
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList);
        resultListView.setAdapter(adapter);

    }

    // Sort teams that put in scale by average cubes in scale per game
    private void gameAvgScale() {
        ArrayList<Team> tempTeams = new ArrayList();
        for (Team t : teams) {
            if (t.getCubesAt().contains("סקייל")) {
                tempTeams.add(t);
            }


        }

        for (int i = (tempTeams.size() - 1); i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (tempTeams.get(j - 1).getAvgScale() < tempTeams.get(j).getAvgScale()) {
                    tempTeams.add(j - 1, tempTeams.remove(j));
                }
            }
        }

        viewList = new ArrayList<>();
        for (Team t : tempTeams) {
            viewList.add(t.getTeamNumber() + " - " + (t.getAvgScale()) + " - " + t.getCubesScale());
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList);
        resultListView.setAdapter(adapter);

    }

    // Sort teams that put in switch by average cubes in switch per game
    private void gameAvgSwitch() {
        ArrayList<Team> tempTeams = new ArrayList();
        for (Team t : teams) {
            if (t.getCubesAt().contains("סוויץ'")) {
                tempTeams.add(t);
            }


        }

        for (int i = (tempTeams.size() - 1); i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (tempTeams.get(j - 1).getAvgSwitch() < tempTeams.get(j).getAvgSwitch()) {
                    tempTeams.add(j - 1, tempTeams.remove(j));
                }
            }
        }

        viewList = new ArrayList<>();
        for (Team t : tempTeams) {
            viewList.add(t.getTeamNumber() + " - " + (t.getAvgSwitch()) + " - " + t.getCubesSwitch());
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList);
        resultListView.setAdapter(adapter);

    }

    // Sort teams that put in vault by average cubes in vault per game
    private void gameAvgVault() {
        ArrayList<Team> tempTeams = new ArrayList();
        for (Team t : teams) {
            if (t.getCubesAt().contains("אקסציינג'")) {
                tempTeams.add(t);
            }


        }

        for (int i = (tempTeams.size() - 1); i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (tempTeams.get(j - 1).getAvgVault() < tempTeams.get(j).getAvgVault()) {
                    tempTeams.add(j - 1, tempTeams.remove(j));
                }
            }
        }

        viewList = new ArrayList<>();
        for (Team t : tempTeams) {
            viewList.add(t.getTeamNumber() + " - " + (t.getAvgVault()) + " - " + t.getCubesVault());
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList);
        resultListView.setAdapter(adapter);

    }

    // List view items click listener
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        if (type.equals("game")) {
            if (viewLevel.equals("game")) {
                if (team.equals("")) {
                    viewLevel = "gameAvg";
                    if (i == 3) {
                        // Sort teams that climb by climb percentage in games
                        titleTV.setText("טיפוס");
                        gameAvgClimb();
                    } else if (i == 0) {
                        // Sort teams that put in scale by average cubes in scale per game
                        titleTV.setText("סקייל");
                        gameAvgScale();
                    } else if (i == 1) {
                        // Sort teams that put in switch by average cubes in switch per game
                        titleTV.setText("סוויץ'");
                        gameAvgSwitch();
                    } else if (i == 2) {
                        // Sort teams that put in vault by average cubes in vault per game
                        titleTV.setText("אקסצ'יינג'");
                        gameAvgVault();
                    }
                } else {
                    if (i == 0) {
                        // Set screen for team games for auto screen
                        viewLevel = "gamesAutoTeam";
                        titleTV.setText(team + " אוטונומי ");
                    } else {
                        // Set screen for team games for rest of game screen
                        viewLevel = "gamesRestTeam";
                        titleTV.setText(" שאר המשחק " + team);
                    }
                    teamGames();
                }
            } else if (viewLevel.equals("gamesAutoTeam")) {
                // Shows auto period info of team from game
                titleTV.setText(team + " auto " + teams.get(teamPos).getGames().get(i));
                viewLevel = "gameAuto";
                teamGameAuto(i);

            } else if (viewLevel.equals("gamesRestTeam")) {
                // Shows rest of the info of team from game
                titleTV.setText(team + " game " + teams.get(teamPos).getGames().get(i));
                viewLevel = "gameRest";
                teamGameRest(i);
            }
        } else if (type.equals("pit")) {
            if (viewLevel.equals("pit")) {
                if (i == 0) {
                    // Shows pit form information on the robot
                    titleTV.setText(team + " Robot ");
                    viewLevel = "pitRobot";
                    teamPitRobot();
                } else if (i == 1) {
                    // Shows pit form information on the robot functions
                    titleTV.setText(team + " functions ");
                    viewLevel = "pitFunctinalities";
                    teamPitFunctionality();
                } else if (i == 2) {
                    // Shows pit form information on the robot autonomous period
                    titleTV.setText(team + " autonomous ");
                    viewLevel = "pitAutonomus";
                    teamPitAutonomus();
                }
            }

        }


    }

    // Shows auto period info of team from game
    private void teamGameAuto(int position) {
        viewList = new ArrayList<>();
        viewList.add("Scale " + teams.get(teamPos).getAutoScaleCubes().get(position));
        viewList.add("Switch " + teams.get(teamPos).getAutoSwitchCubes().get(position));
        viewList.add("Passes auto line " + teams.get(teamPos).getAutoLinePass().get(position));
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList);
        resultListView.setAdapter(adapter);
    }

    // Shows rest of the info of team from game
    private void teamGameRest(int position) {
        viewList = new ArrayList<>();
        viewList.add("Scale " + teams.get(teamPos).getPutScale().get(position));
        viewList.add("Switch " + teams.get(teamPos).getPutSwitch().get(position));
        viewList.add("Exchange " + teams.get(teamPos).getPutVault().get(position));
        viewList.add("Floor pickup " + teams.get(teamPos).getPickFloor().get(position));
        viewList.add("Feeder pickup " + teams.get(teamPos).getPickFeeder().get(position));
        viewList.add("Climbed " + teams.get(teamPos).getDidClimb().get(position));
        viewList.add("Helped climb " + teams.get(teamPos).getHelpedClimb().get(position));
        viewList.add("Role " + teams.get(teamPos).getGameRole().get(position));
        viewList.add("Crashed " + teams.get(teamPos).getCrashed().get(position));
        viewList.add("Comments " + teams.get(teamPos).getGameComments().get(position));
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList);
        resultListView.setAdapter(adapter);
    }

    // Shows pit form information on the robot
    private void teamPitRobot() {
        viewList = new ArrayList<>();
        viewList.add("General Strat: " + teams.get(teamPos).getRobotRole());
        viewList.add("General Strat Comment: " + teams.get(teamPos).getRoleComment());
        viewList.add("Driving System" + teams.get(teamPos).getDrivingSystem());
        viewList.add("Wheel Type: " + teams.get(teamPos).getWheelType());
        viewList.add("Robots Weight: " + teams.get(teamPos).getRobotMass());
        viewList.add("Potential for Problems: " + teams.get(teamPos).getIssuesPotential());
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList);
        resultListView.setAdapter(adapter);

    }

    // Shows pit form information on the robot functions
    private void teamPitFunctionality() {
        viewList = new ArrayList<>();
        viewList.add("Cubes System: " + teams.get(teamPos).getCubeSystem());
        viewList.add("Does Cubes to: " + teams.get(teamPos).getCubesAt());
        viewList.add("Does Climb: " + teams.get(teamPos).getClimbs());
        viewList.add("Can we Climb on: " + teams.get(teamPos).getHelpsClimb());
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList);
        resultListView.setAdapter(adapter);
    }

    // Shows pit form information on the robot autonomous period
    private void teamPitAutonomus() {
        viewList = new ArrayList<>();
        viewList.add("Does Pass Auto Line: " + teams.get(teamPos).getAutoBaseLine());
        viewList.add("Where Put Cube In Switch while Auto: " + teams.get(teamPos).getAutoSwitch());
        viewList.add("Where put Cube In Scale while Auto: " + teams.get(teamPos).getAutoScale());
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList);
        resultListView.setAdapter(adapter);

    }

    // Set screen for team games
    private void teamGames() {
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, teams.get(teamPos).getGames());
        resultListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (backBtn.getId() == view.getId()) {
            Intent intent;
            switch (viewLevel) {
                case "game":
                    // Exits screen
                    intent = new Intent(this, ScoutingChooseActivity.class);
                    intent.putExtras(getIntent());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pauseMusic = false;
                    startActivity(intent);
                    break;
                case "noTeam":
                    // Exits screen
                    intent = new Intent(this, ScoutingChooseActivity.class);
                    intent.putExtras(getIntent());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pauseMusic = false;
                    startActivity(intent);
                    break;
                case "pit":
                    // Exits screen
                    intent = new Intent(this, ScoutingChooseActivity.class);
                    intent.putExtras(getIntent());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pauseMusic = false;
                    startActivity(intent);
                    break;
                case "comments":
                    // Exits screen
                    intent = new Intent(this, ScoutingChooseActivity.class);
                    intent.putExtras(getIntent());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pauseMusic = false;
                    startActivity(intent);
                    break;
                case "gameAvg":
                    gameTeamNull(); // Set screen in case of no team
                    break;
                case "gamesAutoTeam":
                    gameTeamNonNull(); // Set screen in case of team for game info
                    break;
                case "gamesRestTeam":
                    gameTeamNonNull(); // Set screen in case of team for game info
                    break;
                case "gameAuto":
                    // Set screen for team games for auto screen
                    titleTV.setText(team + " אוטונומי ");
                    teamGames();
                    viewLevel = "gamesAutoTeam";
                    break;
                case "gameRest":
                    // Set screen for team games for rest of game screen
                    titleTV.setText(" שאר המשחק " + team);
                    teamGames();
                    viewLevel = "gamesRestTeam";
                    break;
                case "pitRobot":
                    pitMainTable(); // Sets screen for basic pit info
                    break;
                case "pitFunctinalities":
                    pitMainTable(); // Sets screen for basic pit info
                    break;
                case "pitAutonomus":
                    pitMainTable(); // Sets screen for basic pit info
                    break;
            }

        }
    }

    // Sets screen for basic pit info
    private void pitMainTable() {
        viewLevel = "pit";
        boolean hasTeam = false;
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getTeamNumber().equals(team)) {
                teamPos = i;
                hasTeam = true;
                break;
            }
        }
        if (hasTeam) {
            titleTV.setText(teams.get(teamPos).getTeamNumber() + " - " + teams.get(teamPos).getTeamName());
            viewList = new ArrayList<>();
            viewList.add("Robot");
            viewList.add("Functionality");
            viewList.add("Autonomous");
        } else {
            viewLevel = "noTeam";
            viewList = new ArrayList<>();
            viewList.add("אין קבוצה כזאת");
            Log.d("teams", teams.size() + " size");
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList);
        resultListView.setAdapter(adapter);
    }

    // Shows comments made on the team
    private void teamComment() {
        viewLevel = "comments";
        boolean hasTeam = false;
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getTeamNumber().equals(team)) {
                teamPos = i;
                hasTeam = true;
                break;
            }
        }
        if (hasTeam) {
            titleTV.setText(teams.get(teamPos).getTeamNumber() + " - " + teams.get(teamPos).getTeamName());
            viewList = teams.get(teamPos).getGameComments();
            viewList.addAll(teams.get(teamPos).getComments());
        } else {
            viewLevel = "noTeam";
            viewList = new ArrayList<>();
            viewList.add("אין קבוצה כזאת");
            Log.d("teams", teams.size() + " size");
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList);
        resultListView.setAdapter(adapter);
    }

    // Shows comments made on the game
    private void gameComment() {
        viewLevel = "comments";
        boolean hasGame = false;
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getGameNum().equals(team)) {
                teamPos = i;
                hasGame = true;
                break;
            }
        }
        if (hasGame) {
            titleTV.setText(comments.get(teamPos).getGameNum());
            viewList = new ArrayList<>();
            viewList.add(comments.get(teamPos).getComment());
        } else {
            viewLevel = "noTeam";
            viewList = new ArrayList<>();
            viewList.add("אין הערה למשחק זה");
            Log.d("comments", teams.size() + " size");
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, viewList);
        resultListView.setAdapter(adapter);
    }

    // Read Data from FireBase + Set screen for team
    public void readData() {
        teams = new ArrayList<>();

        database.collection("teams").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                // Reads pit forms from FireBase and adds the teams to teams list
                Log.d("teams", "team" + documentSnapshots.size());
                for (DocumentSnapshot doc : documentSnapshots.getDocuments()) {
                    Team t = new Team(doc.get("number").toString(), doc.get("name").toString());
                    Log.d("teams", t.getTeamNumber());
                    t.setAutoBaseLine(doc.get("auto_line").toString());
                    t.setAutoScale(doc.get("auto_scale").toString());
                    t.setAutoSwitch(doc.get("auto_switch").toString());
                    t.setClimbs(doc.get("does_climb").toString());
                    t.setCubesAt(doc.get("cubes_at").toString());
                    t.setCubeSystem(doc.get("cube_system").toString());
                    t.setDrivingSystem(doc.get("driving_system").toString());
                    t.setGeneralStrategy(doc.get("strategy").toString());
                    t.setHelpsClimb(doc.get("helps_climb").toString());
                    t.setIssuesPotential(doc.get("problems").toString());
                    if (doc.contains("robot_mass")) {
                        t.setRobotMass(doc.get("robot_mass").toString());
                    } else {
                        t.setRobotMass("");
                    }
                    t.setRobotRole(doc.get("role").toString());
                    t.setRoleComment(doc.get("role_comment").toString());
                    t.setWheelType(doc.get("wheel_type").toString());
                    teams.add(t);
                }
                if (type.equals("game")) {
                    gameTeamNonNull(); // Set screen in case of team for game info
                } else if (type.equals("pit")) {
                    pitMainTable(); // Sets screen for basic pit info
                }


            }
        });
        database.collection("games").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                // Reads games form from FireBase + Adds the games to correct team in teams
                Log.d("teams", "game");
                for (DocumentSnapshot doc : documentSnapshots.getDocuments()) {
                    for (int i = 0; i < teams.size(); i++) {
                        if (teams.get(i).getTeamNumber().equals(doc.get("team"))) {
                            teams.get(i).addGame(doc.get("game_number").toString());
                            teams.get(i).addAutoLinePass(doc.get("passed_auto_line").toString());
                            teams.get(i).addAutoScaleCubes(doc.get("auto_scale").toString());
                            teams.get(i).addAutoSwitchCubes(doc.get("auto_switch").toString());
                            teams.get(i).addClimbedFast(doc.get("climbed_fast").toString());
                            teams.get(i).addCrashed(doc.get("did_crash").toString());
                            teams.get(i).addDidClimb(doc.get("did_climb").toString());
                            teams.get(i).addGameComments(doc.get("comments").toString());
                            teams.get(i).addGameRole(doc.get("role").toString());
                            teams.get(i).addHelpedClimb(doc.get("helped_climb").toString());
                            teams.get(i).addPickFeeder(doc.get("pick_feeder").toString());
                            teams.get(i).addPickFloor(doc.get("pick_floor").toString());
                            teams.get(i).addPutScale(doc.get("put_scale").toString());
                            teams.get(i).addPutSwitch(doc.get("put_switch").toString());
                            teams.get(i).addPutVault(doc.get("put_vault").toString());
                            teams.get(i).addReachPlatform(doc.get("reach_platform").toString());
                            teams.get(i).addScouter(doc.get("scouter").toString());
                            teams.get(i).addStartPos(doc.get("start_position").toString());
                            break;
                        }
                    }
                }
            }
        });


        database.collection("comments").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                // Reads comments form from FireBase + Adds the comments to correct team in teams or adds to gameComments list
                Log.d("teams", "comment");
                for (DocumentSnapshot doc : documentSnapshots.getDocuments()) {
                    if (doc.get("team").toString().length() < 4) {
                        comments.add(new GameComment(doc.get("team").toString(), doc.get("comment").toString()));
                    } else {
                        for (int i = 0; i < teams.size(); i++) {
                            if (teams.get(i).getTeamNumber().equals(doc.get("team"))) {
                                teams.get(i).addComments(doc.get("comment").toString());
                                break;
                            }
                        }
                    }

                }
                if (type.equals("comments")) {
                    if (team.length() == 4) {
                        teamComment(); // Shows comments made on the team
                    } else {
                        gameComment(); // Shows comments made on the game
                    }
                }
            }
        });


    }
}
