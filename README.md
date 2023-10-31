# IIKO-update-service

This service is done as an exercise to practice ZIO, quill and caliban

## What this service does

It periodically dumps IIKO transactions to the local database
and provides GraphQL API for querying them, so that transactions are available even when IIKO servers are down

## Run

1. Rename `env.template` to `.env`: `mv env.template .env` and fill it with your values
2. Create docker image: `sbt docker:publishLocal`
3. Run docker-compose: `docker-compose up -d`
