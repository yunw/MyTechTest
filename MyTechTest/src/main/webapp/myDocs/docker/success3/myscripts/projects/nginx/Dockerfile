FROM nginx
RUN rm /etc/nginx/conf.d/default.conf
#RUN rm /etc/nginx/conf.d/example_ssl.conf
COPY html /usr/share/nginx/html
COPY conf /etc/nginx
