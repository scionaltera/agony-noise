# agony-noise

I needed a sandbox for playing with some procedural generation techniques where I didn't have to compile and run the entire [Agony Engine](https://github.com/scionaltera/agony-engine) project and could iterate faster.

This isn't meant to be a library for doing Fractal Brownian Motion. It's just a project for me to test stuff I may port into a real game later. It might also be useful if you are trying to implement FBM yourself and need an example to look at.

## Compiling

```java
./gradlew clean build
```

## Running

```java
java -cp build/libs/agony-noise-0.1.0-SNAPSHOT.jar com.agonyengine.noise.ImageGenerator [raw|fbm]
```

You should see a new PNG image appear in your project after a few seconds when it runs. If it's not what you wanted, tweak the parameters in the source and try again!