FROM mariadb:10

RUN rm -f /usr/local/bin/docker-entrypoint.sh

COPY docker-entrypoint.sh /usr/local/bin/

COPY srblog.sql /