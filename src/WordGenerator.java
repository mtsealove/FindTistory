import com.sun.javaws.security.AppContextUtil;

import java.io.*;
import java.util.ArrayList;

public class WordGenerator {
    ArrayList<String> words;
    final int maxSize = 4;
    final int lengthWords;

    public WordGenerator() {
        words = new ArrayList<>();
        File file = new File("word.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] word = line.split("");
                for (String w : word) {
                    words.add(w);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        lengthWords = words.size();
    }

    private int randSize() {
        return (int) (Math.random() * maxSize);
    }

    public String generate() {
        int size = randSize();
        String result = "";
        for (int i = 0; i < size; i++) {
            int idx = (int) (Math.random() * lengthWords);
            result += words.get(idx);
        }

        return result;
    }
}
