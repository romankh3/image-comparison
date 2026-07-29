# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

`image-comparison` is a Java library (published to Maven Central) that compares two `BufferedImage`s of the same size and visually highlights differences by drawing rectangles around the differing regions. Implementation uses only standard JDK/AWT features — no third-party runtime dependencies (test-only deps are JUnit 5 and Mockito).

## Build systems

The repo has **two parallel build systems that must be kept in sync manually**: `build.gradle` (used by CircleCI, `run.sh`) and `pom.xml` (used by the GitHub Actions workflow `.github/workflows/maven.yml`). Note their versions can drift (e.g. Gradle `4.4.0` vs Maven `4.5.0-SNAPSHOT`) — check both when bumping the project version.

### Gradle

```sh
./gradlew build          # clean + compile + test + jar (defaultTasks are clean, build)
./gradlew test           # run the JUnit 5 test suite
./gradlew test --tests "com.github.romankh3.image.comparison.ImageComparisonUnitTest"
./gradlew test --tests "com.github.romankh3.image.comparison.ImageComparisonUnitTest.testMethodName"
./run.sh                 # ./gradlew run — runs the application (mainClassName = ImageComparison)
```

### Maven

```sh
mvn package               # what CI runs (mvn -B package --file pom.xml)
mvn test
mvn test -Dtest=ImageComparisonUnitTest
mvn test -Dtest=ImageComparisonUnitTest#testMethodName
```

Source/target compatibility is Java 8 (`sourceCompatibility 1.8` / `maven.compiler.source/target 1.8`), but CI builds with JDK 11 (GitHub Actions) and JDK 8 (CircleCI docker image).

## Architecture

All code lives under `com.github.romankh3.image.comparison`:

- **`ImageComparison`** — the entry point and the entire comparison algorithm. Construct with an expected/actual `BufferedImage` pair (or resource path strings) and an optional result `File` destination, configure via fluent setters (all setters return `this`), then call `compareImages()` or `simpleComparison()`.
  - `compareImages()`: builds a binary difference matrix (`populateTheMatrixOfTheDifferences`), groups adjacent differing pixels into regions via a recursive flood-fill (`groupRegions` / `joinToRegion`, bounded by `threshold` — the max pixel-distance for two differences to be considered part of the same region), converts regions into `Rectangle`s, merges overlapping rectangles (`mergeRectangles`), then draws them onto a copy of the actual image.
  - `simpleComparison()`: short-circuits on the first differing pixel found — much cheaper, but produces no rectangles/result image, only a MATCH/MISMATCH state.
  - Pixel equality is fuzzy: `isDifferentPixels` treats two pixels as equal unless their Euclidean RGB distance exceeds a `differenceConstant` derived from `pixelToleranceLevel`.
  - `excludedAreas` (a list of `Rectangle`s) are skipped during comparison and can optionally be drawn/filled on the result image.
- **`ImageComparisonUtil`** — static helpers used by `ImageComparison`: reading images from the filesystem or classpath resources, saving images, deep-copying/resizing `BufferedImage`s, and computing overall difference percentage between two images.
- **`model/`**
  - `Rectangle` — min/max `Point` pair; supports overlap detection and merging. This is the unit that regions of pixel differences get converted into and that callers use for `excludedAreas`.
  - `ExcludedAreas` — wraps a `List<Rectangle>` and answers point-containment checks, used to skip pixels during comparison.
  - `ImageComparisonResult` — DTO returned by `compareImages()`/`simpleComparison()`: comparison state, difference percent, the result image, and the list of difference rectangles. Built via static factories (`defaultMatchResult`, `defaultMisMatchResult`, `defaultSizeMisMatchResult`).
  - `ImageComparisonState` — `MATCH` / `MISMATCH` / `SIZE_MISMATCH` enum.
- **`exception/`** — `ImageComparisonException` (read/save/general failures) and `ImageNotFoundException` (resource lookup failure), both unchecked.

Test resources (expected/actual/result PNGs used across unit and load tests) live in `src/test/resources/`; the naming convention `expected#N.png` / `actual#N.png` / `result#N.png` ties fixtures to specific test cases by number.
