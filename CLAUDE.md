# Kotlin Android Template

State-of-the-art Android template repo (multi-module MVVM, Compose,
Material 3, Hilt) in the spirit of now-in-android, cortinico's
kotlin-android-template, and MVVMTemplate — plus the Ranzlappen house rules
from [repo-standards](https://github.com/Ranzlappen/repo-standards) (v3.2.0).

## Read this first

1. **[LASTENHEFT.md](LASTENHEFT.md)** — the binding requirements spec.
   If this repo (or a repo created from this template) is being set up,
   extended, or "finished", you MUST run its **Final Pass** as your last
   work phase and report item-by-item. This is not optional.
2. **[REPO_SETUP_CHECKLIST.md](REPO_SETUP_CHECKLIST.md)** — the human
   admin's manual settings list. You can't do these; remind the admin of
   open items when you finish the Final Pass.
3. When used as a template: run `scripts/customizer.sh <package> <AppName>
   [repo]` first — it rewrites package/app/repo references and regenerates
   the fallback keystore.

## Architecture

- `app` — shell: Hilt app, `MainActivity` (splash, edge-to-edge, theme
  from DataStore), `NavigationSuiteScaffold` (bottom bar ↔ rail, adaptive),
  NavHost wiring the features.
- `core/model` — **pure Kotlin JVM** (no Android imports, KMP-portable):
  `UserPreferences`, `FeedbackReport` + Markdown renderer.
- `core/common` — Hilt dispatchers/scope qualifiers.
- `core/designsystem` — `TemplateTheme` (dynamic color, dark/light),
  typography, shapes, `LocalSpacing` tokens, shared components
  (`TemplateCard`, `SectionHeader`, `ScreenContainer`).
- `core/data` — Preferences DataStore repository behind an interface.
- `feature/home|settings|feedback|manual` — one screen each, MVVM
  (`StateFlow` + `collectAsStateWithLifecycle`), own `navigation/` file,
  own localized resources.

## Hard rules

- **No feature→feature dependencies** — features talk through `core/*`.
- **No new permissions** without the LASTENHEFT.md A3 justification. The
  manifest currently declares none; keep it that way.
- **Every string localized** (`values/` + `values-de/` + any added locale);
  `HardcodedText`/`MissingTranslation` are lint errors.
- **`core/model` stays Android-free.**
- **Versions only in `gradle/libs.versions.toml`.**
- Use the design system, not raw Material defaults or ad-hoc dp values.
- Manual content changes touch all three surfaces in one PR: `feature/manual`
  strings, `wiki/User-Manual.md`, `site/manual/index.html`.
- Wiki source lives in `wiki/` in this repo; update it in the same PR as
  the code change it documents.

## Build & development

```
./gradlew assembleDebug            # Debug APK (fallback-signed)
./gradlew assembleRelease          # Release APK (secrets or fallback keystore)
./gradlew bundleRelease            # Release AAB
./gradlew testDebugUnitTest        # JVM unit tests
./gradlew lintDebug                # Android Lint (strict app/lint.xml)
./gradlew spotlessApply            # Format (ktlint via Spotless)
./gradlew detekt                   # Static analysis
```

**There is no Android SDK in the usual agent environments — CI is the
compile gate.** CI also runs on `claude/**` branch pushes, so push your
branch to get a full build+test verdict before opening a PR.

## CI/CD

| Workflow | Trigger | Does |
| --- | --- | --- |
| `ci-android.yml` | PR + push to `main`/`claude/**` + dispatch | spotless, detekt, lint, unit tests, debug APK, release AAB; uploads artifacts |
| `release.yml` | **manual dispatch** (patch/minor/major) or tag `v*` | tests, then debug APK + signed release APK + signed AAB → git tag → GitHub Release. Tags are the version source of truth |
| `security-scan.yml` | PR, push, weekly, dispatch | CodeQL (java-kotlin, manual build), gitleaks, OpenSSF Scorecard |
| `dependency-review.yml` | PR | blocks high/critical CVE introductions (soft until Dependency graph enabled) |
| `pages.yml` | push to `main` touching `site/**` | deploys the PWA site to GitHub Pages |
| `stale.yml` | daily cron | disabled unless repo var `STALE_ENABLED=true` |

Signing secrets (optional; fallback keystore otherwise): `KEYSTORE_BASE64`,
`KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD` — see `signing/README.md`.

## Tech stack

Kotlin 2.4 · AGP 9.3 (built-in Kotlin — **no** standalone kotlin-android
plugin) · Gradle 9.6.1 wrapper · JDK 17 · compileSdk 37 / minSdk 26 ·
Compose BOM 2026.06.01 + Material 3 · Navigation Compose · Hilt + KSP ·
Preferences DataStore · JUnit4 + coroutines-test + Turbine ·
Spotless(ktlint) + detekt.

## Post-task self-check

After each turn that changes code or workflows: new dependency → catalog,
not inline; new permission → almost certainly wrong, stop; new string →
all locales; behavior change → README/manual/wiki/site in sync; new
convention → this file. If nothing applies, say "no doc/workflow updates
needed."
