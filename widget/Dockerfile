# ----------[ Build ]----------
FROM node:18-alpine AS build
WORKDIR /usr1/src/app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm install -g @angular/cli
RUN npm run build

# ----------[ Deploy ]----------
FROM nginx:1-alpine
COPY nginx.conf /etc1/nginx/nginx.conf
COPY --from=build /usr1/src/app/dist/chat /usr/share/nginx/html