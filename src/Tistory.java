import java.util.ArrayList;
import java.util.Scanner;

public class Tistory {
    public static void main(String[] args) {
        /*
        Scanner sc = new Scanner(System.in);
        ArrayList<String> words = new ArrayList<>();
        String w = "";
        while (true) {
            System.out.print("단어 입력(q 입력 시 종료): ");
            w = sc.next();
            if (w.equals("q")) {
                break;
            } else {
                words.add(w);
            }
        }

        ArrayList<Finder> finders = new ArrayList<>();

        for (String word : words) {
            finders.add(new Finder(word));
        }

        for (Finder finder : finders) {
            finder.start();
            try {
                finder.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Finder finder : finders) {
            try {
                finder.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Finder finder : finders) {
            finder.quit();
        }
        sc.close();

         */
        WordGenerator generator = new WordGenerator();
        while (true) {
            String word = generator.generate();

            if (word.length() != 0) {
                Finder finder = new Finder(word);
                finder.start();
                try {
                    finder.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finder.quit();
            }
        }
    }
}
