FROM nginx

# Copy our default nginx config
COPY nginx/default.conf /etc/nginx/conf.d/

# copy app
WORKDIR /app
COPY dist/madb .

CMD ["nginx", "-g", "daemon off;"]