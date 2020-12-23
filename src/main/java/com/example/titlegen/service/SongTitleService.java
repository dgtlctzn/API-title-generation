//package com.example.titlegen.service;
//
//import com.example.titlegen.dao.SongTitleDao;
//import com.example.titlegen.model.SongTitle;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class SongTitleService {
//
//    private final SongTitleDao songTitleDao;
//
//    @Autowired
//    public SongTitleService(@Qualifier("mysql") SongTitleDao songTitleDao) {
//        this.songTitleDao = songTitleDao;
//    }
//
//    public int addSongTitle(SongTitle songTitle) {
//        return songTitleDao.insertSongTitle(songTitle);
//    }
//
//    public List<SongTitle> getAllSongTitles() {
//        return songTitleDao.selectAllSongTitles();
//    }
//
//    public Optional<SongTitle> getSongTitleById(UUID id) {
//        return songTitleDao.selectSongTitleById(id);
//    }
//
//    public int deleteSongTitle(UUID id) {
//        return songTitleDao.deleteSongTitleById(id);
//    }
//
//    public int updateSongTitle(UUID id, SongTitle newSongTitle) {
//        return songTitleDao.updateSongTitleById(id, newSongTitle);
//    }
//}