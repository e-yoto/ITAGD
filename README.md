# IsTodayAGoodDayTo
By Team **TGNAMO**: **Timothy Mangulabnan  #1724781** and **Emmanuel Bandola Sayoto #1725052**



### Table of Contents


1. Description
2. Features
   1. Proposed Features
   2. Implemented Features
3. Resources
4. Performance



## Description 
IsTodayAGoodDayTo (ITAGD abbr. ) is a mobile application that uses data based on the current weather to determine if the weather is right to perform certain activities.


For example, you can open up the app to see if it is better to go out for a run, stay in and read a book, or many more.


ITAGD implements the OpenWeatherMap API for accurate weather information and forecasting as well as for the Sunset and Sunrise times.


## Features


##### Initial Proposed Features


- Activity Search
- Activity Recommendations
- Activity Creation
- My Activities
- Calendar
- Weather Information
- Notifications and Reminders
- Sunset and Sunrise
- Map with Locations
- Customize Activities Preference
- Navigation Drawer
- Customize Weather Details
- Commute Method Recommendation
- Help and Tutorial
- Animations


##### Implemented Proposed Features


- My Activities
  - Your List of Activities.
- Weather Information
	- See information on the weather.
- Sunrise and Sunset times
	- See at what time the sun will rise and set.
- Activity Archive
	- Archive activities to for later use.
- Daily Notifications
	- Morning notifications to see the weather and activities.
- Sounds of the weather
	- Listen to sounds of the current weather.
- Light and Dark Theme
	- Change the theme based on your preference.



## Resources
##### API


ITAGD uses the [OpenWeatherMap](https://openweathermap.org/api) API to gather information on the weather and on the sunset and sunrise time. 

##### Database 


ITAGD makes use of 1 database that contains 3 tables: activity, archive, and weather. 


The first table is activity, and is used to store information on activities. All CRUD operations can be performed in this table to modify information on an activity. 


The second table is archive, and is used to store information on activities that have been archived. In this table, only there is no Update operation, as we do not want to be able to modify an archived activity's information. When an archived activity is restored, it is deleted from the "archive" table and added back into the "activity" table.


The third table is weather, and is used to store the data on the weather that was called with the API.


##### Icons
For icons, [Flaticon](https://www.flaticon.com/) and [Icons8](https://icons8.com/) were used, as well as the drawable Vector Assets available in Android Studio.


## Performance

- Application Startup

![enter image description here](https://lh3.googleusercontent.com/9KD7SrlwH_-8MyO7s2vqMStFEtzF_Q8zxS-XQyIuI-QG3Xb8PMl5iMAUYwjUhg0-l3zP4MSiNsE "App Startup")

- Sound Clicked

![enter image description here](https://lh3.googleusercontent.com/T4X9Ra01gyMS2pfucLJMkkMW2tI90FadPMiT3LzOwkKv84Y-t5nI9VE0yNlIyanlSYWi5pRd4xE "Sound Clicked")

Most of the CPU usage is used when starting up new activities, as it needs to load images and views. Memory wise, it started up at it's highest when launching the application and then stayed constant when using application, and peaks when sound is played (from Main). In terms of network, most of the usage is at the start, when starting the application as that is when the API is called for the weather information and sunset times. Overall, the application is not very demanding in terms of resources whether CPU, memory, Network, or even Battery.

- Theme Change

![enter image description here](https://lh3.googleusercontent.com/mS_azaqT5oANpQv-Ls0ofnJMrlyGdugheUl4kIBpPuLeIrMVcSj39bLFGdAFrSEuXjgCe1LC_gw "Theme Change")

The image above showcases the application's performance when the theme is changed. As shown in the picture, simply changing the theme asked a unnecessary resources again like networking. The reason to why networking is increased during theme change is because the application's current activity is restarted and created again in order to apply the theme. Which results the application into calling the API again. This issue should be fixed in the future. On the other hand, both CPU and energy increased as the application is applying the theme to each elements and views of the application.

- Opening Sun Activity (Sunrise and Sunset)

![enter image description here](https://lh3.googleusercontent.com/sQ4OwlhwJKhLOjePaU60Wf_AtxnBqXqdJpy08xJ8sz2eLu5AiOd3qsvbQ4w_e-zYKXGdMJ9wOGw "Sun Activity")

As seen in the image above, opening the sun activity (sunrise and sunset) does not require another API call as it has been already called when the application starts in Main Activity. All it needs to do is send an intent to the sunrise/sunset activity. Moreover, both CPU and Energy increased as it is setting up the activity's functions and views. There is also a slight increase in memory as more visual aspects like the activity's background and the theme chosen and retrieved by shared preferences are loaded into the activity.

- From Main activity to Activities activity

![enter image description here](https://lh3.googleusercontent.com/w-z-QwyMV57JVl_P_SvDkmcj2CHKJ-2_mVwoFhNxG-eAB_1aWA1TjyUKevqdEgD6KlhrwakQ6dE "Main to Activities")

- From Activities activity to Archives activity

![enter image description here](https://lh3.googleusercontent.com/E4uBy2k6cu_v4yx6GpFR4AnbjU9i98avE3i3GAUnv4eFdpubOf7nT__m3N8yYXMJ5Y4CUD_FBGQ "Activities to Archives")

Activities activity and Archive activity are very similar to each other (recycler view that contains the user's plans/activities). Which is why they both have the same performance.

- Changing List to Grid, Grid to List layout

![enter image description here](https://lh3.googleusercontent.com/PBj-WyxCGjHNpQkF5HtXeOd4SaVP1Y0GAqIF7bhHDqzr-TiKCrzjSnk0dxacdBS87Eb1sr3hAqA "List to Grid")

As changing the layout does not require a lot of resources as all it does is changing the placement of the items in the recycler view, there are only minimal increases in CPU and energy.

- Deleting an Item then archiving it

![enter image description here](https://lh3.googleusercontent.com/nmtYyS0kAVfjJ78g3YKXcLZkJADX8vukdBGO5aJdHW73AUrU8XHFC-uagSKxPd0gzzyamdeQltg "Delete and Archive")

In the image above, there are two instances in both CPU and energy where there was an increase. The first instance corresponds to deleting an item. There was a slight increase as the item is both removed from the recycler view and the database. The second instance was clicking on "archived" shown by a snackbar. This resulted into adding the deleted item into the Archives Activity's recycler view and adding it into the archives table in the database.

- Restoring from the Archives back to the Activities Activity

![enter image description here](https://lh3.googleusercontent.com/t_EXQhWg_GiLeYZ9wkVQcJfMHfrNFqRcjwCJaaFkVQePgesOXClErZVQIGGKrkvNTUa0hgTkpUg "Archives to Activities")

This segment happens when an archived item in the Archives Activity is saved back to the Activities recycler view.  The first increase in both CPU and energy corresponds to removing the archived item from the Archive recycler view and removing it as well from the Archive database. The second increase corresponds to when "OK" is clicked from a snackbar shown in order to return to the Activities recycler view which now includes the item saved back from the archives.

- Creating new Activities
![enter image description here](https://lh3.googleusercontent.com/ORj19l9FEYnUd4cc3n5rKxxTntlDMyzWwcFR1wtgkXUYkGl4WHN8DylBkDjQLzQHKHaHbHVsb6iS "Create Activity")
The image above shows the resources used when a user creates a new activity. The memory usage stays consistent throughout usage at a low level of usage. Energy usage wise, not much energy is consumed until the "Save" button is pressed and the activity is actually added to the database. When it is added, Energy consumption almost reaches Medium level. The same is the case for the CPU. Usage is very low until the activity is added to the database, in which case CPU usage reached 32%.

- Updating created Activities
![
](https://lh3.googleusercontent.com/cvG_tsa4sI3pBdT15GBAVySUVxrfJslnqXPjnPZwkYbcgXZFjl_CjwlV0tY_HyelOlGYJJCXFRrr "Updating activity")
The resources used when updating an activity is very similar as creating an activity: low usage until the database is updated.
- Deleting an activity
![
](https://lh3.googleusercontent.com/tteCAc6rNS4wPDaTpuneZS0NCkS4eFWZNPst9uIAy_gswDHay7XJFyELZWz2PNEPo3t4kge4ysIL "delete activity")
Deleting an activity takes very little resources, even when accessing the database. Deleting from the database used a little over 10%, which is very little compared to creating an activity. Memory does not use more or less when the activity is deleted. Energy use is very low when deleting the activity. Energy usage went up to "Light" when loading the database contents and when opening the dialog to confirm deletion, but is labeled as "none" when the activity was deleted.
