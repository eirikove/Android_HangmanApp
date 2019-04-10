package eirik.hangmanapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameLogic {


    private char[] englishAlphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private char[] norwegianAlphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Æ', 'Ø', 'Å'};

    private int mistakes = 0;
    private int language = 0;

    private ArrayList<String> norwegianWords = new ArrayList<>(Arrays.asList
            ("LASTEBIL", "RØRLEGGER", "HÅNDGRANAT", "ISKREM", "BRUS", "ØL", "SKY", "LEFSE", "HUND", "ORD MED MELLOMROM"));
    private ArrayList<String> englishWords = new ArrayList<>(Arrays.asList
            ("TEST", "WORDS", "WORD WITH SPACES", "HANGMAN", "ALE", "RICOCHET", "MEDIA", "AWESOME", "THREE", "POSITIVE"));

    private ArrayList<String> usedWords;

    private boolean wordIsGuessed = false;

    public ArrayList<String> selectLanguage(int lang){
        if (lang == 1){
            return norwegianWords;
        }
        else if (lang == 2){
            return englishWords;
        }
        return null;
    }

    public String selectNewWord(ArrayList<String> words, ArrayList<String> usedWords){
        Random r = new Random();
        boolean exit = false;
        boolean wordTaken = false;
        String newWord = "";
        while(!exit) {
            newWord = words.get(r.nextInt(words.size()));
            for (int i = 0; i <= usedWords.size(); i++) {
                if (newWord.equals(usedWords.get(i))) {
                    wordTaken = true;
                    break;
                }
            }
            if (!wordTaken) {
                usedWords.add(newWord);
                exit = true;
            }
        }
        return newWord;
    }

    // Returns data about the word - its length and in which positions it contains spaces.
    public int[] returnWordInfo(String word){
        int[] wordInfo = new int[word.length()];
        for(int i = 0; i < word.length(); i++){
            String letter = Character.toString(word.charAt(i));
            if(letter.equals(" ")){
                wordInfo[i] = 0;
            }else{
                wordInfo[i] = 1;
            }
        }
        return wordInfo;
    }

    public char[] splitWord(String word){
        return word.toCharArray();
    }

    public void tryLetter(char[] word, char input){

    }

    public void paintLines(char[] word){

    }


    public void round(){
        String newWord = selectNewWord(selectLanguage(language), usedWords);
        char[] word = newWord.toCharArray();
        int mistakes = 0;
        char[] display = new char[word.length];

        for(int i = 0; i < word.length; i++){ // Adds spaces to according places
            if(word[i] == ' '){
                display[i] = ' ';
            }
        }

        // paint lines for array "copy", blank lines for spaces


        do {
            // paint image ("hang"+misses)
            boolean hit = false;

            if(hit = false){
                mistakes++;
                String misses = ""+mistakes;
            }
        }while(mistakes < 8);

        if(mistakes == 8){
            // show failed layout
            // start new round
        }

    }





}
