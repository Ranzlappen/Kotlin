## Summary

<!-- What does this PR change, and why? 2–4 sentences. -->

## Type of change

<!-- Check all that apply. -->

- [ ] Bug fix
- [ ] New feature
- [ ] Refactoring (no behavior change)
- [ ] Documentation / wiki
- [ ] CI / build / tooling

## Checklist

- [ ] `./gradlew spotlessApply detekt testDebugUnitTest lintDebug` passes locally (or CI is green)
- [ ] New user-facing strings are localized (`values/` **and** `values-de/`, plus any other shipped locales)
- [ ] No new permissions added — or the addition is justified against LASTENHEFT.md in this PR
- [ ] No feature-to-feature dependencies introduced (features talk through `core/*`)
- [ ] Docs kept in sync: README / CLAUDE.md / wiki / in-app manual updated where behavior changed
- [ ] Conventional Commit messages

## Test plan

<!-- How was this verified? Unit tests, manual steps on a device/emulator, screenshots for UI changes. -->
