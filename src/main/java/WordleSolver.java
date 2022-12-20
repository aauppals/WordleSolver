import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WordleSolver {

    // possible answer words (from answer.txt)
    public static List<String> answerWordsList = new LinkedList<>();


    // read words from dictionary URL and store them in answerWordsList
    public static List<String> readAnswerWords() {

        try (InputStream dictIs = new URL(
                // word dictionary URL
                "https://raw.githubusercontent.com/dwyl/english-words/master/words.txt").openStream()) {

            return answerWordsList =
                    Pattern.compile("\n")
                            .splitAsStream(
                                    new String(dictIs.readAllBytes()))
                            .map(s ->
                                    // replacing non alphabets
                                    s.replaceAll("[^a-zA-Z]", "")
                                            .toUpperCase())
                            // extracting only those words from
                            // dictionary that are of length 5
                            .filter(s -> s.length() == 5)
                            .distinct()
                            .collect(Collectors.toList());

        } catch (Exception e) {
            return List.of();
        }
    }

    public static void calculatePossibleWords(String inputWord, int[] result) {
        String inputWordUpper = inputWord.toUpperCase();
        // check if the word has a letter that appeared more than once
        boolean hasDuplicateLetter = false;
        byte[] appears = new byte[26];
        for (char c : inputWordUpper.toCharArray())
            appears[c - 'A']++;
        for (byte b : appears)
            if (b > 1) {
                hasDuplicateLetter = true;
                break;
            }
        // remove the word that has been identified as not being the answer
        if (!(result[0] == 1 && result[1] == 1 && result[2] == 1 && result[3] == 1 && result[4] == 1))
            answerWordsList.remove(inputWordUpper);
        // calculation begins

//        if (hasDuplicateLetter) {
        // use a more 'conservative' algorithm. it doesn't have problems when there are duplicate letters in the word
        for (int loc = 0; loc != 5; loc++) {
            switch (result[loc]) {
                case 2://the char is in another location
                {
                    //keep the words that have the char
                    for (Iterator<String> it = answerWordsList.iterator(); it.hasNext(); )
                        if (it.next().charAt(loc) == inputWordUpper.charAt(loc)) it.remove();
                    // word in list, and input word, have yellow letter at specific index


                    for (Iterator<String> it = answerWordsList.iterator(); it.hasNext(); )
                        if (!it.next().contains(inputWordUpper.charAt(loc) + "")) it.remove();
                    break;
                    //if the yellow letter in input word, is not in the word in Dictionary list
                }
                case 1://the char is in the right location
                {
                    //keep the words that have the same char at the same location
                    for (Iterator<String> it = answerWordsList.iterator(); it.hasNext(); )
                        if (it.next().charAt(loc) != inputWordUpper.charAt(loc)) it.remove();
                    break;
                }
                case 0://the char is not in the answer word
                {
                    //remove the words that have the same char that is not matched
                    for (Iterator<String> it = answerWordsList.iterator(); it.hasNext(); )
                        if (it.next().charAt(loc) == inputWordUpper.charAt(loc)) it.remove();
                    break;
                }
            }
        }
//        } else {
//            // use a more 'aggressive' algorithm
//            for (int loc = 0; loc != 5; loc++) {
//                switch (result[loc]) {
//                    case 2://the char is in another location
//                    {
//                        //keep the words that have the char
//                        LinkedList<String> temp = new LinkedList<>();
//                        for (String word : answerWordsList)
//                            if (word.contains(inputWordUpper.charAt(loc) + "")) temp.add(word);
//                        //and remove the words that have the char in this location
//                        for (Iterator<String> it = temp.iterator(); it.hasNext(); )
//                            if (it.next().charAt(loc) == inputWordUpper.charAt(loc)) it.remove();
//                        answerWordsList = temp;
//                        break;
//                    }
//                    case 1://the char is in the right location
//                    {
//                        //keep the words that have the same char at the same location
//                        LinkedList<String> temp = new LinkedList<>();
//                        for (String word : answerWordsList)
//                            if (word.charAt(loc) == inputWordUpper.charAt(loc)) temp.add(word);
//                        answerWordsList = temp;
//                        break;
//                    }
//                    case 0://the char is not in the answer word
//                    {
//                        //remove the words that have the same char that is not matched
//                        LinkedList<String> temp = new LinkedList<>();
//                        for (String word : answerWordsList)
//                            if (!word.contains(inputWordUpper.charAt(loc) + "")) temp.add(word);
//                        answerWordsList = temp;
//                        break;
//                    }
//                }
//            }
//        }
    }
}


