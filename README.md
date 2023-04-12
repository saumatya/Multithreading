# Multithreading

## fit app

this application demonstrates how multithreading works.

---

-   in one thread, this application counts the distance traveled
-   in the second, data about the location and heartbeat of the user is uploaded to the server
-   in the third thread, the user's heartbeat is counted

---

```
buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //This thread will calculate distance
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Your code here
                        stopRecording();
                    }
                }).start();
                //This thread  will upload the location , distance data to server
                new Thread(new  Runnable() {
                    @Override
                    public void run() {
                        // Your code here
                        uploadToServer();
                    }
                }).start();

                //This thread will get and display hear rate
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Your code here
                        getHeartRate();
                    }
                }).start();
            }
        });
```

This code is related to the implementation of an Android application that has a button with an OnClickListener. When the user clicks on this button, three separate threads will be started to perform three different tasks:

The first thread is responsible for calculating the distance. When the user clicks the button, a new thread will be created using the Runnable interface, which is implemented by providing the code inside the run() method. The stopRecording() method will be called inside this run() method, which will perform the necessary calculations for the distance.

The second thread is responsible for uploading location and distance data to a server. When the user clicks the button, a new thread will be created using the Runnable interface, which is implemented by providing the code inside the run() method. The uploadToServer() method will be called inside this run() method, which will upload the data to the server.

The third thread is responsible for getting and displaying the heart rate. When the user clicks the button, a new thread will be created using the Runnable interface, which is implemented by providing the code inside the run() method. The getHeartRate() method will be called inside this run() method, which will retrieve the heart rate data and display it on the user interface.

By using separate threads for each task, the application can perform these operations simultaneously, without blocking the main UI thread. This ensures a smooth user experience and improves the overall performance of the application.
