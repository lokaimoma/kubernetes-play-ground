FROM node:18-alpine as base
RUN apk add --no-cache g++ make py3-pip libc6-compat
WORKDIR /app
ENV NODE_ENV=development
COPY package*.json .
RUN npm install
COPY . .
CMD npm run dev
