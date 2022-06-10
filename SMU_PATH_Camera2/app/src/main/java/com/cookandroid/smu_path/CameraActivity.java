package com.cookandroid.smu_path;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.MatOfRect2d;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Rect2d;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.CAMERA;

import static android.os.Environment.getExternalStorageDirectory;

public class CameraActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
    String currentDateandTime = sdf.format(new Date());

    private String fileName = "YOLOv3";

    private static final String TAG = "YOLOv3";

    CameraBridgeViewBase cameraBridgeViewBase;
    BaseLoaderCallback baseLoaderCallback;

    boolean startYolo = false;
    boolean firstTimeYolo = false;

    Net tinyYolo;

    String logInfo;

    int frameCounter = 1;

    String outputFileName = Environment.getExternalStorageDirectory() + "/" +
            Environment.DIRECTORY_DCIM + "/YoloResults/" + currentDateandTime +"_Results" ;

    private static String getPath(String file, Context context) {
        AssetManager assetManager = context.getAssets();
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(assetManager.open(file));
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
            File outFile = new File(context.getFilesDir(), file);
            FileOutputStream os = new FileOutputStream(outFile);
            os.write(data);
            os.close();
            return outFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void YOLO(View Button) {
        if(startYolo == false) {
            startYolo = true;

            if(firstTimeYolo == false) {
                firstTimeYolo = true;
                String tinyYoloCfg = getPath("yolov3.cfg", this);
                String tinyYoloWeights = getPath("yolov3_last.weights", this);

                tinyYolo = Dnn.readNetFromDarknet(tinyYoloCfg, tinyYoloWeights);
            }
        }
        else {
            startYolo = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraBridgeViewBase = (JavaCameraView)findViewById(R.id.CameraView);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);

        baseLoaderCallback = new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                super.onManagerConnected(status);

                switch (status) {
                    case BaseLoaderCallback.SUCCESS:
                        cameraBridgeViewBase.enableView();
                        break;
                    default:
                        super.onManagerConnected(status);
                        break;
                }
            }
        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Writing external memory permission is granted");
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Reading external memory permission is granted");
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }

    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat frame = inputFrame.rgba();

        if (startYolo == true) {
            logInfo = "";

            Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGBA2RGB);

            Mat imageBlob = Dnn.blobFromImage(frame, 0.00392, new Size(416,416),new Scalar(0, 0, 0),/*swapRB*/false, /*crop*/false);

            tinyYolo.setInput(imageBlob);

            java.util.List<Mat> result = new java.util.ArrayList<Mat>(3);

            List<String> outBlobNames = new java.util.ArrayList<>();
            outBlobNames.add(0, "yolo_16");
            outBlobNames.add(1, "yolo_23");
            outBlobNames.add(2, "yolo_30");

            tinyYolo.forward(result, outBlobNames);

            float confThreshold = 0.2f;

            List<Integer> clsIds = new ArrayList<>();
            List<Float> confs = new ArrayList<>();
            List<Rect2d> rects = new ArrayList<>();

            for (int i = 0; i < result.size(); ++i)
            {
                Mat level = result.get(i);
                for (int j = 0; j < level.rows(); ++j)
                {
                    Mat row = level.row(j);
                    Mat scores = row.colRange(5, level.cols());
                    Core.MinMaxLocResult mm = Core.minMaxLoc(scores);
                    float confidence = (float)mm.maxVal;
                    Point classIdPoint = mm.maxLoc;
                    if (confidence > confThreshold)
                    {
                        int centerX = (int)(row.get(0,0)[0] * frame.cols());
                        int centerY = (int)(row.get(0,1)[0] * frame.rows());

                        int width   = (int)(row.get(0,2)[0] * frame.cols());
                        int height  = (int)(row.get(0,3)[0] * frame.rows());

                        int left    = centerX - width  / 2;
                        int top     = centerY - height / 2;

                        clsIds.add((int)classIdPoint.x);
                        confs.add((float)confidence);
                        rects.add(new Rect2d(left, top, width, height));

                    }
                }
            }
            int ArrayLength = confs.size();

            if (ArrayLength>=1) {
                float nmsThresh = 0.2f;
                MatOfFloat confidences = new MatOfFloat(Converters.vector_float_to_Mat(confs));
                Rect2d[] boxesArray = rects.toArray(new Rect2d[0]);
                //MatOfRect boxes = new MatOfRect(boxesArray);
                MatOfInt indices = new MatOfInt();
                MatOfRect2d bbox = new MatOfRect2d();
                //Dnn.NMSBoxes(boxes, confidences, confThreshold, nmsThresh, indices);
                bbox.fromList(rects);
                int[] ind = indices.toArray();

                for (int i = 0; i < ind.length; ++i) {
                    int idx = ind[i];

                    Rect2d box = boxesArray[idx];

                    int idGuy = clsIds.get(idx);

                    float conf = confs.get(idx);

                    List<String> cocoNames = Arrays.asList("Design", "Sports");

                    int intConf = (int) (conf * 100);

                    // The class text
                    Imgproc.putText(frame,cocoNames.get(idGuy) + " " + intConf + "%",box.tl(),Core.FONT_HERSHEY_SIMPLEX, 2, new Scalar(255,255,0),2);

                    // The box.
                    Imgproc.rectangle(frame, box.tl(), box.br(), new Scalar(0, 255, 0), 2);


                    // update the info to log
                    logInfo = logInfo  + Integer.toString(frameCounter) + "," + cocoNames.get(idGuy) + "," + Double.toString(box.x) +
                            "," + Double.toString(box.y) + "," + Double.toString(box.width) + "," + Double.toString(box.height) + "," + conf + "\n";

                    try {

                        FileOutputStream logFile = new FileOutputStream(new File(outputFileName + ".txt"), true);

                        logFile.write((logInfo).getBytes());
                        logFile.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    frameCounter = frameCounter +1;
                }
            }
        }
        return frame;
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        if (startYolo == true){

            String tinyYoloCfg = Environment.getExternalStorageDirectory() + "/assets/yolov3.cfg" ;
            String tinyYoloWeights = Environment.getExternalStorageDirectory() + "/assets/yolov3_last.weights";

            tinyYolo = Dnn.readNetFromDarknet(tinyYoloCfg, tinyYoloWeights);


        }
    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(),"There's a problem, yo!", Toast.LENGTH_SHORT).show();
        }

        else
        {
            baseLoaderCallback.onManagerConnected(baseLoaderCallback.SUCCESS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(cameraBridgeViewBase!=null){
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraBridgeViewBase!=null){
            cameraBridgeViewBase.disableView();
        }
    }
}