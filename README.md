# XML File Storage Service

Це простий Spring Boot додаток для завантаження, зберігання та обробки XML файлів з подальшим перетворенням у JSON.

## Особливості
- Завантаження XML файлів (перевірка формату імені, конвертація у JSON, збереження у файловій системі).
- Оновлення файлу (замінює існуючий файл).
- Видалення файлів.
- Отримання вмісту файлів.
- Фільтрація файлів за датою, типом або клієнтом.

## Формат імені файлу
customer_type_date.xml

## Конфігурація
У `application.yml` вказана директорія для збереження JSON файлів:
```yaml
file:
  storage:
    dir:
      name: json-files
```
При запуску додатку директорія json-files буде створена автоматично, якщо вона не існує.

Встановлення та запуск
Попередні вимоги:

    Java 17+
    Maven
    
git clone <repo-url>
cd xml-file-storage
mvn clean install
mvn spring-boot:run

API документація

1. Upload XML файл
POST /api/v1/files/upload

Параметри:
    file — XML файл (Multipart)
![image](https://github.com/user-attachments/assets/fed6fbe7-bd39-484d-9e18-ba082e76abef)


Якщо ім’я файлу не відповідає шаблону — повертається помилка 400 Bad Request.
![image](https://github.com/user-attachments/assets/c2370627-924d-4099-a1bc-d6287a167afd)


2. Update існуючий файл
PUT /api/v1/files/upload

Параметри:
    file — XML файл (Multipart)
![image](https://github.com/user-attachments/assets/e4c8c4f6-d9a5-4840-a597-ee08afaeddde)

Якщо такого файлу не існує - повертається помилка 404 Not Found.

3. Видалити файл
DELETE /api/v1/files

Параметри:
    fileName — назва файлу
![image](https://github.com/user-attachments/assets/2ee0666a-1878-488e-a719-0147df0918f4)

Якщо такого файлу не існує - повертається помилка 404 Not Found.

4. Фільтрація файлів
GET /api/v1/files/filter

Параметри (опціонально):
    customer — ім'я клієнта
    type — тип файлу
    date — дата (формат: yyyy-MM-dd)

![image](https://github.com/user-attachments/assets/3c321603-c503-45c6-8be6-2995ba7c47fc)



Важливо

    Завантаження файлів з неправильним ім'ям (що не відповідає шаблону customer_type_date.xml) призведе до помилки.
    Повторне завантаження файлу з тим же ім'ям через POST поверне помилку.
    Для заміни існуючого файлу використовуйте PUT.


