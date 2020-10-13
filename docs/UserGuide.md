
# User Guide  
  
## Introduction  
  
Zoomaster is a desktop app for organizing website links, optimized for use via a   
Command Line Interface (CLI) while retaining benefits of a Graphical User Interface (GUI).   
If you can type fast, Zoomaster can help fetch useful website links for you quicker than the bookmark function on your browser.  
  
### Table of contents  
* [Quick Start](#quick-start)  
* [Features](#features)  
  * [Global](#global)  
    * [Switch mode:](#mode) **mode**
    *  [Exit:](#exit)  **exit**   
  * [Bookmark mode](#bookmarkmode)  
    * [Show bookmarks:](#showbookmark) **show**
    * [Add bookmark:](#addbookmark)  **add**   
    * [Delete bookmark:](#deletebookmark)  **delete** 
    * [Find bookmark:](#findbookmark)  **find** 
    * [Launch bookmark:](#launchbookmark)  **launch** 
   * [Timetable mode](#timetablemode)  
* [FAQ](#faq)  
* [Command Summary](#command-summary)  
  
## Quick Start  
  
{Give steps to get started quickly}  
  
1. Ensure that you have Java 11 or above installed.  
1. Down the latest version of `Zoomaster` from [here](http://link.to/duke).  
  
## Features   
{Give detailed description of each feature}  

 <a name="global"></a> 
 ### Global
 The commands below can be used in both modes of the app.   
 
<a name="mode"></a>  
#### Switch mode: `mode`  
Switches between the “bookmark” and “timetable” modes. Depending on the mode,   
the behaviour of the commands below changes.  
  

    Format: mode {bookmark/timetable}
    
  
* The `DEADLINE` can be in a natural language format.  
* The `TODO_NAME` cannot contain punctuation.    
  
Example of usage:   
* `mode bookmark`
* `mode timetable` 

<a name="exit"></a>  
#### Exit the app: `exit`  
Exits the application 
  

    Format: exit    
  

  <br/><br/> 
  <a name="bookmarkmode"></a>  
### Bookmark mode  

  <a name="showbookmark"></a>  
#### Show bookmarks: `show`  
Prints all bookmark in the bookmark list  
  
    Format: show

  
<a name="addbookmark"></a>  
#### Add bookmark: `add`  
Add a URL bookmark under a certain topic and description.  
  
    Format: add {MODULE(optional)} {DESCRIPTON} {URL}
 
* The `MODULE` is optional.  
* The `TOPIC` and `DESCRIPTION` can only contain one word each.  
* The `URL` has to start with `www.` or `https://`.  
* There should be strictly one space between the `TOPIC`, `DESCRIPTION` and `URL`.    
  
Example of usage:   
`add CS2113T tutorial www.cs2113t.nus.edu.sg/`  
  
`add website https://cs2113t.nus.edu.sg/`  

<a name="deletebookmark"></a>  
#### Delete bookmarks: `delete`  
Deletes a bookmark with the specified index.  The index will correspond to the index of that bookmark in the list. You can do a `show` command to check the bookmark indexes.  
  
    Format: delete {INDEX}
    
Example of usage:   
  
* `delete 2`  
  
* `delete 4`  

<a name="findbookmark"></a>  
#### Find bookmarks: `find`  
Finds bookmarks with matching module and description and prints them
  
    Format: find {MODULE} {DESCRIPTION(optional)}

* The `DESCRIPTION` is optional.  
* The `TOPIC` and `DESCRIPTION` can only contain one word each.  
* There should be strictly one space between the `TOPIC`, `DESCRIPTION`.

Example of usage:   
  
* `find CS2113 tutorial`  
  
* `find CS2113`  

<a name="launchbookmark"></a>  
#### Launch bookmarks: `launch`  
Launches bookmarks in the default browser using either:
* Index
* Matching module or description
The index will correspond to the index of that bookmark in the list. You can do a `show` command to check the bookmark indexes.  
  
    Format: launch {INDEX}
    
    Format: launch {MODULE} {DESCRIPTION(optional)}

* The `DESCRIPTION` is optional.  
* The `TOPIC` and `DESCRIPTION` can only contain one word each.  
* There should be strictly one space between the `TOPIC`, `DESCRIPTION`.   

Example of usage:   
  
* `launch CS2113 tutorial`  
  
* `launch CS2113`  
  
  <br/><br/> 
<a name="timetablemode"></a>  
### Timetable mode    
  
  
## FAQ  
  
**Q**: How do I transfer my data to another computer?   
  
**A**: Copy the `data/data.txt` and `data/slots.txt` file to the directory where the `jar` file is at.   
       Start the application and all the data should be loaded.  
  

## Command Summary
**Action** | **Format, Examples**
------------ | -------------
**mode**|`mode {bookmark/timetable}`<br>example: `mode bookmark`
**exit**|`exit`
**Bookmark Mode**|
**show**|`show`
**add**|`add {MODULE(optional)} {DESCRIPTON} {URL}` <br>example: `add CS2113T tutorial www.yahoo.com`
**delete**|`delete {INDEX}`<br>example: `delete 2`
**find**|`find {MODULE} {DESCRIPTION(optional)}` <br>example: `find CS2113 tutorial`
**launch**|`launch {MODULE} {DESCRIPTION(optional)}` <br>example: `launch CS2113`
**Timetable Mode**|
**slotadd**|`before  <yyyyMMdd HHmm>`<br>example: `before 20210909 1159`
**slotdel**|`after <yyyyMMdd HHmm>`<br>example: `after 19990101 0100`
**show**|`today`
