package com.felipebrito.facial_detection.services;

import com.felipebrito.facial_detection.models.Person;
import com.felipebrito.facial_detection.repository.PersonRepository;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.opencv.global.opencv_core.CV_32SC1;
import static org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;

@Service
public class FaceRecognitionService {
    private PersonRepository personRepository;
    private LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();
    private FaceDetectionService faceDetectionService;

    public FaceRecognitionService(PersonRepository personRepository, FaceDetectionService faceDetectionService) {
        this.personRepository = personRepository;
        this.faceDetectionService = faceDetectionService;
    }

    public void train() {
        var persons = personRepository.findAll();
        List<Mat> images = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();
        for (var person : persons) {
            File folder = new File("dataset/" + person.getId());
            if (!folder.exists()) continue;
            for (File file : folder.listFiles()) {
                Mat img = imread(file.getAbsolutePath(), IMREAD_GRAYSCALE);
                images.add(img);
                labels.add(person.getId().intValue());
            }
        }
        MatVector imagesVector = new MatVector(images.toArray(new Mat[0]));
        Mat labelsMatrix = new Mat(labels.size(), 1, CV_32SC1);
        for (int i = 0; i < labels.size(); i++) {
            labelsMatrix.ptr(i).putInt(labels.get(i));
        }
        recognizer.train(imagesVector, labelsMatrix);
        recognizer.save("model.yml");
    }

    public int recognize(Mat face) {
        int[] label = new int[1];
        double[] confidence = new double[1];
        recognizer.predict(face, label, confidence);
        return label[0];
    }
    public String recognizeFromWebcam(){
        VideoCapture camera = new VideoCapture(0);
        if(!camera.isOpened()){
            throw new RuntimeException("Error! The camera was not opened.");
        }
        Mat frame = new Mat();
        camera.read(frame);
        camera.release();
        var faces = faceDetectionService.detectFaces(frame);
        if(faces.size() == 0){
            throw new RuntimeException("No face detected");
        }
        Rect rect = faces.get(0);
        Mat faceImage = new Mat(frame, rect);
        Mat faceGray = new Mat();
        org.bytedeco.opencv.global.opencv_imgproc.cvtColor(faceImage, faceGray, org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2GRAY);
        int id = recognize(faceGray);
        var person = personRepository.findById((long) id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        return person.getName();
    }


}