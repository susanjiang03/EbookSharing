EBookSharing Application version 1.0 Dec 14 2015


GENERAL USAGE NOTES
———————————————————


-EBookSharing application is a local Desktop application developed in the Java GUI. Our programm files are located in package ebooksharing1. To let our project run locally , first need to create a JDBC database, as stated in DbConnetor.java file, ( jdbc:derby://localhost:1527/UsersRegistration", "java", "java"), the name of the database is “UsersRegistration”, username is “java”, password is “java”. However, you can change names as you like.  Secondly, run the CREATE_TABLE.java to create the tables the project needs in the database. Then run POPULATEDUMMYDATA.java to populate some data to database.
Run RESET_TABLE.java to delete all records from the database, but tables remains. If want to repopulate the dummy data in database, always run DROP_TABLES.java first, second CREATE_TABLE.java, then POPULATEDUMMYDATA.java. Otherwise there will be foreign key constraint issue.


-After the application is run a welcome page appears that has login as well as registration section to it. Registered user may sign in with right credentials where as new user can register themselves first before logging in with unique Email address as well as the username. To register as Super user the system will ask for a special code which we have set it to be “access”. Without that code any body can register only as regular user. The welcome page consist of other two buttons ‘continue as visitor’ which navigates to the another page with very minimal access feature. The other button ‘exit’ simply exit closes the system. 


-After the successful sign in and based on the credential to be of super user or regular user the user will be navigated to another page. If super user page opens, it has all the features of the system. If the user are regular user, the regular user page opens that has all the features except super user privileges. 


-The browse book page consists of all the books in the system and the corresponding reviews, ratings and reading points of the book.
—-A user can read a selected book by clicking on the button ‘read selected book’. If the book is not shared by the user, then the user is asked to rent the book by using their points. The reading points of the book indicate the points needed to read the book in 1 second. The user is prompted to enter the amount of time he or she wishes to read the book for. If the user has enough points, he or she is taken to the page where the text in the book can be read otherwise they are notified that they do not have enough points. The page closes as soon as the time is up. If a user finishes reading the book before assigned he or she can close the page. If the book is shared, the user is notified the time they have to read the book. 
—-A user can make a complaint against the book if he or she wishes to. The user clicks on the complaint button to complaint against the selected book. The user is taken to a page that consists of text area to write about the complaint a button to find the number of bad words in the book. On the clicking the button, the user is asked to enter the number of bad words they want to look for in the book. They are prompted to enter the bad words for the number they chose to enter in the first prompt. The system then looks for the bad word and displays the total number of bad words. A user can complaint if he or she has read the book and not looked for a bad word, found bad words but did not read the book or read the book and found bad words as well. On clicking on complaint, the complaint is submitted to the super user to review.
—-A user can rate/review a book by clicking on the rate/review button. The user can only do so if they have read the book. They are taken to a page where they can rate/review the book. The average rating is taken of all users and individual ratings is displayed as well. The reading history of the user that rated the book is displayed also.
—-A user can click on recommendations button to look for any recommendation. On clicking the button a page pops up the shows five recommended books by the system. If the user hasn’t read the book, they are shown five most read books in the application based on reading duration for each book. If the user has read a book, they are shown the top 5 books under the category of the last book read by the user.
—-A user invites a different user to share a book for a particular amount of time by entering the time and the choosing the user they want to share the book with. A user cannot share a book with himself. The user can refer to the invitation panel to check the status of their invitations.


-User profile displays all the details of the User activities. Approved Book on shelves are the list of books contributed by the user and approved by the super user. Pending books table shows if the book has been uploaded and it needs to be approved by the super user. The buttons underneath ‘Accept the granted points’ and ‘Reject the granted points’ are for accepting the super user points offer for the contributed book and to reject the offer respectively. List of book table keeps the record of which book has been read by the user for how long.


-Invitations has the record of the shared book invitation been received and invitation been sent to. Two button ‘Accept Invitation’ and ‘Reject Invitation’ are active for Invitation received table. When Invitation is received from other user to share a specific book, the status for that book is pending. Once the user clicks on the pending book and hits accept the book is now shared and status changes to accepted instantaneously. You can’t accept the accepted book twice and can’t delete is as well. Where as the delete button simply delete the book that is still pending and you don’t want to accept the sharing request. 


-Message center is the zone where user can communicate to others. The main purpose of this center is to receive the update of the pending book process. If the super user grants some points a message is received as well as when he declines the book the message is received too. For extra purpose the user can send any message using the text are by choosing the existing user in the system form the dropdown list and simply hit the send button.


-BookUpload comprises the input fields for new book to be uploaded. If the name of the book and author matches you won’t be allowed to contribute that specific book as copyright issue might occur. All the fields need to filled out. On the Upload cover page it is important to attach a .jpg file and in the Upload book it is important to upload .txt file.




 


-Super User can use all the features that a registered user has. Besides, super user can approve a book uploaded with assigning reading points, pending a book with a lower points offered, and reject a book if he think it is not appropriate. The system can allow multiple super users. So a super user cannot approve the book for himself, otherwise, he can cheat by grand as many points as he wants. A super user must wait for other super users’ decision.


- A super user also deal with complaints. A complaints can be non sense, can be regular issue, can be serious issue. A book with 3 regular issues will be removed from the system also all info related to this book. Here , we allow a user to have at most 5 books got removed. If a user has 5 books got removed , he will be put in blacklist, and he can never sign in or register with the same email, but the books he contributed before will remain in the system.
A book with a serious complaint will be removed from the system immediately.


- A super user also will decide to remove or not to remove the books haven’t been read for 1 year or the books have been rated under 5.


- A user will get responding points deducted when his book get removed. 


- A super user can  not deal the complaints or issue about the book he uploaded.


Installation
————————————
EbookSharing Application is run using the NetBeans IDE. There is no .exe file produced yet. No installation is required. It is simply run by using the source code on IDE.


EBookSharing Application can be reached at:


Email: injit.gurung@gmail.com, majithiaaditya@gmail.com, susanjiang03@gmail.com




Copy Rigth 2015: All Right Reserved.