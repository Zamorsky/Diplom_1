

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Ingredient;
import praktikum.IngredientType;
import java.util.Arrays;
import java.util.Collection;
import constants.Const;

@RunWith(Parameterized.class)
public class IngredientTests {

    private final IngredientType type;
    private final String name;
    private final float price;

    public IngredientTests(IngredientType type, String name, float price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> dataForTest() {
        return Arrays.asList(new Object[][] {
                { IngredientType.FILLING, "Бекон", 100.0f },
                { IngredientType.SAUCE, "Майонез", 30.0f },
                { IngredientType.FILLING, "Сыр", 50.0f },
                { IngredientType.SAUCE, "Кетчуп", 20.0f },
                { IngredientType.FILLING, "Помидор", 10.0f },
                { IngredientType.SAUCE, "Горчица", 15.0f }
        });
    }

    @Test
    public void testConstructorAndGetters() {
        // Создаем объект Ingredient
        Ingredient ingredient = new Ingredient(type, name, price);

        // Проверяем значения через геттеры
        Assert.assertEquals("Ошибка! Тип ингредиента не совпал с ожидаемым:", type, ingredient.getType());
        Assert.assertEquals("Ошибка! Название ингредиента не совпало с ожидаемым:", name, ingredient.getName());
        Assert.assertEquals("Ошибка! Цена ингредиента не совпала с ожидаемой:", price, ingredient.getPrice(), Const.DELTA);
    }
}