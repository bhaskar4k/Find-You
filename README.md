# Find-You

Features of project

1. User Creation/Login :-
- Every login will provide a month long session so that user doesn't waste their time to login every time. There will be no more login screen untill the sesssion ends or user does logout manually.

2. Newsfeed :-
- Built an API that will provide a dynamic newsfeed for every active user.
- Wrote algorithm for dynamic newsfeed in such a way that :
For example you follow two people Ram and Shyam then ram and shyam's post will be recommended first (Sorted by Newest post to older post). Then random posts from other users post will be recommended.

3. Like/Comment :-
- From newsfeed you can do like or comment on any post you want.
- Also you can view like list to see who liked the post and comment box to see all comments done by different user.
- If a post is visible on screen then it's like list and comment box will be updated after each 10 seconds. And if you scroll the post up then like list and comment box will stop updating.
- Approximately at most 1 to 5 post can be visible on screen according to screen size. Then only those 1 to 5 post's like and comment list will be updated after each 10 seconds.
- Made this system using AJAX, JavaScript and DOM Manipulation. For these 1 - 5 post a JavaScript function will be called continiously after each 10 second and that javascript function will make a AJAX call to server and will keep updating like list and comment box using DOM manipulation without loading the webpage.

4. User Profile :-
- In your profile page you can change your profile photo, can change all user details, can change bio.
- You can make, edit or delete post that is created by you.
- You can like comment your own post also.

5. Searchbar :-
- Made a searchbar that will give result according to your inputted text.
- If you type 'r'(uppercase and lowercase doesn't matter) then peoples name starting with 'r' will be shown in real time.
- Made this using AJAX, JavaScript and DOM Manipulation (Same concept as like list and comment box).

6. Follow :-
- You can search any user's profile from searchbar and can follow/unfollow them.
- Your following user's list and count will be updated accordingly. Also the person you did follow/unfollow his/her follower user's list and count will also be updated automatically after each 15 seconds without loading the webpage (Same concept as like list and comment box).
- Made this using AJAX, JavaScript and DOM Manipulation.

7. View other user's profile :-
- You can search other users from searchbar or can click user's name that are showing in like list, comment box, following/follower user's list.
- After viewing any user's profile you can like their post, comment on their post or you can follow/unfollow them.
