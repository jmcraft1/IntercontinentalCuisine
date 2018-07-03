# IntercontinentalCuisine

You must link each app to the firebase database.
1. Sign in to google account and go to https://console.firebase.google.com/ and click add project.
2. Here you will create a name for your project and click create project.
3. On the next screen click add firebase to your android app.
4. Copy and paste the package name from the mainActivity java file into the package name box in the firebase screen.
5. You can pick a nickname for the app if so desired.
6. With the project open in android studio, click the gradle tab on the right side of the window.
7. Expand app name, then expand app name(root), then expand tasks, then expand android, and click on signing report.
8. In the signing report, copy and paste the hexadecimal code from the SHA1: field into the signing certificate on the firebase screen
9. Click on register app.
10. On the next screen, download google-services.json file,  and put this file in the app directory.
11. At this point, you would alter the gradle files, but they are already altered when I commited them to this repository.
12. Once the first app is added to the firebase database project, then go to the project overview and click add another app and repeat the process for the other two aps.
13. Make sure each app has its own google-services.json file in the app folder.
14. Click on the database tab on the left side of the firebase screen.
15. Click on the menu button on the right side of the window, and hit import json.
16. Download firebase.json from this repository and use that file to import to firebase project.
The path for the google-services.json should be HH/app/google-services.json, HHStore/app/google-services.json, and HHDriver/app/google-services.json

Demo video at https://www.youtube.com/watch?v=POSlJEfLIOo&t=5s
