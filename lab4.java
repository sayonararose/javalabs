//Варіант 18
//Модифікувати лабораторну роботу №2 наступним чином: для літер, слів, речень, розділових знаків та тексту створити окремі класи. 	Слово повинно складатися з масиву літер, речення з масиву слів та розділових знаків, текст з масиву речень. 
//Замінити послідовність табуляцій та пробілів одним пробілом.
//Створити клас, який складається з виконавчого методу, що виконує описану дію з лабораторної роботи №2, але в якості типів використовує створені класи.
public class lab4 { 

    //1. Клас letter 
    //Клас, що представляє одну літеру.
    static class Letter {
        private final char character;

        public Letter(char character) {
            this.character = character;
        }

        public char getCharacter() {
            return character;
        }

        @Override
        public String toString() {
            return String.valueOf(character);
        }
    }

    // 2.Клас Punctuation 
    static class Punctuation {
        private final char symbol;

        public Punctuation(char symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return String.valueOf(symbol);
        }
    }

    //3.Клас Word 
    // Клас, який представляє слово як масив літер.
    static class Word {
        private final Letter[] letters;

        public Word(String word) {
            // Створюємо масив літер довжиною рівною довжині слова
            letters = new Letter[word.length()];
            for (int i = 0; i < word.length(); i++) {
                letters[i] = new Letter(word.charAt(i));
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (Letter letter : letters) {
                builder.append(letter.getCharacter());
            }
            return builder.toString();
        }
    }

    //4.Клас Sentence
    //Клас речення: складається з масиву слів та завершальної пунктуації.
    static class Sentence {
        private final Word[] words;
        private final Punctuation punctuation;

        public Sentence(String raw) {
            raw = raw.trim();

            // Перевірка на наявність розділового знаку в кінці
            char last = raw.charAt(raw.length() - 1);
            Punctuation p = null;

            if (last == '.' || last == '!' || last == '?') {
                p = new Punctuation(last);
                // Відрізаємо знак пунктуації від рядка
                raw = raw.substring(0, raw.length() - 1);
            }
            punctuation = p;

            // Розбиваємо залишок речення на слова
            String[] parts = raw.split(" ");
            words = new Word[parts.length];
            for (int i = 0; i < parts.length; i++) {
                words[i] = new Word(parts[i]);
            }
        }

        public int getWordCount() {
            return words.length;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < words.length; i++) {
                builder.append(words[i]);
                // Додаємо пробіл між словами
                if (i < words.length - 1) builder.append(" ");
            }
            if (punctuation != null) builder.append(punctuation);
            return builder.toString();
        }
    }

    // 5.Клас Text 
    // Замінює багато пробілів одним пробілом.
    static class Text {
        private final Sentence[] sentences;

        public Text(String input) {
            // Заміна послідовності табуляцій та пробілів одним пробілом
            input = input.replaceAll("[ \\t]+", " ");

            // Розбиття на речення зі збереженням розділювача 
            String[] parts = input.split("(?<=[.!?])");
            sentences = new Sentence[parts.length];

            for (int i = 0; i < parts.length; i++) {
                sentences[i] = new Sentence(parts[i].trim());
            }
        }

        public Sentence[] getSentences() {
            return sentences;
        }
    }


    // сортування речень.
    static class TextProcessor {

        public void execute(String textData) {
            System.out.println("Вхiдний текст:\n" + textData + "\n");

            Text text = new Text(textData);
            Sentence[] sentences = text.getSentences();

            // Сортування методом бульбашки 
            for (int i = 0; i < sentences.length - 1; i++) {
                for (int j = 0; j < sentences.length - i - 1; j++) {
                    if (sentences[j].getWordCount() > sentences[j + 1].getWordCount()) {
                        Sentence tmp = sentences[j];
                        sentences[j] = sentences[j + 1];
                        sentences[j + 1] = tmp;
                    }
                }
            }

            System.out.println("Речення за зростанням кiлькостi слiв:\n");
            for (Sentence s : sentences) {
                System.out.println(s);
            }
        }
    }

    public static void main(String[] args) {
        // Текст із пісні з навмисними зайвими пробілами та табуляціями
        String text = "My   heart\tis flutters under the moonlight. "
                + "I wanna go dance under the milky way, let's go. "
                + "Now let go oh oh oh. "
                + "Let's have no regrets when the day is gone. "
                + "So that time can't tear us apart. "
                + "So that this moment can be eternal.";

        TextProcessor processor = new TextProcessor();
        processor.execute(text);
    }
}