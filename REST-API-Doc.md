# REST Server API Document
This is the document of the REST server.

## Note
The difference between POST and PUT is that POST is often used to create a new resource and PUT is often used to update a resource. In this project, resources are internally identified by their *id*s. Thus, we use POST method to create a new resource with a newly generated id and use PUT method to update an old resource with a known id.

All fields in **Data Params** are REQUIRED except explicitly tagged OPTIONAL.


## Resources Path
Here we list all the resources and supported operations on them.

The plural form of the resource refer to the set of the resource. GET method on them will return a list of the resources. POST method on them will try to create a new resource.

`/<resources>/{id}` often refer to a specific resource. GET method returns the data of the resource. DELETE method deletes the resource. And PUT method will try to update the resource with new data.

- `/courses`
    - POST, a new course, with specified teacher
- `/courses/{cid}`
    - GET, data of the course, meta data mostly
    - DELETE, the course
    - PUT, the updated data of the course
- `/courses/{cid}/graphs`
    - GET, all graphs of the course
    - POST, a new graph to the course
- `/courses/{cid}/students`
    - GET, all students of the course
    - POST, a new student to the course, a.k.a. a student join the course
- `/graphs/{gid}`
    - GET, data of the course, without nodes
    - DELETE, the graph
    - PUT, the updated data of the graph
- `/graphs/{gid}/nodes`
    - GET, all nodes of the graph
    - POST, a new node to the graph, with specified parent node
- `/nodes/{nid}`
    - GET, data of the node, including parent node, children nodes, and some meta data
    - DELETE, the node
    - PUT, the updated node info
- `/nodes/{nid}/questions`
    - GET, all questions of a node
    - POST, a new question to the node
- `/nodes/{nid}/lectures`
    - GET, all lectures of a node
    - POST, a new lecture to the node
- `/nodes/{nid}/resources`
    - GET, all resources of a node
    - POST, a new resource to the node
- `/questions/{qid}`
    - GET, data of the question
    - PUT, the updated question
    - DELETE, the question
- `/questions/{qid}/answers`
    - GET, all answers of the question
    - POST, a new answer to this question
- `/answers/{aid}`
    - GET, the data of the answer
    - PUT, the updated answer, restricted for that students are not allowed to edit their answers
    - DELETE, the answer
- `/lectures/{lid}`
    - GET, the data of the lecture
    - PUT, the updated lecture
    - DELETE, the lecture
- `/resources/{rid}`
    - GET, the data of the resource
    - PUT, the updated resource
    - DELETE, the resource
- `/users`
    - POST, a new user, registration
- `/users/{uid}`
    - GET, public available data of the user, such as name, id or type
- `/account` # This is related to login user
    - GET, private data of the user himself, including email
    - PUT, the updated data of the user
    - DELETE, the user account
- `/account/courses`
    - GET, get a list of the course that the user is related to
- `/token`
    - POST, a new token, used to authorization, login
    - DELETE, this token, logout



## Course Related
Meta data structure of a course:
```
course_meta :
{
    name : <string>,
    id : <integer>,
    code : <string>,
    teacher_name : <string>,
    teacher_id : <integer>,
    student_num : <integer>
}
```

### Create a course

Teacher create a new course. Needs authorization.

- **URL**

    /courses

- **Method:**

    `POST`

- **URL Params**

    **Required:**

    None.

    **Optional:**

    None.

- **Data Params**
    ```
    {
        name : <string>,
        code : <string>  # 课程代码
    }
    ```

    Example:
    ```
    {
        name : "Advanced Web",
        code : "GRE4T4G41N"
    }
    ```

- **Success Response:**

    - **Code:** 201 CREATED <br>
      **Content:** `<course_meta>` of the newly created course
 
- **Error Response:**

    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or `teacher_id` does not match token.

- **Notes:**

    None.

----
### Delete a course

Teacher delete a course. Needs authorization.

- **URL**

    /courses/{cid}

- **Method:**

    `DELETE`

- **URL Params**

    **Required:**

    `cid=<integer>`, course id

    **Optional:**

    None.

- **Data Params**
    
    None.

- **Success Response:**

    - **Code:** 204 NO CONTENT <br>
      **Content:** None.
 
- **Error Response:**

    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or `teacher_id` does not match token.
    
    OR

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Course not found" }` <br>
      **Condition:** Course id invalid.

- **Notes:**

    None.

----
### List courses of a user

List all courses that a student takes or a teacher teaches. Needs Authorization.

- **URL**

    /account/courses

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    None.

    **Optional:**

    None.

- **Data Params**

    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** 
    ```
    [
        <course_meta>,
        ...
    ]
    ```
 
- **Error Response:**

    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or user id does not match token.

- **Notes:**

    This could be considered as get all courses while filtered by user(student/teacher).

----
### Get data of the course

- **URL**

    /courses/{cid}

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    `cid=<integer>`

    **Optional:**

    None.

- **Data Params**
    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<course_meta>`
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Course not found" }` <br>
      **Condition:** Course with that id is not found.

- **Notes:**

    None.

----
### Update data of the course

Update the meta of the course. Only include the fields that need to be updated(replaced).

- **URL**

    /courses/{cid}

- **Method:**

    `PUT`

- **URL Params**

    **Required:**

    `cid=<integer>`

    **Optional:**

    None.

- **Data Params**
    ```
    {
        name : <string>, # OPTIONAL
        code : <string>, # OPTIONAL
    }
    ```

    Example:
    ```
    {
        name : "More Advanced Web"
    }
    ```

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<course_meta>`
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Course not found" }` <br>
      **Condition:** Course with that id is not found.

- **Notes:**

    None.

----
### List all students of the course

Get a list of students of a course.

- **URL**

    /courses/{cid}/students

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    `cid=<integer>`

    **Optional:**

    None.

- **Data Params**
    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** 
    ```
    [
        <user_public>,
        ...
    ]
    ```
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Course not found" }` <br>
      **Condition:** Course with that id is not found.

- **Notes:**

    None.

----
### Add a new student to the course(student join the course)

Student join a course. Or add a student to a course.

- **URL**

    /courses/{cid}/students

- **Method:**

    `POST`

- **URL Params**

    **Required:**

    `cid=<integer>`

    **Optional:**

    None.

- **Data Params**
    ```
    {
        code : <string> # Course code
    }
    ```

    Example:
    ```
    {
        code : "course_code"
    }
    ```

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<course_meta>`, Meta data of the course that the student join.
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Course not found" }` <br>
      **Condition:** Course with that id is not found.
    
    OR

    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or user id does not match token.
     
    OR
    
    - **Code:** 422 UNPROCESSABLE ENTRY <br>
      **Content:** `{ error : "Course code invalid" }` <br>
      **Condition:** Course code not match.
    
    OR
    
    - **Code:** 403 FORBIDDEN <br>
      **Content:** `{ error : "Not a student" }` <br>
      **Condition:** Current login user is not a student.
    
    OR
    
    - **Code:** 409 FORBIDDEN <br>
      **Content:** `{ error : "Student already in this course." }` <br>
      **Condition:** Current login user is already a student in this course.

- **Notes:**

    None.

----

## Token Related

Token is used for authorization. It is stored in the memory and will be deleted if user logout or another client login with the same account. Token will also be deleted after certain amount of time.

### Login

User login. Return a newly created token if success.

- **URL**

    /token

- **Method:**

    `POST`

- **URL Params**

    **Required:**

    None.

    **Optional:**

    None.

- **Data Params**
    ```
    {
        email : <string>, # user email
        password : <string> # user password
    }
    ```

    Example:
    ```
    {
        email : "abc@fudan.edu.cn",
        password : "logmein123"
    }
    ```

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** 
    ```
    {
        user : <user_private>,
        token : <string> # access token
    }
    ```
 
- **Error Response:**

    - **Code:** 422 UNPROCESSABLE ENTRY <br>
      **Content:** `{ error : "Email is invalid or password is wrong." }` <br>
      **Condition:** User does not exist OR password is wrong.
       Do not differentiate these two errors for security concern.

- **Notes:**

    Successful login will return a token. Add token to HTTP header in the following requests to authorize them.

----
### Logout

User logout. Delete a token.

- **URL**

    /token

- **Method:**

    `DELETE`

- **URL Params**

    **Required:**
    
    None.

    **Optional:**

    None.

- **Data Params**
    None.

- **Success Response:**

    - **Code:** 204 NO CONTENT <br>
      **Content:** None.
 
- **Error Response:**

    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** None. <br>
      **Condition:** User not login. 

- **Notes:**

    This request will delete the token in memory thus prevent future requests from authorizing though this token.

    If the user is not in login state, which means the head of the HTTP request does not have any token, it will cause a 401 error.

----

## User Related
Private data of a user:
```
user_private :
{
    name : <string>,
    id : <integer>,
    email : <string>,
    type : <string>
}
```

Public data of a user:
```
user_public :
{
    name : <string>,
    id : <integer>,
    type : <string>
}
```
### Register

Create a user. Do **NOT** log in the user after successful registration.

- **URL**

    /users

- **Method:**

    `POST`

- **URL Params**

    **Required:**

    None.

    **Optional:**

    None.

- **Data Params**
    ```
    {
        email : <string>, # user email, unique across all users, will be checked for uniqueness
        name : <string>, # user name, used for showing on the page, allow duplication
        password : <string>, # user password
        type : <string> # user type, enum, all uppercases, "STUDENT" or "TEACHER"
    }
    ```

    Example:
    ```
    {
        email : "abc@fudan.edu.cn",
        name : "Alice B. Coffee",
        password : "logmein123",
        type : "STUDENT"
    }
    ```

- **Success Response:**

    - **Code:** 201 CREATED <br>
      **Content:** `<user_private>`, User private data
 
- **Error Response:**

    - **Code:** 400 BAD REQUEST <br>
      **Content:** `{ error : "Invalid field" }` <br>
      **Condition:** There is any invalid field.
    
    OR

    - **Code:** 409 CONFLICT <br>
      **Content:** `{ error : "User existed" }` <br>
      **Condition:** Every field is valid but user with the same email already existed.

- **Notes:**

    The syntactic validness of the fields, e.g. email, password, or name, should be checked before sent to server. Although server will do the check anyway. Invalid field or missing field will cause an error response with HTTP code 400 and an error message.

    Already registered email will result in a 409 error.

----
### Delete Account

Delete the account. Login required.

- **URL**

    /account

- **Method:**

    `DELETE`

- **URL Params**

    **Required:**

    None.

    **Optional:**

    None.

- **Data Params**
    
    None.

- **Success Response:**

    - **Code:** 204 NO CONTENT <br>
      **Content:** None.
 
- **Error Response:**

    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login.

- **Notes:**

    None.

----
### Update user profile

Update user profile. Note that only fields that are included in the request body are to be updated.
Fields that are not within the request remain unchanged.

- **URL**

    /account

- **Method:**

    `PUT`

- **URL Params**

    **Required:**

    None.

    **Optional:**

    None.

- **Data Params**
    
    ```
    {
        email : <string>, # OPTIONAL, user email, unique across all users, will be checked for uniqueness
        name : <string>, # OPTIONAL, user name, used for showing on the page, allow duplication
        password : <string>, # REQUIRED, user old password
        newPassword : <string> # OPTIONAL, user new password
    }
    ```

- **Success Response:**

    - **Code:** 204 NO CONTENT <br>
      **Content:** None.
 
- **Error Response:**

    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login.
      
    OR
    
    - **Code:** 422 UNPROCESSABLE ENTRY <br>
      **Content:** `{ error : "Password is wrong." }` <br>
      **Condition:** Password is wrong.

- **Notes:**

    None.

----
### Get user private profile

Get user profile. Login required.

- **URL**

    /account

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    None.

    **Optional:**

    None.

- **Data Params**
    
    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<user_private>`, private data of the user
 
- **Error Response:**

    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login.

- **Notes:**

    None.

----
### Get user public data

Get user public profile.

- **URL**

    /users/{uid}

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    `uid=<integer>`

    **Optional:**

    None.

- **Data Params**
    
    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<user_public>`, public data of the user
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "User not found" }` <br>
      **Condition:** User not found.

- **Notes:**

    None.

----

## Graph Related
Everything here needs authorized.

Meta data structure of a graph:
```
graph_meta :
{
    name : <string>,
    id : <string>,
    description : <string>
}
```

Jsmind data of the graph:
```
{
    jsmind : <string>
}
```
### List all graphs of the course

- **URL**

    /courses/{cid}/graphs

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    `cid=<integer>` 

    **Optional:**

    None.

- **Data Params**

    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** 
    ```
    {
        graphs : [
            <graph_meta>,
            ...
        ],
        graph_num : <integer>
    }
    ```
 
- **Error Response:**

    None.

- **Notes:**

    None.

----
### Add a new graph to the course

Teacher create a new graph for a course. Need authorization.

- **URL**

    /courses/{cid}/graphs

- **Method:**

    `POST`

- **URL Params**

    **Required:**

    `cid=<integer>`

    **Optional:**

    None.

- **Data Params**
    ```
    {
        name : <string>, # name of the graph
        description : <string>, # description of the graph
        jsmind : <string> # initial jsmind data, by default, send an empty string ""
    }
    ```

    Example:
    ```
    {
        name : "Advanced Topics Graph",
        description : "This is the graph of advanced topics of the course. We will cover this part later in this course.",
        jsmind : ""
    }
    ```

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<graph_meta>`, metadata of the newly created graph
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Course not found" }` <br>
      **Condition:** Course id invalid.
    
    OR
    
    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or user is not the teacher of this course.

- **Notes:**

    None.

----
### Get jsMind of the graph

Get jsMind of the graph in a string format.

- **URL**

    /graphs/{gid}/jsmind

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    `gid=<integer>`

    **Optional:**

    None.

- **Data Params**
    
    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `{jsmind : <string>}`, jsmind of the graph
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Graph not found" }` <br>
      **Condition:** Graph id invalid.

- **Notes:**

    None.

----
### Update jsMind of the graph

Update jsmind of the graph. Needs Authorization.

- **URL**

    /graphs/{gid}/jsmind

- **Method:**

    `PUT`

- **URL Params**

    **Required:**

    `gid=<integer>`

    **Optional:**

    None.

- **Data Params**
    ```
    {
        jsmind : <string> # jsmind json in a string
    }
    ```

    Example:
    ```
    {
        jsmind : ""
    }
    ```

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `{jsmind : <string>}`
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Graph not found" }` <br>
      **Condition:** Graph id invalid.
      
    OR
    
    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or user is not the teacher of this course.

- **Notes:**

    None.

----
### Get meta data of the graph

Get graph meta data.

- **URL**

    /graphs/{gid}

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    `gid=<integer>`

    **Optional:**

    None.

- **Data Params**
    
    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<graph_meta>`, metadata of the graph
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Graph not found" }` <br>
      **Condition:** Graph id invalid.

- **Notes:**

    None.

----
### Update meta data of the graph

Update data of the graph. Needs Authorization.

- **URL**

    /graphs/{gid}

- **Method:**

    `PUT`

- **URL Params**

    **Required:**

    `gid=<integer>`

    **Optional:**

    None.

- **Data Params**
    ```
    {
        name : <string>, # OPTIONAL
        description : <string> # OPTIONAL
    }
    ```

    Example:
    ```
    {
        name : "Advanced Topics Graph",
        description : "This is the graph of advanced topics of the course. We will cover this part later in this course."
    }
    ```

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<graph_meta>`, metadata of the updated graph
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Graph not found" }` <br>
      **Condition:** Graph id invalid.
      
    OR
    
    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or user is not the teacher of this course.

- **Notes:**

    None.

----
### Delete the graph

Delete the graph.

- **URL**

    /graphs/{gid}

- **Method:**

    `DELETE`

- **URL Params**

    **Required:**

    `gid=<integer>`

    **Optional:**

    None.

- **Data Params**
    
    None.

- **Success Response:**

    - **Code:** 204 NO CONTENT <br>
      **Content:** None.
 
- **Error Response:**

    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or user is not the teacher of this course.
    
    OR

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Graph not found" }` <br>
      **Condition:** Graph id invalid.

- **Notes:**

    None.

----

## Node Related
Everything here needs authorized.

Meta data of a node:
```
node_meta :
{
    title : <string>,
    id : <string>
}
```
### List all nodes of the graph

- **URL**

    /graphs/{gid}/nodes

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    `gid=<integer>` 

    **Optional:**

    None.

- **Data Params**

    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** 
    ```
    [
        <node_meta>,
        ...
    ]
    ```
 
- **Error Response:**

    None.

- **Notes:**

    None.

----
### Add a node to the graph
### Get data of the node
### Update date of the node
### Delete the node

- **URL**

    /nodes/{nid}

- **Method:**

    `DELETE`

- **URL Params**

    **Required:**

    `nid=<integer>`

    **Optional:**

    None.

- **Data Params**

    None.

- **Success Response:**

    - **Code:** 204 NO CONTENT <br>
      **Content:** None.
 
- **Error Response:**

    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or user is not the teacher of this course.
    
    OR

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Node not found" }` <br>
      **Condition:** Node id invalid.

- **Notes:**

    None.

----

## Resource Related
Everything here needs authorized.

The way to upload file, please refer to 
[this blog](https://o7planning.org/en/11673/spring-boot-file-upload-with-angularjs-example).

Resource metadata structure:
```
resource :
{
    id : <integer>,
    title : <string>,
    link : <string> #OPTIONAL, if there is link, it means the resource is just a link, redirect to the link
}
```
### List all resources of the node

- **URL**

    /nodes/{nid}/resources

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    `nid=<integer>` 

    **Optional:**

    None.

- **Data Params**

    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** 
    ```
    [
        <resource>, # just metadata
        ...
    ]
    ```
 
- **Error Response:**

    None.

- **Notes:**

    None.

----
### Add a new resource to the node

Teacher add a new resource to a node. Need authorization.

- **URL**

    /nodes/{nid}/resources

- **Method:**

    `POST`

- **URL Params**

    **Required:**

    `nid=<integer>`

    **Optional:**

    None.

- **Data Params**
    ```
    {
        title : <string>, # title of the resource
        link : <string> # OPTIONAL, link of the resource
        file : <file> # OPTIONAL, file of the resource
    }
    ```

    Example:
    ```
    {
        title : "PRML Book",
        link : "http://some.domain/u/r/l/prml.pdf",
        file : null
    }
    ```

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<resource>`
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Node not found" }` <br>
      **Condition:** Node id invalid.
    
    OR
    
    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or user is not the teacher of this course.

- **Notes:**

    None.

----
### Get meta data of the resource

Get resource meta data.

- **URL**

    /resources/{rid}

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    `rid=<integer>`

    **Optional:**

    None.

- **Data Params**
    
    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<resource>`
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Resource not found" }` <br>
      **Condition:** Resource id invalid.

- **Notes:**

    None.

----
### Get file of the resource
### Get meta data of the resource

Get resource file.

- **URL**

    /resources/{rid}/file

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    `rid=<integer>`

    **Optional:**

    None.

- **Data Params**
    
    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<file>`
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Resource not found" }` <br>
      **Condition:** Resource id invalid or resource is just a link.

- **Notes:**

    None.

----
### Update data of the Resource

Update data of the resource. Needs Authorization.

- **URL**

    /resources/{rid}

- **Method:**

    `PUT`

- **URL Params**

    **Required:**

    `rid=<integer>`

    **Optional:**

    None.

- **Data Params**

    ```
    {
        title : <string>, # OPTIONAL
        link : <string>, # OPTIONAL
        file : <file> #OPTIONAL 
    }
    ```

    Example:
    ```
    {
        title : "PRML Book",
        link : "http://some.domain/u/r/l/prml.pdf",
        file : null
    }
    ```

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<resource>`
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Resource not found" }` <br>
      **Condition:** Resource id invalid.
      
    OR
    
    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or user is not the teacher of this course.

- **Notes:**

    None.

----
### Delete the resource

Delete the resource.

- **URL**

    /resources/{rid}

- **Method:**

    `DELETE`

- **URL Params**

    **Required:**

    `rid=<integer>`

    **Optional:**

    None.

- **Data Params**
    
    None.

- **Success Response:**

    - **Code:** 204 NO CONTENT <br>
      **Content:** None.
 
- **Error Response:**

    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or user is not the teacher of this course.
    
    OR

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Resource not found" }` <br>
      **Condition:** Resource id invalid.

- **Notes:**

    None.

----

## Lecture Related
Almost the same as resources
url is `/lectures/{lid}`

## Question Related
Everything here needs authorized.

Question data structure:
```
question: # Response format
{
    id : <integer>,
    description : <string>,
    type : <string>, # "MULTIPLE_CHOICE" or "SHORT_ANSWER"
    choices : [ # presented only when type is "MULTIPLE_CHOICE"
        {
            key : <string>,
            value : <string>
        },
        ...
    ],
    answer : <string>,
    submission_num : <interger>, # OPTIONAL only available for teacher, otherwise it's null
    correct_num : <interger> # OPTIONAL only available for teacher AND type is MULTIPLE_CHOICE, otherwise it's null
}
```
Note that `answer` is the `key` of the choice if `type` is `MULTIPLE_CHOICE`. 
For teacher, it is the correct answer, `null` if `type` is `SHORT_ANSWER`.
For student, it is the answer he choose, `null` if not answered yet.

### List all questions of the node

- **URL**

    /nodes/{nid}/questions

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    `nid=<integer>` 

    **Optional:**

    None.

- **Data Params**

    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** 
    ```
    [
        <question>,
        ...
    ]
    ```
 
- **Error Response:**

    None.

- **Notes:**

    None.

----
### Add a new question to the node

Teacher add a new question to a node. Need authorization.

- **URL**

    /nodes/{nid}/questions

- **Method:**

    `POST`

- **URL Params**

    **Required:**

    `nid=<integer>`

    **Optional:**

    None.

- **Data Params**
    ```
    {
        description : <string>,
        type : <string>, # "MULTIPLE_CHOICE" or "SHORT_ANSWER"
        choices : [ # presented only when type is "MULTIPLE_CHOICE"
            {
                key : <string>,
                value : <string>
            },
            ...
        ],
        answer : <string> # presented only when type is "MULTIPLE_CHOICE"
    }
    ```

    Example:
    ```
    {
        description : "What is Neo4j?,
        type : "MULTIPLE_CHOICE",
        choices : [
            {
                key : "A",
                value : "A server."
            },
            {
                key : "B",
                value : "A database."
            },
            {
                key : "C",
                value : "A programming language."
            }
        ],
        answer : "B"
    }
    ```

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<question>`
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Node not found" }` <br>
      **Condition:** Node id invalid.
    
    OR
    
    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or user is not the teacher of this course.

- **Notes:**

    None.

----
### Get data of the resource

Get resource meta data.

- **URL**

    /questions/{qid}

- **Method:**

    `GET`

- **URL Params**

    **Required:**

    `qid=<integer>`

    **Optional:**

    None.

- **Data Params**
    
    None.

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<question>`
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Resource not found" }` <br>
      **Condition:** Resource id invalid.

- **Notes:**

    None.

----
### Update data of the Resource

Update data of the resource. Needs Authorization.

- **URL**

    /questions/{qid}

- **Method:**

    `PUT`

- **URL Params**

    **Required:**

    `qid=<integer>`

    **Optional:**

    None.

- **Data Params**

    ```
    {
        description : <string>,
        choices : [ # OPTIONAL
            {
                key : <string>,
                value : <string>
            },
            ...
        ],
        answer : <string> # OPTIONAL
    }
    ```

    Example:
    ```
    {
        description : "What is Neo4j?
        choices : [
            {
                key : "B",
                value : "A server."
            },
            {
                key : "C",
                value : "A database."
            },
            {
                key : "D",
                value : "A programming language."
            }
        ],
        answer : "B"
    }
    ```

- **Success Response:**

    - **Code:** 200 <br>
      **Content:** `<question>`
 
- **Error Response:**

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Resource not found" }` <br>
      **Condition:** Resource id invalid.
      
    OR
    
    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or user is not the teacher of this course.

- **Notes:**

    None.

----
### Delete the question

Delete the question.

- **URL**

    /questions/{qid}

- **Method:**

    `DELETE`

- **URL Params**

    **Required:**

    `qid=<integer>`

    **Optional:**

    None.

- **Data Params**
    
    None.

- **Success Response:**

    - **Code:** 204 NO CONTENT <br>
      **Content:** None.
 
- **Error Response:**

    - **Code:** 401 UNAUTHORIZED <br>
      **Content:** `{ error : "Unauthorized" }` <br>
      **Condition:** User not login or user is not the teacher of this course.
    
    OR

    - **Code:** 404 NOT FOUND <br>
      **Content:** `{ error : "Question not found" }` <br>
      **Condition:** Question id invalid.

- **Notes:**

    None.

----

## Answer Related
Not required.
