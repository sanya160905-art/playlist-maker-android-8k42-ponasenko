# Чек-лист спринта 5

## Архитектура приложения
- [x] Применена Clean Architecture (пакеты data, domain, ui, creator)
- [x] Слой Data реализован:
    - [x] Классы: TrackDto, TracksSearchRequest, TracksSearchResponse, BaseResponse
    - [x] RetrofitNetworkClient для связи с сервером (эмулятором)
    - [x] TracksRepositoryImpl (реализация репозитория)
- [x] Слой Domain реализован:
    - [x] Интерфейсы: NetworkClient, TracksRepository
    - [x] Сущность Track для передачи между слоями
- [x] Класс Storage для эмуляции ответа сервера