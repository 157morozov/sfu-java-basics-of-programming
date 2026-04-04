/*
Задание 1: Входные данные: целочисленная матрица. Результат работы алгоритма: матрица,
полученная из входной, путем упорядочивания строк в порядке убывания суммы модулей их элементов,
а также дополнением матрицы новым столбцом с суммой модулей элементов каждой строки.
*/

// Объявление пакета
package pr1;

import java.util.Random; // Импорт класса библиотеки случайных значений
import java.util.Scanner; // Импорт класса библиотеки ввода значений с терминала


// Объявление главного класса файла
public class Main {

  // Объявление констант для импортированных библиотек
  private static final Scanner scanner = new Scanner(System.in);
  private static final Random random = new Random();

  // Объявление пунктов меню
  private static final String[] MENU_ITEMS = {
      "Ввод исходных данных вручную",
      "Ввод исходных данных сгенерированных случайным образом",
      "Выполнение алгоритма по заданию",
      "Вывод результата",
      "Завершение работы программы",
  };

  // Реализация главного метода класса
  public static void main(String[] args) {
    int[][] matrix = null; // Объявление матрицы
    int[][] result = null; // Объявление результирующей марицы

    boolean isRunning = true; // Замыкание
    while (isRunning) {
      printMenu(); // Выводим меню

      String input = scanner.nextLine().trim(); // Берем действие пользователя

      // Обрабатываем неправильный ввод пользователя по int значению
      // NOTE: Реализовал так, потому что в задании нельзя использовать try-catch
      if (!input.matches("\\d+")) {
        System.out.println("Ошибка: введите номер пункта");
        continue;
      }

      // Обрабатываем неправильный ввод пользователя вне пунктов меню
      int choice = Integer.parseInt(input);

      if (choice < 1 || choice > MENU_ITEMS.length) {
        System.out.printf("Ошибка: введите номер от 1 до %d\n", MENU_ITEMS.length);
        continue;
      }

      // Определяем нужное поведение программы по выбранному пункту меню
      switch (choice) {
        case 1: // Ввод исходных данных, как вручную
          matrix = getUserMatrix();
          result = null;
          break;
        case 2: // Так и сгенерированных случайным образом
          matrix = getRandomMatrix();
          result = null;
          break;
        case 3: // Выполнение алгоритма по заданию
          if (matrix == null) {
            System.out.println("Ошибка: сначала введите или сгенерируйте матрицу (пункты 1 или 2)");
          } else {
            result = getManipulatedMatrix(matrix);
            System.out.println("Алгоритм выполнен");
          }
          break;
        case 4: // Вывод результата
          if (result == null) {
            System.out.println("Ошибка: сначала выполните алгоритм (пункт 3)");
          } else {
            System.out.println("Результирующая матрица:");
            printMatrix(result);
          }
          break;
        case 5: // Завершение программы
          isRunning = false;
          break;
      }
    }
  }

  /**
   * Выводим меню программы
   */
  private static void printMenu() {
    System.out.println("Выберите действие: ");

    for (int i = 0; i < MENU_ITEMS.length; i++) {
      System.out.printf("%d. %s\n", i + 1, MENU_ITEMS[i]);
    }

    System.out.print("Ваш выбор: ");
  }

  /**
   * Возвращает исходную матрицу с пользовательским вводом.
   *
   * @return исходная матрица
   */
  private static int[][] getUserMatrix() {
    // Просим ввод у пользователя для определения размерности матрицы
    int rowsCount = getIntPositiveInput("Введите кол-во строк матрицы: ");
    int columnsCount = getIntPositiveInput("Введите кол-во столбцов матрицы: ");

    int[][] userMatrix = new int[rowsCount][columnsCount]; // Инициализируем матрицу по размерам

    // Заполняем матрицу с пользовательским вводом значений
    for (int i = 0; i < rowsCount; i++) {
      userMatrix[i] = getMatrixRowInput(columnsCount, i);
    }

    return userMatrix;
  }

  /**
   * Возвращает исходную матрицу со случайными данными.
   *
   * @return исходная матрица
   */
  private static int[][] getRandomMatrix() {
    int[][] randomMatrix = new int[random.nextInt(4, 8)]
        [random.nextInt(4, 8)]; // Инициализируем матрицу со случайной размерностью

    // Заполняем матрицу случайными значениями
    for (int i = 0; i < randomMatrix.length; i++) {
      for (int j = 0; j < randomMatrix[i].length; j++) {
        randomMatrix[i][j] = random.nextInt(-10, 10);
      }
    }

    // Выводим итог генерации
    System.out.println("Исходная сгенерированная матрица: ");
    printMatrix(randomMatrix);

    return randomMatrix;
  }

  /**
   * Запращивает у пользователя и возвращает целое положительное число. В случае ошибки выболняет
   * рекурсию.
   *
   * @param message сообщение пользовтелю перед вводом
   * @return позитивное целое число
   */
  private static int getIntPositiveInput(String message) {
    // Запрашиваем ввод
    System.out.print(message);
    String input = scanner.nextLine();

    // Через регулярное выражение обрабатываем возможные ошибки
    // NOTE: Реализовал так, потому что в задании нельзя использовать try-catch
    if (!input.matches("\\d+")) {
      System.out.println("Ошибка: введите положительное целое число!");
      return getIntPositiveInput(message); // Вызываем рекурсию в случае ошибки
    }

    int parsedValue = Integer.parseInt(input);

    if (!(parsedValue > 0)) {
      System.out.println("Ошибка: введите положительное целое число!");
      return getIntPositiveInput(message); // Вызываем рекурсию в случае ошибки
    }
    ;

    return parsedValue;
  }

  /**
   * Запрашивает у пользователя и возвращает массив целых чисел - строки матрицы.
   *
   * @param columnsCount   кол-во значений в строке
   * @param curRowPosition - номер текущей строки
   * @return массив-строка матрицы
   */
  private static int[] getMatrixRowInput(int columnsCount, int curRowPosition) {
    System.out.printf("Введите строку № %d: ", curRowPosition + 1);
    String input = scanner.nextLine().trim(); // Запрашиваем ввод

    // Обрабатываем через регулярное выражение некорректные символы и разделение чисел
    if (!input.matches("^-?\\d+(\\s+-?\\d+)*$")) {
      System.out.println("Ошибка: введите строку целых чисел, разделенных пробелом!");
      return getMatrixRowInput(columnsCount, curRowPosition); // Рекурсия при ошибке
    }

    String[] inputParts = input.split("\\s+"); // Разделяем на числа по пробелу

    // Обрабатыаем некорректное кол-во введеных чисел
    if (inputParts.length != columnsCount) {
      System.out.println("Ошибка: введите корректное кол-во столбцов строки!");
      return getMatrixRowInput(columnsCount, curRowPosition); // Рекурсия при ошибке
    }

    int[] result = new int[columnsCount]; // Инициализируем итоговый массив-строку

    // Заполняем его
    for (int i = 0; i < inputParts.length; i++) {
      result[i] = Integer.parseInt(inputParts[i]);
    }

    return result;
  }

  /**
   * Возвращает матрицу с суммой значений и правильным порядком по ней
   *
   * @param originMatrix исходная матрица
   * @return итоговая матрица
   */
  private static int[][] getManipulatedMatrix(int[][] originMatrix) {
    // Инициализируем расширенную матрицу
    int[][] extendedMatrix = new int[originMatrix.length][originMatrix[0].length + 1];

    // Заполняем сумму в расширенную матрицу на последний индекс строки
    for (int i = 0; i < originMatrix.length; i++) {
      int sum = 0; // Объявляем сумму
      for (int j = 0; j < originMatrix[i].length; j++) {
        sum += Math.abs(originMatrix[i][j]); // Считаем сумму
        extendedMatrix[i][j] = originMatrix[i][j]; // Заполняем расширенную матрицу
      }
      extendedMatrix[i][originMatrix[i].length] = sum; // Заполняем сумму на последний индекс строки
    }

    // Реализация сортировки строк по ее сумме
    boolean isSorted; // Флаг для понимая статуса сортировки
    do {
      isSorted = true; // Сбрасываем флаг

      for (int i = 0; i < extendedMatrix.length; i++) {
        int cursor = extendedMatrix[i][extendedMatrix[i].length - 1]; // Текущая сумма

        // Если это послендий элемент суммы, то пропускаем
        if (i + 1 >= extendedMatrix.length) {
          continue;
        }

        // Инициализируем и заполняем сумму следующей строки
        int nextCursor = extendedMatrix[i + 1][extendedMatrix[i].length - 1];

        // Если текущая сумма больше или равна следующей, то пропускаем
        if (cursor >= nextCursor) {
          continue;
        }

        // Иначе меняем флаг на ложь
        isSorted = false;

        int[] tempRow = extendedMatrix[i]; // Запоминаем временную строку

        // Меняем строки местами
        extendedMatrix[i] = extendedMatrix[i + 1];
        extendedMatrix[i + 1] = tempRow;
      }
    } while (!isSorted); // Если неотсортирована матрица, то начинаем заново

    return extendedMatrix;
  }

  /**
   * Вывод матрицы в терминал
   *
   * @param matrix матрица для вывода
   */
  private static void printMatrix(int[][] matrix) {
    // Проходимся по элементам и выводим их в терминал
    for (int[] row : matrix) {
      for (int column : row) {
        System.out.print(column + " ");
      }
      System.out.println();
    }
  }
}
