/*
Главный файл проекта.
Предоставляет меню для запуска заданий по практическим работам.
*/

import java.util.Scanner;

public class Main {

  private static final Scanner scanner = new Scanner(System.in);

  private static final String[] TASKS = {
      "Сортировка матрицы по сумме модулей элементов строк",
  };

  public static void main(String[] args) {
    System.out.println("===== Практические задания по основам Java =====");

    boolean isRunning = true;
    while (isRunning) {
      printMenu();

      String input = scanner.nextLine().trim();

      if (input.equalsIgnoreCase("q")) {
        isRunning = false;
        System.out.println("Выход из программы");
        continue;
      }

      if (!input.matches("\\d+")) {
        System.out.println("Ошибка: введите номер задания или 'q' для выхода");
        continue;
      }

      int choice = Integer.parseInt(input);

      if (choice < 1 || choice > TASKS.length) {
        System.out.printf("Ошибка: введите номер от 1 до %d\n", TASKS.length);
        continue;
      }

      runTask(choice);
    }
  }

  private static void printMenu() {
    System.out.println("Выберите задание: ");
    for (int i = 0; i < TASKS.length; i++) {
      System.out.printf("%d. %s\n", i + 1, TASKS[i]);
    }
    System.out.println("q. Выйти");
    System.out.print("Ваш выбор: ");
  }

  private static void runTask(int taskNumber) {
    switch (taskNumber) {
      case 1:
        System.out.println("--- " + TASKS[0] + " ---");
        pr1.Main.main(null);
        break;
      default:
        System.out.println("Задание не найдено");
        break;
    }
    System.out.println("--- Задание завершено, возврат в меню ---");
  }
}
