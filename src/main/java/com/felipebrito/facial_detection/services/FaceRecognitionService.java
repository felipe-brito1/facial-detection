package com.felipebrito.facial_detection.services;

import com.felipebrito.facial_detection.repository.PersonRepository;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
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

    public FaceRecognitionService(PersonRepository personRepository) {
        this.personRepository = personRepository;
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
}