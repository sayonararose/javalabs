//Варінат 18
//Визначити клас меблі, який складається як мінімум з 5-и полів. 
//Створити клас із виконавчим методом, в якому створити масив з об’єктів класу визначеному варіантом (п. 2). 
//Відсортувати масив, за одним з полів за зростанням, а за іншим — за спаданням, використовуючи при цьому стандартні засоби сортування (). 
//Після цього знайти в масиві об’єкт, який ідентичний заданому. Всі змінні повинні бути описані та значення їх задані у виконавчому методі.
import java.util.Arrays;
import java.util.Comparator;

class Furniture {
    // поля класу що описують меблі
    String name;
    String material;
    String color;
    double price;
    double weight;

    // конструктор, щоб створювати об'єкти
    public Furniture(String name, String material, String color, double price, double weight) {
        this.name = name;
        this.material = material;
        this.color = color;
        this.price = price;
        this.weight = weight;
    }

    // метод для порівняння двох об'єктів 
    public boolean equals(Furniture other) {
        return this.name.equals(other.name)
                && this.material.equals(other.material)
                && this.color.equals(other.color)
                && this.price == other.price
                && this.weight == other.weight;
    }

    public String toString() {
        return name + " (" + material + ", " + color + ") - " + price + " грн, " + weight + " кг";
    }
}


public class lab3 {

    public static void main(String[] args) {
        
        // 1. Створюємо масив об'єктів і заповнюємо його даними 
        Furniture[] list = {
                new Furniture("Стіл", "Дерево", "Коричневий", 2500, 30),
                new Furniture("Стілець", "Метал", "Чорний", 1200, 10),
                new Furniture("Шафа", "ДСП", "Білий", 5000, 60),
                new Furniture("Комод", "Дерево", "Червоний", 3200, 40),
                new Furniture("Полиця", "Пластик", "Сірий", 900, 5)
        };

        // 2. Сортування за ціною, за зростанням
        Arrays.sort(list, Comparator.comparingDouble(f -> f.price));

        System.out.println("Сортування за ціною (зростання):");
        // Виводимо відсортований масив
        for (Furniture f : list) {
            System.out.println(f);
        }

        // 3. Сортування за вагою, за спаданням
        Arrays.sort(list, (a, b) -> Double.compare(b.weight, a.weight));

        System.out.println("\nСортування за вагою (спадання):");
        // Знову виводимо масив
        for (Furniture f : list) {
            System.out.println(f);
        }

        // 4. Пошук ідентичного об'єкта
        Furniture target = new Furniture("Комод", "Дерево", "Червоний", 3200, 40);
        boolean found = false; 

        System.out.println("\nШукаємо: " + target);

        // Проходимо по всьому масиву
        for (Furniture f : list) {
            // Використовуємо метод equals для порівняння
            if (f.equals(target)) {
                System.out.println("Знайдено iдентичний об'єкт: " + f);
                found = true; 
                break; 
            }
        }

        // Якщо прапорець так і не піднявся, значить не знайшли
        if (!found) {
            System.out.println("Ідентичний об'єкт не знайдено.");
        }
    }
}