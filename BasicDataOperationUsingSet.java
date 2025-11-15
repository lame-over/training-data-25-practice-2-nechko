
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationUsingSet реалізує операції з множиною LinkedHashSet для float.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataAnalysis()} - Запускає аналіз даних.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив float.</li>
 *   <li>{@link #findInArray()} - Пошук значення в масиві float.</li>
 *   <li>{@link #locateMinMaxInArray()} - Знаходить граничні значення в масиві.</li>
 *   <li>{@link #findInSet()} - Пошук значення в множині float.</li>
 *   <li>{@link #locateMinMaxInSet()} - Знаходить мінімальне і максимальне значення в множині.</li>
 *   <li>{@link #analyzeArrayAndSet()} - Аналізує елементи масиву та множини.</li>
 * </ul>
 */
public class BasicDataOperationUsingSet {
    float floatValueToSearch;
    Float[] floatArray;
    Set<Float> dateTimeSet = new LinkedHashSet<>();

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param floatValueToSearch Значення для пошуку
     * @param floatArray Масив float
     */
    BasicDataOperationUsingSet(float floatValueToSearch, Float[] floatArray) {
        this.floatValueToSearch = floatValueToSearch;
        this.floatArray = floatArray;
        this.dateTimeSet = new LinkedHashSet<>(Arrays.asList(floatArray));
    }
    
    /**
     * Запускає комплексний аналіз даних з використанням множини LinkedHashSet.
     * 
     * Метод завантажує дані, виконує операції з множиною та масивом float.
     */
    public void executeDataAnalysis() {
        // спочатку аналізуємо множину дати та часу
        findInSet();
        locateMinMaxInSet();
        analyzeArrayAndSet();

        // потім обробляємо масив
        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(floatArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив об'єктів float за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    private void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(floatArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    private void findInArray() {
        long timeStart = System.nanoTime();

        int position = Arrays.binarySearch(this.floatArray, floatValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в масивi дати i часу");

        if (position >= 0) {
            System.out.println("Елемент '" + floatValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + floatValueToSearch + "' відсутній в масиві.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в масиві float.
     */
    private void locateMinMaxInArray() {
        if (floatArray == null || floatArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        float minValue = floatArray[0];
        float maxValue = floatArray[0];

        for (float currentDateTime : floatArray) {
            if (floatValueToSearch < minValue) {
                minValue = currentDateTime;
            }
            if (floatValueToSearch > maxValue) {
                maxValue = currentDateTime;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в масивi");

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Здійснює пошук конкретного значення в множині дати та часу.
     */
    private void findInSet() {
        long timeStart = System.nanoTime();

        boolean elementExists = this.dateTimeSet.contains(floatValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в LinkedHashSet дати i часу");

        if (elementExists) {
            System.out.println("Елемент '" + floatValueToSearch + "' знайдено в LinkedHashSet");
        } else {
            System.out.println("Елемент '" + floatValueToSearch + "' відсутній в LinkedHashSet.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в множині float.
     */
    private void locateMinMaxInSet() {
        if (dateTimeSet == null || dateTimeSet.isEmpty()) {
            System.out.println("LinkedHashSet є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        float minValue = Collections.min(dateTimeSet);
        float maxValue = Collections.max(dateTimeSet);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в LinkedHashSet");

        System.out.println("Найменше значення в LinkedHashSet: " + minValue);
        System.out.println("Найбільше значення в LinkedHashSet: " + maxValue);
    }

    /**
     * Аналізує та порівнює елементи масиву та множини.
     */
    private void analyzeArrayAndSet() {
        System.out.println("Кiлькiсть елементiв в масивi: " + floatArray.length);
        System.out.println("Кiлькiсть елементiв в LinkedHashSet: " + dateTimeSet.size());

        boolean allElementsPresent = true;
        for (float dateTimeElement : floatArray) {
            if (!dateTimeSet.contains(dateTimeElement)) {
                allElementsPresent = false;
                break;
            }
        }

        if (allElementsPresent) {
            System.out.println("Всi елементи масиву наявні в LinkedHashSet.");
        } else {
            System.out.println("Не всi елементи масиву наявні в LinkedHashSet.");
        }
    }
}