# graphql-demo

Simple graph-ql demo application. This application is intended to demonstrate how easy it is to combine graphql
and spring boot applications.

Based on documentation from https://www.baeldung.com/spring-graphql

# Getting Started

Start mongo-db
```bash
make up
```
This will start two docker containers:
* mongo-db
* mongoclient

mongo will be available on `127.0.0.1:27017`

mongoclient will be available on http://localhost:3300/ if you connect to `mongodb://mongodb:27017/test?ssl=false`
you will have access to the applications mongo-db container.

Start the application:
```bash
./gradlew bootRun
```
The first time the application starts it will populate the mongo-db with 200 random posts.

## GraphQL Queries
Use the GraphiQL interface to query the API: http://localhost:8080/graphiql?path=/graphql

### Query recent posts
```graphql
{
    recentPosts(count:10, offset: 0) {
        id
        slug
        thumbnail
        image
        content
        category
        tags {
            slug
            name
        }
        author {
            name
            thumbnail
        }
        version
        createdBy
        createdDateTime
        modifiedBy
        modifiedDateTime
    }
}
```

### Query posts by Category

You can query the API using http://localhost:8080/graphiql?path=/graphql

```graphql
{
    postsByCategory(category: "wasp", count: 10, offset: 0) {
        id
        slug
        category
        tags {
            name
        }
    }
}
```

### Query posts by Category and Tag
```graphql
{
  postsByCategoryAndTag(category: "wasp", tag: "Warhol", count: 10, offset: 0) {
    id
    slug
    category
    tags {
      name
    }
  }
}
```

### Add a post to the DB
```graphql
mutation {
  addPost(
    slug: "post-slug"
    thumbnail: "thumbnail"
    image: "image"
    content: "content"
    category: "category"
    tags: [
      { slug: "tag-1" name: "Tag One" }
      { slug: "tag-2" name: "Tag Two" }
    ]
    author: {
      name: "Richard"
      thumbnail: "thumbnail"
    }
  ) {
    id
    slug
    thumbnail
    image
    content
    category
    tags {
      slug
      name
    }
    author {
      name
      thumbnail
    }
    
    version
    createdDateTime
    modifiedDateTime
  }
}
```

## Using MongoDB Client
Once you have connected the client to mongodb you can access the shell @ http://localhost:3300/shell

### Listing collections
```javascript
db.getCollectionNames();
```

### Dropping `Post` collection
```javascript
db.Post.drop();
```
NOTE: You will need to restart the application after dropping the collection