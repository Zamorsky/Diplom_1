import constants.Const;
import org.junit.Test; // Импортируем аннотацию @Test
import org.junit.runner.RunWith; // Импортируем аннотацию @RunWith для указания параметризованного запуска
import org.junit.runners.Parameterized; // Импортируем класс Parameterized для работы с параметризацией
import praktikum.Bun;

import java.util.Arrays; // Для удобного создания списка массивов
import java.util.Collection; // Интерфейс Collection используется для параметров

import static org.junit.Assert.assertEquals; // Для проверки значений в тестах

/**
 * Класс тестов для проверки работы класса Bun с использованием параметризации.
 */
@RunWith(Parameterized.class) // Указываем, что тесты будут выполняться с использованием параметризованного запуска
public class BunTests {

    // Поля для параметров, которые будут использоваться в тестах
    private final String name; // Название булочки, передаваемое в конструктор
    private final float price; // Цена булочки, передаваемая в конструктор
    private final String expectedName; // Ожидаемое название булочки, которое будем проверять
    private final float expectedPrice; // Ожидаемая цена булочки, которую будем проверять


    public BunTests(String name, float price, String expectedName, float expectedPrice) {
        this.name = name; // Инициализируем поле name
        this.price = price; // Инициализируем поле price
        this.expectedName = expectedName; // Инициализируем поле expectedName
        this.expectedPrice = expectedPrice; // Инициализируем поле expectedPrice
    }

    /**
     * Метод с аннотацией @Parameterized.Parameters.
     * Этот метод должен возвращать коллекцию массивов параметров, где каждый массив — это набор данных для одного теста.
     *
     * @return Коллекция массивов параметров.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        // Создаём список массивов параметров.
        // Каждый массив содержит: входные данные (name, price) и ожидаемые значения (expectedName, expectedPrice).
        return Arrays.asList(new Object[][]{
                {"black bun", 50.0f, "black bun", 50.0f},  // Обычный случай: валидное название и цена
                {"white bun", -10.0f, "white bun", -10.0f},      // Отрицательная цена: проверяем, что она корректно сохраняется
                {"red bun", 0.0f, "red bun", 0.0f},      // Нулевая цена: проверяем, что это поддерживается
                {"", 25.0f, "", 25.0f},              // Пустое название: должно сохраняться как есть
                {null, 15.0f, null, 15.0f}           // Название null: проверяем, что это допустимо
        });
    }

    /**
     * Тестовый метод, который будет запускаться для каждого набора параметров.
     * Проверяет, что объект Bun создаётся корректно и возвращает ожидаемые значения через геттеры.
     */
    @Test
    public void testBunCreation() {
        // Act: Создаём объект Bun с заданными параметрами
        Bun bun = new Bun(name, price);

        // Assert: Проверяем, что геттеры возвращают ожидаемые значения
        assertEquals("Ошибка! Название булочки не совпало с ожидаемым:",expectedName, bun.getName()); // Проверяем название булочки
        assertEquals("Ошибка! Цена булочки не совпала с ожидаемой:",expectedPrice, bun.getPrice(), Const.DELTA); // Проверяем цену булочки с допустимой погрешностью 0.01
    }
}
