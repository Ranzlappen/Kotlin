# Security Policy

## Reporting a vulnerability

Please report vulnerabilities **privately** — never in a public issue:

1. Preferred: [open a draft security advisory](../../security/advisories/new).
2. Alternative: email <info@ranzlappen.com>.

Include: affected version/commit, reproduction steps, impact assessment,
and any suggested fix.

**SLAs:** acknowledgement within 3 business days, triage within 10 business
days, coordinated disclosure within 30–90 days depending on severity.

## Supported versions

| Version | Supported |
| --- | --- |
| Latest release | ✅ |
| Older releases | ❌ — upgrade to the latest |

## Out of scope

- The committed **public fallback keystore** (`signing/fallback.keystore`).
  It is intentionally public and provides zero security by design; it exists
  only so debug builds and secretless CI builds share a stable signature.
  Reports about it will be closed.
- Vulnerabilities requiring a rooted device or a compromised OS.
- Dependency CVEs without a demonstrated exploit path in this app
  (Dependabot and dependency-review already track these).
