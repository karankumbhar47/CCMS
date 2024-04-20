FROM ubuntu
RUN apt update # && apt -y upgrade
RUN apt -y install openjdk-17-jre
RUN apt -y install nginx
COPY target/CCMS-0.0.1-SNAPSHOT.jar app.jar
COPY nginx.conf /etc/nginx/sites-available/backend
EXPOSE 80
EXPOSE 443

# ENTRYPOINT java -jar app.jar
CMD ["nginx", "-g", "daemon off;"]
