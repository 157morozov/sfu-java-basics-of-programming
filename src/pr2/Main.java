package pr2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Запуск приложения.
 */
public class Main {

  private static final Scanner SCANNER = new Scanner(System.in);

  private static final String[] MENU_ITEMS = {
      "Добавить пустой объект (конструктор по умолчанию)",
      "Добавить объект с данными пользователя",
      "Редактировать поле объекта по индексу",
      "Вывести информацию обо всех объектах",
      "Сортировать список по выбранному полю",
      "Завершить работу программы",
  };

  /**
   * Главный метод программы.
   *
   * @param args аргументы командной строки.
   */
  public static void main(String[] args) {
    List<Student> students = new ArrayList<>();
    boolean running = true;

    while (running) {
      printMenu();
      int choice = readIntInRange(1, MENU_ITEMS.length);

      switch (choice) {
        case 1:
          addDefaultStudent(students);
          break;
        case 2:
          addStudentFromInput(students);
          break;
        case 3:
          editStudent(students);
          break;
        case 4:
          printAllStudents(students);
          break;
        case 5:
          sortStudents(students);
          break;
        case 6:
          running = false;
          System.out.println("Программа завершена");
          break;
        default:
          System.out.println("Ошибка: неизвестный пункт меню");
      }
    }
  }

  private static void addDefaultStudent(List<Student> students) {
    students.add(new Student());
    System.out.println(
        "Добавлен объект со значениями по умолчанию (индекс " + (students.size() - 1) + ")");
  }

  private static void addStudentFromInput(List<Student> students) {
    System.out.println("Добавление нового студента: ");

    String name = readString("Имя");
    String surname = readString("Фамилия");
    String patronymic = readString("Отчество");
    int age = readIntWithPrompt("Возраст (16–100)", 16, 100);
    double gpa = readDoubleWithPrompt("Средний балл (0.0–5.0)", 0.0, 5.0);
    int groupNumber = readIntWithPrompt("Номер группы (> 0)", 1, Integer.MAX_VALUE);
    double scholarship = readDoubleWithPrompt("Стипендия (руб., >= 0)", 0.0, Double.MAX_VALUE);

    String groupName = readString("Название учебной группы");
    int course = readIntWithPrompt("Курс (1–6)", 1, 6);
    StudyGroup group = new StudyGroup(groupName, course);

    Gender gender = readGender();

    Student student = new Student(
        name, surname, patronymic, age, gpa, groupNumber, scholarship, group, gender);
    students.add(student);
    System.out.println("Студент добавлен (индекс " + (students.size() - 1) + ")");
  }

  private static void editStudent(List<Student> students) {
    if (students.isEmpty()) {
      System.out.println("Список студентов пуст");
      return;
    }

    int index = readIndexInList(students);
    Student student = students.get(index);

    System.out.println("Выберите поле для редактирования:");
    String[] fields = {
        "Имя", "Фамилия", "Отчество", "Возраст", "Средний балл",
        "Номер группы", "Стипендия", "Учебная группа (название + курс)", "Пол"
    };
    for (int i = 0; i < fields.length; i++) {
      System.out.printf("%d. %s%n", i + 1, fields[i]);
    }

    int fieldChoice = readIntInRange(1, fields.length);

    switch (fieldChoice) {
      case 1:
        student.setName(readString("Новое имя"));
        break;
      case 2:
        student.setSurname(readString("Новая фамилия"));
        break;
      case 3:
        student.setPatronymic(readString("Новое отчество"));
        break;
      case 4:
        student.setAge(readIntWithPrompt("Новый возраст (16–100)", 16, 100));
        break;
      case 5:
        student.setGpa(
            readDoubleWithPrompt("Новый средний балл (0.0–5.0)", 0.0, 5.0));
        break;
      case 6:
        student.setGroupNumber(
            readIntWithPrompt("Новый номер группы (> 0)", 1, Integer.MAX_VALUE));
        break;
      case 7:
        student.setScholarship(
            readDoubleWithPrompt("Новая стипендия (>= 0)", 0.0, Double.MAX_VALUE));
        break;
      case 8:
        String groupName = readString("Новое название группы");
        int course = readIntWithPrompt("Новый курс (1–6)", 1, 6);
        student.setGroup(new StudyGroup(groupName, course));
        break;
      case 9:
        student.setGender(readGender());
        break;
      default:
        System.out.println("Ошибка: неизвестное поле");
    }

    System.out.println("Данные обновлены");
  }

  private static void printAllStudents(List<Student> students) {
    if (students.isEmpty()) {
      System.out.println("Список студентов пуст");
      return;
    }
    System.out.println("Список студентов:");
    for (int i = 0; i < students.size(); i++) {
      System.out.printf("%d. %s%n", i, students.get(i));
    }
  }

  private static void sortStudents(List<Student> students) {
    if (students.isEmpty()) {
      System.out.println("Список студентов пуст");
      return;
    }

    System.out.println("Выберите поле для сортировки:");
    String[] sortFields = {
        "Фамилия", "Имя", "Возраст", "Средний балл", "Номер группы", "Стипендия"
    };
    for (int i = 0; i < sortFields.length; i++) {
      System.out.printf("%d. %s%n", i + 1, sortFields[i]);
    }

    int sortChoice = readIntInRange(1, sortFields.length);
    Comparator<Student> comparator;

    switch (sortChoice) {
      case 1:
        comparator = Comparator.comparing(Student::getSurname);
        break;
      case 2:
        comparator = Comparator.comparing(Student::getName);
        break;
      case 3:
        comparator = Comparator.comparingInt(Student::getAge);
        break;
      case 4:
        comparator = Comparator.comparingDouble(Student::getGpa);
        break;
      case 5:
        comparator = Comparator.comparingInt(Student::getGroupNumber);
        break;
      case 6:
        comparator = Comparator.comparingDouble(Student::getScholarship);
        break;
      default:
        System.out.println("Ошибка: неизвестное поле сортировки");
        return;
    }

    students.sort(comparator);
    System.out.println("Список отсортирован по полю \"" + sortFields[sortChoice - 1] + "\"");
    printAllStudents(students);
  }

  private static void printMenu() {
    System.out.println("Выберите действие:");
    for (int i = 0; i < MENU_ITEMS.length; i++) {
      System.out.printf("%d. %s%n", i + 1, MENU_ITEMS[i]);
    }
    System.out.print("Ваш выбор: ");
  }

  private static int readIntInRange(int min, int max) {
    while (true) {
      String input = SCANNER.nextLine().trim();
      if (!input.matches("-?\\d+")) {
        System.out.printf("Ошибка: введите целое число от %d до %d: ", min, max);
        continue;
      }
      int value = Integer.parseInt(input);
      if (value < min || value > max) {
        System.out.printf("Ошибка: число должно быть от %d до %d: ", min, max);
        continue;
      }
      return value;
    }
  }

  private static int readIntWithPrompt(String prompt, int min, int max) {
    System.out.printf("%s: ", prompt);
    return readIntInRange(min, max);
  }

  private static double readDoubleInRange(double min, double max) {
    while (true) {
      String input = SCANNER.nextLine().trim().replace(',', '.');
      double value;
      try {
        value = Double.parseDouble(input);
      } catch (NumberFormatException e) {
        System.out.printf("Ошибка: введите число (напр. 3.75): ");
        continue;
      }
      if (value < min || value > max) {
        System.out.printf("Ошибка: число должно быть от %.2f до %.2f: ", min, max);
        continue;
      }
      return value;
    }
  }

  private static double readDoubleWithPrompt(String prompt, double min, double max) {
    System.out.printf("%s: ", prompt);
    return readDoubleInRange(min, max);
  }

  private static String readString(String prompt) {
    while (true) {
      System.out.printf("%s: ", prompt);
      String value = SCANNER.nextLine().trim();
      if (value.isBlank() || value.length() < 2 || value.length() > 50) {
        System.out.println("Ошибка: введите строку от 2 до 50 символов");
        continue;
      }
      return value;
    }
  }

  private static int readIndexInList(List<Student> students) {
    System.out.printf("Введите индекс объекта (0–%d): ", students.size() - 1);
    return readIntInRange(0, students.size() - 1);
  }

  private static Gender readGender() {
    System.out.println("Пол: 1. Мужской, 2. Женский");
    System.out.print("Ваш выбор: ");
    int choice = readIntInRange(1, 2);
    return choice == 1 ? Gender.MALE : Gender.FEMALE;
  }
}