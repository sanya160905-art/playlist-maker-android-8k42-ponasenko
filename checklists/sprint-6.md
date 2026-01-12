# Чек-лист спринта 6

## Реализация поиска треков
- [x] Создан SearchViewModel с состояниями SearchState (sealed class)
- [x] Состояния: Initial, Searching, Success, Fail
- [x] Функция search() реализована с обработкой ошибок
- [x] Добавлен объект Creator с функцией getTracksRepository()
- [x] Создан SearchScreen composable с OutlinedTextField
- [x] Реализована подписка на searchScreenState через collectAsState()
- [x] Обработка всех состояний экрана:
    - [x] Initial: отображение "Введите строку для поиска"
    - [x] Searching: отображение CircularProgressIndicator
    - [x] Success: отображение списка треков через LazyColumn
    - [x] Fail: отображение ошибки
- [x] Реализован TrackListItem для отображения отдельного трека
- [x] Добавлена иконка ic_music.png в res/drawable
- [x] Иконка поиска (Icons.Filled.Search) кликабельна
- [x] Обновлён MainActivity для использования SearchScreen
- [x] Поиск работает: ввод текста → нажатие иконки → отображение результатов
- [x] Проект собирается и запускается