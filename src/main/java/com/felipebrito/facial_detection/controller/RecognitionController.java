package com.felipebrito.facial_detection.controller;

import com.felipebrito.facial_detection.services.FaceRecognitionService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Treina a webcam para reconhecer os rostos")
    @PostMapping("/train")
    ResponseEntity<String> train(){
        faceRecognitionService.train();
        return ResponseEntity.ok("Model trained sucessfully");
    }

    @Operation(summary = "Reconhece os rostos presentes na webcam")
    @PostMapping("/recognize")
    ResponseEntity<String> recognize(){
        String name = faceRecognitionService.recognizeFromWebcam();
        return ResponseEntity.ok("Recognized: " + name);

    }


}
