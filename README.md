# hm-micronaut
A collection of micronaut libs used by team digihot,

## LeaderElection
A simple leader election library that uses kubernetes elector to elect a leader among a group of micronaut applications.

### Usage
1. Add the following dependency to your micronaut application

```
implementation("com.github.navikt:hm-micronaut-leaderelection:202405140823")

```

2. Add the following configuration to your application.yml

``` 
elector:
  path: ${ELECTOR_PATH:localhost}
```

3. Add the following code to your micronaut application

```
    @LeaderOnly
    @Scheduled(fixedDelay = "30m")
    open fun runFuction() {
        runBlocking {
            // TODO
        }
    }
```
