export JAVA_HOME=/home/pavel/.jdks/corretto-21.0.4/

export POSTGRES_HOST=localhost
export POSTGRES_DB=default

.PHONY: run migrate populate depopulate


run:
	sudo chmod -R 777 docker/postgres-data
	./mvnw package
	docker-compose build
	docker-compose up

init:
	mkdir docker/postgres-data
	make migrate
	make populate

migrate:
	sudo chmod -R 777 docker/postgres-data
	docker-compose up -d postgres
	./mvnw clean flyway:migrate -Dflyway.configFiles=flyway.conf
	docker-compose down

populate:
	docker-compose up -d postgres
	./mvnw spring-boot:run -Dspring-boot.run.arguments=populate
	docker-compose down

depopulate:
	docker-compose up -d postgres
	./mvnw spring-boot:run -Dspring-boot.run.arguments=depopulate
	docker-compose down

purge:
	docker-compose down
	sudo rm -r docker/postgres-data

