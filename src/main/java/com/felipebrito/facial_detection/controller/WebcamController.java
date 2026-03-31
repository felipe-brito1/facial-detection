package com.felipebrito.facial_detection.controller;


import com.felipebrito.facial_detection.services.WebcamService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webcam")
public class WebcamController {
    private WebcamService webcamService;


    public WebcamController(WebcamService webcamService){
        this.webcamService = webcamService;
    }
    @Operation(summary = "Abre a webcam e identifica os rostos.")
    @PostMapping("/start")
    ResponseEntity<String> startWebcam(){
        new Thread(webcamService::start).start();
        return ResponseEntity.ok("Webcam started.");
    }
    @Operation(summary = "Fecha a webcam")
    @PostMapping("/stop")
    ResponseEntity<String> stopWebcam(){
        webcamService.stop();
        return ResponseEntity.ok("Webcam stopped");
    }

}
