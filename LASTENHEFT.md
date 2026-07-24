# LASTENHEFT — Requirements Specification

> **Who this is for.** Two audiences: **me** (Ranzlappen — as a persistent
> reminder of what every one of my Android apps must eventually have) and
> **AI agents** (as a binding requirements contract). It is personal to my
> projects; it encodes decisions already made across HardwareDash,
> GlyphBoard, and Pageside. Do not relitigate them — apply them.

> **When this applies.** Always — but especially **when this repo is used
> as a template** for a new app. Any AI agent that scaffolds, extends, or
> "finishes setting up" a repo created from this template MUST run the
> [Final Pass](#final-pass-for-ai-agents) below as the last step of the
> setup, and MUST treat every requirement in this file as in-scope work it
> either implements, verifies as already present, or explicitly reports as
> deferred (with a filed issue). Silence about a requirement is a defect.

---

## A. Product requirements (every app)

### A1. Feedback: bug reports & feature requests — in-app

Ported from the HardwareDash legacy branch and already implemented in
`feature/feedback`:

- An in-app screen where the user writes a bug report or feature request.
- A coarse, non-identifying device snapshot (app version, device model,
  Android version, screen, locale — **never** logs, never identifiers) that
  the user can toggle off.
- The report renders to Markdown and submits via **prefilled GitHub issue
  URL**, **mailto:** (`info@ranzlappen.com`), or **clipboard** — the user
  always sees the full content before anything leaves the device.
- GitHub issue templates (`.github/ISSUE_TEMPLATE/`) mirror the same
  structure so web-filed and app-filed reports look alike.

### A2. Full localization support

- **Every** user-facing string lives in resources; `HardcodedText` and
  `MissingTranslation` are lint **errors**, not warnings.
- English is the source language; German ships from day one. Adding a
  locale = new `values-<tag>/` folders + entry in
  `res/xml/locales_config.xml` + entry in `AppLanguage`.
- Per-app language selection works on **all** supported API levels
  (AppCompatDelegate + `autoStoreLocales` service; system per-app settings
  on Android 13+ via `localeConfig`).
- The in-app manual, wiki, and Pages site are translated to the same set of
  languages, or explicitly documented as English-only.

### A3. Exact permission management (GlyphBoard discipline)

- The manifest declares **zero permissions by default**. Every permission
  added later must be: (1) required by a shipped feature, (2) requested in
  context at time of use, (3) degradable — the app works without it, and
  (4) documented in the manual's privacy chapter and the README.
- No `INTERNET` permission unless the product is explicitly a networked
  app. Feedback, support links, and updates go through external apps
  (browser/email) instead.
- If permissions exist, the feedback screen's environment table reports
  their grant state (like HardwareDash legacy did).

### A4. Support / Ko-fi (Pageside discipline)

- `.github/FUNDING.yml` with `ko_fi: F1F1140LWT` (already present).
- A **plain text** "☕ Support this project" link (Settings → About and the
  Pages site footer) — never the hosted Ko-fi button image; the app and
  site load no remote resources.

### A5. User manual — consistent across three surfaces

The same manual content exists in:
1. **In-app**: `feature/manual` (localized, offline).
2. **Wiki**: `wiki/User-Manual.md` (mirrored to the GitHub Wiki).
3. **Pages site**: `site/manual/index.html`.

A change to app behavior is not done until all three are updated in the
same PR. Chapter structure stays identical across surfaces.

### A6. Privacy baseline

- No analytics, no trackers, no logging of user content — ever.
- Anything leaving the device goes through an app the user chooses, with
  the content visible first.
- Backup rules (`backup_rules.xml` / `data_extraction_rules.xml`) exist and
  only include harmless data.

### A7. Modern design pass

- Material 3, dynamic color (user-toggleable), dark/light/system theme
  choice persisted via DataStore.
- All UI goes through the design system (`core:designsystem`): theme,
  spacing tokens (`LocalSpacing`), shared components. No raw `dp` rhythm
  values, no ad-hoc colors.
- Splash screen (androidx SplashScreen), edge-to-edge, adaptive launcher
  icon with monochrome layer.

### A8. Complete responsiveness pass

- Adaptive navigation: bottom bar on phones, rail on tablets/foldables
  (`NavigationSuiteScaffold` — already wired).
- Content max-width constraint on large screens (`ScreenContainer`).
- Every new screen is checked in portrait, landscape, and a tablet/desktop
  window before it ships. Rotation must not lose state
  (`rememberSaveable` / ViewModel).

### A9. Accessibility

- Touch targets ≥ 48dp, content descriptions on informative icons
  (lint-enforced), headings marked with `semantics { heading() }`,
  meaningful TalkBack order.

## B. Engineering requirements

### B1. Architecture

- Multi-module: `app` + `core/*` + `feature/*`. **No feature→feature
  dependencies** — features communicate through `core/*` (HardwareDash
  rule).
- MVVM + unidirectional data flow: ViewModel exposes `StateFlow`, Compose
  collects with `collectAsStateWithLifecycle`.
- Hilt for DI; pure-Kotlin modules (`core:model`) stay free of Android
  imports (KMP-portable, unit-tested on the JVM).
- Versions live **only** in `gradle/libs.versions.toml`.

### B2. Quality gates (all enforced in CI)

- Spotless/ktlint formatting, detekt static analysis, Android Lint (with
  the strict `app/lint.xml`), JVM unit tests.
- New logic ships with unit tests. Pure functions (report builders, codecs,
  mappers) are always tested.

### B3. Releases

- **Manual release workflow** (`release.yml`): dispatch with
  patch/minor/major bump (or explicit `v*` tag) → builds **debug APK,
  signed release APK, signed AAB** → tags → GitHub Release. Git tags are
  the single source of truth for versions.
- Fallback keystore policy per `signing/README.md`: committed public
  keystore until real `KEYSTORE_*` secrets exist; never mix the two after.

### B4. Repo hygiene (repo-standards v3)

- Conventional Commits; small focused PRs; wiki source mirrored in `wiki/`;
  community files present (`CODE_OF_CONDUCT`, `CONTRIBUTING`, `SECURITY`,
  issue forms, PR template, CODEOWNERS, FUNDING); Dependabot weekly with
  grouped minor+patch; security scanning (CodeQL + gitleaks + Scorecard);
  per-PR dependency review; `.standards-version` tracked.

---

## Final Pass (for AI agents)

**Trigger:** a new repo has just been created from this template (or an
existing one is being brought in line). After the primary setup work —
customizer run, initial feature scaffolding, whatever was asked — execute
this checklist as a distinct, final work phase and report each item as
✅ done / ☑️ verified already present / ⏭️ deferred (with an issue filed).

1. **Identity** — `scripts/customizer.sh` was run: package, app name, repo
   references, and fallback keystore are no longer the template's. No
   `io.github.ranzlappen.template` or `Ranzlappen/Kotlin` references remain
   (outside docs that explain the template's origin).
2. **Feedback flow** — `feature/feedback` points at the *new* repo's issues
   URL and the correct contact email; issue templates match the report
   format.
3. **Localization** — all strings of any newly added features exist in
   every shipped locale; lint passes with `MissingTranslation` as error.
4. **Permissions** — manifest still declares exactly the permissions the
   product needs (default: none). Any addition is documented per A3.
5. **Support links** — FUNDING.yml, Settings → About link, and site footer
   point at the right Ko-fi profile.
6. **Manual sync** — in-app manual, `wiki/User-Manual.md`, and
   `site/manual/` describe the *actual* app, not the template. All three
   updated together.
7. **Design & responsiveness** — new screens use the design system and
   `ScreenContainer`; spot-check phone + tablet layouts (A7/A8).
8. **CI green** — `spotlessCheck detekt lintDebug testDebugUnitTest
   assembleDebug bundleRelease` all pass on CI.
9. **Release dry run** — the release workflow is dispatchable; signing
   secrets configured or consciously deferred to the fallback keystore.
10. **Docs** — README badges/URLs updated; CLAUDE.md reflects the real app;
    `pwa`/site URLs match the chosen subdomain.
11. **Admin checklist handoff** — remind the human that
    [REPO_SETUP_CHECKLIST.md](REPO_SETUP_CHECKLIST.md) contains the manual
    repo-settings steps an agent cannot do (branch protection, Pages
    source, DNS, webhooks, auto-merge, …), and list which of those are
    still open if detectable via API.

Then post (as PR comment or final response) a short **Final Pass report**
listing every numbered item with its status. A template consumer repo may
extend this file, but must not silently delete requirements — removals are
a conscious, documented decision.

---

*Last reviewed: 2026-07-24 · Source paths: `LASTENHEFT.md` (this file),
`REPO_SETUP_CHECKLIST.md`, `CLAUDE.md` · Standards:
[Ranzlappen/repo-standards](https://github.com/Ranzlappen/repo-standards) v3.2.0*
