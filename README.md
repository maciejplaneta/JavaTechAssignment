# Simple file server

### Application is storing files locally. It creates directory named 'upload' in 'java.io.tmpdir' location as default and inside it, per every application startup, it creates a storage folder named after startup date, ex. '16-02-2022_22-51-31' 
### Location can be changed by running with vm param:
```
-Djava.io.tmpdir=<desired_location_path>
```

### API documentation can be found under endpoint
```
/swagger-ui/
```

### Supported operations:
#### Files storage
```
POST /storage - supports HTML upload form (try at /uploadForm)
GET /storage/{fileId}
PUT /storage/{fileId}
DELETE /storage/{fileId}
```
#### Files metadata
```
GET /metadata/{fileId}
GET /metadata?page={page}&size={size}
```

Supported file types can be found [here](https://github.com/maciejplaneta/JavaTechAssignment/blob/master/src/main/java/com/example/javatechassignment/domain/validation/SupportedFileExtension.java) 
