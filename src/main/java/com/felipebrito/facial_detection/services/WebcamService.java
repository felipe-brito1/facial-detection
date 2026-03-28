package com.felipebrito.facial_detection.services;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.springframework.stereotype.Service;

import static org.bytedeco.opencv.global.opencv_highgui.*;
import static org.bytedeco.opencv.global.opencv_videoio.*;

@Service
public class WebcamService {
    private FaceDetectionService faceDetectionService;
    private VideoCapture camera = new VideoCapture();
    private boolean running;

    public WebcamService(FaceDetectionService faceDetectionService) {
        this.faceDetectionService = faceDetectionService;
    }

    public void start() {
        running = true;
        camera.open(0);
        camera.set(CAP_PROP_FRAME_WIDTH, 1280);
        camera.set(CAP_PROP_FRAME_HEIGHT, 720);
        if (!camera.isOpened()) {
            throw new RuntimeException("Error! The camera was not opened.");
        }
        while (running) {
            Mat frame = new Mat();
            camera.read(frame);
            if (frame.empty()) continue;

            Mat resized = new Mat();
            org.bytedeco.opencv.global.opencv_imgproc.resize(frame, resized,
                    new org.bytedeco.opencv.opencv_core.Size(1280, 720));

            Mat result = faceDetectionService.detectAndDraw(resized);
            imshow("Facial Detection", result);
            int key = waitKey(30);
            if (key == 27) stop();
        }
    }

    public void stop() {
        running = false;
        camera.release();
        destroyAllWindows();
    }
}