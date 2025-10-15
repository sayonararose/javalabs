//Варіант 18
//C3 Тип StringBuilder
//C17 Дія з текстом Вивести всі речення заданого тексту в порядку зростання кількості слів у них. 
import java.util.ArrayList;
import java.util.List;

public class lab2 {

    public static void main(String[] args) {
        try {
            // За приклад тексту візьмемо слова з пісні
            String initialText = "My heart is flutters under the moonlight. "
                    + "I wanna go dance under the milky way, let's go. "
                    + "Now let go oh oh oh. "
                    + "Let's have no regrets when the day is gone. "
                    + "So that time can't tear us apart. "
                    + "So that this moment can be eternal.";

            StringBuilder text = new StringBuilder(initialText);
            
            // Створюємо список для речень
            List<StringBuilder> sentences = new ArrayList<>();
            int startIndex = 0;

            // Проходимо по тексту і ділимо його на речення
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (c == '.' || c == '!' || c == '?') {
                    sentences.add(new StringBuilder(text.substring(startIndex, i + 1)));
                    startIndex = i + 1; // Початок наступного речення
                }
            }
            
            // Створюємо масив для кількості слів
            int[] wordCounts = new int[sentences.size()];
            for (int i = 0; i < sentences.size(); i++) {
                wordCounts[i] = countWords(sentences.get(i));
            }

            // Сортуємо методом бульбашки
            for (int i = 0; i < sentences.size() - 1; i++) {
                for (int j = 0; j < sentences.size() - i - 1; j++) {
                    if (wordCounts[j] > wordCounts[j + 1]) {
                        int tempCount = wordCounts[j];
                        wordCounts[j] = wordCounts[j + 1];
                        wordCounts[j + 1] = tempCount;
                        StringBuilder tempSentence = sentences.get(j);
                        sentences.set(j, sentences.get(j + 1));
                        sentences.set(j + 1, tempSentence);
                    }
                }
            }

            // Виводимо результат
            System.out.println("Речення за зростанням кiлькостi слiв:");
            for (StringBuilder s : sentences) {
                System.out.println(s.toString().trim());
            }

        } catch (Exception e) {
            // Обробляємо можливі помилки
            System.out.println("Виникла помилка: " + e.getMessage());
        }
    }

    // Метод для підрахунку слів
    public static int countWords(StringBuilder sentence) {
        int count = 0;
        boolean inWord = false;
        for (int i = 0; i < sentence.length(); i++) {
            if (Character.isLetterOrDigit(sentence.charAt(i))) {
                if (!inWord) {
                    count++;
                    inWord = true;
                }
            } else {
                inWord = false;
            }
        }
        return count;
    }
}
