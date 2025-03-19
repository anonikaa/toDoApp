# Running the Application and Tests

## Running the Application with Docker

Run the container:
   ```sh
   docker run -p 8080:4242 image-name
   ```

## Running Tests

### If using `zsh`:
```sh
mvn test -Dtest='com.todoapp.**'
```

### If using other shells (bash, etc.):
```sh
mvn test -Dtest=com.todoapp.*  
```

