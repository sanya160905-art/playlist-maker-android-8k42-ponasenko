# Чек-лист спринта 10

## Хранение данных: Room и DataStore

### Подключение библиотек
- [x] Добавлены зависимости Room (runtime, ktx, compiler через KSP) в `libs.versions.toml` и `build.gradle.kts`
- [x] Добавлена зависимость DataStore Preferences
- [x] Подключён плагин KSP для генерации кода Room

### Room — хранение треков и плейлистов
- [x] Создана сущность `TrackEntity` с полями id, trackName, artistName, trackTime, artworkUrl, isFavorite, playlistId
- [x] Создана сущность `PlaylistEntity` с полями id, name, description
- [x] Реализован `TracksDao` с методами вставки, поиска по имени/исполнителю, получения избранных треков, треков плейлиста, обновления статуса избранного
- [x] Реализован `PlaylistDao` с методами вставки и получения плейлистов
- [x] Создан класс `AppDatabase` объединяющий обе сущности и DAO
- [x] Создан класс `App : Application()` для инициализации базы данных при запуске приложения
- [x] `Creator` обновлён — база данных создаётся через `Room.databaseBuilder()` с использованием `Context`
- [x] Реализованы конвертеры Entity ↔ Domain модель (`toTrack()`, `toEntity()`, `toPlaylist()`)

### Domain и Data слои
- [x] `TracksRepository` дополнен методами: `getTrackByNameAndArtist`, `getFavoriteTracks`, `insertTrackToPlaylist`, `deleteTrackFromPlaylist`, `updateTrackFavoriteStatus`
- [x] `TracksRepositoryImpl` переписан на работу с `AppDatabase` вместо моков
- [x] Реализована идемпотентная вставка трека — перед добавлением проверяется наличие записи по trackName+artistName, чтобы избежать дублей
- [x] `PlaylistsRepository` дополнен методом `addNewPlaylist`
- [x] `PlaylistsRepositoryImpl` переписан на работу с `AppDatabase` вместо `DatabaseMock`
- [x] Модель `Track` дополнена полем `isFavorite` для корректного отображения статуса избранного в UI

### DataStore — история поиска
- [x] Создан класс `SearchHistoryPreferences`, работающий с `DataStore<Preferences>`
- [x] Реализован метод `addEntry()` — хранит не более 10 запросов, без дублей, в порядке от новых к старым
- [x] Реализован метод `getEntries()` для получения сохранённой истории
- [x] Реализован метод `clearHistory()` для очистки истории
- [x] `SearchHistoryRepository` и `SearchHistoryRepositoryImpl` переписаны на suspend-методы поверх DataStore
- [x] `SearchViewModel` обновлён — обращения к истории поиска выполняются в `viewModelScope.launch`
- [x] Класс `DatabaseMock` полностью удалён из проекта

### Функциональные проверки
- [x] Созданный плейлист сохраняется в базе и отображается при следующем запуске приложения
- [x] Добавление трека в плейлист работает без дублирования записей
- [x] Добавление в «Избранное» корректно отражается в UI (сердечко) и сохраняется между запусками
- [x] Список «Избранное» обновляется в реальном времени и корректно очищается при снятии лайка
- [x] История поиска сохраняется между перезапусками приложения
- [x] Переход на экран трека работает как из списка поиска и истории, так и из плейлиста и избранного
- [x] Сборка проекта (assembleDebug) проходит успешно