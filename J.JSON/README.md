# JSON

https://www.playframework.com/documentation/2.6.x/ScalaJson

POST Testing

```
$ curl --request POST \
       --header 'Content-Type: application/json' \
       --header 'Accept: application/json' \
       --data '{"foo":"foo","bar":{"bar":1}}' \
       http://localhost:9000/post
```
