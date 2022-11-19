# ActivityLifecycleTracker

View your activity/fragment lifecycle events in a web app. No manual logging entries required!

Built on top of [AndroidRestServer](https://github.com/Sushobh/AndroidRestServer/)

https://user-images.githubusercontent.com/20350751/202836947-f97ddaac-50b5-4cdc-8494-1ae44ca10e0c.mp4

## How to get it?
```
   dependencies {
       implementation 'com.sushobh:activitytracker:1.0.1'
    }
```
The library picks up the context using a empty content provider so that you don't have to initialize it.

## Accessing the web app

If your phone and your computer are on the same wifi network, you simply have to navigate to **<PHONE_IP_ADDRESS>:8080**, <br/> 
for example 192.1.0.33:8080 , the app also displays a notification that gives you the ip address <br/> so that you don't have to bother with finding your IP address.

If you are running a emulator or if your phone and computer are not on the same network or if you are blocked by vpn, 
you will need to be on usb debugging.

Then run this command,

 **adb forward tcp:8080 tcp:<PORT_OF_YOUR_CHOICE>** , for example  **adb forward tcp:8080 tcp:3000**

## Troubleshooting adb connections

If you are using the adb command and are not able to see the web app. Verify that port forwarding is
working. You can run this command to verify it.

**adb forward list**

which outputs this, assuming that you had run the forwarding command.
```
  emulator-5554 tcp:8080 tcp:3000
```

You can also try this command to remove all existing port forwards and re run the forwarding command.

**adb forward --remove-all**


