APP ?= api

REVISION  ?= $(shell git rev-parse --short=8 HEAD)
BUILDTIME ?= $(shell date -u +"%Y%m%d%H%M%S")
ENV ?= local
ENV_PREFIX = ${ENV}-knighttodotech
ECS_SERVICE =  ${ENV_PREFIX}-${APP}
TAG ?= ${ENV}

PG_CONTAINER_NAME = knighttodo-test-postgres
CURRENT_DIR = $(shell pwd)
## Docker commands:

.PHONY: docker/image/build
docker/image/build:
	docker build \
		--add-host=host.docker.internal:172.17.0.1 \
		-t ${ECR_REGISTRY_URI}:${REVISION} \
		-t ${ECR_REGISTRY_URI}:${TAG} \
		.

##   docker/image/push - push an image to a registry
.PHONY: docker/image/push
docker/image/push: docker/login
	docker push ${ECR_REGISTRY_URI}:${REVISION}
	docker push ${ECR_REGISTRY_URI}:${TAG}

##   docker/postgres/start - start a postgres docker container for testing
.PHONY: docker/postgres/start
docker/postgres/start:
	chmod +x sql/pg_init_local.sql
	docker run -d -p 5432:5432 \
		-e POSTGRES_USER=postgre \
		-e POSTGRES_PASSWORD=postgre \
		-e POSTGRES_DB=knight \
		-v ${CURRENT_DIR}/sql:/docker-entrypoint-initdb.d \
		--name=${PG_CONTAINER_NAME} postgres:12
	docker exec ${PG_CONTAINER_NAME} sh -c 'until pg_isready; do sleep 1; done'

##   docker/postgres/stop - stop postgres docker container
.PHONY: docker/postgres/stop
docker/postgres/stop:
	docker stop ${PG_CONTAINER_NAME}

##   docker/postgres/stop - start a postgres docker container for testing
.PHONY: docker/postgres/rm
docker/postgres/rm:
	docker rm -f ${PG_CONTAINER_NAME}


