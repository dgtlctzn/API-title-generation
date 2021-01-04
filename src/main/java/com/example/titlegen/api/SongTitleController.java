package com.example.titlegen.api;

import com.example.titlegen.model.Titles;
import com.example.titlegen.nlp.Nlp;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.titlegen.format.FormatString;

import java.util.*;

@RequestMapping("/api/v1/titlegen")
@RestController
public class SongTitleController extends Nlp {

    @Value("${spring.datasource.password}")
    private String password;

    @PostMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> addNewSongTitle (@RequestBody Titles titles,
                                                 @RequestHeader Map<String,String> headers) {
        if (!headers.get("authorization").equals(this.password)) {
            FormatString format = new FormatString(true, null, null,"invalid token");
            Gson gson = new Gson();
            String json = gson.toJson(format);
            return new ResponseEntity<>(json, HttpStatus.FORBIDDEN);
        };
        if (headers.containsKey("type") && headers.get("type").equals("song")) {
            // title generation for songs
            String text = titles.getName();
            List<String> namesSaved = saveSongWords(text);

            FormatString format = new FormatString(false, namesSaved.toArray(), "song", "words saved to database");
            Gson gson = new Gson();
            String json = gson.toJson(format);
            return new ResponseEntity<>(json, HttpStatus.OK);

        } else if (headers.containsKey("type") && headers.get("type").equals("startup")) {
            // title generation for startups
            String text = titles.getName();
            List<String> namesSaved = saveStartupWords(text);
            FormatString format = new FormatString(false, namesSaved.toArray(), "startup", "words saved to database");
            Gson gson = new Gson();
            String json = gson.toJson(format);
            return new ResponseEntity<>(json, HttpStatus.OK);

        } else if (headers.containsKey("type") && headers.get("type").equals("book")) {
            // title generation for books
            String text = titles.getName();
            List<String> namesSaved = saveBookWords(text);
            FormatString format = new FormatString(false, namesSaved.toArray(), "book", "words saved to database");
            Gson gson = new Gson();
            String json = gson.toJson(format);
            return new ResponseEntity<>(json, HttpStatus.OK);

        } else {
            FormatString format = new FormatString(true, null, null, "title type not specified in header");
            Gson gson = new Gson();
            String json = gson.toJson(format);
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllSongTitles(@RequestParam HashMap<String, String> params) {
        Gson gson = new Gson();

        int num;
        String type;
        try {
            type = params.get("type");

            if (params.containsKey("no")) {
                // allows for optional number of song titles to be generated
                try {
                    num = Integer.parseInt(params.get("no"));
                    if (num > 15 || num < 1) {
                        // limits song title generation to 15 titles
                        FormatString format = new FormatString(true, null, null, "Results queried must be 15 or less at a time");
                        String json = gson.toJson(format);
                        return new ResponseEntity<>(json, HttpStatus.NOT_ACCEPTABLE);
                    }
                } catch (Exception e) {
                    FormatString format = new FormatString(true, null, null, "invalid param");
                    String json = gson.toJson(format);
                    return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
                }
            } else {
                num = 1;
            }
            FormatString format;
            String json;

            switch(type) {
                case "song":
                    // song title generation
                    List<String> songTitles = songTitleGen(num);
                    format = new FormatString(false, songTitles.toArray(), "song", String.format("%d song title(s) generated", songTitles.size()));
                    json = gson.toJson(format);
                    return new ResponseEntity<>(json, HttpStatus.OK);
                case "startup":
                    // startup title generation
                    List<String> startups = startupTitleGen(num);
                    format = new FormatString(false, startups.toArray(), "startup", String.format("%d startup title(s) generated", startups.size()));
                    json = gson.toJson(format);
                    return new ResponseEntity<>(json, HttpStatus.OK);
                case "book":
                    // book title generation
                    List<String> books = bookTitleGen(num);
                    format = new FormatString(false, books.toArray(), "book", String.format("%d book title(s) generated", books.size()));
                    json = gson.toJson(format);
                    return new ResponseEntity<>(json, HttpStatus.OK);
                default:
                    format = new FormatString(true, null, null, "invalid title type");
                    json = gson.toJson(format);
                    return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
            FormatString format = new FormatString(true, null, null, "missing title type in params");
            String json = gson.toJson(format);
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
    }

}