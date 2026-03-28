package com.felipebrito.facial_detection.services;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service
public class FaceDetectionService {
    private CascadeClassifier faceDetector;

    public FaceDetectionService() throws URISyntaxException {
        var resource = getClass().getClassLoader().getResource("models/haarcascade_frontalface_default.xml");
        if (resource == null){
            throw new RuntimeException("Haarcascade file not found");
        }
        String path = new java.io.File(resource.toURI()).getAbsolutePath();
        this.faceDetector = new CascadeClassifier(path);
        boolean test = faceDetector.empty();
        if(test) {
            throw new RuntimeException("Error loading Haar Cascade");
        }
    }
    public Mat detectAndDraw(Mat frame){
        Mat gray = new Mat();
        Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);
        MatOfRect faces = new MatOfRect();
        faceDetector.detectMultiScale(gray, faces);
        for(Rect rect : faces.toArray()){
            Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width,
                            rect.y + rect.height),
                            new Scalar(0, 255, 0), 2);
        }
        return frame;
    }

}
