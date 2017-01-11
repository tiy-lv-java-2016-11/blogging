# Bloggy Blog

![blog-dispair](blogging-despair-poster.jpg)

## Description
We are finally going to write and deploy a blog.  For this we are going to be using a template to make our site look much nicer than we might be able to manage
in the time we are given.

## Requirements
* Create a new project with Spring Initializr
	* JPA
	* PostGres
	* H2
	* DevTools
	* Web
	* Mustache

### Back End
* Create a `User` object should contain
	* `id`
	* `username`
	* `password`: make sure this is hashed
* `Entry` Object
	* `id`
	* `title`: Title of the post
	* `short`: short description of the post
	* `post`: plain text of the post
	* `createdAt`: LocalDateTime of when the blog was created
	* `user`: for who posted the post
* `Comment` object
	* `id`
	* `entry`: Entry that the comment is for
	* `createdAt`: time comment was created
* Create repositories for each of the objects
* Create controllers for the following:
	* List of the blog posts
	* Detail for the blog post
		* Include the comments
	* Login
		* If the user doesn't exist create them
		* Hash the password
	* Logout
	* Post endpoint to create a new `Entry`
	* Post endpoint to create a new `Comment` for a blog post
* Test post endpoints

### Front End
Do one of the following:
* Create the templates from the provided template download.
	* Integrate the home page to list out the blog posts
		* Add a link to login
		* Add a button/link to logout
	* Integrate the entry detail page with your controller
		* Include the comments in the comment section
		* Include a form to create a new comment
	* Login form
	* New blog entry form
* Use Bootstrap to design all of the above without a template

### Deployment 
* Deploy the system to Heroku

## Hard Mode
* Write tests for all endpoints
* Add Paging for the blog posts and the comments
* Use Travis CI to automatically test your project
* Create api versions of all of the endpoints for public consumption

## Nightmare Mode
* Use Travis CI to deploy code to Heroku automatically
* Convert the front end to use only AJAX and hit the back end api

## Resources
* [Github Repo](https://github.com/tiy-lv-java-2016-11/blogging)
* [Heroku](https://dashboard.heroku.com/)
* [Travis-CI](https://travis-ci.org/)
* [Bootstrap](http://getbootstrap.com/)
