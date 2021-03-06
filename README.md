# Vextensions

[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](LICENSE)
[![Discord](https://img.shields.io/discord/785956343407181824.svg)](https://discord.gg/wvcX92VyEH)
[![Build Status](https://ci.vironlab.eu/job/Vextension/badge/icon)](https://ci.vironlab.eu/job/Vextension/)

### Project description

Vextension JVM Utility v2.0.0-SNAPSHOT

This version brings breaking changes to Version 1

---

## Project is still a Snapshot!

---

## Maven Dependency

```gradle

//Repository - Groovy
maven {
    name = "vironlab"
    url = "https://repo.vironlab.eu/repository/maven-snapshot/"
}

//Repository - Kotlin DSL
maven("https://repo.vironlab.eu/repository/maven-snapshot/")

//Dependency - Common
compileOnly("eu.vironlab.vextension:vextension-common:2.0.0-SNAPSHOT")

//Dependency - Command
compileOnly("eu.vironlab.vextension:vextension-command:2.0.0-SNAPSHOT")

//Dependency - Console
compileOnly("eu.vironlab.vextension:vextension-cli:2.0.0-SNAPSHOT")

//Dependency - Minecraft - Server
compileOnly("eu.vironlab.vextension:vextension-minecraft-server:2.0.0-SNAPSHOT")

//Dependency - Minecraft - Proxy
compileOnly("eu.vironlab.vextension:vextension-minecraft-proxy:2.0.0-SNAPSHOT")
```

---

## Modules

### Common

The common Module includes all Utils required for the other Modules and some things for your Project

### CLI

The CLI is an easy to use Command-Line Interface for your Application

### Command

This is a General Command System you can use for every software with user input. In Addition it has an implementation
for our CLI Module

### Minecraft - Server

There are some Utils and Builder for the latest Bukkit and Sponge API

### Minecraft - Proxy

A Launcher for Vextension on your Proxy Server like Bungeecord or Velocity wich come with some utils

---

## Documentation

https://docs.vironlab.eu/vextension-v2.0.0/

---

### Discord

<div align="center">
    <h1 style="color:#154444">Other Links:</h1>
    <a style="color:#00ff00" target="_blank" href="https://discord.gg/wvcX92VyEH"><img src="https://img.shields.io/discord/785956343407181824?label=vironlab.eu%20Discord&logo=Discord&logoColor=%23ffffff&style=flat-square"></img></a>
</div>
