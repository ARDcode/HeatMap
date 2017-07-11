package develop.heatmap;

class DBHelper{
//    private static final String LOG_TAG = "myLogs";
//    private static final int DATABASE_VERSION = 1;
//    private static final String DATABASE_NAME = "myDB";
//    private static final String TABLE_PROTOTYPES = "prototypes_coordinates";
//    private static final String KEY_ID = "id";
//    private static final String KEY_URL = "url";
//    private static final String KEY_NAME = "name";
//    private static final String KEY_COORDINATES = "coordinates";
//
//    public DBHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        Log.d(LOG_TAG, "--- onCreate database ---");
//
//        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PROTOTYPES + "("
//                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_URL + " TEXT UNIQUE," + KEY_NAME + " TEXT,"
//                + KEY_COORDINATES + " TEXT DEFAULT ''" + ")";
//        db.execSQL(CREATE_CONTACTS_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//
//
//    @Override
//    public void addPrototype(Prototype prototype) {
//
//    }
//
//    @Override
//    public void addPrototype(Prototype_coordinates prototype_coordinates) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_URL, prototype_coordinates.getUrl());
//        values.put(KEY_NAME, prototype_coordinates.getName());
//        if(getUrlCount(prototype_coordinates.getUrl())==0){
//            db.insert(TABLE_PROTOTYPES, null, values);
//        }
////db.execSQL("INSERT INTO " +  TABLE_PROTOTYPES+"("+KEY_ID+","+KEY_URL+","+KEY_NAME+","+KEY_COORDINATES+")"+" VALUES ( '','"+values.get(KEY_URL)+"','"+values.get(KEY_NAME)+ "','' ) WHERE NOT EXISTS (SELECT * FROM "+ TABLE_PROTOTYPES+" WHERE URL = "+values.get(KEY_URL)+")");
//        db.close();
//    }
//    @Override
//    public int getUrlCount(String url){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String countQuery = "SELECT * FROM "+TABLE_PROTOTYPES+" WHERE "+KEY_URL+"='"+url+"'";
//        Cursor cursor = db.rawQuery(countQuery, null);
//
//        return cursor.getCount();
//
//    }
//    @Override
//    public Prototype_coordinates getPrototype(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_PROTOTYPES, new String[]{KEY_ID, KEY_URL,
//                        KEY_NAME, KEY_COORDINATES}, KEY_ID + "=?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//
//        Prototype_coordinates prototype_coordinates = new Prototype_coordinates(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
//
//        return prototype_coordinates;
//    }
//
//    @Override
//    public List<Prototype_coordinates> getAllPrototypes() {
//        List<Prototype_coordinates> contactList = new ArrayList<Prototype_coordinates>();
//        String selectQuery = "SELECT  * FROM " + TABLE_PROTOTYPES;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Prototype_coordinates prototype_coordinates = new Prototype_coordinates();
//                prototype_coordinates.setId(Integer.parseInt(cursor.getString(0)));
//                prototype_coordinates.setUrl(cursor.getString(1));
//                prototype_coordinates.setName(cursor.getString(2));
//                prototype_coordinates.setCoodinates(cursor.getString(3));
//                contactList.add(prototype_coordinates);
//            } while (cursor.moveToNext());
//        }
//
//        return contactList;
//    }
//
//    @Override
//    public List<Prototype> getPrototypes() {
//        return null;
//    }
//
//    @Override
//    public int getId(String url) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_PROTOTYPES, new String[]{KEY_ID, KEY_URL,
//                        KEY_NAME, KEY_COORDINATES}, KEY_URL + "=?",
//                new String[]{String.valueOf(url)}, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return cursor.getInt(0);
//    }
//
//    @Override
//    public int updateCoordinates(String url, String coordinates) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        int id = getId(url);
//        ContentValues values = new ContentValues();
//        if (getPrototype(id).getCoodinates() == null) {
//            values.put(KEY_COORDINATES, coordinates);
//        } else {
//            values.put(KEY_COORDINATES, getPrototype(id).getCoodinates() + coordinates);
//        }
//
//        return db.update(TABLE_PROTOTYPES, values, KEY_ID + " = ?",
//                new String[]{String.valueOf(id)});
//    }
//
//    @Override
//    public void deleteAll() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ= '0' WHERE NAME='" + TABLE_PROTOTYPES + "';");
//        db.delete(TABLE_PROTOTYPES, null, null);
//        db.close();
//    }
}