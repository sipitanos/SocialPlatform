hy359-2020-a5

Web Programming - 2020 - A5

1)		Completed the first task with a AJAX request on the deleteUserServlet which is responsible for deleting all the comments and ratings of a post, 
		then deleting each post and in the end deletes the user. 
		After it invalidates the session and instructs the page to go back to signin page.


2)		Completed the second task as the user can see the location of each post with a map. 
		Didnt have time to make it look very pretty but it does the job. There is a button 
		to show the map for each post and a button to close the map. If a user presses the 
		button to show the map while the map is being displayed, the map will refresh with the new location.

3) 		Completed the third task as the password gets encrypted at both client and server side. 
		How it works: 
		when user signs up for first time enters his password then in js at client side i do m5 hash 
		and add salt Then send it to server. Server at servlet takes the hashed passsword. Hassh it 
		again with md5 + some salt and then sends it to the database. When user signins again enters 
		his password that gets hashed again at js + salt. send to appropriate servlet, the servlet hashes 
		it again with md5 and some salt. and then checks that the result is the same as the database saved password.
		
4) Not Implemented

5) Not Implemented

6) 		A user can create a comment for a post and can also delete it. 
		All with their servlets and AJAX requests of course. After deletion or insertion of a comment the page reloads 
		(NO REFRESH ITS DONE SINGLE PAGE APPLICATION STYLE) with the new data.

7) Not Implemented

8) Not Implemented