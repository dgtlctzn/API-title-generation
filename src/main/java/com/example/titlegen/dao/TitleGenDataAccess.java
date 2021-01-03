package com.example.titlegen.dao;

import com.example.titlegen.model.*;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TitleGenDataAccess {

    @Autowired
    private SongNounDao songNounDao;
    @Autowired
    private VerbDao verbDao;
    @Autowired
    private PronounDao pronounDao;
    @Autowired
    private DeterminerDao determinerDao;
    @Autowired
    private PrepositionDao prepositionDao;
    @Autowired
    private StartupVerbsDao startupVerbsDao;
    @Autowired
    private BookNounDao bookNounDao;

    public Random random = new Random();

    // functions to randomly choose a word from the db by word type
    public String findNoun(String type) {
        Iterable<SongNouns> nouns = songNounDao.findAll();
        List<String> nounList = new ArrayList<>();
        for (SongNouns noun : nouns) {
            if (!type.equals("") && noun.getType().equals(type)) {
                nounList.add(noun.getNoun());
            } else if (type.equals("")) {
                nounList.add(noun.getNoun());
            }
        }
        int nounLength = nounList.size();
        int randNounNum = this.random.nextInt(nounLength);
        return nounList.get(randNounNum);
    }

    public String findBookNoun(String type) {
        Iterable<BookNouns> bookNouns = bookNounDao.findAll();
        List<String> nounList = new ArrayList<>();
        for (BookNouns bookNoun : bookNouns) {
            if (!type.equals("") && bookNoun.getType().equals(type)) {
                nounList.add(bookNoun.getNoun());
            } else if (type.equals("")) {
                nounList.add(bookNoun.getNoun());
            }
        }
        int nounLength = nounList.size();
        int randNounNum = this.random.nextInt(nounLength);
        return nounList.get(randNounNum);
    }

    public String findStartupVerb() {
        Iterable<StartupVerbs> verbs = startupVerbsDao.findAll();
        List<String> verbList = new ArrayList<>();
        for (StartupVerbs verb : verbs) {
            verbList.add(verb.getVerb());
        }
        int verbLength = verbList.size();
        int randVerbNum = random.nextInt(verbLength);
        return verbList.get(randVerbNum);
    }

    public String findVerb(String type) {
        Iterable<Verbs> verbs = verbDao.findAll();
        List<String> verbList = new ArrayList<>();
        for (Verbs verb : verbs) {
            if (!type.equals("") && verb.getType().equals(type)) {
                verbList.add(verb.getVerb());
            } else if (type.equals("")) {
                verbList.add(verb.getVerb());
            }
        }
        int verbLength = verbList.size();
        int randVerbNum = random.nextInt(verbLength);
        return verbList.get(randVerbNum);
    }

    public String findPronoun(String type) {
        Iterable<Pronouns> pronouns = pronounDao.findAll();
        List<String> pronounList = new ArrayList<>();
        for (Pronouns pronoun : pronouns) {
            if (!type.equals("") && pronoun.getType().equals(type)) {
                pronounList.add(pronoun.getPronoun());
            } else if (type.equals("")) {
                pronounList.add(pronoun.getPronoun());
            }
        }
        int pronounLength = pronounList.size();
        int randVerbNum = random.nextInt(pronounLength);
        return pronounList.get(randVerbNum);
    }

    public String findDeterminer() {
        Iterable<Determiners> determiners = determinerDao.findAll();
        int determinerLength = (int)determiners.spliterator().getExactSizeIfKnown();
        int randDeterminerNum = random.nextInt(determinerLength);
        List<String> determinerList = new ArrayList<>();
        for (Determiners determiner : determiners) {
            determinerList.add(determiner.getDeterminer());
        }
        return determinerList.get(randDeterminerNum);
    }

    public String findPreposition() {
        Iterable<Prepositions> prepositions = prepositionDao.findAll();
        int prepositionLength = (int)prepositions.spliterator().getExactSizeIfKnown();
        int randPrepositionNum = random.nextInt(prepositionLength);
        List<String> prepositionList = new ArrayList<>();
        for (Prepositions preposition : prepositions) {
            prepositionList.add(preposition.getPreposition());
        }
        return prepositionList.get(randPrepositionNum);
    }

    public List<String> saveSongWords(String text) {
        Document doc = new Document(text);
        int nounsNum = 0;
        int verbsNum = 0;
        int pronounsNum = 0;
        int determinersNum = 0;
        int prepositionsNum = 0;
        for (Sentence sentence : doc.sentences()) {
            for (int i = 0; i < sentence.length(); i++) {
                String pos = sentence.posTag(i);
                if (pos.contains("NN")) {
                    // save nouns "NN" to noun table
                    SongNouns noun = new SongNouns();
                    noun.setNoun(sentence.word(i));
                    noun.setType(sentence.posTag(i));
                    songNounDao.save(noun);
                    nounsNum += 1;
                } else if (pos.contains("VB")) {
                    // save verbs "VB" to verb table
                    Verbs verb = new Verbs();
                    verb.setVerb(sentence.word(i));
                    verb.setType(sentence.posTag(i));
                    verbDao.save(verb);
                    verbsNum += 1;
                } else if (pos.contains("PRP")) {
                    // save pronouns "PRP" to pronoun table
                    Pronouns pronoun = new Pronouns();
                    pronoun.setPronoun(sentence.word(i));
                    pronounDao.save(pronoun);
                    pronounsNum += 1;
                } else if (pos.contains("DT")) {
                    // save determiners "DT" to determiner table
                    Determiners determiner = new Determiners();
                    determiner.setDeterminer(sentence.word(i));
                    determinerDao.save(determiner);
                    determinersNum += 1;
                } else if (pos.contains("IN")) {
                    // save prepositions "IN" to preposition table
                    Prepositions preposition = new Prepositions();
                    preposition.setPreposition(sentence.word(i));
                    prepositionDao.save(preposition);
                    prepositionsNum += 1;
                }
            }
        }
        // list of word types added to db
        List<String> namesSaved = new ArrayList<>();
        namesSaved.add(nounsNum + " nouns");
        namesSaved.add(verbsNum + " verbs");
        namesSaved.add(pronounsNum + " pronouns");
        namesSaved.add(determinersNum + " determiners");
        namesSaved.add(prepositionsNum + " prepositions");
        return namesSaved;
    }

    public List<String> saveStartupWords(String text) {
        int startupVerbNum = 0;
        Document doc = new Document(text);
        for (Sentence sentence : doc.sentences()) {
            for (int i = 0; i < sentence.length(); i++) {
                StartupVerbs startupVerb = new StartupVerbs();
                startupVerb.setVerb(sentence.word(i));
                startupVerbsDao.save(startupVerb);
                startupVerbNum += 1;
            }
        }
        List<String> namesSaved = new ArrayList<>();
        namesSaved.add(startupVerbNum + " verbs");
        return namesSaved;
    }

    public List<String> saveBookWords(String text) {
        int bookNum = 0;
        Document doc = new Document(text);
        for (Sentence sentence : doc.sentences()) {
            for (int i = 0; i < sentence.length(); i++) {
                String pos = sentence.posTag(i);
                if (pos.contains("NN")) {
                    BookNouns bookNoun = new BookNouns();
                    bookNoun.setNoun(sentence.word(i));
                    bookNoun.setType(pos);
                    bookNounDao.save(bookNoun);
                    bookNum += 1;
                }
            }
        }
        List<String> namesSaved = new ArrayList<>();
        namesSaved.add(bookNum + " nouns");
        return namesSaved;
    }
}
