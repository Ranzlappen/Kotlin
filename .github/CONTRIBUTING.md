# Contributing

Thanks for your interest! Quick links: [README](../README.md) ·
[CLAUDE.md](../CLAUDE.md) (AI agent guide) · [LASTENHEFT.md](../LASTENHEFT.md)
(requirements spec) · [Issues](../../issues).

## Before you start

- **Open an issue first** for anything beyond a trivial fix, so we can agree
  on the approach before you invest time.
- Search existing open and closed issues for duplicates.

## Workflow

1. Fork / branch from `main`. Branch names: `feat/…`, `fix/…`, `docs/…`,
   `chore/…`.
2. Make your change. Keep commits small and focused.
3. Use [Conventional Commits](https://www.conventionalcommits.org/):
   `feat:`, `fix:`, `docs:`, `style:`, `refactor:`, `perf:`, `test:`,
   `build:`, `ci:`, `chore:`, `revert:`.
4. Run the local gate before pushing:
   `./gradlew spotlessApply detekt testDebugUnitTest lintDebug`
   (CI runs the same; there is no way around it).
5. Open a PR against `main` and fill in the template. One logical change per
   PR.

## Review

Expect a first response within about a week. PRs need CI green and one
approval to merge. Stale approvals are dismissed on new pushes.

## Community standards

Contributions are bound by the
[Code of Conduct](CODE_OF_CONDUCT.md), GitHub's
[Community Guidelines](https://docs.github.com/site-policy/github-terms/github-community-guidelines),
and GitHub's Acceptable Use Policies. Report conduct issues to
<info@ranzlappen.com>.
