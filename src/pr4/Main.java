package pr4;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    String input = readLatinString(scanner);

    StringAnalyser lowerCaseAnalyser = new LowerCaseAnalyser();
    StringAnalyser upperCaseAnalyser = new UpperCaseAnalyser();

    System.out.println("Результаты анализа:");
    System.out.println("Строка: " + input);
    System.out.println("Строчных символов: " + lowerCaseAnalyser.analyse(input));
    System.out.println("Заглавных символов: " + upperCaseAnalyser.analyse(input));

    scanner.close();
  }

  /**
   * Считывает строку и проверяет, что в ней только латиница.
   *
   * @param scanner объект для чтения ввода
   * @return валидная строка
   */
  private static String readLatinString(Scanner scanner) {
    while (true) {
      System.out.print("Введите строку (только латиница): ");
      String str = scanner.nextLine();

      if (str.isEmpty()) {
        System.out.println("Ошибка: строка не должна быть пустой.");
        continue;
      }

      // Проверка на наличие только латинских букв (без кириллицы и спецсимволов)
      if (str.matches("^[a-zA-Z]+$")) {
        return str;
      } else {
        System.out.println("Ошибка: допускаются только латинские буквы (a-z, A-Z).");
      }
    }
  }
}