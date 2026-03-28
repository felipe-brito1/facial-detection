package com.felipebrito.facial_detection.controller;

import com.felipebrito.facial_detection.services.FaceRecognitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recognition")
public class RecognitionController {
    private FaceRecognitionService faceRecognitionService;

    public RecognitionController(FaceRecognitionService faceRecognitionService){
        this.faceRecognitionService = faceRecognitionService;
    }

    @PostMapping("/train")
    ResponseEntity<String> train(){
        faceRecognitionService.train();
        return ResponseEntity.ok("Model trained sucessfully");
    }

    @PostMapping("/recognize")
    ResponseEntity<String> recognize(){
        String name = faceRecognitionService.recognizeFromWebcam();
        return ResponseEntity.ok("Recognized: " + name);

    }


}
