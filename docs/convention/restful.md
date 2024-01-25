## RESTful URLs

### 基本规范
* A URL identifies a resource.
* URLs should include nouns, not verbs.
* Use plural nouns only for consistency (no singular nouns).
* Use HTTP verbs (GET, POST, PUT, DELETE) to operate on the collections and elements.
* You shouldn’t need to go deeper than resource/identifier/resource.
* Put the version number at the base of your URL, for example http://example.com/v1/path/to/resource.
* URL v. header:
  * If it changes the logic you write to handle the response, put it in the URL.
  * If it doesn’t change the logic for each response, like OAuth info, put it in the header.
* Specify optional fields in a comma separated list.
* Formats should be in the form of api/v2/resource/{id}.json

### 好的示例（应该这样做）
* List of magazines:
  * GET http://www.example.gov/api/v1/magazines.json
* Filtering is a query:
  * GET http://www.example.gov/api/v1/magazines.json?year=2011&sort=desc
  * GET http://www.example.gov/api/v1/magazines.json?topic=economy&year=2011
* A single magazine in JSON format:
  * GET http://www.example.gov/api/v1/magazines/1234.json
* All articles in (or belonging to) this magazine:
  * GET http://www.example.gov/api/v1/magazines/1234/articles.json
* All articles in this magazine in XML format:
  * GET http://example.gov/api/v1/magazines/1234/articles.xml
* Specify optional fields in a comma separated list:
  * GET http://www.example.gov/api/v1/magazines/1234.json?fields=title,subtitle,date
* Add a new article to a particular magazine:
  * POST http://example.gov/api/v1/magazines/1234/articles

### 不好的示例（不应该这样做）
* Non-plural noun:
  * http://www.example.gov/magazine
  * http://www.example.gov/magazine/1234
  * http://www.example.gov/publisher/magazine/1234
* Verb in URL:
  * http://www.example.gov/magazine/1234/create
* Filter outside of query string
  * http://www.example.gov/magazines/2011/desc

## HTTP Verbs

HTTP verbs, or methods, should be used in compliance with their definitions under the [HTTP/1.1](http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html) standard.
The action taken on the representation will be contextual to the media type being worked on and its current state. Here's an example of how HTTP verbs map to create, read, update, delete operations in a particular context:

| HTTP METHOD | POST            | GET       | PUT         | DELETE |
| ----------- | --------------- | --------- | ----------- | ------ |
| CRUD OP     | CREATE          | READ      | UPDATE      | DELETE |
| /dogs       | Create new dogs | List dogs | Bulk update | Delete all dogs |
| /dogs/1234  | Error           | Show Bo   | If exists, update Bo; If not, error | Delete Bo |

(Example from Web API Design, by Brian Mulloy, Apigee.)


## Responses

* No values in keys
* No internal-specific names (e.g. "node" and "taxonomy term")
* Metadata should only contain direct properties of the response set, not properties of the members of the response set

### Good examples

No values in keys:

```json
{
  "tags": [
    {"id": "125", "name": "Environment"},
    {"id": "834", "name": "Water Quality"}
  ]
}
```


### Bad examples

Values in keys:

```json
{
  "tags": [
    {"125": "Environment"},
    {"834": "Water Quality"}
  ]
}
```


## Error handling

Error responses should include a common HTTP status code, message for the developer, message for the end-user (when appropriate), internal error code (corresponding to some specific internally determined ID), links where developers can find more info. For example:

```json
{
  "status" : 400,
  "developerMessage" : "Verbose, plain language description of the problem. Provide developers suggestions about how to solve their problems here",
  "userMessage" : "This is a message that can be passed along to end-users, if needed.",
  "errorCode" : "444444",
  "moreInfo" : "http://www.example.gov/developer/path/to/help/for/444444, http://drupal.org/node/444444"
}
```

Use three simple, common response codes indicating (1) success, (2) failure due to client-side problem, (3) failure due to server-side problem:
* 200 - OK
* 400 - Bad Request
* 500 - Internal Server Error
