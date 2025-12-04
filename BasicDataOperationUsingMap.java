import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
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
    private final Scorpion KEY_TO_ADD = new Scorpion("Гак", 7.2);

    private final String VALUE_TO_SEARCH_AND_DELETE = "Олена";
    private final String VALUE_TO_ADD = "Богдан";

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

        for (Map.Entry<Scorpion, String> entry : hashtable.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в Hashtable");
    }

    /**
     * Сортує LinkedHashMap за ключами.
     * Використовує Collections.sort() з природним порядком Scorpion (Scorpion.compareTo()).
     * Перезаписує linkedHashMap відсортованими даними.
     */
    private void sortLinkedHashMap() {
        long timeStart = System.nanoTime();

        // Створюємо список ключів і сортуємо за природним порядком Scorpion
        List<Scorpion> sortedKeys = new ArrayList<>(linkedHashMap.keySet());
        Collections.sort(sortedKeys);
        
        // Створюємо нову LinkedHashMap з відсортованими ключами
        LinkedHashMap<Scorpion, String> sortedLinkedHashMap = new LinkedHashMap<>();
        for (Scorpion key : sortedKeys) {
            sortedLinkedHashMap.put(key, linkedHashMap.get(key));
        }
        
        // Перезаписуємо оригінальну linkedHashMap
        linkedHashMap = sortedLinkedHashMap;

        PerformanceTracker.displayOperationTime(timeStart, "сортування LinkedHashMap за ключами");
    }

    private void sortHashtable() {
        long timeStart = System.nanoTime();

        // Створюємо список ключів і сортуємо за природним порядком Scorpion
        List<Scorpion> sortedKeys = new ArrayList<>(hashtable.keySet());
        Collections.sort(sortedKeys);
        
        // Створюємо нову Hashtable з відсортованими ключами
        Hashtable<Scorpion, String> sortedHashtable = new Hashtable<>();
        for (Scorpion key : sortedKeys) {
            sortedHashtable.put(key, hashtable.get(key));
        }
        
        // Перезаписуємо оригінальну hashtable
        hashtable = sortedHashtable;

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
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Scorpion, String>> entries = new ArrayList<>(hashtable.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Scorpion, String> searchEntry = new Map.Entry<Scorpion, String>() {
            public Scorpion getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в Hashtable");

        if (position >= 0) {
            Map.Entry<Scorpion, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Scorpion: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в Hashtable.");
        }
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

        List<Scorpion> keysToRemove = new ArrayList<>();
        for (Map.Entry<Scorpion, String> entry : hashtable.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }
        
        for (Scorpion key : keysToRemove) {
            hashtable.remove(key);
        }

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
        for (Map.Entry<Scorpion, String> entry : linkedHashMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

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
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Scorpion, String>> entries = new ArrayList<>(linkedHashMap.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Scorpion, String> searchEntry = new Map.Entry<Scorpion, String>() {
            public Scorpion getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в LinkedHashMap");

        if (position >= 0) {
            Map.Entry<Scorpion, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Scorpion: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в LinkedHashMap.");
        }
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

        List<Scorpion> keysToRemove = new ArrayList<>();
        for (Map.Entry<Scorpion, String> entry : linkedHashMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }
        
        for (Scorpion key : keysToRemove) {
            linkedHashMap.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з LinkedHashMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        // Створюємо початкові дані (ключ: Scorpion, значення: ім'я власника)
        Hashtable<Scorpion, String> hashtable = new Hashtable<>();
        hashtable.put(new Scorpion("Тум", 2.3), "Андрій");
        hashtable.put(new Scorpion("Луна",4.2), "Ірина");
        hashtable.put(new Scorpion("Барсик", 3.12), "Олена");
        hashtable.put(new Scorpion("Боні", 1.75), "Олена");
        hashtable.put(new Scorpion("Тайсон", 9.2), "Ірина");
        hashtable.put(new Scorpion("Барсик",1.9), "Андрій");
        hashtable.put(new Scorpion("Ґуфі", 2.1), "Тимофій");
        hashtable.put(new Scorpion("Ґуфі", 2.2), "Поліна");
        hashtable.put(new Scorpion("Муся", 2.3), "Стефанія");
        hashtable.put(new Scorpion("Чіпо", 2.4), "Ярослав");

        LinkedHashMap<Scorpion, String> linkedHashMap = new LinkedHashMap<Scorpion, String>() {{
        put(new Scorpion("Тум", 2.3), "Андрій");
        put(new Scorpion("Луна",4.2), "Ірина");
        put(new Scorpion("Барсик", 3.12), "Олена");
        put(new Scorpion("Боні", 1.75), "Олена");
        put(new Scorpion("Тайсон", 9.2), "Ірина");
        put(new Scorpion("Барсик",1.9), "Андрій");
        put(new Scorpion("Ґуфі", 2.1), "Тимофій");
        put(new Scorpion("Ґуфі", 2.2), "Поліна");
        put(new Scorpion("Муся", 2.3), "Стефанія");
        put(new Scorpion("Чіпо", 2.4), "Ярослав");
        }};

        // Створюємо об'єкт і виконуємо операції
        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashtable, linkedHashMap);
        operations.executeDataOperations();
    }
}
