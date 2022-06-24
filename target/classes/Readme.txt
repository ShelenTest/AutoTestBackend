1. Тест getCategoryById:
    1.1. Запрос успешный
    1.2. в body ID = 1
    1.3. в body Title = "Food"

2. Тест createProductInFoodCategoryTest:
    2.1. Объект успешно создан
    2.2. body categoryTitle = "Food"
    2.3. Объект успешно удален

3. Тест modifyProductInFoodCategoryTest:
    3.1. Объект успешно создан
    3.2. После замены значения поля price (300):
        3.2.1. Объект успешно модифицирован
        3.2.2. ID объекта такой же, как при создании
        3.2.3. Значение поля price = 300
    3.3. Объект успешно удален

4. Test getProductByIdInFoodCategoryTest:
    4.1. Объект создан
    4.2. Get-запрос прошел
    4.3. body response categoryTitle = "Food"
    4.4. Объект удален

5. Тест getProductsInFoodCategoryTest:
    Возвращает непустое значение



