package pr3;

import java.util.Scanner;

/**
 * Вспомогательный класс для чтения и валидации пользовательского ввода.
 * Предоставляет методы для безопасного считывания строк, целых чисел
 * и чисел с плавающей точкой из стандартного потока ввода.
 */
public class InputReader {

  /** Сканер для чтения из стандартного потока ввода. */
  private final Scanner scanner;

  /**
   * Конструктор, принимающий сканер.
   *
   * @param scanner сканер для чтения ввода
   */
  public InputReader(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Считывает строку, удовлетворяющую ограничениям по длине.
   *
   * @param prompt приглашение для ввода
   * @param minLen минимальная длина строки (включительно)
   * @param maxLen максимальная длина строки (включительно)
   * @return введённая строка
   */
  public String readString(String prompt, int minLen, int maxLen) {
    while (true) {
      System.out.printf("%s: ", prompt);
      String value = scanner.nextLine().trim();
      if (value.length() < minLen || value.length() > maxLen) {
        System.out.printf(
            "Ошибка: строка должна содержать от %d до %d символов.%n", minLen, maxLen);
        continue;
      }
      return value;
    }
  }

  /**
   * Считывает целое число в указанном диапазоне.
   *
   * @param prompt приглашение для ввода
   * @param min минимальное допустимое значение (включительно)
   * @param max максимальное допустимое значение (включительно)
   * @return введённое целое число
   */
  public int readInt(String prompt, int min, int max) {
    while (true) {
      System.out.printf("%s (%d–%d): ", prompt, min, max);
      String input = scanner.nextLine().trim();
      if (!input.matches("-?\\d+")) {
        System.out.println("Ошибка: введите целое число.");
        continue;
      }
      long value;
      value = Long.parseLong(input);
      if (value < min || value > max) {
        System.out.printf("Ошибка: число должно быть от %d до %d.%n", min, max);
        continue;
      }
      return (int) value;
    }
  }

  /**
   * Считывает число с плавающей точкой в указанном диапазоне.
   *
   * @param prompt приглашение для ввода
   * @param min минимальное допустимое значение (включительно)
   * @param max максимальное допустимое значение (включительно)
   * @return введённое число
   */
  public double readDouble(String prompt, double min, double max) {
    while (true) {
      System.out.printf("%s (%.2f–%.2f): ", prompt, min, max);
      String input = scanner.nextLine().trim().replace(',', '.');
      double value;
      boolean parsed = true;
      double tmp = 0;
      try {
        tmp = Double.parseDouble(input);
      } catch (NumberFormatException e) {
        parsed = false;
      }
      if (!parsed) {
        System.out.println("Ошибка: введите число (например, 3.14).");
        continue;
      }
      value = tmp;
      if (value < min || value > max) {
        System.out.printf("Ошибка: число должно быть от %.2f до %.2f.%n", min, max);
        continue;
      }
      return value;
    }
  }

  /**
   * Считывает булево значение («да»/«нет»).
   *
   * @param prompt приглашение для ввода
   * @return {@code true}, если пользователь ввёл «да» (1), {@code false} — «нет» (2)
   */
  public boolean readBoolean(String prompt) {
    System.out.printf("%s (1 — да, 2 — нет): ", prompt);
    int choice = readInt("", 1, 2);
    return choice == 1;
  }

  /**
   * Считывает целое число без отображения диапазона.
   * Используется внутри меню для выбора пункта.
   *
   * @param min минимальное допустимое значение
   * @param max максимальное допустимое значение
   * @return введённое целое число
   */
  public int readMenuChoice(int min, int max) {
    while (true) {
      String input = scanner.nextLine().trim();
      if (!input.matches("-?\\d+")) {
        System.out.printf("Ошибка: введите число от %d до %d: ", min, max);
        continue;
      }
      long value = Long.parseLong(input);
      if (value < min || value > max) {
        System.out.printf("Ошибка: выберите от %d до %d: ", min, max);
        continue;
      }
      return (int) value;
    }
  }
}