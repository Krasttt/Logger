-- Вывод логов определенного приложения
select  log.id,app.name, log.message,log.class,log.thread,log.date
from "Log" as log, "App" as app where log.app_id = app.id and app.name = 'app1';

-- Вывод логов за определенную дату
SELECT *
FROM "Log"
where date::date = '2019-01-30';

-- Вывод логов определенного класса
SELECT *
FROM "Log"
where class like '%ClassA';

-- Вывод логов определенного пакета
SELECT *
FROM "Log"
where class like 'com.nc%';

-- Вывод логов определенного уровня
select  log.id,level.name, log.message,log.class,log.thread,log.date
from "Log" as log, "Level" as level where log.level_id = level.id and level.name = 'INFO';

-- Вывод логов определенного потока
SELECT *
FROM "Log"
where thread = 'main';

--Все логи приложения за последние сутки
SELECT id, level_id, app_id, message, class, thread, date
FROM public."Log" where date::timestamp > (now()  - '1 days'::interval);

--Количество записей определенного уровня (к примеру, error) для конкретного приложения
SELECT app.name as app,count(level)
FROM("Log" as log JOIN "Level" as level ON(log.level_id=level.id) JOIN "App" as app ON (log.app_id=app.id))
where level.name='ERROR' GROUP BY (app);

--Изменить предыдущий запрос так, чтобы выводились приложения, в которых ошибок больше 50 штук
SELECT app.name as app,count(level)
FROM("Log" as log JOIN "Level" as level ON(log.level_id=level.id) JOIN "App" as app ON (log.app_id=app.id))
where level.name='INFO' GROUP BY (app) HAVING count(level)>50;

--Изменить запрос, чтобы выбирались только записи последние 24 часа (сутки)
SELECT app.name as app,count(app)
FROM("Log" as log JOIN "Level" as level ON(log.level_id=level.id) JOIN "App" as app ON (log.app_id=app.id))
where level.name='INFO' and log.date::timestamp > (now()  - '1 days'::interval) GROUP BY (app) ;

--Выводит резултатат по всем приложениям и по всем ошибкам:
SELECT app.name as app,level.name as level,count(level)
FROM("Log" as log JOIN "Level" as level ON(log.level_id=level.id) JOIN "App" as app ON (log.app_id=app.id))
GROUP BY (app,level) ;

--Удаляет все записи определенного приложения, которые старше 4х суток
DELETE FROM "Log" as log
WHERE log.app_id IN (SELECT id FROM "App" as app WHERE app.name = 'app1')
  and log.date::date::timestamp > (now()  - '4 days'::interval);