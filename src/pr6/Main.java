package pr6;

import java.util.Scanner;

/**
 * Точка входа в приложение для демонстрации обобщённого кольцевого
 * однонаправленного списка.
 */
public class Main {

  /** Объект для чтения данных из консоли. */
  private static final Scanner SCANNER = new Scanner(System.in);

  /** Пункты главного меню. */
  private static final String[] MENU_ITEMS = {
      "Проверить, список пуст/не пуст",
      "Установить указатель в начало списка",
      "Добавить элемент за указателем",
      "Удалить элемент за указателем",
      "Просмотреть элемент за указателем",
      "Переместить указатель вправо",
      "Обменять значения конца списка и элемента за указателем",
      "Обменять значения начала списка и элемента за указателем",
      "Вывести список на экран",
      "Завершить работу программы"
  };

  /** Пункты меню выбора типа данных. */
  private static final String[] DATA_MODE_ITEMS = {
      "Работать со строковыми данными (String)",
      "Работать с целочисленными данными (int через Integer)"
  };

  /**
   * Главный метод программы.
   *
   * @param args аргументы командной строки.
   */
  public static void main(String[] args) {
    DataMode dataMode = readDataMode();

    if (dataMode == DataMode.INTEGER) {
      runIntegerList();
    } else {
      runStringList();
    }
  }

  /**
   * Запускает демонстрационное меню для списка строк.
   */
  private static void runStringList() {
    CircularSinglyLinkedList<String> list = new CircularSinglyLinkedList<>();
    boolean running = true;

    while (running) {
      printMenu();
      int choice = readIntInRange(1, MENU_ITEMS.length);
      running = processStringMenuChoice(list, choice);
    }
  }

  /**
   * Запускает демонстрационное меню для списка целых чисел.
   */
  private static void runIntegerList() {
    CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
    boolean running = true;

    while (running) {
      printMenu();
      int choice = readIntInRange(1, MENU_ITEMS.length);
      running = processIntegerMenuChoice(list, choice);
    }
  }

  /**
   * Обрабатывает выбранный пункт меню для строкового списка.
   *
   * @param list список строк.
   * @param choice выбранный пункт меню.
   * @return {@code true}, если программа должна продолжить работу.
   */
  private static boolean processStringMenuChoice(
      CircularSinglyLinkedList<String> list, int choice) {

    try {
      switch (choice) {
        case 1:
          printListState(list);
          break;
        case 2:
          list.setPointerToStart();
          System.out.println("Указатель установлен в начало списка");
          break;
        case 3:
          System.out.print("Введите строку: ");
          list.addAfterPointer(SCANNER.nextLine());
          System.out.println("Элемент добавлен");
          break;
        case 4:
          System.out.println("Удалён элемент: " + list.removeAfterPointer());
          break;
        case 5:
          System.out.println("Элемент за указателем: " + list.getAfterPointer());
          break;
        case 6:
          list.movePointerRight();
          System.out.println("Указатель перемещён вправо");
          break;
        case 7:
          list.swapTailAndAfterPointer();
          System.out.println("Значения конца списка и элемента за указателем обменяны");
          break;
        case 8:
          list.swapHeadAndAfterPointer();
          System.out.println("Значения начала списка и элемента за указателем обменяны");
          break;
        case 9:
          printList(list);
          break;
        case 10:
          System.out.println("Программа завершена");
          return false;
        default:
          System.out.println("Ошибка: неизвестный пункт меню");
      }
    } catch (InvalidListOperationException ex) {
    }

    return true;
  }

  /**
   * Обрабатывает выбранный пункт меню для списка целых чисел.
   *
   * @param list список целых чисел.
   * @param choice выбранный пункт меню.
   * @return {@code true}, если программа должна продолжить работу.
   */
  private static boolean processIntegerMenuChoice(
      CircularSinglyLinkedList<Integer> list, int choice) {

    try {
      switch (choice) {
        case 1:
          printListState(list);
          break;
        case 2:
          list.setPointerToStart();
          System.out.println("Указатель установлен в начало списка");
          break;
        case 3:
          int value = readInt("Введите целое число: ");
          list.addAfterPointer(value);
          System.out.println("Элемент добавлен");
          break;
        case 4:
          System.out.println("Удалён элемент: " + list.removeAfterPointer());
          break;
        case 5:
          System.out.println("Элемент за указателем: " + list.getAfterPointer());
          break;
        case 6:
          list.movePointerRight();
          System.out.println("Указатель перемещён вправо");
          break;
        case 7:
          list.swapTailAndAfterPointer();
          System.out.println("Значения конца списка и элемента за указателем обменяны");
          break;
        case 8:
          list.swapHeadAndAfterPointer();
          System.out.println("Значения начала списка и элемента за указателем обменяны");
          break;
        case 9:
          printList(list);
          break;
        case 10:
          System.out.println("Программа завершена");
          return false;
        default:
          System.out.println("Ошибка: неизвестный пункт меню");
      }
    } catch (InvalidListOperationException ex) {
    }

    return true;
  }

  /**
   * Считывает режим работы приложения.
   *
   * @return выбранный режим работы.
   */
  private static DataMode readDataMode() {
    System.out.println("Выберите тип данных для демонстрации коллекции:");
    for (int i = 0; i < DATA_MODE_ITEMS.length; i++) {
      System.out.println((i + 1) + ". " + DATA_MODE_ITEMS[i]);
    }

    int choice = readIntInRange(1, DATA_MODE_ITEMS.length);
    DataMode dataMode = choice == 1 ? DataMode.STRING : DataMode.INTEGER;
    System.out.println("Выбран режим: " + dataMode.getLabel());
    return dataMode;
  }

  /**
   * Выводит главное меню на экран.
   */
  private static void printMenu() {
    System.out.println();
    System.out.println("Меню:");
    for (int i = 0; i < MENU_ITEMS.length; i++) {
      System.out.println((i + 1) + ". " + MENU_ITEMS[i]);
    }
  }

  /**
   * Выводит состояние списка.
   *
   * @param list список для проверки.
   */
  private static void printListState(CircularSinglyLinkedList<?> list) {
    if (list.isEmpty()) {
      System.out.println("Список пуст");
    } else {
      System.out.println("Список не пуст. Количество элементов: " + list.size());
    }
  }

  /**
   * Выводит список на экран.
   *
   * @param list список для вывода.
   */
  private static void printList(CircularSinglyLinkedList<?> list) {
    System.out.println(list.toDisplayString());
  }

  /**
   * Считывает целое число в заданном диапазоне.
   *
   * @param min минимальное допустимое значение.
   * @param max максимальное допустимое значение.
   * @return введённое целое число.
   */
  private static int readIntInRange(int min, int max) {
    int value;
    while (true) {
      value = readInt("Ваш выбор: ");
      if (value >= min && value <= max) {
        return value;
      }
      System.out.println("Ошибка: введите число от " + min + " до " + max);
    }
  }

  /**
   * Считывает целое число из консоли.
   *
   * @param message сообщение перед вводом.
   * @return введённое целое число.
   */
  private static int readInt(String message) {
    while (true) {
      System.out.print(message);
      String input = SCANNER.nextLine();
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException ex) {
        System.out.println("Ошибка: необходимо ввести целое число");
      }
    }
  }
}
