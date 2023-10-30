# ----------[ Build ]----------
FROM node:18-alpine AS build
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install --force
COPY . .
RUN npm install -g @angular/cli
RUN npm run build

# ----------[ Deploy ]----------
FROM nginx:1-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/src/app/dist/chat /usr/share/nginx/html