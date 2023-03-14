# Lunar-Agent
A java agent for improving LunarClient experience.
<strong>Only works for version `1.8.9-b4db1c9/master`).</strong>
<br>
<br>
<strong>Note: I might re-write this in [Weave-MC](https://github.com/Weave-MC/), a mod loader for newer versions of LunarClient.</strong>

<br>

## Features

### Hypixel Bedwars Stats Checker
![](https://github.com/Monmcgt/Lunar-Agent/blob/master/assets/bedwars-stats.gif?raw=true)
#### Specific: `/lunaragent bedwars specific <player>`
#### Who(/who): `/lunaragent bedwars who`
#### Party: `/lunaragent bedwars party`

<br>

### AutoQueue/AutoDodge <strong>(Bannable!)</strong>
#### Commands: `/lunaragent bedwars autoqueue`

<br>

### ESP and Tracers
![](https://github.com/Monmcgt/Lunar-Agent/blob/master/assets/ESP-Tracers.png?raw=true)
The image above is only for showcasing the modules!
#### ESP: `/lunaragent modules esp`
#### Tracers: `/lunaragent modules tracers`

<br>

### Commands' Aliases
Take a look at `@me.monmcgt.code.commands.CommandInfo` in those files that extend `me.monmcgt.code.commands.CommandAbstract` (e.g. <a href="https://github.com/Monmcgt/Lunar-Agent/blob/cc9ba6aaf0e052e3510540d990f710ff23a57af5/src/main/java/me/monmcgt/code/commands/impl/overlay/BedwarsOverlay%24Command.kt#L17">BedwarsOverlay$Command.kt</a>
#### For Example: `/lunaragent bedwars` can be shortened to `/la bw`

<br>

### Developing API and More
<ul>
<li>Hooks</li>
<li>Render Utilities</li>
<li>Hypixel APIs Wrapper</li>
<li>ClassLoader</li>
<li>Easy-to-understand Command System</li>
<li>MCP <-> Lunar Mappings Converter</li>
<li><a href="https://github.com/Monmcgt/Lunar-Agent/tree/master/src/main/java/me/monmcgt/code">...</a></li>
</ul>
