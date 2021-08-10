FROM mhart/alpine-node:8 AS build-env
RUN apk update
WORKDIR /app
COPY . .
RUN yarn install --production
RUN yarn build

FROM alpine
RUN apk update && apk add ca-certificates nginx && rm -rf /var/cache/apk/*
RUN mkdir -p /run/nginx && touch /run/nginx/nginx.pid
WORKDIR /app
COPY --from=build-env /app/build /app
COPY ./default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
ARG dataservice=localhost:8080
ENV DATA_IP_ADDRESS=$dataservice
ARG authservice=localhost:8081
ENV AUTH_IP_ADDRESS=$authservice
CMD ["nginx", "-g", "daemon off;"]