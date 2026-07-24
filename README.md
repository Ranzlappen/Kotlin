# Kotlin Android Template

[![CI](https://github.com/Ranzlappen/Kotlin/actions/workflows/ci-android.yml/badge.svg)](https://github.com/Ranzlappen/Kotlin/actions/workflows/ci-android.yml)
[![Release](https://img.shields.io/github/v/release/Ranzlappen/Kotlin?include_prereleases)](https://github.com/Ranzlappen/Kotlin/releases)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Standards](https://img.shields.io/badge/repo--standards-v3-blue)](https://github.com/Ranzlappen/repo-standards)
[![Ko-fi](https://img.shields.io/badge/Ko--fi-support-ff5e5b)](https://ko-fi.com/F1F1140LWT)

A personal, state-of-the-art **Android template repository** — the best of
[now-in-android](https://github.com/android/nowinandroid),
[cortinico/kotlin-android-template](https://github.com/cortinico/kotlin-android-template),
and [Drjacky/MVVMTemplate](https://github.com/Drjacky/MVVMTemplate), fused
with the [Ranzlappen/repo-standards](https://github.com/Ranzlappen/repo-standards)
house rules — **plus a twist**: a personalized
**[Lastenheft](LASTENHEFT.md)** (requirements specification) baked into the
repo. It reminds *me* what every app must have, and it binds *AI agents* to
run a final requirements pass whenever this template spawns a new repo.

## What you get

**A runnable app** (multi-module MVVM, Jetpack Compose, Material 3):

- 🏗️ `app` + `core/{model,common,designsystem,data}` +
  `feature/{home,settings,feedback,manual}` — no feature→feature deps
- 🎨 Design system: theme w/ dynamic color, dark/light/system (persisted),
  spacing tokens, shared components, splash, edge-to-edge, adaptive icon
- 📱 Responsiveness: adaptive navigation (bar ↔ rail), content max-width,
  state-preserving rotation
- 🌍 Full localization (EN + DE), in-app per-app language picker, lint
  errors on hardcoded/untranslated strings
- 🐛 In-app **bug report / feature request** flow (ported from HardwareDash
  legacy): device snapshot → Markdown → prefilled GitHub issue / email /
  clipboard
- 📖 In-app **user manual**, mirrored in the wiki and on the Pages site
- 🔒 Zero permissions, zero network, zero trackers by default
- ✅ Unit tests (JUnit4, coroutines-test, Turbine) for every pure layer

**A complete repo shell:**

- ⚙️ CI (spotless + detekt + lint + tests + builds), **manual release
  workflow** (debug APK + signed release APK + signed AAB from git-tag
  versions), CodeQL/gitleaks/Scorecard, dependency review, Dependabot
- 🌐 GitHub **Pages PWA** site under `site/` (manifest + service worker +
  custom-subdomain DNS recipe)
- 📋 Community files: issue forms, PR template, CODEOWNERS, CoC,
  CONTRIBUTING, SECURITY, FUNDING (Ko-fi)
- 📚 Wiki seed under `wiki/`
- 📜 **[LASTENHEFT.md](LASTENHEFT.md)** — requirements spec + AI final-pass
  contract
- ☑️ **[REPO_SETUP_CHECKLIST.md](REPO_SETUP_CHECKLIST.md)** — every manual
  admin setting (branch auto-delete, Pages source, DNS, Discord webhook,
  auto-merge, badges, …)

## Using the template

1. **Create the repo** on GitHub: *Use this template* → new repository.
2. **Customize:**
   ```bash
   scripts/customizer.sh io.github.ranzlappen.myapp MyApp my-app
   ```
   (rewrites package/app/repo references, regenerates the fallback
   keystore).
3. **Admin pass:** work through [REPO_SETUP_CHECKLIST.md](REPO_SETUP_CHECKLIST.md).
4. **AI pass:** point your agent at the repo. [CLAUDE.md](CLAUDE.md)
   routes it to [LASTENHEFT.md](LASTENHEFT.md), whose **Final Pass** it
   must run and report before calling the setup done.

## Building

```bash
./gradlew assembleDebug          # debug APK
./gradlew testDebugUnitTest      # unit tests
./gradlew spotlessApply detekt   # format + static analysis
./gradlew lintDebug              # Android Lint (strict)
```

Requires JDK 17. Kotlin 2.3, AGP 9.2, Gradle 9.5.1, compileSdk 37,
minSdk 26. Releases: Actions → **Release** → run with patch/minor/major.
Signing secrets are optional — see [signing/README.md](signing/README.md).

## Support

Free and MIT-licensed. If it saves you time:
[ko-fi.com/F1F1140LWT](https://ko-fi.com/F1F1140LWT) — a plain link by
design; nothing here loads remote resources.
