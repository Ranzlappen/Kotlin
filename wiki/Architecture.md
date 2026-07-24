# Architecture

Companion to [`CLAUDE.md`](https://github.com/Ranzlappen/Kotlin/blob/main/CLAUDE.md);
when the two disagree, **CLAUDE.md wins**.

## At a glance

```
app                       ← shell: Hilt app, MainActivity, adaptive nav, NavHost
├── core/model            ← pure Kotlin JVM: UserPreferences, FeedbackReport (+ tests)
├── core/common           ← Hilt dispatcher/scope qualifiers
├── core/designsystem     ← TemplateTheme, tokens, shared components
├── core/data             ← Preferences DataStore repository (+ tests)
├── feature/home          ← landing screen
├── feature/settings      ← theme, dynamic color, language, about (+ VM tests)
├── feature/feedback      ← bug report / feature request flow (+ tests)
└── feature/manual        ← in-app user manual
```

## Rules that shape the graph

- Features never depend on other features; shared things live in `core/*`.
- `core:model` has **no Android imports** — it is the KMP-portable,
  JVM-unit-tested domain vocabulary.
- ViewModels expose `StateFlow`; Compose collects with
  `collectAsStateWithLifecycle`. UI events flow back as plain lambdas.
- Each feature owns its `navigation/` file exposing a route constant, a
  `navigateToX()` extension, and a `NavGraphBuilder.xScreen()` builder; the
  app module composes them into one NavHost.

## Cross-cutting concerns

- **Persistence**: single Preferences DataStore (`core:data`), repository
  interface + Hilt binding, so features and tests never see DataStore
  directly.
- **Theming**: `TemplateTheme(darkTheme, dynamicColor)`; resolved in
  `MainActivity` from the persisted `UserPreferences`.
- **i18n**: resource strings only; `values/` (EN) + `values-de/`;
  per-app language via AppCompatDelegate + `locales_config.xml`.
- **Error surfaces**: user-visible failures use localized toasts/text; no
  silent catches around user actions.

## Deployment shape

Git-tag-driven versions. CI builds and tests every PR/push; the manual
`release.yml` workflow produces debug APK + signed release APK + signed
AAB and publishes a GitHub Release. The `site/` directory deploys to
GitHub Pages as an installable PWA.

## Why these choices

1. **Multi-module now, not later** — cheap while the app is small,
   impossible to retrofit cleanly once it isn't (now-in-android lesson).
2. **Plain per-module Gradle files instead of convention plugins** — AGP
   9's built-in Kotlin is new; simple explicit build files are more robust
   for a template whose consumers will edit them. Revisit when the module
   count grows past ~15 (decision recorded 2026-07-24).
3. **No network stack by default** — my apps are privacy-first
   (GlyphBoard precedent); adding networking is a conscious product step.
4. **Tags as version source of truth** — no version-bump commits, no
   drift between manifest and release.

---
*Last reviewed: 2026-07-24 · Source paths: `settings.gradle.kts`, `app/`, `core/`, `feature/` · Related: `CLAUDE.md`, `LASTENHEFT.md`*
