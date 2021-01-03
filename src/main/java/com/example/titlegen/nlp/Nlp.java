package com.example.titlegen.nlp;

import com.example.titlegen.dao.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Nlp extends TitleGenDataAccess {

    public List<String> songTitleGen(int num) {
        List<String> songTitles = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            String songTitle;
            int randomPos = random.nextInt(5);
            switch(randomPos) {
                case 0:
                    songTitle = "The " + findNoun("") + " " + findNoun("NNS");
                    break;
                case 1:
                    songTitle = findVerb("VBG") + " " + findNoun("NNS");
                    break;
                case 2:
                    songTitle = findVerb("") + " " + findPreposition() + " " + findNoun("");
                    break;
                case 3:
                    songTitle = findPronoun("PRP") + " " + findVerb("VBD") + " " + findPronoun("PRP$") + " " + findNoun("NNP");
                    break;
                default:
                    songTitle = findNoun("") + " " + findPreposition() + " " + findPronoun("PRP$") + " " + findNoun("");
                    break;
            }
            songTitles.add(songTitle);
        }
        return songTitles;
    }

    public List<String> startupTitleGen(int num) {
        List<String> startups = new ArrayList<>();

        List<String> vowels = new LinkedList<>();
        vowels.add("a");
        vowels.add("e");
        vowels.add("i");
        vowels.add("o");
        vowels.add("u");

        Random random = new Random();

        for (int i = 0; i < num; i++) {
            String modified = "";
            String name = findStartupVerb();
            int randomStartup = random.nextInt(4);

            switch(randomStartup) {
                case 0:
                    modified += name;
                    modified += ".io";
                    startups.add(modified);
                    break;
                case 1:
                    modified += name;
                    modified += ".ai";
                    startups.add(modified);
                    break;
                case 2:
                    String[] startup = name.split("");
                    String lastLetter = startup[startup.length - 1];
                    if (lastLetter.equals("y")) {
                        modified += name.replace("y", "i");
                    } else if (!vowels.contains(lastLetter)){
                        modified += name + "ify";
                    } else {
                        modified += name + "X";
                    }
                    startups.add(modified);
                    break;
                case 3:
                    StringBuilder c = new StringBuilder();
                    String[] arr = name.split("");
                    boolean stop = false;
                    if (arr.length > 6) {
                        for (int j = arr.length - 1; j >= 0; j--) {
                            if (!stop && vowels.contains(arr[j]) && j != arr.length - 1) {
                                stop = true;
                            } else {
                                c.append(arr[j]);
                            }
                        }
                        modified += c.reverse().toString();
                    } else {
                        c.append("e-");
                        c.append(name);
                        modified += c.toString();
                    }
                    startups.add(modified);
                    break;
            }
        }
        return startups;
    }

    public List<String> bookTitleGen(int num) {
        String booktitle;
        List<String> books = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < num; i++) {
            int randomBook = random.nextInt(4);

            switch(randomBook) {
                case 0:
                    booktitle = findPronoun("PRP$") + " " + findBookNoun("");
                    books.add(booktitle);
                    break;
                case 1:
                    booktitle = findBookNoun("") + " " + findPreposition() + " " + findPronoun("PRP$") + " " + findBookNoun("NNS");
                    books.add(booktitle);
                    break;
                case 2:
                    booktitle = findVerb("VBG") + " " + findPreposition() + " " + findDeterminer() + " " + findBookNoun("NNS");
                    books.add(booktitle);
                    break;
                case 3:
                    booktitle = "The " + findBookNoun("NNP") + "'s " + findBookNoun("");
                    books.add(booktitle);
                    break;
            }

        }
        return books;
    }
}
