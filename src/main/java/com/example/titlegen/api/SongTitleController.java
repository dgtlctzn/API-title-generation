package com.example.titlegen.api;

import com.example.titlegen.dao.SongTitleDao;
import com.example.titlegen.model.SongTitle;
//import com.example.titlegen.service.SongTitleService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/api/v1/songtitle")
@RestController
public class SongTitleController {

    @Autowired
    private SongTitleDao songTitledao;

    @PostMapping // Map ONLY POST Requests
    public @ResponseBody String addNewSongTitle (@RequestBody SongTitle songTitle) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        SongTitle n = new SongTitle();
        n.setFirst_half(songTitle.getFirst_half());
        n.setSecond_half(songTitle.getSecond_half());
        songTitledao.save(n);
        return "Saved";
    }
//
//    @Autowired
//    public SongTitleController(SongTitleService songTitleService) {
//        this.songTitleService = songTitleService;
//    }
//
//    @PostMapping
//    public void addSongTitle(@Validated @NonNull @RequestBody SongTitle songTitle) {
//        songTitleService.addSongTitle(songTitle);
//    }
//
//    @GetMapping
//    public List<SongTitle> getAllSongTitles(@RequestParam("id") Optional<UUID> id) {
//        if (id.isPresent()) {
//            List<SongTitle> foundList = new ArrayList<>();
//            SongTitle foundId = songTitleService.getSongTitleById(id.get()).orElse(null);
//            foundList.add(foundId);
//            return foundList;
//        }
//        return songTitleService.getAllSongTitles();
//    }
//
    @GetMapping
    public List<SongTitle> getSongTitleById(@RequestParam("id") Optional<Integer> id) {
        if (id.isPresent()) {
            List<SongTitle> foundList = new ArrayList<>();
            Optional<SongTitle> x = songTitledao.findById(id.get());
            if (x.isPresent()) {
                foundList.add(x.get());
                return foundList;
            }
        }
        return (List<SongTitle>) songTitledao.findAll();
    }
//
//    @DeleteMapping(path = "{id}")
//    public void deleteSongTitleById(@PathVariable("id") UUID id) {
//        songTitleService.deleteSongTitle(id);
//    }
//
//    @PutMapping(path = "{id}")
//    public void updateSongTitle(@PathVariable("id") UUID id, @Validated @NonNull @RequestBody SongTitle songTitleToUpdate) {
//        songTitleService.updateSongTitle(id, songTitleToUpdate);
//    }
}