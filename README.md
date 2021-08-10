# team_one
## Docker Setup Instructions

The app uses a Docker network to communicated with the containers.\
Here are some instructions for setup.

## Build the Data Service
docker -t data_service build .\
docker run -d --name data_service -p 8080:8080 data_service

## Create Network, Connect Data Service, Get Data Service IP
docker network create mccnetwork\
docker network connect mccnetwork data_service\
docker network inspect mccnetwork

## Build the Auth Service using Data Service IP in Docker Network as arg.
docker -t auth_service build .\
docker run -d --name auth_service -p 8081:8081 --env DATA_IP_ADDRESS=172.18.0.2:8080 auth_service\
docker network connect mccnetwork auth_service

## Build the React Client using Data and Auth Service args.
Build react client - docker build -t react_client .\
docker run --name react_client -d -p 3000:80 -e DATA_IP_ADDRESS=172.18.0.2 -e AUTH_IP_ADDRESS=172.18.0.3 react_client\
docker network connect mccnetwork react_client
