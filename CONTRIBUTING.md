# Contributing Guidelines

If you have ideas for improvements or new features,
please [open a discussion](https://github.com/php-perfect/ddev-intellij-plugin/discussions/new?category=ideas) and share
them with us. Pull requests are
welcome, but please open an issue for the problem you try to solve first.

If you want to optimize an existing feature without changing its behavior or provide additional test cases, you can
submit a [pull request](https://github.com/php-perfect/ddev-intellij-plugin/pulls) directly.

## Issues

In case you encounter any issues, please make sure they are related to this integration plugin and
not [DDEV](https://github.com/drud/ddev) itself.

DDEV related issues can be reported [here](https://github.com/drud/ddev/issues).

For plugin related issues please [open an issue](https://github.com/php-perfect/ddev-intellij-plugin/issues/new) in this
repository with at least the following information:

- Your IDE (PHPStorm, WebStorm, ...)
- Your OS (In case of Windows, are you using the WSL?)
- How can we reproduce the issue
- In case of an error, please submit the error report and provide the report ID in your issue

## Submitting an Error Report

If a critical error occurs in the plugin, this will trigger an error in your IDE.
You will be alerted by a flashing red exclamation mark in the lower right corner of your IDE, usually in combination
with an error message.
Clicking on it will open a dialog where you can file a bug report. Only bugs that are directly related to the code
of this plugin will be sent to us.

When you file an error report, you will be given an error report ID to refer to.

These error reports help us a lot to find the cause of the error and provide you with better support.

The error report will contain information about your operating system, your ddev, docker and IDE versions as well as
the error information and the message shown in the report dialog.

## Testing of Pre-Releases

You can help us by testing pre-release versions.

To install pre-release versions, add the desired update channel in your IDE as an additional plugin repository.
Alternatively, you can download them
from [Jetbrains Marketplace](https://plugins.jetbrains.com/plugin/18813-ddev-integration) and install them from disk.

The following repositories can be added:

- `alpha`: https://plugins.jetbrains.com/plugins/alpha/list
- `beta`: https://plugins.jetbrains.com/plugins/beta/list
- `eap`: https://plugins.jetbrains.com/plugins/eap/list

Please note: Adding one of these channels may cause other plugins to install pre-release versions as well.

## Development

If you would like to contribute, you can follow the steps below to get a development-ready setup.

- Open IntelliJ IDEA, select `File > New > Project from Version Control` and check out this repository.
- Select JDK 11 as project JDK (create it from an installed JDK if necessary).
- Run the `runIde`  to build and start a development version of IDEA with the DDEV Integration plugin.
- Read the [IntelliJ Platform SDK documentation](https://plugins.jetbrains.com/docs/intellij/welcome.html).

If you do not have an IntelliJ Ultimate license, you can use any other installed IntelliJ-based IDE for development.

Add the path to your local installation in the [build.gradle.kts](build.gradle.kts) file to use a local installation as
the development target.

```kotlin
tasks {
    runIde {
        ideDir.set(File("C:\\Users\\nl\\AppData\\Local\\JetBrains\\Toolbox\\apps\\PhpStorm\\ch-0\\213.7172.28"))
    }
}
```
