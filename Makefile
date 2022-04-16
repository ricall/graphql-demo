.PHONY: up down

up:
	docker-compose -f docker-compose.yml up $(c)

down:
	docker-compose -f docker-compose.yml down $(c)

