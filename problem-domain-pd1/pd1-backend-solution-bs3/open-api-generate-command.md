# OpenAPI generate command

From root path:

```
 openapi-generator-cli generate -i ../api/sakila-films-crud.yml -g go-server --additional-properties=onlyInterfaces=true,outputAsLibrary=true,sourceFolder=openapi
```