package com.felipebrito.facial_detection.services;

import com.felipebrito.facial_detection.models.Person;
import com.felipebrito.facial_detection.repository.PersonRepository;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.springframework.stereotype.Service;

import java.io.File;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

@Service
public class PersonService {
    private PersonRepository personRepository;
    private FaceDetectionService faceDetectionService;

    public PersonService(PersonRepository personRepository, FaceDetectionService faceDetectionService) {
        this.personRepository = personRepository;
        this.faceDetectionService = faceDetectionService;
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void capturePhotos(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        File folder = new File("dataset/" + person.getId());
        if (!folder.exists()) {
            folder.mkdirs();
        }

        VideoCapture camera = new VideoCapture(0);
        if (!camera.isOpened()) {
            throw new RuntimeException("Could not open camera");
        }

        int count = 0;
        Mat frame = new Mat();

        while (count < 30) {
            camera.read(frame);
            if (frame.empty()) continue;
            RectVector faces = faceDetectionService.detectFaces(frame);
            if (faces.size() != 1) continue;
            Rect rect = faces.get(0);
            Mat faceImage = new Mat(frame, rect);
            imwrite("dataset/" + person.getId() + "/" + count + ".jpg", faceImage);
            count++;
        }
        camera.release();
    }
}