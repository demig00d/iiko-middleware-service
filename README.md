# IIKO-middleware-service

This service is developed as an exercise to practice ZIO, Quill, and Caliban.

## Overview

The main purpose of this service is to periodically export IIKO transactions to a local database and provide GraphQL APIs to query them. This ensures that the transactions remain available even if the IIKO servers go down (which happens frequently).

## Run

1. Rename `env.template` to `.env`: `mv env.template .env` and fill it with your values
2. Create a docker image: `sbt docker:publishLocal`
3. Run the sevice using docker-compose: `docker-compose up -d`
