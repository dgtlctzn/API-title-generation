[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
# Title Generation API


## Description
Title Gen is an API for generating random song, book, or startup titles.
The following GET endpoint allows queries for title type and amount of titles to be generated:

[titlegen.us-east-1.elasticbeanstalk.com/api/v1/titlegen](http://titlegen.us-east-1.elasticbeanstalk.com/api/v1/titlegen)

Param | Required | Description
------|----------|------------
no | false | number of titles generated. An empty param will return one title
type | true | type of generated title: `song`, `book`, or `startup`.

The format for the returned JSON response is as follows:

```JSON
{
    "error": Boolean,
    "data": [
      String1, 
      String2, 
      etc.] //Array of Generated Titles,
    "type": String,
    "message": String
}
```
The password restricted POST route uses the [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/) package to process language and separate words by [pos tags](https://cs.nyu.edu/grishman/jet/guide/PennPOS.html).
Once sentences are deconstructed and pos tags assigned, words are inserted into a MySQL database by type. 
The GET route queries the database and selects random words by type and orders them in predefined pos structures. 

## Questions
Github profile: [dgtlctzn](https://github.com/dgtlctzn)

If you have any questions about the project please contact josephperry720@gmail.com
## License
This project is covered under the GNU license