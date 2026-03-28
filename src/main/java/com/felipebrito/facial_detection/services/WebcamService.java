    package com.felipebrito.facial_detection.services;


    import org.opencv.core.Mat;
    import org.opencv.highgui.HighGui;
    import org.opencv.videoio.VideoCapture;
    import org.opencv.videoio.Videoio;
    import org.springframework.stereotype.Service;

    @Service
    public class WebcamService {
        private FaceDetectionService faceDetectionService;
        private VideoCapture camera = new VideoCapture();
        private boolean running;


        public WebcamService(FaceDetectionService faceDetectionService){
            this.faceDetectionService = faceDetectionService;
        }

        public void start(){
            running = true;
            camera.open(0);
            camera.set(Videoio.CAP_PROP_FRAME_WIDTH, 1280);
            camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, 720);
            if(!camera.isOpened()){
                throw new RuntimeException("Error! The camera was not opened.");
            }
            while(running){
                Mat frame = new Mat();
                camera.read(frame);
                if(frame.empty()){
                    continue;
                }
                Mat result = faceDetectionService.detectAndDraw(frame);
                HighGui.imshow("Facial Detection", frame);
                int key = HighGui.waitKey(30);
                if(key == 27){
                    stop();
                }

            }
        }
        public void stop(){
            running = false;
            camera.release();
            HighGui.destroyAllWindows();
        }

    }
