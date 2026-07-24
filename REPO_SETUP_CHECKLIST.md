# Repo Setup Checklist (for the repo admin)

> **Who this is for:** me, the human repo admin. These are the GitHub
> settings an AI agent **cannot** configure (or shouldn't without me).
> Work through it top to bottom after creating a repo from this template.
> AI agents: your part is the [LASTENHEFT.md Final Pass](LASTENHEFT.md#final-pass-for-ai-agents);
> when it runs, remind me of whatever below is still unchecked.

## 1. Immediately after creating the repo

- [ ] Run `scripts/customizer.sh <package> <AppName> [repo-name]`, review,
      commit.
- [ ] **Settings → General**
  - [ ] ✅ **Automatically delete head branches** (after merge)
  - [ ] ✅ Allow **auto-merge**
  - [ ] ✅ Allow **squash merging** (default commit message: PR title);
        decide on merge commits / rebase (my default: squash only)
  - [ ] Enable **Issues**, **Wiki**; disable Projects/Discussions unless
        needed
  - [ ] Add description, topics (`android`, `kotlin`, `compose`, …), and
        website URL (the Pages URL below)
- [ ] **Template flag**: if this repo should itself be a template —
      Settings → General → ✅ *Template repository* (the `Ranzlappen/Kotlin`
      origin repo keeps this ON; apps created from it keep it OFF)

## 2. Branch protection (Settings → Branches → `main`)

- [ ] Require a pull request before merging, ≥ 1 approval
- [ ] Dismiss stale approvals on new pushes
- [ ] Require status checks: **Build & test** (CI), **Dependency review**,
      **CodeQL** — mark "require branches up to date"
- [ ] Require conversation resolution
- [ ] Block force pushes and deletions
- [ ] (Optional, repo-standards governance): linear history, no admin
      bypass; tag protection rule for `v*`

## 3. Actions & security (Settings)

- [ ] **Actions → General**: Workflow permissions = *Read repository
      contents* (workflows elevate per-job themselves); ✅ Allow GitHub
      Actions to create and approve PRs **off**
- [ ] **Advanced Security**: enable **Dependency graph**, **Dependabot
      alerts**, **Dependabot security updates**, **Secret scanning** (+
      push protection)
- [ ] After Dependency graph is on: remove the `continue-on-error` line in
      `.github/workflows/dependency-review.yml` (turns it into a hard gate)
- [ ] **Secrets → Actions**: add `KEYSTORE_BASE64`, `KEYSTORE_PASSWORD`,
      `KEY_ALIAS`, `KEY_PASSWORD` when real signing starts
      (see `signing/README.md`)
- [ ] **Variables → Actions**: `STALE_ENABLED=true` only if the stale bot
      should run

## 4. GitHub Pages + DNS (custom subdomain)

- [ ] **Settings → Pages → Build and deployment → Source: “GitHub
      Actions”** (NOT “Deploy from a branch” — the `pages.yml` workflow is
      the deployer)
- [ ] DNS at the registrar: `CNAME <subdomain> → ranzlappen.github.io`
      (subdomain only, e.g. `myapp` → `myapp.ranzlappen.com`; never point
      it at the repo)
- [ ] **Settings → Pages → Custom domain**: `<subdomain>.ranzlappen.com`,
      wait for the DNS check, then ✅ **Enforce HTTPS**
- [ ] Update `site/CNAME` to the same value and push (keeps the domain
      across deploys)
- [ ] Verify the PWA installs (manifest + service worker served over HTTPS)

## 5. Wiki

- [ ] Create the first wiki page in the UI (activates the wiki git repo)
- [ ] Paste/push the pages from `wiki/` (source of truth stays in-repo;
      mirror on change — same PR rule from CLAUDE.md)

## 6. Integrations & notifications

- [ ] **Discord webhook**: Discord server → Integrations → Webhooks → copy
      URL, then GitHub → Settings → Webhooks → Add:
      Payload URL = `<discord-webhook-url>/github`, content type
      `application/json`, events: *Send me everything* (or curate:
      pushes, releases, issues, PRs)
- [ ] Watch/notification settings for the repo (releases at minimum)
- [ ] (Optional) Claude GitHub App / PR steward on the repo

## 7. Dependabot auto-merge (optional but my default)

- [ ] With branch protection + auto-merge enabled above, grouped
      minor/patch dependency PRs can auto-merge once CI is green: enable
      auto-merge per PR, or add a small workflow later — decide per repo

## 8. README badges (after the first CI run)

- [ ] Check the badge URLs render: CI, Release, License, repo-standards,
      Ko-fi (they're pre-wired in README.md — broken ones mean a workflow
      hasn't run yet or the repo rename wasn't propagated)

## 9. First release sanity check

- [ ] Actions → **Release** → Run workflow → `patch` → confirm the Release
      appears with `app-debug.apk`, `app-release.apk`, `app-release.aab`
- [ ] Install the release APK on a device; verify in-place update works on
      the next release (same signature)

---

*Last reviewed: 2026-07-24 · Source paths: `REPO_SETUP_CHECKLIST.md`,
`.github/workflows/*`, `site/` · Related: `LASTENHEFT.md`, `signing/README.md`*
