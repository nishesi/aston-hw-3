# Приложение по поиску достопримечательностей
## Инструкция запуска
 - В папке [resources](./src/main/resources) создать файл application.properties со следующим содержимым:
```properties
   datasource.url=jdbc:postgresql://postgres:5432/postgres
   datasource.username=postgres
   datasource.password=postgres
   datasource.driver-class-name=org.postgresql.Driver
   jpa.hibernate.ddl-auto=validate
```
 - В корневой директории проекта выполнить команду
```shell
docker-compose up
```