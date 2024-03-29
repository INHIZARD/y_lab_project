# Monitoring-Service

Разработайте веб-сервис для подачи показаний счетчиков отопления, горячей и холодной воды

# Описание

Показания можно подавать один раз в месяц.
Ранее поданные показания редактировать запрещено.
Последние поданные показания считаются актуальными.
Пользователь может видеть только свои показания, администратор может видеть показания всех пользователей.
Создайте реализацию, которая соответствует описанным ниже требованиям и ограничениям.

# Требования

* предусмотреть расширение перечня подаваемых показаний
* данные хранятся в памяти приложения
* приложение должно быть консольным (никаких спрингов, взаимодействий с БД и тд, только java-core и collections)
* регистрация пользователя
* авторизация пользователя
* реализовать эндпоинт для получения актуальных показаний счетчиков
* реализовать эндпоинт подачи показаний
* реализовать эндпоинт просмотра показаний за конкретный месяц
* реализовать эндпоинт просмотра истории подачи показаний
* реализовать контроль прав пользователя
* Аудит действий пользователя
  (авторизация, завершение работы, подача показаний, получение истории подачи показаний и тд)

# Нефункциональные требования

Unit-тестирование

---

# Реализация

Приложение предоставляет взаимодействие с 3 сессиями:
сессия логирования,
сессия пользователя,
сессия администратора.
В каждой из сессий пользователь имеет возможность применять различные команды.

## Команды меню логирования

- `login [username] [password]` команда для входа в систему
- `signup [username] [password]` команда для регистрации в систему (логин пользователя должен быть уникален,
  поэтому при попытке ввести уже существующий логин, будет получена ошибка)
- `exit` команда для выхода из сессии
- `help` команда для вывода меню со всеми командами

## Команды меню пользователя

- `indications` команда для просмотра счетчиков
- `submit` команда для внесения данных показателей
- `history` команда для просмотра истории внесения показателей
- `history [MM.yyyy]` команда для просмотра истории в конкретную дату
- `logout` выход из аккаунта
- `help` команда для вывода меню со всеми командами
- `infuture [days]` _**специальная команда**_ для увеличения текущей даты на некоторое количество дней,
  данная команда предназначена для удобства использования приложения

## Команды меню администратора

- `users` вывод списка всех пользователей
- `history [username]` история подачи показаний пользователя
- `audit [username]` аудит пользователя
- `indication [title]` внесение нового счетчика
- `sadmin [username]` команда присваивания прав администратора пользователю
- `dadmin [username]` команда для забирания прав администратора у пользователя (у себя нельзя забрать права)
- `logout` выход из аккаунта
- `help` команда для вывода меню со всеми командами
- `infuture [days]` _**специальная команда**_ для увеличения текущей даты на некоторое количество дней,
  данная команда предназначена для удобства использования приложения
