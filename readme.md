# DraftChat #

## Build & Run ##
Configure DB connection in src/main/resources/application.conf
```sh
$ cd DraftChat
$ sbt
> initDB
> jetty:start (or sbt startJetty)
```

Open [http://localhost:8080/](http://localhost:8080/) in your browser.
Default credentials are admin:admin. 