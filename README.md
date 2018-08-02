# Connectivity
Android network utility, register and listen network status changes. Also can use getting simple network
status information.

## Prerequisites
First, dependency must be added to build.gradle file.
```groovy
implementation 'nurisezgin.com.connectivity:network:1.0.0'
```

## How To Use
* ConnectivityMonitor needs application context, before startProfiling provide context instance through
singleton object.
```java
    ConnectivityMonitor.getInstance()
        .registerAppContext(() -> appContext);
```
* Add rx consumer for watching changes.
```java
    ConnectivityMonitor.getInstance()
        .addWatcher(status -> {...});
```
* Start & Stop monitoring, can start profiling before add watcher or after.
```java
    ConnectivityMonitor.getInstance()
        .startProfiling();
    ConnectivityMonitor.getInstance()
        .stopProfiling();
```

## Authors
* **Nuri SEZGIN**-[Email](acnnurisezgin@gmail.com)

## Licence

```
Copyright 2018 Nuri SEZGİN

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```