package com.felipebrito.facial_detection.services;

import com.felipebrito.facial_detection.models.Person;
import com.felipebrito.facial_detection.repository.PersonRepository;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class PersonService {
    private PersonRepository personRepository;
    private FaceDetectionService faceDetectionService;

    public PersonService(PersonRepository personRepository, FaceDetectionService faceDetectionService){
        this.personRepository = personRepository;
        this.faceDetectionService = faceDetectionService;
    }

    public Person save(Person person){
        return personRepository.save(person);
    }
    public void capturePhotos(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Person not found"));

        File folder = new File("dataset/" + person.getId());
        if(!folder.exists()){
            folder.mkdirs();
        }
        VideoCapture camera = new VideoCapture(0);
        if(!camera.isOpened()){
            throw new RuntimeException("Could not open camera");
        }
        int count = 0;
        Mat frame = new Mat();

        while(count < 30){
            camera.read(frame);
            if(frame.empty()) continue;
            var faces = faceDetectionService.detectFaces(frame);
            if(faces.toArray().length != 1) continue;
            Mat faceImage = new Mat(frame, faces.toArray()[0]);
            Imgcodecs.imwrite("dataset/" + person.getId() + "/" + count + ".jpg", faceImage);
            count++;
            }
        camera.release();
        }

    }


