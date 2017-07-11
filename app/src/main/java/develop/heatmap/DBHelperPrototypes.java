package develop.heatmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import develop.heatmap.model.Prototype;
import develop.heatmap.model.PrototypeUser;
import develop.heatmap.model.Prototype_coordinates;
import develop.heatmap.model.VideoEmotion;

public class DBHelperPrototypes extends SQLiteOpenHelper implements IDatabaseHandler {
    private static final String LOG_TAG = "myLogs";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TestDBFinal";
    //Prototypes
    private static final String TABLE_PROTOTYPES = "prototypes";
    private static final String KEY_ID = "id";
    private static final String KEY_URL = "url";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE_CREATED = "date_created";
    private static final String KEY_USERS = "users";
    //PrototypeUsers
    private static final String TABLE_PROTOTYPE_USERS = "prototype_users";
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_BIO = "bio";
    private static final String KEY_USER_ADD_DATE = "date_created";
    private static final String KEY_USER_LASTREC = "date_lastrec";
    private static final String KEY_USER_PROT_ID = "prototype_id";
    //PrototypeCoordinates
    private static final String TABLE_HEATMAP = "prototypes_coordinates";
    private static final String KEY_HEAT_ID = "id";
    private static final String KEY_HEAT_URL = "url";
    private static final String KEY_HEATUSER_ID = "user_id";
    private static final String KEY_HEAT_COORDINATES = "coordinates";
    //PrototypeEmotions
    private static final String TABLE_EMOTIONS = "video_emotions";
    private static final String KEY_EMOTION_ID = "id";
    private static final String KEY_EMOTION_NAME  = "video_name";
    private static final String KEY_EMOTION_URL = "video_url";
    private static final String KEY_EMOTION_JSON  = "video_json";
    public DBHelperPrototypes(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");

        String CREATE_PROTOTYPES_TABLE = "CREATE TABLE " + TABLE_PROTOTYPES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_URL + " TEXT," + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE_CREATED + " TEXT,"
                + KEY_USERS + " TEXT"
                + ")";
        String CREATE_PROTOTYPE_USERS_TABLE = "CREATE TABLE " + TABLE_PROTOTYPE_USERS + "("
                + KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_BIO + " TEXT,"
                + KEY_USER_NAME + " TEXT,"
                + KEY_USER_ADD_DATE + " TEXT,"
                + KEY_USER_LASTREC + " TEXT,"
                + KEY_USER_PROT_ID + " INTEGER"
                + ")";
        String CREATE_HEATMAP_TABLE = "CREATE TABLE " + TABLE_HEATMAP + "("
                + KEY_HEAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_HEAT_URL + " TEXT," + KEY_HEATUSER_ID + " TEXT,"
                + KEY_HEAT_COORDINATES + " TEXT DEFAULT ''" + ")";
        String CREATE_EMOTIONS_TABLE = "CREATE TABLE " + TABLE_EMOTIONS + "("
                + KEY_EMOTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_EMOTION_NAME + " TEXT," + KEY_EMOTION_URL + " TEXT,"
                + KEY_EMOTION_JSON + " TEXT DEFAULT ''" + ")";
        db.execSQL(CREATE_PROTOTYPES_TABLE);
        db.execSQL(CREATE_PROTOTYPE_USERS_TABLE);
        db.execSQL(CREATE_HEATMAP_TABLE);
        db.execSQL(CREATE_EMOTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROTOTYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROTOTYPE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEATMAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMOTIONS);
        // create new tables
        onCreate(db);
    }


    @Override
    public void addPrototype(Prototype prototype) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_URL, prototype.getUrl());
        values.put(KEY_NAME, prototype.getName());
        values.put(KEY_DESCRIPTION, prototype.getDescription());
        values.put(KEY_DATE_CREATED, prototype.getDate_created());
        values.put(KEY_USERS, prototype.getUsers());
//        if (getUrlCount(prototype.getUrl()) == 0) {
            db.insert(TABLE_PROTOTYPES, null, values);
//        }
//db.execSQL("INSERT INTO " +  TABLE_PROTOTYPES+"("+KEY_ID+","+KEY_URL+","+KEY_NAME+","+KEY_COORDINATES+")"+" VALUES ( '','"+values.get(KEY_URL)+"','"+values.get(KEY_NAME)+ "','' ) WHERE NOT EXISTS (SELECT * FROM "+ TABLE_PROTOTYPES+" WHERE URL = "+values.get(KEY_URL)+")");
        db.close();
    }

    @Override
    public void addPrototypeUser(PrototypeUser prototypeUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_BIO, prototypeUser.getBio());
        values.put(KEY_USER_NAME, prototypeUser.getName());
        values.put(KEY_USER_ADD_DATE, prototypeUser.getDate_created());
        values.put(KEY_USER_LASTREC, prototypeUser.getLast_addrec());
        values.put(KEY_USER_PROT_ID, prototypeUser.getPrototype_id());
        db.insert(TABLE_PROTOTYPE_USERS, null, values);
//db.execSQL("INSERT INTO " +  TABLE_PROTOTYPES+"("+KEY_ID+","+KEY_URL+","+KEY_NAME+","+KEY_COORDINATES+")"+" VALUES ( '','"+values.get(KEY_URL)+"','"+values.get(KEY_NAME)+ "','' ) WHERE NOT EXISTS (SELECT * FROM "+ TABLE_PROTOTYPES+" WHERE URL = "+values.get(KEY_URL)+")");
        db.close();
    }
    @Override
    public void addVideoEmotion(VideoEmotion videoEmotion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMOTION_NAME, videoEmotion.getName());
        values.put(KEY_EMOTION_URL, videoEmotion.getUrl());
        values.put(KEY_EMOTION_JSON, videoEmotion.getJson());
        db.insert(TABLE_EMOTIONS, null, values);
        db.close();
    }

    @Override
    public String getPrototypeUrl(int id) {
        String selectQuery = "SELECT  * FROM " + TABLE_PROTOTYPES + " WHERE " + KEY_ID + "=" + id;
        String url = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                url = cursor.getString(1);
            } while (cursor.moveToNext());
        }
        return url;
    }

    @Override
    public void addPrototype(Prototype_coordinates prototype_coordinates) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HEAT_URL, prototype_coordinates.getUrl());
        values.put(KEY_HEATUSER_ID, prototype_coordinates.getUser_id());
        if(getUrlCount(prototype_coordinates.getUrl(), prototype_coordinates.getUser_id())==0){
            db.insert(TABLE_HEATMAP, null, values);
        }
       // db.execSQL("INSERT INTO " + TABLE_HEATMAP + "(" + KEY_HEAT_ID + "," + KEY_HEAT_URL + "," + KEY_HEATUSER_ID + "," + KEY_HEAT_COORDINATES + ")" + " VALUES ( '','" + values.get(KEY_HEAT_URL) + "','" + values.get(KEY_HEATUSER_ID) + "','' ) WHERE NOT EXISTS (SELECT * FROM " + TABLE_HEATMAP + " WHERE " + KEY_HEAT_URL + " = " + values.get(KEY_URL) + " AND " + KEY_HEATUSER_ID + " = " + values.get(KEY_HEATUSER_ID) + ")");
//        db.execSQL("INSERT INTO "+ TABLE_HEATMAP+"(id, url, user_id, coordinates)\n" +
//                "SELECT * FROM (SELECT '', '"+values.get(KEY_HEAT_URL)+"', '"+values.get(KEY_HEATUSER_ID)+"', '') AS tmp\n" +
//                "WHERE NOT EXISTS (\n" +
//                "    SELECT * FROM "+TABLE_HEATMAP+" WHERE " + KEY_HEAT_URL + " = '" + values.get(KEY_URL) + "' AND " + KEY_HEATUSER_ID + " = " + values.get(KEY_HEATUSER_ID) +
//                ") LIMIT 1;");
        db.close();
    }

    @Override
    public int getUrlCount(String url, String user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String countQuery = "SELECT * FROM "+TABLE_HEATMAP+" WHERE "+KEY_URL+"='"+url+"' AND "+ KEY_HEATUSER_ID+"='"+user_id+"'";
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();

    }

        @Override
    public Prototype_coordinates getPrototype(int id) {

//        Cursor cursor = db.query(TABLE_HEATMAP, new String[]{KEY_HEAT_ID, KEY_HEAT_URL,
//                        KEY_HEATUSER_ID, KEY_HEAT_COORDINATES}, KEY_ID + "=?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
            String selectQuery = "SELECT  * FROM " + TABLE_HEATMAP+ " WHERE "+KEY_HEAT_ID+"="+id;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            Prototype_coordinates prototype_coordinates = new Prototype_coordinates();;
            if (cursor.moveToFirst()) {
                do {

                    prototype_coordinates.setId(Integer.parseInt(cursor.getString(0)));
                    prototype_coordinates.setUrl(cursor.getString(1));
                    prototype_coordinates.setUser_id(cursor.getString(2));
                    prototype_coordinates.setCoodinates(cursor.getString(3));
                } while (cursor.moveToNext());
            }
//        Prototype_coordinates prototype_coordinates = new Prototype_coordinates(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));

        return prototype_coordinates;
    }

    @Override
    public List<Prototype_coordinates> getAllPrototypes() {
        List<Prototype_coordinates> prototype_list = new ArrayList<Prototype_coordinates>();
        String selectQuery = "SELECT  * FROM " + TABLE_HEATMAP;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Prototype_coordinates prototype_coordinates = new Prototype_coordinates();
                prototype_coordinates.setId(Integer.parseInt(cursor.getString(0)));
                prototype_coordinates.setUrl(cursor.getString(1));
                prototype_coordinates.setUser_id(cursor.getString(2));
                prototype_coordinates.setCoodinates(cursor.getString(3));
                prototype_list.add(prototype_coordinates);
            } while (cursor.moveToNext());
        }

        return prototype_list;
    }
    @Override
    public List<Prototype_coordinates> getAllPrototypes(String id) {
        List<Prototype_coordinates> prototype_list = new ArrayList<Prototype_coordinates>();
        String selectQuery = "SELECT  * FROM " + TABLE_HEATMAP + " WHERE " + KEY_HEATUSER_ID+"='"+id+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Prototype_coordinates prototype_coordinates = new Prototype_coordinates();
                prototype_coordinates.setId(Integer.parseInt(cursor.getString(0)));
                prototype_coordinates.setUrl(cursor.getString(1));
                prototype_coordinates.setUser_id(cursor.getString(2));
                prototype_coordinates.setCoodinates(cursor.getString(3));
                prototype_list.add(prototype_coordinates);
            } while (cursor.moveToNext());
        }

        return prototype_list;
    }


    @Override
    public List<Prototype> getPrototypes() {
        List<Prototype> prototypesList = new ArrayList<Prototype>();
        String selectQuery = "SELECT  * FROM " + TABLE_PROTOTYPES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Prototype prototype = new Prototype();
                prototype.setId(Integer.parseInt(cursor.getString(0)));
                prototype.setUrl(cursor.getString(1));
                prototype.setName(cursor.getString(2));
                prototype.setDescription(cursor.getString(3));
                prototype.setDate_created(cursor.getString(4));
                prototype.setUsers(cursor.getString(5));
                prototypesList.add(prototype);
            } while (cursor.moveToNext());
        }

        return prototypesList;
    }
//    @Override
//    public List<Prototype> getPrototypes(int id) {
//        List<Prototype> prototypesList = new ArrayList<Prototype>();
//        String selectQuery = "SELECT  * FROM " + TABLE_PROTOTYPES + " WHERE "+ ;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Prototype prototype = new Prototype();
//                prototype.setId(Integer.parseInt(cursor.getString(0)));
//                prototype.setUrl(cursor.getString(1));
//                prototype.setName(cursor.getString(2));
//                prototype.setDescription(cursor.getString(3));
//                prototype.setDate_created(cursor.getString(4));
//                prototype.setUsers(cursor.getString(5));
//                prototypesList.add(prototype);
//            } while (cursor.moveToNext());
//        }
//
//        return prototypesList;
//    }

    @Override
    public List<PrototypeUser> getPrototypeUsers(int prototype_id) {
        List<PrototypeUser> prototypeUsersList = new ArrayList<PrototypeUser>();
        String selectQuery = "SELECT  * FROM " + TABLE_PROTOTYPE_USERS + " WHERE " + KEY_USER_PROT_ID + "=" + prototype_id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PrototypeUser prototypeUser = new PrototypeUser();
                prototypeUser.setId(Integer.parseInt(cursor.getString(0)));
                prototypeUser.setBio(cursor.getString(1));
                prototypeUser.setName(cursor.getString(2));
                prototypeUser.setDate_created(cursor.getString(3));
                prototypeUser.setLast_addrec(cursor.getString(4));
                prototypeUser.setPrototype_id(cursor.getInt(5));
                prototypeUsersList.add(prototypeUser);
            } while (cursor.moveToNext());
        }

        return prototypeUsersList;
    }

    @Override
    public int getId(String url, String heatuserid) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_HEATMAP, new String[]{KEY_HEAT_ID, KEY_HEAT_URL,
//                        KEY_HEATUSER_ID, KEY_HEAT_COORDINATES}, KEY_HEAT_URL + "=? AND "+KEY_HEATUSER_ID + "=?",
//                new String[]{String.valueOf(url)},null, null, null, null);
        String countQuery = "SELECT * FROM "+TABLE_HEATMAP+" WHERE "+KEY_HEAT_URL+"='"+url+"' AND "+ KEY_HEATUSER_ID+"='"+heatuserid+"'";
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getInt(0);
    }
    @Override
    public String getName(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_HEATMAP, new String[]{KEY_HEAT_ID, KEY_HEAT_URL,
//                        KEY_HEATUSER_ID, KEY_HEAT_COORDINATES}, KEY_HEAT_URL + "=? AND "+KEY_HEATUSER_ID + "=?",
//                new String[]{String.valueOf(url)},null, null, null, null);
        String countQuery = "SELECT * FROM "+TABLE_PROTOTYPES+" WHERE "+KEY_ID+"='"+id+"'";
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getString(2);
    }

    @Override
    public int updateCoordinates(String url, String coordinates, String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id = getId(url, userid);
        System.out.println(coordinates);
        System.out.println(id);
        ContentValues values = new ContentValues();
        if (getPrototype(id).getCoodinates() == null) {
            values.put(KEY_HEAT_COORDINATES, coordinates);
        } else {
            values.put(KEY_HEAT_COORDINATES, getPrototype(id).getCoodinates() + coordinates);
        }

        return db.update(TABLE_HEATMAP, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
    @Override
    public int updateEmotions(String name, String json) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
            values.put(KEY_EMOTION_JSON, json);

        return db.update(TABLE_EMOTIONS, values, KEY_EMOTION_NAME + " = ?",
                new String[]{name});
    }
    @Override
    public ArrayList<String> getEmotionUrl(String videoName) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_HEATMAP, new String[]{KEY_HEAT_ID, KEY_HEAT_URL,
//                        KEY_HEATUSER_ID, KEY_HEAT_COORDINATES}, KEY_HEAT_URL + "=? AND "+KEY_HEATUSER_ID + "=?",
//                new String[]{String.valueOf(url)},null, null, null, null);
        String countQuery = "SELECT * FROM "+TABLE_EMOTIONS+" WHERE "+KEY_EMOTION_NAME+"='"+videoName+"'";
        Cursor cursor = db.rawQuery(countQuery, null);
        ArrayList<String> arrayList = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if(cursor.getCount()>0){
            arrayList.add(cursor.getString(1));
            arrayList.add(cursor.getString(2));
            arrayList.add(cursor.getString(3));
            return arrayList;
        }else return null;
    }


    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ= '0' WHERE NAME='" + TABLE_PROTOTYPES + "';");
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ= '0' WHERE NAME='" + TABLE_PROTOTYPE_USERS + "';");
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ= '0' WHERE NAME='" + TABLE_HEATMAP + "';");
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ= '0' WHERE NAME='" + TABLE_EMOTIONS + "';");
        db.delete(TABLE_PROTOTYPES, null, null);
        db.delete(TABLE_PROTOTYPE_USERS, null, null);
        db.delete(TABLE_HEATMAP, null, null);
        db.delete(TABLE_EMOTIONS, null, null);
        db.close();
    }
}