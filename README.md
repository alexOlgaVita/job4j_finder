# job4j_finder

### О проекте
Проект посвящен созданию консольной утилиты для поиска файлов в файловой системе.

### Цель
Решить тестовое задание.

### Техническое задание
1. Создать программу для поиска файлов. Под эту программу создайте отдельный репозиторий job4j_finder

2. Программа должна искать данные в заданном каталоге и подкаталогах.
3. Имя файла может задаваться: целиком, по маске, по регулярному выражению(не обязательно).
4. Программа должна запускаться с параметрами, например:  -d=c:  -n=*.?xt -t=mask -o=log.txt
   Ключи
   -d - директория, в которой начинать поиск.
   -n - имя файла, маска, либо регулярное выражение.
   -t - тип поиска: mask искать по маске, name по полному совпадение имени, regex по регулярному выражению.
   -o - результат записать в файл.
5. Параметры в программу должны передаваться в командной строке.
6. Программа должна записывать результат в файл.
7. В программе должна быть валидация ключей и подсказка.