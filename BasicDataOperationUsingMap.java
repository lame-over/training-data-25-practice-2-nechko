import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map для зберігання пар ключ-значення.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними Map.</li>
 *   <li>{@link #findByKey()} - Здійснює пошук елемента за ключем в Map.</li>
 *   <li>{@link #findByValue()} - Здійснює пошук елемента за значенням в Map.</li>
 *   <li>{@link #addEntry()} - Додає новий запис до Map.</li>
 *   <li>{@link #removeByKey()} - Видаляє запис з Map за ключем.</li>
 *   <li>{@link #removeByValue()} - Видаляє записи з Map за значенням.</li>
 *   <li>{@link #sortByKey()} - Сортує Map за ключами.</li>
 *   <li>{@link #sortByValue()} - Сортує Map за значеннями.</li>
 * </ul>
 */
public class BasicDataOperationUsingMap {
    private final Scorpion KEY_TO_SEARCH_AND_DELETE = new Scorpion("Гак",6.9);
    private final Scorpion KEY_TO_ADD = new Scorpion("Жало", 7.2);

    private final String VALUE_TO_SEARCH_AND_DELETE = "Макар";
    private final String VALUE_TO_ADD = "Владислав";

    private Hashtable<Scorpion, String> hashtable;
    private LinkedHashMap<Scorpion, String> linkedHashMap;
    private TreeMap<Scorpion, String> treeMap;

    /**
     * Компаратор для сортування Map.Entry за значеннями String.
     * Використовує метод String.compareTo() для порівняння імен власників.
     */
    static class OwnerValueComparator implements Comparator<Map.Entry<Scorpion, String>> {
        @Override
        public int compare(Map.Entry<Scorpion, String> e1, Map.Entry<Scorpion, String> e2) {
            String v1 = e1.getValue();
            String v2 = e2.getValue();
            if (v1 == null && v2 == null) return 0;
            if (v1 == null) return -1;
            if (v2 == null) return 1;
            return v1.compareTo(v2);
        }
    }

    public record Scorpion(String nickname, Double species) {}

    private static final Comparator<Scorpion> SCORPION_COMPARATOR = Comparator.comparing(Scorpion::nickname).thenComparing(Scorpion::species, Comparator.reverseOrder());

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
     * @param hashtable Hashtable з початковими даними (ключ: Scorpion, значення: ім'я власника)
     * @param linkedHashMap LinkedHashMap з початковими даними (ключ: Scorpion, значення: ім'я власника)
     * @param treeMap TreeMap з початковими даними (ключ: Scorpion, значення: ім'я власника)
     */
    BasicDataOperationUsingMap(Hashtable<Scorpion, String> hashtable, LinkedHashMap<Scorpion, String> linkedHashMap, TreeMap<Scorpion, String> treeMap) {
        this.hashtable = hashtable;
        this.linkedHashMap = linkedHashMap;
        this.treeMap = treeMap;
    }
    
    /**
     * Виконує комплексні операції з Map.
     * 
     * Метод виконує різноманітні операції з Map: пошук, додавання, видалення та сортування.
     */
    public void executeDataOperations() {
        //Спочатку працюємо з Hashtable
        System.out.println("========= Операції з Hashtable =========");
        System.out.println("Початковий розмір Hashtable: " + hashtable.size());
        
        //Пошук до сортування
        findByKeyInHashtable();
        findByValueInHashtable();

        printHashtable();
        sortHashtable();
        printHashtable();

        //Пошук після сортування
        findByKeyInHashtable();
        findByValueInHashtable();

        addEntryToHashtable();
        
        removeByKeyFromHashtable();
        removeByValueFromHashtable();
               
        System.out.println("Кінцевий розмір Hashtable: " + hashtable.size());

        //Обробляємо LinkedHashMap
        System.out.println("\n\n========= Операції з LinkedHashMap =========");
        System.out.println("Початковий розмір LinkedHashMap: " + linkedHashMap.size());
        
        findByKeyInLinkedHashMap();
        findByValueInLinkedHashMap();

        printLinkedHashMap();
        sortLinkedHashMap();
        printLinkedHashMap();

        addEntryToLinkedHashMap();
        
        removeByKeyFromLinkedHashMap();
        removeByValueFromLinkedHashMap();

        System.out.println("Кінцевий розмір LinkedHashMap: " + linkedHashMap.size());

        //Обробляємо TreeMap
        System.out.println("\n\n========= Операції з Treemap =========");
        System.out.println("Початковий розмір Treemap: " + treeMap.size());

        findByKeyInTreeMap();
        findByValueInTreeMap();

        printTreeMap();
        sortTreeMap();
        printTreeMap();

        addEntryToTreeMap();
        
        removeByKeyFromTreeMap();
        removeByValueFromTreeMap();

        System.out.println("Кінцевий розмір Treemap: " + treeMap.size());
    }


    // ===== Методи для Hashtable =====

    /**
     * Виводить вміст Hashtable без сортування.
     * Hashtable не гарантує жодного порядку елементів.
     */
    private void printHashtable() {
        System.out.println("\n=== Пари ключ-значення в Hashtable ===");
        long timeStart = System.nanoTime();

        hashtable.entrySet().forEach(entry ->
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue())
        );


        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в Hashtable");
    }

    /**
     * Виводить вміст Treemap без сортування.
     * Hashtable не гарантує жодного порядку елементів.
     */
    private void printTreeMap() {
        System.out.println("\n=== Пари ключ-значення в TreeMap ===");
        long timeStart = System.nanoTime();

        treeMap.entrySet().forEach(entry ->
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue())
        );


        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в TreeMap");
    }
    /**
     * Сортує LinkedHashMap, HashMap, TreeMap за ключами.
     * Використовує Collections.sort() з природним порядком Scorpion (Scorpion.compareTo()).
     * Перезаписує linkedHashMap відсортованими даними.
     */
    private void sortLinkedHashMap() {
        long timeStart = System.nanoTime();

        linkedHashMap = linkedHashMap.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(SCORPION_COMPARATOR))
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));

        PerformanceTracker.displayOperationTime(timeStart, "сортування LinkedHashMap за ключами");
    }

    private void sortTreeMap() {
        long timeStart = System.nanoTime();

        treeMap = treeMap.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(SCORPION_COMPARATOR))
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    () -> new TreeMap<>(SCORPION_COMPARATOR)
            ));

        PerformanceTracker.displayOperationTime(timeStart, "сортування TreeMap за ключами");
    }

    private void sortHashtable() {
        long timeStart = System.nanoTime();

        hashtable = hashtable.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(SCORPION_COMPARATOR))
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    Hashtable::new
            ));

        PerformanceTracker.displayOperationTime(timeStart, "сортування Hashtable за ключами");
    }

    /**
     * Здійснює пошук елемента за ключем в Hashtable.
     * Використовує Scorpion.hashCode() та Scorpion.equals() для пошуку.
     */
    void findByKeyInHashtable() {
        long timeStart = System.nanoTime();

        boolean found = hashtable.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в Hashtable");

        if (found) {
            String value = hashtable.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в Hashtable.");
        }
    }

    /**
     * Здійснює пошук елемента за ключем в Treemap.
     * Використовує Scorpion.hashCode() та Scorpion.equals() для пошуку.
     */
    void findByKeyInTreeMap() {
        long timeStart = System.nanoTime();

        boolean found = treeMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в TreeMap");

        if (found) {
            String value = treeMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в TreeMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в Hashtable.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInHashtable() {
        List<Scorpion> keysToRemove = hashtable.entrySet().stream()
            .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        keysToRemove.forEach(hashtable::remove);
    }

    /**
     * Здійснює пошук елемента за значенням в Treemap.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInTreeMap() {
        List<Scorpion> keysToRemove = treeMap.entrySet().stream()
            .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        keysToRemove.forEach(treeMap::remove);
    }

    /**
     * Додає новий запис до Hashtable.
     */
    void addEntryToHashtable() {
        long timeStart = System.nanoTime();

        hashtable.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до Hashtable");

        System.out.println("Додано новий запис: Scorpion='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з Hashtable за ключем.
     */
    void removeByKeyFromHashtable() {
        long timeStart = System.nanoTime();

        String removedValue = hashtable.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з Hashtable");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з Hashtable за значенням.
     */
    void removeByValueFromHashtable() {
        long timeStart = System.nanoTime();

        List<Scorpion> keysToRemove = hashtable.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
       
        keysToRemove.forEach(hashtable::remove);


        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з Hashtable");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
    * Додає новий запис до Treemap.
    */
    void addEntryToTreeMap() {
        long timeStart = System.nanoTime();

        treeMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до TreeMap");

        System.out.println("Додано новий запис: Scorpion='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з Hashtable за ключем.
     */
    void removeByKeyFromTreeMap() {
        long timeStart = System.nanoTime();

        String removedValue = treeMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з TreeMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з Treemap за значенням.
     */
    void removeByValueFromTreeMap() {
        long timeStart = System.nanoTime();

        List<Scorpion> keysToRemove = treeMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
       
        keysToRemove.forEach(treeMap::remove);


        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з TreeMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для LinkedHashMap =====

    /**
     * Виводить вміст LinkedHashMap.
     * LinkedHashMap автоматично відсортована за ключами (Scorpion nickname за зростанням, pincerSize за спаданням).
     */
    private void printLinkedHashMap() {
        System.out.println("\n=== Пари ключ-значення в LinkedHashMap ===");

        long timeStart = System.nanoTime();

        linkedHashMap.entrySet().forEach(entry ->
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue())
        );


        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в LinkedHashMap");
    }

    /**
     * Здійснює пошук елемента за ключем в LinkedHashMap.
     * Використовує Scorpion.compareTo() для навігації по дереву.
     */
    void findByKeyInLinkedHashMap() {
        long timeStart = System.nanoTime();

        boolean found = linkedHashMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в LinkedHashMap");

        if (found) {
            String value = linkedHashMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в LinkedHashMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в LinkedHashMap.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInLinkedHashMap() {
        List<Scorpion> keysToRemove = linkedHashMap.entrySet().stream()
            .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());


        keysToRemove.forEach(linkedHashMap::remove);
    }

    /**
     * Додає новий запис до LinkedHashMap.
     */
    void addEntryToLinkedHashMap() {
        long timeStart = System.nanoTime();

        linkedHashMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до LinkedHashMap");

        System.out.println("Додано новий запис: Scorpion='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з LinkedHashMap за ключем.
     */
    void removeByKeyFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        String removedValue = linkedHashMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з LinkedHashMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з LinkedHashMap за значенням.
     */
    void removeByValueFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        List<Scorpion> keysToRemove = linkedHashMap.entrySet().stream()
            .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
       
        keysToRemove.forEach(hashtable::remove);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з LinkedHashMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        
        TreeMap<Scorpion, String> treeMap = new TreeMap<>(SCORPION_COMPARATOR);
        treeMap.put(new Scorpion("Шип", 8.5), "Орест");
        treeMap.put(new Scorpion("Жало", 7.2), "Зоряна");
        treeMap.put(new Scorpion("Клеш", 9.1), "Макар");
        treeMap.put(new Scorpion("Панцир", 6.8), "Іринка");
        treeMap.put(new Scorpion("Жало", 7.8), "Демид");
        treeMap.put(new Scorpion("Хвіст", 5.9), "Макар");
        treeMap.put(new Scorpion("Терен", 8.2), "Оксана");
        treeMap.put(new Scorpion("Скорп", 6.5), "Юхим");
        treeMap.put(new Scorpion("Ракун", 7.1), "Зоряна");
        treeMap.put(new Scorpion("Оса", 5.5), "Ярина");

        // Створюємо початкові дані (ключ: Scorpion, значення: ім'я власника)
        Hashtable<Scorpion, String> hashtable = new Hashtable<>();
        hashtable.put(new Scorpion("Шип", 8.5), "Орест");
        hashtable.put(new Scorpion("Жало", 7.2), "Зоряна");
        hashtable.put(new Scorpion("Клеш", 9.1), "Макар");
        hashtable.put(new Scorpion("Панцир", 6.8), "Іринка");
        hashtable.put(new Scorpion("Жало", 7.8), "Демид");
        hashtable.put(new Scorpion("Хвіст", 5.9), "Макар");
        hashtable.put(new Scorpion("Терен", 8.2), "Оксана");
        hashtable.put(new Scorpion("Скорп", 6.5), "Юхим");
        hashtable.put(new Scorpion("Ракун", 7.1), "Зоряна");
        hashtable.put(new Scorpion("Оса", 5.5), "Ярина");
        
        LinkedHashMap<Scorpion, String> linkedHashMap = new LinkedHashMap<Scorpion, String>() {{
        put(new Scorpion("Шип", 8.5), "Орест");
        put(new Scorpion("Жало", 7.2), "Зоряна");
        put(new Scorpion("Клеш", 9.1), "Макар");
        put(new Scorpion("Панцир", 6.8), "Іринка");
        put(new Scorpion("Жало", 7.8), "Демид");
        put(new Scorpion("Хвіст", 5.9), "Макар");
        put(new Scorpion("Терен", 8.2), "Оксана");
        put(new Scorpion("Скорп", 6.5), "Юхим");
        put(new Scorpion("Ракун", 7.1), "Зоряна");
        put(new Scorpion("Оса", 5.5), "Ярина");
        }};

        // Створюємо об'єкт і виконуємо операції
        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashtable, linkedHashMap, treeMap);
        operations.executeDataOperations();
    }
}
