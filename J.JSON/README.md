# JSON

https://github.com/jilen/play-circe

POST Testing

```
$ curl -X POST \
       --header 'Content-Type: application/json' \
       --data '{"foo":"foo","bar":{"bar":1}}' \
       http://localhost:9000/post
```

```
$ curl -X POST \
      --header 'Content-Type: application/json' \
      --header 'Accept: application/json' \
      --data '{}' \
      http://localhost:9000/post
```
