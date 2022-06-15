# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),

## [1.0.0-beta1]

### Added

- Make DDEV binary configurable and refactor command execution to use system environment
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/66
- Add sentry.io error reporting (#37) by @SpraxDev in https://github.com/php-perfect/ddev-intellij-plugin/pull/60
- Add check if docker is running on startup in https://github.com/php-perfect/ddev-intellij-plugin/pull/68
- Add PHP Interpreter automatically for composer
- Add notification if track of the DDEV status is lost
- Add Got It Tutorial for the integrated DDEV terminal
- Add validation for plugins that are required for PHP interpreter registration
- Reload plugin on configuration change. A restart of the IDE is no longer required.

### Changed

- Parse DDEV JSON output per line in https://github.com/php-perfect/ddev-intellij-plugin/pull/61
- Enable Start / Stop / Restart buttons when in DDEV status is unknown to restore the status
- Enable dynamic loading of the plugin -> No restart needed when enabling / disabling the plugin
- Various updates of the dependencies

### Fixed

- Fix error when DDEV status was not parsable by the plugin
- Fix error when the output of DDEV contained multiple JSON objects
- Fix timeout when using zsh with enabled git plugin in WSL as default shell
- Fix wrong logging channel for some classes
- Fix error when opening multiple projects with running DDEV container at the same time
- Minor wording/typo fixes for DdevIntegrationBundle.properties, for #48 by @rfay
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/51
- Various optimizations and fixes

### New Contributors

- @dependabot made their first contribution in https://github.com/php-perfect/ddev-intellij-plugin/pull/46
- @rfay made their first contribution in https://github.com/php-perfect/ddev-intellij-plugin/pull/51
- @SpraxDev made their first contribution in https://github.com/php-perfect/ddev-intellij-plugin/pull/60

**Full Changelog**: https://github.com/php-perfect/ddev-intellij-plugin/compare/1.0.0-alpha3...1.0.0-beta

## [1.0.0-beta]

Broken because of an error in the release pipeline.

## [1.0.0-alpha3]

### Added

- Ddev PHP Interpreter will automatically be set as default interpreter for the project if none is set yet
- Added tests for ServiceActionManager by @kevin0x90
  in [#29](https://github.com/php-perfect/ddev-intellij-plugin/pull/29)
- Added first interactive tutorial element for the ddev status
  bar [#5](https://github.com/php-perfect/ddev-intellij-plugin/issues/5)

### Changed

- Improved error handling and extended timeouts for ddev background processes to
  address [#23](https://github.com/php-perfect/ddev-intellij-plugin/issues/23)
- General optimizations

### Fixed

- Fixed warning in
  WSL `your 131072x1 screen size is bogus` [#30](https://github.com/php-perfect/ddev-intellij-plugin/issues/30)
- Fixed [#25](https://github.com/php-perfect/ddev-intellij-plugin/issues/25) by adding a notification if a soft
  dependency is missing
- Fixed [#24](https://github.com/php-perfect/ddev-intellij-plugin/issues/24) by connecting to database via 127.0.0.1
  instead of localhost

### New Contributors

* @kevin0x90 made their first contribution in https://github.com/php-perfect/ddev-intellij-plugin/pull/29

### Special Thanks

Thanks to our alpha testers of the alpha2 pre release!

Your feedback made this release possible:
[@marcvangend](https://github.com/marcvangend), [@calien666](https://github.com/calien666)
, [@cmuench](https://github.com/cmuench), [@kgaut](https://github.com/kgaut) and others

## [1.0.0-alpha2]

### Added

- Assistance and completion for DDEV configuration file with included

### Changed

- General Refactorings
- Supported IntelliJ Versions set 2022.1

### Fixed

- Auto Configuration for PHP Remote Interpreter works much better now
- Lots of Bug fixes

## [1.0.0-alpha1]

### Added

- Run DDEV actions via GUI
- Quick access DDEV Services
- Auto configure PHP Remote Interpreter
- Auto configure data source
- Assistance and completion for DDEV configuration file
- DDEV version check
- Integrated DDEV terminal
