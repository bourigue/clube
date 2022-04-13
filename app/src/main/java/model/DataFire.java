package model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DataFire{

    private String userID;
    private FirebaseUser user;
    private DatabaseReference dbRefUsers,dbref,dbRefImagesReviews,dbRefSuggestions,dbConnections,dbChat,dbMessaging,dbRefReport,dbSearch,dbRequests
            ,dbMsgCall, dbFavorite, dbEarning, dbEarningDays,dbConfirmMatch;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private DatabaseReference dbRefSapm;
    private DatabaseReference dbRefInnappriatePhoto;
    private DatabaseReference dbGifts;
    private DatabaseReference dbsend_gift;
    private DatabaseReference dbHistory,dbMatchHistory;
    private DatabaseReference dbVideoCall,dbMessagesReview;


    public DataFire() {

    }

    public DatabaseReference getDbMatchHistory() {
        dbMatchHistory = getDbref().child("match_history");
        return dbMatchHistory;
    }

    public DatabaseReference getDbConfirmMatch() {
        dbConfirmMatch = getDbref().child("matchConfirm");
        return dbConfirmMatch;
    }

    public DatabaseReference getDbEarningDays() {
        dbEarningDays = getDbref().child("earningDays");
        return dbEarningDays;
    }

    public DatabaseReference getDbEarning() {
        dbEarning = getDbref().child("earning");
        return dbEarning;
    }

    public DatabaseReference getDbFavorite() {
        dbFavorite = getDbref().child("favorite");
        return dbFavorite;
    }

    public DatabaseReference getDbref(){
        dbref =  FirebaseDatabase.getInstance().getReference();
        return dbref;
    }

    public FirebaseAuth getmAuth(){
        mAuth = FirebaseAuth.getInstance();
        return mAuth;
    }

    public DatabaseReference getDbRefChat(){
        dbChat = getDbref().child("chat");
        return dbChat;
    }

    public DatabaseReference getDbRefMessagesReview(){
        dbMessagesReview = getDbref().child("messages_reviews");
        return dbMessagesReview;
    }

    public DatabaseReference getDbRefMsgCall(){
        dbMsgCall = getDbref().child("messages_call");
        return dbMsgCall;
    }

    public DatabaseReference getDbRefVideoCall(){
        dbVideoCall = getDbref().child("video_call");
        return dbVideoCall;
    }

    public DatabaseReference getDbRefGifts(){
        dbGifts = getDbref().child("gifts");
        return dbGifts;
    }

    public DatabaseReference getDbRequests(){
        dbRequests = getDbref().child("requests");
        return dbRequests;
    }

    public DatabaseReference getDbHistory(){
        dbHistory = getDbref().child("history");
        return dbHistory;
    }

    public DatabaseReference getDbSendGift(){
        dbsend_gift = getDbref().child("send_gift");
        return dbsend_gift;
    }

    public DatabaseReference getDbRefReport(){
        dbRefReport = FirebaseDatabase.getInstance().getReference().child("report");
        return dbRefReport;
    }

    public DatabaseReference getDbRefSearch(){
        dbSearch= FirebaseDatabase.getInstance().getReference().child("search");
        return dbSearch;
    }

    public DatabaseReference getDbRefReportSpam(){
        dbRefSapm = getDbRefReport().child("spam");
        return dbRefSapm;
    }
    public DatabaseReference getDbRefReportInappropriatePhoto(){
        dbRefInnappriatePhoto = getDbRefReport().child("inappropriate_photo");
        return dbRefInnappriatePhoto;
    }


    public DatabaseReference getDbRefMessages(){
        dbMessaging = getDbref().child("messages");
        return dbMessaging;
    }

    public DatabaseReference getDbRefUsers(){
        dbRefUsers = getDbref().child("users");
        return dbRefUsers;
    }
    public DatabaseReference getdbRefImagesReviews(){
        dbRefImagesReviews = getDbref().child("images_reviews");
        return dbRefImagesReviews;
    }

    public DatabaseReference getdbRefSuggestions(){
        dbRefSuggestions = getDbref().child("suggestions");
        return dbRefSuggestions;
    }

    public DatabaseReference getDbConnections(){
        dbConnections = FirebaseDatabase.getInstance().getReference().child("connections");
        return dbConnections;
    }

    public String getUserID(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        return userID;
    }

    public FirebaseUser getCurrentUser(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        return user;
    }



    public StorageReference getStorageref(){
        storageReference = FirebaseStorage.getInstance().getReference();
        return storageReference;
    }




}
