<p align="center">
    <img alt="The DDEV Integration Logo" height="128" src="./src/main/resources/META-INF/pluginIcon.svg" title="DDEV Integration Logo" width="128"/>
</p>

[![Java CI with Gradle](https://github.com/php-perfect/ddev-intellij-plugin/actions/workflows/gradle.yml/badge.svg "Build")](https://github.com/php-perfect/ddev-intellij-plugin/actions/workflows/gradle.yml)
[![License](https://img.shields.io/badge/License-BSD_3--Clause-blue.svg "BSD 3")](https://opensource.org/licenses/BSD-3-Clause)

[comment]: <> (Maybe add another badge or link here to quickly navigate to the plugin store?)

# DDEV Integration for IntelliJ IDEA

This plugin implements [DDEV](https://github.com/drud/ddev) integration in IntelliJ IDEA.

## Features

The goal of this plugin is to integrate all common DDEV tasks into the GUI of IntelliJ IDEA, so that no CLI commands are
needed. In addition, this plugin automates some configurations for easier use of DDEV in your project.

* Run DDEV actions via GUI
* Quick access DDEV Services
* Auto configure PHP Remote Interpreter<sup>1</sup>
* Auto configure data source<sup>1</sup>
* Assistance and completion for DDEV configuration file
* DDEV version check
* Integrated DDEV terminal<sup>1</sup>
* And much more coming soon!

<sup>1</sup> Features are only available in IDEs supporting these, like PHPStorm and IntelliJ Ultimate.

## Installation

You can install the Plugin from within your IDE. Go to `Settings > Plugins > Marketplace` and search for "DDEV
Integration". Click install and Restart your IDE.

Please note: You will need to install DDEV manually. You can find the installation
instructions [here](https://ddev.readthedocs.io/en/stable/).

## Bug Reports

In case you encounter any issues, please make sure they are related to this plugin and not DDEV itself. For plugin
related issues please [open an issue](https://github.com/php-perfect/ddev-intellij-plugin/issues/new) in this repository
with the following information:

* Your IDE (PHPStorm, WebStorm, ...)
* Your OS (In case of Windows: WSL?)
* How can we reproduce this issue

DDEV related bugs can be reported [here](https://github.com/drud/ddev/issues).

## Contributing

In case you have an idea for a new feature please open an issue and tell us. Pull request are welcome, but only for
features that were discussed in an issue before.

In case you would like to optimize an existing feature, without changing its behavior, or you like to provide additional
test cases you can file a pull request directly.

### Development

Clone this repository to your computer

```
$ git clone https://github.com/php-perfect/ddev-intellij-plugin.git
```

Open IntelliJ IDEA, select `File > New > Project from existing sources`, point to the directory where the DDEV
Integration plugin repository is and then import it as sbt project.

In the next step, select JDK 11 as project JDK (create it from an installed JDK if necessary).

Select the runIde run configuration and select the Run or Debug button to build and start a development version of IDEA
with the DDEV Integration plugin.

Read the [IntelliJ Platform SDK documentation](https://plugins.jetbrains.com/docs/intellij/welcome.html).
