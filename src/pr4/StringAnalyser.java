package pr4;

/**
 * Интерфейс для анализа строки по определенному критерию.
 */
@FunctionalInterface
public interface StringAnalyser {

  /**
   * Анализирует строку и возвращает целочисленный результат.
   *
   * @param str строка для анализа
   * @return результат анализа
   */
  int analyse(String str);
}