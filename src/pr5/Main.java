package pr5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Main {

  /**
   * Логгер уровня приложения. Настраивается в {@link #configureLogging()}.
   */
  private static final Logger APP_LOGGER = Logger.getLogger(Main.class.getName());

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
   * Настраивает логирование: устанавливает уровень {@code INFO} и форматирует вывод через
   * {@link SimpleFormatter}, чтобы журнал читался отдельно от стандартного вывода программы.
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
   * Паттерн 1 — простой перехват и
   * паттерн 3 — подавление исключений:
   * если конструктор {@link Student#Student()} выбросил {@link InvalidFieldException},
   * перехватываем её, выводим сообщение об ошибке и дополнительно отображаем все подавленные
   * исключения через {@link Throwable#getSuppressed()}.
   *
   * @param students список студентов, в который добавляется новый объект.
   */
  private static void addDefaultStudent(List<Student> students) {
    try {
      students.add(new Student());
      System.out.println(
          "Добавлен объект со значениями по умолчанию (индекс " + (students.size() - 1) + ")");
      APP_LOGGER.info("Добавлен студент по умолчанию, всего: " + students.size());
    } catch (InvalidFieldException ex) {
      System.out.println("Ошибка создания студента по умолчанию: " + ex.getMessage());
      Throwable[] suppressed = ex.getSuppressed();
      if (suppressed.length > 0) {
        System.out.println("  Подавленные исключения (" + suppressed.length + "):");
        for (Throwable s : suppressed) {
          System.out.println("    → " + s.getMessage());
        }
      }
      APP_LOGGER.log(Level.WARNING, "Не удалось создать студента по умолчанию", ex);
    }
  }

  /**
   * Запрашивает у пользователя данные и добавляет нового студента.
   * Паттерн 2 — повторное генерирование / цепочка исключений:
   * конструктор {@link Student} внутри себя оборачивает {@link StudentAgeException} в
   * {@link InvalidFieldException}; здесь мы перехватываем её, печатаем сообщение верхнего уровня и,
   * если есть причина, раскрываем цепочку.
   *
   * @param students список студентов.
   */
  private static void addStudentFromInput(List<Student> students) {
    System.out.println("Добавление нового студента:");

    try {
      String name = readString("Имя");
      String surname = readString("Фамилия");
      String patronymic = readString("Отчество");
      int age = readIntWithPrompt("Возраст (16–100)", 16, 100);
      double gpa = readDoubleWithPrompt("Средний балл (0.0–5.0)", 0.0, 5.0);
      int groupNumber = readIntWithPrompt("Номер группы (> 0)", 1, Integer.MAX_VALUE);
      double scholarship = readDoubleWithPrompt("Стипендия (руб., >= 0)", 0.0, Double.MAX_VALUE);

      StudyGroup group = readStudyGroup();
      Gender gender = readGender();

      Student student = new Student(
          name, surname, patronymic, age, gpa, groupNumber, scholarship, group, gender);
      students.add(student);
      System.out.println("Студент добавлен (индекс " + (students.size() - 1) + ")");
      APP_LOGGER.info("Добавлен новый студент: " + student.getFullName());

    } catch (InvalidFieldException ex) {
      System.out.println("Ошибка: " + ex.getMessage()
          + " (поле: «" + ex.getFieldName() + "»)");
      Throwable cause = ex.getCause();
      if (cause != null) {
        System.out.println("  Причина: " + cause.getMessage());
        if (cause instanceof StudentAgeException) {
          StudentAgeException ageEx = (StudentAgeException) cause;
          System.out.println("  Недопустимый возраст: " + ageEx.getInvalidAge());
        }
      }
      APP_LOGGER.log(Level.WARNING, "Ошибка при добавлении студента", ex);
    }
  }

  /**
   * Редактирует указанное поле существующего студента.
   * Паттерн 1 — простой перехват: каждый сеттер может выбросить
   * {@link InvalidFieldException} или {@link StudentAgeException}; оба типа перехватываются и
   * отображаются пользователю.
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
      System.out.println("Данные обновлены");
      APP_LOGGER.info("Обновлён студент [" + index + "]: поле " + fields[fieldChoice - 1]);

    } catch (InvalidFieldException ex) {
      System.out.println("Ошибка обновления поля «" + ex.getFieldName() + "»: "
          + ex.getMessage());
      APP_LOGGER.log(Level.WARNING, "Ошибка редактирования студента", ex);
    } catch (StudentAgeException ex) {
      System.out.println("Ошибка: недопустимый возраст " + ex.getInvalidAge()
          + ". " + ex.getMessage());
      APP_LOGGER.log(Level.WARNING, "Ошибка возраста при редактировании", ex);
    }
  }

  /**
   * Выводит информацию обо всех студентах в списке.
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
   * Сортирует список студентов по выбранному пользователем полю.
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
    System.out.println(
        "Список отсортирован по полю \"" + sortFields[sortChoice - 1] + "\"");
    printAllStudents(students);
  }

  /**
   * Выводит главное меню программы.
   */
  private static void printMenu() {
    System.out.println("Выберите действие:");
    for (int i = 0; i < MENU_ITEMS.length; i++) {
      System.out.printf("%d. %s%n", i + 1, MENU_ITEMS[i]);
    }
    System.out.print("Ваш выбор: ");
  }

  /**
   * Читает из стандартного ввода целое число в диапазоне [{@code min}; {@code max}].
   *
   * @param min минимально допустимое значение.
   * @param max максимально допустимое значение.
   * @return введённое пользователем число.
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
   * Выводит подсказку и читает целое число в диапазоне [{@code min}; {@code max}].
   *
   * @param prompt подсказка пользователю.
   * @param min минимально допустимое значение.
   * @param max максимально допустимое значение.
   * @return введённое пользователем число.
   */
  private static int readIntWithPrompt(String prompt, int min, int max) {
    System.out.printf("%s: ", prompt);
    return readIntInRange(min, max);
  }

  /**
   * Читает из стандартного ввода вещественное число в диапазоне [{@code min}; {@code max}].
   *
   * @param min минимально допустимое значение.
   * @param max максимально допустимое значение.
   * @return введённое пользователем число.
   */
  private static double readDoubleInRange(double min, double max) {
    while (true) {
      String input = SCANNER.nextLine().trim().replace(',', '.');
      double value;
      try {
        value = Double.parseDouble(input);
      } catch (NumberFormatException e) {
        System.out.print("Ошибка: введите число (напр. 3.75): ");
        continue;
      }
      if (value < min || value > max) {
        System.out.printf("Ошибка: число должно быть от %.2f до %.2f: ", min, max);
        continue;
      }
      return value;
    }
  }

  /**
   * Выводит подсказку и читает вещественное число в диапазоне [{@code min}; {@code max}].
   *
   * @param prompt подсказка пользователю.
   * @param min минимально допустимое значение.
   * @param max максимально допустимое значение.
   * @return введённое пользователем число.
   */
  private static double readDoubleWithPrompt(String prompt, double min, double max) {
    System.out.printf("%s: ", prompt);
    return readDoubleInRange(min, max);
  }

  /**
   * Читает строку от пользователя, проверяя длину (2–50 символов).
   *
   * @param prompt подсказка пользователю.
   * @return введённая строка.
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
   * Читает индекс существующего студента из диапазона [0; size-1].
   *
   * @param students список студентов.
   * @return допустимый индекс.
   */
  private static int readIndexInList(List<Student> students) {
    System.out.printf("Введите индекс объекта (0–%d): ", students.size() - 1);
    return readIntInRange(0, students.size() - 1);
  }

  /**
   * Читает пол студента интерактивно.
   *
   * @return выбранное значение {@link Gender}.
   */
  private static Gender readGender() {
    System.out.println("Пол: 1. Мужской  2. Женский");
    System.out.print("Ваш выбор: ");
    int choice = readIntInRange(1, 2);
    return choice == 1 ? Gender.MALE : Gender.FEMALE;
  }

  /**
   * Запрашивает у пользователя данные учебной группы и создаёт объект {@link StudyGroup}.
   * При ошибке создания группы выводит сообщение и повторяет запрос.
   *
   * @return корректно заполненный объект {@link StudyGroup}.
   */
  private static StudyGroup readStudyGroup() {
    while (true) {
      String groupName = readString("Название учебной группы");
      int course = readIntWithPrompt(
          "Курс (" + StudyGroup.MIN_COURSE + "–" + StudyGroup.MAX_COURSE + ")",
          StudyGroup.MIN_COURSE, StudyGroup.MAX_COURSE);
      try {
        return new StudyGroup(groupName, course);
      } catch (InvalidFieldException ex) {
        System.out.println("Ошибка создания группы: " + ex.getMessage() + ". Повторите ввод.");
        APP_LOGGER.log(Level.WARNING, "Невалидные данные группы", ex);
      }
    }
  }
}
