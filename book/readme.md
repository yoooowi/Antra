# Many-to-Many Book-Author Server

## Database Schemas

```postgresql
CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

CREATE TABLE authors (
                         author_id SERIAL PRIMARY KEY,
                         first_name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100) NOT NULL
);

CREATE TABLE books_authors (
                              id SERIAL PRIMARY KEY,
                              book_id INTEGER REFERENCES books(book_id),
                              author_id INTEGER REFERENCES authors(author_id)
);
```
## APIs
API documentation can also be seen at `http://localhost:[port]/swagger-ui/index.html`

- `GET /book/{id}`  
  Returns a `BookDTO` object if such book exists, or `404` if it doesn't exist. 


- `PUT /book/{id}`   
    Updates and returns the updated `BookDTO` object if such book exists.  
    A `BookDTO` object is required in the request body.  


- `DELETE /book/{id}`  
    Deletes the book object with given `id`.  
    `200 OK` is returned even if the book doesn't exist.  


- `POST /book`  
    Adds a new book to the database.  
    A `BookDTO` object is required in the request body.


- `GET /book/search`  
    Returns a list of `BookDTO` objects. Supports the following parameters
  - `?author_id={id}` Search book by author id.
  - `?author={firstName}+{lastName}` Search book by author full name.   


- `GET /author?name=`  
    Search author by full name and returns a `AuthorDTO` object.  


- `GET /author/{id}`  
    Returns a `AuthorDTO` object with given ID, or `404` if it doesn't exist.


- `POST /author`  
    Adds a new author to the database.  
    A `AuthorDTO` object is required in request body.


## Object Schemas

### `BookDTO` for POST requests
```json
{
  "title" : "",
  "author_ids" : []
}
```

### `BookDTO` as responses and for PUT requests
```json
{
  "id": 0,
  "title": "",
  "author_ids": []
}
```

### `AuthorDTO` for POST requests
```json
{
  "first_name" : "",
  "last_name" : ""
}
```

### `AuthorDTO` as responses and for PUT requests
```json
{
  "id": 0,
  "first_name": "",
  "last_name": ""
}
```