# Spice
This is an Android library that allows a similar experience to the iOS NSFetchedResultsController. It leverages Volley and GSON for REST requests and SugarORM for data (cache) persistence and retrieval.

## Usage Instructions: 
- copy dataCache.jar and sugar.jar into your libs folder
- add the following dependencies to your <b>build.gradle</b>:
  
<code>compile 'com.mcxiaoke.volley:library:1.0.+@aar'</code>

<code>compile 'com.github.satyan:sugar:1.3.1'</code>

<code>compile 'com.google.code.gson:gson:2.3'</code>

<code>compile 'com.google.guava:guava:18.0'</code>
