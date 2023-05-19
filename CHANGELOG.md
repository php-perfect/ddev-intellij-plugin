# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),

## [1.1.0]

### Added

- Auto Configuration for NodeJS Remote Interpreter by @nico-loeber
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/177
- Add craftcms as valid project type to the DDEV configuration schema

### Fixed

- Do not replace unchanged data source configuration by @nico-loeber
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/180

## Dependency Updates

- Bump junitVersion from 5.9.2 to 5.9.3 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/173
- Bump org.mockito:mockito-core from 5.2.0 to 5.3.1 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/172
- Bump io.sentry:sentry from 6.17.0 to 6.18.1 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/176
- Bump org.junit.platform:junit-platform-launcher from 1.9.2 to 1.9.3 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/174
- Bump io.sentry:sentry from 6.18.1 to 6.19.0 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/179

**Full Changelog**: https://github.com/php-perfect/ddev-intellij-plugin/compare/1.0.5...1.1.0

## [1.0.5]

### Fixed

- Compatibility of the DDEV status bar for IntelliJ 2023.1
- Compatibility of the DDEV terminal for IntelliJ 2023.1
- Compatibility of actions for IntelliJ 2023.1

### Dependency Updates

- Update gradle wrapper to 8.0
- Bump mockito-core from 4.9.0 to 4.10.0 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/137
- Bump mockito-core from 4.10.0 to 4.11.0 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/140
- Bump sentry from 6.9.2 to 6.11.0 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/142
- Bump gson from 2.10 to 2.10.1 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/145
- Bump org.jetbrains.intellij from 1.10.1 to 1.12.0 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/149
- Bump junit-platform-launcher from 1.9.1 to 1.9.2 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/147
- Bump org.mockito:mockito-core from 4.11.0 to 5.1.1 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/154
- Bump junitVersion from 5.9.1 to 5.9.2 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/146
- Bump assertj-core from 3.23.1 to 3.24.2 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/150
- Bump org.jetbrains.intellij from 1.12.0 to 1.13.2 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/165
- Bump org.mockito:mockito-core from 5.1.1 to 5.2.0 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/164
- Bump org.sonarqube from 3.5.0.2730 to 4.0.0.2929 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/160
- Bump io.sentry:sentry from 6.11.0 to 6.17.0 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/169
- Bump org.jetbrains.intellij from 1.13.2 to 1.13.3 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/168

**Full Changelog**: https://github.com/php-perfect/ddev-intellij-plugin/compare/1.0.4...1.0.5

## [1.0.4]

### Fixed

- Configure DataSource TreePattern to index all schemas by @nico-loeber
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/135
- Fix failing unit tests by adding `junit-platform-launcher` as dependency by @SpraxDev
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/131
- GitHub Actions: Upload test reports even when tests fail by @SpraxDev
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/132

### Dependency Updates

- Bump sentry from 6.5.0 to 6.6.0 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/118
- Bump sentry from 6.6.0 to 6.9.1 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/130
- Bump mockito-core from 4.8.1 to 4.9.0 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/125
- Bump org.sonarqube from 3.4.0.2513 to 3.5.0.2730 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/119
- Bump gson from 2.9.1 to 2.10 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/117
- Bump org.jetbrains.changelog from 1.3.1 to 2.0.0 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/120
- Bump org.jetbrains.intellij from 1.10.0 to 1.10.1 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/134
- Bump sentry from 6.9.1 to 6.9.2 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/133

**Full Changelog**: https://github.com/php-perfect/ddev-intellij-plugin/compare/1.0.3...v1.0.4

## [1.0.3]

### Fixed

* Fix mariadb specific dsn type by @nico-loeber in https://github.com/php-perfect/ddev-intellij-plugin/pull/107
* Create docker remote server if it does not exist by @nico-loeber
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/102
* Re-enable test coverage report by @SpraxDev in https://github.com/php-perfect/ddev-intellij-plugin/pull/88

### Dependency Updates

* Bump gson from 2.9.0 to 2.9.1 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/90
* Bump org.jetbrains.intellij from 1.7.0 to 1.8.0 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/93
* Bump sentry from 6.3.0 to 6.3.1 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/91
* Bump org.jetbrains.intellij from 1.8.0 to 1.8.1 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/99
* Bump sentry from 6.3.1 to 6.4.0 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/96
* Bump mockito-core from 4.6.1 to 4.7.0 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/94
* Bump sentry from 6.4.0 to 6.4.1 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/101
* Bump org.jetbrains.intellij from 1.8.1 to 1.9.0 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/103
* Bump mockito-core from 4.7.0 to 4.8.0 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/105
* Bump sentry from 6.4.1 to 6.4.3 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/111
* Bump junitVersion from 5.9.0 to 5.9.1 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/109
* Bump sentry from 6.4.3 to 6.5.0 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/112
* Bump mockito-core from 4.8.0 to 4.8.1 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/116

**Full Changelog**: https://github.com/php-perfect/ddev-intellij-plugin/compare/1.0.1...v1.0.2

## [1.0.2]

## [1.0.1]

### Changed

- Support for IntelliJ 2022.2 (No more support for 2022.1)
- Increase ddev command timeout and add path to error message
- Bump org.jetbrains.intellij from 1.6.0 to 1.7.0 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/84
- Bump sentry from 6.1.4 to 6.3.0 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/87
- Bump junitVersion from 5.8.2 to 5.9.0 by @dependabot in https://github.com/php-perfect/ddev-intellij-plugin/pull/89

### Fixed

- Add missing checkout step for sentry release job
- Fix possible null pointer in Description::getServices

**Full Changelog**: https://github.com/php-perfect/ddev-intellij-plugin/compare/1.0.0...v1.0.1

## [1.0.0]

### Added

- Add deployment configuration for the ddev mount by @nico-loeber
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/57
- Add option to disable version check in update notification

### Changed

- Refactor dynamic ddev config arguments for better extensibility
- Update DdevNotifier and Tests for better maintainability
- Bump sentry from 5.7.4 to 6.1.4 by @dependabot
- Bump org.sonarqube from 3.3 to 3.4.0.2513 by @dependabot
  in https://github.com/php-perfect/ddev-intellij-plugin/pull/71
- Remove dead code and do some tidy up

### Fixed

- Fix changelog format so the changelog is shown correctly in JetBrains Marketplace
- Remove unsupported JVM option MaxPermSize
- Do not record uncaught exceptions in sentry.io

**Please note**: There is a known issue
where [DDEV commands fail when the project is opened in PHPStorm](https://github.com/php-perfect/ddev-intellij-plugin/issues/80)
.

**Full Changelog**: https://github.com/php-perfect/ddev-intellij-plugin/compare/1.0.0-beta1...v1.0.0

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
