# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),

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

## New Contributors

* @kevin0x90 made their first contribution in https://github.com/php-perfect/ddev-intellij-plugin/pull/29

## Special Thanks

Thanks to our alpha testers of the alpha2 pre release!

Your feedback made this release possible:
@marcvangend, @calien666, @cmuench, @kgaut and others

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
