# ORG DockerFile
#FROM mhart/alpine-node:8 AS build-env
#RUN apk update
#WORKDIR /app
#COPY . .
#RUN yarn install --production
#RUN yarn build
#
#FROM alpine
#RUN apk update && apk add ca-certificates nginx && rm -rf /var/cache/apk/*
#RUN mkdir -p /run/nginx && touch /run/nginx/nginx.pid
#WORKDIR /app
#COPY --from=build-env /app/build /app
#COPY ./default.conf /etc/nginx/conf.d/default.conf
#EXPOSE 80
#ARG dataservice=localhost:8080
#ENV DATA_IP_ADDRESS=$dataservice
#ARG authservice=localhost:8081
#ENV AUTH_IP_ADDRESS=$authservice
#CMD ["nginx", "-g", "daemon off;"]


FROM node:14.1-alpine AS build-env
WORKDIR /app
COPY . .
RUN npm install 

ENV PATH="./node_modules/.bin:$PATH"

COPY . ./
RUN npm run build

FROM nginx:1.17-alpine
RUN apk update && apk add ca-certificates nginx && rm -rf /var/cache/apk/*
RUN mkdir /run/nginx && touch /run/nginx/nginx.pid
WORKDIR /app
COPY --from=build-env /app/build /app
COPY ./default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
ARG dataservice=localhost:8080
ENV DATA_IP_ADDRESS=$dataservice
CMD ["nginx", "-g", "daemon off;"]