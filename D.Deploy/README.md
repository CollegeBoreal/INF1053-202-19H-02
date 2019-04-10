# Deploiement

## Site

| ID      | PROD (AZ)                        | QA (AWS)                           |  DEV (local)             |
|---------|----------------------------------|------------------------------------|--------------------------|
|300089781| http://104.211.2.218:4200        |http://amelie.zaouidubois.ca:4200   | http://10.13.237.10:4200 |
|300108495| http://safaa.zaouidubois.ca:4200 |http://52.201.211.2:4200            | http://10.13.237.13:4200 |

## Dans votre propre répertoire

```
$ mkdir <monID> && cd $_
```

## [nginx](https://www.nginx.com/) 

### Créer le répertoire de configuration de NGINX

```
$ mkdir conf 
```

### Créer le fichier de configuration NGINX

```
$ cat <<EOF > conf/nginx.conf
worker_processes 1;

events {
    worker_connections 1024;
}

http {
    server {
       listen 80;

       location / {
           root /var/http;
           index index.html index.htm;
       }
    }
}
EOF
```

## [Angular](https://angular.io)

### Créer votre projet Angular

```
$ ng new angular --style=scss --routing=true
```

### Générer les fichiers 'Webpack'

```
$ cd angular && ng build --progress --prod --aot && cd ..
```

## [Docker](https://docker.io)

### Créer le fichier de configuration Docker (Dockerfile: encore appellé le Docker makefile )

```
$ cat <<EOF > Dockerfile
FROM nginx

RUN rm /etc/nginx/conf.d/default.conf

COPY conf /etc/nginx

COPY angular/dist/angular /var/http
EOF
```

### Créer l'image Docker du projet

```
$ docker image build .
```

### Récupérer l'ID de l'image

```
$ docker images
```

### Donner un nom et version à l'image (Tagger)

```
$ docker image tag <image ID> myapp:latest
```

## Éxécuter le conteneur en utilisant le `tag`

```
$ docker run --name myApp --detach --publish 4200:80 myapp:latest
```

# Références 

[Here Document](https://en.wikipedia.org/wiki/Here_document#Unix_shells)
