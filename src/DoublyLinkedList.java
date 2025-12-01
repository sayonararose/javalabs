import java.util.*;

public class DoublyLinkedList<T> implements List<T> {
   //  Внутрішній клас, що описує елемент списку.
    private class Node {
        T value;    // Значення елемента
        Node next;  // Посилання на наступний елемент
        Node prev;  // Посилання на попередній елемент

        Node(T value) {
            this.value = value;
        }
    }

    private Node head; // Початок списку
    private Node tail; // Кінець списку
    private int size = 0; // Поточна кількість елементів

    // --- Конструктори ---


    //  1. Конструктор за замовчуванням (створює порожній список).

    public DoublyLinkedList() { }

    //  2. Конструктор, що створює список з одним елементом.
    public DoublyLinkedList(T element) {
        add(element);
    }

    // 3. Конструктор, що копіює елементи з іншої колекції.

    public DoublyLinkedList(Collection<? extends T> collection) {
        for (T item : collection) {
            add(item);
        }
    }

    // --- Реалізація методів ---

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    // Додає елемент в кінець списку.

    @Override
    public boolean add(T element) {
        Node newNode = new Node(element);
        if (head == null) {
            // Якщо список порожній, новий елемент стає і головою, і хвостом
            head = newNode;
            tail = newNode;
        } else {
            // Додаємо після хвоста
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode; // Оновлюємо посилання на хвіст
        }
        size++;
        return true;
    }


    // Вставляє елемент за вказаним індексом.

    @Override
    public void add(int index, T element) {
        // Перевірка коректності індексу
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Індекс: " + index);
        }

        // Якщо індекс дорівнює розміру, додаємо в кінець
        if (index == size) {
            add(element); 
            return;
        }

        Node newNode = new Node(element);
        
        if (index == 0) {
            // Вставка на початок списку
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            // Вставка в середину списку
            // Шукаємо елемент, перед яким треба вставити
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            
            //Змінюємо посилання сусідів
            newNode.next = current;
            newNode.prev = current.prev;
            
            // "Зв'язуємо" попередній елемент з новим
            current.prev.next = newNode;
            // "Зв'язуємо" поточний елемент з новим
            current.prev = newNode;
        }
        size++;
    }


    // Отримує елемент за індексом.

    @Override
    public T get(int index) {
        checkIndex(index);
        // Проходимо по списку до потрібного елемента
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    // Видаляє елемент за індексом.

    @Override
    public T remove(int index) {
        checkIndex(index);
        // Шукаємо вузол для видалення
        Node toRemove = head;
        for (int i = 0; i < index; i++) {
            toRemove = toRemove.next;
        }
        T val = toRemove.value;
        unlink(toRemove); // Викликаємо допоміжний метод розриву зв'язків
        return val;
    }


    //  Видаляє перше входження конкретного об'єкта.

    @Override
    public boolean remove(Object o) {
        Node current = head;
        while (current != null) {
            // Порівнюємо значення
            if (Objects.equals(current.value, o)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Допоміжний метод для фізичного видалення вузла зі списку.
   
    private void unlink(Node node) {
        // Обробка попереднього вузла
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            // Якщо попереднього немає, значить видаляємо голову
            head = node.next;
        }

        // Обробка наступного вузла
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            // Якщо наступного немає, значить видаляємо хвіст
            tail = node.prev;
        }
        size--;
    }

    @Override
    public void clear() {
        // Очищаємо посилання
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        Node current = head;
        while (current != null) {
            if (Objects.equals(current.value, o)) return true;
            current = current.next;
        }
        return false;
    }


    // Ітератор для проходу по списку.

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) throw new NoSuchElementException();
                T val = current.value;
                current = current.next;
                return val;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        int i = 0;
        Node curr = head;
        while (curr != null) {
            arr[i++] = curr.value;
            curr = curr.next;
        }
        return arr;
    }

    // Метод для сумісності з generic-масивами
    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return (T1[]) java.util.Arrays.copyOf(toArray(), size, a.getClass());
        }
        System.arraycopy(toArray(), 0, a, 0, size);
        if (a.length > size) a[size] = null;
        return a;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
    }

    // --- Заглушки для методів---
    @Override public T set(int index, T element) { throw new UnsupportedOperationException(); }
    @Override public int indexOf(Object o) { return -1; }
    @Override public int lastIndexOf(Object o) { return -1; }
    @Override public boolean containsAll(Collection<?> c) { return false; }
    @Override public boolean addAll(Collection<? extends T> c) { return false; }
    @Override public boolean addAll(int index, Collection<? extends T> c) { return false; }
    @Override public boolean removeAll(Collection<?> c) { return false; }
    @Override public boolean retainAll(Collection<?> c) { return false; }
    @Override public ListIterator<T> listIterator() { return null; }
    @Override public ListIterator<T> listIterator(int index) { return null; }
    @Override public List<T> subList(int fromIndex, int toIndex) { return null; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node curr = head;
        while (curr != null) {
            sb.append(curr.value);
            if (curr.next != null) sb.append(", ");
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }
}