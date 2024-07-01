package com.example.qts.controller;

import com.example.qts.qts.QtsRequestDTO;
import com.example.qts.qts.QtsResponseDTO;
import com.example.qts.service.QtsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qts")
public class QtsController {
    @Autowired
    private QtsService qtsService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<QtsResponseDTO>> getAllQts() {
        List<QtsResponseDTO> qtsList = qtsService.getAllQts();
        return ResponseEntity.ok(qtsList);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/generate")
    public ResponseEntity<Void> generateQts(@RequestBody QtsRequestDTO qtsRequestDTO) {
        qtsService.generateQts(qtsRequestDTO.courseName());
        return ResponseEntity.ok().build();
    }
}