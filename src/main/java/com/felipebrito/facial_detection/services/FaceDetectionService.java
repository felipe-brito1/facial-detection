package com.felipebrito.facial_detection.services;

import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

import static org.bytedeco.opencv.global.opencv_imgproc.*;

@Service
public class FaceDetectionService {
    private CascadeClassifier faceDetector;

    public FaceDetectionService() throws URISyntaxException {
        var resource = getClass().getClassLoader().getResource("models/haarcascade_frontalface_default.xml");
        if (resource == null) {
            throw new RuntimeException("Haarcascade file not found");
        }
        String path = new java.io.File(resource.toURI()).getAbsolutePath();
        this.faceDetector = new CascadeClassifier(path);
        if (faceDetector.empty()) {
            throw new RuntimeException("Error loading Haar Cascade");
        }
    }

    public Mat detectAndDraw(Mat frame) {
        Mat gray = new Mat();
        cvtColor(frame, gray, COLOR_BGR2GRAY);
        RectVector faces = new RectVector();
        faceDetector.detectMultiScale(gray, faces);
        for (int i = 0; i < faces.size(); i++) {
            Rect rect = faces.get(i);
            rectangle(frame,
                    new Point(rect.x(), rect.y()),
                    new Point(rect.x() + rect.width(), rect.y() + rect.height()),
                    new Scalar(0, 255, 0, 0), 2, 8, 0);
        }
        putText(frame, "Faces: " + faces.size(), new Point(10, 30),
                FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(0, 255, 0, 0), 2, 8, false);
        return frame;
    }

    public RectVector detectFaces(Mat mat) {
        Mat gray = new Mat();
        cvtColor(mat, gray, COLOR_BGR2GRAY);
        RectVector faces = new RectVector();
        faceDetector.detectMultiScale(gray, faces);
        return faces;
    }
}