## Covenant Code Landing Service — Документация

### 📦 Общая информация

Проект covenant-code-landing-service — это backend для лендинга на Spring Boot 4.0.0, с базой данных PostgreSQL.
Используемые технологии:

* Java 21 (Amazon Corretto 21 LTS)

* Spring Boot 4.x (WebMVC, Security тесты, H2 для локальной разработки)

* Maven — сборка проекта

* Docker + Docker Compose — контейнеризация

* PostgreSQL 16 — база данных

* Lombok — генерация boilerplate-кода

### 📁 Структура проекта
```
covenant-code-landing-service/
│
├─ src/                        # Исходный код проекта
├─ pom.xml                     # Maven зависимости
├─ Dockerfile                  # Сборка и запуск контейнера
├─ docker-compose.yml          # Docker Compose окружение
├─ credential.env              # Переменные окружения
├─ HELP.md                     # Эта документация
```

### ⚙️ Maven (pom.xml)

### Зависимости

#### Группировка зависимостей:

* Spring Boot Core

* spring-boot-starter-webmvc — MVC, контроллеры, REST API

#### Databases

* spring-boot-starter-data-jpa

* spring-boot-h2console — консоль H2 для dev

* com.h2database:h2 — in-memory БД для локальной разработки

* org.postgresql:postgresql — драйвер PostgreSQL

#### Lombok

* org.projectlombok:lombok — генерация getter/setter, toString, builder

#### Testing

* spring-boot-starter-security-test — тесты Security

* spring-boot-starter-webmvc-test — тестирование MVC

#### Build Plugins

* maven-compiler-plugin — подключение Lombok для аннотаций

* spring-boot-maven-plugin — сборка fat JAR, исключение Lombok из финального артефакта
