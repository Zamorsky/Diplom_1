import org.junit.Assert; // Используется для проверки результатов тестов (ассерты)
import org.junit.Before; // Аннотация для метода, который выполняется перед каждым тестом
import org.junit.Test; // Аннотация для обозначения тестового метода
import org.junit.runner.RunWith; // Используется для запуска тестов с помощью специального раннера
import org.mockito.Mock; // Аннотация для создания мока (заглушки)
import org.mockito.Mockito; // Основной инструмент для работы с моками (например, для задания поведения мока)
import org.mockito.junit.MockitoJUnitRunner; // Раннер, который интегрирует Mockito с JUnit
import praktikum.Bun; // Класс булочки
import praktikum.Burger; // Класс бургера
import praktikum.Ingredient; // Класс ингредиента
import praktikum.IngredientType; // Перечисление типов ингредиентов (начинка или соус)

import java.util.List; // Импортируем класс для работы со списками

// Аннотация @RunWith указывает, что тесты будут выполняться с помощью MockitoJUnitRunner,
// который автоматически обрабатывает аннотации @Mock.
@RunWith(MockitoJUnitRunner.class)
public class BurgerTests {

    // Аннотация @Mock создаёт заглушку (mock-объект) для класса Bun.
    // Это виртуальный объект, который мы будем использовать вместо реального объекта.
    @Mock
    private Bun mockBun;

    // Создаем мок для ингредиента. Это будет использоваться для тестирования добавления ингредиентов.
    @Mock
    private Ingredient mockIngredient1;

    @Mock
    private Ingredient mockIngredient2;

    // Экземпляр класса Burger, который будет тестироваться.
    private Burger burger;

    // Метод с аннотацией @Before выполняется перед каждым тестовым методом.
    // В данном случае мы инициализируем объект Burger.
    @Before
    public void setUp() {
        burger = new Burger(); // Создаём новый пустой бургер
    }

    // Тестируем метод установки булочки (setBuns).
    @Test
    public void testSetBuns() {
        // Задаём поведение для мока булочки: при вызове метода getName() возвращать "white bun".
        Mockito.when(mockBun.getName()).thenReturn("white bun");

        // Устанавливаем булочку в бургер.
        burger.setBuns(mockBun);

        // Проверяем, что булочка установлена правильно, вызывая метод getName().
        Assert.assertEquals("white bun", burger.bun.getName());
    }

    // Тестируем добавление ингредиентов в бургер.
    @Test
    public void testAddIngredient() {
        // Настраиваем мок ингредиента: метод getName() возвращает "hot sauce".
        Mockito.when(mockIngredient1.getName()).thenReturn("hot sauce");

        // Добавляем ингредиент в бургер.
        burger.addIngredient(mockIngredient1);

        // Получаем список ингредиентов из бургера.
        List<Ingredient> ingredients = burger.ingredients;

        // Проверяем, что в списке ингредиентов теперь один элемент.
        Assert.assertEquals(1, ingredients.size());

        // Проверяем, что первый ингредиент имеет имя "hot sauce".
        Assert.assertEquals("hot sauce", ingredients.get(0).getName());
    }

    // Тестируем удаление ингредиентов из бургера.
    @Test
    public void testRemoveIngredient() {
        // Добавляем два ингредиента в бургер.
        burger.addIngredient(mockIngredient1);
        Mockito.when(mockIngredient2.getName()).thenReturn("chili sauce");
        burger.addIngredient(mockIngredient2);

        // Удаляем первый ингредиент (индекс 0).
        burger.removeIngredient(0);

        // Проверяем, что после удаления остался только один ингредиент.
        List<Ingredient> ingredients = burger.ingredients;
        Assert.assertEquals(1, ingredients.size());

        // Проверяем, что оставшийся ингредиент — это "chili sauce".
        Assert.assertEquals("chili sauce", ingredients.get(0).getName());
    }

    // Тестируем перемещение ингредиентов внутри списка.
    @Test
    public void testMoveIngredient() {
        // Добавляем два ингредиента с заданными именами.
        Mockito.when(mockIngredient1.getName()).thenReturn("hot sauce");
        burger.addIngredient(mockIngredient1);

        Mockito.when(mockIngredient2.getName()).thenReturn("chili sauce");
        burger.addIngredient(mockIngredient2);

        // Перемещаем первый ингредиент (индекс 0) на позицию 1.
        burger.moveIngredient(0, 1);

        // Проверяем, что ингредиенты поменялись местами.
        List<Ingredient> ingredients = burger.ingredients;
        Assert.assertEquals("chili sauce", ingredients.get(0).getName());
        Assert.assertEquals("hot sauce", ingredients.get(1).getName());
    }

    // Тестируем расчёт общей стоимости бургера.
    @Test
    public void testGetPrice() {
        // Настраиваем моки для булочки и ингредиентов, задавая их цены.
        Mockito.when(mockBun.getPrice()).thenReturn(50.0f);
        Mockito.when(mockIngredient1.getPrice()).thenReturn(30.0f);
        Mockito.when(mockIngredient2.getPrice()).thenReturn(15.0f);

        // Устанавливаем булочку и добавляем два ингредиента.
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        // Вычисляем общую цену бургера.
        float price = burger.getPrice();

        // Ожидаемая цена: 2 булочки (по 50) + 2 ингредиента (30 + 15).
        float expectedPrice = 2 * 50.0f + 30.0f + 15.0f;

        // Проверяем, что вычисленная цена совпадает с ожидаемой (с допуском 0.001).
        Assert.assertEquals(expectedPrice, price, 0.001f);
    }

    // Тестируем создание чека (getReceipt).
    @Test
    public void testGetReceipt() {
        // Настраиваем моки для булочки и ингредиентов.
        Mockito.when(mockBun.getName()).thenReturn("white bun");
        Mockito.when(mockBun.getPrice()).thenReturn(50.0f);

        Mockito.when(mockIngredient1.getName()).thenReturn("hot sauce");
        Mockito.when(mockIngredient1.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(mockIngredient1.getPrice()).thenReturn(30.0f);

        Mockito.when(mockIngredient2.getName()).thenReturn("chili sauce");
        Mockito.when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(mockIngredient2.getPrice()).thenReturn(15.0f);

        // Устанавливаем булочку и добавляем ингредиенты.
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        // Получаем чек.
        String actualReceipt = burger.getReceipt();

        // Ожидаемый чек в виде строки.
        String expectedReceipt = "(==== white bun ====)\n" +
                "= filling hot sauce =\n" +
                "= filling chili sauce =\n" +
                "(==== white bun ====)\n" +
                "\nPrice: 145,000000\n";

        // Преобразуем символы перевода строки (\r\n -> \n) для унификации.
        actualReceipt = actualReceipt.replaceAll("\r\n", "\n");
        expectedReceipt = expectedReceipt.replaceAll("\r\n", "\n");

        // Проверяем, что сгенерированный чек соответствует ожидаемому.
        Assert.assertEquals(expectedReceipt, actualReceipt);
    }
}
