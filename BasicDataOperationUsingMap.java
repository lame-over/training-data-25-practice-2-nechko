import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
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

    /**
     * Внутрішній клас Scorpion для зберігання інформації про домашню тварину.
     * 
     * Реалізує Comparable<Scorpion> для визначення природного порядку сортування.
     * Природний порядок: спочатку за кличкою (nickname) за зростанням, потім за видом (pincerSize) за спаданням.
     */
    public static class Scorpion implements Comparable<Scorpion> {
        private final String nickname;
        private final Double pincerSize;

        public Scorpion(String nickname) {
            this.nickname = nickname;
            this.pincerSize = null;
        }

        public Scorpion(String nickname, Double pincerSize) {
            this.nickname = nickname;
            this.pincerSize = pincerSize;
        }

        public String getNickname() { 
            return nickname; 
        }

        public Double getpincerSize() {
            return pincerSize;
        }

        /**
         * Порівнює цей об'єкт Scorpion з іншим для визначення порядку сортування.
         * Природний порядок: спочатку за кличкою (nickname) за зростанням, потім за видом (pincerSize) за спаданням.
         * 
         * @param other Scorpion об'єкт для порівняння
         * @return негативне число, якщо цей Scorpion < other; 
         *         0, якщо цей Scorpion == other; 
         *         позитивне число, якщо цей Scorpion > other
         * 
         * Критерій порівняння: поля nickname (кличка) за зростанням та pincerSize (вид) за спаданням.
         * 
         * Цей метод використовується:
         * - LinkedHashMap для автоматичного сортування ключів Scorpion за nickname (зростання), потім за pincerSize (спадання)
         * - Collections.sort() для сортування Map.Entry за ключами Scorpion
         * - Collections.binarySearch() для пошуку в відсортованих колекціях
         */
        @Override
        public int compareTo(Scorpion other) {
            if (other == null) return 1;
            
            // Спочатку порівнюємо за кличкою (за зростанням)
            int nicknameComparison = 0;
            if (this.nickname == null && other.nickname == null) {
                nicknameComparison = 0;
            } else if (this.nickname == null) {
                nicknameComparison = -1;
            } else if (other.nickname == null) {
                nicknameComparison = 1;
            } else {
                nicknameComparison = other.nickname.compareTo(this.nickname);
            }
            
            // Якщо клички різні, повертаємо результат
            if (nicknameComparison != 0) {
                return nicknameComparison;
            }
            
            // Якщо клички однакові, порівнюємо за видом (за спаданням - інвертуємо результат)
            if (this.pincerSize == null && other.pincerSize == null) return 0;
            if (this.pincerSize == null) return 1;  // null йде в кінець при спаданні
            if (other.pincerSize == null) return -1;
            return other.pincerSize.compareTo(this.pincerSize);  // Інвертоване порівняння для спадання
        }

        /**
         * Перевіряє рівність цього Scorpion з іншим об'єктом.
         * Два Scorpion вважаються рівними, якщо їх клички (nickname) та види (pincerSize) однакові.
         * 
         * @param obj об'єкт для порівняння
         * @return true, якщо об'єкти рівні; false в іншому випадку
         * 
         * Критерій рівності: поля nickname (кличка) та pincerSize (вид).
         * 
         * Важливо: метод узгоджений з compareTo() - якщо equals() повертає true,
         * то compareTo() повертає 0, оскільки обидва методи порівнюють за nickname та pincerSize.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Scorpion scorpion = (Scorpion) obj;
            
            boolean nicknameEquals = nickname != null ? nickname.equals(scorpion.nickname) : scorpion.nickname == null;
            boolean pincerSizeEquals = pincerSize != null ? pincerSize.equals(scorpion.pincerSize) : scorpion.pincerSize == null;
            
            return nicknameEquals && pincerSizeEquals;
        }

        /**
         * Повертає хеш-код для цього Scorpion.
         * 
         * @return хеш-код, обчислений на основі nickname та pincerSize
         * 
         * Базується на полях nickname та pincerSize для узгодженості з equals().
         * 
         * Важливо: узгоджений з equals() - якщо два Scorpion рівні за equals()
         * (мають однакові nickname та pincerSize), вони матимуть однаковий hashCode().
         */
        @Override
        public int hashCode() {
            // Початкове значення: хеш-код поля nickname (або 0, якщо nickname == null)
            int result = nickname != null ? nickname.hashCode() : 0;
            
            // Комбінуємо хеш-коди полів за формулою: result = 31 * result + hashCode(поле)
            // Множник 31 - просте число, яке дає хороше розподілення хеш-кодів
            // і оптимізується JVM як (result << 5) - result
            // Додаємо хеш-код виду (або 0, якщо pincerSize == null) до загального результату
            result = 31 * result + (pincerSize != null ? pincerSize.hashCode() : 0);
            
            return result;
        }

        /**
         * Повертає строкове представлення Scorpion.
         * 
         * @return кличка тварини (nickname), вид (pincerSize) та hashCode
         */
        @Override
        public String toString() {
            if (pincerSize != null) {
                return "Scorpion{nickname='" + nickname + "', pincerSize='" + pincerSize + "', hashCode=" + hashCode() + "}";
            }
            return "Scorpion{nickname='" + nickname + "', hashCode=" + hashCode() + "}";
        }
    }

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
     * @param hashtable Hashtable з початковими даними (ключ: Scorpion, значення: ім'я власника)
     * @param linkedHashMap LinkedHashMap з початковими даними (ключ: Scorpion, значення: ім'я власника)
     */
    BasicDataOperationUsingMap(Hashtable<Scorpion, String> hashtable, LinkedHashMap<Scorpion, String> linkedHashMap) {
        this.hashtable = hashtable;
        this.linkedHashMap = linkedHashMap;
    }
    
    /**
     * Виконує комплексні операції з Map.
     * 
     * Метод виконує різноманітні операції з Map: пошук, додавання, видалення та сортування.
     */
    public void executeDataOperations() {
        // Спочатку працюємо з Hashtable
        System.out.println("========= Операції з Hashtable =========");
        System.out.println("Початковий розмір Hashtable: " + hashtable.size());
        
        // Пошук до сортування
        findByKeyInHashtable();
        findByValueInHashtable();

        printHashtable();
        sortHashtable();
        printHashtable();

        // Пошук після сортування
        findByKeyInHashtable();
        findByValueInHashtable();

        addEntryToHashtable();
        
        removeByKeyFromHashtable();
        removeByValueFromHashtable();
               
        System.out.println("Кінцевий розмір Hashtable: " + hashtable.size());

        // Потім обробляємо LinkedHashMap
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
     * Сортує LinkedHashMap за ключами.
     * Використовує Collections.sort() з природним порядком Scorpion (Scorpion.compareTo()).
     * Перезаписує linkedHashMap відсортованими даними.
     */
    private void sortLinkedHashMap() {
        long timeStart = System.nanoTime();

        linkedHashMap = linkedHashMap.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));

        PerformanceTracker.displayOperationTime(timeStart, "сортування LinkedHashMap за ключами");
    }

    private void sortHashtable() {
        long timeStart = System.nanoTime();

        hashtable = hashtable.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
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
        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashtable, linkedHashMap);
        operations.executeDataOperations();
    }
}
