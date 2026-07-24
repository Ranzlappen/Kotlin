# FAQ

Recurring questions get answered here; anything asked more than twice
should graduate into the README or CLAUDE.md.

## Using the template

**Q: How do I start a new app from this?**
Create a repo via *Use this template*, run
`scripts/customizer.sh <package> <AppName> [repo]`, then follow
`REPO_SETUP_CHECKLIST.md` (human) and the `LASTENHEFT.md` Final Pass (AI).

**Q: Why does the repo ship a keystore? Isn't that insecure?**
`signing/fallback.keystore` is deliberately public (password `template`).
It gives every build a stable signature before real secrets exist and
provides zero security by design. See `signing/README.md`.

**Q: Why is there no version number in the code?**
Versions come from git tags; CI injects `-PversionName/-PversionCode`.
Local builds default to `1.0.0 (1)`.

## Development

**Q: Why no build-logic convention plugins like now-in-android?**
Deliberate: AGP 9's built-in Kotlin is young, and a template's consumers
edit build files directly. See the decision in
[Architecture](./Architecture).

**Q: Where do I add a dependency?**
`gradle/libs.versions.toml` only — never inline in a module file.

**Q: How do I add a language?**
`values-<tag>/` folders for every module with strings +
`res/xml/locales_config.xml` + `AppLanguage` enum entry.

**Q: There's no Android SDK on my machine/agent session — how do I build?**
Push your branch: CI runs the full pipeline on `claude/**` branches and
PRs. CI is the compile gate.

## Operations

**Q: How do I cut a release?**
Actions → Release → *Run workflow* → pick patch/minor/major. The workflow
tags, builds debug APK + signed release APK + AAB, and publishes the
GitHub Release.

**Q: Why did dependency-review not block a vulnerable PR?**
The repo's Dependency graph toggle probably isn't enabled yet — see
`REPO_SETUP_CHECKLIST.md` §3.

---
*Last reviewed: 2026-07-24 · Source paths: `README.md`, `REPO_SETUP_CHECKLIST.md` · Related: `LASTENHEFT.md`*
