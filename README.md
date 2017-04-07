PASSMEMBER
----------

### INTRODUCTION
It is a JAVA password manager based [Revelation](https://revelation.olasagasti.info/). 

The purpose is to make a clone of Revelation which multi-platform. Also, I try to use as much as I can plain JAVA. 

### FUNCTIONALITIES
* Open a password file
* Save a password file (not implemented encryption yet)
* Append a simple password entry
* Delete a password entry

### TECHNICAL ASPECTS

Passmember is actually a Maven project using Java 8. It will be interesting to make it compatible with JAVA 6 in order to used more widely. 

### ROADMAP
1. Append Java doc
2. Improve tree management for password storing 
3. Improve modal prompts for password typing
4. Implement an option modal prompt
5. Use .ini file to store and manage options chosen by the user
6. Implement internationalization
7. Export passwords into different formats as :
    * CSV
    * Excel
    * XML
    * HTML
* Provide more information about passwords with :
    * an added date
    * a modified date
    * a password type (mail, ssh, website, ...)
    * password strength

### ADDITIONAL NOTES

Feel free to suggest new improvements or functionalities with pull requests as contributions. Il will try to write a wiki about PassMember to provide more information about how to use it and how this software works in background.
