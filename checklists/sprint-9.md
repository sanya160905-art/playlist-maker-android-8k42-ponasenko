# Чек-лист спринта 9

## Сетевые запросы и экран плейлиста

### Подключение Retrofit и Gson
- [x] Добавлены зависимости Retrofit и Gson в `libs.versions.toml` и `build.gradle.kts`
- [x] Добавлено разрешение `android.permission.INTERNET` в `AndroidManifest.xml`

### Реальный поиск через iTunes Search API
- [x] Создан интерфейс `ITunesApiService` с методом search() через `@GET` и `@Query`
- [x] Обновлён `TrackDto` под реальную структуру ответа iTunes API (nullable поля)
- [x] Добавлено поле `artworkUrl100` для обложки трека
- [x] `TracksSearchResponse` обновлён с аннотациями `@SerializedName`
- [x] `RetrofitNetworkClient` переписан на реальный Retrofit с `GsonConverterFactory`
- [x] Удалён моковый класс `Storage` — данные теперь приходят с сервера
- [x] `NetworkClient.doRequest()` сделан suspend-функцией
- [x] `Creator` обновлён — `RetrofitNetworkClient` создаётся без моковой зависимости

### Обработка состояний поиска
- [x] `TracksRepository.searchTracks()` возвращает `Result<List<Track>>` для разделения ошибок и пустого результата
- [x] Пустой результат поиска отображается как "Ничего не найдено" (`Success` с пустым списком)
- [x] Ошибка сети/сервера отображается отдельным состоянием с кнопкой "Обновить"
- [x] Реализован метод `retrySearch()` в `SearchViewModel` — повторяет последний неудавшийся запрос
- [x] `Track` расширен полем `artworkUrl: String?`
- [x] Обложки треков загружаются через Coil (`AsyncImage`) с плейсхолдером `ic_music`

### Экран плейлиста
- [x] Создана модель `Playlist` (id, name, description, tracks)
- [x] `DatabaseMock` дополнен моковыми плейлистами для тестирования
- [x] Создан интерфейс `PlaylistsRepository` с методами `getAllPlaylists()` и `getPlaylist(id)`
- [x] Реализован `PlaylistsRepositoryImpl` на основе `DatabaseMock`
- [x] Создан `PlaylistViewModel` с полем `playlist: Flow<Playlist?>`
- [x] Создан `PlaylistScreen` — отображает название, описание и список треков плейлиста
- [x] Список плейлистов в `PlaylistsScreen` подключён к `DatabaseMock` вместо заглушки
- [x] Добавлен маршрут `PLAYLIST_DETAIL/{playlistId}` с `navArgument` в `PlaylistHost`
- [x] Реализована навигация: список плейлистов → экран плейлиста → экран трека
- [x] Реализована навигация назад со всех новых экранов

### Общее
- [x] Сборка проекта (assembleDebug) проходит успешно
- [x] Поиск работает с реальным API, обложки треков отображаются в списке результатов
- [x] Список плейлистов и переход в плейлист работают на моковых данных