package com.edic.azureadeval.controllers;

import com.edic.azureadeval.models.Answer;
import com.edic.azureadeval.services.EvaluatorService;
import com.edic.azureadeval.services.FileWritingService;
import com.edic.azureadeval.services.JsonParserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    JsonParserService jsonParserService;

    @Autowired
    EvaluatorService evaluatorService;

    @Autowired
    FileWritingService fileWritingService;

    @PostMapping(value = "/post/answers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postAnswers(@RequestBody String body) {

        List<Answer> answersObjectList = jsonParserService.toAnswersList(body);
        String username = jsonParserService.getUsername(body);
        JSONObject jsonResult = evaluatorService.evaluate(answersObjectList, username) ;

        fileWritingService.createAndWriteJson(username, jsonResult);

        return new ResponseEntity<String>("OK", HttpStatus.CREATED);
    }

    @GetMapping(value = "/get/answers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAnswers(@RequestBody String body) {
        return new ResponseEntity<String>("OK", HttpStatus.CREATED);
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> index(@RequestBody String body){
        return new ResponseEntity<String>("OK", HttpStatus.CREATED);
    }
}
