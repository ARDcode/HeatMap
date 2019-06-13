package develop.heatmap;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;
import com.google.gson.Gson;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import develop.heatmap.model.Coordinates;
import develop.heatmap.model.Emotion;
import develop.heatmap.model.EmotionStat;
import develop.heatmap.model.EmotionsTime;
import develop.heatmap.model.Prototype_coordinates;
import develop.heatmap.model.UserStat;
import develop.heatmap.service.CameraService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordActivity extends AppCompatActivity implements Detector.ImageListener  {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1000;
    private static final int DISPLAY_WIDTH = 720;
    private static final int DISPLAY_HEIGHT = 1280;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private static final int REQUEST_PERMISSIONS = 10;

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    public String currentDateandTime;
    private int screenWidth;
    private int screenHeight;
    private ImageView imageView;
    private FrameLayout frameLayout;
    private HeatMap heatMapOverlay;
    private List<WeightedLatLng> data = new ArrayList<>();
    private WebView mWebView;
    private double ins = 0;
    private String prototype_user_id;
    private String prototype_url;
    private String prototype_user_name;
    private String prototype_name;
    private ProgressDialog pd;
    private Handler h;
    private int mScreenDensity;
    private MediaProjectionManager mProjectionManager;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjectionCallback mMediaProjectionCallback;
    private MediaRecorder mMediaRecorder;
    private boolean record = false;
    private Button fab1;
    private ToggleButton mToggleButton;
    private Camera camera;
    private MediaRecorder m_recorder;
    private CameraDevice mCameraDevice;
    private boolean mRecording;
    private boolean mHandlingEvent;
    private CameraDetector detector;
    private SurfaceView cameraView;
    CameraDetector.CameraType cameraType;
    private ArrayList<Coordinates> coordinatesArrayList;
    private ArrayList<EmotionsTime> emotionsTime;
    private ArrayList<UserStat> userStats;
    private long startTime;
    private long stopTime;
    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            mCameraDevice = cameraDevice;
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraDevice.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            cameraDevice.close();
        }
    };
    private ProgressDialog progressDialog;
    private SlidingUpPanelLayout mLayout;
    private String path;

    @Override
    public void onImageResults(List<Face> faces, Frame image, float timestamp) {

        if (faces == null)
            return; //frame was not processed

        if (faces.size() == 0)
            return; //no face found

        //For each face found
        for (int i = 0 ; i < faces.size() ; i++) {
            Face face = faces.get(i);

            int faceId = face.getId();
//
//            //Appearance
            Face.GENDER genderValue = face.appearance.getGender();
            Face.GLASSES glassesValue = face.appearance.getGlasses();
            Face.AGE ageValue = face.appearance.getAge();
            Face.ETHNICITY ethnicityValue = face.appearance.getEthnicity();
            Log.d("genderValue", String.valueOf(genderValue));
            Log.d("ageValue", String.valueOf(ageValue));
            Log.d("ethnicityValue", String.valueOf(ethnicityValue));
//
//
//            //Some Emoji
//            float smiley = face.emojis.getSmiley();
//            float laughing = face.emojis.getLaughing();
//            float wink = face.emojis.getWink();
//
//
            //Some Emotions
//            float joy = face.emotions.getJoy();
//            float anger = face.emotions.getAnger();
//            float disgust = face.emotions.getDisgust();
            String anger = String.valueOf(face.emotions.getAnger());
            String contempt = String.valueOf(face.emotions.getContempt());
            String disgust = String.valueOf(face.emotions.getDisgust());
            String joy = String.valueOf(face.emotions.getJoy());
            String sadness = String.valueOf(face.emotions.getSadness());
            String surprise = String.valueOf(face.emotions.getSurprise());
            EmotionStat emotionStat = new EmotionStat(anger, contempt, disgust, joy, sadness, surprise);
            userStats.add(new UserStat(String.valueOf(genderValue), String.valueOf(ageValue), String.valueOf(ethnicityValue)));
            long currentTime = System.currentTimeMillis();
            long def = currentTime - startTime;

            emotionsTime.add(new EmotionsTime(String.valueOf(def), emotionStat));
//            //Some Expressions
//            float smile = face.expressions.getSmile();
//            float brow_furrow = face.expressions.getBrowFurrow();
//            float brow_raise = face.expressions.getBrowRaise();
//
//            //Measurements
//            float interocular_distance = face.measurements.getInterocularDistance();
//            float yaw = face.measurements.orientation.getYaw();
//            float roll = face.measurements.orientation.getRoll();
//            float pitch = face.measurements.orientation.getPitch();
//
//            //Face feature points coordinates
//            PointF[] points = face.getFacePoints();
//            Log.d("Joy", String.valueOf(joy));
//            Log.d("Anger", String.valueOf(anger));
//            Log.d("Disgust", String.valueOf(disgust));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_slide);
        cameraView = (SurfaceView) findViewById(R.id.camera_preview);
        detector = new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, cameraView, 1, Detector.FaceDetectorMode.LARGE_FACES);
        cameraView.setAlpha(0);
        ViewGroup.LayoutParams params = cameraView.getLayoutParams();
        params.height = 1;
        params.width = 1;
        int rate = 10;
        detector.setMaxProcessRate(rate);
        cameraView.setLayoutParams(params);
        detector.setImageListener(this);
        detector.setDetectAllExpressions(true);
        detector.setDetectAllEmotions(true);
        detector.setDetectAllEmojis(true);
        detector.setDetectAllAppearances(true);
        detector.setMaxProcessRate(2);
        final DBHelperPrototypes db = new DBHelperPrototypes(this);
        mWebView = (WebView) findViewById(R.id.ss);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 6.0.1; ASUS_Z00ED Build/MMB29P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.68 Mobile Safari/537.36");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            prototype_user_id = bundle.getString("prototypeUser_id");
            prototype_url = bundle.getString("prototype_url");
            prototype_user_name = bundle.getString("prototypeUser_name");
            prototype_name = bundle.getString("prototype_name");
        }
        System.out.println("Ссылка:" + prototype_url);
        System.out.println("ID:" + prototype_user_id);
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setFocusable(false);
        mWebView.setFocusableInTouchMode(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //    mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
        //mWebView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
        coordinatesArrayList = new ArrayList<>();
        //mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent event) {
                int x = (int) event.getX();
                int y = mWebView.getScrollY() + (int) event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        db.addPrototype(new Prototype_coordinates(mWebView.getUrl(), prototype_user_id, "0,0;"));
                        coordinatesArrayList.add(new Coordinates(mWebView.getUrl(), x + "," + y + ";", prototype_user_id));
                        try {
                            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                                    "/HeatMap/" + prototype_name + "/" + prototype_user_name + "/";
                            File dir = new File(file_path);
                            if (!dir.exists())
                                dir.mkdirs();
                            File file = new File(dir, db.getId(mWebView.getUrl(), prototype_user_id) + "_screen.jpeg");
                            if (!file.exists()) {
                                file.createNewFile();
                                FileOutputStream fOut = new FileOutputStream(file);
                                View v1 = mWebView;
                                v1.setDrawingCacheEnabled(true);
                                Bitmap screen = Bitmap.createBitmap(v1.getDrawingCache());
                                v1.setDrawingCacheEnabled(false);
                                screen.compress(Bitmap.CompressFormat.JPEG, 30, fOut); // bmp is your Bitmap instance
                                // PNG is a lossless format, the compression factor (100) is ignored
                                fOut.flush();
                                fOut.close();
                                Toast.makeText(getApplicationContext(), "Ready", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {

                        }
                        break;

                    case MotionEvent.ACTION_MOVE:


                    case MotionEvent.ACTION_UP:

                }
                return false;
            }
        });
        mWebView.loadUrl(prototype_url);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

        mMediaRecorder = new MediaRecorder();

        mProjectionManager = (MediaProjectionManager) getSystemService
                (Context.MEDIA_PROJECTION_SERVICE);

        Log.d("fff", "Ready");
        imageView = (ImageView) findViewById(R.id.image);
        fab1 = (Button) findViewById(R.id.fab1);

        mToggleButton = (ToggleButton) findViewById(R.id.toggle);
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                currentDateandTime = sdf.format(new Date());
                if (ContextCompat.checkSelfPermission(RecordActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat
                        .checkSelfPermission(RecordActivity.this,
                                Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale
                            (RecordActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale
                                    (RecordActivity.this, Manifest.permission.RECORD_AUDIO)) {
                        mToggleButton.setChecked(false);
                        Snackbar.make(findViewById(android.R.id.content), R.string.label_permissions,
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(RecordActivity.this,
                                                new String[]{Manifest.permission
                                                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                                                REQUEST_PERMISSIONS);
                                    }
                                }).show();
                    } else {
                        ActivityCompat.requestPermissions(RecordActivity.this,
                                new String[]{Manifest.permission
                                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    onToggleScreenShare(v);
                    if (mRecording) {
                        detector.stop();
                        stopTime = System.currentTimeMillis();
                        stopRecording();
                    } else {
                        detector.start();
                        emotionsTime = new ArrayList<>();
                        userStats = new ArrayList<>();
                        startTime = System.currentTimeMillis();
                        startRecording();
                    }
                }

            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                System.out.println("Size: " + coordinatesArrayList.size());
                if (coordinatesArrayList.size() > 0) {
                    for (Coordinates coord : coordinatesArrayList) {
                        db.updateCoordinates(coord.getUrl(), coord.getCoordinates(), coord.getUser_id());
                    }

                    List<Prototype_coordinates> prototypes = db.getAllPrototypes(prototype_user_id);
                    for (Prototype_coordinates cn : prototypes) {

                        String log = "Id: " + cn.getId() + " ,URL: " + cn.getUrl() + " ,Coordinates: " + cn.getCoodinates() + "UserId" + cn.getUser_id();
                        String[] parts = cn.getCoodinates().split(";");
                        ins = 0;
                        data.clear();
                        for (String coord : parts) {
                            String coords[] = coord.split(",");
                            ins += 1.1;

                            generateHeatMapData(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), ins);

                        }
                        heatMapOverlay = new HeatMap.Builder().weightedData(data).radius(40).width(screenWidth).height(screenHeight).build();
                        heatMapOverlay.setWeightedData(data);
                        imageView.setImageBitmap(heatMapOverlay.generateMap());
                        FileOutputStream fOut = null;
                        try {
                            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                                    "/HeatMap/" + prototype_name + "/" + prototype_user_name + "/";
                            File dir = new File(file_path);
                            if (!dir.exists())
                                dir.mkdirs();
                            File file = new File(dir, cn.getId() + ".jpeg");
                            file.createNewFile();
                            System.out.println("ФАИЛ" + file_path);

                            fOut = new FileOutputStream(file);
                            Bitmap bitmap = BitmapFactory.decodeFile(file_path + cn.getId() + "_screen.jpeg");
                            if (bitmap != null) {
                                overlay(bitmap, heatMapOverlay.generateMap()).compress(Bitmap.CompressFormat.JPEG, 30, fOut); // bmp is your Bitmap instance
                            }
                            Toast.makeText(getApplicationContext(), "Ready", Toast.LENGTH_SHORT).show();
                            FileUploadService service =
                                    UploadServiceGenerator.createService(FileUploadService.class);

                            // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
                            // use the FileUtils to get the actual file by ur

                            // create RequestBody instance from file
                            RequestBody requestFile =
                                    RequestBody.create(
                                            MediaType.parse("application/octet-stream"),
                                            file
                                    );

                            // MultipartBody.Part is used to send also the actual file name
                            MultipartBody.Part body =
                                    MultipartBody.Part.createFormData("imagefile", file.getName(), requestFile);

                            // add another part within the multipart request
                            RequestBody name =
                                    RequestBody.create(
                                            okhttp3.MultipartBody.FORM, file.getName());
                            RequestBody id =
                                    RequestBody.create(
                                            okhttp3.MultipartBody.FORM, String.valueOf(cn.getId()));
                            RequestBody user =
                                    RequestBody.create(
                                            okhttp3.MultipartBody.FORM, cn.getUser_id());
                            RequestBody url =
                                    RequestBody.create(
                                            okhttp3.MultipartBody.FORM, cn.getUrl());
                            RequestBody data =
                                    RequestBody.create(
                                            okhttp3.MultipartBody.FORM, cn.getCoodinates());

                            // finally, execute the request
                            Call<ResponseBody> call = service.uploadImage(name, url, data, body);
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call,
                                                       Response<ResponseBody> response) {
                                    Log.v("Upload", "success");
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.e("Upload error:", t.getMessage());
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (fOut != null) {
                                    fOut.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        System.out.println(log);
                    }
                }
                coordinatesArrayList.clear();
//                progressDialog.dismiss();
            }
        });


        measureScreen();

    }

    private void startRecording() {
        setRecording(true);
        if (!mHandlingEvent) {
            mHandlingEvent = true;

            ResultReceiver receiver = new ResultReceiver(new Handler()) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {

//                    handleStartRecordingResult(resultCode, resultData);
                    mHandlingEvent = false;
                }
            };
//            String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
//                    "/HeatMap/" + prototype_name + "/" + prototype_user_name + "/" + currentDateandTime;

//            CameraService.startToStartRecording(this,
//                    Camera.CameraInfo.CAMERA_FACING_FRONT, receiver, path);
        }
    }

    private void stopRecording() {
        setRecording(false);
        if (!mHandlingEvent) {
            mHandlingEvent = true;

            ResultReceiver receiver = new ResultReceiver(new Handler()) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    setRecording(false);
//                   handleStopRecordingResult(resultCode, resultData);
                    mHandlingEvent = false;
                }
            };
//            CameraService.startToStopRecording(this, receiver);


        }
    }

    private void setRecording(boolean recording) {
        mRecording = recording;
    }

//    private void handleStartRecordingResult(int resultCode, Bundle resultData) {
//        if (resultCode == CameraService.RECORD_RESULT_OK) {
//            Toast.makeText(this, "Start recording...", Toast.LENGTH_SHORT).show();
//        } else {
//            // start recording failed.
//            Toast.makeText(this, "Start recording failed...", Toast.LENGTH_SHORT).show();
//            setRecording(false);
//        }
//    }
//
//    private void handleStopRecordingResult(int resultCode, Bundle resultData) {
//        if (resultCode == CameraService.RECORD_RESULT_OK) {
//            String videoPath = resultData.getString(CameraService.VIDEO_PATH);
//            Toast.makeText(this, "Record succeed, file saved in " + videoPath,
//                    Toast.LENGTH_LONG).show();
//
//        } else if (resultCode == CameraService.RECORD_RESULT_UNSTOPPABLE) {
//            Toast.makeText(this, "Stop recording failed...", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Recording failed...", Toast.LENGTH_SHORT).show();
//            setRecording(true);
//        }
//    }

    private void sendVideoToServer(String videopath){
        FileUploadService service =
                UploadServiceGenerator.createService(FileUploadService.class);
        System.out.println(videopath);
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by ur
        File file = new File(videopath);
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("application/octet-stream"),
                        file
                );
        RequestBody requestJson =
                RequestBody.create(
                        MediaType.parse("application/octet-stream"),
                        file
                );
        Gson gson = new Gson();
        String json = gson.toJson(emotionsTime);
        String jsonUser = gson.toJson(userStats);
        Log.d(TAG, "sendVideoToServer: "+ file.getName());
        Log.d(TAG, "sendVideoToServer: "+ json);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("videofile", file.getName(), requestFile);

        // add another part within the multipart request
        RequestBody name =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, file.getName());
        RequestBody url =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, prototype_url);
        RequestBody emotions =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, json);
        RequestBody userStat =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, jsonUser);
        // finally, execute the request
        Call<ResponseBody> call = service.uploadVideo(name, url, emotions, userStat, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("UploadedVideo", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }
    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.d("CameraInfo", "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    private Bitmap overlay2(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, new Matrix(), null);
        return bmOverlay;
    }

    private Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, new Matrix(), null);
        return bmOverlay;
    }

    private void measureScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    @NonNull
    private List<WeightedLatLng> generateHeatMapData(int x, int y, double inst) {

        data.add(new WeightedLatLng(x,
                y,
                inst));
        return data;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE) {
            Log.e(TAG, "Unknown request code: " + requestCode);
            return;
        }
        if (resultCode != RESULT_OK) {
            Toast.makeText(this,
                    "Screen Cast Permission Denied", Toast.LENGTH_SHORT).show();
            mToggleButton.setChecked(false);
            return;
        }
        mMediaProjectionCallback = new MediaProjectionCallback();
        mMediaProjection = mProjectionManager.getMediaProjection(resultCode, data);
        mMediaProjection.registerCallback(mMediaProjectionCallback, null);
        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();

    }

    public void onToggleScreenShare(View view) {
        if (((ToggleButton) view).isChecked()) {
            initRecorder();
            shareScreen();
        } else {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            Log.v(TAG, "Stopping Recording");
            stopScreenSharing();
            sendVideoToServer(path);
        }
    }

    private void shareScreen() {
        if (mMediaProjection == null) {
            startActivityForResult(mProjectionManager.createScreenCaptureIntent(), REQUEST_CODE);
            return;
        }
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
    }

    private VirtualDisplay createVirtualDisplay() {
        return mMediaProjection.createVirtualDisplay("MainActivity",
                DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mMediaRecorder.getSurface(), null /*Callbacks*/, null
                /*Handler*/);
    }

    private void initRecorder() {
        try {
//            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/HeatMap/" + prototype_name + "/" + prototype_user_name + "/" + currentDateandTime + ".mp4";
            mMediaRecorder.setOutputFile(path);
            System.out.println(path);
            mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setVideoEncodingBitRate(1024 * 1000);
            mMediaRecorder.setVideoFrameRate(30);
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int orientation = ORIENTATIONS.get(rotation + 90);
            mMediaRecorder.setOrientationHint(orientation);
            mMediaRecorder.prepare();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopScreenSharing() {
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
        //mMediaRecorder.release(); //If used: mMediaRecorder object cannot
        // be reused again
        destroyMediaProjection();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyMediaProjection();
    }

    private void destroyMediaProjection() {
        if (mMediaProjection != null) {
            mMediaProjection.unregisterCallback(mMediaProjectionCallback);
            mMediaProjection.stop();
            mMediaProjection = null;
        }
        Log.i(TAG, "MediaProjection Stopped");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if ((grantResults.length > 0) && (grantResults[0] +
                        grantResults[1]) == PackageManager.PERMISSION_GRANTED) {
                    onToggleScreenShare(mToggleButton);
                } else {
                    mToggleButton.setChecked(false);
                    Snackbar.make(findViewById(android.R.id.content), R.string.label_permissions,
                            Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    intent.setData(Uri.parse("package:" + getPackageName()));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(intent);
                                }
                            }).show();
                }
                return;
            }
        }
    }
    private class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            if (mToggleButton.isChecked()) {
                mToggleButton.setChecked(false);
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                setRecording(false);
                Log.v(TAG, "Recording Stopped");
            }
            mMediaProjection = null;
            stopScreenSharing();
        }
    }
}
