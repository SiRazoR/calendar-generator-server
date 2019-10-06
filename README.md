# Calendar-Generator
This project contains two different applications. One of which is a calendar-generator-server, an application written with **Spring Boot** that provides schedule API. 

## Description
**Calendar-generator-server** is a Spring Boot application written in Java that contains Web Scraper which is crawling through University Of Economics in Cracow schedules. It provides information as available dean groups, lectures of every group which comes with exact date, hour, lecturer, location and name of the lecture. From available data API creates simple or complex schedules. It means that generated schedules may contain many groups which is helpful for students with multiple specializations or simply those who want to attempt to different group courses. Besides that schedule API can create a custom URL which will contain previously provided groups data, so users may connect that to any application which supports iCalendar files. Providers as Google with their calendar application will frequently send GET to acquire fresh informations in case of cancelled lectures. Because of that schedule will be refreshed if changes will occur.

 # Project overview
You can check the preview of the application written in React that uses this API by just clicking <a href="https://sirazor.github.io/calendar-generator-client/" target="_blank">THIS</a> hyperlink. To check a project, visit <a href="https://github.com/SiRazoR/calendar-generator-client" target="_blank">calendar-generator-client</a>
 
