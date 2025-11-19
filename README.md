# lib-version

The **lib-version** repository contains a reusable Java library used in the DODA A1 assignment. Its purpose is to provide runtime access to the library’s version number via the `VersionUtil` class. The version is automatically extracted from Maven metadata inside the JAR, ensuring that the **app** repository can always display the correct library version.

## Features

The library exposes one public method:

`getVersion()` – returns the version of the library at runtime.

The version is read from:
META-INF/maven/nl.tudelft.doda/lib-version/pom.properties


This supports:
- Stable releases (e.g., `1.0.0`)
- Snapshot builds (e.g., `1.0.1-SNAPSHOT`)
- Pre-releases (e.g., `1.0.1-feature-branch-20251119-120011`)

This satisfies the version-awareness requirements in **F1** and the automated versioning requirement in **F11**.

## Project Structure
lib-version
├── pom.xml \
├── README.md \
├── src \
│ └── main \
│ └── java\
│ └── nl \
│ └── tudelft \
│ └── doda \
│ └── VersionUtil.java \
└── .github \
└── workflows \
├── release-lib-version.yml \
└── prerelease-lib-version.yml


## Using lib-version in the app repository

Add this dependency to your app repository's `pom.xml`:
```xml
<dependency> 
    <groupId>nl.tudelft.doda</groupId> 
    <artifactId>lib-version</artifactId> 
    <version>1.0.0</version> 
</dependency>
```
Add the GitHub Packages Maven repository:
```xml
<repositories>
  <repository>
    <id>github</id>
    <name>GitHub Packages</name>
    <url>https://maven.pkg.github.com/doda2025-team17/lib-version</url>
  </repository>
</repositories>
```

Use the library in your Java code:
```java
import nl.tudelft.doda.VersionUtil;

String version = VersionUtil.getVersion();
```

## Automated Versioning (F11)

This repository includes two GitHub Actions workflows implementing the versioning system required for F11.

### Stable Release Workflow (release-lib-version.yml)

Triggered when a Git tag matching: vX.Y.Z is pushed

This workflow does the following:

* Validates that main contains X.Y.Z-SNAPSHOT
* Sets the project version to X.Y.Z
* Builds and publishes the library
* Commits the released version
* Bumps to X.Y.(Z+1)-SNAPSHOT
* Pushes commits and retags the release

### Pre-release Workflow (prerelease-lib-version.yml)

Triggered when pushing to any non-main branch.

This workflow does the following:
* Reads the base version from pom.xml
* Generates a pre-release version: BASEVERSION-branchname-timestamp
* Builds and publishes the library
* Does not modify main 

## Creating a Stable Release

Run the following:
```css
git checkout main
git pull
git tag v1.0.0
git push origin v1.0.0
```

GitHub Actions will automatically:
* Build the library
* Publish it
* Bump to the next snapshot version
* Push updated files to main

## Building Locally

```css
mvn clean install
```

The resulting JAR appears in:
```sql
target/lib-version-<version>.jar
```

## Branch Strategy
main  
* Purpose: Active development  
* Version format: X.Y.Z-SNAPSHOT

feature branches  
* Purpose: Pre-release builds  
* Version format: X.Y.Z-branch-timestamp

vX.Y.Z
* Purpose: Stable releases  
* Version format: X.Y.Z  


