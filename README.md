# Eurovision lyrics Twitter bot

A Twitter bot that tweets Eurovision song texts as tweets. It uses http://www.diggiloo.net/ as the source. The bot is run twice a day and tweets with the name [@esc_lyrics_](https://twitter.com/esc_lyrics_).

## Environment variables

- The source url: `URL`, e.g. `http://www.diggiloo.net/?random`
- Twitter authentication:
 - `CONSUMER_KEY`
 - `CONSUMER_SECRET`
 - `ACCESS_TOKEN`
 - `ACCESS_TOKEN_SECRET`


## Usage

Run either with lein:
```
$ lein run
```

Or compile and run:
```
$ lein uberjar
$ java -jar target/uberjar/eurovision-lyrics-bot-1.0.0-standalone.jar
```

## License

Copyright (c) 2017 Yrjö Kari-Koskinen <ykk@peruna.fi>

Eurovision lyrics Twitter bot source code is licensed with the MIT License, see [LICENSE](https://github.com/ykarikos/eurovision-lyrics-bot/blob/master/LICENSE).

## Thanks

This project is a grateful recipient of the [Futurice Open Source sponsorship program](http://futurice.com/blog/sponsoring-free-time-open-source-activities?utm_source=github&utm_medium=spice).
