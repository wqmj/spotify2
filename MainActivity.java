package com.example.caozhenjie.ec601_spotify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.opencv.android.OpenCVLoader;

import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.SurfaceView;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.core.Mat;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;



public class MainActivity extends AppCompatActivity implements CvCameraViewListener2 {


    Mat frame;
    private CameraBridgeViewBase mOpenCvCameraView;



    BaseLoaderCallback mBaseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {

            switch (status){
                case LoaderCallbackInterface.SUCCESS:{
                    Log.i("opencv", "Opencv loaded successfully!");
                    mOpenCvCameraView.enableView();

                }break;
                default:{
                    super.onManagerConnected(status);

                }break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOpenCvCameraView = (CameraBridgeViewBase)findViewById(R.id.MainActivityCameraView);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCameraIndex(1);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_camerview, menu);
//        return true;
//    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("opencv", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mBaseLoaderCallback);
        } else {
            Log.d("opencv", "OpenCV library found inside package. Using it!");
            mBaseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null);
        mOpenCvCameraView.disableView();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onCameraViewStarted(int width, int height) {


    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        frame = inputFrame.rgba();
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGBA2RGB);
        Log.d("cols: ", "" + frame.cols());
        Log.d("rows: ", "" + frame.rows());
        Imgproc.pyrDown(frame, frame, new Size(frame.cols() / 2, frame.rows() / 2));
        Imgproc.pyrDown(frame, frame, new Size(frame.cols() / 2, frame.rows() / 2));
        int finger = detect();
        Log.d("opencv", "finger: " + finger);
        Imgproc.pyrUp(frame, frame, new Size(frame.cols() * 2, frame.rows() * 2));
        Imgproc.pyrUp(frame, frame, new Size(frame.cols()*2 , frame.rows()*2 ));
        return frame;

    }

    public void onDestroy(){
        super.onDestroy();
        if(mOpenCvCameraView != null){
            mOpenCvCameraView.disableView();
        }
    }

    public double myMax(double a,double b,double c){
        double max = a;
        if(b>max){
            max = b;
        }
        if(c>max){
            max = c;
        }
        return max;
    }
    public double myMin(double a,double b,double c){
        double min = a;
        if(b<min){
            min = b;
        }
        if(c<min){
            min = c;
        }
        return min;

    }

//    float distanceP2P(int x1,int x2,int y1, int y2){
//        I);
//        return distance;
//    }

    public int detect(){
        int finger = 0;
        Mat detect = frame.clone();
        for(int i=0;i<frame.rows();i++){
            for(int j=0;j<frame.cols();j++){
                double[] intensity = frame.get(i, j);
                double B = intensity[2];
                double G = intensity[1];
                double R = intensity[0];
                if ((R > 95 && G > 40 && B > 20) &&
                        (myMax(R, G, B) - myMin(R, G, B) > 15)
                        && (R - G > 15) && (R > G) && (R > B)){
                    detect.put(i, j, new double[]{255, 255, 255});
                }
                else{
                    detect.put(i, j, new double[]{0, 0, 0});
                }
            }
        }

        //Find contours of the largest skin area
        // init
        Mat detectContours = new Mat();
        Imgproc.cvtColor(detect, detectContours, Imgproc.COLOR_BGRA2GRAY);
        List<MatOfPoint> contours = new ArrayList<>();

        Mat hierarchy = new Mat();

        Imgproc.findContours(detectContours, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        double maxArea = -1;
        int maxAreaIdx = -1;
        for (int idx = 0; idx < contours.size(); idx++) {
            Mat contour = contours.get(idx);
            double contourarea = Imgproc.contourArea(contour);
            if(contourarea>2000){
                if (contourarea > maxArea) {
                    maxArea = contourarea;
                    maxAreaIdx = idx;

                }
            }
        }
        if(maxAreaIdx != -1){
            Imgproc.drawContours(frame, contours, maxAreaIdx, new Scalar(255, 0, 0),1);

            List<MatOfInt> hull = new ArrayList<MatOfInt>();
            for(int i=0; i < contours.size(); i++){
                hull.add(new MatOfInt());
            }
            for(int i=0; i < contours.size(); i++){
                Imgproc.convexHull(contours.get(i), hull.get(i));
            }

            // Convert MatOfInt to MatOfPoint for drawing convex hull

            // Loop over all contours
            List<Point[]> hullpoints = new ArrayList<Point[]>();
            for(int i=0; i < hull.size(); i++){
                Point[] points = new Point[hull.get(i).rows()];

                // Loop over all points that need to be hulled in current contour
                for(int j=0; j < hull.get(i).rows(); j++){
                    int index = (int)hull.get(i).get(j, 0)[0];
                    points[j] = new Point(contours.get(i).get(index, 0)[0], contours.get(i).get(index, 0)[1]);
                }

                hullpoints.add(points);
            }

            // Convert Point arrays into MatOfPoint
            List<MatOfPoint> hullmop = new ArrayList<MatOfPoint>();
            for(int i=0; i < hullpoints.size(); i++){
                MatOfPoint mop = new MatOfPoint();
                mop.fromArray(hullpoints.get(i));
                hullmop.add(mop);
            }

            Imgproc.drawContours(frame, hullmop,maxAreaIdx, new Scalar(0, 255, 0),1);

            //Get the convexity Defects from convex hull
            MatOfInt4 convexityDefects = new MatOfInt4();
            Imgproc.convexityDefects(contours.get(maxAreaIdx), hull.get(maxAreaIdx), convexityDefects);

            //Convert the MatOfInt4 to MatOfPoint
            ArrayList<Integer> startidx = new ArrayList<Integer>();
            ArrayList<Integer> endidx = new ArrayList<Integer>();
            ArrayList<Integer> faridx = new ArrayList<Integer>();
            ArrayList<Integer> depth = new ArrayList<Integer>();
            List<Integer> cdList = convexityDefects.toList();

            for(int k=0;k<cdList.size();k++){
                int r = k%4;
                int m = k/4;

                //switch (r){
                //	case 0:
                //		int startidx = cdList.get(k);
                //		break;
                if(r == 3 ){
                    int dis = cdList.get(k)/128;
                    if(dis>40){
                        finger++;
                    }
                }
            }
        }

        return finger;

    }
}

