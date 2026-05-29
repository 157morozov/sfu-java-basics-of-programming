package pr5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Точка входа в приложение для работы со списком студентов.
 */
public class Main {

  /** Логгер уровня приложения. */
  private static final Logger APP_LOGGER = Logger.getLogger(Main.class.getName());

  /** Объект для чтения данных из консоли. */
  private static final Scanner SCANNER = new Scanner(System.in);

  /** Пункты главного меню. */
  private static final String[] MENU_ITEMS = {
      "Добавить пустой объект (конструктор по умолчанию)",
      "Добавить объект с данными пользователя",
      "Редактировать поле объекта по индексу",
      "Вывести информацию обо всех объектах",
      "Сортировать список по выбранному полю",
      "Завершить работу программы"
  };

  /**
   * Главный метод программы.
   *
   * @param args аргументы командной строки.
   */
  public static void main(String[] args) {
    configureLogging();

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
          APP_LOGGER.info("Приложение завершено");
          break;
        default:
          System.out.println("Ошибка: неизвестный пункт меню");
      }
    }
  }

  /**
   * Настраивает протоколирование приложения.
   */
  private static void configureLogging() {
    Logger rootLogger = Logger.getLogger("");

    for (java.util.logging.Handler h : rootLogger.getHandlers()) {
      rootLogger.removeHandler(h);
    }

    ConsoleHandler handler = new ConsoleHandler();
    handler.setLevel(Level.ALL);
    handler.setFormatter(new SimpleFormatter());
    rootLogger.addHandler(handler);
    rootLogger.setLevel(Level.INFO);
  }

  /**
   * Добавляет студента со значениями по умолчанию.
   * Простой перехват исключения показан в конструкции {@code try-catch}; блок {@code catch}
   * оставлен пустым по требованию задания преподавателя. Реакция интерфейса выполняется после
   * конструкции {@code try-catch} по факту успешного или неуспешного создания объекта.
   *
   * @param students список студентов.
   */
  private static void addDefaultStudent(List<Student> students) {
    Student student = null;

    try {
      student = new Student();
    } catch (InvalidFieldException ex) {
    }

    if (student == null) {
      System.out.println("Ошибка: студент со значениями по умолчанию не создан");
      APP_LOGGER.warning("Не удалось создать студента по умолчанию");
      return;
    }

    students.add(student);
    System.out.println(
        "Добавлен объект со значениями по умолчанию (индекс " + (students.size() - 1) + ")");
    APP_LOGGER.info("Добавлен студент по умолчанию, всего: " + students.size());
  }

  /**
   * Запрашивает у пользователя данные и добавляет нового студента.
   * Повторное генерирование и связывание в цепочку реализованы в конструкторе {@link Student}:
   * некорректный возраст связывается с {@link InvalidFieldException} через причину исключения.
   * Блок {@code catch} здесь намеренно пустой, а пользователь получает сообщение после проверки
   * результата создания объекта.
   *
   * @param students список студентов.
   */
  private static void addStudentFromInput(List<Student> students) {
    System.out.println("Добавление нового студента:");

    Student student = null;
    String name = readString("Имя");
    String surname = readString("Фамилия");
    String patronymic = readString("Отчество");
    int age = readIntWithPrompt("Возраст (16–100)", 16, 100);
    double gpa = readDoubleWithPrompt("Средний балл (0.0–5.0)", 0.0, 5.0);
    int groupNumber = readIntWithPrompt("Номер группы (> 0)", 1, Integer.MAX_VALUE);
    double scholarship = readDoubleWithPrompt("Стипендия (руб., >= 0)", 0.0, Double.MAX_VALUE);
    StudyGroup group = readStudyGroup();
    Gender gender = readGender();

    try {
      student = new Student(
          name, surname, patronymic, age, gpa, groupNumber, scholarship, group, gender);
    } catch (InvalidFieldException ex) {
    }

    if (student == null) {
      System.out.println("Ошибка: студент не добавлен. Проверьте введённые данные.");
      APP_LOGGER.warning("Ошибка при добавлении студента");
      return;
    }

    students.add(student);
    System.out.println("Студент добавлен (индекс " + (students.size() - 1) + ")");
    APP_LOGGER.info("Добавлен новый студент: " + student.getFullName());
  }

  /**
   * Редактирует выбранное поле существующего студента.
   * Перехват исключений выполняется пустыми блоками {@code catch}; сообщение пользователю
   * выводится после конструкции {@code try-catch}.
   *
   * @param students список студентов.
   */
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
    boolean updated = false;

    try {
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
          student.setGpa(readDoubleWithPrompt("Новый средний балл (0.0–5.0)", 0.0, 5.0));
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
          student.setGroup(readStudyGroup());
          break;
        case 9:
          student.setGender(readGender());
          break;
        default:
          System.out.println("Ошибка: неизвестное поле");
      }
      updated = true;
    } catch (InvalidFieldException ex) {
    } catch (StudentAgeException ex) {
    }

    if (updated) {
      System.out.println("Данные обновлены");
      APP_LOGGER.info("Обновлён студент [" + index + "]: поле " + fields[fieldChoice - 1]);
    } else {
      System.out.println("Ошибка: данные не обновлены. Значение не прошло проверку.");
      APP_LOGGER.warning("Ошибка редактирования студента");
    }
  }

  /**
   * Выводит информацию обо всех студентах.
   *
   * @param students список студентов.
   */
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

  /**
   * Сортирует список студентов по выбранному полю.
   *
   * @param students список студентов.
   */
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

  /**
   * Выводит главное меню.
   */
  private static void printMenu() {
    System.out.println("Выберите действие:");
    for (int i = 0; i < MENU_ITEMS.length; i++) {
      System.out.printf("%d. %s%n", i + 1, MENU_ITEMS[i]);
    }
    System.out.print("Ваш выбор: ");
  }

  /**
   * Читает целое число из заданного диапазона.
   *
   * @param min минимальное значение.
   * @param max максимальное значение.
   * @return введённое целое число.
   */
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

  /**
   * Выводит подсказку и читает целое число.
   *
   * @param prompt подсказка.
   * @param min минимальное значение.
   * @param max максимальное значение.
   * @return введённое целое число.
   */
  private static int readIntWithPrompt(String prompt, int min, int max) {
    System.out.printf("%s: ", prompt);
    return readIntInRange(min, max);
  }

  /**
   * Читает вещественное число из заданного диапазона.
   *
   * @param min минимальное значение.
   * @param max максимальное значение.
   * @return введённое вещественное число.
   */
  private static double readDoubleInRange(double min, double max) {
    while (true) {
      String input = SCANNER.nextLine().trim().replace(',', '.');
      if (!input.matches("-?((\\d+\\.\\d+)|(\\d+)|(\\.\\d+))")) {
        System.out.print("Ошибка: введите число (напр. 3.75): ");
        continue;
      }
      double value = Double.parseDouble(input);
      if (value < min || value > max) {
        System.out.printf("Ошибка: число должно быть от %.2f до %.2f: ", min, max);
        continue;
      }
      return value;
    }
  }

  /**
   * Выводит подсказку и читает вещественное число.
   *
   * @param prompt подсказка.
   * @param min минимальное значение.
   * @param max максимальное значение.
   * @return введённое вещественное число.
   */
  private static double readDoubleWithPrompt(String prompt, double min, double max) {
    System.out.printf("%s: ", prompt);
    return readDoubleInRange(min, max);
  }

  /**
   * Читает строку и проверяет её длину.
   *
   * @param prompt подсказка.
   * @return корректная строка.
   */
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

  /**
   * Читает индекс существующего студента.
   *
   * @param students список студентов.
   * @return индекс студента.
   */
  private static int readIndexInList(List<Student> students) {
    System.out.printf("Введите индекс объекта (0–%d): ", students.size() - 1);
    return readIntInRange(0, students.size() - 1);
  }

  /**
   * Читает пол студента.
   *
   * @return выбранный пол.
   */
  private static Gender readGender() {
    System.out.println("Пол: 1. Мужской  2. Женский");
    System.out.print("Ваш выбор: ");
    int choice = readIntInRange(1, 2);
    return choice == 1 ? Gender.MALE : Gender.FEMALE;
  }

  /**
   * Читает учебную группу. Если конструктор выбрасывает исключение, блок {@code catch}
   * остаётся пустым, а сообщение пользователю выводится после проверки результата.
   *
   * @return корректная учебная группа.
   */
  private static StudyGroup readStudyGroup() {
    while (true) {
      String groupName = readString("Название учебной группы");
      int course = readIntWithPrompt(
          "Курс (" + StudyGroup.MIN_COURSE + "–" + StudyGroup.MAX_COURSE + ")",
          StudyGroup.MIN_COURSE, StudyGroup.MAX_COURSE);
      StudyGroup group = null;

      try {
        group = new StudyGroup(groupName, course);
      } catch (InvalidFieldException ex) {
      }

      if (group != null) {
        return group;
      }
      System.out.println("Ошибка создания группы. Повторите ввод.");
      APP_LOGGER.warning("Невалидные данные группы");
    }
  }
}
