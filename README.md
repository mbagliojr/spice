# Spice
This is an Android library that allows a similar experience to the iOS NSFetchedResultsController. It leverages Volley and GSON for REST requests and subsequent JSON parsing. It then integrates with your favorite ORM (SugarORM by default) for data (cache) persistence and retrieval. This is ideal for any application looking to do server calls and have a local database for offline viewing of the app.

## Example
- For an example, check out the app directory
- To view the library source, look at the spice directory

## Usage Instructions: 
- copy <b>spice-{version}.jar</b> and <b>sugar-{version}.jar</b> from top level folder into your libs folder
- add the following dependencies to your <b>build.gradle</b>:
  
<code>compile 'com.mcxiaoke.volley:library:1.0.+@aar'</code>

<code>compile 'com.google.code.gson:gson:2.3'</code>

<code>compile 'com.google.guava:guava:18.0'</code>
